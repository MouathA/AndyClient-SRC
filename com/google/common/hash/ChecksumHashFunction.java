package com.google.common.hash;

import java.io.*;
import com.google.common.base.*;
import java.util.zip.*;

final class ChecksumHashFunction extends AbstractStreamingHashFunction implements Serializable
{
    private final Supplier checksumSupplier;
    private final int bits;
    private final String toString;
    private static final long serialVersionUID = 0L;
    
    ChecksumHashFunction(final Supplier supplier, final int bits, final String s) {
        this.checksumSupplier = (Supplier)Preconditions.checkNotNull(supplier);
        Preconditions.checkArgument(bits == 32 || bits == 64, "bits (%s) must be either 32 or 64", bits);
        this.bits = bits;
        this.toString = (String)Preconditions.checkNotNull(s);
    }
    
    @Override
    public int bits() {
        return this.bits;
    }
    
    @Override
    public Hasher newHasher() {
        return new ChecksumHasher((Checksum)this.checksumSupplier.get(), null);
    }
    
    @Override
    public String toString() {
        return this.toString;
    }
    
    static int access$100(final ChecksumHashFunction checksumHashFunction) {
        return checksumHashFunction.bits;
    }
    
    private final class ChecksumHasher extends AbstractByteHasher
    {
        private final Checksum checksum;
        final ChecksumHashFunction this$0;
        
        private ChecksumHasher(final ChecksumHashFunction this$0, final Checksum checksum) {
            this.this$0 = this$0;
            this.checksum = (Checksum)Preconditions.checkNotNull(checksum);
        }
        
        @Override
        protected void update(final byte b) {
            this.checksum.update(b);
        }
        
        @Override
        protected void update(final byte[] array, final int n, final int n2) {
            this.checksum.update(array, n, n2);
        }
        
        @Override
        public HashCode hash() {
            final long value = this.checksum.getValue();
            if (ChecksumHashFunction.access$100(this.this$0) == 32) {
                return HashCode.fromInt((int)value);
            }
            return HashCode.fromLong(value);
        }
        
        ChecksumHasher(final ChecksumHashFunction checksumHashFunction, final Checksum checksum, final ChecksumHashFunction$1 object) {
            this(checksumHashFunction, checksum);
        }
    }
}
