package io.netty.handler.ssl;

import io.netty.buffer.*;
import java.util.*;
import io.netty.util.internal.logging.*;
import javax.net.ssl.*;
import java.security.*;

public abstract class JdkSslContext extends SslContext
{
    private static final InternalLogger logger;
    static final String PROTOCOL = "TLS";
    static final List DEFAULT_CIPHERS;
    private final String[] cipherSuites;
    private final List unmodifiableCipherSuites;
    
    private static void addIfSupported(final String[] array, final List list, final String... array2) {
        while (0 < array2.length) {
            final String s = array2[0];
            while (0 < array.length) {
                final String s2 = array[0];
                if (s.equals(s2)) {
                    list.add(s2);
                    break;
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    JdkSslContext(final Iterable iterable) {
        this.cipherSuites = toCipherSuiteArray(iterable);
        this.unmodifiableCipherSuites = Collections.unmodifiableList((List<?>)Arrays.asList((T[])this.cipherSuites));
    }
    
    public abstract SSLContext context();
    
    public final SSLSessionContext sessionContext() {
        if (this.isServer()) {
            return this.context().getServerSessionContext();
        }
        return this.context().getClientSessionContext();
    }
    
    @Override
    public final List cipherSuites() {
        return this.unmodifiableCipherSuites;
    }
    
    @Override
    public final long sessionCacheSize() {
        return this.sessionContext().getSessionCacheSize();
    }
    
    @Override
    public final long sessionTimeout() {
        return this.sessionContext().getSessionTimeout();
    }
    
    @Override
    public final SSLEngine newEngine(final ByteBufAllocator byteBufAllocator) {
        final SSLEngine sslEngine = this.context().createSSLEngine();
        sslEngine.setEnabledCipherSuites(this.cipherSuites);
        sslEngine.setEnabledProtocols(JdkSslContext.PROTOCOLS);
        sslEngine.setUseClientMode(this.isClient());
        return this.wrapEngine(sslEngine);
    }
    
    @Override
    public final SSLEngine newEngine(final ByteBufAllocator byteBufAllocator, final String s, final int n) {
        final SSLEngine sslEngine = this.context().createSSLEngine(s, n);
        sslEngine.setEnabledCipherSuites(this.cipherSuites);
        sslEngine.setEnabledProtocols(JdkSslContext.PROTOCOLS);
        sslEngine.setUseClientMode(this.isClient());
        return this.wrapEngine(sslEngine);
    }
    
    private SSLEngine wrapEngine(final SSLEngine sslEngine) {
        if (this.nextProtocols().isEmpty()) {
            return sslEngine;
        }
        return new JettyNpnSslEngine(sslEngine, this.nextProtocols(), this.isServer());
    }
    
    private static String[] toCipherSuiteArray(final Iterable iterable) {
        if (iterable == null) {
            return JdkSslContext.DEFAULT_CIPHERS.toArray(new String[JdkSslContext.DEFAULT_CIPHERS.size()]);
        }
        final ArrayList<String> list = new ArrayList<String>();
        for (final String s : iterable) {
            if (s == null) {
                break;
            }
            list.add(s);
        }
        return list.toArray(new String[list.size()]);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
        final SSLContext instance = SSLContext.getInstance("TLS");
        instance.init(null, null, null);
        final SSLEngine sslEngine = instance.createSSLEngine();
        final String[] supportedProtocols = sslEngine.getSupportedProtocols();
        final ArrayList list = new ArrayList();
        addIfSupported(supportedProtocols, list, "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3");
        if (!list.isEmpty()) {
            JdkSslContext.PROTOCOLS = (String[])list.toArray(new String[list.size()]);
        }
        else {
            JdkSslContext.PROTOCOLS = sslEngine.getEnabledProtocols();
        }
        final String[] supportedCipherSuites = sslEngine.getSupportedCipherSuites();
        final ArrayList<Object> list2 = new ArrayList<Object>();
        addIfSupported(supportedCipherSuites, list2, "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_RC4_128_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "SSL_RSA_WITH_RC4_128_SHA", "SSL_RSA_WITH_RC4_128_MD5", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA");
        if (!list2.isEmpty()) {
            DEFAULT_CIPHERS = Collections.unmodifiableList((List<?>)list2);
        }
        else {
            DEFAULT_CIPHERS = Collections.unmodifiableList((List<?>)Arrays.asList((T[])sslEngine.getEnabledCipherSuites()));
        }
        if (JdkSslContext.logger.isDebugEnabled()) {
            JdkSslContext.logger.debug("Default protocols (JDK): {} ", Arrays.asList(JdkSslContext.PROTOCOLS));
            JdkSslContext.logger.debug("Default cipher suites (JDK): {}", JdkSslContext.DEFAULT_CIPHERS);
        }
    }
}
