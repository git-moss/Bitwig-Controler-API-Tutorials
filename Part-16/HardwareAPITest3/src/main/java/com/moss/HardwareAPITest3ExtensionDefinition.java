// MIT License
// 
// Copyright (c) 2019-2025 Jürgen Moßgraber
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

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
