package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.message.*;
import java.io.*;
import org.apache.http.*;

@NotThreadSafe
public class DefaultHttpResponseWriter extends AbstractMessageWriter
{
    public DefaultHttpResponseWriter(final SessionOutputBuffer sessionOutputBuffer, final LineFormatter lineFormatter) {
        super(sessionOutputBuffer, lineFormatter);
    }
    
    public DefaultHttpResponseWriter(final SessionOutputBuffer sessionOutputBuffer) {
        super(sessionOutputBuffer, null);
    }
    
    protected void writeHeadLine(final HttpResponse httpResponse) throws IOException {
        this.lineFormatter.formatStatusLine(this.lineBuf, httpResponse.getStatusLine());
        this.sessionBuffer.writeLine(this.lineBuf);
    }
    
    @Override
    protected void writeHeadLine(final HttpMessage httpMessage) throws IOException {
        this.writeHeadLine((HttpResponse)httpMessage);
    }
}
