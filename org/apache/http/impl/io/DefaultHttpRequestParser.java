package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.config.*;
import org.apache.http.impl.*;
import org.apache.http.message.*;
import java.io.*;
import org.apache.http.*;

@NotThreadSafe
public class DefaultHttpRequestParser extends AbstractMessageParser
{
    private final HttpRequestFactory requestFactory;
    private final CharArrayBuffer lineBuf;
    
    @Deprecated
    public DefaultHttpRequestParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpRequestFactory httpRequestFactory, final HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.requestFactory = (HttpRequestFactory)Args.notNull(httpRequestFactory, "Request factory");
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    public DefaultHttpRequestParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpRequestFactory httpRequestFactory, final MessageConstraints messageConstraints) {
        super(sessionInputBuffer, lineParser, messageConstraints);
        this.requestFactory = ((httpRequestFactory != null) ? httpRequestFactory : DefaultHttpRequestFactory.INSTANCE);
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    public DefaultHttpRequestParser(final SessionInputBuffer sessionInputBuffer, final MessageConstraints messageConstraints) {
        this(sessionInputBuffer, null, null, messageConstraints);
    }
    
    public DefaultHttpRequestParser(final SessionInputBuffer sessionInputBuffer) {
        this(sessionInputBuffer, null, null, MessageConstraints.DEFAULT);
    }
    
    @Override
    protected HttpRequest parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        this.lineBuf.clear();
        if (sessionInputBuffer.readLine(this.lineBuf) == -1) {
            throw new ConnectionClosedException("Client closed connection");
        }
        return this.requestFactory.newHttpRequest(this.lineParser.parseRequestLine(this.lineBuf, new ParserCursor(0, this.lineBuf.length())));
    }
    
    @Override
    protected HttpMessage parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        return this.parseHead(sessionInputBuffer);
    }
}
