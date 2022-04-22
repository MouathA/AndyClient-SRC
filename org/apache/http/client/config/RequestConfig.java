package org.apache.http.client.config;

import org.apache.http.*;
import java.net.*;
import java.util.*;

public class RequestConfig implements Cloneable
{
    public static final RequestConfig DEFAULT;
    private final boolean expectContinueEnabled;
    private final HttpHost proxy;
    private final InetAddress localAddress;
    private final boolean staleConnectionCheckEnabled;
    private final String cookieSpec;
    private final boolean redirectsEnabled;
    private final boolean relativeRedirectsAllowed;
    private final boolean circularRedirectsAllowed;
    private final int maxRedirects;
    private final boolean authenticationEnabled;
    private final Collection targetPreferredAuthSchemes;
    private final Collection proxyPreferredAuthSchemes;
    private final int connectionRequestTimeout;
    private final int connectTimeout;
    private final int socketTimeout;
    
    RequestConfig(final boolean expectContinueEnabled, final HttpHost proxy, final InetAddress localAddress, final boolean staleConnectionCheckEnabled, final String cookieSpec, final boolean redirectsEnabled, final boolean relativeRedirectsAllowed, final boolean circularRedirectsAllowed, final int maxRedirects, final boolean authenticationEnabled, final Collection targetPreferredAuthSchemes, final Collection proxyPreferredAuthSchemes, final int connectionRequestTimeout, final int connectTimeout, final int socketTimeout) {
        this.expectContinueEnabled = expectContinueEnabled;
        this.proxy = proxy;
        this.localAddress = localAddress;
        this.staleConnectionCheckEnabled = staleConnectionCheckEnabled;
        this.cookieSpec = cookieSpec;
        this.redirectsEnabled = redirectsEnabled;
        this.relativeRedirectsAllowed = relativeRedirectsAllowed;
        this.circularRedirectsAllowed = circularRedirectsAllowed;
        this.maxRedirects = maxRedirects;
        this.authenticationEnabled = authenticationEnabled;
        this.targetPreferredAuthSchemes = targetPreferredAuthSchemes;
        this.proxyPreferredAuthSchemes = proxyPreferredAuthSchemes;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }
    
    public boolean isExpectContinueEnabled() {
        return this.expectContinueEnabled;
    }
    
    public HttpHost getProxy() {
        return this.proxy;
    }
    
    public InetAddress getLocalAddress() {
        return this.localAddress;
    }
    
    public boolean isStaleConnectionCheckEnabled() {
        return this.staleConnectionCheckEnabled;
    }
    
    public String getCookieSpec() {
        return this.cookieSpec;
    }
    
    public boolean isRedirectsEnabled() {
        return this.redirectsEnabled;
    }
    
    public boolean isRelativeRedirectsAllowed() {
        return this.relativeRedirectsAllowed;
    }
    
    public boolean isCircularRedirectsAllowed() {
        return this.circularRedirectsAllowed;
    }
    
    public int getMaxRedirects() {
        return this.maxRedirects;
    }
    
    public boolean isAuthenticationEnabled() {
        return this.authenticationEnabled;
    }
    
    public Collection getTargetPreferredAuthSchemes() {
        return this.targetPreferredAuthSchemes;
    }
    
    public Collection getProxyPreferredAuthSchemes() {
        return this.proxyPreferredAuthSchemes;
    }
    
    public int getConnectionRequestTimeout() {
        return this.connectionRequestTimeout;
    }
    
    public int getConnectTimeout() {
        return this.connectTimeout;
    }
    
    public int getSocketTimeout() {
        return this.socketTimeout;
    }
    
    @Override
    protected RequestConfig clone() throws CloneNotSupportedException {
        return (RequestConfig)super.clone();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(", expectContinueEnabled=").append(this.expectContinueEnabled);
        sb.append(", proxy=").append(this.proxy);
        sb.append(", localAddress=").append(this.localAddress);
        sb.append(", staleConnectionCheckEnabled=").append(this.staleConnectionCheckEnabled);
        sb.append(", cookieSpec=").append(this.cookieSpec);
        sb.append(", redirectsEnabled=").append(this.redirectsEnabled);
        sb.append(", relativeRedirectsAllowed=").append(this.relativeRedirectsAllowed);
        sb.append(", maxRedirects=").append(this.maxRedirects);
        sb.append(", circularRedirectsAllowed=").append(this.circularRedirectsAllowed);
        sb.append(", authenticationEnabled=").append(this.authenticationEnabled);
        sb.append(", targetPreferredAuthSchemes=").append(this.targetPreferredAuthSchemes);
        sb.append(", proxyPreferredAuthSchemes=").append(this.proxyPreferredAuthSchemes);
        sb.append(", connectionRequestTimeout=").append(this.connectionRequestTimeout);
        sb.append(", connectTimeout=").append(this.connectTimeout);
        sb.append(", socketTimeout=").append(this.socketTimeout);
        sb.append("]");
        return sb.toString();
    }
    
