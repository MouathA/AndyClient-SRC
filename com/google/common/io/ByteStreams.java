package com.google.common.io;

import com.google.common.annotations.*;
import java.nio.channels.*;
import java.nio.*;
import java.util.*;
import com.google.common.hash.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.io.*;

@Beta
public final class ByteStreams
{
    private static final int BUF_SIZE = 4096;
    private static final OutputStream NULL_OUTPUT_STREAM;
    
    private ByteStreams() {
    }
    
    @Deprecated
    public static InputSupplier newInputStreamSupplier(final byte[] array) {
        return asInputSupplier(ByteSource.wrap(array));
    }
    
    @Deprecated
    public static InputSupplier newInputStreamSupplier(final byte[] array, final int n, final int n2) {
        return asInputSupplier(ByteSource.wrap(array).slice(n, n2));
    }
    
    @Deprecated
    public static void write(final byte[] array, final OutputSupplier outputSupplier) throws IOException {
        asByteSink(outputSupplier).write(array);
    }
    
    @Deprecated
    public static long copy(final InputSupplier inputSupplier, final OutputSupplier outputSupplier) throws IOException {
        return asByteSource(inputSupplier).copyTo(asByteSink(outputSupplier));
    }
    
    @Deprecated
    public static long copy(final InputSupplier inputSupplier, final OutputStream outputStream) throws IOException {
        return asByteSource(inputSupplier).copyTo(outputStream);
    }
    
    @Deprecated
    public static long copy(final InputStream inputStream, final OutputSupplier outputSupplier) throws IOException {
        return asByteSink(outputSupplier).writeFrom(inputStream);
    }
    
