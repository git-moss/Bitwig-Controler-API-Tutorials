package com.moss;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.AbsoluteHardwareKnob;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.HardwareButton;
import com.bitwig.extension.controller.api.HardwareSlider;
import com.bitwig.extension.controller.api.HardwareSurface;
import com.bitwig.extension.controller.api.MidiIn;
import com.bitwig.extension.controller.api.PianoKeyboard;
import com.bitwig.extension.controller.api.RelativeHardwareKnob;
import com.bitwig.extension.controller.api.TrackBank;
import com.bitwig.extension.controller.api.Transport;


/**
 * Demo code to explain the Bitwig Hardware API.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class HardwareAPITest3Extension extends ControllerExtension
{
    private final int channel        = 0;
    private final int buttonControl  = 112; // TODO -> EXCHANGE WITH YOUR CONTROL
    private final int sliderControl  = 14;  // TODO -> EXCHANGE WITH YOUR CONTROL
    private final int absKnobControl = 15;  // TODO -> EXCHANGE WITH YOUR CONTROL
    private final int relKnobControl = 16;  // TODO -> EXCHANGE WITH YOUR CONTROL


    protected HardwareAPITest3Extension (final HardwareAPITest3ExtensionDefinition definition, final ControllerHost host)
    {
        super (definition, host);
    }


    /** {@inheritDoc} */
    @Override
    public void init ()
    {
        final ControllerHost host = this.getHost ();

        // 1. Create the controls

        final HardwareSurface hardwareSurface = host.createHardwareSurface ();
        // HardwareButton
        final HardwareButton playButton = hardwareSurface.createHardwareButton ("PLAY_BUTTON");
        // HardwareSlider
        final HardwareSlider slider = hardwareSurface.createHardwareSlider ("SLIDER_1");
        // AbsoluteHardwareKnob
        final AbsoluteHardwareKnob absKnob = hardwareSurface.createAbsoluteHardwareKnob ("ABSOLUTE_KNOB");
        // RelativeHardwareKnob
        final RelativeHardwareKnob relKnob = hardwareSurface.createRelativeHardwareKnob ("RELATIVE_KNOB");

        // PianoKeyboard
        final PianoKeyboard keyboard = hardwareSurface.createPianoKeyboard ("THE_KEYBOARD", 49, 0, 36);

        // 2. Bind the controls to MIDI commands

        final MidiIn port = host.getMidiInPort (0);
        playButton.pressedAction ().setActionMatcher (port.createCCActionMatcher (this.channel, this.buttonControl, 127));
        slider.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (this.channel, this.sliderControl));
        absKnob.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (this.channel, this.absKnobControl));
        relKnob.setAdjustValueMatcher (port.createRelativeBinOffsetCCValueMatcher (this.channel, this.relKnobControl, 127));

        // Use these actions to bind to knob touch events...
        // relKnob.beginTouchAction ()
        // relKnob.endTouchAction ()

        port.createNoteInput ("Hardware Test 3");
        keyboard.setMidiIn (port);

        // 3. Bind the controls to actions / targets

        final Transport transport = host.createTransport ();
        playButton.pressedAction ().setBinding (transport.playAction ());

        final TrackBank trackBank = host.createMainTrackBank (3, 0, 0);
        slider.setBinding (trackBank.getItemAt (0).volume ());
        absKnob.setBinding (trackBank.getItemAt (1).volume ());
        relKnob.setBinding (trackBank.getItemAt (2).volume ());

        // Use these actions to bind to knob touch events...
        // relKnob.beginTouchAction ()
        // relKnob.endTouchAction ()

        // If you want to create a clickable knob...
        // relKnob.setHardwareButton (playButton);

        // 4. Layout the simulator UI

        hardwareSurface.setPhysicalSize (200, 200);

        playButton.setBounds (137.0, 133.5, 8.75, 7.0);
        hardwareSurface.hardwareElementWithId ("SLIDER_1").setBounds (106.0, 120.0, 10.0, 34.25);
        hardwareSurface.hardwareElementWithId ("ABSOLUTE_KNOB").setBounds (127.25, 12.25, 10.0, 10.0);
        hardwareSurface.hardwareElementWithId ("RELATIVE_KNOB").setBounds (127.25, 78.75, 10.0, 10.0);
        hardwareSurface.hardwareElementWithId ("THE_KEYBOARD").setBounds (4.5, 163.5, 191.0, 28.25);
    }


    /** {@inheritDoc} */
    @Override
    public void exit ()
    {
        // Not used
    }


    /** {@inheritDoc} */
    @Override
    public void flush ()
    {
        // Not used
    }
}
