package javax.vecmath;

import java.io.*;

public abstract class Tuple3d implements Serializable
{
    public double x;
    public double y;
    public double z;
    
    public Tuple3d(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Tuple3d(final double[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public Tuple3d(final Tuple3d tuple3d) {
        this.x = tuple3d.x;
        this.y = tuple3d.y;
        this.z = tuple3d.z;
    }
    
    public Tuple3d(final Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }
    
    public Tuple3d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public final void set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public final void set(final double[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public final void set(final Tuple3d tuple3d) {
        this.x = tuple3d.x;
        this.y = tuple3d.y;
        this.z = tuple3d.z;
    }
    
    public final void set(final Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }
    
    public final void get(final double[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
    }
    
    public final void get(final Tuple3d tuple3d) {
        tuple3d.x = this.x;
        tuple3d.y = this.y;
        tuple3d.z = this.z;
    }
    
    public final void add(final Tuple3d tuple3d, final Tuple3d tuple3d2) {
        this.x = tuple3d.x + tuple3d2.x;
        this.y = tuple3d.y + tuple3d2.y;
        this.z = tuple3d.z + tuple3d2.z;
    }
    
    public final void add(final Tuple3d tuple3d) {
        this.x += tuple3d.x;
        this.y += tuple3d.y;
        this.z += tuple3d.z;
    }
    
    public final void sub(final Tuple3d tuple3d, final Tuple3d tuple3d2) {
        this.x = tuple3d.x - tuple3d2.x;
        this.y = tuple3d.y - tuple3d2.y;
        this.z = tuple3d.z - tuple3d2.z;
    }
    
    public final void sub(final Tuple3d tuple3d) {
        this.x -= tuple3d.x;
        this.y -= tuple3d.y;
        this.z -= tuple3d.z;
    }
    
    public final void negate(final Tuple3d tuple3d) {
        this.x = -tuple3d.x;
        this.y = -tuple3d.y;
        this.z = -tuple3d.z;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public final void scale(final double n, final Tuple3d tuple3d) {
        this.x = n * tuple3d.x;
        this.y = n * tuple3d.y;
        this.z = n * tuple3d.z;
    }
    
    public final void scale(final double n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
    }
    
    public final void scaleAdd(final double n, final Tuple3d tuple3d, final Tuple3d tuple3d2) {
        this.x = n * tuple3d.x + tuple3d2.x;
        this.y = n * tuple3d.y + tuple3d2.y;
        this.z = n * tuple3d.z + tuple3d2.z;
    }
    
    public final void scaleAdd(final double n, final Tuple3d tuple3d) {
        this.x = n * this.x + tuple3d.x;
        this.y = n * this.y + tuple3d.y;
        this.z = n * this.z + tuple3d.z;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.x);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.y);
        final long doubleToLongBits3 = Double.doubleToLongBits(this.z);
        return (int)(doubleToLongBits ^ doubleToLongBits >> 32 ^ doubleToLongBits2 ^ doubleToLongBits2 >> 32 ^ doubleToLongBits3 ^ doubleToLongBits3 >> 32);
    }
    
    public boolean equals(final Tuple3d tuple3d) {
        return tuple3d != null && this.x == tuple3d.x && this.y == tuple3d.y && this.z == tuple3d.z;
    }
    
    public boolean epsilonEquals(final Tuple3d tuple3d, final double n) {
        return Math.abs(tuple3d.x - this.x) <= n && Math.abs(tuple3d.y - this.y) <= n && Math.abs(tuple3d.z - this.z) <= n;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
    
    public final void absolute(final Tuple3d tuple3d) {
        this.set(tuple3d);
        this.absolute();
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
    
    @Deprecated
    public final void clamp(final float n, final float n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    @Deprecated
    public final void clamp(final float n, final float n2, final Tuple3d tuple3d) {
        this.set(tuple3d);
        this.clamp(n, n2);
    }
    
    @Deprecated
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
    }
    
    @Deprecated
    public final void clampMin(final float n, final Tuple3d tuple3d) {
        this.set(tuple3d);
        this.clampMin(n);
    }
    
    @Deprecated
    public final void clampMax(final float n, final Tuple3d tuple3d) {
        this.set(tuple3d);
        this.clampMax(n);
    }
    
    @Deprecated
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
    }
    
    public final void clamp(final double n, final double n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clamp(final double n, final double n2, final Tuple3d tuple3d) {
        this.set(tuple3d);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final double z) {
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
    
    public final void clampMin(final double n, final Tuple3d tuple3d) {
        this.set(tuple3d);
        this.clampMin(n);
    }
    
    public final void clampMax(final double n, final Tuple3d tuple3d) {
        this.set(tuple3d);
        this.clampMax(n);
    }
    
    public final void clampMax(final double z) {
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
    
    @Deprecated
    public final void interpolate(final Tuple3d tuple3d, final Tuple3d tuple3d2, final float n) {
        this.set(tuple3d);
        this.interpolate(tuple3d2, n);
    }
    
    @Deprecated
    public final void interpolate(final Tuple3d tuple3d, final float n) {
        final float n2 = 1.0f - n;
        this.x = n2 * this.x + n * tuple3d.x;
        this.y = n2 * this.y + n * tuple3d.y;
        this.z = n2 * this.z + n * tuple3d.z;
    }
    
    public final void interpolate(final Tuple3d tuple3d, final Tuple3d tuple3d2, final double n) {
        this.set(tuple3d);
        this.interpolate(tuple3d2, n);
    }
    
    public final void interpolate(final Tuple3d tuple3d, final double n) {
        final double n2 = 1.0 - n;
        this.x = n2 * this.x + n * tuple3d.x;
        this.y = n2 * this.y + n * tuple3d.y;
        this.z = n2 * this.z + n * tuple3d.z;
    }
}
