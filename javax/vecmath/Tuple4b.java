package javax.vecmath;

import java.io.*;

public abstract class Tuple4b implements Serializable
{
    public byte x;
    public byte y;
    public byte z;
    public byte w;
    
    public Tuple4b(final byte x, final byte y, final byte z, final byte w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Tuple4b(final byte[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }
    
    public Tuple4b(final Tuple4b tuple4b) {
        this.x = tuple4b.x;
        this.y = tuple4b.y;
        this.z = tuple4b.z;
        this.w = tuple4b.w;
    }
    
    public Tuple4b() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }
    
    public final void set(final Tuple4b tuple4b) {
        this.x = tuple4b.x;
        this.y = tuple4b.y;
        this.z = tuple4b.z;
        this.w = tuple4b.w;
    }
    
    public final void set(final byte[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }
    
    public final void get(final byte[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        array[3] = this.w;
    }
    
    public final void get(final Tuple4b tuple4b) {
        tuple4b.x = this.x;
        tuple4b.y = this.y;
        tuple4b.z = this.z;
        tuple4b.w = this.w;
    }
    
    @Override
    public int hashCode() {
        return this.x | this.y << 8 | this.z << 16 | this.w << 24;
    }
    
    public boolean equals(final Tuple4b tuple4b) {
        return tuple4b != null && this.x == tuple4b.x && this.y == tuple4b.y && this.z == tuple4b.z && this.w == tuple4b.w;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }
}
