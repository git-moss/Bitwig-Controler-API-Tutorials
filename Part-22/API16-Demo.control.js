// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2022
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

loadAPI(16);

host.setShouldFailOnDeprecatedUse(true);
host.defineController("moss", "API16-Demo", "0.1", "85661779-961a-45ab-8312-130f7c81ce63", "moss");

var cursorTrack;
var slotBank;
var cursorDevice;
var controlsPage;
var parameter;

function init ()
{
   cursorTrack = host.createCursorTrack ("APITEST_CURSOR_TRACK", "Cursor Track", 0, 8, true);
   slotBank = cursorTrack.clipLauncherSlotBank ();

   cursorDevice = cursorTrack.createCursorDevice ("APITEST_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
   controlsPage = cursorDevice.createCursorRemoteControlsPage (8);
   parameter = controlsPage.getParameter (0);

   println ("API16-Demo initialized.");
}

function flush ()
{
   // Not used
}

function exit ()
{
   // Not used
}

function launchWithOptions (slotIndex)
{
   var slot = slotBank.getItemAt (slotIndex);
   slot.launchWithOptions ("1", "play_with_quantization");
}

function launchLastClipWithOptions ()
{
   cursorTrack.launchLastClipWithOptions ("none", "continue_immediately");
}

function createPresetPage ()
{
   controlsPage.createPresetPage ();
}

function activateMapping ()
{
   parameter.isBeingMapped ().set (true);
}

function removeMapping ()
{
   parameter.deleteObject ();
}