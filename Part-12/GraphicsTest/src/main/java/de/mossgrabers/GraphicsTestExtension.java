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

import com.bitwig.extension.api.Color;
import com.bitwig.extension.api.graphics.Bitmap;
import com.bitwig.extension.api.graphics.BitmapFormat;
import com.bitwig.extension.api.graphics.GraphicsOutput;
import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;


public class GraphicsTestExtension extends ControllerExtension
{
    protected GraphicsTestExtension (final GraphicsTestExtensionDefinition definition, final ControllerHost host)
    {
        super (definition, host);
    }


    /** {@inheritDoc} */
    @Override
    public void init ()
    {
        final ControllerHost host = this.getHost ();

        final Bitmap bitmap = host.createBitmap (200, 200, BitmapFormat.ARGB32);
        bitmap.showDisplayWindow ();
        bitmap.setDisplayWindowTitle ("Remote Display Debug Output");

        // Put this in a separate thread! Requires synchronization!
        bitmap.render (this::render);

        // That is how you get your data to send to a display, mitght go to flush() or a separate
        // Thread depending on the refresh requirements of targeted display
        // ByteBuffer buffer = bitmap.getMemoryBlock ().createByteBuffer ();
    }


    private void render (final GraphicsOutput gc)
    {
        gc.setColor (Color.fromRGB (1.0, 0, 0));
        gc.circle (100, 100, 50);
        gc.fill ();
    }


    /** {@inheritDoc} */
    @Override
    public void exit ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public void flush ()
    {
        // Intentionally empty
    }
}
