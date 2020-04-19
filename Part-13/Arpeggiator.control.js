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