package com.google.common.hash;

import java.io.*;
import java.security.*;
import com.google.common.base.*;
import java.util.*;

final class MessageDigestHashFunction extends AbstractStreamingHashFunction implements Serializable
{
    private final MessageDigest prototype;
    private final int bytes;
    private final boolean supportsClone;
    private final String toString;
    
    MessageDigestHashFunction(final String s, final String s2) {
        this.prototype = getMessageDigest(s);
        this.bytes = this.prototype.getDigestLength();
        this.toString = (String)Preconditions.checkNotNull(s2);
        this.supportsClone = this.supportsClone();
    }
    
    MessageDigestHashFunction(final String s, final int bytes, final String s2) {
        this.toString = (String)Preconditions.checkNotNull(s2);
        this.prototype = getMessageDigest(s);
        final int digestLength = this.prototype.getDigestLength();
        Preconditions.checkArgument(bytes >= 4 && bytes <= digestLength, "bytes (%s) must be >= 4 and < %s", bytes, digestLength);
        this.bytes = bytes;
        this.supportsClone = this.supportsClone();
    }
    
    private boolean supportsClone() {
        this.prototype.clone();
        return true;
    }
    
    @Override
    public int bits() {
        return this.bytes * 8;
    }
    
    @Override
    public String toString() {
        return this.toString;
    }
    
    private static MessageDigest getMessageDigest(final String s) {
        return MessageDigest.getInstance(s);
    }
    
    @Override
    public Hasher newHasher() {
        if (this.supportsClone) {
            return new MessageDigestHasher((MessageDigest)this.prototype.clone(), this.bytes, null);
        }
        return new MessageDigestHasher(getMessageDigest(this.prototype.getAlgorithm()), this.bytes, null);
    }
    
    Object writeReplace() {
        return new SerializedForm(this.prototype.getAlgorithm(), this.bytes, this.toString, null);
    }
    
    private static final class MessageDigestHasher extends AbstractByteHasher
    {
        private final MessageDigest digest;
        private final int bytes;
        private boolean done;
        
        private MessageDigestHasher(final MessageDigest digest, final int bytes) {
            this.digest = digest;
            this.bytes = bytes;
        }
        
        @Override
        protected void update(final byte b) {
            this.checkNotDone();
            this.digest.update(b);
        }
        
        @Override
        protected void update(final byte[] array) {
            this.checkNotDone();
            this.digest.update(array);
        }
        
        @Override
        protected void update(final byte[] array, final int n, final int n2) {
            this.checkNotDone();
            this.digest.update(array, n, n2);
        }
        
        private void checkNotDone() {
            Preconditions.checkState(!this.done, (Object)"Cannot re-use a Hasher after calling hash() on it");
        }
        
        @Override
        public HashCode hash() {
            this.checkNotDone();
            this.done = true;
            return (this.bytes == this.digest.getDigestLength()) ? HashCode.fromBytesNoCopy(this.digest.digest()) : HashCode.fromBytesNoCopy(Arrays.copyOf(this.digest.digest(), this.bytes));
        }
        
        MessageDigestHasher(final MessageDigest messageDigest, final int n, final MessageDigestHashFunction$1 object) {
            this(messageDigest, n);
        }
    }
    
    private static final class SerializedForm implements Serializable
    {
        private final String algorithmName;
        private final int bytes;
        private final String toString;
        private static final long serialVersionUID = 0L;
        
        private SerializedForm(final String algorithmName, final int bytes, final String toString) {
            this.algorithmName = algorithmName;
            this.bytes = bytes;
            this.toString = toString;
        }
        
        private Object readResolve() {
            return new MessageDigestHashFunction(this.algorithmName, this.bytes, this.toString);
        }
        
        SerializedForm(final String s, final int n, final String s2, final MessageDigestHashFunction$1 object) {
            this(s, n, s2);
        }
    }
}
