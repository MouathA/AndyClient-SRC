package org.apache.http.impl;

import org.apache.http.annotation.*;
import org.apache.http.entity.*;
import org.apache.http.impl.entity.*;
import org.apache.http.params.*;
import org.apache.http.impl.io.*;
import org.apache.http.message.*;
import org.apache.http.io.*;
import org.apache.http.util.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@NotThreadSafe
public abstract class AbstractHttpServerConnection implements HttpServerConnection
{
    private final EntitySerializer entityserializer;
    private final EntityDeserializer entitydeserializer;
    private SessionInputBuffer inbuffer;
    private SessionOutputBuffer outbuffer;
    private EofSensor eofSensor;
    private HttpMessageParser requestParser;
    private HttpMessageWriter responseWriter;
    private HttpConnectionMetricsImpl metrics;
    
    public AbstractHttpServerConnection() {
        this.inbuffer = null;
        this.outbuffer = null;
        this.eofSensor = null;
        this.requestParser = null;
        this.responseWriter = null;
        this.metrics = null;
        this.entityserializer = this.createEntitySerializer();
        this.entitydeserializer = this.createEntityDeserializer();
    }
    
    protected abstract void assertOpen() throws IllegalStateException;
    
    protected EntityDeserializer createEntityDeserializer() {
        return new EntityDeserializer(new DisallowIdentityContentLengthStrategy(new LaxContentLengthStrategy(0)));
    }
    
    protected EntitySerializer createEntitySerializer() {
        return new EntitySerializer(new StrictContentLengthStrategy());
    }
    
    protected HttpRequestFactory createHttpRequestFactory() {
        return DefaultHttpRequestFactory.INSTANCE;
    }
    
    protected HttpMessageParser createRequestParser(final SessionInputBuffer sessionInputBuffer, final HttpRequestFactory httpRequestFactory, final HttpParams httpParams) {
        return new DefaultHttpRequestParser(sessionInputBuffer, null, httpRequestFactory, httpParams);
    }
    
    protected HttpMessageWriter createResponseWriter(final SessionOutputBuffer sessionOutputBuffer, final HttpParams httpParams) {
        return new HttpResponseWriter(sessionOutputBuffer, null, httpParams);
    }
    
    protected HttpConnectionMetricsImpl createConnectionMetrics(final HttpTransportMetrics httpTransportMetrics, final HttpTransportMetrics httpTransportMetrics2) {
        return new HttpConnectionMetricsImpl(httpTransportMetrics, httpTransportMetrics2);
    }
    
    protected void init(final SessionInputBuffer sessionInputBuffer, final SessionOutputBuffer sessionOutputBuffer, final HttpParams httpParams) {
        this.inbuffer = (SessionInputBuffer)Args.notNull(sessionInputBuffer, "Input session buffer");
        this.outbuffer = (SessionOutputBuffer)Args.notNull(sessionOutputBuffer, "Output session buffer");
        if (sessionInputBuffer instanceof EofSensor) {
            this.eofSensor = (EofSensor)sessionInputBuffer;
        }
        this.requestParser = this.createRequestParser(sessionInputBuffer, this.createHttpRequestFactory(), httpParams);
        this.responseWriter = this.createResponseWriter(sessionOutputBuffer, httpParams);
        this.metrics = this.createConnectionMetrics(sessionInputBuffer.getMetrics(), sessionOutputBuffer.getMetrics());
    }
    
    public HttpRequest receiveRequestHeader() throws HttpException, IOException {
        this.assertOpen();
        final HttpRequest httpRequest = (HttpRequest)this.requestParser.parse();
        this.metrics.incrementRequestCount();
        return httpRequest;
    }
    
    public void receiveRequestEntity(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        this.assertOpen();
        httpEntityEnclosingRequest.setEntity(this.entitydeserializer.deserialize(this.inbuffer, httpEntityEnclosingRequest));
    }
    
    protected void doFlush() throws IOException {
        this.outbuffer.flush();
    }
    
    public void flush() throws IOException {
        this.assertOpen();
        this.doFlush();
    }
    
    public void sendResponseHeader(final HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.assertOpen();
        this.responseWriter.write(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.metrics.incrementResponseCount();
        }
    }
    
    public void sendResponseEntity(final HttpResponse httpResponse) throws HttpException, IOException {
        if (httpResponse.getEntity() == null) {
            return;
        }
        this.entityserializer.serialize(this.outbuffer, httpResponse, httpResponse.getEntity());
    }
    
    protected boolean isEof() {
        return this.eofSensor != null && this.eofSensor.isEof();
    }
    
    public boolean isStale() {
        if (!this.isOpen()) {
            return true;
        }
        if (this.isEof()) {
            return true;
        }
        this.inbuffer.isDataAvailable(1);
        return this.isEof();
    }
    
    public HttpConnectionMetrics getMetrics() {
        return this.metrics;
    }
}
