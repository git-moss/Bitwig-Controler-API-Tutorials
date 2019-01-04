const MOXF_BUTTON_PLAY   = 0x5E;
const MOXF_BUTTON_STOP   = 0x5D;
const MOXF_BUTTON_REC    = 0x5F;
const MOXF_BUTTON_FWD    = 0x5C;
const MOXF_BUTTON_BACK   = 0x5B;
const MOXF_BUTTON_LOCATE = 0x58;


function MOXFHardware (outputPort, inputPort, inputCallback)
{
    this.portOut = outputPort;
    this.portIn  = inputPort;

    this.ledCache = initArray (-1, 128);

    this.portIn.setMidiCallback (inputCallback);
    this.noteIn = this.portIn.createNoteInput ("Yamaha MOXF", "91????", "81????", "B101??", "B140??", "E1????");

    // --- Example for inverting the keyboard
    //
    // var noteMap = initArray (-1, 128);
    // for (i = 0; i < 128; i++)
    //    noteMap[i] = 127 - i;
    // this.noteIn.setKeyTranslationTable (noteMap);
}

MOXFHardware.prototype.updateLED = function (note, isOn)
{
    var value = isOn ? 127 : 0;
    if (this.ledCache[note] != value)
    {
        this.ledCache[note] = value;
        this.portOut.sendMidi (0x90, note, value);
        println ("Updated to " + this.ledCache[note]);
    }
    else
        println ("Not updated.");
}
