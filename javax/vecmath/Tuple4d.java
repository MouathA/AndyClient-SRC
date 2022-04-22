package javax.vecmath;

import java.io.*;

public abstract class Tuple4d implements Serializable
{
    public double x;
    public double y;
    public double z;
    public double w;
    
    public Tuple4d(final double n, final double n2, final double n3, final double n4) {
        this.set(n, n2, n3, n4);
    }
    
    public Tuple4d(final double[] array) {
        this.set(array);
    }
    
    public Tuple4d(final Tuple4d tuple4d) {
        this.set(tuple4d);
    }
    
    public Tuple4d(final Tuple4f tuple4f) {
        this.set(tuple4f);
    }
    
    public Tuple4d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
    }
    
    public final void set(final double x, final double y, final double z, final double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public final void set(final double[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }
    
    public final void set(final Tuple4d tuple4d) {
        this.x = tuple4d.x;
        this.y = tuple4d.y;
        this.z = tuple4d.z;
        this.w = tuple4d.w;
    }
    
    public final void set(final Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }
    
    public final void get(final double[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        array[3] = this.w;
    }
    
    public final void get(final Tuple4d tuple4d) {
        tuple4d.x = this.x;
        tuple4d.y = this.y;
        tuple4d.z = this.z;
        tuple4d.w = this.w;
    }
    
    public final void add(final Tuple4d tuple4d, final Tuple4d tuple4d2) {
        this.x = tuple4d.x + tuple4d2.x;
        this.y = tuple4d.y + tuple4d2.y;
        this.z = tuple4d.z + tuple4d2.z;
        this.w = tuple4d.w + tuple4d2.w;
    }
    
    public final void add(final Tuple4d tuple4d) {
        this.x += tuple4d.x;
        this.y += tuple4d.y;
        this.z += tuple4d.z;
        this.w += tuple4d.w;
    }
    
    public final void sub(final Tuple4d tuple4d, final Tuple4d tuple4d2) {
        this.x = tuple4d.x - tuple4d2.x;
        this.y = tuple4d.y - tuple4d2.y;
        this.z = tuple4d.z - tuple4d2.z;
        this.w = tuple4d.w - tuple4d2.w;
    }
    
    public final void sub(final Tuple4d tuple4d) {
        this.x -= tuple4d.x;
        this.y -= tuple4d.y;
        this.z -= tuple4d.z;
        this.w -= tuple4d.w;
    }
    
    public final void negate(final Tuple4d tuple4d) {
        this.x = -tuple4d.x;
        this.y = -tuple4d.y;
        this.z = -tuple4d.z;
        this.w = -tuple4d.w;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }
    
    public final void scale(final double n, final Tuple4d tuple4d) {
        this.x = n * tuple4d.x;
        this.y = n * tuple4d.y;
        this.z = n * tuple4d.z;
        this.w = n * tuple4d.w;
    }
    
    public final void scale(final double n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
    }
    
    public final void scaleAdd(final double n, final Tuple4d tuple4d, final Tuple4d tuple4d2) {
        this.x = n * tuple4d.x + tuple4d2.x;
        this.y = n * tuple4d.y + tuple4d2.y;
        this.z = n * tuple4d.z + tuple4d2.z;
        this.w = n * tuple4d.w + tuple4d2.w;
    }
    
    public final void scaleAdd(final double n, final Tuple4d tuple4d) {
        this.x = n * this.x + tuple4d.x;
        this.y = n * this.y + tuple4d.y;
        this.z = n * this.z + tuple4d.z;
        this.w = n * this.z + tuple4d.w;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.x);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.y);
        final long doubleToLongBits3 = Double.doubleToLongBits(this.z);
        final long doubleToLongBits4 = Double.doubleToLongBits(this.w);
        return (int)(doubleToLongBits ^ doubleToLongBits >> 32 ^ doubleToLongBits2 ^ doubleToLongBits2 >> 32 ^ doubleToLongBits3 ^ doubleToLongBits3 >> 32 ^ doubleToLongBits4 ^ doubleToLongBits4 >> 32);
    }
    
    public boolean equals(final Tuple4d tuple4d) {
        return tuple4d != null && this.x == tuple4d.x && this.y == tuple4d.y && this.z == tuple4d.z && this.w == tuple4d.w;
    }
    
    public boolean epsilonEquals(final Tuple4d tuple4d, final double n) {
        return Math.abs(tuple4d.x - this.x) <= n && Math.abs(tuple4d.y - this.y) <= n && Math.abs(tuple4d.z - this.z) <= n && Math.abs(tuple4d.w - this.w) <= n;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }
    
    public final void clamp(final double n, final double n2, final Tuple4d tuple4d) {
        this.set(tuple4d);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final double n, final Tuple4d tuple4d) {
        this.set(tuple4d);
        this.clampMin(n);
    }
    
    public final void clampMax(final double n, final Tuple4d tuple4d) {
        this.set(tuple4d);
        this.clampMax(n);
    }
    
    public final void absolute(final Tuple4d tuple4d) {
        this.set(tuple4d);
        this.absolute();
    }
    
    public final void clamp(final double n, final double n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clampMin(final double n) {
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
    
    public final void clampMax(final double n) {
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
    
    public final void interpolate(final Tuple4d tuple4d, final Tuple4d tuple4d2, final double n) {
        this.set(tuple4d);
        this.interpolate(tuple4d2, n);
    }
    
    public final void interpolate(final Tuple4d tuple4d, final double n) {
        final double n2 = 1.0 - n;
        this.x = n2 * this.x + n * tuple4d.x;
        this.y = n2 * this.y + n * tuple4d.y;
        this.z = n2 * this.z + n * tuple4d.z;
        this.w = n2 * this.w + n * tuple4d.w;
    }
}
