package javax.vecmath;

import java.io.*;

public class Vector3f extends Tuple3f implements Serializable
{
    public Vector3f(final float n, final float n2, final float n3) {
        super(n, n2, n3);
    }
    
    public Vector3f(final float[] array) {
        super(array);
    }
    
    public Vector3f(final Vector3f vector3f) {
        super(vector3f);
    }
    
    public Vector3f(final Vector3d vector3d) {
        super(vector3d);
    }
    
    public Vector3f(final Tuple3d tuple3d) {
        super(tuple3d);
    }
    
    public Vector3f(final Tuple3f tuple3f) {
        super(tuple3f);
    }
    
    public Vector3f() {
    }
    
    public final float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public final float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }
    
    public final void cross(final Vector3f vector3f, final Vector3f vector3f2) {
        this.set(vector3f.y * vector3f2.z - vector3f.z * vector3f2.y, vector3f.z * vector3f2.x - vector3f.x * vector3f2.z, vector3f.x * vector3f2.y - vector3f.y * vector3f2.x);
    }
    
    public final float dot(final Vector3f vector3f) {
        return this.x * vector3f.x + this.y * vector3f.y + this.z * vector3f.z;
    }
    
    public final void normalize(final Vector3f vector3f) {
        this.set(vector3f);
        this.normalize();
    }
    
    public final void normalize() {
        final double n = this.length();
        this.x /= (float)n;
        this.y /= (float)n;
        this.z /= (float)n;
    }
    
    public final float angle(final Vector3f vector3f) {
        final double n = this.y * vector3f.z - this.z * vector3f.y;
        final double n2 = this.z * vector3f.x - this.x * vector3f.z;
        final double n3 = this.x * vector3f.y - this.y * vector3f.x;
        return (float)Math.abs(Math.atan2(Math.sqrt(n * n + n2 * n2 + n3 * n3), this.dot(vector3f)));
    }
}
