package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import java.nio.charset.*;
import org.apache.http.config.*;
import org.apache.http.entity.*;
import org.apache.http.io.*;
import java.net.*;
import java.io.*;
import org.apache.http.*;

@NotThreadSafe
class LoggingManagedHttpClientConnection extends DefaultManagedHttpClientConnection
{
    private final Log log;
    private final Log headerlog;
    private final Wire wire;
    
    public LoggingManagedHttpClientConnection(final String s, final Log log, final Log headerlog, final Log log2, final int n, final int n2, final CharsetDecoder charsetDecoder, final CharsetEncoder charsetEncoder, final MessageConstraints messageConstraints, final ContentLengthStrategy contentLengthStrategy, final ContentLengthStrategy contentLengthStrategy2, final HttpMessageWriterFactory httpMessageWriterFactory, final HttpMessageParserFactory httpMessageParserFactory) {
        super(s, n, n2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy, contentLengthStrategy2, httpMessageWriterFactory, httpMessageParserFactory);
        this.log = log;
        this.headerlog = headerlog;
        this.wire = new Wire(log2, s);
    }
    
    @Override
    public void close() throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.getId() + ": Close connection");
        }
        super.close();
    }
    
    @Override
    public void shutdown() throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.getId() + ": Shutdown connection");
        }
        super.shutdown();
    }
    
    @Override
    protected InputStream getSocketInputStream(final Socket socket) throws IOException {
        InputStream socketInputStream = super.getSocketInputStream(socket);
        if (this.wire.enabled()) {
            socketInputStream = new LoggingInputStream(socketInputStream, this.wire);
        }
        return socketInputStream;
    }
    
    @Override
    protected OutputStream getSocketOutputStream(final Socket socket) throws IOException {
        OutputStream socketOutputStream = super.getSocketOutputStream(socket);
        if (this.wire.enabled()) {
            socketOutputStream = new LoggingOutputStream(socketOutputStream, this.wire);
        }
        return socketOutputStream;
    }
    
    @Override
    protected void onResponseReceived(final HttpResponse httpResponse) {
        if (httpResponse != null && this.headerlog.isDebugEnabled()) {
            this.headerlog.debug(this.getId() + " << " + httpResponse.getStatusLine().toString());
            final Header[] allHeaders = httpResponse.getAllHeaders();
            while (0 < allHeaders.length) {
                this.headerlog.debug(this.getId() + " << " + allHeaders[0].toString());
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    protected void onRequestSubmitted(final HttpRequest httpRequest) {
        if (httpRequest != null && this.headerlog.isDebugEnabled()) {
            this.headerlog.debug(this.getId() + " >> " + httpRequest.getRequestLine().toString());
            final Header[] allHeaders = httpRequest.getAllHeaders();
            while (0 < allHeaders.length) {
                this.headerlog.debug(this.getId() + " >> " + allHeaders[0].toString());
                int n = 0;
                ++n;
            }
        }
    }
}
