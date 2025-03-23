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
load ("RemoteControlHandler.js");
load ("ModeHandler.js");

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Yamaha", "MOXF", "0.1", "bdf1fb54-4642-41d0-9851-460bc63fcf4e", "Jürgen Moßgraber");
host.defineMidiPorts(1, 1);
host.addDeviceNameBasedDiscoveryPair(["MIDIIN4 (mio10)"], ["MIDIOUT4 (mio10)"]);

var hardware = null;
var transportHandler = null;
var modeHandler = null;

var MODE_OPTIONS = [ "Track", "Device" ];
var BOOLEAN_OPTIONS = [ "Off", "On" ];

function doObject (object, f)
{
    return function ()
    {
        f.apply (object, arguments);
    };
}


function init ()
{
   // Preferences
   var preferences = host.getPreferences ();
   var modeSetting = preferences.getEnumSetting ("Mode", "Global", MODE_OPTIONS, MODE_OPTIONS[0]);

   // Document States
   var documentState = host.getDocumentState ();
   var fixVelocityEnableSetting = documentState.getEnumSetting ("Enable", "Fix velocity", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS[0]);
   var fixVelocityValueSetting = documentState.getNumberSetting ("Velocity", "Fix velocity", 0, 127, 1, "", 127);

   hardware = new MOXFHardware (host.getMidiOutPort (0), host.getMidiInPort (0), handleMidi, fixVelocityEnableSetting, fixVelocityValueSetting);
   transportHandler = new TransportHandler (host.createTransport ());

   var cursorTrack = host.createCursorTrack ("MOXF_CURSOR_TRACK", "Cursor Track", 0, 0, true);
   var trackHandler = new TrackHandler (host.createMainTrackBank (4, 0, 0), cursorTrack);
   
   var cursorDevice = cursorTrack.createCursorDevice ("MOXF_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
   var remoteControlHandler = new RemoteControlHandler (cursorDevice, cursorDevice.createCursorRemoteControlsPage (8));

   var modes = [ trackHandler, remoteControlHandler ];
   modeHandler = new ModeHandler (modes, modeSetting);

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