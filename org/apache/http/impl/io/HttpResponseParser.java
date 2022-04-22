package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@NotThreadSafe
public class HttpResponseParser extends AbstractMessageParser
{
    private final HttpResponseFactory responseFactory;
    private final CharArrayBuffer lineBuf;
    
    public HttpResponseParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpResponseFactory httpResponseFactory, final HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.responseFactory = (HttpResponseFactory)Args.notNull(httpResponseFactory, "Response factory");
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    @Override
    protected HttpMessage parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        this.lineBuf.clear();
        if (sessionInputBuffer.readLine(this.lineBuf) == -1) {
            throw new NoHttpResponseException("The target server failed to respond");
        }
        return this.responseFactory.newHttpResponse(this.lineParser.parseStatusLine(this.lineBuf, new ParserCursor(0, this.lineBuf.length())), null);
    }
}
