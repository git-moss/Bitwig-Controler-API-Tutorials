loadAPI(10);

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Moss", "Hardware API Test 1", "1.0", "68e7b117-0999-496a-b5a0-16f533fc5da0", "Moss");
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
   

   // 2. Bind the controls to MIDI commands

   var port = host.getMidiInPort(0);
   playButton.pressedAction ().setActionMatcher (port.createCCActionMatcher (channel, buttonControl, 127));
   slider.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (channel, sliderControl));
   absKnob.setAdjustValueMatcher (port.createAbsoluteCCValueMatcher (channel, absKnobControl));
   relKnob.setAdjustValueMatcher (port.createRelativeBinOffsetCCValueMatcher (channel, relKnobControl, 127));


   // 3. Bind the controls to actions / targets

   var transport = host.createTransport ();
   playButton.pressedAction ().setBinding (transport.playAction ());

   var trackBank = host.createMainTrackBank (3, 0, 0);
   slider.setBinding (trackBank.getItemAt (0).volume ());
   absKnob.setBinding (trackBank.getItemAt (1).volume ());
   relKnob.setBinding (trackBank.getItemAt (2).volume ());

   println("BindingTest initialized!");
}

function flush()
{
   // Empty
}

function exit()
{
   println("BindingTest exited!");
}