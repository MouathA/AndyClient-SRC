package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.impl.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.entity.*;
import org.apache.http.*;

@Immutable
public class HttpService
{
    private HttpParams params;
    private HttpProcessor processor;
    private HttpRequestHandlerMapper handlerMapper;
    private ConnectionReuseStrategy connStrategy;
    private HttpResponseFactory responseFactory;
    private HttpExpectationVerifier expectationVerifier;
    
    @Deprecated
    public HttpService(final HttpProcessor httpProcessor, final ConnectionReuseStrategy connectionReuseStrategy, final HttpResponseFactory httpResponseFactory, final HttpRequestHandlerResolver httpRequestHandlerResolver, final HttpExpectationVerifier httpExpectationVerifier, final HttpParams params) {
        this(httpProcessor, connectionReuseStrategy, httpResponseFactory, new HttpRequestHandlerResolverAdapter(httpRequestHandlerResolver), httpExpectationVerifier);
        this.params = params;
    }
    
    @Deprecated
    public HttpService(final HttpProcessor httpProcessor, final ConnectionReuseStrategy connectionReuseStrategy, final HttpResponseFactory httpResponseFactory, final HttpRequestHandlerResolver httpRequestHandlerResolver, final HttpParams params) {
        this(httpProcessor, connectionReuseStrategy, httpResponseFactory, new HttpRequestHandlerResolverAdapter(httpRequestHandlerResolver), null);
        this.params = params;
    }
    
    @Deprecated
    public HttpService(final HttpProcessor httpProcessor, final ConnectionReuseStrategy connReuseStrategy, final HttpResponseFactory responseFactory) {
        this.params = null;
        this.processor = null;
        this.handlerMapper = null;
        this.connStrategy = null;
        this.responseFactory = null;
        this.expectationVerifier = null;
        this.setHttpProcessor(httpProcessor);
        this.setConnReuseStrategy(connReuseStrategy);
        this.setResponseFactory(responseFactory);
    }
    
    public HttpService(final HttpProcessor httpProcessor, final ConnectionReuseStrategy connectionReuseStrategy, final HttpResponseFactory httpResponseFactory, final HttpRequestHandlerMapper handlerMapper, final HttpExpectationVerifier expectationVerifier) {
        this.params = null;
        this.processor = null;
        this.handlerMapper = null;
        this.connStrategy = null;
        this.responseFactory = null;
        this.expectationVerifier = null;
        this.processor = (HttpProcessor)Args.notNull(httpProcessor, "HTTP processor");
        this.connStrategy = ((connectionReuseStrategy != null) ? connectionReuseStrategy : DefaultConnectionReuseStrategy.INSTANCE);
        this.responseFactory = ((httpResponseFactory != null) ? httpResponseFactory : DefaultHttpResponseFactory.INSTANCE);
        this.handlerMapper = handlerMapper;
        this.expectationVerifier = expectationVerifier;
    }
    
    public HttpService(final HttpProcessor httpProcessor, final ConnectionReuseStrategy connectionReuseStrategy, final HttpResponseFactory httpResponseFactory, final HttpRequestHandlerMapper httpRequestHandlerMapper) {
        this(httpProcessor, connectionReuseStrategy, httpResponseFactory, httpRequestHandlerMapper, null);
    }
    
    public HttpService(final HttpProcessor httpProcessor, final HttpRequestHandlerMapper httpRequestHandlerMapper) {
        this(httpProcessor, null, null, httpRequestHandlerMapper, null);
    }
    
    @Deprecated
    public void setHttpProcessor(final HttpProcessor processor) {
        Args.notNull(processor, "HTTP processor");
        this.processor = processor;
    }
    
    @Deprecated
    public void setConnReuseStrategy(final ConnectionReuseStrategy connStrategy) {
        Args.notNull(connStrategy, "Connection reuse strategy");
        this.connStrategy = connStrategy;
    }
    
    @Deprecated
    public void setResponseFactory(final HttpResponseFactory responseFactory) {
        Args.notNull(responseFactory, "Response factory");
        this.responseFactory = responseFactory;
    }
    
    @Deprecated
    public void setParams(final HttpParams params) {
        this.params = params;
    }
    
