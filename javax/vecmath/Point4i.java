package javax.vecmath;

import java.io.*;

public class Point4i extends Tuple4i implements Serializable
{
    public Point4i(final int n, final int n2, final int n3, final int n4) {
        super(n, n2, n3, n4);
    }
    
    public Point4i(final int[] array) {
        super(array);
    }
    
    public Point4i(final Point4i point4i) {
        super(point4i);
    }
    
    public Point4i() {
    }
}
