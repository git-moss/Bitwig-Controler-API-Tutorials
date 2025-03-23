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

import com.bitwig.extension.api.util.midi.ShortMidiMessage;


/**
 * A mode allows to use the same controls on a device for different tasks depending on the active
 * mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public interface Mode
{
    /**
     * Get a descriptive name for the mode.
     *
     * @return The name
     */
    String getName ();


    /**
     * Set the indication for the controls in Bitwig.
     *
     * @param enable True to enable otherwise disable
     */
    void setIndication (boolean enable);


    /**
     * Handle/react to the midi commands of the buttons and knobs of the device.
     *
     * @param message The midi message
     * @return True if the midi message was processed in the method
     */
    boolean handleMidi (ShortMidiMessage message);
}
