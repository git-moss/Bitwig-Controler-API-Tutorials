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

function TrackHandler (trackbank, cursorTrack)
{
    this.trackbank = trackbank;
    this.cursorTrack = cursorTrack;

    for (i = 0; i < this.trackbank.getSizeOfBank (); i++)
    {
        var track = this.trackbank.getItemAt (i);

        var p = track.pan ();
        p.markInterested ();
        p.setIndication (true);

        p = track.volume ();
        p.markInterested ();
        p.setIndication (true);
    }

    this.trackbank.followCursorTrack (this.cursorTrack);

    this.cursorTrack.solo ().markInterested ();
    this.cursorTrack.mute ().markInterested ();
}

TrackHandler.prototype.handleMidi = function (status, data1, data2)
{
    if (isNoteOn(status))
    {
        switch (data1)
        {
            case MOXF_BUTTON_SF1:
                this.trackbank.getItemAt (0).select ();
                return true;

            case MOXF_BUTTON_SF2:
                this.trackbank.getItemAt (1).select ();
                return true;

            case MOXF_BUTTON_SF3:
                this.trackbank.getItemAt (2).select ();
                return true;

            case MOXF_BUTTON_SF4:
                this.trackbank.getItemAt (3).select ();
                return true;

            case MOXF_BUTTON_SF5:
                this.trackbank.scrollPageBackwards ();
                return true;

            case MOXF_BUTTON_SF6:
                this.trackbank.scrollPageForwards ();
                return true;

            case MOXF_BUTTON_SOLO:
                this.cursorTrack.solo ().toggle ();
                return true;

            case MOXF_BUTTON_MUTE:
                this.cursorTrack.mute ().toggle ();
                return true;

            default:
                return false;
        }
    }

    if (isChannelController(status))
    {
        switch (data1)
        {
            // Absolute values
            case MOXF_KNOB_1:
                this.trackbank.getItemAt (0).pan ().set (data2, 128);
                return true;

            case MOXF_KNOB_2:
                this.trackbank.getItemAt (1).pan ().set (data2, 128);
                return true;

            case MOXF_KNOB_3:
                this.trackbank.getItemAt (2).pan ().set (data2, 128);
                return true;

            case MOXF_KNOB_4:
                this.trackbank.getItemAt (3).pan ().set (data2, 128);
                return true;

            // Relative values
            case MOXF_KNOB_5:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.trackbank.getItemAt (0).volume ().inc (value, 128);
                return true;

            case MOXF_KNOB_6:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.trackbank.getItemAt (1).volume ().inc (value, 128);
                return true;

            case MOXF_KNOB_7:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.trackbank.getItemAt (2).volume ().inc (value, 128);
                return true;

            case MOXF_KNOB_8:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.trackbank.getItemAt (3).volume ().inc (value, 128);
                return true;

            default:
                return false;
        }
    }

    return false;    
}
