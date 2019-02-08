// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.handler;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;


/**
 * A mode allows to use the same controls on a device for different tasks depending on the active
 * mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public interface Mode
{
    /**
     * Get a descriptive name for the mode.
     *
     * @return The name
     */
    String getName ();


    /**
     * Set the indication for the controls in Bitwig.
     *
     * @param enable True to enable otherwise disable
     */
    void setIndication (boolean enable);


    /**
     * Handle/react to the midi commands of the buttons and knobs of the device.
     *
     * @param message The midi message
     * @return True if the midi message was processed in the method
     */
    boolean handleMidi (ShortMidiMessage message);
}
