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