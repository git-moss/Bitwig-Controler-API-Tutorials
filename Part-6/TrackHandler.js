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
