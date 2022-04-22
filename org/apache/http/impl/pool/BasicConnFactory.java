package org.apache.http.impl.pool;

import org.apache.http.pool.*;
import org.apache.http.annotation.*;
import javax.net.*;
import javax.net.ssl.*;
import org.apache.http.util.*;
import org.apache.http.params.*;
import org.apache.http.config.*;
import org.apache.http.impl.*;
import java.io.*;
import org.apache.http.*;
import java.net.*;

@Immutable
public class BasicConnFactory implements ConnFactory
{
    private final SocketFactory plainfactory;
    private final SSLSocketFactory sslfactory;
    private final int connectTimeout;
    private final SocketConfig sconfig;
    private final HttpConnectionFactory connFactory;
    
    @Deprecated
    public BasicConnFactory(final SSLSocketFactory sslfactory, final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP params");
        this.plainfactory = null;
        this.sslfactory = sslfactory;
        this.connectTimeout = httpParams.getIntParameter("http.connection.timeout", 0);
        this.sconfig = HttpParamConfig.getSocketConfig(httpParams);
        this.connFactory = new DefaultBHttpClientConnectionFactory(HttpParamConfig.getConnectionConfig(httpParams));
    }
    
    @Deprecated
    public BasicConnFactory(final HttpParams httpParams) {
        this(null, httpParams);
    }
    
    public BasicConnFactory(final SocketFactory plainfactory, final SSLSocketFactory sslfactory, final int connectTimeout, final SocketConfig socketConfig, final ConnectionConfig connectionConfig) {
        this.plainfactory = plainfactory;
        this.sslfactory = sslfactory;
        this.connectTimeout = connectTimeout;
        this.sconfig = ((socketConfig != null) ? socketConfig : SocketConfig.DEFAULT);
        this.connFactory = new DefaultBHttpClientConnectionFactory((connectionConfig != null) ? connectionConfig : ConnectionConfig.DEFAULT);
    }
    
    public BasicConnFactory(final int n, final SocketConfig socketConfig, final ConnectionConfig connectionConfig) {
        this(null, null, n, socketConfig, connectionConfig);
    }
    
    public BasicConnFactory(final SocketConfig socketConfig, final ConnectionConfig connectionConfig) {
        this(null, null, 0, socketConfig, connectionConfig);
    }
    
    public BasicConnFactory() {
        this(null, null, 0, SocketConfig.DEFAULT, ConnectionConfig.DEFAULT);
    }
    
    @Deprecated
    protected HttpClientConnection create(final Socket socket, final HttpParams httpParams) throws IOException {
        final DefaultBHttpClientConnection defaultBHttpClientConnection = new DefaultBHttpClientConnection(httpParams.getIntParameter("http.socket.buffer-size", 8192));
        defaultBHttpClientConnection.bind(socket);
        return defaultBHttpClientConnection;
    }
    
    public HttpClientConnection create(final HttpHost httpHost) throws IOException {
        final String schemeName = httpHost.getSchemeName();
        Socket socket = null;
        if ("http".equalsIgnoreCase(schemeName)) {
            socket = ((this.plainfactory != null) ? this.plainfactory.createSocket() : new Socket());
        }
        if ("https".equalsIgnoreCase(schemeName)) {
            socket = ((this.sslfactory != null) ? this.sslfactory : SSLSocketFactory.getDefault()).createSocket();
        }
        if (socket == null) {
            throw new IOException(schemeName + " scheme is not supported");
        }
        final String hostName = httpHost.getHostName();
        httpHost.getPort();
        if (443 == -1) {
            if (!httpHost.getSchemeName().equalsIgnoreCase("http")) {
                if (httpHost.getSchemeName().equalsIgnoreCase("https")) {}
            }
        }
        socket.setSoTimeout(this.sconfig.getSoTimeout());
        socket.connect(new InetSocketAddress(hostName, 443), this.connectTimeout);
        socket.setTcpNoDelay(this.sconfig.isTcpNoDelay());
        final int soLinger = this.sconfig.getSoLinger();
        if (soLinger >= 0) {
            socket.setSoLinger(soLinger > 0, soLinger);
        }
        socket.setKeepAlive(this.sconfig.isSoKeepAlive());
        return (HttpClientConnection)this.connFactory.createConnection(socket);
    }
    
    public Object create(final Object o) throws IOException {
        return this.create((HttpHost)o);
    }
}
