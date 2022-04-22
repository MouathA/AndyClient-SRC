package javax.vecmath;

import java.io.*;

public class Vector4f extends Tuple4f implements Serializable
{
    public Vector4f(final float n, final float n2, final float n3, final float n4) {
        super(n, n2, n3, n4);
    }
    
    public Vector4f(final float[] array) {
        super(array);
    }
    
    public Vector4f(final Vector4f vector4f) {
        super(vector4f);
    }
    
    public Vector4f(final Vector4d vector4d) {
        super(vector4d);
    }
    
    public Vector4f(final Tuple4d tuple4d) {
        super(tuple4d);
    }
    
    public Vector4f(final Tuple4f tuple4f) {
        super(tuple4f);
    }
    
    public Vector4f() {
    }
    
    public Vector4f(final Tuple3f tuple3f) {
        super(tuple3f.x, tuple3f.y, tuple3f.z, 0.0f);
    }
    
    public final void set(final Tuple3f tuple3f) {
        this.set(tuple3f.x, tuple3f.y, tuple3f.z, 0.0f);
    }
    
    public final float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }
    
    public final float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }
    
    public final float dot(final Vector4f vector4f) {
        return this.x * vector4f.x + this.y * vector4f.y + this.z * vector4f.z + this.w * vector4f.w;
    }
    
    public final void normalize(final Vector4d vector4d) {
        this.set(vector4d);
        this.normalize();
    }
    
    public final void normalize() {
        final double n = this.length();
        this.x /= (float)n;
        this.y /= (float)n;
        this.z /= (float)n;
        this.w /= (float)n;
    }
    
    public final float angle(final Vector4f vector4f) {
        return (float)Math.acos(this.dot(vector4f) / (double)vector4f.length() / this.length());
    }
}
