package io.netty.handler.ssl;

import java.security.cert.*;
import javax.net.ssl.*;
import javax.security.cert.*;
import java.security.*;

final class JettyNpnSslSession implements SSLSession
{
    private final SSLEngine engine;
    private String applicationProtocol;
    
    JettyNpnSslSession(final SSLEngine engine) {
        this.engine = engine;
    }
    
    void setApplicationProtocol(String replace) {
        if (replace != null) {
            replace = replace.replace(':', '_');
        }
        this.applicationProtocol = replace;
    }
    
    @Override
    public String getProtocol() {
        final String protocol = this.unwrap().getProtocol();
        final String applicationProtocol = this.applicationProtocol;
        if (applicationProtocol != null) {
            final StringBuilder sb = new StringBuilder(32);
            if (protocol != null) {
                sb.append(protocol.replace(':', '_'));
                sb.append(':');
            }
            else {
                sb.append("null:");
            }
            sb.append(applicationProtocol);
            return sb.toString();
        }
        if (protocol != null) {
            return protocol.replace(':', '_');
        }
        return null;
    }
    
    private SSLSession unwrap() {
        return this.engine.getSession();
    }
    
    @Override
    public byte[] getId() {
        return this.unwrap().getId();
    }
    
    @Override
    public SSLSessionContext getSessionContext() {
        return this.unwrap().getSessionContext();
    }
    
    @Override
    public long getCreationTime() {
        return this.unwrap().getCreationTime();
    }
    
    @Override
    public long getLastAccessedTime() {
        return this.unwrap().getLastAccessedTime();
    }
    
    @Override
    public void invalidate() {
        this.unwrap().invalidate();
    }
    
    @Override
    public boolean isValid() {
        return this.unwrap().isValid();
    }
    
    @Override
    public void putValue(final String s, final Object o) {
        this.unwrap().putValue(s, o);
    }
    
    @Override
    public Object getValue(final String s) {
        return this.unwrap().getValue(s);
    }
    
    @Override
    public void removeValue(final String s) {
        this.unwrap().removeValue(s);
    }
    
    @Override
    public String[] getValueNames() {
        return this.unwrap().getValueNames();
    }
    
    @Override
    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return this.unwrap().getPeerCertificates();
    }
    
    @Override
    public Certificate[] getLocalCertificates() {
        return this.unwrap().getLocalCertificates();
    }
    
    @Override
    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return this.unwrap().getPeerCertificateChain();
    }
    
    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return this.unwrap().getPeerPrincipal();
    }
    
    @Override
    public Principal getLocalPrincipal() {
        return this.unwrap().getLocalPrincipal();
    }
    
    @Override
    public String getCipherSuite() {
        return this.unwrap().getCipherSuite();
    }
    
    @Override
    public String getPeerHost() {
        return this.unwrap().getPeerHost();
    }
    
    @Override
    public int getPeerPort() {
        return this.unwrap().getPeerPort();
    }
    
    @Override
    public int getPacketBufferSize() {
        return this.unwrap().getPacketBufferSize();
    }
    
    @Override
    public int getApplicationBufferSize() {
        return this.unwrap().getApplicationBufferSize();
    }
}
