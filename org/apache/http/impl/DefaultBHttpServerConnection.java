package org.apache.http.impl;

import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.config.*;
import org.apache.http.entity.*;
import org.apache.http.io.*;
import org.apache.http.impl.entity.*;
import org.apache.http.impl.io.*;
import java.net.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@NotThreadSafe
public class DefaultBHttpServerConnection extends BHttpConnectionBase implements HttpServerConnection
{
    private final HttpMessageParser requestParser;
    private final HttpMessageWriter responseWriter;
    
    public DefaultBHttpServerConnection(final int n, final int n2, final CharsetDecoder charsetDecoder, final CharsetEncoder charsetEncoder, final MessageConstraints messageConstraints, final ContentLengthStrategy contentLengthStrategy, final ContentLengthStrategy contentLengthStrategy2, final HttpMessageParserFactory httpMessageParserFactory, final HttpMessageWriterFactory httpMessageWriterFactory) {
        super(n, n2, charsetDecoder, charsetEncoder, messageConstraints, (contentLengthStrategy != null) ? contentLengthStrategy : DisallowIdentityContentLengthStrategy.INSTANCE, contentLengthStrategy2);
        this.requestParser = ((httpMessageParserFactory != null) ? httpMessageParserFactory : DefaultHttpRequestParserFactory.INSTANCE).create(this.getSessionInputBuffer(), messageConstraints);
        this.responseWriter = ((httpMessageWriterFactory != null) ? httpMessageWriterFactory : DefaultHttpResponseWriterFactory.INSTANCE).create(this.getSessionOutputBuffer());
    }
    
    public DefaultBHttpServerConnection(final int n, final CharsetDecoder charsetDecoder, final CharsetEncoder charsetEncoder, final MessageConstraints messageConstraints) {
        this(n, n, charsetDecoder, charsetEncoder, messageConstraints, null, null, null, null);
    }
    
    public DefaultBHttpServerConnection(final int n) {
        this(n, n, null, null, null, null, null, null, null);
    }
    
    protected void onRequestReceived(final HttpRequest httpRequest) {
    }
    
    protected void onResponseSubmitted(final HttpResponse httpResponse) {
    }
    
    public void bind(final Socket socket) throws IOException {
        super.bind(socket);
    }
    
    public HttpRequest receiveRequestHeader() throws HttpException, IOException {
        this.ensureOpen();
        final HttpRequest httpRequest = (HttpRequest)this.requestParser.parse();
        this.onRequestReceived(httpRequest);
        this.incrementRequestCount();
        return httpRequest;
    }
    
    public void receiveRequestEntity(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        this.ensureOpen();
        httpEntityEnclosingRequest.setEntity(this.prepareInput(httpEntityEnclosingRequest));
    }
    
    public void sendResponseHeader(final HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.ensureOpen();
        this.responseWriter.write(httpResponse);
        this.onResponseSubmitted(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.incrementResponseCount();
        }
    }
    
    public void sendResponseEntity(final HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.ensureOpen();
        final HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return;
        }
        final OutputStream prepareOutput = this.prepareOutput(httpResponse);
        entity.writeTo(prepareOutput);
        prepareOutput.close();
    }
    
    public void flush() throws IOException {
        this.ensureOpen();
        this.doFlush();
    }
}
