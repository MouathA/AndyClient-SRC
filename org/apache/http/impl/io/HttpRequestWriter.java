package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.io.*;
import org.apache.http.message.*;
import org.apache.http.params.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@NotThreadSafe
public class HttpRequestWriter extends AbstractMessageWriter
{
    public HttpRequestWriter(final SessionOutputBuffer sessionOutputBuffer, final LineFormatter lineFormatter, final HttpParams httpParams) {
        super(sessionOutputBuffer, lineFormatter, httpParams);
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
