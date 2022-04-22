package com.google.common.hash;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.security.*;
import java.io.*;
import com.google.common.primitives.*;

@Beta
public abstract class HashCode
{
    private static final char[] hexDigits;
    
    HashCode() {
    }
    
    public abstract int bits();
    
    public abstract int asInt();
    
    public abstract long asLong();
    
    public abstract long padToLong();
    
    public abstract byte[] asBytes();
    
    public int writeBytesTo(final byte[] array, final int n, int min) {
        min = Ints.min(min, this.bits() / 8);
        Preconditions.checkPositionIndexes(n, n + min, array.length);
        this.writeBytesToImpl(array, n, min);
        return min;
    }
    
    abstract void writeBytesToImpl(final byte[] p0, final int p1, final int p2);
    
    byte[] getBytesInternal() {
        return this.asBytes();
    }
    
    public static HashCode fromInt(final int n) {
        return new IntHashCode(n);
    }
    
    public static HashCode fromLong(final long n) {
        return new LongHashCode(n);
    }
    
    public static HashCode fromBytes(final byte[] array) {
        Preconditions.checkArgument(array.length >= 1, (Object)"A HashCode must contain at least 1 byte.");
        return fromBytesNoCopy(array.clone());
    }
    
    static HashCode fromBytesNoCopy(final byte[] array) {
        return new BytesHashCode(array);
    }
    
    public static HashCode fromString(final String s) {
        Preconditions.checkArgument(s.length() >= 2, "input string (%s) must have at least 2 characters", s);
        Preconditions.checkArgument(s.length() % 2 == 0, "input string (%s) must have an even number of characters", s);
        final byte[] array = new byte[s.length() / 2];
        while (0 < s.length()) {
            array[0] = (byte)((decode(s.charAt(0)) << 4) + decode(s.charAt(1)));
            final int n;
            n += 2;
        }
        return fromBytesNoCopy(array);
    }
    
    private static int decode(final char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new IllegalArgumentException("Illegal hexadecimal character: " + c);
    }
    
    @Override
    public final boolean equals(@Nullable final Object o) {
        return o instanceof HashCode && MessageDigest.isEqual(this.asBytes(), ((HashCode)o).asBytes());
    }
    
    @Override
    public final int hashCode() {
        if (this.bits() >= 32) {
            return this.asInt();
        }
        final byte[] bytes = this.asBytes();
        int n = bytes[0] & 0xFF;
        while (1 < bytes.length) {
            n |= (bytes[1] & 0xFF) << 8;
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = this.asBytes();
        final StringBuilder sb = new StringBuilder(2 * bytes.length);
        final byte[] array = bytes;
        while (0 < array.length) {
            final byte b = array[0];
            sb.append(HashCode.hexDigits[b >> 4 & 0xF]).append(HashCode.hexDigits[b & 0xF]);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    static {
        hexDigits = "0123456789abcdef".toCharArray();
    }
    
    private static final class BytesHashCode extends HashCode implements Serializable
    {
        final byte[] bytes;
        private static final long serialVersionUID = 0L;
        
        BytesHashCode(final byte[] array) {
            this.bytes = (byte[])Preconditions.checkNotNull(array);
        }
        
        @Override
        public int bits() {
            return this.bytes.length * 8;
        }
        
        @Override
        public byte[] asBytes() {
            return this.bytes.clone();
        }
        
        @Override
        public int asInt() {
            Preconditions.checkState(this.bytes.length >= 4, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", this.bytes.length);
            return (this.bytes[0] & 0xFF) | (this.bytes[1] & 0xFF) << 8 | (this.bytes[2] & 0xFF) << 16 | (this.bytes[3] & 0xFF) << 24;
        }
        
        @Override
        public long asLong() {
            Preconditions.checkState(this.bytes.length >= 8, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", this.bytes.length);
            return this.padToLong();
        }
        
        @Override
        public long padToLong() {
            long n = this.bytes[0] & 0xFF;
            while (1 < Math.min(this.bytes.length, 8)) {
                n |= ((long)this.bytes[1] & 0xFFL) << 8;
                int n2 = 0;
                ++n2;
            }
            return n;
        }
        
        @Override
        void writeBytesToImpl(final byte[] array, final int n, final int n2) {
            System.arraycopy(this.bytes, 0, array, n, n2);
        }
        
        @Override
        byte[] getBytesInternal() {
            return this.bytes;
        }
    }
    
    private static final class LongHashCode extends HashCode implements Serializable
    {
        final long hash;
        private static final long serialVersionUID = 0L;
        
        LongHashCode(final long hash) {
            this.hash = hash;
        }
        
        @Override
        public int bits() {
            return 64;
        }
        
        @Override
        public byte[] asBytes() {
            return new byte[] { (byte)this.hash, (byte)(this.hash >> 8), (byte)(this.hash >> 16), (byte)(this.hash >> 24), (byte)(this.hash >> 32), (byte)(this.hash >> 40), (byte)(this.hash >> 48), (byte)(this.hash >> 56) };
        }
        
        @Override
        public int asInt() {
            return (int)this.hash;
        }
        
        @Override
        public long asLong() {
            return this.hash;
        }
        
        @Override
        public long padToLong() {
            return this.hash;
        }
        
        @Override
        void writeBytesToImpl(final byte[] array, final int n, final int n2) {
            while (0 < n2) {
                array[n + 0] = (byte)(this.hash >> 0);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    private static final class IntHashCode extends HashCode implements Serializable
    {
        final int hash;
        private static final long serialVersionUID = 0L;
        
        IntHashCode(final int hash) {
            this.hash = hash;
        }
        
        @Override
        public int bits() {
            return 32;
        }
        
        @Override
        public byte[] asBytes() {
            return new byte[] { (byte)this.hash, (byte)(this.hash >> 8), (byte)(this.hash >> 16), (byte)(this.hash >> 24) };
        }
        
        @Override
        public int asInt() {
            return this.hash;
        }
        
        @Override
        public long asLong() {
            throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
        }
        
        @Override
        public long padToLong() {
            return UnsignedInts.toLong(this.hash);
        }
        
        @Override
        void writeBytesToImpl(final byte[] array, final int n, final int n2) {
            while (0 < n2) {
                array[n + 0] = (byte)(this.hash >> 0);
                int n3 = 0;
                ++n3;
            }
        }
    }
}
