package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.client.utils.*;
import java.net.*;
import org.apache.http.protocol.*;
import org.apache.http.client.params.*;
import java.util.concurrent.*;
import org.apache.http.impl.auth.*;
import org.apache.http.util.*;
import org.apache.http.auth.*;
import org.apache.http.conn.*;
import java.io.*;
import org.apache.http.*;
import org.apache.http.conn.routing.*;
import org.apache.http.entity.*;
import org.apache.http.params.*;
import org.apache.http.message.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;

@Deprecated
@NotThreadSafe
public class DefaultRequestDirector implements RequestDirector
{
    private final Log log;
    protected final ClientConnectionManager connManager;
    protected final HttpRoutePlanner routePlanner;
    protected final ConnectionReuseStrategy reuseStrategy;
    protected final ConnectionKeepAliveStrategy keepAliveStrategy;
    protected final HttpRequestExecutor requestExec;
    protected final HttpProcessor httpProcessor;
    protected final HttpRequestRetryHandler retryHandler;
    @Deprecated
    protected final RedirectHandler redirectHandler;
    protected final RedirectStrategy redirectStrategy;
    @Deprecated
    protected final AuthenticationHandler targetAuthHandler;
    protected final AuthenticationStrategy targetAuthStrategy;
    @Deprecated
    protected final AuthenticationHandler proxyAuthHandler;
    protected final AuthenticationStrategy proxyAuthStrategy;
    protected final UserTokenHandler userTokenHandler;
    protected final HttpParams params;
    protected ManagedClientConnection managedConn;
    protected final AuthState targetAuthState;
    protected final AuthState proxyAuthState;
    private final HttpAuthenticator authenticator;
    private int execCount;
    private int redirectCount;
    private final int maxRedirects;
    private HttpHost virtualHost;
    
    @Deprecated
    public DefaultRequestDirector(final HttpRequestExecutor httpRequestExecutor, final ClientConnectionManager clientConnectionManager, final ConnectionReuseStrategy connectionReuseStrategy, final ConnectionKeepAliveStrategy connectionKeepAliveStrategy, final HttpRoutePlanner httpRoutePlanner, final HttpProcessor httpProcessor, final HttpRequestRetryHandler httpRequestRetryHandler, final RedirectHandler redirectHandler, final AuthenticationHandler authenticationHandler, final AuthenticationHandler authenticationHandler2, final UserTokenHandler userTokenHandler, final HttpParams httpParams) {
        this(LogFactory.getLog(DefaultRequestDirector.class), httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, new DefaultRedirectStrategyAdaptor(redirectHandler), new AuthenticationStrategyAdaptor(authenticationHandler), new AuthenticationStrategyAdaptor(authenticationHandler2), userTokenHandler, httpParams);
    }
    
    @Deprecated
    public DefaultRequestDirector(final Log log, final HttpRequestExecutor httpRequestExecutor, final ClientConnectionManager clientConnectionManager, final ConnectionReuseStrategy connectionReuseStrategy, final ConnectionKeepAliveStrategy connectionKeepAliveStrategy, final HttpRoutePlanner httpRoutePlanner, final HttpProcessor httpProcessor, final HttpRequestRetryHandler httpRequestRetryHandler, final RedirectStrategy redirectStrategy, final AuthenticationHandler authenticationHandler, final AuthenticationHandler authenticationHandler2, final UserTokenHandler userTokenHandler, final HttpParams httpParams) {
        this(LogFactory.getLog(DefaultRequestDirector.class), httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectStrategy, new AuthenticationStrategyAdaptor(authenticationHandler), new AuthenticationStrategyAdaptor(authenticationHandler2), userTokenHandler, httpParams);
    }
    
