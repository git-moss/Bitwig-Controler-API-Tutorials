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

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Yamaha", "MOXF", "0.1", "bdf1fb54-4642-41d0-9851-460bc63fcf4e", "Jürgen Moßgraber");
host.defineMidiPorts(1, 1);
host.addDeviceNameBasedDiscoveryPair(["MIDIIN4 (mio10)"], ["MIDIOUT4 (mio10)"]);

var hardware = null;
var transport = null;


function init ()
{
   hardware = new MOXFHardware (host.getMidiOutPort (0), host.getMidiInPort (0), onMidi0);

   transport = host.createTransport ();

   transport.isPlaying ().addValueObserver (function (value)
   {
      println (value ? "Playing..." : "Stopped.");
   });

   transport.isPlaying ().markInterested ();

   println ("MOXF initialized!");
}

function onMidi0(status, data1, data2)
{
   if (isNoteOn(status) && data2 == 127)
   {
      switch (data1)
      {
         case MOXF_BUTTON_PLAY:
            transport.play ();
            break;

         case MOXF_BUTTON_STOP:
            transport.stop ();
            break;

         case MOXF_BUTTON_REC:
            transport.record ();
            break;

         case MOXF_BUTTON_FWD:
            transport.fastForward ();
            break;

         case MOXF_BUTTON_BACK:
            transport.rewind ();
            break;

         case MOXF_BUTTON_LOCATE:
            transport.tapTempo ();
            break;

         default:
            host.errorln ("Command " + data1 + " is not supported.");
            break;
      }
   }
}

function flush()
{
   var isPlaying = transport.isPlaying ().get ();
   hardware.updateLED (MOXF_BUTTON_PLAY, isPlaying);
}

function exit()
{
   println ("Exited!");
}