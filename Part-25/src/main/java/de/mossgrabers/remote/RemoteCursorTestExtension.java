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

import com.bitwig.extension.controller.*;
import com.bitwig.extension.controller.api.*;


public class RemoteCursorTestExtension extends ControllerExtension
{
   ControllerHost host;


   protected RemoteCursorTestExtension (final RemoteCursorTestExtensionDefinition definition, final ControllerHost host)
   {
      super (definition, host);
   }

   @Override
   public void init ()
   {
      this.host = getHost ();      
      final CursorTrack cursorTrack = host.createCursorTrack ("MyCursorTrackID", "The Cursor Track", 0, 0, true);
      final PinnableCursorDevice cursorDevice = cursorTrack.createCursorDevice ("CURSOR_DEVICE", "Cursor device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
      
      final int numMonitoredPages = 8;

      for (int i = 0; i < numMonitoredPages; i++)
      {
         final int page = i;
         final CursorRemoteControlsPage remoteControls = cursorDevice.createCursorRemoteControlsPage ("Page " + page, 8, "");
         remoteControls.pageCount ().addValueObserver(newValue -> readjust (remoteControls, page), -1);
         remoteControls.selectedPageIndex ().addValueObserver(newValue -> readjust (remoteControls, page), -1);
         final RemoteControl control = remoteControls.getParameter (0);
         control.name ().addValueObserver (name -> notifyParamName (name, remoteControls, page));
      }

      host.showPopupNotification("RemoteCursorTest Initialized");
   }


   private void notifyParamName (final String name, final CursorRemoteControlsPage remoteControls, final int page)
   {
      if (page >= remoteControls.pageCount().get())
         host.println ("Page " + page + " Param 1: Invalid (no page " + page + ")");
      else
         host.println ("Page " + page + " Param 1: " + name);
   }


   private void readjust (final CursorRemoteControlsPage remoteControls, final int page)
   {
      if (page >= remoteControls.pageCount ().get ())
      {
         host.println ("Page " + page + " is out of range.");
         return;
      }
      
      if (remoteControls.selectedPageIndex ().get () == page)
      {
         host.println ("Page " + page + " OK.");
         return;
      }
         
      host.println ("Reselecting Page " + page + "...");
      host.scheduleTask (() ->
      {
         remoteControls.selectedPageIndex ().set (page);
      }, 500);
     
   }

   @Override
   public void exit ()
   {
      getHost().showPopupNotification ("RemoteCursorTest Exited");
   }

   @Override
   public void flush ()
   {
   }
}