    public DefaultRequestDirector(final Log log, final HttpRequestExecutor requestExec, final ClientConnectionManager connManager, final ConnectionReuseStrategy reuseStrategy, final ConnectionKeepAliveStrategy keepAliveStrategy, final HttpRoutePlanner routePlanner, final HttpProcessor httpProcessor, final HttpRequestRetryHandler retryHandler, final RedirectStrategy redirectStrategy, final AuthenticationStrategy targetAuthStrategy, final AuthenticationStrategy proxyAuthStrategy, final UserTokenHandler userTokenHandler, final HttpParams params) {
        Args.notNull(log, "Log");
        Args.notNull(requestExec, "Request executor");
        Args.notNull(connManager, "Client connection manager");
        Args.notNull(reuseStrategy, "Connection reuse strategy");
        Args.notNull(keepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(routePlanner, "Route planner");
        Args.notNull(httpProcessor, "HTTP protocol processor");
        Args.notNull(retryHandler, "HTTP request retry handler");
        Args.notNull(redirectStrategy, "Redirect strategy");
        Args.notNull(targetAuthStrategy, "Target authentication strategy");
        Args.notNull(proxyAuthStrategy, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        Args.notNull(params, "HTTP parameters");
        this.log = log;
        this.authenticator = new HttpAuthenticator(log);
        this.requestExec = requestExec;
        this.connManager = connManager;
        this.reuseStrategy = reuseStrategy;
        this.keepAliveStrategy = keepAliveStrategy;
        this.routePlanner = routePlanner;
        this.httpProcessor = httpProcessor;
        this.retryHandler = retryHandler;
        this.redirectStrategy = redirectStrategy;
        this.targetAuthStrategy = targetAuthStrategy;
        this.proxyAuthStrategy = proxyAuthStrategy;
        this.userTokenHandler = userTokenHandler;
        this.params = params;
        if (redirectStrategy instanceof DefaultRedirectStrategyAdaptor) {
            this.redirectHandler = ((DefaultRedirectStrategyAdaptor)redirectStrategy).getHandler();
        }
        else {
            this.redirectHandler = null;
        }
        if (targetAuthStrategy instanceof AuthenticationStrategyAdaptor) {
            this.targetAuthHandler = ((AuthenticationStrategyAdaptor)targetAuthStrategy).getHandler();
        }
        else {
            this.targetAuthHandler = null;
        }
        if (proxyAuthStrategy instanceof AuthenticationStrategyAdaptor) {
            this.proxyAuthHandler = ((AuthenticationStrategyAdaptor)proxyAuthStrategy).getHandler();
        }
        else {
            this.proxyAuthHandler = null;
        }
        this.managedConn = null;
        this.execCount = 0;
        this.redirectCount = 0;
        this.targetAuthState = new AuthState();
        this.proxyAuthState = new AuthState();
        this.maxRedirects = this.params.getIntParameter("http.protocol.max-redirects", 100);
    }
    
    private RequestWrapper wrapRequest(final HttpRequest httpRequest) throws ProtocolException {
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            return new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httpRequest);
        }
        return new RequestWrapper(httpRequest);
    }
    
    protected void rewriteRequestURI(final RequestWrapper requestWrapper, final HttpRoute httpRoute) throws ProtocolException {
        final URI uri = requestWrapper.getURI();
        URI uri2;
        if (httpRoute.getProxyHost() != null && !httpRoute.isTunnelled()) {
            if (!uri.isAbsolute()) {
                uri2 = URIUtils.rewriteURI(uri, httpRoute.getTargetHost(), true);
            }
            else {
                uri2 = URIUtils.rewriteURI(uri);
            }
        }
        else if (uri.isAbsolute()) {
            uri2 = URIUtils.rewriteURI(uri, null, true);
        }
        else {
            uri2 = URIUtils.rewriteURI(uri);
        }
        requestWrapper.setURI(uri2);
    }
    
