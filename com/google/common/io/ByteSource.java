package com.google.common.io;

import java.nio.charset.*;
import com.google.common.annotations.*;
import com.google.common.hash.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.io.*;

public abstract class ByteSource implements InputSupplier
{
    private static final int BUF_SIZE = 4096;
    private static final byte[] countBuffer;
    
    protected ByteSource() {
    }
    
    public CharSource asCharSource(final Charset charset) {
        return new AsCharSource(charset, null);
    }
    
    public abstract InputStream openStream() throws IOException;
    
    @Deprecated
    @Override
    public final InputStream getInput() throws IOException {
        return this.openStream();
    }
    
    public InputStream openBufferedStream() throws IOException {
        final InputStream openStream = this.openStream();
        return (openStream instanceof BufferedInputStream) ? openStream : new BufferedInputStream(openStream);
    }
    
    public ByteSource slice(final long n, final long n2) {
        return new SlicedByteSource(n, n2, null);
    }
    
    public boolean isEmpty() throws IOException {
        final Closer create = Closer.create();
        final boolean b = ((InputStream)create.register(this.openStream())).read() == -1;
        create.close();
        return b;
    }
    
    public long size() throws IOException {
        final Closer create = Closer.create();
        final long countBySkipping = this.countBySkipping((InputStream)create.register(this.openStream()));
        create.close();
        return countBySkipping;
    }
    
    private long countBySkipping(final InputStream inputStream) throws IOException {
        long n = 0L;
        while (true) {
            final long skip = inputStream.skip(Math.min(inputStream.available(), Integer.MAX_VALUE));
            if (skip <= 0L) {
                if (inputStream.read() == -1) {
                    return n;
                }
                if (n == 0L && inputStream.available() == 0) {
                    throw new IOException();
                }
                ++n;
            }
            else {
                n += skip;
            }
        }
    }
    
    private long countByReading(final InputStream inputStream) throws IOException {
        long n = 0L;
        long n2;
        while ((n2 = inputStream.read(ByteSource.countBuffer)) != -1L) {
            n += n2;
        }
        return n;
    }
    
    public long copyTo(final OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(outputStream);
        final Closer create = Closer.create();
        final long copy = ByteStreams.copy((InputStream)create.register(this.openStream()), outputStream);
        create.close();
        return copy;
    }
    
    public long copyTo(final ByteSink byteSink) throws IOException {
        Preconditions.checkNotNull(byteSink);
        final Closer create = Closer.create();
        final long copy = ByteStreams.copy((InputStream)create.register(this.openStream()), (OutputStream)create.register(byteSink.openStream()));
        create.close();
        return copy;
    }
    
    public byte[] read() throws IOException {
        final Closer create = Closer.create();
        final byte[] byteArray = ByteStreams.toByteArray((InputStream)create.register(this.openStream()));
        create.close();
        return byteArray;
    }
    
    @Beta
    public Object read(final ByteProcessor byteProcessor) throws IOException {
        Preconditions.checkNotNull(byteProcessor);
        final Closer create = Closer.create();
        final Object bytes = ByteStreams.readBytes((InputStream)create.register(this.openStream()), byteProcessor);
        create.close();
        return bytes;
    }
    
    public HashCode hash(final HashFunction hashFunction) throws IOException {
        final Hasher hasher = hashFunction.newHasher();
        this.copyTo(Funnels.asOutputStream(hasher));
        return hasher.hash();
    }
    
    public boolean contentEquals(final ByteSource byteSource) throws IOException {
        Preconditions.checkNotNull(byteSource);
        final byte[] array = new byte[4096];
        final byte[] array2 = new byte[4096];
        final Closer create = Closer.create();
        final InputStream inputStream = (InputStream)create.register(this.openStream());
        final InputStream inputStream2 = (InputStream)create.register(byteSource.openStream());
        while (true) {
            final int read = ByteStreams.read(inputStream, array, 0, 4096);
            if (read != ByteStreams.read(inputStream2, array2, 0, 4096) || !Arrays.equals(array, array2)) {
                create.close();
                return true;
            }
            if (read != 4096) {
                create.close();
                return true;
            }
        }
    }
    
    public static ByteSource concat(final Iterable iterable) {
        return new ConcatenatedByteSource(iterable);
    }
    
    public static ByteSource concat(final Iterator iterator) {
        return concat(ImmutableList.copyOf(iterator));
    }
    
    public static ByteSource concat(final ByteSource... array) {
        return concat(ImmutableList.copyOf(array));
    }
    
    public static ByteSource wrap(final byte[] array) {
        return new ByteArrayByteSource(array);
    }
    
    public static ByteSource empty() {
        return EmptyByteSource.access$200();
    }
    
    @Override
    public Object getInput() throws IOException {
        return this.getInput();
    }
    
    static {
        countBuffer = new byte[4096];
    }
    
    private static final class ConcatenatedByteSource extends ByteSource
    {
        private final Iterable sources;
        
        ConcatenatedByteSource(final Iterable iterable) {
            this.sources = (Iterable)Preconditions.checkNotNull(iterable);
        }
        
