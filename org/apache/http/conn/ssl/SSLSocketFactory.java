package org.apache.http.conn.ssl;

import org.apache.http.conn.socket.*;
import org.apache.http.annotation.*;
import org.apache.http.conn.scheme.*;
import java.security.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.*;
import org.apache.http.params.*;
import org.apache.http.conn.*;
import javax.net.ssl.*;
import org.apache.http.util.*;
import java.net.*;

@Deprecated
@ThreadSafe
public class SSLSocketFactory implements LayeredConnectionSocketFactory, SchemeLayeredSocketFactory, LayeredSchemeSocketFactory, LayeredSocketFactory
{
    public static final String TLS = "TLS";
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER;
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER;
    private final javax.net.ssl.SSLSocketFactory socketfactory;
    private final HostNameResolver nameResolver;
    private X509HostnameVerifier hostnameVerifier;
    private final String[] supportedProtocols;
    private final String[] supportedCipherSuites;
    
    public static SSLSocketFactory getSocketFactory() throws SSLInitializationException {
        return new SSLSocketFactory(SSLContexts.createDefault(), SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    private static String[] split(final String s) {
        if (TextUtils.isBlank(s)) {
            return null;
        }
        return s.split(" *, *");
    }
    
    public static SSLSocketFactory getSystemSocketFactory() throws SSLInitializationException {
        return new SSLSocketFactory((javax.net.ssl.SSLSocketFactory)javax.net.ssl.SSLSocketFactory.getDefault(), split(System.getProperty("https.protocols")), split(System.getProperty("https.cipherSuites")), SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLSocketFactory(final String s, final KeyStore keyStore, final String s2, final KeyStore keyStore2, final SecureRandom secureRandom, final HostNameResolver hostNameResolver) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().useProtocol(s).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, (char[])((s2 != null) ? s2.toCharArray() : null)).loadTrustMaterial(keyStore2).build(), hostNameResolver);
    }
    
    public SSLSocketFactory(final String s, final KeyStore keyStore, final String s2, final KeyStore keyStore2, final SecureRandom secureRandom, final TrustStrategy trustStrategy, final X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().useProtocol(s).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, (char[])((s2 != null) ? s2.toCharArray() : null)).loadTrustMaterial(keyStore2, trustStrategy).build(), x509HostnameVerifier);
    }
    
    public SSLSocketFactory(final String s, final KeyStore keyStore, final String s2, final KeyStore keyStore2, final SecureRandom secureRandom, final X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().useProtocol(s).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, (char[])((s2 != null) ? s2.toCharArray() : null)).loadTrustMaterial(keyStore2).build(), x509HostnameVerifier);
    }
    
    public SSLSocketFactory(final KeyStore keyStore, final String s, final KeyStore keyStore2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadKeyMaterial(keyStore, (char[])((s != null) ? s.toCharArray() : null)).loadTrustMaterial(keyStore2).build(), SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLSocketFactory(final KeyStore keyStore, final String s) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadKeyMaterial(keyStore, (char[])((s != null) ? s.toCharArray() : null)).build(), SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLSocketFactory(final KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadTrustMaterial(keyStore).build(), SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLSocketFactory(final TrustStrategy trustStrategy, final X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build(), x509HostnameVerifier);
    }
    
    public SSLSocketFactory(final TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build(), SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLSocketFactory(final SSLContext sslContext) {
        this(sslContext, SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }
    
    public SSLSocketFactory(final SSLContext sslContext, final HostNameResolver nameResolver) {
        this.socketfactory = sslContext.getSocketFactory();
        this.hostnameVerifier = SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.nameResolver = nameResolver;
        this.supportedProtocols = null;
        this.supportedCipherSuites = null;
    }
    
    public SSLSocketFactory(final SSLContext sslContext, final X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext)Args.notNull(sslContext, "SSL context")).getSocketFactory(), null, null, x509HostnameVerifier);
    }
    
    public SSLSocketFactory(final SSLContext sslContext, final String[] array, final String[] array2, final X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext)Args.notNull(sslContext, "SSL context")).getSocketFactory(), array, array2, x509HostnameVerifier);
    }
    
    public SSLSocketFactory(final javax.net.ssl.SSLSocketFactory sslSocketFactory, final X509HostnameVerifier x509HostnameVerifier) {
        this(sslSocketFactory, null, null, x509HostnameVerifier);
    }
    
    public SSLSocketFactory(final javax.net.ssl.SSLSocketFactory sslSocketFactory, final String[] supportedProtocols, final String[] supportedCipherSuites, final X509HostnameVerifier x509HostnameVerifier) {
        this.socketfactory = (javax.net.ssl.SSLSocketFactory)Args.notNull(sslSocketFactory, "SSL socket factory");
        this.supportedProtocols = supportedProtocols;
        this.supportedCipherSuites = supportedCipherSuites;
        this.hostnameVerifier = ((x509HostnameVerifier != null) ? x509HostnameVerifier : SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        this.nameResolver = null;
    }
    
    public Socket createSocket(final HttpParams httpParams) throws IOException {
        return this.createSocket((HttpContext)null);
    }
    
    public Socket createSocket() throws IOException {
        return this.createSocket((HttpContext)null);
    }
    
    public Socket connectSocket(final Socket socket, final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2, final HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        Args.notNull(inetSocketAddress, "Remote address");
        Args.notNull(httpParams, "HTTP parameters");
        HttpHost httpHost;
        if (inetSocketAddress instanceof HttpInetSocketAddress) {
            httpHost = ((HttpInetSocketAddress)inetSocketAddress).getHttpHost();
        }
        else {
            httpHost = new HttpHost(inetSocketAddress.getHostName(), inetSocketAddress.getPort(), "https");
        }
        return this.connectSocket(HttpConnectionParams.getConnectionTimeout(httpParams), socket, httpHost, inetSocketAddress, inetSocketAddress2, null);
    }
    
    public boolean isSecure(final Socket socket) throws IllegalArgumentException {
        Args.notNull(socket, "Socket");
        Asserts.check(socket instanceof SSLSocket, "Socket not created by this factory");
        Asserts.check(!socket.isClosed(), "Socket is closed");
        return true;
    }
    
    public Socket createLayeredSocket(final Socket socket, final String s, final int n, final HttpParams httpParams) throws IOException, UnknownHostException {
        return this.createLayeredSocket(socket, s, n, (HttpContext)null);
    }
    
    public Socket createLayeredSocket(final Socket socket, final String s, final int n, final boolean b) throws IOException, UnknownHostException {
        return this.createLayeredSocket(socket, s, n, (HttpContext)null);
    }
    
    public void setHostnameVerifier(final X509HostnameVerifier hostnameVerifier) {
        Args.notNull(hostnameVerifier, "Hostname verifier");
        this.hostnameVerifier = hostnameVerifier;
    }
    
    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }
    
    public Socket connectSocket(final Socket socket, final String s, final int n, final InetAddress inetAddress, final int n2, final HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetAddress inetAddress2;
        if (this.nameResolver != null) {
            inetAddress2 = this.nameResolver.resolve(s);
        }
        else {
            inetAddress2 = InetAddress.getByName(s);
        }
        InetSocketAddress inetSocketAddress = null;
        if (inetAddress != null || n2 > 0) {
            inetSocketAddress = new InetSocketAddress(inetAddress, (n2 > 0) ? n2 : 0);
        }
        return this.connectSocket(socket, new HttpInetSocketAddress(new HttpHost(s, n), inetAddress2, n), inetSocketAddress, httpParams);
    }
    
    public Socket createSocket(final Socket socket, final String s, final int n, final boolean b) throws IOException, UnknownHostException {
        return this.createLayeredSocket(socket, s, n, b);
    }
    
    protected void prepareSocket(final SSLSocket sslSocket) throws IOException {
    }
    
    private void internalPrepareSocket(final SSLSocket sslSocket) throws IOException {
        if (this.supportedProtocols != null) {
            sslSocket.setEnabledProtocols(this.supportedProtocols);
        }
        if (this.supportedCipherSuites != null) {
            sslSocket.setEnabledCipherSuites(this.supportedCipherSuites);
        }
        this.prepareSocket(sslSocket);
    }
    
    public Socket createSocket(final HttpContext httpContext) throws IOException {
        final SSLSocket sslSocket = (SSLSocket)this.socketfactory.createSocket();
        this.internalPrepareSocket(sslSocket);
        return sslSocket;
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
        this.internalPrepareSocket(sslSocket);
        sslSocket.startHandshake();
        this.verifyHostname(sslSocket, s);
        return sslSocket;
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
