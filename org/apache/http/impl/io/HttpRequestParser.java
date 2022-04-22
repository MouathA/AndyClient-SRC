package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@NotThreadSafe
public class HttpRequestParser extends AbstractMessageParser
{
    private final HttpRequestFactory requestFactory;
    private final CharArrayBuffer lineBuf;
    
    public HttpRequestParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final HttpRequestFactory httpRequestFactory, final HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.requestFactory = (HttpRequestFactory)Args.notNull(httpRequestFactory, "Request factory");
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    @Override
    protected HttpMessage parseHead(final SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        this.lineBuf.clear();
        if (sessionInputBuffer.readLine(this.lineBuf) == -1) {
            throw new ConnectionClosedException("Client closed connection");
        }
        return this.requestFactory.newHttpRequest(this.lineParser.parseRequestLine(this.lineBuf, new ParserCursor(0, this.lineBuf.length())));
    }
}
