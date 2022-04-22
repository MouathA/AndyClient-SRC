package org.apache.http.protocol;

import org.apache.http.annotation.*;
import java.util.*;
import java.io.*;
import org.apache.http.*;

@ThreadSafe
public final class ImmutableHttpProcessor implements HttpProcessor
{
    private final HttpRequestInterceptor[] requestInterceptors;
    private final HttpResponseInterceptor[] responseInterceptors;
    
    public ImmutableHttpProcessor(final HttpRequestInterceptor[] array, final HttpResponseInterceptor[] array2) {
        if (array != null) {
            final int length = array.length;
            System.arraycopy(array, 0, this.requestInterceptors = new HttpRequestInterceptor[length], 0, length);
        }
        else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (array2 != null) {
            final int length2 = array2.length;
            System.arraycopy(array2, 0, this.responseInterceptors = new HttpResponseInterceptor[length2], 0, length2);
        }
        else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }
    
    public ImmutableHttpProcessor(final List list, final List list2) {
        if (list != null) {
            this.requestInterceptors = list.toArray(new HttpRequestInterceptor[list.size()]);
        }
        else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (list2 != null) {
            this.responseInterceptors = list2.toArray(new HttpResponseInterceptor[list2.size()]);
        }
        else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }
    
    @Deprecated
    public ImmutableHttpProcessor(final HttpRequestInterceptorList list, final HttpResponseInterceptorList list2) {
        int n = 0;
        if (list != null) {
            final int requestInterceptorCount = list.getRequestInterceptorCount();
            this.requestInterceptors = new HttpRequestInterceptor[requestInterceptorCount];
            while (0 < requestInterceptorCount) {
                this.requestInterceptors[0] = list.getRequestInterceptor(0);
                ++n;
            }
        }
        else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (list2 != null) {
            final int responseInterceptorCount = list2.getResponseInterceptorCount();
            this.responseInterceptors = new HttpResponseInterceptor[responseInterceptorCount];
            while (0 < responseInterceptorCount) {
                this.responseInterceptors[0] = list2.getResponseInterceptor(0);
                ++n;
            }
        }
        else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }
    
    public ImmutableHttpProcessor(final HttpRequestInterceptor... array) {
        this(array, null);
    }
    
    public ImmutableHttpProcessor(final HttpResponseInterceptor... array) {
        this(null, array);
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws IOException, HttpException {
        final HttpRequestInterceptor[] requestInterceptors = this.requestInterceptors;
        while (0 < requestInterceptors.length) {
            requestInterceptors[0].process(httpRequest, httpContext);
            int n = 0;
            ++n;
        }
    }
    
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws IOException, HttpException {
        final HttpResponseInterceptor[] responseInterceptors = this.responseInterceptors;
        while (0 < responseInterceptors.length) {
            responseInterceptors[0].process(httpResponse, httpContext);
            int n = 0;
            ++n;
        }
    }
}
