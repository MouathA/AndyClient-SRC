package javax.vecmath;

import java.io.*;

public class Color4b extends Tuple4b implements Serializable
{
    public Color4b(final byte b, final byte b2, final byte b3, final byte b4) {
        super(b, b2, b3, b4);
    }
    
    public Color4b(final byte[] array) {
        super(array);
    }
    
    public Color4b(final Color4b color4b) {
        super(color4b);
    }
    
    public Color4b(final Tuple4b tuple4b) {
        super(tuple4b);
    }
    
    public Color4b() {
    }
}
