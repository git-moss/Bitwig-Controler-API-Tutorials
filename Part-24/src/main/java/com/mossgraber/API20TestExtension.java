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

package com.mossgraber;

import com.bitwig.extension.controller.*;
import com.bitwig.extension.controller.api.*;

public class API20TestExtension extends ControllerExtension
{
   protected API20TestExtension(final API20TestExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
      // Uncomment the methods you want to test
      // this.testLastClickedParameter ();
      this.testMasterRecorder ();
      // this.testNewParameterValues ();
   }


   private void testLastClickedParameter ()
   {
      final ControllerHost host = getHost();   

      final LastClickedParameter lcp = host.createLastClickedParameter("MyLastClickedParam", "Last Clicked 1");
      final Parameter lastParameter =  lcp.parameter();
      
      lastParameter.name().addValueObserver(name -> host.println("Last Clicked - Name: '" + name + "'"));
      lastParameter.discreteValueCount().addValueObserver(discrete -> host.println("Last Clicked - Discrete: " + discrete));
      lastParameter.getOrigin().addValueObserver(origin -> host.println("Last Clicked - Origin: " + origin));

      final ColorValue cv = lcp.parameterColor();
      cv.addValueObserver((red, green, blue) -> host.println("Last Clicked - Color: '" + red + ":" + green + ":" + blue));

      // Not implemented:
      // lcp.updateLockedToLastClicked();

      lcp.isLocked().addValueObserver(isLocked ->  host.println("Is locked: " + isLocked));
      // Bitwig seems to remember the last locked parameter on restart, if it was locked and then does not release it!
      lcp.isLocked().set(false);

      host.scheduleTask (() -> {

         lcp.isLocked().set(true);
         host.println("Now locked to: " + lastParameter.name().get());

         host.scheduleTask (() -> lcp.isLocked().set(false), 10000);
         
      }, 10000);
   }

   private void testMasterRecorder ()
   {
      final ControllerHost host = getHost();   
      final MasterRecorder recorder = host.createMasterRecorder();
      
      recorder.isActive().addValueObserver(active -> 
      {
        host.println ("Master recorder: " + (active ? "Recording..." : "Stopped."));
      });
      
      recorder.duration().addValueObserver(milliseconds -> 
      {
        long hours = (milliseconds / 3600000);
        long minutes = (milliseconds % 3600000) / 60000;
        long seconds = (milliseconds % 60000) / 1000;
        long millis = milliseconds % 1000;
        host.println (String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis));
      });

      host.scheduleTask (() -> {

         host.println ("Starting Master recording...");
         recorder.start();

         host.scheduleTask (() -> {
            
            host.println ("Stopping Master recording...");
            recorder.stop();
            
         }, 10000);
         
      }, 10000);
   }

   private void testNewParameterValues ()
   {
      final ControllerHost host = getHost();
      final Project project = host.getProject ();
      final Track rootTrackGroup = project.getRootTrackGroup ();
      final CursorRemoteControlsPage remoteControlsPage = rootTrackGroup.createCursorRemoteControlsPage (1);
      final RemoteControl parameter = remoteControlsPage.getParameter(0);
      parameter.name().addValueObserver(name -> host.println("Param 1 - Name: " + name));
      parameter.discreteValueCount().addValueObserver(discrete -> host.println("Param 1 - Discrete: " + discrete));
      parameter.getOrigin().addValueObserver(origin -> host.println("Param 1 - Origin: " + origin));
   }

   @Override
   public void exit()
   {
   }

   @Override
   public void flush()
   {
   }
}
