package io.netty.handler.codec.marshalling;

import org.jboss.marshalling.*;
import io.netty.buffer.*;
import java.io.*;

class ChannelBufferByteOutput implements ByteOutput
{
    private final ByteBuf buffer;
    
    ChannelBufferByteOutput(final ByteBuf buffer) {
        this.buffer = buffer;
    }
    
    public void close() throws IOException {
    }
    
    public void flush() throws IOException {
    }
    
    public void write(final int n) throws IOException {
        this.buffer.writeByte(n);
    }
    
    public void write(final byte[] array) throws IOException {
        this.buffer.writeBytes(array);
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.buffer.writeBytes(array, n, n2);
    }
    
    ByteBuf getBuffer() {
        return this.buffer;
    }
}
