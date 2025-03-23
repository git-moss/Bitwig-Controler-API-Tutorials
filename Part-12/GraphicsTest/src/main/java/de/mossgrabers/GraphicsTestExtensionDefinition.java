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

package de.mossgrabers;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

import java.util.UUID;


public class GraphicsTestExtensionDefinition extends ControllerExtensionDefinition
{
    private static final UUID DRIVER_ID = UUID.fromString ("b17f9f0b-1041-45e9-a391-d28609254de4");


    /**
     * Constructor.
     */
    public GraphicsTestExtensionDefinition ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public boolean isUsingBetaAPI ()
    {
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public String getName ()
    {
        return "GraphicsTest";
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
        return "MOSS";
    }


    /** {@inheritDoc} */
    @Override
    public String getHardwareModel ()
    {
        return "GraphicsTest";
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
        return 0;
    }


    /** {@inheritDoc} */
    @Override
    public int getNumMidiOutPorts ()
    {
        return 0;
    }


    /** {@inheritDoc} */
    @Override
    public void listAutoDetectionMidiPortNames (final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
    {
        // Intentionall empty
    }


    /** {@inheritDoc} */
    @Override
    public GraphicsTestExtension createInstance (final ControllerHost host)
    {
        return new GraphicsTestExtension (this, host);
    }
}
