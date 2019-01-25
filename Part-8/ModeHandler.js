// Mode Interface:
//      - String getName ()
//      - void setIndication (boolean enable)
//      - void handleMidi (byte status, byte data1, byte data2)

function ModeHandler (modes, modeSetting)
{
    this.modes = modes;
    this.modeSetting = modeSetting;

    this.setActiveMode (modes[0]);

    this.modeSetting.addValueObserver (doObject (this, ModeHandler.prototype.modeSettingCallback));
}

ModeHandler.prototype.modeSettingCallback = function (value)
{
    var i;
    for (i = 0; i < MODE_OPTIONS.length; i++)
    {
        if (MODE_OPTIONS[i] == value)
            this.setActiveMode (this.modes[i]);
    }
}

ModeHandler.prototype.setActiveMode = function (newMode)
{
    this.activeMode = newMode;
    host.showPopupNotification (this.activeMode.getName ());
    this.updateIndication ();
}

ModeHandler.prototype.updateIndication = function ()
{
    var i;
    for (i = 0; i < this.modes.length; i++)
        this.modes[i].setIndication (false);
    this.activeMode.setIndication (true);
}

ModeHandler.prototype.handleMidi = function (status, data1, data2)
{
    if (isNoteOn(status))
    {
        switch (data1)
        {
            case MOXF_BUTTON_A:
                this.setActiveMode (this.modes[0]);
                this.modeSetting.set (MODE_OPTIONS[0]);
                return true;

            case MOXF_BUTTON_B:
                this.setActiveMode (this.modes[1]);
                this.modeSetting.set (MODE_OPTIONS[1]);
                return true;
        }
    }

    if (this.activeMode.handleMidi (status, data1, data2))
        return true;

    return false;    
}
