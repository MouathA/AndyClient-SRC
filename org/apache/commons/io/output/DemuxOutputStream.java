package org.apache.commons.io.output;

import java.io.*;

public class DemuxOutputStream extends OutputStream
{
    private final InheritableThreadLocal m_streams;
    
    public DemuxOutputStream() {
        this.m_streams = new InheritableThreadLocal();
    }
    
    public OutputStream bindStream(final OutputStream outputStream) {
        final OutputStream outputStream2 = (OutputStream)this.m_streams.get();
        this.m_streams.set(outputStream);
        return outputStream2;
    }
    
    @Override
    public void close() throws IOException {
        final OutputStream outputStream = (OutputStream)this.m_streams.get();
        if (null != outputStream) {
            outputStream.close();
        }
    }
    
    @Override
    public void flush() throws IOException {
        final OutputStream outputStream = (OutputStream)this.m_streams.get();
        if (null != outputStream) {
            outputStream.flush();
        }
    }
    
    @Override
    public void write(final int n) throws IOException {
        final OutputStream outputStream = (OutputStream)this.m_streams.get();
        if (null != outputStream) {
            outputStream.write(n);
        }
    }
}
