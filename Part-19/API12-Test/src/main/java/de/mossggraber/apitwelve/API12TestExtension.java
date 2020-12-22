package de.mossggraber.apitwelve;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Device;
import com.bitwig.extension.controller.api.DeviceBank;
import com.bitwig.extension.controller.api.DeviceMatcher;
import com.bitwig.extension.controller.api.DocumentState;
import com.bitwig.extension.controller.api.InsertionPoint;
import com.bitwig.extension.controller.api.Parameter;
import com.bitwig.extension.controller.api.SpecificPluginDevice;

/**
 * Demo code to explain the Bitwig API 12.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class API12TestExtension extends ControllerExtension
{
	private static final String NEOVERB_ID = "5653545A4D52314E656F766572620000";
	
	
   protected API12TestExtension(final API12TestExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   
   /** {@inheritDoc} */
   @Override
   public void init()
   {
      final ControllerHost host = getHost();      

      ///////////////////////////////////////////////////////////////////////////////////////////////
      // #1 Create a VST3 matcher
      final CursorTrack cursorTrack = host.createCursorTrack(0, 0);
      final DeviceBank neoverbFilterDeviceBank = cursorTrack.createDeviceBank(1);
      
      final DeviceMatcher neoverbDeviceMatcher = host.createVST3DeviceMatcher 	(NEOVERB_ID);
      neoverbFilterDeviceBank.setDeviceMatcher(neoverbDeviceMatcher);
      
      final Device device = neoverbFilterDeviceBank.getItemAt(0);
      
      device.exists().addValueObserver(newValue -> {
    	  
    	  host.println("Neoverb found: " + (newValue ? "Yes" : "No"));
    	  
      });
      
      ///////////////////////////////////////////////////////////////////////////////////////////////
      // #2 Listen for specific parameter value 
      final SpecificPluginDevice specificDevice = device.createSpecificVst3Device(NEOVERB_ID);
      final Parameter dryWetParameter = specificDevice.createParameter(2);
      dryWetParameter.displayedValue().addValueObserver(value -> host.println("Dry/Wet: " + value));
      
      ///////////////////////////////////////////////////////////////////////////////////////////////
      // #3 Insert a specific device
      final DocumentState documentState = host.getDocumentState();
      documentState.getSignalSetting("Insert NeoVerb", "Add", "Insert").addSignalObserver(() -> 
      {
	      final InsertionPoint startOfDeviceChainInsertionPoint = cursorTrack.startOfDeviceChainInsertionPoint ();
    	      
         // Crashes Bitwig 3.3.1
         // startOfDeviceChainInsertionPoint.insertVST3Device(NEOVERB_ID);

         // Let's insert an EQ+ instead, which works fine...
         startOfDeviceChainInsertionPoint.insertBitwigDevice (UUID.fromString ("e4815188-ba6f-4d14-bcfc-2dcb8f778ccb"));
      });
      
   }

   
   /** {@inheritDoc} */
   @Override
   public void exit()
   {
       // Not used
   }

   
   /** {@inheritDoc} */
   @Override
   public void flush()
   {
       // Not used
   }
}
