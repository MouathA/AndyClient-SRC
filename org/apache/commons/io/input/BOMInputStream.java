package org.apache.commons.io.input;

import org.apache.commons.io.*;
import java.io.*;
import java.util.*;

public class BOMInputStream extends ProxyInputStream
{
    private final boolean include;
    private final List boms;
    private ByteOrderMark byteOrderMark;
    private int[] firstBytes;
    private int fbLength;
    private int fbIndex;
    private int markFbIndex;
    private boolean markedAtStart;
    private static final Comparator ByteOrderMarkLengthComparator;
    
    public BOMInputStream(final InputStream inputStream) {
        this(inputStream, false, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
    }
    
    public BOMInputStream(final InputStream inputStream, final boolean b) {
        this(inputStream, b, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
    }
    
    public BOMInputStream(final InputStream inputStream, final ByteOrderMark... array) {
        this(inputStream, false, array);
    }
    
    public BOMInputStream(final InputStream inputStream, final boolean include, final ByteOrderMark... array) {
        super(inputStream);
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("No BOMs specified");
        }
        this.include = include;
        Arrays.sort(array, BOMInputStream.ByteOrderMarkLengthComparator);
        this.boms = Arrays.asList(array);
    }
    
    public boolean hasBOM() throws IOException {
        return this.getBOM() != null;
    }
    
    public boolean hasBOM(final ByteOrderMark byteOrderMark) throws IOException {
        if (!this.boms.contains(byteOrderMark)) {
            throw new IllegalArgumentException("Stream not configure to detect " + byteOrderMark);
        }
        return this.byteOrderMark != null && this.getBOM().equals(byteOrderMark);
    }
    
    public ByteOrderMark getBOM() throws IOException {
        if (this.firstBytes == null) {
            this.fbLength = 0;
            this.firstBytes = new int[this.boms.get(0).length()];
            while (0 < this.firstBytes.length) {
                this.firstBytes[0] = this.in.read();
                ++this.fbLength;
                if (this.firstBytes[0] < 0) {
                    break;
                }
                int n = 0;
                ++n;
            }
            this.byteOrderMark = this.find();
            if (this.byteOrderMark != null && !this.include) {
                if (this.byteOrderMark.length() < this.firstBytes.length) {
                    this.fbIndex = this.byteOrderMark.length();
                }
                else {
                    this.fbLength = 0;
                }
            }
        }
        return this.byteOrderMark;
    }
    
    public String getBOMCharsetName() throws IOException {
        this.getBOM();
        return (this.byteOrderMark == null) ? null : this.byteOrderMark.getCharsetName();
    }
    
    private int readFirstBytes() throws IOException {
        this.getBOM();
        return (this.fbIndex < this.fbLength) ? this.firstBytes[this.fbIndex++] : -1;
    }
    
    private ByteOrderMark find() {
        for (final ByteOrderMark byteOrderMark : this.boms) {
            if (this.matches(byteOrderMark)) {
                return byteOrderMark;
            }
        }
        return null;
    }
    
    private boolean matches(final ByteOrderMark byteOrderMark) {
        while (0 < byteOrderMark.length()) {
            if (byteOrderMark.get(0) != this.firstBytes[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public int read() throws IOException {
        final int firstBytes = this.readFirstBytes();
        return (firstBytes >= 0) ? firstBytes : this.in.read();
    }
    
    @Override
    public int read(final byte[] array, int n, int n2) throws IOException {
        while (n2 > 0 && 0 >= 0) {
            this.readFirstBytes();
            if (0 >= 0) {
                array[n++] = 0;
                --n2;
                int n3 = 0;
                ++n3;
            }
        }
        final int read = this.in.read(array, n, n2);
        return (read < 0) ? ((0 > 0) ? 0 : -1) : (0 + read);
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public synchronized void mark(final int n) {
        this.markFbIndex = this.fbIndex;
        this.markedAtStart = (this.firstBytes == null);
        this.in.mark(n);
    }
    
    @Override
    public synchronized void reset() throws IOException {
        this.fbIndex = this.markFbIndex;
        if (this.markedAtStart) {
            this.firstBytes = null;
        }
        this.in.reset();
    }
    
    @Override
    public long skip(long n) throws IOException {
        while (n > 0L && this.readFirstBytes() >= 0) {
            --n;
        }
        return this.in.skip(n);
    }
    
    static {
        ByteOrderMarkLengthComparator = new Comparator() {
            public int compare(final ByteOrderMark byteOrderMark, final ByteOrderMark byteOrderMark2) {
                final int length = byteOrderMark.length();
                final int length2 = byteOrderMark2.length();
                if (length > length2) {
                    return -1;
                }
                if (length2 > length) {
                    return 1;
                }
                return 0;
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((ByteOrderMark)o, (ByteOrderMark)o2);
            }
        };
    }
}
