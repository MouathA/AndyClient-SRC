package org.apache.commons.io.output;

import org.apache.commons.io.input.*;
import java.util.*;
import java.io.*;

public class ByteArrayOutputStream extends OutputStream
{
    private static final byte[] EMPTY_BYTE_ARRAY;
    private final List buffers;
    private int currentBufferIndex;
    private int filledBufferSum;
    private byte[] currentBuffer;
    private int count;
    
    public ByteArrayOutputStream() {
        this(1024);
    }
    
    public ByteArrayOutputStream(final int n) {
        this.buffers = new ArrayList();
        if (n < 0) {
            throw new IllegalArgumentException("Negative initial size: " + n);
        }
        // monitorenter(this)
        this.needNewBuffer(n);
    }
    // monitorexit(this)
    
    private void needNewBuffer(final int n) {
        if (this.currentBufferIndex < this.buffers.size() - 1) {
            this.filledBufferSum += this.currentBuffer.length;
            ++this.currentBufferIndex;
            this.currentBuffer = this.buffers.get(this.currentBufferIndex);
        }
        else {
            int max;
            if (this.currentBuffer == null) {
                max = n;
                this.filledBufferSum = 0;
            }
            else {
                max = Math.max(this.currentBuffer.length << 1, n - this.filledBufferSum);
                this.filledBufferSum += this.currentBuffer.length;
            }
            ++this.currentBufferIndex;
            this.currentBuffer = new byte[max];
            this.buffers.add(this.currentBuffer);
        }
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) {
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        // monitorenter(this)
        final int count = this.count + n2;
        int i = n2;
        final int n3 = this.count - this.filledBufferSum;
        while (i > 0) {
            final int min = Math.min(i, this.currentBuffer.length - 0);
            System.arraycopy(array, n + n2 - i, this.currentBuffer, 0, min);
            i -= min;
            if (i > 0) {
                this.needNewBuffer(count);
            }
        }
        this.count = count;
    }
    // monitorexit(this)
    
    @Override
    public synchronized void write(final int n) {
        final int n2 = this.count - this.filledBufferSum;
        if (0 == this.currentBuffer.length) {
            this.needNewBuffer(this.count + 1);
        }
        this.currentBuffer[0] = (byte)n;
        ++this.count;
    }
    
    public synchronized int write(final InputStream inputStream) throws IOException {
        final int n = this.count - this.filledBufferSum;
        for (int i = inputStream.read(this.currentBuffer, 0, this.currentBuffer.length - 0); i != -1; i = inputStream.read(this.currentBuffer, 0, this.currentBuffer.length - 0)) {
            this.count += i;
            if (0 == this.currentBuffer.length) {
                this.needNewBuffer(this.currentBuffer.length);
            }
        }
        return 0;
    }
    
    public synchronized int size() {
        return this.count;
    }
    
    @Override
    public void close() throws IOException {
    }
    
    public synchronized void reset() {
        this.count = 0;
        this.filledBufferSum = 0;
        this.currentBufferIndex = 0;
        this.currentBuffer = this.buffers.get(this.currentBufferIndex);
    }
    
    public synchronized void writeTo(final OutputStream outputStream) throws IOException {
        int count = this.count;
        for (final byte[] array : this.buffers) {
            final int min = Math.min(array.length, count);
            outputStream.write(array, 0, min);
            count -= min;
            if (count == 0) {
                break;
            }
        }
    }
    
    public static InputStream toBufferedInputStream(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(inputStream);
        return byteArrayOutputStream.toBufferedInputStream();
    }
    
    private InputStream toBufferedInputStream() {
        int count = this.count;
        if (count == 0) {
            return new ClosedInputStream();
        }
        final ArrayList<Object> list = new ArrayList<Object>(this.buffers.size());
        for (final byte[] array : this.buffers) {
            final int min = Math.min(array.length, count);
            list.add(new ByteArrayInputStream(array, 0, min));
            count -= min;
            if (count == 0) {
                break;
            }
        }
        return new SequenceInputStream((Enumeration<? extends InputStream>)Collections.enumeration(list));
    }
    
    public synchronized byte[] toByteArray() {
        int count = this.count;
        if (count == 0) {
            return ByteArrayOutputStream.EMPTY_BYTE_ARRAY;
        }
        final byte[] array = new byte[count];
        for (final byte[] array2 : this.buffers) {
            final int min = Math.min(array2.length, count);
            System.arraycopy(array2, 0, array, 0, min);
            count -= min;
            if (count == 0) {
                break;
            }
        }
        return array;
    }
    
    @Override
    public String toString() {
        return new String(this.toByteArray());
    }
    
    public String toString(final String s) throws UnsupportedEncodingException {
        return new String(this.toByteArray(), s);
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
    }
}
