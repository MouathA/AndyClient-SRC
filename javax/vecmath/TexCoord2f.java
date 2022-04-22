package javax.vecmath;

import java.io.*;

public class TexCoord2f extends Tuple2f implements Serializable
{
    public TexCoord2f(final float n, final float n2) {
        super(n, n2);
    }
    
    public TexCoord2f(final float[] array) {
        super(array);
    }
    
    public TexCoord2f(final TexCoord2f texCoord2f) {
        super(texCoord2f);
    }
    
    public TexCoord2f(final Tuple2f tuple2f) {
        super(tuple2f);
    }
    
    public TexCoord2f() {
    }
}
