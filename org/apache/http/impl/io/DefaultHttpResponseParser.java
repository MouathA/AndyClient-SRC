package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.config.*;
import org.apache.http.impl.*;
import org.apache.http.message.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.*;

@NotThreadSafe
public class DefaultHttpResponseParser extends AbstractMessageParser
{
    private final HttpResponseFactory responseFactory;
    private final CharArrayBuffer lineBuf;
    
    @Deprecated
    public DefaultHttpResponseParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpResponseFactory httpResponseFactory, final HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.responseFactory = (HttpResponseFactory)Args.notNull(httpResponseFactory, "Response factory");
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    public DefaultHttpResponseParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpResponseFactory httpResponseFactory, final MessageConstraints messageConstraints) {
        super(sessionInputBuffer, lineParser, messageConstraints);
        this.responseFactory = ((httpResponseFactory != null) ? httpResponseFactory : DefaultHttpResponseFactory.INSTANCE);
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    public DefaultHttpResponseParser(final SessionInputBuffer sessionInputBuffer, final MessageConstraints messageConstraints) {
        this(sessionInputBuffer, null, null, messageConstraints);
    }
    
    public DefaultHttpResponseParser(final SessionInputBuffer sessionInputBuffer) {
        this(sessionInputBuffer, null, null, MessageConstraints.DEFAULT);
    }
    
    @Override
    protected HttpResponse parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        this.lineBuf.clear();
        if (sessionInputBuffer.readLine(this.lineBuf) == -1) {
            throw new NoHttpResponseException("The target server failed to respond");
        }
        return this.responseFactory.newHttpResponse(this.lineParser.parseStatusLine(this.lineBuf, new ParserCursor(0, this.lineBuf.length())), null);
    }
    
    @Override
    protected HttpMessage parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        return this.parseHead(sessionInputBuffer);
    }
}
