package javax.vecmath;

import java.io.*;

public class TexCoord3f extends Tuple3f implements Serializable
{
    public TexCoord3f(final float n, final float n2, final float n3) {
        super(n, n2, n3);
    }
    
    public TexCoord3f(final float[] array) {
        super(array);
    }
    
    public TexCoord3f(final TexCoord3f texCoord3f) {
        super(texCoord3f);
    }
    
    public TexCoord3f() {
    }
}
