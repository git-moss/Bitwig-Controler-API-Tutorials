package de.mossggraber.apitwelve;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class API12TestExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("e9a88dc4-25d3-4b3e-ac96-41cad164c3c2");
   
   public API12TestExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "API12-Test";
   }
   
   @Override
   public String getAuthor()
   {
      return "moss";
   }

   @Override
   public String getVersion()
   {
      return "1.0";
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
      return "API12-Test";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 12;
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
   public API12TestExtension createInstance(final ControllerHost host)
   {
      return new API12TestExtension(this, host);
   }
}
