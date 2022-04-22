package io.netty.handler.ssl;

import java.io.*;
import java.util.*;
import io.netty.buffer.*;
import javax.net.ssl.*;

public abstract class SslContext
{
    public static SslProvider defaultServerProvider() {
        if (OpenSsl.isAvailable()) {
            return SslProvider.OPENSSL;
        }
        return SslProvider.JDK;
    }
    
    public static SslProvider defaultClientProvider() {
        return SslProvider.JDK;
    }
    
    public static SslContext newServerContext(final File file, final File file2) throws SSLException {
        return newServerContext(null, file, file2, null, null, null, 0L, 0L);
    }
    
    public static SslContext newServerContext(final File file, final File file2, final String s) throws SSLException {
        return newServerContext(null, file, file2, s, null, null, 0L, 0L);
    }
    
    public static SslContext newServerContext(final File file, final File file2, final String s, final Iterable iterable, final Iterable iterable2, final long n, final long n2) throws SSLException {
        return newServerContext(null, file, file2, s, iterable, iterable2, n, n2);
    }
    
    public static SslContext newServerContext(final SslProvider sslProvider, final File file, final File file2) throws SSLException {
        return newServerContext(sslProvider, file, file2, null, null, null, 0L, 0L);
    }
    
    public static SslContext newServerContext(final SslProvider sslProvider, final File file, final File file2, final String s) throws SSLException {
        return newServerContext(sslProvider, file, file2, s, null, null, 0L, 0L);
    }
    
    public static SslContext newServerContext(SslProvider sslProvider, final File file, final File file2, final String s, final Iterable iterable, final Iterable iterable2, final long n, final long n2) throws SSLException {
        if (sslProvider == null) {
            sslProvider = (OpenSsl.isAvailable() ? SslProvider.OPENSSL : SslProvider.JDK);
        }
        switch (sslProvider) {
            case JDK: {
                return new JdkSslServerContext(file, file2, s, iterable, iterable2, n, n2);
            }
            case OPENSSL: {
                return new OpenSslServerContext(file, file2, s, iterable, iterable2, n, n2);
            }
            default: {
                throw new Error(sslProvider.toString());
            }
        }
    }
    
    public static SslContext newClientContext() throws SSLException {
        return newClientContext(null, null, null, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final File file) throws SSLException {
        return newClientContext(null, file, null, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, null, trustManagerFactory, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final File file, final TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, file, trustManagerFactory, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final File file, final TrustManagerFactory trustManagerFactory, final Iterable iterable, final Iterable iterable2, final long n, final long n2) throws SSLException {
        return newClientContext(null, file, trustManagerFactory, iterable, iterable2, n, n2);
    }
    
    public static SslContext newClientContext(final SslProvider sslProvider) throws SSLException {
        return newClientContext(sslProvider, null, null, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final SslProvider sslProvider, final File file) throws SSLException {
        return newClientContext(sslProvider, file, null, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final SslProvider sslProvider, final TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(sslProvider, null, trustManagerFactory, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final SslProvider sslProvider, final File file, final TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(sslProvider, file, trustManagerFactory, null, null, 0L, 0L);
    }
    
    public static SslContext newClientContext(final SslProvider sslProvider, final File file, final TrustManagerFactory trustManagerFactory, final Iterable iterable, final Iterable iterable2, final long n, final long n2) throws SSLException {
        if (sslProvider != null && sslProvider != SslProvider.JDK) {
            throw new SSLException("client context unsupported for: " + sslProvider);
        }
        return new JdkSslClientContext(file, trustManagerFactory, iterable, iterable2, n, n2);
    }
    
    SslContext() {
    }
    
    public final boolean isServer() {
        return !this.isClient();
    }
    
    public abstract boolean isClient();
    
    public abstract List cipherSuites();
    
    public abstract long sessionCacheSize();
    
    public abstract long sessionTimeout();
    
    public abstract List nextProtocols();
    
    public abstract SSLEngine newEngine(final ByteBufAllocator p0);
    
    public abstract SSLEngine newEngine(final ByteBufAllocator p0, final String p1, final int p2);
    
    public final SslHandler newHandler(final ByteBufAllocator byteBufAllocator) {
        return newHandler(this.newEngine(byteBufAllocator));
    }
    
    public final SslHandler newHandler(final ByteBufAllocator byteBufAllocator, final String s, final int n) {
        return newHandler(this.newEngine(byteBufAllocator, s, n));
    }
    
    private static SslHandler newHandler(final SSLEngine sslEngine) {
        return new SslHandler(sslEngine);
    }
}
