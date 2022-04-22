package org.apache.http.impl.conn;

import org.apache.http.impl.io.*;
import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.commons.logging.*;
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
    private final Log log;
    private final HttpResponseFactory responseFactory;
    private final CharArrayBuffer lineBuf;
    
    @Deprecated
    public DefaultHttpResponseParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpResponseFactory responseFactory, final HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(responseFactory, "Response factory");
        this.responseFactory = responseFactory;
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    public DefaultHttpResponseParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpResponseFactory httpResponseFactory, final MessageConstraints messageConstraints) {
        super(sessionInputBuffer, lineParser, messageConstraints);
        this.log = LogFactory.getLog(this.getClass());
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
    protected HttpResponse parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException {
        while (true) {
            this.lineBuf.clear();
            final int line = sessionInputBuffer.readLine(this.lineBuf);
            if (line == -1 && !false) {
                throw new NoHttpResponseException("The target server failed to respond");
            }
            final ParserCursor parserCursor = new ParserCursor(0, this.lineBuf.length());
            if (this.lineParser.hasProtocolVersion(this.lineBuf, parserCursor)) {
                return this.responseFactory.newHttpResponse(this.lineParser.parseStatusLine(this.lineBuf, parserCursor), null);
            }
            if (line == -1 || this.reject(this.lineBuf, 0)) {
                throw new ProtocolException("The server failed to respond with a valid HTTP response");
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Garbage in response: " + this.lineBuf.toString());
            }
            int n = 0;
            ++n;
        }
    }
    
    protected boolean reject(final CharArrayBuffer charArrayBuffer, final int n) {
        return false;
    }
    
    @Override
    protected HttpMessage parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        return this.parseHead(sessionInputBuffer);
    }
}
