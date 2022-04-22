package io.netty.handler.codec.spdy;

import io.netty.handler.codec.http.*;

public final class SpdyHttpHeaders
{
    private SpdyHttpHeaders() {
    }
    
    public static void removeStreamId(final HttpMessage httpMessage) {
        httpMessage.headers().remove("X-SPDY-Stream-ID");
    }
    
    public static int getStreamId(final HttpMessage httpMessage) {
        return HttpHeaders.getIntHeader(httpMessage, "X-SPDY-Stream-ID");
    }
    
    public static void setStreamId(final HttpMessage httpMessage, final int n) {
        HttpHeaders.setIntHeader(httpMessage, "X-SPDY-Stream-ID", n);
    }
    
    public static void removeAssociatedToStreamId(final HttpMessage httpMessage) {
        httpMessage.headers().remove("X-SPDY-Associated-To-Stream-ID");
    }
    
    public static int getAssociatedToStreamId(final HttpMessage httpMessage) {
        return HttpHeaders.getIntHeader(httpMessage, "X-SPDY-Associated-To-Stream-ID", 0);
    }
    
    public static void setAssociatedToStreamId(final HttpMessage httpMessage, final int n) {
        HttpHeaders.setIntHeader(httpMessage, "X-SPDY-Associated-To-Stream-ID", n);
    }
    
    public static void removePriority(final HttpMessage httpMessage) {
        httpMessage.headers().remove("X-SPDY-Priority");
    }
    
    public static byte getPriority(final HttpMessage httpMessage) {
        return (byte)HttpHeaders.getIntHeader(httpMessage, "X-SPDY-Priority", 0);
    }
    
    public static void setPriority(final HttpMessage httpMessage, final byte b) {
        HttpHeaders.setIntHeader(httpMessage, "X-SPDY-Priority", b);
    }
    
    public static void removeUrl(final HttpMessage httpMessage) {
        httpMessage.headers().remove("X-SPDY-URL");
    }
    
    public static String getUrl(final HttpMessage httpMessage) {
        return httpMessage.headers().get("X-SPDY-URL");
    }
    
    public static void setUrl(final HttpMessage httpMessage, final String s) {
        httpMessage.headers().set("X-SPDY-URL", s);
    }
    
    public static void removeScheme(final HttpMessage httpMessage) {
        httpMessage.headers().remove("X-SPDY-Scheme");
    }
    
    public static String getScheme(final HttpMessage httpMessage) {
        return httpMessage.headers().get("X-SPDY-Scheme");
    }
    
    public static void setScheme(final HttpMessage httpMessage, final String s) {
        httpMessage.headers().set("X-SPDY-Scheme", s);
    }
    
    public static final class Names
    {
        public static final String STREAM_ID = "X-SPDY-Stream-ID";
        public static final String ASSOCIATED_TO_STREAM_ID = "X-SPDY-Associated-To-Stream-ID";
        public static final String PRIORITY = "X-SPDY-Priority";
        public static final String URL = "X-SPDY-URL";
        public static final String SCHEME = "X-SPDY-Scheme";
        
        private Names() {
        }
    }
}
