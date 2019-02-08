// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.handler;

import de.mossgrabers.MOXFHardware;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.controller.api.CursorDevice;
import com.bitwig.extension.controller.api.CursorRemoteControlsPage;


/**
 * The remote control mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class RemoteControlHandler implements Mode
{
    private final CursorDevice             cursorDevice;
    private final CursorRemoteControlsPage remoteControlsBank;


    /**
     * Constructor.
     *
     * @param cursorDevice The cursor device
     * @param remoteControlsBank The remote controls bank
     */
    public RemoteControlHandler (final CursorDevice cursorDevice, final CursorRemoteControlsPage remoteControlsBank)
    {
        this.cursorDevice = cursorDevice;
        this.remoteControlsBank = remoteControlsBank;

        for (int i = 0; i < this.remoteControlsBank.getParameterCount (); i++)
            this.remoteControlsBank.getParameter (i).markInterested ();

        this.cursorDevice.isEnabled ().markInterested ();
        this.cursorDevice.isWindowOpen ().markInterested ();
    }


    /** {@inheritDoc} */
    @Override
    public String getName ()
    {
        return "Device Mode";
    }


    /** {@inheritDoc} */
    @Override
    public void setIndication (final boolean enable)
    {
        for (int i = 0; i < this.remoteControlsBank.getParameterCount (); i++)
            this.remoteControlsBank.getParameter (i).setIndication (enable);
    }


    /** {@inheritDoc} */
    @Override
    public boolean handleMidi (final ShortMidiMessage message)
    {
        if (message.isNoteOn ())
        {
            switch (message.getData1 ())
            {
                case MOXFHardware.MOXF_BUTTON_SF1:
                    this.cursorDevice.selectPrevious ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF2:
                    this.cursorDevice.selectNext ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF3:
                    // Not used
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF4:
                    // Not used
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF5:
                    this.remoteControlsBank.selectPrevious ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF6:
                    this.remoteControlsBank.selectNext ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SOLO:
                    this.cursorDevice.isWindowOpen ().toggle ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_MUTE:
                    this.cursorDevice.isEnabled ().toggle ();
                    return true;

                default:
                    return false;
            }
        }

        if (message.isControlChange ())
        {
            final int data2 = message.getData2 ();
            final Integer value = Integer.valueOf (data2 > 64 ? 64 - data2 : data2);

            switch (message.getData1 ())
            {
                // Absolute values
                case MOXFHardware.MOXF_KNOB_1:
                    this.remoteControlsBank.getParameter (0).set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_2:
                    this.remoteControlsBank.getParameter (1).set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_3:
                    this.remoteControlsBank.getParameter (2).set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_4:
                    this.remoteControlsBank.getParameter (3).set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                // Relative values
                case MOXFHardware.MOXF_KNOB_5:
                    this.remoteControlsBank.getParameter (4).inc (value, Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_6:
                    this.remoteControlsBank.getParameter (5).inc (value, Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_7:
                    this.remoteControlsBank.getParameter (6).inc (value, Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_8:
                    this.remoteControlsBank.getParameter (7).inc (value, Integer.valueOf (128));
                    return true;

                default:
                    return false;
            }
        }

        return false;
    }
}