package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.message.*;
import org.apache.http.params.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@NotThreadSafe
public class HttpResponseWriter extends AbstractMessageWriter
{
    public HttpResponseWriter(final SessionOutputBuffer sessionOutputBuffer, final LineFormatter lineFormatter, final HttpParams httpParams) {
        super(sessionOutputBuffer, lineFormatter, httpParams);
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
