package io.netty.handler.codec.http;

import io.netty.util.*;
import io.netty.buffer.*;
import java.util.*;

public class HttpMethod implements Comparable
{
    public static final HttpMethod OPTIONS;
    public static final HttpMethod GET;
    public static final HttpMethod HEAD;
    public static final HttpMethod POST;
    public static final HttpMethod PUT;
    public static final HttpMethod PATCH;
    public static final HttpMethod DELETE;
    public static final HttpMethod TRACE;
    public static final HttpMethod CONNECT;
    private static final Map methodMap;
    private final String name;
    private final byte[] bytes;
    
    public static HttpMethod valueOf(String trim) {
        if (trim == null) {
            throw new NullPointerException("name");
        }
        trim = trim.trim();
        if (trim.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        final HttpMethod httpMethod = HttpMethod.methodMap.get(trim);
        if (httpMethod != null) {
            return httpMethod;
        }
        return new HttpMethod(trim);
    }
    
    public HttpMethod(final String s) {
        this(s, false);
    }
    
    private HttpMethod(String trim, final boolean b) {
        if (trim == null) {
            throw new NullPointerException("name");
        }
        trim = trim.trim();
        if (trim.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        while (0 < trim.length()) {
            if (Character.isISOControl(trim.charAt(0)) || Character.isWhitespace(trim.charAt(0))) {
                throw new IllegalArgumentException("invalid character in name");
            }
            int n = 0;
            ++n;
        }
        this.name = trim;
        if (b) {
            this.bytes = trim.getBytes(CharsetUtil.US_ASCII);
        }
        else {
            this.bytes = null;
        }
    }
    
    public String name() {
        return this.name;
    }
    
    @Override
    public int hashCode() {
        return this.name().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof HttpMethod && this.name().equals(((HttpMethod)o).name());
    }
    
    @Override
    public String toString() {
        return this.name();
    }
    
    public int compareTo(final HttpMethod httpMethod) {
        return this.name().compareTo(httpMethod.name());
    }
    
    void encode(final ByteBuf byteBuf) {
        if (this.bytes == null) {
            HttpHeaders.encodeAscii0(this.name, byteBuf);
        }
        else {
            byteBuf.writeBytes(this.bytes);
        }
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((HttpMethod)o);
    }
    
    static {
        OPTIONS = new HttpMethod("OPTIONS", true);
        GET = new HttpMethod("GET", true);
        HEAD = new HttpMethod("HEAD", true);
        POST = new HttpMethod("POST", true);
        PUT = new HttpMethod("PUT", true);
        PATCH = new HttpMethod("PATCH", true);
        DELETE = new HttpMethod("DELETE", true);
        TRACE = new HttpMethod("TRACE", true);
        CONNECT = new HttpMethod("CONNECT", true);
        (methodMap = new HashMap()).put(HttpMethod.OPTIONS.toString(), HttpMethod.OPTIONS);
        HttpMethod.methodMap.put(HttpMethod.GET.toString(), HttpMethod.GET);
        HttpMethod.methodMap.put(HttpMethod.HEAD.toString(), HttpMethod.HEAD);
        HttpMethod.methodMap.put(HttpMethod.POST.toString(), HttpMethod.POST);
        HttpMethod.methodMap.put(HttpMethod.PUT.toString(), HttpMethod.PUT);
        HttpMethod.methodMap.put(HttpMethod.PATCH.toString(), HttpMethod.PATCH);
        HttpMethod.methodMap.put(HttpMethod.DELETE.toString(), HttpMethod.DELETE);
        HttpMethod.methodMap.put(HttpMethod.TRACE.toString(), HttpMethod.TRACE);
        HttpMethod.methodMap.put(HttpMethod.CONNECT.toString(), HttpMethod.CONNECT);
    }
}
