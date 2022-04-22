package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.message.*;
import org.apache.http.io.*;

@Immutable
public class DefaultHttpRequestWriterFactory implements HttpMessageWriterFactory
{
    public static final DefaultHttpRequestWriterFactory INSTANCE;
    private final LineFormatter lineFormatter;
    
    public DefaultHttpRequestWriterFactory(final LineFormatter lineFormatter) {
        this.lineFormatter = ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE);
    }
    
    public DefaultHttpRequestWriterFactory() {
        this(null);
    }
    
    public HttpMessageWriter create(final SessionOutputBuffer sessionOutputBuffer) {
        return new DefaultHttpRequestWriter(sessionOutputBuffer, this.lineFormatter);
    }
    
    static {
        INSTANCE = new DefaultHttpRequestWriterFactory();
    }
}
