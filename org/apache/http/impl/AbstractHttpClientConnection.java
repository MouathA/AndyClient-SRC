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
public abstract class AbstractHttpClientConnection implements HttpClientConnection
{
    private final EntitySerializer entityserializer;
    private final EntityDeserializer entitydeserializer;
    private SessionInputBuffer inbuffer;
    private SessionOutputBuffer outbuffer;
    private EofSensor eofSensor;
    private HttpMessageParser responseParser;
    private HttpMessageWriter requestWriter;
    private HttpConnectionMetricsImpl metrics;
    
    public AbstractHttpClientConnection() {
        this.inbuffer = null;
        this.outbuffer = null;
        this.eofSensor = null;
        this.responseParser = null;
        this.requestWriter = null;
        this.metrics = null;
        this.entityserializer = this.createEntitySerializer();
        this.entitydeserializer = this.createEntityDeserializer();
    }
    
    protected abstract void assertOpen() throws IllegalStateException;
    
    protected EntityDeserializer createEntityDeserializer() {
        return new EntityDeserializer(new LaxContentLengthStrategy());
    }
    
    protected EntitySerializer createEntitySerializer() {
        return new EntitySerializer(new StrictContentLengthStrategy());
    }
    
    protected HttpResponseFactory createHttpResponseFactory() {
        return DefaultHttpResponseFactory.INSTANCE;
    }
    
    protected HttpMessageParser createResponseParser(final SessionInputBuffer sessionInputBuffer, final HttpResponseFactory httpResponseFactory, final HttpParams httpParams) {
        return new DefaultHttpResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }
    
    protected HttpMessageWriter createRequestWriter(final SessionOutputBuffer sessionOutputBuffer, final HttpParams httpParams) {
        return new HttpRequestWriter(sessionOutputBuffer, null, httpParams);
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
        this.responseParser = this.createResponseParser(sessionInputBuffer, this.createHttpResponseFactory(), httpParams);
        this.requestWriter = this.createRequestWriter(sessionOutputBuffer, httpParams);
        this.metrics = this.createConnectionMetrics(sessionInputBuffer.getMetrics(), sessionOutputBuffer.getMetrics());
    }
    
    public boolean isResponseAvailable(final int n) throws IOException {
        this.assertOpen();
        return this.inbuffer.isDataAvailable(n);
    }
    
    public void sendRequestHeader(final HttpRequest httpRequest) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        this.assertOpen();
        this.requestWriter.write(httpRequest);
        this.metrics.incrementRequestCount();
    }
    
    public void sendRequestEntity(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        this.assertOpen();
        if (httpEntityEnclosingRequest.getEntity() == null) {
            return;
        }
        this.entityserializer.serialize(this.outbuffer, httpEntityEnclosingRequest, httpEntityEnclosingRequest.getEntity());
    }
    
    protected void doFlush() throws IOException {
        this.outbuffer.flush();
    }
    
    public void flush() throws IOException {
        this.assertOpen();
        this.doFlush();
    }
    
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        this.assertOpen();
        final HttpResponse httpResponse = (HttpResponse)this.responseParser.parse();
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.metrics.incrementResponseCount();
        }
        return httpResponse;
    }
    
    public void receiveResponseEntity(final HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.assertOpen();
        httpResponse.setEntity(this.entitydeserializer.deserialize(this.inbuffer, httpResponse));
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
