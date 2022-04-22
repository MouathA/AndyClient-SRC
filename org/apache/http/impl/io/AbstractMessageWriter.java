package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import java.io.*;
import org.apache.http.*;

@NotThreadSafe
public abstract class AbstractMessageWriter implements HttpMessageWriter
{
    protected final SessionOutputBuffer sessionBuffer;
    protected final CharArrayBuffer lineBuf;
    protected final LineFormatter lineFormatter;
    
    @Deprecated
    public AbstractMessageWriter(final SessionOutputBuffer sessionBuffer, final LineFormatter lineFormatter, final HttpParams httpParams) {
        Args.notNull(sessionBuffer, "Session input buffer");
        this.sessionBuffer = sessionBuffer;
        this.lineBuf = new CharArrayBuffer(128);
        this.lineFormatter = ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE);
    }
    
    public AbstractMessageWriter(final SessionOutputBuffer sessionOutputBuffer, final LineFormatter lineFormatter) {
        this.sessionBuffer = (SessionOutputBuffer)Args.notNull(sessionOutputBuffer, "Session input buffer");
        this.lineFormatter = ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE);
        this.lineBuf = new CharArrayBuffer(128);
    }
    
    protected abstract void writeHeadLine(final HttpMessage p0) throws IOException;
    
    public void write(final HttpMessage httpMessage) throws IOException, HttpException {
        Args.notNull(httpMessage, "HTTP message");
        this.writeHeadLine(httpMessage);
        final HeaderIterator headerIterator = httpMessage.headerIterator();
        while (headerIterator.hasNext()) {
            this.sessionBuffer.writeLine(this.lineFormatter.formatHeader(this.lineBuf, headerIterator.nextHeader()));
        }
        this.lineBuf.clear();
        this.sessionBuffer.writeLine(this.lineBuf);
    }
}
