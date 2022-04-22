package org.apache.http.impl.conn;

import org.apache.http.impl.io.*;
import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@ThreadSafe
public class DefaultResponseParser extends AbstractMessageParser
{
    private final Log log;
    private final HttpResponseFactory responseFactory;
    private final CharArrayBuffer lineBuf;
    private final int maxGarbageLines;
    
    public DefaultResponseParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpResponseFactory responseFactory, final HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(responseFactory, "Response factory");
        this.responseFactory = responseFactory;
        this.lineBuf = new CharArrayBuffer(128);
        this.maxGarbageLines = this.getMaxGarbageLines(httpParams);
    }
    
    protected int getMaxGarbageLines(final HttpParams httpParams) {
        return httpParams.getIntParameter("http.connection.max-status-line-garbage", Integer.MAX_VALUE);
    }
    
    @Override
    protected HttpMessage parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException {
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
            if (line == -1 || 0 >= this.maxGarbageLines) {
                throw new ProtocolException("The server failed to respond with a valid HTTP response");
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Garbage in response: " + this.lineBuf.toString());
            }
            int n = 0;
            ++n;
        }
    }
}
