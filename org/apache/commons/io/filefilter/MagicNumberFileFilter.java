package org.apache.commons.io.filefilter;

import org.apache.commons.io.*;
import java.io.*;
import java.util.*;

public class MagicNumberFileFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = -547733176983104172L;
    private final byte[] magicNumbers;
    private final long byteOffset;
    
    public MagicNumberFileFilter(final byte[] array) {
        this(array, 0L);
    }
    
    public MagicNumberFileFilter(final String s) {
        this(s, 0L);
    }
    
    public MagicNumberFileFilter(final String s, final long byteOffset) {
        if (s == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        }
        if (s.length() == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (byteOffset < 0L) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        this.magicNumbers = s.getBytes();
        this.byteOffset = byteOffset;
    }
    
    public MagicNumberFileFilter(final byte[] array, final long byteOffset) {
        if (array == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (byteOffset < 0L) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        System.arraycopy(array, 0, this.magicNumbers = new byte[array.length], 0, array.length);
        this.byteOffset = byteOffset;
    }
    
    @Override
    public boolean accept(final File file) {
        if (file == null || !file.isFile() || !file.canRead()) {
            return false;
        }
        final byte[] array = new byte[this.magicNumbers.length];
        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.seek(this.byteOffset);
        if (randomAccessFile.read(array) != this.magicNumbers.length) {
            IOUtils.closeQuietly(randomAccessFile);
            return false;
        }
        Arrays.equals(this.magicNumbers, array);
        IOUtils.closeQuietly(randomAccessFile);
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append("(");
        sb.append(new String(this.magicNumbers));
        sb.append(",");
        sb.append(this.byteOffset);
        sb.append(")");
        return sb.toString();
    }
}