        @Override
        public InputStream openStream() throws IOException {
            return new MultiInputStream(this.sources.iterator());
        }
        
        @Override
        public boolean isEmpty() throws IOException {
            final Iterator<ByteSource> iterator = this.sources.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public long size() throws IOException {
            long n = 0L;
            final Iterator<ByteSource> iterator = this.sources.iterator();
            while (iterator.hasNext()) {
                n += iterator.next().size();
            }
            return n;
        }
        
        @Override
        public String toString() {
            return "ByteSource.concat(" + this.sources + ")";
        }
        
        @Override
        public Object getInput() throws IOException {
            return super.getInput();
        }
    }
    
    private static final class EmptyByteSource extends ByteArrayByteSource
    {
        private static final EmptyByteSource INSTANCE;
        
        private EmptyByteSource() {
            super(new byte[0]);
        }
        
        @Override
        public CharSource asCharSource(final Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }
        
        @Override
        public byte[] read() {
            return this.bytes;
        }
        
        @Override
        public String toString() {
            return "ByteSource.empty()";
        }
        
        static EmptyByteSource access$200() {
            return EmptyByteSource.INSTANCE;
        }
        
        static {
            INSTANCE = new EmptyByteSource();
        }
    }
    
    private static class ByteArrayByteSource extends ByteSource
    {
        protected final byte[] bytes;
        
        protected ByteArrayByteSource(final byte[] array) {
            this.bytes = (byte[])Preconditions.checkNotNull(array);
        }
        
        @Override
        public InputStream openStream() {
            return new ByteArrayInputStream(this.bytes);
        }
        
        @Override
        public InputStream openBufferedStream() throws IOException {
            return this.openStream();
        }
        
        @Override
        public boolean isEmpty() {
            return this.bytes.length == 0;
        }
        
        @Override
        public long size() {
            return this.bytes.length;
        }
        
        @Override
        public byte[] read() {
            return this.bytes.clone();
        }
        
        @Override
        public long copyTo(final OutputStream outputStream) throws IOException {
            outputStream.write(this.bytes);
            return this.bytes.length;
        }
        
        @Override
        public Object read(final ByteProcessor byteProcessor) throws IOException {
            byteProcessor.processBytes(this.bytes, 0, this.bytes.length);
            return byteProcessor.getResult();
        }
        
        @Override
        public HashCode hash(final HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.bytes);
        }
        
        @Override
        public String toString() {
            return "ByteSource.wrap(" + Ascii.truncate(BaseEncoding.base16().encode(this.bytes), 30, "...") + ")";
        }
        
        @Override
        public Object getInput() throws IOException {
            return super.getInput();
        }
    }
    
    private final class SlicedByteSource extends ByteSource
    {
        private final long offset;
        private final long length;
        final ByteSource this$0;
        
        private SlicedByteSource(final ByteSource this$0, final long offset, final long length) {
            this.this$0 = this$0;
            Preconditions.checkArgument(offset >= 0L, "offset (%s) may not be negative", offset);
            Preconditions.checkArgument(length >= 0L, "length (%s) may not be negative", length);
            this.offset = offset;
            this.length = length;
        }
        
        @Override
        public InputStream openStream() throws IOException {
            return this.sliceStream(this.this$0.openStream());
        }
        
        @Override
        public InputStream openBufferedStream() throws IOException {
            return this.sliceStream(this.this$0.openBufferedStream());
        }
        
        private InputStream sliceStream(final InputStream inputStream) throws IOException {
            if (this.offset > 0L) {
                ByteStreams.skipFully(inputStream, this.offset);
            }
            return ByteStreams.limit(inputStream, this.length);
        }
        
        @Override
        public ByteSource slice(final long n, final long n2) {
            Preconditions.checkArgument(n >= 0L, "offset (%s) may not be negative", n);
            Preconditions.checkArgument(n2 >= 0L, "length (%s) may not be negative", n2);
            return this.this$0.slice(this.offset + n, Math.min(n2, this.length - n));
        }
        
        @Override
        public boolean isEmpty() throws IOException {
            return this.length == 0L || super.isEmpty();
        }
        
        @Override
        public String toString() {
            return this.this$0.toString() + ".slice(" + this.offset + ", " + this.length + ")";
        }
        
        @Override
        public Object getInput() throws IOException {
            return super.getInput();
        }
        
        SlicedByteSource(final ByteSource byteSource, final long n, final long n2, final ByteSource$1 object) {
            this(byteSource, n, n2);
        }
    }
    
    private final class AsCharSource extends CharSource
    {
        private final Charset charset;
        final ByteSource this$0;
        
        private AsCharSource(final ByteSource this$0, final Charset charset) {
            this.this$0 = this$0;
            this.charset = (Charset)Preconditions.checkNotNull(charset);
        }
        
        @Override
        public Reader openStream() throws IOException {
            return new InputStreamReader(this.this$0.openStream(), this.charset);
        }
        
        @Override
        public String toString() {
            return this.this$0.toString() + ".asCharSource(" + this.charset + ")";
        }
        
        AsCharSource(final ByteSource byteSource, final Charset charset, final ByteSource$1 object) {
            this(byteSource, charset);
        }
    }
}
