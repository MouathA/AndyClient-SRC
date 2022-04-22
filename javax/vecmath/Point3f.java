package javax.vecmath;

import java.io.*;

public class Point3f extends Tuple3f implements Serializable
{
    public Point3f(final float n, final float n2, final float n3) {
        super(n, n2, n3);
    }
    
    public Point3f(final float[] array) {
        super(array);
    }
    
    public Point3f(final Point3f point3f) {
        super(point3f);
    }
    
    public Point3f(final Point3d point3d) {
        super(point3d);
    }
    
    public Point3f(final Tuple3f tuple3f) {
        super(tuple3f);
    }
    
    public Point3f(final Tuple3d tuple3d) {
        super(tuple3d);
    }
    
    public Point3f() {
    }
    
    public final float distanceSquared(final Point3f point3f) {
        final double n = this.x - point3f.x;
        final double n2 = this.y - point3f.y;
        final double n3 = this.z - point3f.z;
        return (float)(n * n + n2 * n2 + n3 * n3);
    }
    
    public final float distance(final Point3f point3f) {
        return (float)Math.sqrt(this.distanceSquared(point3f));
    }
    
    public final float distanceL1(final Point3f point3f) {
        return Math.abs(this.x - point3f.x) + Math.abs(this.y - point3f.y) + Math.abs(this.z - point3f.z);
    }
    
    public final float distanceLinf(final Point3f point3f) {
        return Math.max(Math.max(Math.abs(this.x - point3f.x), Math.abs(this.y - point3f.y)), Math.abs(this.z - point3f.z));
    }
    
    public final void project(final Point4f point4f) {
        this.x = point4f.x / point4f.w;
        this.y = point4f.y / point4f.w;
        this.z = point4f.z / point4f.w;
    }
}
