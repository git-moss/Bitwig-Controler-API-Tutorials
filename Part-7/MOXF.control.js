loadAPI(7);
load ("MOXFHardware.js");
load ("TransportHandler.js");
load ("TrackHandler.js");
load ("RemoteControlHandler.js");
load ("ModeHandler.js");

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Yamaha", "MOXF", "0.1", "bdf1fb54-4642-41d0-9851-460bc63fcf4e", "Jürgen Moßgraber");
host.defineMidiPorts(1, 1);
host.addDeviceNameBasedDiscoveryPair(["MIDIIN4 (mio10)"], ["MIDIOUT4 (mio10)"]);

var hardware = null;
var transportHandler = null;
var modeHandler = null;


function init ()
{
   hardware = new MOXFHardware (host.getMidiOutPort (0), host.getMidiInPort (0), handleMidi);
   transportHandler = new TransportHandler (host.createTransport ());

   var cursorTrack = host.createCursorTrack ("MOXF_CURSOR_TRACK", "Cursor Track", 0, 0, true);
   var trackHandler = new TrackHandler (host.createMainTrackBank (4, 0, 0), cursorTrack);
   
   var cursorDevice = cursorTrack.createCursorDevice ("MOXF_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
   var remoteControlHandler = new RemoteControlHandler (cursorDevice, cursorDevice.createCursorRemoteControlsPage (8));

   var modes = [ trackHandler, remoteControlHandler ];
   modeHandler = new ModeHandler (modes);

   println ("MOXF initialized!");
}

function handleMidi (status, data1, data2)
{
   if (transportHandler.handleMidi (status, data1, data2))
      return;

   if (modeHandler.handleMidi (status, data1, data2))
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