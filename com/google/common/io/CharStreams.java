package com.google.common.io;

import com.google.common.annotations.*;
import java.nio.charset.*;
import java.nio.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import java.io.*;

@Beta
public final class CharStreams
{
    private static final int BUF_SIZE = 2048;
    
    private CharStreams() {
    }
    
    @Deprecated
    public static InputSupplier newReaderSupplier(final String s) {
        return asInputSupplier(CharSource.wrap(s));
    }
    
    @Deprecated
    public static InputSupplier newReaderSupplier(final InputSupplier inputSupplier, final Charset charset) {
        return asInputSupplier(ByteStreams.asByteSource(inputSupplier).asCharSource(charset));
    }
    
    @Deprecated
    public static OutputSupplier newWriterSupplier(final OutputSupplier outputSupplier, final Charset charset) {
        return asOutputSupplier(ByteStreams.asByteSink(outputSupplier).asCharSink(charset));
    }
    
    @Deprecated
    public static void write(final CharSequence charSequence, final OutputSupplier outputSupplier) throws IOException {
        asCharSink(outputSupplier).write(charSequence);
    }
    
    @Deprecated
    public static long copy(final InputSupplier inputSupplier, final OutputSupplier outputSupplier) throws IOException {
        return asCharSource(inputSupplier).copyTo(asCharSink(outputSupplier));
    }
    
    @Deprecated
    public static long copy(final InputSupplier inputSupplier, final Appendable appendable) throws IOException {
        return asCharSource(inputSupplier).copyTo(appendable);
    }
    
    public static long copy(final Readable readable, final Appendable appendable) throws IOException {
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(appendable);
        final CharBuffer allocate = CharBuffer.allocate(2048);
        long n = 0L;
        while (readable.read(allocate) != -1) {
            allocate.flip();
            appendable.append(allocate);
            n += allocate.remaining();
            allocate.clear();
        }
        return n;
    }
    
    public static String toString(final Readable readable) throws IOException {
        return toStringBuilder(readable).toString();
    }
    
    @Deprecated
    public static String toString(final InputSupplier inputSupplier) throws IOException {
        return asCharSource(inputSupplier).read();
    }
    
    private static StringBuilder toStringBuilder(final Readable readable) throws IOException {
        final StringBuilder sb = new StringBuilder();
        copy(readable, sb);
        return sb;
    }
    
    @Deprecated
    public static String readFirstLine(final InputSupplier inputSupplier) throws IOException {
        return asCharSource(inputSupplier).readFirstLine();
    }
    
    @Deprecated
    public static List readLines(final InputSupplier inputSupplier) throws IOException {
        final Closer create = Closer.create();
        final List lines = readLines((Readable)create.register((Closeable)inputSupplier.getInput()));
        create.close();
        return lines;
    }
    
    public static List readLines(final Readable readable) throws IOException {
        final ArrayList<String> list = new ArrayList<String>();
        String line;
        while ((line = new LineReader(readable).readLine()) != null) {
            list.add(line);
        }
        return list;
    }
    
    public static Object readLines(final Readable readable, final LineProcessor lineProcessor) throws IOException {
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(lineProcessor);
        String line;
        while ((line = new LineReader(readable).readLine()) != null && lineProcessor.processLine(line)) {}
        return lineProcessor.getResult();
    }
    
    @Deprecated
    public static Object readLines(final InputSupplier inputSupplier, final LineProcessor lineProcessor) throws IOException {
        Preconditions.checkNotNull(inputSupplier);
        Preconditions.checkNotNull(lineProcessor);
        final Closer create = Closer.create();
        final Object lines = readLines((Readable)create.register((Closeable)inputSupplier.getInput()), lineProcessor);
        create.close();
        return lines;
    }
    
