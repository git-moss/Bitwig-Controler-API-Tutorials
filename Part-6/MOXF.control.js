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

loadAPI(7);
load ("MOXFHardware.js");
load ("TransportHandler.js");
load ("TrackHandler.js");

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Yamaha", "MOXF", "0.1", "bdf1fb54-4642-41d0-9851-460bc63fcf4e", "Jürgen Moßgraber");
host.defineMidiPorts(1, 1);
host.addDeviceNameBasedDiscoveryPair(["MIDIIN4 (mio10)"], ["MIDIOUT4 (mio10)"]);

var hardware = null;
var transportHandler = null;
var trackHandler = null;


function init ()
{
   hardware = new MOXFHardware (host.getMidiOutPort (0), host.getMidiInPort (0), handleMidi);
   transportHandler = new TransportHandler (host.createTransport ());
   trackHandler = new TrackHandler (host.createMainTrackBank (4, 0, 0), host.createCursorTrack ("MOXF_CURSOR_TRACK", "Cursor Track", 0, 0, true));

   println ("MOXF initialized!");
}

function handleMidi (status, data1, data2)
{
   if (transportHandler.handleMidi (status, data1, data2))
      return;

   if (trackHandler.handleMidi (status, data1, data2))
      return;

   host.errorln ("Midi command not processed: " + status + " : " + data1);
}

function flush ()
{
   transportHandler.updateLEDs ();
}

function exit ()
{
   println ("Exited!");
}