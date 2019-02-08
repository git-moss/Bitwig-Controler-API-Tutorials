// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers;

import de.mossgrabers.handler.Mode;
import de.mossgrabers.handler.ModeHandler;
import de.mossgrabers.handler.RemoteControlHandler;
import de.mossgrabers.handler.TrackHandler;
import de.mossgrabers.handler.TransportHandler;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorDeviceFollowMode;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.DocumentState;
import com.bitwig.extension.controller.api.PinnableCursorDevice;
import com.bitwig.extension.controller.api.Preferences;
import com.bitwig.extension.controller.api.SettableEnumValue;
import com.bitwig.extension.controller.api.SettableRangedValue;


/**
 * Bitwig extension for the Yamaha MOXF.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class MOXFExtensionExtension extends ControllerExtension
{
    private TransportHandler transportHandler;
    private ModeHandler      modeHandler;


    /**
     * Constructor.
     *
     * @param definition The definition object
     * @param host The controller host
     */
    protected MOXFExtensionExtension (final MOXFExtensionExtensionDefinition definition, final ControllerHost host)
    {
        super (definition, host);
    }


    /** {@inheritDoc} */
    @Override
    public void init ()
    {
        final ControllerHost host = this.getHost ();

        // Preferences
        final Preferences preferences = host.getPreferences ();
        final SettableEnumValue modeSetting = preferences.getEnumSetting ("Mode", "Global", ModeHandler.MODE_OPTIONS, ModeHandler.MODE_OPTIONS[0]);

        // Document States
        final DocumentState documentState = host.getDocumentState ();
        final SettableEnumValue fixVelocityEnableSetting = documentState.getEnumSetting ("Enable", "Fix velocity", MOXFHardware.BOOLEAN_OPTIONS, MOXFHardware.BOOLEAN_OPTIONS[0]);
        final SettableRangedValue fixVelocityValueSetting = documentState.getNumberSetting ("Velocity", "Fix velocity", 0, 127, 1, "", 127);

        final MOXFHardware hardware = new MOXFHardware (host.getMidiOutPort (0), host.getMidiInPort (0), this::handleMidi, fixVelocityEnableSetting, fixVelocityValueSetting);
        this.transportHandler = new TransportHandler (host.createTransport (), hardware);

        final CursorTrack cursorTrack = host.createCursorTrack ("MOXF_CURSOR_TRACK", "Cursor Track", 0, 0, true);
        final TrackHandler trackHandler = new TrackHandler (host.createMainTrackBank (4, 0, 0), cursorTrack);

        final PinnableCursorDevice cursorDevice = cursorTrack.createCursorDevice ("MOXF_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
        final RemoteControlHandler remoteControlHandler = new RemoteControlHandler (cursorDevice, cursorDevice.createCursorRemoteControlsPage (8));

        final Mode [] modes = new Mode []
        {
            trackHandler,
            remoteControlHandler
        };
        this.modeHandler = new ModeHandler (modes, modeSetting, host);

        host.println ("MOXF initialized!");
    }


    /**
     * Callback for receiving MIDI data.
     *
     * @param statusByte The status byte
     * @param data1 The data1 byte
     * @param data2 The data2 byte
     */
    public void handleMidi (final int statusByte, final int data1, final int data2)
    {
        final ShortMidiMessage msg = new ShortMidiMessage (statusByte, data1, data2);

        if (this.transportHandler.handleMidi (msg))
            return;

        if (this.modeHandler.handleMidi (msg))
            return;

        this.getHost ().errorln ("Midi command not processed: " + msg.getStatusByte () + " : " + msg.getData1 ());
    }


    /** {@inheritDoc} */
    @Override
    public void exit ()
    {
        this.getHost ().println ("MOXF Exited.");
    }


    /** {@inheritDoc} */
    @Override
    public void flush ()
    {
        this.transportHandler.updateLEDs ();
    }
}
