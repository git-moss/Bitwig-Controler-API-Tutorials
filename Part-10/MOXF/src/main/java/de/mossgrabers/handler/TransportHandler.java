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
import com.bitwig.extension.controller.api.Transport;


/**
 * Handles all MIDI commands related to the transport controls like Play, Stop, etc.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class TransportHandler
{
    private Transport    transport;
    private MOXFHardware hardware;


    /**
     * Constructor.
     *
     * @param transport The Bitwig transport object
     * @param hardware The MOXF device
     */
    public TransportHandler (final Transport transport, final MOXFHardware hardware)
    {
        this.transport = transport;
        this.hardware = hardware;

        this.transport.isPlaying ().markInterested ();
        this.transport.isArrangerRecordEnabled ().markInterested ();
    }


    /**
     * Handle/process the given MIDI message.
     *
     * @param message The message to process
     * @return True if the message was process by the method
     */
    public boolean handleMidi (final ShortMidiMessage message)
    {
        if (!message.isNoteOn ())
            return false;

        if (message.getData2 () == 0)
            return true;

        switch (message.getData1 ())
        {
            case MOXFHardware.MOXF_BUTTON_PLAY:
                this.transport.play ();
                return true;

            case MOXFHardware.MOXF_BUTTON_STOP:
                this.transport.stop ();
                return true;

            case MOXFHardware.MOXF_BUTTON_REC:
                this.transport.record ();
                return true;

            case MOXFHardware.MOXF_BUTTON_FWD:
                this.transport.fastForward ();
                return true;

            case MOXFHardware.MOXF_BUTTON_BACK:
                this.transport.rewind ();
                return true;

            case MOXFHardware.MOXF_BUTTON_LOCATE:
                this.transport.tapTempo ();
                return true;

            default:
                return false;
        }
    }


    /**
     * Update the LED states on the device (only as an example but not working with the MOXF).
     */
    public void updateLEDs ()
    {
        this.hardware.updateLED (MOXFHardware.MOXF_BUTTON_PLAY, this.transport.isPlaying ().get ());
        this.hardware.updateLED (MOXFHardware.MOXF_BUTTON_REC, this.transport.isArrangerRecordEnabled ().get ());
    }
}