package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import org.apache.http.impl.auth.*;
import org.apache.commons.logging.*;
import org.apache.http.client.protocol.*;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.*;
import java.util.concurrent.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.conn.*;
import org.apache.http.client.config.*;
import java.io.*;
import org.apache.http.conn.routing.*;
import org.apache.http.message.*;
import org.apache.http.entity.*;
import org.apache.http.*;

@Immutable
public class MainClientExec implements ClientExecChain
{
    private final Log log;
    private final HttpRequestExecutor requestExecutor;
    private final HttpClientConnectionManager connManager;
    private final ConnectionReuseStrategy reuseStrategy;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final HttpProcessor proxyHttpProcessor;
    private final AuthenticationStrategy targetAuthStrategy;
    private final AuthenticationStrategy proxyAuthStrategy;
    private final HttpAuthenticator authenticator;
    private final UserTokenHandler userTokenHandler;
    private final HttpRouteDirector routeDirector;
    
    public MainClientExec(final HttpRequestExecutor requestExecutor, final HttpClientConnectionManager connManager, final ConnectionReuseStrategy reuseStrategy, final ConnectionKeepAliveStrategy keepAliveStrategy, final AuthenticationStrategy targetAuthStrategy, final AuthenticationStrategy proxyAuthStrategy, final UserTokenHandler userTokenHandler) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(connManager, "Client connection manager");
        Args.notNull(reuseStrategy, "Connection reuse strategy");
        Args.notNull(keepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(targetAuthStrategy, "Target authentication strategy");
        Args.notNull(proxyAuthStrategy, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        this.authenticator = new HttpAuthenticator();
        this.proxyHttpProcessor = new ImmutableHttpProcessor(new HttpRequestInterceptor[] { new RequestTargetHost(), new RequestClientConnControl() });
        this.routeDirector = new BasicRouteDirector();
        this.requestExecutor = requestExecutor;
        this.connManager = connManager;
        this.reuseStrategy = reuseStrategy;
        this.keepAliveStrategy = keepAliveStrategy;
        this.targetAuthStrategy = targetAuthStrategy;
        this.proxyAuthStrategy = proxyAuthStrategy;
        this.userTokenHandler = userTokenHandler;
    }
    
    public CloseableHttpResponse execute(final HttpRoute httpRoute, final HttpRequestWrapper httpRequestWrapper, final HttpClientContext httpClientContext, final HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        AuthState targetAuthState = httpClientContext.getTargetAuthState();
        if (targetAuthState == null) {
            targetAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.target-scope", targetAuthState);
        }
        AuthState proxyAuthState = httpClientContext.getProxyAuthState();
        if (proxyAuthState == null) {
            proxyAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.proxy-scope", proxyAuthState);
        }
        if (httpRequestWrapper instanceof HttpEntityEnclosingRequest) {
            Proxies.enhanceEntity((HttpEntityEnclosingRequest)httpRequestWrapper);
        }
        Object state = httpClientContext.getUserToken();
        final ConnectionRequest requestConnection = this.connManager.requestConnection(httpRoute, state);
        if (httpExecutionAware != null) {
            if (httpExecutionAware.isAborted()) {
                requestConnection.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware.setCancellable(requestConnection);
        }
        final RequestConfig requestConfig = httpClientContext.getRequestConfig();
        final int connectionRequestTimeout = requestConfig.getConnectionRequestTimeout();
        final HttpClientConnection value = requestConnection.get((connectionRequestTimeout > 0) ? ((long)connectionRequestTimeout) : 0L, TimeUnit.MILLISECONDS);
        httpClientContext.setAttribute("http.connection", value);
        if (requestConfig.isStaleConnectionCheckEnabled() && value.isOpen()) {
            this.log.debug("Stale connection check");
            if (value.isStale()) {
                this.log.debug("Stale connection detected");
                value.close();
            }
        }
        final ConnectionHolder cancellable = new ConnectionHolder(this.log, this.connManager, value);
        if (httpExecutionAware != null) {
            httpExecutionAware.setCancellable(cancellable);
        }
        while (1 <= 1 || Proxies.isRepeatable(httpRequestWrapper)) {
            if (httpExecutionAware != null && httpExecutionAware.isAborted()) {
                throw new RequestAbortedException("Request aborted");
            }
            if (!value.isOpen()) {
                this.log.debug("Opening connection " + httpRoute);
                this.establishRoute(proxyAuthState, value, httpRoute, httpRequestWrapper, httpClientContext);
            }
            final int socketTimeout = requestConfig.getSocketTimeout();
            if (socketTimeout >= 0) {
                value.setSocketTimeout(socketTimeout);
            }
            if (httpExecutionAware != null && httpExecutionAware.isAborted()) {
                throw new RequestAbortedException("Request aborted");
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Executing request " + httpRequestWrapper.getRequestLine());
            }
            if (!httpRequestWrapper.containsHeader("Authorization")) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Target auth state: " + targetAuthState.getState());
                }
                this.authenticator.generateAuthResponse(httpRequestWrapper, targetAuthState, httpClientContext);
            }
            if (!httpRequestWrapper.containsHeader("Proxy-Authorization") && !httpRoute.isTunnelled()) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Proxy auth state: " + proxyAuthState.getState());
                }
                this.authenticator.generateAuthResponse(httpRequestWrapper, proxyAuthState, httpClientContext);
            }
            final HttpResponse execute = this.requestExecutor.execute(httpRequestWrapper, value, httpClientContext);
            if (this.reuseStrategy.keepAlive(execute, httpClientContext)) {
                final long keepAliveDuration = this.keepAliveStrategy.getKeepAliveDuration(execute, httpClientContext);
                if (this.log.isDebugEnabled()) {
                    String string;
                    if (keepAliveDuration > 0L) {
                        string = "for " + keepAliveDuration + " " + TimeUnit.MILLISECONDS;
                    }
                    else {
                        string = "indefinitely";
                    }
                    this.log.debug("Connection can be kept alive " + string);
                }
                cancellable.setValidFor(keepAliveDuration, TimeUnit.MILLISECONDS);
                cancellable.markReusable();
            }
            else {
                cancellable.markNonReusable();
            }
            if (this.needAuthentication(targetAuthState, proxyAuthState, httpRoute, execute, httpClientContext)) {
                final HttpEntity entity = execute.getEntity();
                if (cancellable.isReusable()) {
                    EntityUtils.consume(entity);
                }
                else {
                    value.close();
                    if (proxyAuthState.getState() == AuthProtocolState.SUCCESS && proxyAuthState.getAuthScheme() != null && proxyAuthState.getAuthScheme().isConnectionBased()) {
                        this.log.debug("Resetting proxy auth state");
                        proxyAuthState.reset();
                    }
                    if (targetAuthState.getState() == AuthProtocolState.SUCCESS && targetAuthState.getAuthScheme() != null && targetAuthState.getAuthScheme().isConnectionBased()) {
                        this.log.debug("Resetting target auth state");
                        targetAuthState.reset();
                    }
                }
                final HttpRequest original = httpRequestWrapper.getOriginal();
                if (!original.containsHeader("Authorization")) {
                    httpRequestWrapper.removeHeaders("Authorization");
                }
                if (!original.containsHeader("Proxy-Authorization")) {
                    httpRequestWrapper.removeHeaders("Proxy-Authorization");
                }
                int n = 0;
                ++n;
            }
            else {
                if (state == null) {
                    state = this.userTokenHandler.getUserToken(httpClientContext);
                    httpClientContext.setAttribute("http.user-token", state);
                }
                if (state != null) {
                    cancellable.setState(state);
                }
                final HttpEntity entity2 = execute.getEntity();
                if (entity2 == null || !entity2.isStreaming()) {
                    cancellable.releaseConnection();
                    return Proxies.enhanceResponse(execute, null);
                }
                return Proxies.enhanceResponse(execute, cancellable);
            }
        }
        throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
    }
    
    void establishRoute(final AuthState authState, final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final HttpRequest httpRequest, final HttpClientContext httpClientContext) throws HttpException, IOException {
        final int connectTimeout = httpClientContext.getRequestConfig().getConnectTimeout();
        final RouteTracker routeTracker = new RouteTracker(httpRoute);
        int i;
        do {
            final HttpRoute route = routeTracker.toRoute();
            i = this.routeDirector.nextStep(httpRoute, route);
            switch (i) {
                case 1: {
                    this.connManager.connect(httpClientConnection, httpRoute, (connectTimeout > 0) ? connectTimeout : false, httpClientContext);
                    routeTracker.connectTarget(httpRoute.isSecure());
                    continue;
                }
                case 2: {
                    this.connManager.connect(httpClientConnection, httpRoute, (connectTimeout > 0) ? connectTimeout : false, httpClientContext);
                    routeTracker.connectProxy(httpRoute.getProxyHost(), false);
                    continue;
                }
                case 3: {
                    final boolean tunnelToTarget = this.createTunnelToTarget(authState, httpClientConnection, httpRoute, httpRequest, httpClientContext);
                    this.log.debug("Tunnel to target created.");
                    routeTracker.tunnelTarget(tunnelToTarget);
                    continue;
                }
                case 4: {
                    final int n = route.getHopCount() - 1;
                    final boolean tunnelToProxy = this.createTunnelToProxy(httpRoute, n, httpClientContext);
                    this.log.debug("Tunnel to proxy created.");
                    routeTracker.tunnelProxy(httpRoute.getHopTarget(n), tunnelToProxy);
                    continue;
                }
                case 5: {
                    this.connManager.upgrade(httpClientConnection, httpRoute, httpClientContext);
                    routeTracker.layerProtocol(httpRoute.isSecure());
                    continue;
                }
                case -1: {
                    throw new HttpException("Unable to establish route: planned = " + httpRoute + "; current = " + route);
                }
                case 0: {
                    this.connManager.routeComplete(httpClientConnection, httpRoute, httpClientContext);
                    continue;
                }
                default: {
                    throw new IllegalStateException("Unknown step indicator " + i + " from RouteDirector.");
                }
            }
        } while (i > 0);
    }
    
    private boolean createTunnelToTarget(final AuthState authState, final HttpClientConnection httpClientConnection, final HttpRoute httpRoute, final HttpRequest httpRequest, final HttpClientContext httpClientContext) throws HttpException, IOException {
        final RequestConfig requestConfig = httpClientContext.getRequestConfig();
        final int connectTimeout = requestConfig.getConnectTimeout();
        final HttpHost targetHost = httpRoute.getTargetHost();
        final HttpHost proxyHost = httpRoute.getProxyHost();
        final BasicHttpRequest basicHttpRequest = new BasicHttpRequest("CONNECT", targetHost.toHostString(), httpRequest.getProtocolVersion());
        this.requestExecutor.preProcess(basicHttpRequest, this.proxyHttpProcessor, httpClientContext);
        while (true) {
            if (!httpClientConnection.isOpen()) {
                this.connManager.connect(httpClientConnection, httpRoute, (connectTimeout > 0) ? connectTimeout : false, httpClientContext);
            }
            basicHttpRequest.removeHeaders("Proxy-Authorization");
            this.authenticator.generateAuthResponse(basicHttpRequest, authState, httpClientContext);
            final HttpResponse execute = this.requestExecutor.execute(basicHttpRequest, httpClientConnection, httpClientContext);
            if (execute.getStatusLine().getStatusCode() < 200) {
                throw new HttpException("Unexpected response to CONNECT request: " + execute.getStatusLine());
            }
            if (!requestConfig.isAuthenticationEnabled()) {
                continue;
            }
            if (this.authenticator.isAuthenticationRequested(proxyHost, execute, this.proxyAuthStrategy, authState, httpClientContext) && this.authenticator.handleAuthChallenge(proxyHost, execute, this.proxyAuthStrategy, authState, httpClientContext)) {
                if (this.reuseStrategy.keepAlive(execute, httpClientContext)) {
                    this.log.debug("Connection kept alive");
                    EntityUtils.consume(execute.getEntity());
                }
                else {
                    httpClientConnection.close();
                }
            }
            else {
                if (execute.getStatusLine().getStatusCode() > 299) {
                    final HttpEntity entity = execute.getEntity();
                    if (entity != null) {
                        execute.setEntity(new BufferedHttpEntity(entity));
                    }
                    httpClientConnection.close();
                    throw new TunnelRefusedException("CONNECT refused by proxy: " + execute.getStatusLine(), execute);
                }
                return false;
            }
        }
    }
    
    private boolean createTunnelToProxy(final HttpRoute httpRoute, final int n, final HttpClientContext httpClientContext) throws HttpException {
        throw new HttpException("Proxy chains are not supported.");
    }
    
    private boolean needAuthentication(final AuthState authState, final AuthState authState2, final HttpRoute httpRoute, final HttpResponse httpResponse, final HttpClientContext httpClientContext) {
        if (httpClientContext.getRequestConfig().isAuthenticationEnabled()) {
            HttpHost httpHost = httpClientContext.getTargetHost();
            if (httpHost == null) {
                httpHost = httpRoute.getTargetHost();
            }
            if (httpHost.getPort() < 0) {
                httpHost = new HttpHost(httpHost.getHostName(), httpRoute.getTargetHost().getPort(), httpHost.getSchemeName());
            }
            final boolean authenticationRequested = this.authenticator.isAuthenticationRequested(httpHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
            HttpHost httpHost2 = httpRoute.getProxyHost();
            if (httpHost2 == null) {
                httpHost2 = httpRoute.getTargetHost();
            }
            final boolean authenticationRequested2 = this.authenticator.isAuthenticationRequested(httpHost2, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
            if (authenticationRequested) {
                return this.authenticator.handleAuthChallenge(httpHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
            }
            if (authenticationRequested2) {
                return this.authenticator.handleAuthChallenge(httpHost2, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
            }
        }
        return false;
    }
}