    public HttpResponse execute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        httpContext.setAttribute("http.auth.target-scope", this.targetAuthState);
        httpContext.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
        HttpHost httpHost2 = httpHost;
        final RequestWrapper wrapRequest = this.wrapRequest(httpRequest);
        wrapRequest.setParams(this.params);
        final HttpRoute determineRoute = this.determineRoute(httpHost2, wrapRequest, httpContext);
        this.virtualHost = (HttpHost)wrapRequest.getParams().getParameter("http.virtual-host");
        if (this.virtualHost != null && this.virtualHost.getPort() == -1) {
            ((httpHost2 != null) ? httpHost2 : determineRoute.getTargetHost()).getPort();
            if (0 != -1) {
                this.virtualHost = new HttpHost(this.virtualHost.getHostName(), 0, this.virtualHost.getSchemeName());
            }
        }
        RoutedRequest routedRequest = new RoutedRequest(wrapRequest, determineRoute);
        HttpResponse tryExecute = null;
        while (!true) {
            final RequestWrapper request = routedRequest.getRequest();
            final HttpRoute route = routedRequest.getRoute();
            Object state = httpContext.getAttribute("http.user-token");
            if (this.managedConn == null) {
                final ClientConnectionRequest requestConnection = this.connManager.requestConnection(route, state);
                if (httpRequest instanceof AbortableHttpRequest) {
                    ((AbortableHttpRequest)httpRequest).setConnectionRequest(requestConnection);
                }
                this.managedConn = requestConnection.getConnection(HttpClientParams.getConnectionManagerTimeout(this.params), TimeUnit.MILLISECONDS);
                if (HttpConnectionParams.isStaleCheckingEnabled(this.params) && this.managedConn.isOpen()) {
                    this.log.debug("Stale connection check");
                    if (this.managedConn.isStale()) {
                        this.log.debug("Stale connection detected");
                        this.managedConn.close();
                    }
                }
            }
            if (httpRequest instanceof AbortableHttpRequest) {
                ((AbortableHttpRequest)httpRequest).setReleaseTrigger(this.managedConn);
            }
            this.tryConnect(routedRequest, httpContext);
            final String userInfo = request.getURI().getUserInfo();
            if (userInfo != null) {
                this.targetAuthState.update(new BasicScheme(), new UsernamePasswordCredentials(userInfo));
            }
            if (this.virtualHost != null) {
                httpHost2 = this.virtualHost;
            }
            else {
                final URI uri = request.getURI();
                if (uri.isAbsolute()) {
                    httpHost2 = URIUtils.extractHost(uri);
                }
            }
            if (httpHost2 == null) {
                httpHost2 = route.getTargetHost();
            }
            request.resetHeaders();
            this.rewriteRequestURI(request, route);
            httpContext.setAttribute("http.target_host", httpHost2);
            httpContext.setAttribute("http.route", route);
            httpContext.setAttribute("http.connection", this.managedConn);
            this.requestExec.preProcess(request, this.httpProcessor, httpContext);
            tryExecute = this.tryExecute(routedRequest, httpContext);
            if (tryExecute == null) {
                continue;
            }
            tryExecute.setParams(this.params);
            this.requestExec.postProcess(tryExecute, this.httpProcessor, httpContext);
            this.reuseStrategy.keepAlive(tryExecute, httpContext);
            if (false) {
                final long keepAliveDuration = this.keepAliveStrategy.getKeepAliveDuration(tryExecute, httpContext);
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
                this.managedConn.setIdleDuration(keepAliveDuration, TimeUnit.MILLISECONDS);
            }
            final RoutedRequest handleResponse = this.handleResponse(routedRequest, tryExecute, httpContext);
            if (handleResponse != null) {
                if (false) {
                    EntityUtils.consume(tryExecute.getEntity());
                    this.managedConn.markReusable();
                }
                else {
                    this.managedConn.close();
                    if (this.proxyAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0 && this.proxyAuthState.getAuthScheme() != null && this.proxyAuthState.getAuthScheme().isConnectionBased()) {
                        this.log.debug("Resetting proxy auth state");
                        this.proxyAuthState.reset();
                    }
                    if (this.targetAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0 && this.targetAuthState.getAuthScheme() != null && this.targetAuthState.getAuthScheme().isConnectionBased()) {
                        this.log.debug("Resetting target auth state");
                        this.targetAuthState.reset();
                    }
                }
                if (!handleResponse.getRoute().equals(routedRequest.getRoute())) {
                    this.releaseConnection();
                }
                routedRequest = handleResponse;
            }
            if (this.managedConn == null) {
                continue;
            }
            if (state == null) {
                state = this.userTokenHandler.getUserToken(httpContext);
                httpContext.setAttribute("http.user-token", state);
            }
            if (state == null) {
                continue;
            }
            this.managedConn.setState(state);
        }
        if (tryExecute == null || tryExecute.getEntity() == null || !tryExecute.getEntity().isStreaming()) {
            if (false) {
                this.managedConn.markReusable();
            }
            this.releaseConnection();
        }
        else {
            tryExecute.setEntity(new BasicManagedEntity(tryExecute.getEntity(), this.managedConn, false));
        }
        return tryExecute;
    }
    
    private void tryConnect(final RoutedRequest routedRequest, final HttpContext httpContext) throws HttpException, IOException {
        final HttpRoute route = routedRequest.getRoute();
        httpContext.setAttribute("http.request", routedRequest.getRequest());
        int n = 0;
        ++n;
        if (!this.managedConn.isOpen()) {
            this.managedConn.open(route, httpContext, this.params);
        }
        else {
            this.managedConn.setSocketTimeout(HttpConnectionParams.getSoTimeout(this.params));
        }
        this.establishRoute(route, httpContext);
    }
    
    private HttpResponse tryExecute(final RoutedRequest routedRequest, final HttpContext httpContext) throws HttpException, IOException {
        final RequestWrapper request = routedRequest.getRequest();
        final HttpRoute route = routedRequest.getRoute();
        HttpResponse execute = null;
        final Throwable t = null;
        ++this.execCount;
        request.incrementExecCount();
        if (request.isRepeatable()) {
            if (!this.managedConn.isOpen()) {
                if (route.isTunnelled()) {
                    this.log.debug("Proxied connection. Need to start over.");
                    return execute;
                }
                this.log.debug("Reopening the direct connection.");
                this.managedConn.open(route, httpContext, this.params);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Attempt " + this.execCount + " to execute request");
            }
            execute = this.requestExec.execute(request, this.managedConn, httpContext);
            return execute;
        }
        this.log.debug("Cannot retry non-repeatable request");
        if (t != null) {
            throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", t);
        }
        throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
    }
    
    protected void releaseConnection() {
        this.managedConn.releaseConnection();
        this.managedConn = null;
    }
    
    protected HttpRoute determineRoute(final HttpHost httpHost, final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException {
        return this.routePlanner.determineRoute((httpHost != null) ? httpHost : ((HttpHost)httpRequest.getParams().getParameter("http.default-host")), httpRequest, httpContext);
    }
    
    protected void establishRoute(final HttpRoute httpRoute, final HttpContext httpContext) throws HttpException, IOException {
        final BasicRouteDirector basicRouteDirector = new BasicRouteDirector();
        int i;
        do {
            final HttpRoute route = this.managedConn.getRoute();
            i = basicRouteDirector.nextStep(httpRoute, route);
            switch (i) {
                case 1:
                case 2: {
                    this.managedConn.open(httpRoute, httpContext, this.params);
                    continue;
                }
                case 3: {
                    final boolean tunnelToTarget = this.createTunnelToTarget(httpRoute, httpContext);
                    this.log.debug("Tunnel to target created.");
                    this.managedConn.tunnelTarget(tunnelToTarget, this.params);
                    continue;
                }
                case 4: {
                    final int n = route.getHopCount() - 1;
                    final boolean tunnelToProxy = this.createTunnelToProxy(httpRoute, n, httpContext);
                    this.log.debug("Tunnel to proxy created.");
                    this.managedConn.tunnelProxy(httpRoute.getHopTarget(n), tunnelToProxy, this.params);
                    continue;
                }
                case 5: {
                    this.managedConn.layerProtocol(httpContext, this.params);
                    continue;
                }
                case -1: {
                    throw new HttpException("Unable to establish route: planned = " + httpRoute + "; current = " + route);
                }
                case 0: {
                    continue;
                }
                default: {
                    throw new IllegalStateException("Unknown step indicator " + i + " from RouteDirector.");
                }
            }
        } while (i > 0);
    }
    
    protected boolean createTunnelToTarget(final HttpRoute httpRoute, final HttpContext httpContext) throws HttpException, IOException {
        final HttpHost proxyHost = httpRoute.getProxyHost();
        final HttpHost targetHost = httpRoute.getTargetHost();
        while (true) {
            if (!this.managedConn.isOpen()) {
                this.managedConn.open(httpRoute, httpContext, this.params);
            }
            final HttpRequest connectRequest = this.createConnectRequest(httpRoute, httpContext);
            connectRequest.setParams(this.params);
            httpContext.setAttribute("http.target_host", targetHost);
            httpContext.setAttribute("http.proxy_host", proxyHost);
            httpContext.setAttribute("http.connection", this.managedConn);
            httpContext.setAttribute("http.request", connectRequest);
            this.requestExec.preProcess(connectRequest, this.httpProcessor, httpContext);
            final HttpResponse execute = this.requestExec.execute(connectRequest, this.managedConn, httpContext);
            execute.setParams(this.params);
            this.requestExec.postProcess(execute, this.httpProcessor, httpContext);
            if (execute.getStatusLine().getStatusCode() < 200) {
                throw new HttpException("Unexpected response to CONNECT request: " + execute.getStatusLine());
            }
            if (!HttpClientParams.isAuthenticating(this.params)) {
                continue;
            }
            if (this.authenticator.isAuthenticationRequested(proxyHost, execute, this.proxyAuthStrategy, this.proxyAuthState, httpContext) && this.authenticator.authenticate(proxyHost, execute, this.proxyAuthStrategy, this.proxyAuthState, httpContext)) {
                if (this.reuseStrategy.keepAlive(execute, httpContext)) {
                    this.log.debug("Connection kept alive");
                    EntityUtils.consume(execute.getEntity());
                }
                else {
                    this.managedConn.close();
                }
            }
            else {
                if (execute.getStatusLine().getStatusCode() > 299) {
                    final HttpEntity entity = execute.getEntity();
                    if (entity != null) {
                        execute.setEntity(new BufferedHttpEntity(entity));
                    }
                    this.managedConn.close();
                    throw new TunnelRefusedException("CONNECT refused by proxy: " + execute.getStatusLine(), execute);
                }
                this.managedConn.markReusable();
                return false;
            }
        }
    }
    
    protected boolean createTunnelToProxy(final HttpRoute httpRoute, final int n, final HttpContext httpContext) throws HttpException, IOException {
        throw new HttpException("Proxy chains are not supported.");
    }
    
    protected HttpRequest createConnectRequest(final HttpRoute httpRoute, final HttpContext httpContext) {
        final HttpHost targetHost = httpRoute.getTargetHost();
        final String hostName = targetHost.getHostName();
        int n = targetHost.getPort();
        if (n < 0) {
            n = this.connManager.getSchemeRegistry().getScheme(targetHost.getSchemeName()).getDefaultPort();
        }
        final StringBuilder sb = new StringBuilder(hostName.length() + 6);
        sb.append(hostName);
        sb.append(':');
        sb.append(Integer.toString(n));
        return new BasicHttpRequest("CONNECT", sb.toString(), HttpProtocolParams.getVersion(this.params));
    }
    
    protected RoutedRequest handleResponse(final RoutedRequest routedRequest, final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        final HttpRoute route = routedRequest.getRoute();
        final RequestWrapper request = routedRequest.getRequest();
        final HttpParams params = request.getParams();
        if (HttpClientParams.isAuthenticating(params)) {
            HttpHost targetHost = (HttpHost)httpContext.getAttribute("http.target_host");
            if (targetHost == null) {
                targetHost = route.getTargetHost();
            }
            if (targetHost.getPort() < 0) {
                targetHost = new HttpHost(targetHost.getHostName(), this.connManager.getSchemeRegistry().getScheme(targetHost).getDefaultPort(), targetHost.getSchemeName());
            }
            final boolean authenticationRequested = this.authenticator.isAuthenticationRequested(targetHost, httpResponse, this.targetAuthStrategy, this.targetAuthState, httpContext);
            HttpHost httpHost = route.getProxyHost();
            if (httpHost == null) {
                httpHost = route.getTargetHost();
            }
            final boolean authenticationRequested2 = this.authenticator.isAuthenticationRequested(httpHost, httpResponse, this.proxyAuthStrategy, this.proxyAuthState, httpContext);
            if (authenticationRequested && this.authenticator.authenticate(targetHost, httpResponse, this.targetAuthStrategy, this.targetAuthState, httpContext)) {
                return routedRequest;
            }
            if (authenticationRequested2 && this.authenticator.authenticate(httpHost, httpResponse, this.proxyAuthStrategy, this.proxyAuthState, httpContext)) {
                return routedRequest;
            }
        }
        if (!HttpClientParams.isRedirecting(params) || !this.redirectStrategy.isRedirected(request, httpResponse, httpContext)) {
            return null;
        }
        if (this.redirectCount >= this.maxRedirects) {
            throw new RedirectException("Maximum redirects (" + this.maxRedirects + ") exceeded");
        }
        ++this.redirectCount;
        this.virtualHost = null;
        final HttpUriRequest redirect = this.redirectStrategy.getRedirect(request, httpResponse, httpContext);
        redirect.setHeaders(request.getOriginal().getAllHeaders());
        final URI uri = redirect.getURI();
        final HttpHost host = URIUtils.extractHost(uri);
        if (host == null) {
            throw new ProtocolException("Redirect URI does not specify a valid host name: " + uri);
        }
        if (!route.getTargetHost().equals(host)) {
            this.log.debug("Resetting target auth state");
            this.targetAuthState.reset();
            final AuthScheme authScheme = this.proxyAuthState.getAuthScheme();
            if (authScheme != null && authScheme.isConnectionBased()) {
                this.log.debug("Resetting proxy auth state");
                this.proxyAuthState.reset();
            }
        }
        final RequestWrapper wrapRequest = this.wrapRequest(redirect);
        wrapRequest.setParams(params);
        final HttpRoute determineRoute = this.determineRoute(host, wrapRequest, httpContext);
        final RoutedRequest routedRequest2 = new RoutedRequest(wrapRequest, determineRoute);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Redirecting to '" + uri + "' via " + determineRoute);
        }
        return routedRequest2;
    }
    
    private void abortConnection() {
        final ManagedClientConnection managedConn = this.managedConn;
        if (managedConn != null) {
            this.managedConn = null;
            managedConn.abortConnection();
            managedConn.releaseConnection();
        }
    }
}
