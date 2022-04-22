package io.netty.handler.codec.http;

import java.nio.charset.*;
import java.util.*;
import java.net.*;

public class QueryStringEncoder
{
    private final Charset charset;
    private final String uri;
    private final List params;
    
    public QueryStringEncoder(final String s) {
        this(s, HttpConstants.DEFAULT_CHARSET);
    }
    
    public QueryStringEncoder(final String uri, final Charset charset) {
        this.params = new ArrayList();
        if (uri == null) {
            throw new NullPointerException("getUri");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.uri = uri;
        this.charset = charset;
    }
    
    public void addParam(final String s, final String s2) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        this.params.add(new Param(s, s2));
    }
    
    public URI toUri() throws URISyntaxException {
        return new URI(this.toString());
    }
    
    @Override
    public String toString() {
        if (this.params.isEmpty()) {
            return this.uri;
        }
        final StringBuilder append = new StringBuilder(this.uri).append('?');
        while (0 < this.params.size()) {
            final Param param = this.params.get(0);
            append.append(encodeComponent(param.name, this.charset));
            if (param.value != null) {
                append.append('=');
                append.append(encodeComponent(param.value, this.charset));
            }
            if (0 != this.params.size() - 1) {
                append.append('&');
            }
            int n = 0;
            ++n;
        }
        return append.toString();
    }
    
    private static String encodeComponent(final String s, final Charset charset) {
        return URLEncoder.encode(s, charset.name()).replace("+", "%20");
    }
    
    private static final class Param
    {
        final String name;
        final String value;
        
        Param(final String name, final String value) {
            this.value = value;
            this.name = name;
        }
    }
}
