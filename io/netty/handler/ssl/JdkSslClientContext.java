package io.netty.handler.ssl;

import java.security.*;
import java.io.*;
import java.security.cert.*;
import java.util.*;
import io.netty.buffer.*;
import javax.net.ssl.*;

public final class JdkSslClientContext extends JdkSslContext
{
    private final SSLContext ctx;
    private final List nextProtocols;
    
    public JdkSslClientContext() throws SSLException {
        this(null, null, null, null, 0L, 0L);
    }
    
    public JdkSslClientContext(final File file) throws SSLException {
        this(file, null);
    }
    
    public JdkSslClientContext(final TrustManagerFactory trustManagerFactory) throws SSLException {
        this(null, trustManagerFactory);
    }
    
    public JdkSslClientContext(final File file, final TrustManagerFactory trustManagerFactory) throws SSLException {
        this(file, trustManagerFactory, null, null, 0L, 0L);
    }
    
    public JdkSslClientContext(final File file, TrustManagerFactory instance, final Iterable iterable, final Iterable iterable2, final long n, final long n2) throws SSLException {
        super(iterable);
        if (iterable2 != null && iterable2.iterator().hasNext()) {
            if (!JettyNpnSslEngine.isAvailable()) {
                throw new SSLException("NPN/ALPN unsupported: " + iterable2);
            }
            final ArrayList<String> list = new ArrayList<String>();
            for (final String s : iterable2) {
                if (s == null) {
                    break;
                }
                list.add(s);
            }
            this.nextProtocols = Collections.unmodifiableList((List<?>)list);
        }
        else {
            this.nextProtocols = Collections.emptyList();
        }
        if (file == null) {
            this.ctx = SSLContext.getInstance("TLS");
            if (instance == null) {
                this.ctx.init(null, null, null);
            }
            else {
                instance.init((KeyStore)null);
                this.ctx.init(null, instance.getTrustManagers(), null);
            }
        }
        else {
            final KeyStore instance2 = KeyStore.getInstance("JKS");
            instance2.load(null, null);
            final CertificateFactory instance3 = CertificateFactory.getInstance("X.509");
            final ByteBuf[] certificates;
            final ByteBuf[] array = certificates = PemReader.readCertificates(file);
            int n3 = 0;
            while (0 < certificates.length) {
                final X509Certificate x509Certificate = (X509Certificate)instance3.generateCertificate(new ByteBufInputStream(certificates[0]));
                instance2.setCertificateEntry(x509Certificate.getSubjectX500Principal().getName("RFC2253"), x509Certificate);
                ++n3;
            }
            final ByteBuf[] array2 = array;
            while (0 < array2.length) {
                array2[0].release();
                ++n3;
            }
            if (instance == null) {
                instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            }
            instance.init(instance2);
            (this.ctx = SSLContext.getInstance("TLS")).init(null, instance.getTrustManagers(), null);
        }
        final SSLSessionContext clientSessionContext = this.ctx.getClientSessionContext();
        if (n > 0L) {
            clientSessionContext.setSessionCacheSize((int)Math.min(n, 2147483647L));
        }
        if (n2 > 0L) {
            clientSessionContext.setSessionTimeout((int)Math.min(n2, 2147483647L));
        }
    }
    
    @Override
    public boolean isClient() {
        return true;
    }
    
    @Override
    public List nextProtocols() {
        return this.nextProtocols;
    }
    
    @Override
    public SSLContext context() {
        return this.ctx;
    }
}
