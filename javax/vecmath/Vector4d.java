package javax.vecmath;

import java.io.*;

public class Vector4d extends Tuple4d implements Serializable
{
    public Vector4d(final double n, final double n2, final double n3, final double n4) {
        super(n, n2, n3, n4);
    }
    
    public Vector4d(final double[] array) {
        super(array);
    }
    
    public Vector4d(final Vector4f vector4f) {
        super(vector4f);
    }
    
    public Vector4d(final Vector4d vector4d) {
        super(vector4d);
    }
    
    public Vector4d(final Tuple4d tuple4d) {
        super(tuple4d);
    }
    
    public Vector4d(final Tuple4f tuple4f) {
        super(tuple4f);
    }
    
    public Vector4d() {
    }
    
    public Vector4d(final Tuple3d tuple3d) {
        super(tuple3d.x, tuple3d.y, tuple3d.z, 0.0);
    }
    
    public final void set(final Tuple3d tuple3d) {
        this.set(tuple3d.x, tuple3d.y, tuple3d.z, 0.0);
    }
    
    public final double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }
    
    public final double length() {
        return Math.sqrt(this.lengthSquared());
    }
    
    public final double dot(final Vector4d vector4d) {
        return this.x * vector4d.x + this.y * vector4d.y + this.z * vector4d.z + this.w * vector4d.w;
    }
    
    public final void normalize(final Vector4d vector4d) {
        this.set(vector4d);
        this.normalize();
    }
    
    public final void normalize() {
        final double length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
        this.w /= length;
    }
    
    public final double angle(final Vector4d vector4d) {
        return Math.acos(this.dot(vector4d) / vector4d.length() / this.length());
    }
}
