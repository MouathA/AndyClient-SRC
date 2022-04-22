package javax.vecmath;

import java.io.*;

public class Color3f extends Tuple3f implements Serializable
{
    public Color3f(final float n, final float n2, final float n3) {
        super(n, n2, n3);
    }
    
    public Color3f(final float[] array) {
        super(array);
    }
    
    public Color3f(final Color3f color3f) {
        super(color3f);
    }
    
    public Color3f(final Tuple3d tuple3d) {
        super(tuple3d);
    }
    
    public Color3f(final Tuple3f tuple3f) {
        super(tuple3f);
    }
    
    public Color3f() {
    }
}
