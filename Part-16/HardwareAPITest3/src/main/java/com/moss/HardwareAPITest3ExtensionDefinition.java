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
public class HardwareAPITest3ExtensionDefinition extends ControllerExtensionDefinition
{
    private static final UUID DRIVER_ID = UUID.fromString ("a4d5c3b8-29f5-4851-b725-f93fe91e7764");


    /**
     * Constructor
     */
    public HardwareAPITest3ExtensionDefinition ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public String getName ()
    {
        return "HardwareAPITest3";
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
        return "HardwareAPITest3";
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
    public HardwareAPITest3Extension createInstance (final ControllerHost host)
    {
        return new HardwareAPITest3Extension (this, host);
    }
}
