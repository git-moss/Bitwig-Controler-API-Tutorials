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

package de.mossgrabers.remote;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;


public class RemoteCursorTestExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString ("5a418561-6058-412f-aa7f-b9d8e318269d");
   
   public RemoteCursorTestExtensionDefinition ()
   {
   }

   @Override
   public String getName ()
   {
      return "RemoteCursorTest";
   }
   
   @Override
   public String getAuthor ()
   {
      return "moss";
   }

   @Override
   public String getVersion ()
   {
      return "0.1";
   }

   @Override
   public UUID getId ()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor ()
   {
      return "moss";
   }
   
   @Override
   public String getHardwareModel ()
   {
      return "RemoteCursorTest";
   }

   @Override
   public int getRequiredAPIVersion ()
   {
      return 20;
   }

   @Override
   public int getNumMidiInPorts ()
   {
      return 0;
   }

   @Override
   public int getNumMidiOutPorts ()
   {
      return 0;
   }

   @Override
   public void listAutoDetectionMidiPortNames (final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
   }

   @Override
   public RemoteCursorTestExtension createInstance (final ControllerHost host)
   {
      return new RemoteCursorTestExtension (this, host);
   }
}
