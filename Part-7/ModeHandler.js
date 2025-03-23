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

// Mode Interface:
//      - String getName ()
//      - void setIndication (boolean enable)
//      - void handleMidi (byte status, byte data1, byte data2)

function ModeHandler (modes)
{
    this.modes = modes;
    this.setActiveMode (modes[0]);
}

ModeHandler.prototype.setActiveMode = function (newMode)
{
    this.activeMode = newMode;
    host.showPopupNotification (this.activeMode.getName ());
    this.updateIndication ();
}

ModeHandler.prototype.updateIndication = function ()
{
    var i;
    for (i = 0; i < this.modes.length; i++)
        this.modes[i].setIndication (false);
    this.activeMode.setIndication (true);
}

ModeHandler.prototype.handleMidi = function (status, data1, data2)
{
    if (isNoteOn(status))
    {
        switch (data1)
        {
            case MOXF_BUTTON_A:
                this.setActiveMode (this.modes[0]);
                return true;

            case MOXF_BUTTON_B:
                this.setActiveMode (this.modes[1]);
                return true;
        }
    }

    if (this.activeMode.handleMidi (status, data1, data2))
        return true;

    return false;    
}
