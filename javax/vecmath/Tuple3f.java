package javax.vecmath;

import java.io.*;

public abstract class Tuple3f implements Serializable
{
    public float x;
    public float y;
    public float z;
    
    public Tuple3f(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Tuple3f(final float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public Tuple3f(final Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }
    
    public Tuple3f(final Tuple3d tuple3d) {
        this.x = (float)tuple3d.x;
        this.y = (float)tuple3d.y;
        this.z = (float)tuple3d.z;
    }
    
    public Tuple3f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }
    
    public final void set(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public final void set(final float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public final void set(final Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }
    
    public final void set(final Tuple3d tuple3d) {
        this.x = (float)tuple3d.x;
        this.y = (float)tuple3d.y;
        this.z = (float)tuple3d.z;
    }
    
    public final void get(final float[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
    }
    
    public final void get(final Tuple3f tuple3f) {
        tuple3f.x = this.x;
        tuple3f.y = this.y;
        tuple3f.z = this.z;
    }
    
    public final void add(final Tuple3f tuple3f, final Tuple3f tuple3f2) {
        this.x = tuple3f.x + tuple3f2.x;
        this.y = tuple3f.y + tuple3f2.y;
        this.z = tuple3f.z + tuple3f2.z;
    }
    
    public final void add(final Tuple3f tuple3f) {
        this.x += tuple3f.x;
        this.y += tuple3f.y;
        this.z += tuple3f.z;
    }
    
    public final void sub(final Tuple3f tuple3f, final Tuple3f tuple3f2) {
        this.x = tuple3f.x - tuple3f2.x;
        this.y = tuple3f.y - tuple3f2.y;
        this.z = tuple3f.z - tuple3f2.z;
    }
    
    public final void sub(final Tuple3f tuple3f) {
        this.x -= tuple3f.x;
        this.y -= tuple3f.y;
        this.z -= tuple3f.z;
    }
    
    public final void negate(final Tuple3f tuple3f) {
        this.x = -tuple3f.x;
        this.y = -tuple3f.y;
        this.z = -tuple3f.z;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public final void scale(final float n, final Tuple3f tuple3f) {
        this.x = n * tuple3f.x;
        this.y = n * tuple3f.y;
        this.z = n * tuple3f.z;
    }
    
    public final void scale(final float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
    }
    
    public final void scaleAdd(final float n, final Tuple3f tuple3f, final Tuple3f tuple3f2) {
        this.x = n * tuple3f.x + tuple3f2.x;
        this.y = n * tuple3f.y + tuple3f2.y;
        this.z = n * tuple3f.z + tuple3f2.z;
    }
    
    public final void scaleAdd(final float n, final Tuple3f tuple3f) {
        this.x = n * this.x + tuple3f.x;
        this.y = n * this.y + tuple3f.y;
        this.z = n * this.z + tuple3f.z;
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.x) ^ Float.floatToIntBits(this.y) ^ Float.floatToIntBits(this.z);
    }
    
    public boolean equals(final Tuple3f tuple3f) {
        return tuple3f != null && this.x == tuple3f.x && this.y == tuple3f.y && this.z == tuple3f.z;
    }
    
    public boolean epsilonEquals(final Tuple3f tuple3f, final float n) {
        return Math.abs(tuple3f.x - this.x) <= n && Math.abs(tuple3f.y - this.y) <= n && Math.abs(tuple3f.z - this.z) <= n;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
    
    public final void clamp(final float n, final float n2, final Tuple3f tuple3f) {
        this.set(tuple3f);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final float n, final Tuple3f tuple3f) {
        this.set(tuple3f);
        this.clampMin(n);
    }
    
    public final void clampMax(final float n, final Tuple3f tuple3f) {
        this.set(tuple3f);
        this.clampMax(n);
    }
    
    public final void absolute(final Tuple3f tuple3f) {
        this.set(tuple3f);
        this.absolute();
    }
    
    public final void clamp(final float n, final float n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clampMin(final float z) {
        if (this.x < z) {
            this.x = z;
        }
        if (this.y < z) {
            this.y = z;
        }
        if (this.z < z) {
            this.z = z;
        }
    }
    
    public final void clampMax(final float z) {
        if (this.x > z) {
            this.x = z;
        }
        if (this.y > z) {
            this.y = z;
        }
        if (this.z > z) {
            this.z = z;
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
    }
    
    public final void interpolate(final Tuple3f tuple3f, final Tuple3f tuple3f2, final float n) {
        this.set(tuple3f);
        this.interpolate(tuple3f2, n);
    }
    
    public final void interpolate(final Tuple3f tuple3f, final float n) {
        final float n2 = 1.0f - n;
        this.x = n2 * this.x + n * tuple3f.x;
        this.y = n2 * this.y + n * tuple3f.y;
        this.z = n2 * this.z + n * tuple3f.z;
    }
}
