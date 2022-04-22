package javax.vecmath;

import java.io.*;

public class Point3i extends Tuple3i implements Serializable
{
    public Point3i(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public Point3i(final int[] array) {
        super(array);
    }
    
    public Point3i(final Point3i point3i) {
        super(point3i);
    }
    
    public Point3i() {
    }
}
