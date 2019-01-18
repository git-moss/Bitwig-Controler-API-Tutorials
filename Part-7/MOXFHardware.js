// Buttons
const MOXF_BUTTON_SOLO   = 0x08;
const MOXF_BUTTON_MUTE   = 0x10;

const MOXF_BUTTON_F1     = 0x36;
const MOXF_BUTTON_F2     = 0x37;
const MOXF_BUTTON_F3     = 0x38;
const MOXF_BUTTON_F4     = 0x39;
const MOXF_BUTTON_F5     = 0x3A;
const MOXF_BUTTON_F6     = 0x3B;

const MOXF_BUTTON_SF1    = 0x3C;
const MOXF_BUTTON_SF2    = 0x3D;
const MOXF_BUTTON_SF3    = 0x3E;
const MOXF_BUTTON_SF4    = 0x3F;
const MOXF_BUTTON_SF5    = 0x40;
const MOXF_BUTTON_SF6    = 0x41;

const MOXF_BUTTON_A      = 0x42;
const MOXF_BUTTON_B      = 0x43;
const MOXF_BUTTON_C      = 0x44;
const MOXF_BUTTON_D      = 0x45;
const MOXF_BUTTON_E      = 0x46;
const MOXF_BUTTON_F      = 0x47;

const MOXF_BUTTON_PLAY   = 0x5E;
const MOXF_BUTTON_STOP   = 0x5D;
const MOXF_BUTTON_REC    = 0x5F;
const MOXF_BUTTON_FWD    = 0x5C;
const MOXF_BUTTON_BACK   = 0x5B;
const MOXF_BUTTON_LOCATE = 0x58;

const MOXF_BUTTON_CATEGORY = 0x76;
const MOXF_BUTTON_FAVORITE = 0x77;

// Knobs
const MOXF_KNOB_MAIN = 0x3C;
const MOXF_KNOB_1    = 0x4A;
const MOXF_KNOB_2    = 0x47;
const MOXF_KNOB_3    = 0x49;
const MOXF_KNOB_4    = 0x48;
const MOXF_KNOB_5    = 0x1C;
const MOXF_KNOB_6    = 0x1D;
const MOXF_KNOB_7    = 0x1E;
const MOXF_KNOB_8    = 0x1F;


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
    if (this.ledCache[note] == value)
        return;
    this.ledCache[note] = value;
    this.portOut.sendMidi (0x90, note, value);
}
