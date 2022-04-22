package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.message.*;
import org.apache.http.io.*;

@Immutable
public class DefaultHttpResponseWriterFactory implements HttpMessageWriterFactory
{
    public static final DefaultHttpResponseWriterFactory INSTANCE;
    private final LineFormatter lineFormatter;
    
    public DefaultHttpResponseWriterFactory(final LineFormatter lineFormatter) {
        this.lineFormatter = ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE);
    }
    
    public DefaultHttpResponseWriterFactory() {
        this(null);
    }
    
    public HttpMessageWriter create(final SessionOutputBuffer sessionOutputBuffer) {
        return new DefaultHttpResponseWriter(sessionOutputBuffer, this.lineFormatter);
    }
    
    static {
        INSTANCE = new DefaultHttpResponseWriterFactory();
    }
}
