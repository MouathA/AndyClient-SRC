package javax.vecmath;

import java.io.*;

public class Vector3d extends Tuple3d implements Serializable
{
    public Vector3d(final double n, final double n2, final double n3) {
        super(n, n2, n3);
    }
    
    public Vector3d(final double[] array) {
        super(array);
    }
    
    public Vector3d(final Vector3f vector3f) {
        super(vector3f);
    }
    
    public Vector3d(final Vector3d vector3d) {
        super(vector3d);
    }
    
    public Vector3d(final Tuple3d tuple3d) {
        super(tuple3d);
    }
    
    public Vector3d(final Tuple3f tuple3f) {
        super(tuple3f);
    }
    
    public Vector3d() {
    }
    
    public final void cross(final Vector3d vector3d, final Vector3d vector3d2) {
        this.set(vector3d.y * vector3d2.z - vector3d.z * vector3d2.y, vector3d.z * vector3d2.x - vector3d.x * vector3d2.z, vector3d.x * vector3d2.y - vector3d.y * vector3d2.x);
    }
    
    public final void normalize(final Vector3d vector3d) {
        this.set(vector3d);
        this.normalize();
    }
    
    public final void normalize() {
        final double length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }
    
    public final double dot(final Vector3d vector3d) {
        return this.x * vector3d.x + this.y * vector3d.y + this.z * vector3d.z;
    }
    
    public final double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public final double length() {
        return Math.sqrt(this.lengthSquared());
    }
    
    public final double angle(final Vector3d vector3d) {
        final double n = this.y * vector3d.z - this.z * vector3d.y;
        final double n2 = this.z * vector3d.x - this.x * vector3d.z;
        final double n3 = this.x * vector3d.y - this.y * vector3d.x;
        return Math.abs(Math.atan2(Math.sqrt(n * n + n2 * n2 + n3 * n3), this.dot(vector3d)));
    }
}
