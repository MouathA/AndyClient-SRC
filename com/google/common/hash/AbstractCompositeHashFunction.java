package com.google.common.hash;

import com.google.common.base.*;
import java.nio.charset.*;

abstract class AbstractCompositeHashFunction extends AbstractStreamingHashFunction
{
    final HashFunction[] functions;
    private static final long serialVersionUID = 0L;
    
    AbstractCompositeHashFunction(final HashFunction... functions) {
        while (0 < functions.length) {
            Preconditions.checkNotNull(functions[0]);
            int n = 0;
            ++n;
        }
        this.functions = functions;
    }
    
    abstract HashCode makeHash(final Hasher[] p0);
    
    @Override
    public Hasher newHasher() {
        final Hasher[] array = new Hasher[this.functions.length];
        while (0 < array.length) {
            array[0] = this.functions[0].newHasher();
            int n = 0;
            ++n;
        }
        return new Hasher(array) {
            final Hasher[] val$hashers;
            final AbstractCompositeHashFunction this$0;
            
            @Override
            public Hasher putByte(final byte b) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putByte(b);
                    int n = 0;
                    ++n;
                }
                return this;
            }
            
            @Override
            public Hasher putBytes(final byte[] array) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putBytes(array);
                    int n = 0;
                    ++n;
                }
                return this;
            }
            
            @Override
            public Hasher putBytes(final byte[] array, final int n, final int n2) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putBytes(array, n, n2);
                    int n3 = 0;
                    ++n3;
                }
                return this;
            }
            
            @Override
            public Hasher putShort(final short n) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putShort(n);
                    int n2 = 0;
                    ++n2;
                }
                return this;
            }
            
            @Override
            public Hasher putInt(final int n) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putInt(n);
                    int n2 = 0;
                    ++n2;
                }
                return this;
            }
            
            @Override
            public Hasher putLong(final long n) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putLong(n);
                    int n2 = 0;
                    ++n2;
                }
                return this;
            }
            
            @Override
            public Hasher putFloat(final float n) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putFloat(n);
                    int n2 = 0;
                    ++n2;
                }
                return this;
            }
            
            @Override
            public Hasher putDouble(final double n) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putDouble(n);
                    int n2 = 0;
                    ++n2;
                }
                return this;
            }
            
            @Override
            public Hasher putBoolean(final boolean b) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putBoolean(b);
                    int n = 0;
                    ++n;
                }
                return this;
            }
            
            @Override
            public Hasher putChar(final char c) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putChar(c);
                    int n = 0;
                    ++n;
                }
                return this;
            }
            
            @Override
            public Hasher putUnencodedChars(final CharSequence charSequence) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putUnencodedChars(charSequence);
                    int n = 0;
                    ++n;
                }
                return this;
            }
            
            @Override
            public Hasher putString(final CharSequence charSequence, final Charset charset) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putString(charSequence, charset);
                    int n = 0;
                    ++n;
                }
                return this;
            }
            
            @Override
            public Hasher putObject(final Object o, final Funnel funnel) {
                final Hasher[] val$hashers = this.val$hashers;
                while (0 < val$hashers.length) {
                    val$hashers[0].putObject(o, funnel);
                    int n = 0;
                    ++n;
                }
                return this;
            }
            
            @Override
            public HashCode hash() {
                return this.this$0.makeHash(this.val$hashers);
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
            public PrimitiveSink putChar(final char c) {
                return this.putChar(c);
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
            
            @Override
            public PrimitiveSink putLong(final long n) {
                return this.putLong(n);
            }
            
            @Override
            public PrimitiveSink putInt(final int n) {
                return this.putInt(n);
            }
            
            @Override
            public PrimitiveSink putShort(final short n) {
                return this.putShort(n);
            }
            
            @Override
            public PrimitiveSink putBytes(final byte[] array, final int n, final int n2) {
                return this.putBytes(array, n, n2);
            }
            
            @Override
            public PrimitiveSink putBytes(final byte[] array) {
                return this.putBytes(array);
            }
            
            @Override
            public PrimitiveSink putByte(final byte b) {
                return this.putByte(b);
            }
        };
    }
}
