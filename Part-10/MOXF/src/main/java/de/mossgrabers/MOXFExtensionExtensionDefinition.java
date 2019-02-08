// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

import java.util.UUID;


/**
 * The description of the extension.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class MOXFExtensionExtensionDefinition extends ControllerExtensionDefinition
{
    private static final UUID DRIVER_ID = UUID.fromString ("2f4727a3-9e7f-4908-8c59-6c2759e577c0");


    /**
     * Constructor.
     */
    public MOXFExtensionExtensionDefinition ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public String getName ()
    {
        return "MOXF";
    }


    /** {@inheritDoc} */
    @Override
    public String getAuthor ()
    {
        return "Jürgen Moßgraber";
    }


    /** {@inheritDoc} */
    @Override
    public String getVersion ()
    {
        return "0.1";
    }


    /** {@inheritDoc} */
    @Override
    public UUID getId ()
    {
        return DRIVER_ID;
    }


    /** {@inheritDoc} */
    @Override
    public String getHardwareVendor ()
    {
        return "Yamaha";
    }


    /** {@inheritDoc} */
    @Override
    public String getHardwareModel ()
    {
        return "MOXF (Java)";
    }


    /** {@inheritDoc} */
    @Override
    public int getRequiredAPIVersion ()
    {
        return 7;
    }


    /** {@inheritDoc} */
    @Override
    public int getNumMidiInPorts ()
    {
        return 1;
    }


    /** {@inheritDoc} */
    @Override
    public int getNumMidiOutPorts ()
    {
        return 1;
    }


    /** {@inheritDoc} */
    @Override
    public void listAutoDetectionMidiPortNames (final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
    {
        // Note: Only an example on my system. If you use the MOXF with USB replace these with tzhe
        // correct names

        list.add (new String []
        {
            "MIDIIN4 (mio10)"
        }, new String []
        {
            "MIDIOUT4 (mio10)"
        });
    }


    /** {@inheritDoc} */
    @Override
    public MOXFExtensionExtension createInstance (final ControllerHost host)
    {
        return new MOXFExtensionExtension (this, host);
    }
}
