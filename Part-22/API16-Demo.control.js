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