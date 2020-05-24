package com.moss;

import com.bitwig.extension.api.Color;
import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.AbsoluteHardwareKnob;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.HardwareButton;
import com.bitwig.extension.controller.api.HardwareLightVisualState;
import com.bitwig.extension.controller.api.HardwareSlider;
import com.bitwig.extension.controller.api.HardwareSurface;
import com.bitwig.extension.controller.api.HardwareTextDisplay;
import com.bitwig.extension.controller.api.HardwareTextDisplayLine;
import com.bitwig.extension.controller.api.MidiIn;
import com.bitwig.extension.controller.api.MidiOut;
import com.bitwig.extension.controller.api.OnOffHardwareLight;
import com.bitwig.extension.controller.api.PianoKeyboard;
import com.bitwig.extension.controller.api.RelativeHardwareKnob;
import com.bitwig.extension.controller.api.TrackBank;
import com.bitwig.extension.controller.api.Transport;


/**
 * Demo code to explain the Bitwig Hardware API.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class HardwareAPITest4Extension extends ControllerExtension
{
    private final int       channel        = 0;
    private final int       buttonControl  = 112; // TODO -> EXCHANGE WITH YOUR CONTROL
    private final int       sliderControl  = 14;  // TODO -> EXCHANGE WITH YOUR CONTROL
    private final int       absKnobControl = 15;  // TODO -> EXCHANGE WITH YOUR CONTROL
    private final int       relKnobControl = 16;  // TODO -> EXCHANGE WITH YOUR CONTROL

    private HardwareSurface hardwareSurface;


    protected HardwareAPITest4Extension (final HardwareAPITest4ExtensionDefinition definition, final ControllerHost host)
    {
        super (definition, host);
    }


    /** {@inheritDoc} */
    @Override
    public void init ()
    {
        final ControllerHost host = this.getHost ();

        /////////////////////////////////////////////////////////////////////////////////////
        // 1. Create the controls

        // 1.1 Create input controls

        this.hardwareSurface = host.createHardwareSurface ();
        // HardwareButton
        final HardwareButton playButton = this.hardwareSurface.createHardwareButton ("PLAY_BUTTON");
        // HardwareSlider
        final HardwareSlider slider = this.hardwareSurface.createHardwareSlider ("SLIDER_1");
        // AbsoluteHardwareKnob
        final AbsoluteHardwareKnob absKnob = this.hardwareSurface.createAbsoluteHardwareKnob ("ABSOLUTE_KNOB");
        // RelativeHardwareKnob
        final RelativeHardwareKnob relKnob = this.hardwareSurface.createRelativeHardwareKnob ("RELATIVE_KNOB");

        // PianoKeyboard
        final PianoKeyboard keyboard = this.hardwareSurface.createPianoKeyboard ("THE_KEYBOARD", 49, 0, 36);

        // 1.2 Create output controls

        // OnOffHardwareLight
        final OnOffHardwareLight playButtonLight = this.hardwareSurface.createOnOffHardwareLight ("PLAY_BUTTON_LIGHT");
        playButton.setBackgroundLight (playButtonLight);

        // HardwareTextDisplay
        final HardwareTextDisplay textDisplay = this.hardwareSurface.createHardwareTextDisplay ("TEXT_DISPLAY", 1);

        /////////////////////////////////////////////////////////////////////////////////////
        // 2. Bind the controls to MIDI commands

        final MidiIn port = host.getMidiInPort (0);
        playButton.pressedAction ().setActionMatcher (port.createCCActionMatcher (this.channel, this.buttonControl, 127));
        playButton.releasedAction ().setActionMatcher (port.createCCActionMatcher (this.channel, this.buttonControl, 0));

        slider.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (this.channel, this.sliderControl));
        absKnob.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (this.channel, this.absKnobControl));
        relKnob.setAdjustValueMatcher (port.createRelativeBinOffsetCCValueMatcher (this.channel, this.relKnobControl, 127));

        // Use these actions to bind to knob touch events...
        // relKnob.beginTouchAction ()
        // relKnob.endTouchAction ()

        port.createNoteInput ("Hardware Test 3");
        keyboard.setMidiIn (port);

        /////////////////////////////////////////////////////////////////////////////////////
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

        /////////////////////////////////////////////////////////////////////////////////////
        // 4. Set and create output element callbacks

        final MidiOut midiOutPort = host.getMidiOutPort (0);

        // Use state() instead of isOn() for the MultiStateHardwareLight

        transport.isPlaying ().markInterested ();
        playButtonLight.isOn ().setValueSupplier ( () -> transport.isPlaying ().get ());

        playButtonLight.isOn ().onUpdateHardware (isOn -> {

            host.println ("Updating LED: " + (isOn.booleanValue () ? "On" : "Off"));

            // TODO Send the LED light state to the display, normally this is a CC or note command
            // midiOutPort.sendMidi (int status, int data1, int data2)
        });

        playButtonLight.setStateToVisualStateFuncation (onOffState -> {

            final Color backgroundColor = onOffState.booleanValue () ? Color.fromRGB (0, 1, 0) : Color.fromRGB (0, 0, 0);
            final Color labelColor = onOffState.booleanValue () ? Color.fromRGB (0, 0, 0) : Color.fromRGB (1, 1, 1);
            return HardwareLightVisualState.createForColor (backgroundColor, labelColor);

        });

        textDisplay.line (0).text ().setValueSupplier ( () -> {

            final boolean isPlaying = transport.isPlaying ().get ();
            return "Playback is: " + (isPlaying ? "On" : "Off");

        });

        textDisplay.onUpdateHardware ( () -> {

            final HardwareTextDisplayLine displayLine = textDisplay.line (0);
            final String content = displayLine.text ().currentValue ();

            // Note: line.backgroundColor () and line.textColor () can also be used if the
            // displays colors can be changed ...

            host.println ("Updating display: " + content);
            // TODO Send 'content' to the display, normally this is a sysex command
            // midiOutPort.sendSysex (byte[] data)

        });

        /////////////////////////////////////////////////////////////////////////////////////
        // 5. Layout the simulator UI

        this.hardwareSurface.setPhysicalSize (200, 200);

        playButton.setBounds (137.0, 133.5, 30.75, 11.5);
        this.hardwareSurface.hardwareElementWithId ("SLIDER_1").setBounds (106.0, 120.0, 10.0, 34.25);
        this.hardwareSurface.hardwareElementWithId ("ABSOLUTE_KNOB").setBounds (127.25, 12.25, 10.0, 10.0);
        this.hardwareSurface.hardwareElementWithId ("RELATIVE_KNOB").setBounds (127.25, 78.75, 10.0, 10.0);
        this.hardwareSurface.hardwareElementWithId ("THE_KEYBOARD").setBounds (4.5, 163.5, 191.0, 28.25);
        this.hardwareSurface.hardwareElementWithId ("TEXT_DISPLAY").setBounds (16.25, 25.75, 87.75, 10.0);
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
        if (this.hardwareSurface != null)
            this.hardwareSurface.updateHardware ();
    }
}
