package org.apache.http.impl.execchain;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.protocol.*;
import org.apache.http.protocol.*;
import org.apache.http.client.*;
import org.apache.http.client.utils.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.client.config.*;
import org.apache.http.client.methods.*;
import java.net.*;
import org.apache.http.auth.*;
import java.io.*;
import org.apache.http.*;

@ThreadSafe
public class RedirectExec implements ClientExecChain
{
    private final Log log;
    private final ClientExecChain requestExecutor;
    private final RedirectStrategy redirectStrategy;
    private final HttpRoutePlanner routePlanner;
    
    public RedirectExec(final ClientExecChain requestExecutor, final HttpRoutePlanner routePlanner, final RedirectStrategy redirectStrategy) {
        this.log = LogFactory.getLog(this.getClass());
        Args.notNull(requestExecutor, "HTTP client request executor");
        Args.notNull(routePlanner, "HTTP route planner");
        Args.notNull(redirectStrategy, "HTTP redirect strategy");
        this.requestExecutor = requestExecutor;
        this.routePlanner = routePlanner;
        this.redirectStrategy = redirectStrategy;
    }
    
    public CloseableHttpResponse execute(final HttpRoute httpRoute, final HttpRequestWrapper httpRequestWrapper, final HttpClientContext httpClientContext, final HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        final List redirectLocations = httpClientContext.getRedirectLocations();
        if (redirectLocations != null) {
            redirectLocations.clear();
        }
        final RequestConfig requestConfig = httpClientContext.getRequestConfig();
        final int n = (requestConfig.getMaxRedirects() > 0) ? requestConfig.getMaxRedirects() : 50;
        HttpRoute determineRoute = httpRoute;
        HttpRequestWrapper wrap = httpRequestWrapper;
        while (true) {
            final CloseableHttpResponse execute = this.requestExecutor.execute(determineRoute, wrap, httpClientContext, httpExecutionAware);
            if (!requestConfig.isRedirectsEnabled() || !this.redirectStrategy.isRedirected(wrap, execute, httpClientContext)) {
                return execute;
            }
            if (0 >= n) {
                throw new RedirectException("Maximum redirects (" + n + ") exceeded");
            }
            int n2 = 0;
            ++n2;
            final HttpUriRequest redirect = this.redirectStrategy.getRedirect(wrap, execute, httpClientContext);
            if (!redirect.headerIterator().hasNext()) {
                redirect.setHeaders(httpRequestWrapper.getOriginal().getAllHeaders());
            }
            wrap = HttpRequestWrapper.wrap(redirect);
            if (wrap instanceof HttpEntityEnclosingRequest) {
                Proxies.enhanceEntity((HttpEntityEnclosingRequest)wrap);
            }
            final URI uri = wrap.getURI();
            final HttpHost host = URIUtils.extractHost(uri);
            if (host == null) {
                throw new ProtocolException("Redirect URI does not specify a valid host name: " + uri);
            }
            if (!determineRoute.getTargetHost().equals(host)) {
                final AuthState targetAuthState = httpClientContext.getTargetAuthState();
                if (targetAuthState != null) {
                    this.log.debug("Resetting target auth state");
                    targetAuthState.reset();
                }
                final AuthState proxyAuthState = httpClientContext.getProxyAuthState();
                if (proxyAuthState != null) {
                    final AuthScheme authScheme = proxyAuthState.getAuthScheme();
                    if (authScheme != null && authScheme.isConnectionBased()) {
                        this.log.debug("Resetting proxy auth state");
                        proxyAuthState.reset();
                    }
                }
            }
            determineRoute = this.routePlanner.determineRoute(host, wrap, httpClientContext);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Redirecting to '" + uri + "' via " + determineRoute);
            }
            EntityUtils.consume(execute.getEntity());
            execute.close();
        }
    }
}
