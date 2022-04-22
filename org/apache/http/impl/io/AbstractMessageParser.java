package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.config.*;
import org.apache.http.params.*;
import org.apache.http.message.*;
import java.util.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.*;

@NotThreadSafe
public abstract class AbstractMessageParser implements HttpMessageParser
{
    private static final int HEAD_LINE = 0;
    private static final int HEADERS = 1;
    private final SessionInputBuffer sessionBuffer;
    private final MessageConstraints messageConstraints;
    private final List headerLines;
    protected final LineParser lineParser;
    private int state;
    private HttpMessage message;
    
    @Deprecated
    public AbstractMessageParser(final SessionInputBuffer sessionBuffer, final LineParser lineParser, final HttpParams httpParams) {
        Args.notNull(sessionBuffer, "Session input buffer");
        Args.notNull(httpParams, "HTTP parameters");
        this.sessionBuffer = sessionBuffer;
        this.messageConstraints = HttpParamConfig.getMessageConstraints(httpParams);
        this.lineParser = ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE);
        this.headerLines = new ArrayList();
        this.state = 0;
    }
    
    public AbstractMessageParser(final SessionInputBuffer sessionInputBuffer, final LineParser lineParser, final MessageConstraints messageConstraints) {
        this.sessionBuffer = (SessionInputBuffer)Args.notNull(sessionInputBuffer, "Session input buffer");
        this.lineParser = ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE);
        this.messageConstraints = ((messageConstraints != null) ? messageConstraints : MessageConstraints.DEFAULT);
        this.headerLines = new ArrayList();
        this.state = 0;
    }
    
    public static Header[] parseHeaders(final SessionInputBuffer sessionInputBuffer, final int n, final int n2, final LineParser lineParser) throws HttpException, IOException {
        return parseHeaders(sessionInputBuffer, n, n2, (lineParser != null) ? lineParser : BasicLineParser.INSTANCE, new ArrayList());
    }
    
    public static Header[] parseHeaders(final SessionInputBuffer sessionInputBuffer, final int n, final int n2, final LineParser lineParser, final List list) throws HttpException, IOException {
        Args.notNull(sessionInputBuffer, "Session input buffer");
        Args.notNull(lineParser, "Line parser");
        Args.notNull(list, "Header line list");
        CharArrayBuffer charArrayBuffer = null;
        CharArrayBuffer charArrayBuffer2 = null;
        while (true) {
            if (charArrayBuffer == null) {
                charArrayBuffer = new CharArrayBuffer(64);
            }
            else {
                charArrayBuffer.clear();
            }
            int n3 = 0;
            if (sessionInputBuffer.readLine(charArrayBuffer) == -1 || charArrayBuffer.length() < 1) {
                final Header[] array = new Header[list.size()];
                while (0 < list.size()) {
                    array[0] = lineParser.parseHeader(list.get(0));
                    ++n3;
                }
                return array;
            }
            if ((charArrayBuffer.charAt(0) == ' ' || charArrayBuffer.charAt(0) == '\t') && charArrayBuffer2 != null) {
                while (0 < charArrayBuffer.length()) {
                    final char char1 = charArrayBuffer.charAt(0);
                    if (char1 != ' ' && char1 != '\t') {
                        break;
                    }
                    ++n3;
                }
                if (n2 > 0 && charArrayBuffer2.length() + 1 + charArrayBuffer.length() - 0 > n2) {
                    throw new MessageConstraintException("Maximum line length limit exceeded");
                }
                charArrayBuffer2.append(' ');
                charArrayBuffer2.append(charArrayBuffer, 0, charArrayBuffer.length() - 0);
            }
            else {
                list.add(charArrayBuffer);
                charArrayBuffer2 = charArrayBuffer;
                charArrayBuffer = null;
            }
            if (n > 0 && list.size() >= n) {
                throw new MessageConstraintException("Maximum header count exceeded");
            }
        }
    }
    
    protected abstract HttpMessage parseHead(final SessionInputBuffer p0) throws IOException, HttpException, ParseException;
    
    public HttpMessage parse() throws IOException, HttpException {
        switch (this.state) {
            case 0: {
                this.message = this.parseHead(this.sessionBuffer);
                this.state = 1;
            }
            case 1: {
                this.message.setHeaders(parseHeaders(this.sessionBuffer, this.messageConstraints.getMaxHeaderCount(), this.messageConstraints.getMaxLineLength(), this.lineParser, this.headerLines));
                final HttpMessage message = this.message;
                this.message = null;
                this.headerLines.clear();
                this.state = 0;
                return message;
            }
            default: {
                throw new IllegalStateException("Inconsistent parser state");
            }
        }
    }
}
