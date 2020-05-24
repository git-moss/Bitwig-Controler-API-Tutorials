package com.moss;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

import java.util.UUID;


/**
 * Demo code to explain the Bitwig Hardware API. The definition class.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class HardwareAPITest4ExtensionDefinition extends ControllerExtensionDefinition
{
    private static final UUID DRIVER_ID = UUID.fromString ("456e8ab6-ea9a-4cf5-92c9-b5131d68f4be");


    /**
     * Constructor
     */
    public HardwareAPITest4ExtensionDefinition ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public String getName ()
    {
        return "HardwareAPITest4";
    }


    /** {@inheritDoc} */
    @Override
    public String getAuthor ()
    {
        return "moss";
    }


    /** {@inheritDoc} */
    @Override
    public String getVersion ()
    {
        return "1.0";
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
        return "moss";
    }


    /** {@inheritDoc} */
    @Override
    public String getHardwareModel ()
    {
        return "HardwareAPITest4";
    }


    /** {@inheritDoc} */
    @Override
    public int getRequiredAPIVersion ()
    {
        return 11;
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
        // Not used
    }


    /** {@inheritDoc} */
    @Override
    public HardwareAPITest4Extension createInstance (final ControllerHost host)
    {
        return new HardwareAPITest4Extension (this, host);
    }
}
