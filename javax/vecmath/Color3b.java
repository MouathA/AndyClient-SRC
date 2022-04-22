package javax.vecmath;

import java.io.*;

public class Color3b extends Tuple3b implements Serializable
{
    public Color3b(final byte b, final byte b2, final byte b3) {
        super(b, b2, b3);
    }
    
    public Color3b(final byte[] array) {
        super(array);
    }
    
    public Color3b(final Color3b color3b) {
        super(color3b);
    }
    
    public Color3b(final Tuple3b tuple3b) {
        super(tuple3b);
    }
    
    public Color3b() {
    }
}