    @Deprecated
    public static InputSupplier join(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return asInputSupplier(CharSource.concat(Iterables.transform(iterable, new Function() {
            public CharSource apply(final InputSupplier inputSupplier) {
                return CharStreams.asCharSource(inputSupplier);
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
    
    public static void skipFully(final Reader reader, long n) throws IOException {
        Preconditions.checkNotNull(reader);
        while (n > 0L) {
            final long skip = reader.skip(n);
            if (skip == 0L) {
                if (reader.read() == -1) {
                    throw new EOFException();
                }
                --n;
            }
            else {
                n -= skip;
            }
        }
    }
    
    public static Writer nullWriter() {
        return NullWriter.access$000();
    }
    
    public static Writer asWriter(final Appendable appendable) {
        if (appendable instanceof Writer) {
            return (Writer)appendable;
        }
        return new AppendableWriter(appendable);
    }
    
    static Reader asReader(final Readable readable) {
        Preconditions.checkNotNull(readable);
        if (readable instanceof Reader) {
            return (Reader)readable;
        }
        return new Reader(readable) {
            final Readable val$readable;
            
            @Override
            public int read(final char[] array, final int n, final int n2) throws IOException {
                return this.read(CharBuffer.wrap(array, n, n2));
            }
            
            @Override
            public int read(final CharBuffer charBuffer) throws IOException {
                return this.val$readable.read(charBuffer);
            }
            
            @Override
            public void close() throws IOException {
                if (this.val$readable instanceof Closeable) {
                    ((Closeable)this.val$readable).close();
                }
            }
        };
    }
    
    @Deprecated
    public static CharSource asCharSource(final InputSupplier inputSupplier) {
        Preconditions.checkNotNull(inputSupplier);
        return new CharSource(inputSupplier) {
            final InputSupplier val$supplier;
            
            @Override
            public Reader openStream() throws IOException {
                return CharStreams.asReader((Readable)this.val$supplier.getInput());
            }
            
            @Override
            public String toString() {
                return "CharStreams.asCharSource(" + this.val$supplier + ")";
            }
        };
    }
    
    @Deprecated
    public static CharSink asCharSink(final OutputSupplier outputSupplier) {
        Preconditions.checkNotNull(outputSupplier);
        return new CharSink(outputSupplier) {
            final OutputSupplier val$supplier;
            
            @Override
            public Writer openStream() throws IOException {
                return CharStreams.asWriter((Appendable)this.val$supplier.getOutput());
            }
            
            @Override
            public String toString() {
                return "CharStreams.asCharSink(" + this.val$supplier + ")";
            }
        };
    }
    
    static InputSupplier asInputSupplier(final CharSource charSource) {
        return (InputSupplier)Preconditions.checkNotNull(charSource);
    }
    
    static OutputSupplier asOutputSupplier(final CharSink charSink) {
        return (OutputSupplier)Preconditions.checkNotNull(charSink);
    }
    
    private static final class NullWriter extends Writer
    {
        private static final NullWriter INSTANCE;
        
        @Override
        public void write(final int n) {
        }
        
        @Override
        public void write(final char[] array) {
            Preconditions.checkNotNull(array);
        }
        
        @Override
        public void write(final char[] array, final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n + n2, array.length);
        }
        
        @Override
        public void write(final String s) {
            Preconditions.checkNotNull(s);
        }
        
        @Override
        public void write(final String s, final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n + n2, s.length());
        }
        
        @Override
        public Writer append(final CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return this;
        }
        
        @Override
        public Writer append(final CharSequence charSequence, final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, charSequence.length());
            return this;
        }
        
        @Override
        public Writer append(final char c) {
            return this;
        }
        
        @Override
        public void flush() {
        }
        
        @Override
        public void close() {
        }
        
        @Override
        public String toString() {
            return "CharStreams.nullWriter()";
        }
        
        @Override
        public Appendable append(final char c) throws IOException {
            return this.append(c);
        }
        
        @Override
        public Appendable append(final CharSequence charSequence, final int n, final int n2) throws IOException {
            return this.append(charSequence, n, n2);
        }
        
        @Override
        public Appendable append(final CharSequence charSequence) throws IOException {
            return this.append(charSequence);
        }
        
        static NullWriter access$000() {
            return NullWriter.INSTANCE;
        }
        
        static {
            INSTANCE = new NullWriter();
        }
    }
}
