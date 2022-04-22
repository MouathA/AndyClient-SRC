package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.http.impl.*;
import org.apache.http.config.*;
import org.apache.http.io.*;

@Immutable
public class DefaultHttpRequestParserFactory implements HttpMessageParserFactory
{
    public static final DefaultHttpRequestParserFactory INSTANCE;
    private final LineParser lineParser;
    private final HttpRequestFactory requestFactory;
    
    public DefaultHttpRequestParserFactory(final LineParser lineParser, final HttpRequestFactory httpRequestFactory) {
        this.lineParser = ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE);
        this.requestFactory = ((httpRequestFactory != null) ? httpRequestFactory : DefaultHttpRequestFactory.INSTANCE);
    }
    
    public DefaultHttpRequestParserFactory() {
        this(null, null);
    }
    
    public HttpMessageParser create(final SessionInputBuffer sessionInputBuffer, final MessageConstraints messageConstraints) {
        return new DefaultHttpRequestParser(sessionInputBuffer, this.lineParser, this.requestFactory, messageConstraints);
    }
    
    static {
        INSTANCE = new DefaultHttpRequestParserFactory();
    }
}
