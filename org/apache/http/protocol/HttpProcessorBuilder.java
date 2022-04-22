package org.apache.http.protocol;

import org.apache.http.*;
import java.util.*;

public class HttpProcessorBuilder
{
    private ChainBuilder requestChainBuilder;
    private ChainBuilder responseChainBuilder;
    
    public static HttpProcessorBuilder create() {
        return new HttpProcessorBuilder();
    }
    
    HttpProcessorBuilder() {
    }
    
    private ChainBuilder getRequestChainBuilder() {
        if (this.requestChainBuilder == null) {
            this.requestChainBuilder = new ChainBuilder();
        }
        return this.requestChainBuilder;
    }
    
    private ChainBuilder getResponseChainBuilder() {
        if (this.responseChainBuilder == null) {
            this.responseChainBuilder = new ChainBuilder();
        }
        return this.responseChainBuilder;
    }
    
    public HttpProcessorBuilder addFirst(final HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        this.getRequestChainBuilder().addFirst(httpRequestInterceptor);
        return this;
    }
    
    public HttpProcessorBuilder addLast(final HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        this.getRequestChainBuilder().addLast(httpRequestInterceptor);
        return this;
    }
    
    public HttpProcessorBuilder add(final HttpRequestInterceptor httpRequestInterceptor) {
        return this.addLast(httpRequestInterceptor);
    }
    
    public HttpProcessorBuilder addAllFirst(final HttpRequestInterceptor... array) {
        if (array == null) {
            return this;
        }
        this.getRequestChainBuilder().addAllFirst((Object[])array);
        return this;
    }
    
    public HttpProcessorBuilder addAllLast(final HttpRequestInterceptor... array) {
        if (array == null) {
            return this;
        }
        this.getRequestChainBuilder().addAllLast((Object[])array);
        return this;
    }
    
    public HttpProcessorBuilder addAll(final HttpRequestInterceptor... array) {
        return this.addAllLast(array);
    }
    
    public HttpProcessorBuilder addFirst(final HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        this.getResponseChainBuilder().addFirst(httpResponseInterceptor);
        return this;
    }
    
    public HttpProcessorBuilder addLast(final HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        this.getResponseChainBuilder().addLast(httpResponseInterceptor);
        return this;
    }
    
    public HttpProcessorBuilder add(final HttpResponseInterceptor httpResponseInterceptor) {
        return this.addLast(httpResponseInterceptor);
    }
    
    public HttpProcessorBuilder addAllFirst(final HttpResponseInterceptor... array) {
        if (array == null) {
            return this;
        }
        this.getResponseChainBuilder().addAllFirst((Object[])array);
        return this;
    }
    
    public HttpProcessorBuilder addAllLast(final HttpResponseInterceptor... array) {
        if (array == null) {
            return this;
        }
        this.getResponseChainBuilder().addAllLast((Object[])array);
        return this;
    }
    
    public HttpProcessorBuilder addAll(final HttpResponseInterceptor... array) {
        return this.addAllLast(array);
    }
    
    public HttpProcessor build() {
        return new ImmutableHttpProcessor((this.requestChainBuilder != null) ? this.requestChainBuilder.build() : null, (this.responseChainBuilder != null) ? this.responseChainBuilder.build() : null);
    }
}
