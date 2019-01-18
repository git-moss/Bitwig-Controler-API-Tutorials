function RemoteControlHandler (cursorDevice, remoteControlsBank)
{
    this.cursorDevice = cursorDevice;
    this.remoteControlsBank = remoteControlsBank;

    var i;
    for (i = 0; i < this.remoteControlsBank.getParameterCount (); i++)
        this.remoteControlsBank.getParameter (i).markInterested ();

    this.cursorDevice.isEnabled ().markInterested ();
    this.cursorDevice.isWindowOpen ().markInterested ();
}

RemoteControlHandler.prototype.getName = function ()
{
    return "Device Mode";
}

RemoteControlHandler.prototype.setIndication = function (enable)
{
    var i;
    for (i = 0; i < this.remoteControlsBank.getParameterCount (); i++)
        this.remoteControlsBank.getParameter (i).setIndication (enable);
}

RemoteControlHandler.prototype.handleMidi = function (status, data1, data2)
{
    if (isNoteOn(status))
    {
        switch (data1)
        {
            case MOXF_BUTTON_SF1:
                this.cursorDevice.selectPrevious ();
                return true;

            case MOXF_BUTTON_SF2:
                this.cursorDevice.selectNext ();
                return true;

            case MOXF_BUTTON_SF3:
                // Not used
                return true;

            case MOXF_BUTTON_SF4:
                // Not used
                return true;

            case MOXF_BUTTON_SF5:
                this.remoteControlsBank.selectPrevious ();
                return true;

            case MOXF_BUTTON_SF6:
                this.remoteControlsBank.selectNext ();
                return true;

            case MOXF_BUTTON_SOLO:
                this.cursorDevice.isWindowOpen ().toggle ();
                return true;

            case MOXF_BUTTON_MUTE:
                this.cursorDevice.isEnabled ().toggle ();
                return true;

            default:
                return false;
        }
    }

    if (isChannelController(status))
    {
        switch (data1)
        {
            // Absolute values
            case MOXF_KNOB_1:
                this.remoteControlsBank.getParameter (0).set (data2, 128);
                return true;

            case MOXF_KNOB_2:
                this.remoteControlsBank.getParameter (1).set (data2, 128);
                return true;

            case MOXF_KNOB_3:
                this.remoteControlsBank.getParameter (2).set (data2, 128);
                return true;

            case MOXF_KNOB_4:
                this.remoteControlsBank.getParameter (3).set (data2, 128);
                return true;

            // Relative values
            case MOXF_KNOB_5:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.remoteControlsBank.getParameter (4).inc (value, 128);
                return true;

            case MOXF_KNOB_6:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.remoteControlsBank.getParameter (5).inc (value, 128);
                return true;

            case MOXF_KNOB_7:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.remoteControlsBank.getParameter (6).inc (value, 128);
                return true;

            case MOXF_KNOB_8:
                var value = data2 > 64 ? 64 - data2 : data2;
                this.remoteControlsBank.getParameter (7).inc (value, 128);
                return true;

            default:
                return false;
        }
    }

    return false;    
}
