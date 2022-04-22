package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.message.*;
import java.io.*;
import org.apache.http.*;

@NotThreadSafe
public class DefaultHttpRequestWriter extends AbstractMessageWriter
{
    public DefaultHttpRequestWriter(final SessionOutputBuffer sessionOutputBuffer, final LineFormatter lineFormatter) {
        super(sessionOutputBuffer, lineFormatter);
    }
    
    public DefaultHttpRequestWriter(final SessionOutputBuffer sessionOutputBuffer) {
        this(sessionOutputBuffer, null);
    }
    
    protected void writeHeadLine(final HttpRequest httpRequest) throws IOException {
        this.lineFormatter.formatRequestLine(this.lineBuf, httpRequest.getRequestLine());
        this.sessionBuffer.writeLine(this.lineBuf);
    }
    
    @Override
    protected void writeHeadLine(final HttpMessage httpMessage) throws IOException {
        this.writeHeadLine((HttpRequest)httpMessage);
    }
}
