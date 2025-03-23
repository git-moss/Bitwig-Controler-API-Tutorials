// MIT License
// 
// Copyright (c) 2019-2025 Jürgen Moßgraber
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package de.mossgrabers;

import com.bitwig.extension.callback.ShortMidiDataReceivedCallback;
import com.bitwig.extension.controller.api.MidiIn;
import com.bitwig.extension.controller.api.MidiOut;
import com.bitwig.extension.controller.api.NoteInput;
import com.bitwig.extension.controller.api.SettableEnumValue;
import com.bitwig.extension.controller.api.SettableRangedValue;

import java.util.Arrays;


/**
 * Encapsulates the button and knob IDs of the MOXF and the MIDI connection.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class MOXFHardware
{
    /** The Solo button. */
    public static final int       MOXF_BUTTON_SOLO     = 0x08;
    /** The Mute button. */
    public static final int       MOXF_BUTTON_MUTE     = 0x10;

    /** The function button 1. */
    public static final int       MOXF_BUTTON_F1       = 0x36;
    /** The function button 2. */
    public static final int       MOXF_BUTTON_F2       = 0x37;
    /** The function button 3. */
    public static final int       MOXF_BUTTON_F3       = 0x38;
    /** The function button 4. */
    public static final int       MOXF_BUTTON_F4       = 0x39;
    /** The function button 5. */
    public static final int       MOXF_BUTTON_F5       = 0x3A;
    /** The function button 6. */
    public static final int       MOXF_BUTTON_F6       = 0x3B;

    /** The select button 1. */
    public static final int       MOXF_BUTTON_SF1      = 0x3C;
    /** The select button 2. */
    public static final int       MOXF_BUTTON_SF2      = 0x3D;
    /** The select button 3. */
    public static final int       MOXF_BUTTON_SF3      = 0x3E;
    /** The select button 4. */
    public static final int       MOXF_BUTTON_SF4      = 0x3F;
    /** The select button 5. */
    public static final int       MOXF_BUTTON_SF5      = 0x40;
    /** The select button 6. */
    public static final int       MOXF_BUTTON_SF6      = 0x41;

    /** The button A. */
    public static final int       MOXF_BUTTON_A        = 0x42;
    /** The button B. */
    public static final int       MOXF_BUTTON_B        = 0x43;
    /** The button C. */
    public static final int       MOXF_BUTTON_C        = 0x44;
    /** The button D. */
    public static final int       MOXF_BUTTON_D        = 0x45;
    /** The button E. */
    public static final int       MOXF_BUTTON_E        = 0x46;
    /** The button F. */
    public static final int       MOXF_BUTTON_F        = 0x47;

    /** The Play button. */
    public static final int       MOXF_BUTTON_PLAY     = 0x5E;
    /** The Stop button. */
    public static final int       MOXF_BUTTON_STOP     = 0x5D;
    /** The Record button. */
    public static final int       MOXF_BUTTON_REC      = 0x5F;
    /** The Forward button. */
    public static final int       MOXF_BUTTON_FWD      = 0x5C;
    /** The Backwards button. */
    public static final int       MOXF_BUTTON_BACK     = 0x5B;
    /** The Locate button. */
    public static final int       MOXF_BUTTON_LOCATE   = 0x58;

    /** The Category button. */
    public static final int       MOXF_BUTTON_CATEGORY = 0x76;
    /** The Favorite button. */
    public static final int       MOXF_BUTTON_FAVORITE = 0x77;

    /** The main knob. */
    public static final int       MOXF_KNOB_MAIN       = 0x3C;
    /** The knob 1. */
    public static final int       MOXF_KNOB_1          = 0x4A;
    /** The knob 2. */
    public static final int       MOXF_KNOB_2          = 0x47;
    /** The knob 3. */
    public static final int       MOXF_KNOB_3          = 0x49;
    /** The knob 4. */
    public static final int       MOXF_KNOB_4          = 0x48;
    /** The knob 5. */
    public static final int       MOXF_KNOB_5          = 0x1C;
    /** The knob 6. */
    public static final int       MOXF_KNOB_6          = 0x1D;
    /** The knob 7. */
    public static final int       MOXF_KNOB_7          = 0x1E;
    /** The knob 8. */
    public static final int       MOXF_KNOB_8          = 0x1F;

    /** The options for turning on/off the fixed velocity option. */
    public static final String [] BOOLEAN_OPTIONS      =
    {
        "Off",
        "On"
    };

    private final MidiOut         portOut;
    private final MidiIn          portIn;
    private final int []          ledCache             = new int [128];
    private final NoteInput       noteIn;
    private final Integer []      velocityMapIdentity  = new Integer [128];
    private final Integer []      velocityMapFixed     = new Integer [128];

    private boolean               isFixedVelocity;


    /**
     * Construcotr.
     *
     * @param outputPort The MIDI output port
     * @param inputPort The MIDI input port
     * @param inputCallback The MIDI input callback
     * @param fixVelocityEnableSetting The fixed velocity setting
     * @param fixVelocityValueSetting The value for the fixed velocity setting
     */
    public MOXFHardware (final MidiOut outputPort, final MidiIn inputPort, final ShortMidiDataReceivedCallback inputCallback, final SettableEnumValue fixVelocityEnableSetting, final SettableRangedValue fixVelocityValueSetting)
    {
        this.portOut = outputPort;
        this.portIn = inputPort;

        Arrays.fill (this.ledCache, -1);

        this.portIn.setMidiCallback (inputCallback);
        this.noteIn = this.portIn.createNoteInput ("Yamaha MOXF", "91????", "81????", "B101??", "B140??", "E1????");

        for (int i = 0; i < 128; i++)
            this.velocityMapIdentity[i] = Integer.valueOf (i);

        this.isFixedVelocity = false;

        fixVelocityEnableSetting.addValueObserver (this::setVelocityMap);
        fixVelocityValueSetting.addRawValueObserver (this::updateVelocityMap);
    }


    /**
     * Set the velocity map depending on the given setting value (callback).
     *
     * @param value The setting value from BOOLEAN_OPTIONS
     */
    public void setVelocityMap (final String value)
    {
        this.isFixedVelocity = value == BOOLEAN_OPTIONS[1];
        this.noteIn.setVelocityTranslationTable (this.isFixedVelocity ? this.velocityMapFixed : this.velocityMapIdentity);
    }


    /**
     * Update the fixed velocity map with the given value.
     *
     * @param value The new fixed setting value
     */
    public void updateVelocityMap (final Double value)
    {
        final Integer val = Integer.valueOf (value.intValue ());
        for (int i = 0; i < 128; i++)
            this.velocityMapFixed[i] = val;
        if (this.isFixedVelocity)
            this.setVelocityMap (BOOLEAN_OPTIONS[1]);
    }


    /**
     * Update an LED on the MOXF (only an example for how to do this but not working with the MOXF).
     *
     * @param note The note
     * @param isOn True to turn on
     */
    public void updateLED (final int note, final boolean isOn)
    {
        final int value = isOn ? 127 : 0;
        if (this.ledCache[note] == value)
            return;
        this.ledCache[note] = value;
        this.portOut.sendMidi (0x90, note, value);
    }
}