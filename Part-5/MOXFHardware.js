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

const MOXF_BUTTON_PLAY   = 0x5E;
const MOXF_BUTTON_STOP   = 0x5D;
const MOXF_BUTTON_REC    = 0x5F;
const MOXF_BUTTON_FWD    = 0x5C;
const MOXF_BUTTON_BACK   = 0x5B;
const MOXF_BUTTON_LOCATE = 0x58;


function MOXFHardware (outputPort, inputPort, inputCallback)
{
    this.portOut = outputPort;
    this.portIn  = inputPort;

    this.ledCache = initArray (-1, 128);

    this.portIn.setMidiCallback (inputCallback);
    this.noteIn = this.portIn.createNoteInput ("Yamaha MOXF", "91????", "81????", "B101??", "B140??", "E1????");

    // --- Example for inverting the keyboard
    //
    // var noteMap = initArray (-1, 128);
    // for (i = 0; i < 128; i++)
    //    noteMap[i] = 127 - i;
    // this.noteIn.setKeyTranslationTable (noteMap);
}

MOXFHardware.prototype.updateLED = function (note, isOn)
{
    var value = isOn ? 127 : 0;
    if (this.ledCache[note] != value)
    {
        this.ledCache[note] = value;
        this.portOut.sendMidi (0x90, note, value);
        println ("Updated to " + this.ledCache[note]);
    }
    else
        println ("Not updated.");
}
