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

package de.mossgrabers.handler;

import de.mossgrabers.MOXFHardware;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.SettableEnumValue;


/**
 * Handles all available modes.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class ModeHandler
{
    /** Description texts for the available modes. */
    public static final String []   MODE_OPTIONS =
    {
        "Track",
        "Device"
    };

    private final Mode []           modes;
    private final SettableEnumValue modeSetting;
    private final ControllerHost    host;

    private Mode                    activeMode;


    /**
     * Constructor.
     *
     * @param modes The avilable modes
     * @param modeSetting The setting to store the active mode
     * @param host The controller host
     */
    public ModeHandler (final Mode [] modes, final SettableEnumValue modeSetting, final ControllerHost host)
    {
        this.modes = modes;
        this.modeSetting = modeSetting;
        this.host = host;

        this.setActiveMode (modes[0]);

        this.modeSetting.addValueObserver (value -> {
            for (int i = 0; i < MODE_OPTIONS.length; i++)
            {
                if (MODE_OPTIONS[i] == value)
                    this.setActiveMode (this.modes[i]);
            }
        });
    }


    /**
     * Set the active mode.
     *
     * @param newMode The new active mode to set
     */
    public void setActiveMode (final Mode newMode)
    {
        this.activeMode = newMode;
        this.host.showPopupNotification (this.activeMode.getName ());
        this.updateIndication ();
    }


    /**
     * Update the indications in Bitwig.
     */
    public void updateIndication ()
    {
        for (final Mode mode: this.modes)
            mode.setIndication (false);
        this.activeMode.setIndication (true);
    }


    /**
     * Handle/process the given MIDI message.
     *
     * @param message The message to process
     * @return True if the message was process by the method
     */
    public boolean handleMidi (final ShortMidiMessage message)
    {
        if (message.isNoteOn ())
        {
            switch (message.getData1 ())
            {
                case MOXFHardware.MOXF_BUTTON_A:
                    this.setActiveMode (this.modes[0]);
                    this.modeSetting.set (MODE_OPTIONS[0]);
                    return true;

                case MOXFHardware.MOXF_BUTTON_B:
                    this.setActiveMode (this.modes[1]);
                    this.modeSetting.set (MODE_OPTIONS[1]);
                    return true;
            }
        }

        if (this.activeMode.handleMidi (message))
            return true;

        return false;
    }
}