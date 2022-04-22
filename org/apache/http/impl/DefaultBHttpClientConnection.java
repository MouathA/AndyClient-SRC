package org.apache.http.impl;

import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.config.*;
import org.apache.http.entity.*;
import org.apache.http.io.*;
import org.apache.http.impl.io.*;
import java.net.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@NotThreadSafe
public class DefaultBHttpClientConnection extends BHttpConnectionBase implements HttpClientConnection
{
    private final HttpMessageParser responseParser;
    private final HttpMessageWriter requestWriter;
    
    public DefaultBHttpClientConnection(final int n, final int n2, final CharsetDecoder charsetDecoder, final CharsetEncoder charsetEncoder, final MessageConstraints messageConstraints, final ContentLengthStrategy contentLengthStrategy, final ContentLengthStrategy contentLengthStrategy2, final HttpMessageWriterFactory httpMessageWriterFactory, final HttpMessageParserFactory httpMessageParserFactory) {
        super(n, n2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy, contentLengthStrategy2);
        this.requestWriter = ((httpMessageWriterFactory != null) ? httpMessageWriterFactory : DefaultHttpRequestWriterFactory.INSTANCE).create(this.getSessionOutputBuffer());
        this.responseParser = ((httpMessageParserFactory != null) ? httpMessageParserFactory : DefaultHttpResponseParserFactory.INSTANCE).create(this.getSessionInputBuffer(), messageConstraints);
    }
    
    public DefaultBHttpClientConnection(final int n, final CharsetDecoder charsetDecoder, final CharsetEncoder charsetEncoder, final MessageConstraints messageConstraints) {
        this(n, n, charsetDecoder, charsetEncoder, messageConstraints, null, null, null, null);
    }
    
    public DefaultBHttpClientConnection(final int n) {
        this(n, n, null, null, null, null, null, null, null);
    }
    
    protected void onResponseReceived(final HttpResponse httpResponse) {
    }
    
    protected void onRequestSubmitted(final HttpRequest httpRequest) {
    }
    
    public void bind(final Socket socket) throws IOException {
        super.bind(socket);
    }
    
    public boolean isResponseAvailable(final int n) throws IOException {
        this.ensureOpen();
        return this.awaitInput(n);
    }
    
    public void sendRequestHeader(final HttpRequest httpRequest) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        this.ensureOpen();
        this.requestWriter.write(httpRequest);
        this.onRequestSubmitted(httpRequest);
        this.incrementRequestCount();
    }
    
    public void sendRequestEntity(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        this.ensureOpen();
        final HttpEntity entity = httpEntityEnclosingRequest.getEntity();
        if (entity == null) {
            return;
        }
        final OutputStream prepareOutput = this.prepareOutput(httpEntityEnclosingRequest);
        entity.writeTo(prepareOutput);
        prepareOutput.close();
    }
    
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        this.ensureOpen();
        final HttpResponse httpResponse = (HttpResponse)this.responseParser.parse();
        this.onResponseReceived(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.incrementResponseCount();
        }
        return httpResponse;
    }
    
    public void receiveResponseEntity(final HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.ensureOpen();
        httpResponse.setEntity(this.prepareInput(httpResponse));
    }
    
    public void flush() throws IOException {
        this.ensureOpen();
        this.doFlush();
    }
}
