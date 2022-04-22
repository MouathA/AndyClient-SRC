package org.apache.commons.io.input;

import java.io.*;

public class DemuxInputStream extends InputStream
{
    private final InheritableThreadLocal m_streams;
    
    public DemuxInputStream() {
        this.m_streams = new InheritableThreadLocal();
    }
    
    public InputStream bindStream(final InputStream inputStream) {
        final InputStream inputStream2 = (InputStream)this.m_streams.get();
        this.m_streams.set(inputStream);
        return inputStream2;
    }
    
    @Override
    public void close() throws IOException {
        final InputStream inputStream = (InputStream)this.m_streams.get();
        if (null != inputStream) {
            inputStream.close();
        }
    }
    
    @Override
    public int read() throws IOException {
        final InputStream inputStream = (InputStream)this.m_streams.get();
        if (null != inputStream) {
            return inputStream.read();
        }
        return -1;
    }
}
