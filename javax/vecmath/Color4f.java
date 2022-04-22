package javax.vecmath;

import java.io.*;

public class Color4f extends Tuple4f implements Serializable
{
    public Color4f(final float n, final float n2, final float n3, final float n4) {
        super(n, n2, n3, n4);
    }
    
    public Color4f(final float[] array) {
        super(array);
    }
    
    public Color4f(final Color4f color4f) {
        super(color4f);
    }
    
    public Color4f(final Tuple4d tuple4d) {
        super(tuple4d);
    }
    
    public Color4f(final Tuple4f tuple4f) {
        super(tuple4f);
    }
    
    public Color4f() {
    }
}
