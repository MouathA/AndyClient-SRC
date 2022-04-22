package com.google.common.hash;

import com.google.common.base.*;
import java.util.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.zip.*;

@Beta
public final class Hashing
{
    private static final int GOOD_FAST_HASH_SEED;
    
    public static HashFunction goodFastHash(final int n) {
        final int checkPositiveAndMakeMultipleOf32 = checkPositiveAndMakeMultipleOf32(n);
        if (checkPositiveAndMakeMultipleOf32 == 32) {
            return Murmur3_32Holder.GOOD_FAST_HASH_FUNCTION_32;
        }
        if (checkPositiveAndMakeMultipleOf32 <= 128) {
            return Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        }
        final int n2 = (checkPositiveAndMakeMultipleOf32 + 127) / 128;
        final HashFunction[] array = new HashFunction[n2];
        array[0] = Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        int good_FAST_HASH_SEED = Hashing.GOOD_FAST_HASH_SEED;
        while (1 < n2) {
            good_FAST_HASH_SEED += 1500450271;
            array[1] = murmur3_128(good_FAST_HASH_SEED);
            int n3 = 0;
            ++n3;
        }
        return new ConcatenatedHashFunction(array);
    }
    
    public static HashFunction murmur3_32(final int n) {
        return new Murmur3_32HashFunction(n);
    }
    
    public static HashFunction murmur3_32() {
        return Murmur3_32Holder.MURMUR3_32;
    }
    
    public static HashFunction murmur3_128(final int n) {
        return new Murmur3_128HashFunction(n);
    }
    
    public static HashFunction murmur3_128() {
        return Murmur3_128Holder.MURMUR3_128;
    }
    
    public static HashFunction sipHash24() {
        return SipHash24Holder.SIP_HASH_24;
    }
    
    public static HashFunction sipHash24(final long n, final long n2) {
        return new SipHashFunction(2, 4, n, n2);
    }
    
    public static HashFunction md5() {
        return Md5Holder.MD5;
    }
    
    public static HashFunction sha1() {
        return Sha1Holder.SHA_1;
    }
    
    public static HashFunction sha256() {
        return Sha256Holder.SHA_256;
    }
    
    public static HashFunction sha512() {
        return Sha512Holder.SHA_512;
    }
    
    public static HashFunction crc32() {
        return Crc32Holder.CRC_32;
    }
    
    public static HashFunction adler32() {
        return Adler32Holder.ADLER_32;
    }
    
    private static HashFunction checksumHashFunction(final ChecksumType checksumType, final String s) {
        return new ChecksumHashFunction(checksumType, ChecksumType.access$200(checksumType), s);
    }
    
    public static int consistentHash(final HashCode hashCode, final int n) {
        return consistentHash(hashCode.padToLong(), n);
    }
    
    public static int consistentHash(final long n, final int n2) {
        Preconditions.checkArgument(n2 > 0, "buckets must be positive: %s", n2);
        final LinearCongruentialGenerator linearCongruentialGenerator = new LinearCongruentialGenerator(n);
        int n3;
        do {
            n3 = (int)(1 / linearCongruentialGenerator.nextDouble());
        } while (n3 >= 0 && n3 < n2);
        return 0;
    }
    
