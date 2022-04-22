package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.http.impl.*;
import org.apache.http.config.*;
import org.apache.http.io.*;

@Immutable
public class DefaultHttpResponseParserFactory implements HttpMessageParserFactory
{
    public static final DefaultHttpResponseParserFactory INSTANCE;
    private final LineParser lineParser;
    private final HttpResponseFactory responseFactory;
    
    public DefaultHttpResponseParserFactory(final LineParser lineParser, final HttpResponseFactory httpResponseFactory) {
        this.lineParser = ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE);
        this.responseFactory = ((httpResponseFactory != null) ? httpResponseFactory : DefaultHttpResponseFactory.INSTANCE);
    }
    
    public DefaultHttpResponseParserFactory() {
        this(null, null);
    }
    
    public HttpMessageParser create(final SessionInputBuffer sessionInputBuffer, final MessageConstraints messageConstraints) {
        return new DefaultHttpResponseParser(sessionInputBuffer, this.lineParser, this.responseFactory, messageConstraints);
    }
    
    static {
        INSTANCE = new DefaultHttpResponseParserFactory();
    }
}
