package com.google.common.hash;

import java.nio.charset.*;

abstract class AbstractHasher implements Hasher
{
    @Override
    public final Hasher putBoolean(final boolean b) {
        return this.putByte((byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    public final Hasher putDouble(final double n) {
        return this.putLong(Double.doubleToRawLongBits(n));
    }
    
    @Override
    public final Hasher putFloat(final float n) {
        return this.putInt(Float.floatToRawIntBits(n));
    }
    
    @Override
    public Hasher putUnencodedChars(final CharSequence charSequence) {
        while (0 < charSequence.length()) {
            this.putChar(charSequence.charAt(0));
            int n = 0;
            ++n;
        }
        return this;
    }
    
    @Override
    public Hasher putString(final CharSequence charSequence, final Charset charset) {
        return this.putBytes(charSequence.toString().getBytes(charset));
    }
    
    @Override
    public PrimitiveSink putString(final CharSequence charSequence, final Charset charset) {
        return this.putString(charSequence, charset);
    }
    
    @Override
    public PrimitiveSink putUnencodedChars(final CharSequence charSequence) {
        return this.putUnencodedChars(charSequence);
    }
    
    @Override
    public PrimitiveSink putBoolean(final boolean b) {
        return this.putBoolean(b);
    }
    
    @Override
    public PrimitiveSink putDouble(final double n) {
        return this.putDouble(n);
    }
    
    @Override
    public PrimitiveSink putFloat(final float n) {
        return this.putFloat(n);
    }
}
