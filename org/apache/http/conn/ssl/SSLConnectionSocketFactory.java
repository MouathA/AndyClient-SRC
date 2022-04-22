package org.apache.http.conn.ssl;

import org.apache.http.conn.socket.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import javax.net.ssl.*;
import java.io.*;
import org.apache.http.protocol.*;
import javax.net.*;
import org.apache.http.*;
import java.net.*;

@ThreadSafe
public class SSLConnectionSocketFactory implements LayeredConnectionSocketFactory
{
    public static final String TLS = "TLS";
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER;
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER;
    private final SSLSocketFactory socketfactory;
    private final X509HostnameVerifier hostnameVerifier;
    private final String[] supportedProtocols;
    private final String[] supportedCipherSuites;
    
    public static SSLConnectionSocketFactory getSocketFactory() throws SSLInitializationException {
        return new SSLConnectionSocketFactory(SSLContexts.createDefault(), SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    private static String[] split(final String s) {
        if (TextUtils.isBlank(s)) {
            return null;
        }
        return s.split(" *, *");
    }
    
    public static SSLConnectionSocketFactory getSystemSocketFactory() throws SSLInitializationException {
        return new SSLConnectionSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault(), split(System.getProperty("https.protocols")), split(System.getProperty("https.cipherSuites")), SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLConnectionSocketFactory(final SSLContext sslContext) {
        this(sslContext, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLConnectionSocketFactory(final SSLContext sslContext, final X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext)Args.notNull(sslContext, "SSL context")).getSocketFactory(), null, null, x509HostnameVerifier);
    }
    
    public SSLConnectionSocketFactory(final SSLContext sslContext, final String[] array, final String[] array2, final X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext)Args.notNull(sslContext, "SSL context")).getSocketFactory(), array, array2, x509HostnameVerifier);
    }
    
    public SSLConnectionSocketFactory(final SSLSocketFactory sslSocketFactory, final X509HostnameVerifier x509HostnameVerifier) {
        this(sslSocketFactory, null, null, x509HostnameVerifier);
    }
    
    public SSLConnectionSocketFactory(final SSLSocketFactory sslSocketFactory, final String[] supportedProtocols, final String[] supportedCipherSuites, final X509HostnameVerifier x509HostnameVerifier) {
        this.socketfactory = (SSLSocketFactory)Args.notNull(sslSocketFactory, "SSL socket factory");
        this.supportedProtocols = supportedProtocols;
        this.supportedCipherSuites = supportedCipherSuites;
        this.hostnameVerifier = ((x509HostnameVerifier != null) ? x509HostnameVerifier : SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    protected void prepareSocket(final SSLSocket sslSocket) throws IOException {
    }
    
    public Socket createSocket(final HttpContext httpContext) throws IOException {
        return SocketFactory.getDefault().createSocket();
    }
    
    public Socket connectSocket(final int n, final Socket socket, final HttpHost httpHost, final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2, final HttpContext httpContext) throws IOException {
        Args.notNull(httpHost, "HTTP host");
        Args.notNull(inetSocketAddress, "Remote address");
        final Socket socket2 = (socket != null) ? socket : this.createSocket(httpContext);
        if (inetSocketAddress2 != null) {
            socket2.bind(inetSocketAddress2);
        }
        socket2.connect(inetSocketAddress, n);
        if (socket2 instanceof SSLSocket) {
            final SSLSocket sslSocket = (SSLSocket)socket2;
            sslSocket.startHandshake();
            this.verifyHostname(sslSocket, httpHost.getHostName());
            return socket2;
        }
        return this.createLayeredSocket(socket2, httpHost.getHostName(), inetSocketAddress.getPort(), httpContext);
    }
    
    public Socket createLayeredSocket(final Socket socket, final String s, final int n, final HttpContext httpContext) throws IOException {
        final SSLSocket sslSocket = (SSLSocket)this.socketfactory.createSocket(socket, s, n, true);
        if (this.supportedProtocols != null) {
            sslSocket.setEnabledProtocols(this.supportedProtocols);
        }
        if (this.supportedCipherSuites != null) {
            sslSocket.setEnabledCipherSuites(this.supportedCipherSuites);
        }
        this.prepareSocket(sslSocket);
        sslSocket.startHandshake();
        this.verifyHostname(sslSocket, s);
        return sslSocket;
    }
    
    X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }
    
    private void verifyHostname(final SSLSocket sslSocket, final String s) throws IOException {
        this.hostnameVerifier.verify(s, sslSocket);
    }
    
    static {
        ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
        BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
        STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    }
}
