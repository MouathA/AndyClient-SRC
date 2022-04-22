package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;
import org.apache.http.*;
import java.util.*;

@Deprecated
@NotThreadSafe
public final class BasicHttpProcessor implements HttpProcessor, HttpRequestInterceptorList, HttpResponseInterceptorList, Cloneable
{
    protected final List requestInterceptors;
    protected final List responseInterceptors;
    
    public BasicHttpProcessor() {
        this.requestInterceptors = new ArrayList();
        this.responseInterceptors = new ArrayList();
    }
    
    public void addRequestInterceptor(final HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(httpRequestInterceptor);
    }
    
    public void addRequestInterceptor(final HttpRequestInterceptor httpRequestInterceptor, final int n) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(n, httpRequestInterceptor);
    }
    
    public void addResponseInterceptor(final HttpResponseInterceptor httpResponseInterceptor, final int n) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(n, httpResponseInterceptor);
    }
    
    public void removeRequestInterceptorByClass(final Class clazz) {
        final Iterator<Object> iterator = (Iterator<Object>)this.requestInterceptors.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getClass().equals(clazz)) {
                iterator.remove();
            }
        }
    }
    
    public void removeResponseInterceptorByClass(final Class clazz) {
        final Iterator<Object> iterator = (Iterator<Object>)this.responseInterceptors.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getClass().equals(clazz)) {
                iterator.remove();
            }
        }
    }
    
    public final void addInterceptor(final HttpRequestInterceptor httpRequestInterceptor) {
        this.addRequestInterceptor(httpRequestInterceptor);
    }
    
    public final void addInterceptor(final HttpRequestInterceptor httpRequestInterceptor, final int n) {
        this.addRequestInterceptor(httpRequestInterceptor, n);
    }
    
    public int getRequestInterceptorCount() {
        return this.requestInterceptors.size();
    }
    
    public HttpRequestInterceptor getRequestInterceptor(final int n) {
        if (n < 0 || n >= this.requestInterceptors.size()) {
            return null;
        }
        return this.requestInterceptors.get(n);
    }
    
    public void clearRequestInterceptors() {
        this.requestInterceptors.clear();
    }
    
    public void addResponseInterceptor(final HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(httpResponseInterceptor);
    }
    
    public final void addInterceptor(final HttpResponseInterceptor httpResponseInterceptor) {
        this.addResponseInterceptor(httpResponseInterceptor);
    }
    
    public final void addInterceptor(final HttpResponseInterceptor httpResponseInterceptor, final int n) {
        this.addResponseInterceptor(httpResponseInterceptor, n);
    }
    
    public int getResponseInterceptorCount() {
        return this.responseInterceptors.size();
    }
    
    public HttpResponseInterceptor getResponseInterceptor(final int n) {
        if (n < 0 || n >= this.responseInterceptors.size()) {
            return null;
        }
        return this.responseInterceptors.get(n);
    }
    
    public void clearResponseInterceptors() {
        this.responseInterceptors.clear();
    }
    
    public void setInterceptors(final List list) {
        Args.notNull(list, "Inteceptor list");
        this.requestInterceptors.clear();
        this.responseInterceptors.clear();
        for (final HttpRequestInterceptor next : list) {
            if (next instanceof HttpRequestInterceptor) {
                this.addInterceptor(next);
            }
            if (next instanceof HttpResponseInterceptor) {
                this.addInterceptor((HttpResponseInterceptor)next);
            }
        }
    }
    
    public void clearInterceptors() {
        this.clearRequestInterceptors();
        this.clearResponseInterceptors();
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, HttpException {
        final Iterator<HttpRequestInterceptor> iterator = this.requestInterceptors.iterator();
        while (iterator.hasNext()) {
            iterator.next().process(httpRequest, httpContext);
        }
    }
    
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws IOException, HttpException {
        final Iterator<HttpResponseInterceptor> iterator = this.responseInterceptors.iterator();
        while (iterator.hasNext()) {
            iterator.next().process(httpResponse, httpContext);
        }
    }
    
    protected void copyInterceptors(final BasicHttpProcessor basicHttpProcessor) {
        basicHttpProcessor.requestInterceptors.clear();
        basicHttpProcessor.requestInterceptors.addAll(this.requestInterceptors);
        basicHttpProcessor.responseInterceptors.clear();
        basicHttpProcessor.responseInterceptors.addAll(this.responseInterceptors);
    }
    
    public BasicHttpProcessor copy() {
        final BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        this.copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final BasicHttpProcessor basicHttpProcessor = (BasicHttpProcessor)super.clone();
        this.copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }
}
