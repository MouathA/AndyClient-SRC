package org.apache.logging.log4j.core.layout;

import java.nio.charset.*;
import org.apache.logging.log4j.core.*;

public abstract class AbstractStringLayout extends AbstractLayout
{
    private final Charset charset;
    
    protected AbstractStringLayout(final Charset charset) {
        this.charset = charset;
    }
    
    @Override
    public byte[] toByteArray(final LogEvent logEvent) {
        return ((String)this.toSerializable(logEvent)).getBytes(this.charset);
    }
    
    @Override
    public String getContentType() {
        return "text/plain";
    }
    
    protected Charset getCharset() {
        return this.charset;
    }
}
