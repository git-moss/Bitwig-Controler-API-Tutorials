package com.mossgraber;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class API20TestExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("0213ac5d-112a-46eb-a6b7-930ca30c25e6");
   
   public API20TestExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "API20Test";
   }
   
   @Override
   public String getAuthor()
   {
      return "moss";
   }

   @Override
   public String getVersion()
   {
      return "0.1";
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "moss";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "API20Test";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 20;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 0;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 0;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
   }

   @Override
   public API20TestExtension createInstance(final ControllerHost host)
   {
      return new API20TestExtension(this, host);
   }
}