    public static HashCode combineOrdered(final Iterable iterable) {
        final Iterator<HashCode> iterator = iterable.iterator();
        Preconditions.checkArgument(iterator.hasNext(), (Object)"Must be at least 1 hash code to combine.");
        final byte[] array = new byte[iterator.next().bits() / 8];
        final Iterator<HashCode> iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            final byte[] bytes = iterator2.next().asBytes();
            Preconditions.checkArgument(bytes.length == array.length, (Object)"All hashcodes must have the same bit length.");
            while (0 < bytes.length) {
                array[0] = (byte)(array[0] * 37 ^ bytes[0]);
                int n = 0;
                ++n;
            }
        }
        return HashCode.fromBytesNoCopy(array);
    }
    
    public static HashCode combineUnordered(final Iterable iterable) {
        final Iterator<HashCode> iterator = iterable.iterator();
        Preconditions.checkArgument(iterator.hasNext(), (Object)"Must be at least 1 hash code to combine.");
        final byte[] array = new byte[iterator.next().bits() / 8];
        final Iterator<HashCode> iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            final byte[] bytes = iterator2.next().asBytes();
            Preconditions.checkArgument(bytes.length == array.length, (Object)"All hashcodes must have the same bit length.");
            while (0 < bytes.length) {
                final byte[] array2 = array;
                final int n = 0;
                array2[n] += bytes[0];
                int n2 = 0;
                ++n2;
            }
        }
        return HashCode.fromBytesNoCopy(array);
    }
    
    static int checkPositiveAndMakeMultipleOf32(final int n) {
        Preconditions.checkArgument(n > 0, (Object)"Number of bits must be positive");
        return n + 31 & 0xFFFFFFE0;
    }
    
    private Hashing() {
    }
    
    static int access$000() {
        return Hashing.GOOD_FAST_HASH_SEED;
    }
    
    static HashFunction access$100(final ChecksumType checksumType, final String s) {
        return checksumHashFunction(checksumType, s);
    }
    
    static {
        GOOD_FAST_HASH_SEED = (int)System.currentTimeMillis();
    }
    
    private static final class LinearCongruentialGenerator
    {
        private long state;
        
        public LinearCongruentialGenerator(final long state) {
            this.state = state;
        }
        
        public double nextDouble() {
            this.state = 2862933555777941757L * this.state + 1L;
            return ((int)(this.state >>> 33) + 1) / 2.147483648E9;
        }
    }
    
    @VisibleForTesting
    static final class ConcatenatedHashFunction extends AbstractCompositeHashFunction
    {
        private final int bits;
        
        ConcatenatedHashFunction(final HashFunction... array) {
            super(array);
            while (0 < array.length) {
                final int n = 0 + array[0].bits();
                int n2 = 0;
                ++n2;
            }
            this.bits = 0;
        }
        
        @Override
        HashCode makeHash(final Hasher[] array) {
            final byte[] array2 = new byte[this.bits / 8];
            while (0 < array.length) {
                final HashCode hash = array[0].hash();
                final int n = 0 + hash.writeBytesTo(array2, 0, hash.bits() / 8);
                int n2 = 0;
                ++n2;
            }
            return HashCode.fromBytesNoCopy(array2);
        }
        
        @Override
        public int bits() {
            return this.bits;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (!(o instanceof ConcatenatedHashFunction)) {
                return false;
            }
            final ConcatenatedHashFunction concatenatedHashFunction = (ConcatenatedHashFunction)o;
            if (this.bits != concatenatedHashFunction.bits || this.functions.length != concatenatedHashFunction.functions.length) {
                return false;
            }
            while (0 < this.functions.length) {
                if (!this.functions[0].equals(concatenatedHashFunction.functions[0])) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            int bits = this.bits;
            final HashFunction[] functions = this.functions;
            while (0 < functions.length) {
                bits ^= functions[0].hashCode();
                int n = 0;
                ++n;
            }
            return bits;
        }
    }
    
    enum ChecksumType implements Supplier
    {
        CRC_32(32) {
            @Override
            public Checksum get() {
                return new CRC32();
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        }, 
        ADLER_32(32) {
            @Override
            public Checksum get() {
                return new Adler32();
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        };
        
        private final int bits;
        private static final ChecksumType[] $VALUES;
        
        private ChecksumType(final String s, final int n, final int bits) {
            this.bits = bits;
        }
        
        @Override
        public abstract Checksum get();
        
        @Override
        public Object get() {
            return this.get();
        }
        
        static int access$200(final ChecksumType checksumType) {
            return checksumType.bits;
        }
        
        ChecksumType(final String s, final int n, final int n2, final Hashing$1 object) {
            this(s, n, n2);
        }
        
        static {
            $VALUES = new ChecksumType[] { ChecksumType.CRC_32, ChecksumType.ADLER_32 };
        }
    }
    
    private static class Adler32Holder
    {
        static final HashFunction ADLER_32;
        
        static {
            ADLER_32 = Hashing.access$100(ChecksumType.ADLER_32, "Hashing.adler32()");
        }
    }
    
    private static class Crc32Holder
    {
        static final HashFunction CRC_32;
        
        static {
            CRC_32 = Hashing.access$100(ChecksumType.CRC_32, "Hashing.crc32()");
        }
    }
    
    private static class Sha512Holder
    {
        static final HashFunction SHA_512;
        
        static {
            SHA_512 = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");
        }
    }
    
    private static class Sha256Holder
    {
        static final HashFunction SHA_256;
        
        static {
            SHA_256 = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");
        }
    }
    
    private static class Sha1Holder
    {
        static final HashFunction SHA_1;
        
        static {
            SHA_1 = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");
        }
    }
    
    private static class Md5Holder
    {
        static final HashFunction MD5;
        
        static {
            MD5 = new MessageDigestHashFunction("MD5", "Hashing.md5()");
        }
    }
    
    private static class SipHash24Holder
    {
        static final HashFunction SIP_HASH_24;
        
        static {
            SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
        }
    }
    
    private static class Murmur3_128Holder
    {
        static final HashFunction MURMUR3_128;
        static final HashFunction GOOD_FAST_HASH_FUNCTION_128;
        
        static {
            MURMUR3_128 = new Murmur3_128HashFunction(0);
            GOOD_FAST_HASH_FUNCTION_128 = Hashing.murmur3_128(Hashing.access$000());
        }
    }
    
    private static class Murmur3_32Holder
    {
        static final HashFunction MURMUR3_32;
        static final HashFunction GOOD_FAST_HASH_FUNCTION_32;
        
        static {
            MURMUR3_32 = new Murmur3_32HashFunction(0);
            GOOD_FAST_HASH_FUNCTION_32 = Hashing.murmur3_32(Hashing.access$000());
        }
    }
}
