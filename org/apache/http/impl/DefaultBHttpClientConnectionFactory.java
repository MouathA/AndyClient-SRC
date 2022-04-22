package org.apache.http.impl;

import org.apache.http.annotation.*;
import org.apache.http.config.*;
import org.apache.http.entity.*;
import org.apache.http.io.*;
import java.net.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class DefaultBHttpClientConnectionFactory implements HttpConnectionFactory
{
    public static final DefaultBHttpClientConnectionFactory INSTANCE;
    private final ConnectionConfig cconfig;
    private final ContentLengthStrategy incomingContentStrategy;
    private final ContentLengthStrategy outgoingContentStrategy;
    private final HttpMessageWriterFactory requestWriterFactory;
    private final HttpMessageParserFactory responseParserFactory;
    
    public DefaultBHttpClientConnectionFactory(final ConnectionConfig connectionConfig, final ContentLengthStrategy incomingContentStrategy, final ContentLengthStrategy outgoingContentStrategy, final HttpMessageWriterFactory requestWriterFactory, final HttpMessageParserFactory responseParserFactory) {
        this.cconfig = ((connectionConfig != null) ? connectionConfig : ConnectionConfig.DEFAULT);
        this.incomingContentStrategy = incomingContentStrategy;
        this.outgoingContentStrategy = outgoingContentStrategy;
        this.requestWriterFactory = requestWriterFactory;
        this.responseParserFactory = responseParserFactory;
    }
    
    public DefaultBHttpClientConnectionFactory(final ConnectionConfig connectionConfig, final HttpMessageWriterFactory httpMessageWriterFactory, final HttpMessageParserFactory httpMessageParserFactory) {
        this(connectionConfig, null, null, httpMessageWriterFactory, httpMessageParserFactory);
    }
    
    public DefaultBHttpClientConnectionFactory(final ConnectionConfig connectionConfig) {
        this(connectionConfig, null, null, null, null);
    }
    
    public DefaultBHttpClientConnectionFactory() {
        this(null, null, null, null, null);
    }
    
    public DefaultBHttpClientConnection createConnection(final Socket socket) throws IOException {
        final DefaultBHttpClientConnection defaultBHttpClientConnection = new DefaultBHttpClientConnection(this.cconfig.getBufferSize(), this.cconfig.getFragmentSizeHint(), ConnSupport.createDecoder(this.cconfig), ConnSupport.createEncoder(this.cconfig), this.cconfig.getMessageConstraints(), this.incomingContentStrategy, this.outgoingContentStrategy, this.requestWriterFactory, this.responseParserFactory);
        defaultBHttpClientConnection.bind(socket);
        return defaultBHttpClientConnection;
    }
    
    public HttpConnection createConnection(final Socket socket) throws IOException {
        return this.createConnection(socket);
    }
    
    static {
        INSTANCE = new DefaultBHttpClientConnectionFactory();
    }
}