    @Deprecated
    public void setHandlerResolver(final HttpRequestHandlerResolver httpRequestHandlerResolver) {
        this.handlerMapper = new HttpRequestHandlerResolverAdapter(httpRequestHandlerResolver);
    }
    
    @Deprecated
    public void setExpectationVerifier(final HttpExpectationVerifier expectationVerifier) {
        this.expectationVerifier = expectationVerifier;
    }
    
    @Deprecated
    public HttpParams getParams() {
        return this.params;
    }
    
    public void handleRequest(final HttpServerConnection httpServerConnection, final HttpContext httpContext) throws IOException, HttpException {
        httpContext.setAttribute("http.connection", httpServerConnection);
        HttpResponse httpResponse = null;
        final HttpRequest receiveRequestHeader = httpServerConnection.receiveRequestHeader();
        if (receiveRequestHeader instanceof HttpEntityEnclosingRequest) {
            if (((HttpEntityEnclosingRequest)receiveRequestHeader).expectContinue()) {
                httpResponse = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, 100, httpContext);
                if (this.expectationVerifier != null) {
                    this.expectationVerifier.verify(receiveRequestHeader, httpResponse, httpContext);
                }
                if (httpResponse.getStatusLine().getStatusCode() < 200) {
                    httpServerConnection.sendResponseHeader(httpResponse);
                    httpServerConnection.flush();
                    httpResponse = null;
                    httpServerConnection.receiveRequestEntity((HttpEntityEnclosingRequest)receiveRequestHeader);
                }
            }
            else {
                httpServerConnection.receiveRequestEntity((HttpEntityEnclosingRequest)receiveRequestHeader);
            }
        }
        httpContext.setAttribute("http.request", receiveRequestHeader);
        if (httpResponse == null) {
            httpResponse = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, 200, httpContext);
            this.processor.process(receiveRequestHeader, httpContext);
            this.doService(receiveRequestHeader, httpResponse, httpContext);
        }
        if (receiveRequestHeader instanceof HttpEntityEnclosingRequest) {
            EntityUtils.consume(((HttpEntityEnclosingRequest)receiveRequestHeader).getEntity());
        }
        httpContext.setAttribute("http.response", httpResponse);
        this.processor.process(httpResponse, httpContext);
        httpServerConnection.sendResponseHeader(httpResponse);
        httpServerConnection.sendResponseEntity(httpResponse);
        httpServerConnection.flush();
        if (!this.connStrategy.keepAlive(httpResponse, httpContext)) {
            httpServerConnection.close();
        }
    }
    
    protected void handleException(final HttpException ex, final HttpResponse httpResponse) {
        if (ex instanceof MethodNotSupportedException) {
            httpResponse.setStatusCode(501);
        }
        else if (ex instanceof UnsupportedHttpVersionException) {
            httpResponse.setStatusCode(505);
        }
        else if (ex instanceof ProtocolException) {
            httpResponse.setStatusCode(400);
        }
        else {
            httpResponse.setStatusCode(500);
        }
        String s = ex.getMessage();
        if (s == null) {
            s = ex.toString();
        }
        final ByteArrayEntity entity = new ByteArrayEntity(EncodingUtils.getAsciiBytes(s));
        entity.setContentType("text/plain; charset=US-ASCII");
        httpResponse.setEntity(entity);
    }
    
    protected void doService(final HttpRequest httpRequest, final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        HttpRequestHandler lookup = null;
        if (this.handlerMapper != null) {
            lookup = this.handlerMapper.lookup(httpRequest);
        }
        if (lookup != null) {
            lookup.handle(httpRequest, httpResponse, httpContext);
        }
        else {
            httpResponse.setStatusCode(501);
        }
    }
    
    @Deprecated
    private static class HttpRequestHandlerResolverAdapter implements HttpRequestHandlerMapper
    {
        private final HttpRequestHandlerResolver resolver;
        
        public HttpRequestHandlerResolverAdapter(final HttpRequestHandlerResolver resolver) {
            this.resolver = resolver;
        }
        
        public HttpRequestHandler lookup(final HttpRequest httpRequest) {
            return this.resolver.lookup(httpRequest.getRequestLine().getUri());
        }
    }
}
