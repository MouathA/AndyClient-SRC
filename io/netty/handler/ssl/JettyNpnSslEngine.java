package io.netty.handler.ssl;

import java.util.*;
import org.eclipse.jetty.npn.*;
import java.nio.*;
import javax.net.ssl.*;

final class JettyNpnSslEngine extends SSLEngine
{
    private static boolean available;
    private final SSLEngine engine;
    private final JettyNpnSslSession session;
    static final boolean $assertionsDisabled;
    
    static boolean isAvailable() {
        return JettyNpnSslEngine.available;
    }
    
    private static void updateAvailability() {
        if (JettyNpnSslEngine.available) {
            return;
        }
        ClassLoader classLoader = ClassLoader.getSystemClassLoader().getParent();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        Class.forName("sun.security.ssl.NextProtoNegoExtension", true, classLoader);
        JettyNpnSslEngine.available = true;
    }
    
    JettyNpnSslEngine(final SSLEngine engine, final List list, final boolean b) {
        assert !list.isEmpty();
        this.engine = engine;
        this.session = new JettyNpnSslSession(engine);
        if (b) {
            NextProtoNego.put(engine, (NextProtoNego.Provider)new NextProtoNego.ServerProvider(list) {
                final List val$nextProtocols;
                final JettyNpnSslEngine this$0;
                
                public void unsupported() {
                    this.this$0.getSession().setApplicationProtocol(this.val$nextProtocols.get(this.val$nextProtocols.size() - 1));
                }
                
                public List protocols() {
                    return this.val$nextProtocols;
                }
                
                public void protocolSelected(final String applicationProtocol) {
                    this.this$0.getSession().setApplicationProtocol(applicationProtocol);
                }
            });
        }
        else {
            final String[] array = list.toArray(new String[list.size()]);
            NextProtoNego.put(engine, (NextProtoNego.Provider)new NextProtoNego.ClientProvider(array, array[array.length - 1]) {
                final String[] val$list;
                final String val$fallback;
                final JettyNpnSslEngine this$0;
                
                public boolean supports() {
                    return true;
                }
                
                public void unsupported() {
                    JettyNpnSslEngine.access$000(this.this$0).setApplicationProtocol(null);
                }
                
                public String selectProtocol(final List list) {
                    final String[] val$list = this.val$list;
                    while (0 < val$list.length) {
                        final String s = val$list[0];
                        if (list.contains(s)) {
                            return s;
                        }
                        int n = 0;
                        ++n;
                    }
                    return this.val$fallback;
                }
            });
        }
    }
    
    @Override
    public JettyNpnSslSession getSession() {
        return this.session;
    }
    
    @Override
    public void closeInbound() throws SSLException {
        NextProtoNego.remove(this.engine);
        this.engine.closeInbound();
    }
    
    @Override
    public void closeOutbound() {
        NextProtoNego.remove(this.engine);
        this.engine.closeOutbound();
    }
    
    @Override
    public String getPeerHost() {
        return this.engine.getPeerHost();
    }
    
    @Override
    public int getPeerPort() {
        return this.engine.getPeerPort();
    }
    
    @Override
    public SSLEngineResult wrap(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws SSLException {
        return this.engine.wrap(byteBuffer, byteBuffer2);
    }
    
    @Override
    public SSLEngineResult wrap(final ByteBuffer[] array, final ByteBuffer byteBuffer) throws SSLException {
        return this.engine.wrap(array, byteBuffer);
    }
    
    @Override
    public SSLEngineResult wrap(final ByteBuffer[] array, final int n, final int n2, final ByteBuffer byteBuffer) throws SSLException {
        return this.engine.wrap(array, n, n2, byteBuffer);
    }
    
    @Override
    public SSLEngineResult unwrap(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws SSLException {
        return this.engine.unwrap(byteBuffer, byteBuffer2);
    }
    
    @Override
    public SSLEngineResult unwrap(final ByteBuffer byteBuffer, final ByteBuffer[] array) throws SSLException {
        return this.engine.unwrap(byteBuffer, array);
    }
    
    @Override
    public SSLEngineResult unwrap(final ByteBuffer byteBuffer, final ByteBuffer[] array, final int n, final int n2) throws SSLException {
        return this.engine.unwrap(byteBuffer, array, n, n2);
    }
    
    @Override
    public Runnable getDelegatedTask() {
        return this.engine.getDelegatedTask();
    }
    
    @Override
    public boolean isInboundDone() {
        return this.engine.isInboundDone();
    }
    
    @Override
    public boolean isOutboundDone() {
        return this.engine.isOutboundDone();
    }
    
    @Override
    public String[] getSupportedCipherSuites() {
        return this.engine.getSupportedCipherSuites();
    }
    
    @Override
    public String[] getEnabledCipherSuites() {
        return this.engine.getEnabledCipherSuites();
    }
    
    @Override
    public void setEnabledCipherSuites(final String[] enabledCipherSuites) {
        this.engine.setEnabledCipherSuites(enabledCipherSuites);
    }
    
    @Override
    public String[] getSupportedProtocols() {
        return this.engine.getSupportedProtocols();
    }
    
    @Override
    public String[] getEnabledProtocols() {
        return this.engine.getEnabledProtocols();
    }
    
    @Override
    public void setEnabledProtocols(final String[] enabledProtocols) {
        this.engine.setEnabledProtocols(enabledProtocols);
    }
    
    @Override
    public SSLSession getHandshakeSession() {
        return this.engine.getHandshakeSession();
    }
    
    @Override
    public void beginHandshake() throws SSLException {
        this.engine.beginHandshake();
    }
    
    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.engine.getHandshakeStatus();
    }
    
    @Override
    public void setUseClientMode(final boolean useClientMode) {
        this.engine.setUseClientMode(useClientMode);
    }
    
    @Override
    public boolean getUseClientMode() {
        return this.engine.getUseClientMode();
    }
    
    @Override
    public void setNeedClientAuth(final boolean needClientAuth) {
        this.engine.setNeedClientAuth(needClientAuth);
    }
    
    @Override
    public boolean getNeedClientAuth() {
        return this.engine.getNeedClientAuth();
    }
    
    @Override
    public void setWantClientAuth(final boolean wantClientAuth) {
        this.engine.setWantClientAuth(wantClientAuth);
    }
    
    @Override
    public boolean getWantClientAuth() {
        return this.engine.getWantClientAuth();
    }
    
    @Override
    public void setEnableSessionCreation(final boolean enableSessionCreation) {
        this.engine.setEnableSessionCreation(enableSessionCreation);
    }
    
    @Override
    public boolean getEnableSessionCreation() {
        return this.engine.getEnableSessionCreation();
    }
    
    @Override
    public SSLParameters getSSLParameters() {
        return this.engine.getSSLParameters();
    }
    
    @Override
    public void setSSLParameters(final SSLParameters sslParameters) {
        this.engine.setSSLParameters(sslParameters);
    }
    
    @Override
    public SSLSession getSession() {
        return this.getSession();
    }
    
    static JettyNpnSslSession access$000(final JettyNpnSslEngine jettyNpnSslEngine) {
        return jettyNpnSslEngine.session;
    }
    
    static {
        $assertionsDisabled = !JettyNpnSslEngine.class.desiredAssertionStatus();
    }
}