    public static Builder custom() {
        return new Builder();
    }
    
    public static Builder copy(final RequestConfig requestConfig) {
        return new Builder().setExpectContinueEnabled(requestConfig.isExpectContinueEnabled()).setProxy(requestConfig.getProxy()).setLocalAddress(requestConfig.getLocalAddress()).setStaleConnectionCheckEnabled(requestConfig.isStaleConnectionCheckEnabled()).setCookieSpec(requestConfig.getCookieSpec()).setRedirectsEnabled(requestConfig.isRedirectsEnabled()).setRelativeRedirectsAllowed(requestConfig.isRelativeRedirectsAllowed()).setCircularRedirectsAllowed(requestConfig.isCircularRedirectsAllowed()).setMaxRedirects(requestConfig.getMaxRedirects()).setAuthenticationEnabled(requestConfig.isAuthenticationEnabled()).setTargetPreferredAuthSchemes(requestConfig.getTargetPreferredAuthSchemes()).setProxyPreferredAuthSchemes(requestConfig.getProxyPreferredAuthSchemes()).setConnectionRequestTimeout(requestConfig.getConnectionRequestTimeout()).setConnectTimeout(requestConfig.getConnectTimeout()).setSocketTimeout(requestConfig.getSocketTimeout());
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static {
        DEFAULT = new Builder().build();
    }
    
    public static class Builder
    {
        private boolean expectContinueEnabled;
        private HttpHost proxy;
        private InetAddress localAddress;
        private boolean staleConnectionCheckEnabled;
        private String cookieSpec;
        private boolean redirectsEnabled;
        private boolean relativeRedirectsAllowed;
        private boolean circularRedirectsAllowed;
        private int maxRedirects;
        private boolean authenticationEnabled;
        private Collection targetPreferredAuthSchemes;
        private Collection proxyPreferredAuthSchemes;
        private int connectionRequestTimeout;
        private int connectTimeout;
        private int socketTimeout;
        
        Builder() {
            this.staleConnectionCheckEnabled = true;
            this.redirectsEnabled = true;
            this.maxRedirects = 50;
            this.relativeRedirectsAllowed = true;
            this.authenticationEnabled = true;
            this.connectionRequestTimeout = -1;
            this.connectTimeout = -1;
            this.socketTimeout = -1;
        }
        
        public Builder setExpectContinueEnabled(final boolean expectContinueEnabled) {
            this.expectContinueEnabled = expectContinueEnabled;
            return this;
        }
        
        public Builder setProxy(final HttpHost proxy) {
            this.proxy = proxy;
            return this;
        }
        
        public Builder setLocalAddress(final InetAddress localAddress) {
            this.localAddress = localAddress;
            return this;
        }
        
        public Builder setStaleConnectionCheckEnabled(final boolean staleConnectionCheckEnabled) {
            this.staleConnectionCheckEnabled = staleConnectionCheckEnabled;
            return this;
        }
        
        public Builder setCookieSpec(final String cookieSpec) {
            this.cookieSpec = cookieSpec;
            return this;
        }
        
        public Builder setRedirectsEnabled(final boolean redirectsEnabled) {
            this.redirectsEnabled = redirectsEnabled;
            return this;
        }
        
        public Builder setRelativeRedirectsAllowed(final boolean relativeRedirectsAllowed) {
            this.relativeRedirectsAllowed = relativeRedirectsAllowed;
            return this;
        }
        
        public Builder setCircularRedirectsAllowed(final boolean circularRedirectsAllowed) {
            this.circularRedirectsAllowed = circularRedirectsAllowed;
            return this;
        }
        
        public Builder setMaxRedirects(final int maxRedirects) {
            this.maxRedirects = maxRedirects;
            return this;
        }
        
        public Builder setAuthenticationEnabled(final boolean authenticationEnabled) {
            this.authenticationEnabled = authenticationEnabled;
            return this;
        }
        
        public Builder setTargetPreferredAuthSchemes(final Collection targetPreferredAuthSchemes) {
            this.targetPreferredAuthSchemes = targetPreferredAuthSchemes;
            return this;
        }
        
        public Builder setProxyPreferredAuthSchemes(final Collection proxyPreferredAuthSchemes) {
            this.proxyPreferredAuthSchemes = proxyPreferredAuthSchemes;
            return this;
        }
        
        public Builder setConnectionRequestTimeout(final int connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
            return this;
        }
        
        public Builder setConnectTimeout(final int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }
        
        public Builder setSocketTimeout(final int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }
        
        public RequestConfig build() {
            return new RequestConfig(this.expectContinueEnabled, this.proxy, this.localAddress, this.staleConnectionCheckEnabled, this.cookieSpec, this.redirectsEnabled, this.relativeRedirectsAllowed, this.circularRedirectsAllowed, this.maxRedirects, this.authenticationEnabled, this.targetPreferredAuthSchemes, this.proxyPreferredAuthSchemes, this.connectionRequestTimeout, this.connectTimeout, this.socketTimeout);
        }
    }
}
