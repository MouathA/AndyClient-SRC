package com.google.common.io;

import com.google.common.base.*;
import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible(emulated = true)
final class GwtWorkarounds
{
    private GwtWorkarounds() {
    }
    
    @GwtIncompatible("Reader")
    static CharInput asCharInput(final Reader reader) {
        Preconditions.checkNotNull(reader);
        return new CharInput(reader) {
            final Reader val$reader;
            
            @Override
            public int read() throws IOException {
                return this.val$reader.read();
            }
            
            @Override
            public void close() throws IOException {
                this.val$reader.close();
            }
        };
    }
    
    static CharInput asCharInput(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new CharInput(charSequence) {
            int index = 0;
            final CharSequence val$chars;
            
            @Override
            public int read() {
                if (this.index < this.val$chars.length()) {
                    return this.val$chars.charAt(this.index++);
                }
                return -1;
            }
            
            @Override
            public void close() {
                this.index = this.val$chars.length();
            }
        };
    }
    
    @GwtIncompatible("InputStream")
    static InputStream asInputStream(final ByteInput byteInput) {
        Preconditions.checkNotNull(byteInput);
        return new InputStream(byteInput) {
            final ByteInput val$input;
            
            @Override
            public int read() throws IOException {
                return this.val$input.read();
            }
            
            @Override
            public int read(final byte[] array, final int n, final int n2) throws IOException {
                Preconditions.checkNotNull(array);
                Preconditions.checkPositionIndexes(n, n + n2, array.length);
                if (n2 == 0) {
                    return 0;
                }
                final int read = this.read();
                if (read == -1) {
                    return -1;
                }
                array[n] = (byte)read;
                while (1 < n2) {
                    final int read2 = this.read();
                    if (read2 == -1) {
                        return 1;
                    }
                    array[n + 1] = (byte)read2;
                    int n3 = 0;
                    ++n3;
                }
                return n2;
            }
            
            @Override
            public void close() throws IOException {
                this.val$input.close();
            }
        };
    }
    
    @GwtIncompatible("OutputStream")
    static OutputStream asOutputStream(final ByteOutput byteOutput) {
        Preconditions.checkNotNull(byteOutput);
        return new OutputStream(byteOutput) {
            final ByteOutput val$output;
            
            @Override
            public void write(final int n) throws IOException {
                this.val$output.write((byte)n);
            }
            
            @Override
            public void flush() throws IOException {
                this.val$output.flush();
            }
            
            @Override
            public void close() throws IOException {
                this.val$output.close();
            }
        };
    }
    
    @GwtIncompatible("Writer")
    static CharOutput asCharOutput(final Writer writer) {
        Preconditions.checkNotNull(writer);
        return new CharOutput(writer) {
            final Writer val$writer;
            
            @Override
            public void write(final char c) throws IOException {
                this.val$writer.append(c);
            }
            
            @Override
            public void flush() throws IOException {
                this.val$writer.flush();
            }
            
            @Override
            public void close() throws IOException {
                this.val$writer.close();
            }
        };
    }
    
    static CharOutput stringBuilderOutput(final int n) {
        return new CharOutput(new StringBuilder(n)) {
            final StringBuilder val$builder;
            
            @Override
            public void write(final char c) {
                this.val$builder.append(c);
            }
            
            @Override
            public void flush() {
            }
            
            @Override
            public void close() {
            }
            
            @Override
            public String toString() {
                return this.val$builder.toString();
            }
        };
    }
    
    interface CharOutput
    {
        void write(final char p0) throws IOException;
        
        void flush() throws IOException;
        
        void close() throws IOException;
    }
    
    interface ByteOutput
    {
        void write(final byte p0) throws IOException;
        
        void flush() throws IOException;
        
        void close() throws IOException;
    }
    
    interface ByteInput
    {
        int read() throws IOException;
        
        void close() throws IOException;
    }
    
    interface CharInput
    {
        int read() throws IOException;
        
        void close() throws IOException;
    }
}
