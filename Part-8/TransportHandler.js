function TransportHandler (transport)
{
    this.transport = transport;

    // --- Examplefor using value observers
    // transport.isPlaying ().addValueObserver (function (value)
    // {
    //    println (value ? "Playing..." : "Stopped.");
    // });
 
    this.transport.isPlaying ().markInterested ();
    this.transport.isArrangerRecordEnabled ().markInterested ();
}

TransportHandler.prototype.handleMidi = function (status, data1, data2)
{
    if (!isNoteOn(status))
        return false;

    if (data2 == 0)
        return true;

    switch (data1)
    {
        case MOXF_BUTTON_PLAY:
            this.transport.play ();
            return true;

        case MOXF_BUTTON_STOP:
            this.transport.stop ();
            return true;

        case MOXF_BUTTON_REC:
            this.transport.record ();
            return true;

        case MOXF_BUTTON_FWD:
            this.transport.fastForward ();
            return true;

        case MOXF_BUTTON_BACK:
            this.transport.rewind ();
            return true;

        case MOXF_BUTTON_LOCATE:
            this.transport.tapTempo ();
            return true;

        default:
            return false;
    }
}

TransportHandler.prototype.updateLEDs = function ()
{
    hardware.updateLED (MOXF_BUTTON_PLAY, this.transport.isPlaying ().get ());
    hardware.updateLED (MOXF_BUTTON_REC, this.transport.isArrangerRecordEnabled ().get ());
}