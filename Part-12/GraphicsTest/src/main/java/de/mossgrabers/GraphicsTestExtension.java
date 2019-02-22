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
