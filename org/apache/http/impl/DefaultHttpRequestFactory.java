package org.apache.http.impl;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.*;

@Immutable
public class DefaultHttpRequestFactory implements HttpRequestFactory
{
    public static final DefaultHttpRequestFactory INSTANCE;
    private static final String[] RFC2616_ENTITY_ENC_METHODS;
    private static final String[] RFC2616_SPECIAL_METHODS;
    
    private static boolean isOneOf(final String[] array, final String s) {
        while (0 < array.length) {
            if (array[0].equalsIgnoreCase(s)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public HttpRequest newHttpRequest(final RequestLine requestLine) throws MethodNotSupportedException {
        Args.notNull(requestLine, "Request line");
        final String method = requestLine.getMethod();
        if (isOneOf(DefaultHttpRequestFactory.RFC2616_COMMON_METHODS, method)) {
            return new BasicHttpRequest(requestLine);
        }
        if (isOneOf(DefaultHttpRequestFactory.RFC2616_ENTITY_ENC_METHODS, method)) {
            return new BasicHttpEntityEnclosingRequest(requestLine);
        }
        if (isOneOf(DefaultHttpRequestFactory.RFC2616_SPECIAL_METHODS, method)) {
            return new BasicHttpRequest(requestLine);
        }
        throw new MethodNotSupportedException(method + " method not supported");
    }
    
    public HttpRequest newHttpRequest(final String s, final String s2) throws MethodNotSupportedException {
        if (isOneOf(DefaultHttpRequestFactory.RFC2616_COMMON_METHODS, s)) {
            return new BasicHttpRequest(s, s2);
        }
        if (isOneOf(DefaultHttpRequestFactory.RFC2616_ENTITY_ENC_METHODS, s)) {
            return new BasicHttpEntityEnclosingRequest(s, s2);
        }
        if (isOneOf(DefaultHttpRequestFactory.RFC2616_SPECIAL_METHODS, s)) {
            return new BasicHttpRequest(s, s2);
        }
        throw new MethodNotSupportedException(s + " method not supported");
    }
    
    static {
        INSTANCE = new DefaultHttpRequestFactory();
        DefaultHttpRequestFactory.RFC2616_COMMON_METHODS = new String[] { "GET" };
        RFC2616_ENTITY_ENC_METHODS = new String[] { "POST", "PUT" };
        RFC2616_SPECIAL_METHODS = new String[] { "HEAD", "OPTIONS", "DELETE", "TRACE", "CONNECT" };
    }
}
