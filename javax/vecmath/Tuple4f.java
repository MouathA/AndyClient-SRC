package javax.vecmath;

import java.io.*;

public abstract class Tuple4f implements Serializable
{
    public float x;
    public float y;
    public float z;
    public float w;
    
    public Tuple4f(final float n, final float n2, final float n3, final float n4) {
        this.set(n, n2, n3, n4);
    }
    
    public Tuple4f(final float[] array) {
        this.set(array);
    }
    
    public Tuple4f(final Tuple4f tuple4f) {
        this.set(tuple4f);
    }
    
    public Tuple4f(final Tuple4d tuple4d) {
        this.set(tuple4d);
    }
    
    public Tuple4f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }
    
    public final void set(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public final void set(final float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }
    
    public final void set(final Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }
    
    public final void set(final Tuple4d tuple4d) {
        this.x = (float)tuple4d.x;
        this.y = (float)tuple4d.y;
        this.z = (float)tuple4d.z;
        this.w = (float)tuple4d.w;
    }
    
    public final void get(final float[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        array[3] = this.w;
    }
    
    public final void get(final Tuple4f tuple4f) {
        tuple4f.x = this.x;
        tuple4f.y = this.y;
        tuple4f.z = this.z;
        tuple4f.w = this.w;
    }
    
    public final void add(final Tuple4f tuple4f, final Tuple4f tuple4f2) {
        this.x = tuple4f.x + tuple4f2.x;
        this.y = tuple4f.y + tuple4f2.y;
        this.z = tuple4f.z + tuple4f2.z;
        this.w = tuple4f.w + tuple4f2.w;
    }
    
    public final void add(final Tuple4f tuple4f) {
        this.x += tuple4f.x;
        this.y += tuple4f.y;
        this.z += tuple4f.z;
        this.w += tuple4f.w;
    }
    
    public final void sub(final Tuple4f tuple4f, final Tuple4f tuple4f2) {
        this.x = tuple4f.x - tuple4f2.x;
        this.y = tuple4f.y - tuple4f2.y;
        this.z = tuple4f.z - tuple4f2.z;
        this.w = tuple4f.w - tuple4f2.w;
    }
    
    public final void sub(final Tuple4f tuple4f) {
        this.x -= tuple4f.x;
        this.y -= tuple4f.y;
        this.z -= tuple4f.z;
        this.w -= tuple4f.w;
    }
    
    public final void negate(final Tuple4f tuple4f) {
        this.x = -tuple4f.x;
        this.y = -tuple4f.y;
        this.z = -tuple4f.z;
        this.w = -tuple4f.w;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }
    
    public final void scale(final float n, final Tuple4f tuple4f) {
        this.x = n * tuple4f.x;
        this.y = n * tuple4f.y;
        this.z = n * tuple4f.z;
        this.w = n * tuple4f.w;
    }
    
    public final void scale(final float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
    }
    
    public final void scaleAdd(final float n, final Tuple4f tuple4f, final Tuple4f tuple4f2) {
        this.x = n * tuple4f.x + tuple4f2.x;
        this.y = n * tuple4f.y + tuple4f2.y;
        this.z = n * tuple4f.z + tuple4f2.z;
        this.w = n * tuple4f.w + tuple4f2.w;
    }
    
    public final void scaleAdd(final float n, final Tuple4f tuple4f) {
        this.x = n * this.x + tuple4f.x;
        this.y = n * this.y + tuple4f.y;
        this.z = n * this.z + tuple4f.z;
        this.w = n * this.z + tuple4f.w;
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.x) ^ Float.floatToIntBits(this.y) ^ Float.floatToIntBits(this.z) ^ Float.floatToIntBits(this.w);
    }
    
    public boolean equals(final Tuple4f tuple4f) {
        return tuple4f != null && this.x == tuple4f.x && this.y == tuple4f.y && this.z == tuple4f.z && this.w == tuple4f.w;
    }
    
    public boolean epsilonEquals(final Tuple4f tuple4f, final float n) {
        return Math.abs(tuple4f.x - this.x) <= n && Math.abs(tuple4f.y - this.y) <= n && Math.abs(tuple4f.z - this.z) <= n && Math.abs(tuple4f.w - this.w) <= n;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }
    
    public final void clamp(final float n, final float n2, final Tuple4f tuple4f) {
        this.set(tuple4f);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final float n, final Tuple4f tuple4f) {
        this.set(tuple4f);
        this.clampMin(n);
    }
    
    public final void clampMax(final float n, final Tuple4f tuple4f) {
        this.set(tuple4f);
        this.clampMax(n);
    }
    
    public final void absolute(final Tuple4f tuple4f) {
        this.set(tuple4f);
        this.absolute();
    }
    
    public final void clamp(final float n, final float n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clampMin(final float n) {
        if (this.x < n) {
            this.x = n;
        }
        if (this.y < n) {
            this.y = n;
        }
        if (this.z < n) {
            this.z = n;
        }
        if (this.w < n) {
            this.w = n;
        }
    }
    
    public final void clampMax(final float n) {
        if (this.x > n) {
            this.x = n;
        }
        if (this.y > n) {
            this.y = n;
        }
        if (this.z > n) {
            this.z = n;
        }
        if (this.w > n) {
            this.w = n;
        }
    }
    
    public final void absolute() {
        if (this.x < 0.0) {
            this.x = -this.x;
        }
        if (this.y < 0.0) {
            this.y = -this.y;
        }
        if (this.z < 0.0) {
            this.z = -this.z;
        }
        if (this.w < 0.0) {
            this.w = -this.w;
        }
    }
    
    public final void interpolate(final Tuple4f tuple4f, final Tuple4f tuple4f2, final float n) {
        this.set(tuple4f);
        this.interpolate(tuple4f2, n);
    }
    
    public final void interpolate(final Tuple4f tuple4f, final float n) {
        final float n2 = 1.0f - n;
        this.x = n2 * this.x + n * tuple4f.x;
        this.y = n2 * this.y + n * tuple4f.y;
        this.z = n2 * this.z + n * tuple4f.z;
        this.w = n2 * this.w + n * tuple4f.w;
    }
}
