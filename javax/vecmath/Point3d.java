package javax.vecmath;

import java.io.*;

public class Point3d extends Tuple3d implements Serializable
{
    public Point3d(final double n, final double n2, final double n3) {
        super(n, n2, n3);
    }
    
    public Point3d(final double[] array) {
        super(array);
    }
    
    public Point3d(final Point3d point3d) {
        super(point3d);
    }
    
    public Point3d(final Point3f point3f) {
        super(point3f);
    }
    
    public Point3d(final Tuple3d tuple3d) {
        super(tuple3d);
    }
    
    public Point3d(final Tuple3f tuple3f) {
        super(tuple3f);
    }
    
    public Point3d() {
    }
    
    public final double distanceSquared(final Point3d point3d) {
        final double n = this.x - point3d.x;
        final double n2 = this.y - point3d.y;
        final double n3 = this.z - point3d.z;
        return n * n + n2 * n2 + n3 * n3;
    }
    
    public final double distance(final Point3d point3d) {
        return Math.sqrt(this.distanceSquared(point3d));
    }
    
    public final double distanceL1(final Point3d point3d) {
        return Math.abs(this.x - point3d.x) + Math.abs(this.y - point3d.y) + Math.abs(this.z - point3d.z);
    }
    
    public final double distanceLinf(final Point3d point3d) {
        return Math.max(Math.max(Math.abs(this.x - point3d.x), Math.abs(this.y - point3d.y)), Math.abs(this.z - point3d.z));
    }
    
    public final void project(final Point4d point4d) {
        this.x = point4d.x / point4d.w;
        this.y = point4d.y / point4d.w;
        this.z = point4d.z / point4d.w;
    }
}
