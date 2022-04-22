package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.*;
import java.io.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.client.protocol.*;
import org.apache.http.*;

@Immutable
public class DefaultHttpRequestRetryHandler implements HttpRequestRetryHandler
{
    public static final DefaultHttpRequestRetryHandler INSTANCE;
    private final int retryCount;
    private final boolean requestSentRetryEnabled;
    private final Set nonRetriableClasses;
    
    protected DefaultHttpRequestRetryHandler(final int retryCount, final boolean requestSentRetryEnabled, final Collection collection) {
        this.retryCount = retryCount;
        this.requestSentRetryEnabled = requestSentRetryEnabled;
        this.nonRetriableClasses = new HashSet();
        final Iterator<Class<?>> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.nonRetriableClasses.add(iterator.next());
        }
    }
    
    public DefaultHttpRequestRetryHandler(final int n, final boolean b) {
        this(n, b, Arrays.asList(InterruptedIOException.class, UnknownHostException.class, ConnectException.class, SSLException.class));
    }
    
    public DefaultHttpRequestRetryHandler() {
        this(3, false);
    }
    
    public boolean retryRequest(final IOException ex, final int n, final HttpContext httpContext) {
        Args.notNull(ex, "Exception parameter");
        Args.notNull(httpContext, "HTTP context");
        if (n > this.retryCount) {
            return false;
        }
        if (this.nonRetriableClasses.contains(ex.getClass())) {
            return false;
        }
        final Iterator<Class> iterator = this.nonRetriableClasses.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isInstance(ex)) {
                return false;
            }
        }
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        final HttpRequest request = adapt.getRequest();
        return request == 0 && (request == 0 || (!adapt.isRequestSent() || this.requestSentRetryEnabled));
    }
    
    public boolean isRequestSentRetryEnabled() {
        return this.requestSentRetryEnabled;
    }
    
    public int getRetryCount() {
        return this.retryCount;
    }
    
    static {
        INSTANCE = new DefaultHttpRequestRetryHandler();
    }
}
