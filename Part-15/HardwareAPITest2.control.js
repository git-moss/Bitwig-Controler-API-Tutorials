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

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Moss", "Hardware API Test 2", "1.0", "98ff9719-450a-4a5c-86fb-48ff171127ae", "Moss");
host.defineMidiPorts(1, 0);

function init()
{
   var channel = 0;
   var buttonControl = 112;    // TODO -> EXCHANGE WITH YOUR CONTROL
   var sliderControl  = 14;    // TODO -> EXCHANGE WITH YOUR CONTROL
   var absKnobControl = 15;    // TODO -> EXCHANGE WITH YOUR CONTROL
   var relKnobControl = 16;    // TODO -> EXCHANGE WITH YOUR CONTROL

   // 1. Create the controls

   var hardwareSurface = host.createHardwareSurface ();
   // HardwareButton
   var playButton = hardwareSurface.createHardwareButton ("PLAY_BUTTON");
   // HardwareSlider
   var slider = hardwareSurface.createHardwareSlider ("SLIDER_1");
   // AbsoluteHardwareKnob
   var absKnob = hardwareSurface.createAbsoluteHardwareKnob ("ABSOLUTE_KNOB");
   // RelativeHardwareKnob
   var relKnob = hardwareSurface.createRelativeHardwareKnob ("RELATIVE_KNOB");

   // PianoKeyboard
   var keyboard = hardwareSurface.createPianoKeyboard ("THE_KEYBOARD",49, 0, 36);
   

   // 2. Bind the controls to MIDI commands

   var port = host.getMidiInPort(0);
   playButton.pressedAction ().setActionMatcher (port.createCCActionMatcher (channel, buttonControl, 127));
   slider.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (channel, sliderControl));
   absKnob.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (channel, absKnobControl));
   relKnob.setAdjustValueMatcher (port.createRelativeBinOffsetCCValueMatcher (channel, relKnobControl, 127));

   var noteInput = port.createNoteInput ("Hardware Test 2");
   keyboard.setMidiIn (port);


   // 3. Bind the controls to actions / targets

   var transport = host.createTransport ();
   playButton.pressedAction ().setBinding (transport.playAction ());

   var trackBank = host.createMainTrackBank (3, 0, 0);
   slider.setBinding (trackBank.getItemAt (0).volume ());
   absKnob.setBinding (trackBank.getItemAt (1).volume ());
   relKnob.setBinding (trackBank.getItemAt (2).volume ());


   // 4. Layout the simulator UI

   hardwareSurface.setPhysicalSize (200, 200);

   playButton.setBounds(137.0, 133.5, 8.75, 7.0);
   hardwareSurface.hardwareElementWithId("SLIDER_1").setBounds(106.0, 120.0, 10.0, 34.25);
   hardwareSurface.hardwareElementWithId("ABSOLUTE_KNOB").setBounds(127.25, 12.25, 10.0, 10.0);
   hardwareSurface.hardwareElementWithId("RELATIVE_KNOB").setBounds(127.25, 78.75, 10.0, 10.0);
   hardwareSurface.hardwareElementWithId("THE_KEYBOARD").setBounds(4.5, 163.5, 191.0, 28.25);

   println("Initialized!");
}

function flush()
{
   // Empty
}

function exit()
{
   println("Exited!");
}