    public static long copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        final byte[] array = new byte[4096];
        long n = 0L;
        while (true) {
            final int read = inputStream.read(array);
            if (read == -1) {
                break;
            }
            outputStream.write(array, 0, read);
            n += read;
        }
        return n;
    }
    
    public static long copy(final ReadableByteChannel readableByteChannel, final WritableByteChannel writableByteChannel) throws IOException {
        Preconditions.checkNotNull(readableByteChannel);
        Preconditions.checkNotNull(writableByteChannel);
        final ByteBuffer allocate = ByteBuffer.allocate(4096);
        long n = 0L;
        while (readableByteChannel.read(allocate) != -1) {
            allocate.flip();
            while (allocate.hasRemaining()) {
                n += writableByteChannel.write(allocate);
            }
            allocate.clear();
        }
        return n;
    }
    
    public static byte[] toByteArray(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    static byte[] toByteArray(final InputStream inputStream, final int n) throws IOException {
        final byte[] array = new byte[n];
        int read;
        for (int i = n; i > 0; i -= read) {
            final int n2 = n - i;
            read = inputStream.read(array, n2, i);
            if (read == -1) {
                return Arrays.copyOf(array, n2);
            }
        }
        final int read2 = inputStream.read();
        if (read2 == -1) {
            return array;
        }
        final FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream(null);
        fastByteArrayOutputStream.write(read2);
        copy(inputStream, fastByteArrayOutputStream);
        final byte[] array2 = new byte[array.length + fastByteArrayOutputStream.size()];
        System.arraycopy(array, 0, array2, 0, array.length);
        fastByteArrayOutputStream.writeTo(array2, array.length);
        return array2;
    }
    
    @Deprecated
    public static byte[] toByteArray(final InputSupplier inputSupplier) throws IOException {
        return asByteSource(inputSupplier).read();
    }
    
    public static ByteArrayDataInput newDataInput(final byte[] array) {
        return newDataInput(new ByteArrayInputStream(array));
    }
    
    public static ByteArrayDataInput newDataInput(final byte[] array, final int n) {
        Preconditions.checkPositionIndex(n, array.length);
        return newDataInput(new ByteArrayInputStream(array, n, array.length - n));
    }
    
    public static ByteArrayDataInput newDataInput(final ByteArrayInputStream byteArrayInputStream) {
        return new ByteArrayDataInputStream((ByteArrayInputStream)Preconditions.checkNotNull(byteArrayInputStream));
    }
    
    public static ByteArrayDataOutput newDataOutput() {
        return newDataOutput(new ByteArrayOutputStream());
    }
    
    public static ByteArrayDataOutput newDataOutput(final int n) {
        Preconditions.checkArgument(n >= 0, "Invalid size: %s", n);
        return newDataOutput(new ByteArrayOutputStream(n));
    }
    
    public static ByteArrayDataOutput newDataOutput(final ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayDataOutputStream((ByteArrayOutputStream)Preconditions.checkNotNull(byteArrayOutputStream));
    }
    
    public static OutputStream nullOutputStream() {
        return ByteStreams.NULL_OUTPUT_STREAM;
    }
    
    public static InputStream limit(final InputStream inputStream, final long n) {
        return new LimitedInputStream(inputStream, n);
    }
    
    @Deprecated
    public static long length(final InputSupplier inputSupplier) throws IOException {
        return asByteSource(inputSupplier).size();
    }
    
    @Deprecated
    public static boolean equal(final InputSupplier inputSupplier, final InputSupplier inputSupplier2) throws IOException {
        return asByteSource(inputSupplier).contentEquals(asByteSource(inputSupplier2));
    }
    
    public static void readFully(final InputStream inputStream, final byte[] array) throws IOException {
        readFully(inputStream, array, 0, array.length);
    }
    
    public static void readFully(final InputStream inputStream, final byte[] array, final int n, final int n2) throws IOException {
        final int read = read(inputStream, array, n, n2);
        if (read != n2) {
            throw new EOFException("reached end of stream after reading " + read + " bytes; " + n2 + " bytes expected");
        }
    }
    
    public static void skipFully(final InputStream inputStream, long n) throws IOException {
        final long n2 = n;
        while (n > 0L) {
            final long skip = inputStream.skip(n);
            if (skip == 0L) {
                if (inputStream.read() == -1) {
                    throw new EOFException("reached end of stream after skipping " + (n2 - n) + " bytes; " + n2 + " bytes expected");
                }
                --n;
            }
            else {
                n -= skip;
            }
        }
    }
    
    @Deprecated
    public static Object readBytes(final InputSupplier inputSupplier, final ByteProcessor byteProcessor) throws IOException {
        Preconditions.checkNotNull(inputSupplier);
        Preconditions.checkNotNull(byteProcessor);
        final Closer create = Closer.create();
        final Object bytes = readBytes((InputStream)create.register((Closeable)inputSupplier.getInput()), byteProcessor);
        create.close();
        return bytes;
    }
    
    public static Object readBytes(final InputStream inputStream, final ByteProcessor byteProcessor) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(byteProcessor);
        final byte[] array = new byte[4096];
        int read;
        do {
            read = inputStream.read(array);
        } while (read != -1 && byteProcessor.processBytes(array, 0, read));
        return byteProcessor.getResult();
    }
    
    @Deprecated
    public static HashCode hash(final InputSupplier inputSupplier, final HashFunction hashFunction) throws IOException {
        return asByteSource(inputSupplier).hash(hashFunction);
    }
    
    public static int read(final InputStream inputStream, final byte[] array, final int n, final int n2) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(array);
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        while (0 < n2 && inputStream.read(array, n + 0, n2 - 0) != -1) {}
        return 0;
    }
    
    @Deprecated
    public static InputSupplier slice(final InputSupplier inputSupplier, final long n, final long n2) {
        return asInputSupplier(asByteSource(inputSupplier).slice(n, n2));
    }
    
    @Deprecated
    public static InputSupplier join(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return asInputSupplier(ByteSource.concat(Iterables.transform(iterable, new Function() {
            public ByteSource apply(final InputSupplier inputSupplier) {
                return ByteStreams.asByteSource(inputSupplier);
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((InputSupplier)o);
            }
        })));
    }
    
    @Deprecated
    public static InputSupplier join(final InputSupplier... array) {
        return join(Arrays.asList(array));
    }
    
    @Deprecated
    public static ByteSource asByteSource(final InputSupplier inputSupplier) {
        Preconditions.checkNotNull(inputSupplier);
        return new ByteSource(inputSupplier) {
            final InputSupplier val$supplier;
            
            @Override
            public InputStream openStream() throws IOException {
                return (InputStream)this.val$supplier.getInput();
            }
            
            @Override
            public String toString() {
                return "ByteStreams.asByteSource(" + this.val$supplier + ")";
            }
        };
    }
    
    @Deprecated
    public static ByteSink asByteSink(final OutputSupplier outputSupplier) {
        Preconditions.checkNotNull(outputSupplier);
        return new ByteSink(outputSupplier) {
            final OutputSupplier val$supplier;
            
            @Override
            public OutputStream openStream() throws IOException {
                return (OutputStream)this.val$supplier.getOutput();
            }
            
            @Override
            public String toString() {
                return "ByteStreams.asByteSink(" + this.val$supplier + ")";
            }
        };
    }
    
    static InputSupplier asInputSupplier(final ByteSource byteSource) {
        return (InputSupplier)Preconditions.checkNotNull(byteSource);
    }
    
    static OutputSupplier asOutputSupplier(final ByteSink byteSink) {
        return (OutputSupplier)Preconditions.checkNotNull(byteSink);
    }
    
    static {
        NULL_OUTPUT_STREAM = new OutputStream() {
            @Override
            public void write(final int n) {
            }
            
            @Override
            public void write(final byte[] array) {
                Preconditions.checkNotNull(array);
            }
            
            @Override
            public void write(final byte[] array, final int n, final int n2) {
                Preconditions.checkNotNull(array);
            }
            
            @Override
            public String toString() {
                return "ByteStreams.nullOutputStream()";
            }
        };
    }
    
    private static final class LimitedInputStream extends FilterInputStream
    {
        private long left;
        private long mark;
        
        LimitedInputStream(final InputStream inputStream, final long left) {
            super(inputStream);
            this.mark = -1L;
            Preconditions.checkNotNull(inputStream);
            Preconditions.checkArgument(left >= 0L, (Object)"limit must be non-negative");
            this.left = left;
        }
        
        @Override
        public int available() throws IOException {
            return (int)Math.min(this.in.available(), this.left);
        }
        
        @Override
        public synchronized void mark(final int n) {
            this.in.mark(n);
            this.mark = this.left;
        }
        
        @Override
        public int read() throws IOException {
            if (this.left == 0L) {
                return -1;
            }
            final int read = this.in.read();
            if (read != -1) {
                --this.left;
            }
            return read;
        }
        
        @Override
        public int read(final byte[] array, final int n, int n2) throws IOException {
            if (this.left == 0L) {
                return -1;
            }
            n2 = (int)Math.min(n2, this.left);
            final int read = this.in.read(array, n, n2);
            if (read != -1) {
                this.left -= read;
            }
            return read;
        }
        
        @Override
        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (this.mark == -1L) {
                throw new IOException("Mark not set");
            }
            this.in.reset();
            this.left = this.mark;
        }
        
        @Override
        public long skip(long min) throws IOException {
            min = Math.min(min, this.left);
            final long skip = this.in.skip(min);
            this.left -= skip;
            return skip;
        }
    }
    
    private static class ByteArrayDataOutputStream implements ByteArrayDataOutput
    {
        final DataOutput output;
        final ByteArrayOutputStream byteArrayOutputSteam;
        
        ByteArrayDataOutputStream(final ByteArrayOutputStream byteArrayOutputSteam) {
            this.byteArrayOutputSteam = byteArrayOutputSteam;
            this.output = new DataOutputStream(byteArrayOutputSteam);
        }
        
        @Override
        public void write(final int n) {
            this.output.write(n);
        }
        
        @Override
        public void write(final byte[] array) {
            this.output.write(array);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) {
            this.output.write(array, n, n2);
        }
        
        @Override
        public void writeBoolean(final boolean b) {
            this.output.writeBoolean(b);
        }
        
        @Override
        public void writeByte(final int n) {
            this.output.writeByte(n);
        }
        
        @Override
        public void writeBytes(final String s) {
            this.output.writeBytes(s);
        }
        
        @Override
        public void writeChar(final int n) {
            this.output.writeChar(n);
        }
        
        @Override
        public void writeChars(final String s) {
            this.output.writeChars(s);
        }
        
        @Override
        public void writeDouble(final double n) {
            this.output.writeDouble(n);
        }
        
        @Override
        public void writeFloat(final float n) {
            this.output.writeFloat(n);
        }
        
        @Override
        public void writeInt(final int n) {
            this.output.writeInt(n);
        }
        
        @Override
        public void writeLong(final long n) {
            this.output.writeLong(n);
        }
        
        @Override
        public void writeShort(final int n) {
            this.output.writeShort(n);
        }
        
        @Override
        public void writeUTF(final String s) {
            this.output.writeUTF(s);
        }
        
        @Override
        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }
    }
    
    private static class ByteArrayDataInputStream implements ByteArrayDataInput
    {
        final DataInput input;
        
        ByteArrayDataInputStream(final ByteArrayInputStream byteArrayInputStream) {
            this.input = new DataInputStream(byteArrayInputStream);
        }
        
        @Override
        public void readFully(final byte[] array) {
            this.input.readFully(array);
        }
        
        @Override
        public void readFully(final byte[] array, final int n, final int n2) {
            this.input.readFully(array, n, n2);
        }
        
        @Override
        public int skipBytes(final int n) {
            return this.input.skipBytes(n);
        }
        
        @Override
        public boolean readBoolean() {
            return this.input.readBoolean();
        }
        
        @Override
        public byte readByte() {
            return this.input.readByte();
        }
        
        @Override
        public int readUnsignedByte() {
            return this.input.readUnsignedByte();
        }
        
        @Override
        public short readShort() {
            return this.input.readShort();
        }
        
        @Override
        public int readUnsignedShort() {
            return this.input.readUnsignedShort();
        }
        
        @Override
        public char readChar() {
            return this.input.readChar();
        }
        
        @Override
        public int readInt() {
            return this.input.readInt();
        }
        
        @Override
        public long readLong() {
            return this.input.readLong();
        }
        
        @Override
        public float readFloat() {
            return this.input.readFloat();
        }
        
        @Override
        public double readDouble() {
            return this.input.readDouble();
        }
        
        @Override
        public String readLine() {
            return this.input.readLine();
        }
        
        @Override
        public String readUTF() {
            return this.input.readUTF();
        }
    }
    
    private static final class FastByteArrayOutputStream extends ByteArrayOutputStream
    {
        private FastByteArrayOutputStream() {
        }
        
        void writeTo(final byte[] array, final int n) {
            System.arraycopy(this.buf, 0, array, n, this.count);
        }
        
        FastByteArrayOutputStream(final ByteStreams$1 outputStream) {
            this();
        }
    }
}
