package org.apache.http.impl;

import org.apache.http.annotation.*;
import org.apache.http.config.*;
import org.apache.http.entity.*;
import org.apache.http.io.*;
import java.net.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class DefaultBHttpServerConnectionFactory implements HttpConnectionFactory
{
    public static final DefaultBHttpServerConnectionFactory INSTANCE;
    private final ConnectionConfig cconfig;
    private final ContentLengthStrategy incomingContentStrategy;
    private final ContentLengthStrategy outgoingContentStrategy;
    private final HttpMessageParserFactory requestParserFactory;
    private final HttpMessageWriterFactory responseWriterFactory;
    
    public DefaultBHttpServerConnectionFactory(final ConnectionConfig connectionConfig, final ContentLengthStrategy incomingContentStrategy, final ContentLengthStrategy outgoingContentStrategy, final HttpMessageParserFactory requestParserFactory, final HttpMessageWriterFactory responseWriterFactory) {
        this.cconfig = ((connectionConfig != null) ? connectionConfig : ConnectionConfig.DEFAULT);
        this.incomingContentStrategy = incomingContentStrategy;
        this.outgoingContentStrategy = outgoingContentStrategy;
        this.requestParserFactory = requestParserFactory;
        this.responseWriterFactory = responseWriterFactory;
    }
    
    public DefaultBHttpServerConnectionFactory(final ConnectionConfig connectionConfig, final HttpMessageParserFactory httpMessageParserFactory, final HttpMessageWriterFactory httpMessageWriterFactory) {
        this(connectionConfig, null, null, httpMessageParserFactory, httpMessageWriterFactory);
    }
    
    public DefaultBHttpServerConnectionFactory(final ConnectionConfig connectionConfig) {
        this(connectionConfig, null, null, null, null);
    }
    
    public DefaultBHttpServerConnectionFactory() {
        this(null, null, null, null, null);
    }
    
    public DefaultBHttpServerConnection createConnection(final Socket socket) throws IOException {
        final DefaultBHttpServerConnection defaultBHttpServerConnection = new DefaultBHttpServerConnection(this.cconfig.getBufferSize(), this.cconfig.getFragmentSizeHint(), ConnSupport.createDecoder(this.cconfig), ConnSupport.createEncoder(this.cconfig), this.cconfig.getMessageConstraints(), this.incomingContentStrategy, this.outgoingContentStrategy, this.requestParserFactory, this.responseWriterFactory);
        defaultBHttpServerConnection.bind(socket);
        return defaultBHttpServerConnection;
    }
    
    public HttpConnection createConnection(final Socket socket) throws IOException {
        return this.createConnection(socket);
    }
    
    static {
        INSTANCE = new DefaultBHttpServerConnectionFactory();
    }
}
