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

loadAPI(10);

host.defineController("moss", "Arpeggiator", "0.1", "cce26ee9-30b8-4fa1-aee7-41d52193b9d9", "moss");
host.defineMidiPorts(1, 0);

var arpeggiator = null;
var noteLatch =  null;


function init()
{
   // MidiIn
   var midiIn = host.getMidiInPort(0);

   // NoteInput
   var noteInput = midiIn.createNoteInput ("Arpeggiator Test");

   // Arpeggiator
   arpeggiator = noteInput.arpeggiator ();

   // NoteLatch
   noteLatch =  noteInput.noteLatch ();

   // SettableBooleanValue
   arpeggiator.isEnabled ().addValueObserver (function (value) {
      println ("Arpeggiator is: " + (value ? "On" : "Off" ));
   })

   // SettableBooleanValue
   noteLatch.isEnabled ().addValueObserver (function (value) {
      println ("Note Latch is: " + (value ? "On" : "Off" ));
   })

   println("Arpeggiator initialized!");
}

function toggleArpeggiator ()
{
   arpeggiator.isEnabled ().toggle ();
}

function toggleNoteLatch ()
{
   noteLatch.isEnabled ().toggle ();
}


function flush()
{
}

function exit()
{
}