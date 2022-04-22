package io.netty.handler.codec.spdy;

import io.netty.handler.codec.http.*;
import java.util.*;

public abstract class SpdyHeaders implements Iterable
{
    public static final SpdyHeaders EMPTY_HEADERS;
    
    public static String getHeader(final SpdyHeadersFrame spdyHeadersFrame, final String s) {
        return spdyHeadersFrame.headers().get(s);
    }
    
    public static String getHeader(final SpdyHeadersFrame spdyHeadersFrame, final String s, final String s2) {
        final String value = spdyHeadersFrame.headers().get(s);
        if (value == null) {
            return s2;
        }
        return value;
    }
    
    public static void setHeader(final SpdyHeadersFrame spdyHeadersFrame, final String s, final Object o) {
        spdyHeadersFrame.headers().set(s, o);
    }
    
    public static void setHeader(final SpdyHeadersFrame spdyHeadersFrame, final String s, final Iterable iterable) {
        spdyHeadersFrame.headers().set(s, iterable);
    }
    
    public static void addHeader(final SpdyHeadersFrame spdyHeadersFrame, final String s, final Object o) {
        spdyHeadersFrame.headers().add(s, o);
    }
    
    public static void removeHost(final SpdyHeadersFrame spdyHeadersFrame) {
        spdyHeadersFrame.headers().remove(":host");
    }
    
    public static String getHost(final SpdyHeadersFrame spdyHeadersFrame) {
        return spdyHeadersFrame.headers().get(":host");
    }
    
    public static void setHost(final SpdyHeadersFrame spdyHeadersFrame, final String s) {
        spdyHeadersFrame.headers().set(":host", s);
    }
    
    public static void removeMethod(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        spdyHeadersFrame.headers().remove(":method");
    }
    
    public static HttpMethod getMethod(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        return HttpMethod.valueOf(spdyHeadersFrame.headers().get(":method"));
    }
    
    public static void setMethod(final int n, final SpdyHeadersFrame spdyHeadersFrame, final HttpMethod httpMethod) {
        spdyHeadersFrame.headers().set(":method", httpMethod.name());
    }
    
    public static void removeScheme(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        spdyHeadersFrame.headers().remove(":scheme");
    }
    
    public static String getScheme(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        return spdyHeadersFrame.headers().get(":scheme");
    }
    
    public static void setScheme(final int n, final SpdyHeadersFrame spdyHeadersFrame, final String s) {
        spdyHeadersFrame.headers().set(":scheme", s);
    }
    
    public static void removeStatus(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        spdyHeadersFrame.headers().remove(":status");
    }
    
    public static HttpResponseStatus getStatus(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        final String value = spdyHeadersFrame.headers().get(":status");
        final int index = value.indexOf(32);
        if (index == -1) {
            return HttpResponseStatus.valueOf(Integer.parseInt(value));
        }
        final int int1 = Integer.parseInt(value.substring(0, index));
        final String substring = value.substring(index + 1);
        final HttpResponseStatus value2 = HttpResponseStatus.valueOf(int1);
        if (value2.reasonPhrase().equals(substring)) {
            return value2;
        }
        return new HttpResponseStatus(int1, substring);
    }
    
    public static void setStatus(final int n, final SpdyHeadersFrame spdyHeadersFrame, final HttpResponseStatus httpResponseStatus) {
        spdyHeadersFrame.headers().set(":status", httpResponseStatus.toString());
    }
    
    public static void removeUrl(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        spdyHeadersFrame.headers().remove(":path");
    }
    
    public static String getUrl(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        return spdyHeadersFrame.headers().get(":path");
    }
    
    public static void setUrl(final int n, final SpdyHeadersFrame spdyHeadersFrame, final String s) {
        spdyHeadersFrame.headers().set(":path", s);
    }
    
    public static void removeVersion(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        spdyHeadersFrame.headers().remove(":version");
    }
    
    public static HttpVersion getVersion(final int n, final SpdyHeadersFrame spdyHeadersFrame) {
        return HttpVersion.valueOf(spdyHeadersFrame.headers().get(":version"));
    }
    
    public static void setVersion(final int n, final SpdyHeadersFrame spdyHeadersFrame, final HttpVersion httpVersion) {
        spdyHeadersFrame.headers().set(":version", httpVersion.text());
    }
    
    @Override
    public Iterator iterator() {
        return this.entries().iterator();
    }
    
    public abstract String get(final String p0);
    
    public abstract List getAll(final String p0);
    
    public abstract List entries();
    
    public abstract boolean contains(final String p0);
    
    public abstract Set names();
    
    public abstract SpdyHeaders add(final String p0, final Object p1);
    
    public abstract SpdyHeaders add(final String p0, final Iterable p1);
    
    public abstract SpdyHeaders set(final String p0, final Object p1);
    
    public abstract SpdyHeaders set(final String p0, final Iterable p1);
    
    public abstract SpdyHeaders remove(final String p0);
    
    public abstract SpdyHeaders clear();
    
    public abstract boolean isEmpty();
    
    static {
        EMPTY_HEADERS = new SpdyHeaders() {
            @Override
            public List getAll(final String s) {
                return Collections.emptyList();
            }
            
            @Override
            public List entries() {
                return Collections.emptyList();
            }
            
            @Override
            public boolean contains(final String s) {
                return false;
            }
            
            @Override
            public boolean isEmpty() {
                return true;
            }
            
            @Override
            public Set names() {
                return Collections.emptySet();
            }
            
            @Override
            public SpdyHeaders add(final String s, final Object o) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders add(final String s, final Iterable iterable) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders set(final String s, final Object o) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders set(final String s, final Iterable iterable) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders remove(final String s) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public SpdyHeaders clear() {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public Iterator iterator() {
                return this.entries().iterator();
            }
            
            @Override
            public String get(final String s) {
                return null;
            }
        };
    }
    
    public static final class HttpNames
    {
        public static final String HOST = ":host";
        public static final String METHOD = ":method";
        public static final String PATH = ":path";
        public static final String SCHEME = ":scheme";
        public static final String STATUS = ":status";
        public static final String VERSION = ":version";
        
        private HttpNames() {
        }
    }
}
