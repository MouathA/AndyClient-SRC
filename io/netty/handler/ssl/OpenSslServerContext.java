package io.netty.handler.ssl;

import java.io.*;
import org.apache.tomcat.jni.*;
import io.netty.buffer.*;
import javax.net.ssl.*;
import io.netty.util.internal.logging.*;
import java.util.*;

public final class OpenSslServerContext extends SslContext
{
    private static final InternalLogger logger;
    private static final List DEFAULT_CIPHERS;
    private final long aprPool;
    private final List ciphers;
    private final List unmodifiableCiphers;
    private final long sessionCacheSize;
    private final long sessionTimeout;
    private final List nextProtocols;
    private final long ctx;
    private final OpenSslSessionStats stats;
    
    public OpenSslServerContext(final File file, final File file2) throws SSLException {
        this(file, file2, null);
    }
    
    public OpenSslServerContext(final File file, final File file2, final String s) throws SSLException {
        this(file, file2, s, null, null, 0L, 0L);
    }
    
    public OpenSslServerContext(final File file, final File file2, String s, Iterable default_CIPHERS, Iterable emptyList, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this.ciphers = new ArrayList();
        this.unmodifiableCiphers = Collections.unmodifiableList((List<?>)this.ciphers);
        if (file == null) {
            throw new NullPointerException("certChainFile");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("certChainFile is not a file: " + file);
        }
        if (file2 == null) {
            throw new NullPointerException("keyPath");
        }
        if (!file2.isFile()) {
            throw new IllegalArgumentException("keyPath is not a file: " + file2);
        }
        if (default_CIPHERS == null) {
            default_CIPHERS = OpenSslServerContext.DEFAULT_CIPHERS;
        }
        if (s == null) {
            s = "";
        }
        if (emptyList == null) {
            emptyList = Collections.emptyList();
        }
        for (final String s2 : default_CIPHERS) {
            if (s2 == null) {
                break;
            }
            this.ciphers.add(s2);
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        for (final String s3 : emptyList) {
            if (s3 == null) {
                break;
            }
            list.add(s3);
        }
        this.nextProtocols = Collections.unmodifiableList((List<?>)list);
        this.aprPool = Pool.create(0L);
        final Class<OpenSslServerContext> clazz = OpenSslServerContext.class;
        final Class<OpenSslServerContext> clazz2 = OpenSslServerContext.class;
        // monitorenter(clazz)
        SSLContext.setOptions(this.ctx = SSLContext.make(this.aprPool, 6, 1), 4095);
        SSLContext.setOptions(this.ctx, 16777216);
        SSLContext.setOptions(this.ctx, 4194304);
        SSLContext.setOptions(this.ctx, 524288);
        SSLContext.setOptions(this.ctx, 1048576);
        SSLContext.setOptions(this.ctx, 65536);
        final StringBuilder sb = new StringBuilder();
        final Iterator iterator3 = this.ciphers.iterator();
        while (iterator3.hasNext()) {
            sb.append(iterator3.next());
            sb.append(':');
        }
        sb.setLength(sb.length() - 1);
        SSLContext.setCipherSuite(this.ctx, sb.toString());
        SSLContext.setVerify(this.ctx, 0, 10);
        if (!SSLContext.setCertificate(this.ctx, file.getPath(), file2.getPath(), s, 0)) {
            throw new SSLException("failed to set certificate: " + file + " and " + file2 + " (" + SSL.getLastError() + ')');
        }
        if (!SSLContext.setCertificateChainFile(this.ctx, file.getPath(), true) && !SSL.getLastError().startsWith("error:00000000:")) {
            throw new SSLException("failed to set certificate chain: " + file + " (" + SSL.getLastError() + ')');
        }
        if (!list.isEmpty()) {
            final StringBuilder sb2 = new StringBuilder();
            final Iterator<String> iterator4 = list.iterator();
            while (iterator4.hasNext()) {
                sb2.append(iterator4.next());
                sb2.append(',');
            }
            sb2.setLength(sb2.length() - 1);
            SSLContext.setNextProtos(this.ctx, sb2.toString());
        }
        if (sessionCacheSize > 0L) {
            this.sessionCacheSize = sessionCacheSize;
            SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
        }
        else {
            sessionCacheSize = (this.sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480L));
            SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
        }
        if (sessionTimeout > 0L) {
            this.sessionTimeout = sessionTimeout;
            SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
        }
        else {
            sessionTimeout = (this.sessionTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300L));
            SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
        }
        // monitorexit(clazz2)
        this.stats = new OpenSslSessionStats(this.ctx);
    }
    
    @Override
    public boolean isClient() {
        return false;
    }
    
    @Override
    public List cipherSuites() {
        return this.unmodifiableCiphers;
    }
    
    @Override
    public long sessionCacheSize() {
        return this.sessionCacheSize;
    }
    
    @Override
    public long sessionTimeout() {
        return this.sessionTimeout;
    }
    
    @Override
    public List nextProtocols() {
        return this.nextProtocols;
    }
    
    public long context() {
        return this.ctx;
    }
    
    public OpenSslSessionStats stats() {
        return this.stats;
    }
    
    @Override
    public SSLEngine newEngine(final ByteBufAllocator byteBufAllocator) {
        if (this.nextProtocols.isEmpty()) {
            return new OpenSslEngine(this.ctx, byteBufAllocator, null);
        }
        return new OpenSslEngine(this.ctx, byteBufAllocator, this.nextProtocols.get(this.nextProtocols.size() - 1));
    }
    
    @Override
    public SSLEngine newEngine(final ByteBufAllocator byteBufAllocator, final String s, final int n) {
        throw new UnsupportedOperationException();
    }
    
    public void setTicketKeys(final byte[] array) {
        if (array == null) {
            throw new NullPointerException("keys");
        }
        SSLContext.setSessionTicketKeys(this.ctx, array);
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        final Class<OpenSslServerContext> clazz = OpenSslServerContext.class;
        final Class<OpenSslServerContext> clazz2 = OpenSslServerContext.class;
        // monitorenter(clazz)
        if (this.ctx != 0L) {
            SSLContext.free(this.ctx);
        }
        // monitorexit(clazz2)
        this.destroyPools();
    }
    
    private void destroyPools() {
        if (this.aprPool != 0L) {
            Pool.destroy(this.aprPool);
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(OpenSslServerContext.class);
        final ArrayList<Object> list = new ArrayList<Object>();
        Collections.addAll(list, "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-RC4-SHA", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-AES256-SHA", "AES128-GCM-SHA256", "RC4-SHA", "RC4-MD5", "AES128-SHA", "AES256-SHA", "DES-CBC3-SHA");
        DEFAULT_CIPHERS = Collections.unmodifiableList((List<?>)list);
        if (OpenSslServerContext.logger.isDebugEnabled()) {
            OpenSslServerContext.logger.debug("Default cipher suite (OpenSSL): " + list);
        }
    }
}
