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