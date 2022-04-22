package com.viaversion.viaversion.api.minecraft.chunks;

import java.util.*;

public class NibbleArray
{
    private final byte[] handle;
    
    public NibbleArray(final int n) {
        if (n == 0 || n % 2 != 0) {
            throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
        }
        this.handle = new byte[n / 2];
    }
    
    public NibbleArray(final byte[] handle) {
        if (handle.length == 0 || handle.length % 2 != 0) {
            throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
        }
        this.handle = handle;
    }
    
    public byte get(final int n, final int n2, final int n3) {
        return this.get(ChunkSection.index(n, n2, n3));
    }
    
    public byte get(final int n) {
        final byte b = this.handle[n / 2];
        if (n % 2 == 0) {
            return (byte)(b & 0xF);
        }
        return (byte)(b >> 4 & 0xF);
    }
    
    public void set(final int n, final int n2, final int n3, final int n4) {
        this.set(ChunkSection.index(n, n2, n3), n4);
    }
    
    public void set(int n, final int n2) {
        if (n % 2 == 0) {
            n /= 2;
            this.handle[n] = (byte)((this.handle[n] & 0xF0) | (n2 & 0xF));
        }
        else {
            n /= 2;
            this.handle[n] = (byte)((this.handle[n] & 0xF) | (n2 & 0xF) << 4);
        }
    }
    
    public int size() {
        return this.handle.length * 2;
    }
    
    public int actualSize() {
        return this.handle.length;
    }
    
    public void fill(final byte b) {
        final byte b2 = (byte)(b & 0xF);
        Arrays.fill(this.handle, (byte)(b2 << 4 | b2));
    }
    
    public byte[] getHandle() {
        return this.handle;
    }
    
    public void setHandle(final byte[] array) {
        if (array.length != this.handle.length) {
            throw new IllegalArgumentException("Length of handle must equal to size of nibble array!");
        }
        System.arraycopy(array, 0, this.handle, 0, array.length);
    }
}
