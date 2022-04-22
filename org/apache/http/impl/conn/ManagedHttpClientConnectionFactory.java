package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import java.util.concurrent.atomic.*;
import org.apache.http.io.*;
import org.apache.commons.logging.*;
import org.apache.http.impl.io.*;
import org.apache.http.conn.routing.*;
import org.apache.http.config.*;
import org.apache.http.conn.*;
import org.apache.http.entity.*;
import java.nio.charset.*;
import org.apache.http.*;

@Immutable
public class ManagedHttpClientConnectionFactory implements HttpConnectionFactory
{
    private static final AtomicLong COUNTER;
    public static final ManagedHttpClientConnectionFactory INSTANCE;
    private final Log log;
    private final Log headerlog;
    private final Log wirelog;
    private final HttpMessageWriterFactory requestWriterFactory;
    private final HttpMessageParserFactory responseParserFactory;
    
    public ManagedHttpClientConnectionFactory(final HttpMessageWriterFactory httpMessageWriterFactory, final HttpMessageParserFactory httpMessageParserFactory) {
        this.log = LogFactory.getLog(DefaultManagedHttpClientConnection.class);
        this.headerlog = LogFactory.getLog("org.apache.http.headers");
        this.wirelog = LogFactory.getLog("org.apache.http.wire");
        this.requestWriterFactory = ((httpMessageWriterFactory != null) ? httpMessageWriterFactory : DefaultHttpRequestWriterFactory.INSTANCE);
        this.responseParserFactory = ((httpMessageParserFactory != null) ? httpMessageParserFactory : DefaultHttpResponseParserFactory.INSTANCE);
    }
    
    public ManagedHttpClientConnectionFactory(final HttpMessageParserFactory httpMessageParserFactory) {
        this(null, httpMessageParserFactory);
    }
    
    public ManagedHttpClientConnectionFactory() {
        this(null, null);
    }
    
    public ManagedHttpClientConnection create(final HttpRoute httpRoute, final ConnectionConfig connectionConfig) {
        final ConnectionConfig connectionConfig2 = (connectionConfig != null) ? connectionConfig : ConnectionConfig.DEFAULT;
        CharsetDecoder decoder = null;
        CharsetEncoder encoder = null;
        final Charset charset = connectionConfig2.getCharset();
        final CodingErrorAction codingErrorAction = (connectionConfig2.getMalformedInputAction() != null) ? connectionConfig2.getMalformedInputAction() : CodingErrorAction.REPORT;
        final CodingErrorAction codingErrorAction2 = (connectionConfig2.getUnmappableInputAction() != null) ? connectionConfig2.getUnmappableInputAction() : CodingErrorAction.REPORT;
        if (charset != null) {
            decoder = charset.newDecoder();
            decoder.onMalformedInput(codingErrorAction);
            decoder.onUnmappableCharacter(codingErrorAction2);
            encoder = charset.newEncoder();
            encoder.onMalformedInput(codingErrorAction);
            encoder.onUnmappableCharacter(codingErrorAction2);
        }
        return new LoggingManagedHttpClientConnection("http-outgoing-" + Long.toString(ManagedHttpClientConnectionFactory.COUNTER.getAndIncrement()), this.log, this.headerlog, this.wirelog, connectionConfig2.getBufferSize(), connectionConfig2.getFragmentSizeHint(), decoder, encoder, connectionConfig2.getMessageConstraints(), null, null, this.requestWriterFactory, this.responseParserFactory);
    }
    
    public HttpConnection create(final Object o, final ConnectionConfig connectionConfig) {
        return this.create((HttpRoute)o, connectionConfig);
    }
    
    static {
        COUNTER = new AtomicLong();
        INSTANCE = new ManagedHttpClientConnectionFactory();
    }
}
