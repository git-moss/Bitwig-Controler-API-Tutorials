loadAPI(7);

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Yamaha", "MOXF", "0.1", "bdf1fb54-4642-41d0-9851-460bc63fcf4e", "Jürgen Moßgraber");
host.defineMidiPorts(1, 1);
host.addDeviceNameBasedDiscoveryPair(["MIDIIN4 (mio10)"], ["MIDIOUT4 (mio10)"]);


function init()
{
   var inputPort = host.getMidiInPort(0);
   inputPort.setMidiCallback(onMidi0);

   println("MOXF initialized!");
}

function onMidi0(status, data1, data2)
{
    printMidi(status, data1, data2);
}

function flush()
{
   // println("Flush called.");
}

function exit()
{
   println("Exited!");
}