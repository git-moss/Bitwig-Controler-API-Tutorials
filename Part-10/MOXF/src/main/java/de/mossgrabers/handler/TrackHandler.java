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
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Track;
import com.bitwig.extension.controller.api.TrackBank;


/**
 * The track control mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class TrackHandler implements Mode
{
    private final TrackBank   trackbank;
    private final CursorTrack cursorTrack;


    /**
     * Constructor.
     *
     * @param trackbank The trackbank
     * @param cursorTrack The cursor track
     */
    public TrackHandler (final TrackBank trackbank, final CursorTrack cursorTrack)
    {
        this.trackbank = trackbank;
        this.cursorTrack = cursorTrack;

        this.trackbank.followCursorTrack (this.cursorTrack);

        for (int i = 0; i < this.trackbank.getSizeOfBank (); i++)
        {
            final Track track = this.trackbank.getItemAt (i);
            track.pan ().markInterested ();
            track.volume ().markInterested ();
        }

        this.cursorTrack.solo ().markInterested ();
        this.cursorTrack.mute ().markInterested ();
    }


    /** {@inheritDoc} */
    @Override
    public String getName ()
    {
        return "Track Mode";
    }


    /** {@inheritDoc} */
    @Override
    public void setIndication (final boolean enable)
    {
        for (int i = 0; i < this.trackbank.getSizeOfBank (); i++)
        {
            final Track track = this.trackbank.getItemAt (i);
            track.pan ().setIndication (enable);
            track.volume ().setIndication (enable);
        }
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
                    this.trackbank.getItemAt (0).selectInMixer ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF2:
                    this.trackbank.getItemAt (1).selectInMixer ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF3:
                    this.trackbank.getItemAt (2).selectInMixer ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF4:
                    this.trackbank.getItemAt (3).selectInMixer ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF5:
                    this.trackbank.scrollPageBackwards ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SF6:
                    this.trackbank.scrollPageForwards ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_SOLO:
                    this.cursorTrack.solo ().toggle ();
                    return true;

                case MOXFHardware.MOXF_BUTTON_MUTE:
                    this.cursorTrack.mute ().toggle ();
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
                    this.trackbank.getItemAt (0).pan ().set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_2:
                    this.trackbank.getItemAt (1).pan ().set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_3:
                    this.trackbank.getItemAt (2).pan ().set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_4:
                    this.trackbank.getItemAt (3).pan ().set (Integer.valueOf (data2), Integer.valueOf (128));
                    return true;

                // Relative values
                case MOXFHardware.MOXF_KNOB_5:
                    this.trackbank.getItemAt (0).volume ().inc (value, Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_6:
                    this.trackbank.getItemAt (1).volume ().inc (value, Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_7:
                    this.trackbank.getItemAt (2).volume ().inc (value, Integer.valueOf (128));
                    return true;

                case MOXFHardware.MOXF_KNOB_8:
                    this.trackbank.getItemAt (3).volume ().inc (value, Integer.valueOf (128));
                    return true;

                default:
                    return false;
            }
        }

        return false;
    }
}