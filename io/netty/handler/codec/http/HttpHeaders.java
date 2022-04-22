package io.netty.handler.codec.http;

import java.text.*;
import io.netty.buffer.*;
import java.util.*;

public abstract class HttpHeaders implements Iterable
{
    private static final byte[] HEADER_SEPERATOR;
    private static final byte[] CRLF;
    private static final CharSequence CONTENT_LENGTH_ENTITY;
    private static final CharSequence CONNECTION_ENTITY;
    private static final CharSequence CLOSE_ENTITY;
    private static final CharSequence KEEP_ALIVE_ENTITY;
    private static final CharSequence HOST_ENTITY;
    private static final CharSequence DATE_ENTITY;
    private static final CharSequence EXPECT_ENTITY;
    private static final CharSequence CONTINUE_ENTITY;
    private static final CharSequence TRANSFER_ENCODING_ENTITY;
    private static final CharSequence CHUNKED_ENTITY;
    private static final CharSequence SEC_WEBSOCKET_KEY1_ENTITY;
    private static final CharSequence SEC_WEBSOCKET_KEY2_ENTITY;
    private static final CharSequence SEC_WEBSOCKET_ORIGIN_ENTITY;
    private static final CharSequence SEC_WEBSOCKET_LOCATION_ENTITY;
    public static final HttpHeaders EMPTY_HEADERS;
    
    public static boolean isKeepAlive(final HttpMessage httpMessage) {
        final String value = httpMessage.headers().get(HttpHeaders.CONNECTION_ENTITY);
        if (value != null && equalsIgnoreCase(HttpHeaders.CLOSE_ENTITY, value)) {
            return false;
        }
        if (httpMessage.getProtocolVersion().isKeepAliveDefault()) {
            return !equalsIgnoreCase(HttpHeaders.CLOSE_ENTITY, value);
        }
        return equalsIgnoreCase(HttpHeaders.KEEP_ALIVE_ENTITY, value);
    }
    
    public static void setKeepAlive(final HttpMessage httpMessage, final boolean b) {
        final HttpHeaders headers = httpMessage.headers();
        if (httpMessage.getProtocolVersion().isKeepAliveDefault()) {
            if (b) {
                headers.remove(HttpHeaders.CONNECTION_ENTITY);
            }
            else {
                headers.set(HttpHeaders.CONNECTION_ENTITY, HttpHeaders.CLOSE_ENTITY);
            }
        }
        else if (b) {
            headers.set(HttpHeaders.CONNECTION_ENTITY, HttpHeaders.KEEP_ALIVE_ENTITY);
        }
        else {
            headers.remove(HttpHeaders.CONNECTION_ENTITY);
        }
    }
    
    public static String getHeader(final HttpMessage httpMessage, final String s) {
        return httpMessage.headers().get(s);
    }
    
    public static String getHeader(final HttpMessage httpMessage, final CharSequence charSequence) {
        return httpMessage.headers().get(charSequence);
    }
    
    public static String getHeader(final HttpMessage httpMessage, final String s, final String s2) {
        return getHeader(httpMessage, (CharSequence)s, s2);
    }
    
    public static String getHeader(final HttpMessage httpMessage, final CharSequence charSequence, final String s) {
        final String value = httpMessage.headers().get(charSequence);
        if (value == null) {
            return s;
        }
        return value;
    }
    
    public static void setHeader(final HttpMessage httpMessage, final String s, final Object o) {
        httpMessage.headers().set(s, o);
    }
    
    public static void setHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Object o) {
        httpMessage.headers().set(charSequence, o);
    }
    
    public static void setHeader(final HttpMessage httpMessage, final String s, final Iterable iterable) {
        httpMessage.headers().set(s, iterable);
    }
    
    public static void setHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Iterable iterable) {
        httpMessage.headers().set(charSequence, iterable);
    }
    
    public static void addHeader(final HttpMessage httpMessage, final String s, final Object o) {
        httpMessage.headers().add(s, o);
    }
    
    public static void addHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Object o) {
        httpMessage.headers().add(charSequence, o);
    }
    
    public static void removeHeader(final HttpMessage httpMessage, final String s) {
        httpMessage.headers().remove(s);
    }
    
    public static void removeHeader(final HttpMessage httpMessage, final CharSequence charSequence) {
        httpMessage.headers().remove(charSequence);
    }
    
    public static void clearHeaders(final HttpMessage httpMessage) {
        httpMessage.headers().clear();
    }
    
    public static int getIntHeader(final HttpMessage httpMessage, final String s) {
        return getIntHeader(httpMessage, (CharSequence)s);
    }
    
    public static int getIntHeader(final HttpMessage httpMessage, final CharSequence charSequence) {
        final String header = getHeader(httpMessage, charSequence);
        if (header == null) {
            throw new NumberFormatException("header not found: " + (Object)charSequence);
        }
        return Integer.parseInt(header);
    }
    
    public static int getIntHeader(final HttpMessage httpMessage, final String s, final int n) {
        return getIntHeader(httpMessage, (CharSequence)s, n);
    }
    
    public static int getIntHeader(final HttpMessage httpMessage, final CharSequence charSequence, final int n) {
        final String header = getHeader(httpMessage, charSequence);
        if (header == null) {
            return n;
        }
        return Integer.parseInt(header);
    }
    
    public static void setIntHeader(final HttpMessage httpMessage, final String s, final int n) {
        httpMessage.headers().set(s, n);
    }
    
    public static void setIntHeader(final HttpMessage httpMessage, final CharSequence charSequence, final int n) {
        httpMessage.headers().set(charSequence, n);
    }
    
    public static void setIntHeader(final HttpMessage httpMessage, final String s, final Iterable iterable) {
        httpMessage.headers().set(s, iterable);
    }
    
    public static void setIntHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Iterable iterable) {
        httpMessage.headers().set(charSequence, iterable);
    }
    
    public static void addIntHeader(final HttpMessage httpMessage, final String s, final int n) {
        httpMessage.headers().add(s, n);
    }
    
    public static void addIntHeader(final HttpMessage httpMessage, final CharSequence charSequence, final int n) {
        httpMessage.headers().add(charSequence, n);
    }
    
    public static Date getDateHeader(final HttpMessage httpMessage, final String s) throws ParseException {
        return getDateHeader(httpMessage, (CharSequence)s);
    }
    
    public static Date getDateHeader(final HttpMessage httpMessage, final CharSequence charSequence) throws ParseException {
        final String header = getHeader(httpMessage, charSequence);
        if (header == null) {
            throw new ParseException("header not found: " + (Object)charSequence, 0);
        }
        return HttpHeaderDateFormat.get().parse(header);
    }
    
    public static Date getDateHeader(final HttpMessage httpMessage, final String s, final Date date) {
        return getDateHeader(httpMessage, (CharSequence)s, date);
    }
    
    public static Date getDateHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Date date) {
        final String header = getHeader(httpMessage, charSequence);
        if (header == null) {
            return date;
        }
        return HttpHeaderDateFormat.get().parse(header);
    }
    
    public static void setDateHeader(final HttpMessage httpMessage, final String s, final Date date) {
        setDateHeader(httpMessage, (CharSequence)s, date);
    }
    
    public static void setDateHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Date date) {
        if (date != null) {
            httpMessage.headers().set(charSequence, HttpHeaderDateFormat.get().format(date));
        }
        else {
            httpMessage.headers().set(charSequence, null);
        }
    }
    
    public static void setDateHeader(final HttpMessage httpMessage, final String s, final Iterable iterable) {
        httpMessage.headers().set(s, iterable);
    }
    
    public static void setDateHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Iterable iterable) {
        httpMessage.headers().set(charSequence, iterable);
    }
    
    public static void addDateHeader(final HttpMessage httpMessage, final String s, final Date date) {
        httpMessage.headers().add(s, date);
    }
    
    public static void addDateHeader(final HttpMessage httpMessage, final CharSequence charSequence, final Date date) {
        httpMessage.headers().add(charSequence, date);
    }
    
    public static long getContentLength(final HttpMessage httpMessage) {
        final String header = getHeader(httpMessage, HttpHeaders.CONTENT_LENGTH_ENTITY);
        if (header != null) {
            return Long.parseLong(header);
        }
        final long n = getWebSocketContentLength(httpMessage);
        if (n >= 0L) {
            return n;
        }
        throw new NumberFormatException("header not found: Content-Length");
    }
    
    public static long getContentLength(final HttpMessage httpMessage, final long n) {
        final String value = httpMessage.headers().get(HttpHeaders.CONTENT_LENGTH_ENTITY);
        if (value != null) {
            return Long.parseLong(value);
        }
        final long n2 = getWebSocketContentLength(httpMessage);
        if (n2 >= 0L) {
            return n2;
        }
        return n;
    }
    
    private static int getWebSocketContentLength(final HttpMessage httpMessage) {
        final HttpHeaders headers = httpMessage.headers();
        if (httpMessage instanceof HttpRequest) {
            if (HttpMethod.GET.equals(((HttpRequest)httpMessage).getMethod()) && headers.contains(HttpHeaders.SEC_WEBSOCKET_KEY1_ENTITY) && headers.contains(HttpHeaders.SEC_WEBSOCKET_KEY2_ENTITY)) {
                return 8;
            }
        }
        else if (httpMessage instanceof HttpResponse && ((HttpResponse)httpMessage).getStatus().code() == 101 && headers.contains(HttpHeaders.SEC_WEBSOCKET_ORIGIN_ENTITY) && headers.contains(HttpHeaders.SEC_WEBSOCKET_LOCATION_ENTITY)) {
            return 16;
        }
        return -1;
    }
    
    public static void setContentLength(final HttpMessage httpMessage, final long n) {
        httpMessage.headers().set(HttpHeaders.CONTENT_LENGTH_ENTITY, n);
    }
    
    public static String getHost(final HttpMessage httpMessage) {
        return httpMessage.headers().get(HttpHeaders.HOST_ENTITY);
    }
    
    public static String getHost(final HttpMessage httpMessage, final String s) {
        return getHeader(httpMessage, HttpHeaders.HOST_ENTITY, s);
    }
    
    public static void setHost(final HttpMessage httpMessage, final String s) {
        httpMessage.headers().set(HttpHeaders.HOST_ENTITY, s);
    }
    
    public static void setHost(final HttpMessage httpMessage, final CharSequence charSequence) {
        httpMessage.headers().set(HttpHeaders.HOST_ENTITY, charSequence);
    }
    
    public static Date getDate(final HttpMessage httpMessage) throws ParseException {
        return getDateHeader(httpMessage, HttpHeaders.DATE_ENTITY);
    }
    
    public static Date getDate(final HttpMessage httpMessage, final Date date) {
        return getDateHeader(httpMessage, HttpHeaders.DATE_ENTITY, date);
    }
    
    public static void setDate(final HttpMessage httpMessage, final Date date) {
        if (date != null) {
            httpMessage.headers().set(HttpHeaders.DATE_ENTITY, HttpHeaderDateFormat.get().format(date));
        }
        else {
            httpMessage.headers().set(HttpHeaders.DATE_ENTITY, null);
        }
    }
    
    public static boolean is100ContinueExpected(final HttpMessage httpMessage) {
        if (!(httpMessage instanceof HttpRequest)) {
            return false;
        }
        if (httpMessage.getProtocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
            return false;
        }
        final String value = httpMessage.headers().get(HttpHeaders.EXPECT_ENTITY);
        return value != null && (equalsIgnoreCase(HttpHeaders.CONTINUE_ENTITY, value) || httpMessage.headers().contains(HttpHeaders.EXPECT_ENTITY, HttpHeaders.CONTINUE_ENTITY, true));
    }
    
    public static void set100ContinueExpected(final HttpMessage httpMessage) {
        set100ContinueExpected(httpMessage, true);
    }
    
    public static void set100ContinueExpected(final HttpMessage httpMessage, final boolean b) {
        if (b) {
            httpMessage.headers().set(HttpHeaders.EXPECT_ENTITY, HttpHeaders.CONTINUE_ENTITY);
        }
        else {
            httpMessage.headers().remove(HttpHeaders.EXPECT_ENTITY);
        }
    }
    
    static void validateHeaderName(final CharSequence charSequence) {
        if (charSequence == null) {
            throw new NullPointerException("Header names cannot be null");
        }
        while (0 < charSequence.length()) {
            final char char1 = charSequence.charAt(0);
            if (char1 > '\u007f') {
                throw new IllegalArgumentException("Header name cannot contain non-ASCII characters: " + (Object)charSequence);
            }
            switch (char1) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 32:
                case 44:
                case 58:
                case 59:
                case 61: {
                    throw new IllegalArgumentException("Header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + (Object)charSequence);
                }
                default: {
                    int n = 0;
                    ++n;
                    continue;
                }
            }
        }
    }
    
    static void validateHeaderValue(final CharSequence charSequence) {
        if (charSequence == null) {
            throw new NullPointerException("Header values cannot be null");
        }
        while (0 < charSequence.length()) {
            final char char1 = charSequence.charAt(0);
            switch (char1) {
                case 11: {
                    throw new IllegalArgumentException("Header value contains a prohibited character '\\v': " + (Object)charSequence);
                }
                case 12: {
                    throw new IllegalArgumentException("Header value contains a prohibited character '\\f': " + (Object)charSequence);
                }
                default: {
                    Label_0287: {
                        switch (false) {
                            case 0: {
                                switch (char1) {
                                }
                                break;
                            }
                            case 1: {
                                switch (char1) {
                                    case 10: {
                                        break Label_0287;
                                    }
                                    default: {
                                        throw new IllegalArgumentException("Only '\\n' is allowed after '\\r': " + (Object)charSequence);
                                    }
                                }
                                break;
                            }
                            case 2: {
                                switch (char1) {
                                    case 9:
                                    case 32: {
                                        break Label_0287;
                                    }
                                    default: {
                                        throw new IllegalArgumentException("Only ' ' and '\\t' are allowed after '\\n': " + (Object)charSequence);
                                    }
                                }
                                break;
                            }
                        }
                    }
                    int n = 0;
                    ++n;
                    continue;
                }
            }
        }
        if (false) {
            throw new IllegalArgumentException("Header value must not end with '\\r' or '\\n':" + (Object)charSequence);
        }
    }
    
    public static boolean isTransferEncodingChunked(final HttpMessage httpMessage) {
        return httpMessage.headers().contains(HttpHeaders.TRANSFER_ENCODING_ENTITY, HttpHeaders.CHUNKED_ENTITY, true);
    }
    
    public static void removeTransferEncodingChunked(final HttpMessage httpMessage) {
        final List all = httpMessage.headers().getAll(HttpHeaders.TRANSFER_ENCODING_ENTITY);
        if (all.isEmpty()) {
            return;
        }
        final Iterator<String> iterator = all.iterator();
        while (iterator.hasNext()) {
            if (equalsIgnoreCase(iterator.next(), HttpHeaders.CHUNKED_ENTITY)) {
                iterator.remove();
            }
        }
        if (all.isEmpty()) {
            httpMessage.headers().remove(HttpHeaders.TRANSFER_ENCODING_ENTITY);
        }
        else {
            httpMessage.headers().set(HttpHeaders.TRANSFER_ENCODING_ENTITY, all);
        }
    }
    
    public static void setTransferEncodingChunked(final HttpMessage httpMessage) {
        addHeader(httpMessage, HttpHeaders.TRANSFER_ENCODING_ENTITY, HttpHeaders.CHUNKED_ENTITY);
        removeHeader(httpMessage, HttpHeaders.CONTENT_LENGTH_ENTITY);
    }
    
    public static boolean isContentLengthSet(final HttpMessage httpMessage) {
        return httpMessage.headers().contains(HttpHeaders.CONTENT_LENGTH_ENTITY);
    }
    
    public static boolean equalsIgnoreCase(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        final int length = charSequence.length();
        if (length != charSequence2.length()) {
            return false;
        }
        for (int i = length - 1; i >= 0; --i) {
            char char1 = charSequence.charAt(i);
            char char2 = charSequence2.charAt(i);
            if (char1 != char2) {
                if (char1 >= 'A' && char1 <= 'Z') {
                    char1 += ' ';
                }
                if (char2 >= 'A' && char2 <= 'Z') {
                    char2 += ' ';
                }
                if (char1 != char2) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static int hash(final CharSequence charSequence) {
        if (charSequence instanceof HttpHeaderEntity) {
            return ((HttpHeaderEntity)charSequence).hash();
        }
        for (int i = charSequence.length() - 1; i >= 0; --i) {
            char char1 = charSequence.charAt(i);
            if (char1 >= 'A' && char1 <= 'Z') {
                char1 += ' ';
            }
        }
        if (0 > 0) {
            return 0;
        }
        if (0 == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }
    
    static void encode(final HttpHeaders httpHeaders, final ByteBuf byteBuf) {
        if (httpHeaders instanceof DefaultHttpHeaders) {
            ((DefaultHttpHeaders)httpHeaders).encode(byteBuf);
        }
        else {
            for (final Map.Entry<CharSequence, V> entry : httpHeaders) {
                encode(entry.getKey(), (CharSequence)entry.getValue(), byteBuf);
            }
        }
    }
    
    static void encode(final CharSequence charSequence, final CharSequence charSequence2, final ByteBuf byteBuf) {
        if (!encodeAscii(charSequence, byteBuf)) {
            byteBuf.writeBytes(HttpHeaders.HEADER_SEPERATOR);
        }
        if (!encodeAscii(charSequence2, byteBuf)) {
            byteBuf.writeBytes(HttpHeaders.CRLF);
        }
    }
    
    public static boolean encodeAscii(final CharSequence charSequence, final ByteBuf byteBuf) {
        if (charSequence instanceof HttpHeaderEntity) {
            return ((HttpHeaderEntity)charSequence).encode(byteBuf);
        }
        encodeAscii0(charSequence, byteBuf);
        return false;
    }
    
    static void encodeAscii0(final CharSequence charSequence, final ByteBuf byteBuf) {
        while (0 < charSequence.length()) {
            byteBuf.writeByte((byte)charSequence.charAt(0));
            int n = 0;
            ++n;
        }
    }
    
    public static CharSequence newEntity(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        return new HttpHeaderEntity(s);
    }
    
    public static CharSequence newNameEntity(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        return new HttpHeaderEntity(s, HttpHeaders.HEADER_SEPERATOR);
    }
    
    public static CharSequence newValueEntity(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        return new HttpHeaderEntity(s, HttpHeaders.CRLF);
    }
    
    protected HttpHeaders() {
    }
    
    public abstract String get(final String p0);
    
    public String get(final CharSequence charSequence) {
        return this.get(charSequence.toString());
    }
    
    public abstract List getAll(final String p0);
    
    public List getAll(final CharSequence charSequence) {
        return this.getAll(charSequence.toString());
    }
    
    public abstract List entries();
    
    public abstract boolean contains(final String p0);
    
    public boolean contains(final CharSequence charSequence) {
        return this.contains(charSequence.toString());
    }
    
    public abstract boolean isEmpty();
    
    public abstract Set names();
    
    public abstract HttpHeaders add(final String p0, final Object p1);
    
    public HttpHeaders add(final CharSequence charSequence, final Object o) {
        return this.add(charSequence.toString(), o);
    }
    
    public abstract HttpHeaders add(final String p0, final Iterable p1);
    
    public HttpHeaders add(final CharSequence charSequence, final Iterable iterable) {
        return this.add(charSequence.toString(), iterable);
    }
    
    public HttpHeaders add(final HttpHeaders httpHeaders) {
        if (httpHeaders == null) {
            throw new NullPointerException("headers");
        }
        for (final Map.Entry<String, V> entry : httpHeaders) {
            this.add(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    public abstract HttpHeaders set(final String p0, final Object p1);
    
    public HttpHeaders set(final CharSequence charSequence, final Object o) {
        return this.set(charSequence.toString(), o);
    }
    
    public abstract HttpHeaders set(final String p0, final Iterable p1);
    
    public HttpHeaders set(final CharSequence charSequence, final Iterable iterable) {
        return this.set(charSequence.toString(), iterable);
    }
    
    public HttpHeaders set(final HttpHeaders httpHeaders) {
        if (httpHeaders == null) {
            throw new NullPointerException("headers");
        }
        this.clear();
        for (final Map.Entry<String, V> entry : httpHeaders) {
            this.add(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    public abstract HttpHeaders remove(final String p0);
    
    public HttpHeaders remove(final CharSequence charSequence) {
        return this.remove(charSequence.toString());
    }
    
    public abstract HttpHeaders clear();
    
    public boolean contains(final String s, final String s2, final boolean b) {
        final List all = this.getAll(s);
        if (all.isEmpty()) {
            return false;
        }
        for (final String s3 : all) {
            if (b) {
                if (equalsIgnoreCase(s3, s2)) {
                    return true;
                }
                continue;
            }
            else {
                if (s3.equals(s2)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public boolean contains(final CharSequence charSequence, final CharSequence charSequence2, final boolean b) {
        return this.contains(charSequence.toString(), charSequence2.toString(), b);
    }
    
    static {
        HEADER_SEPERATOR = new byte[] { 58, 32 };
        CRLF = new byte[] { 13, 10 };
        CONTENT_LENGTH_ENTITY = newEntity("Content-Length");
        CONNECTION_ENTITY = newEntity("Connection");
        CLOSE_ENTITY = newEntity("close");
        KEEP_ALIVE_ENTITY = newEntity("keep-alive");
        HOST_ENTITY = newEntity("Host");
        DATE_ENTITY = newEntity("Date");
        EXPECT_ENTITY = newEntity("Expect");
        CONTINUE_ENTITY = newEntity("100-continue");
        TRANSFER_ENCODING_ENTITY = newEntity("Transfer-Encoding");
        CHUNKED_ENTITY = newEntity("chunked");
        SEC_WEBSOCKET_KEY1_ENTITY = newEntity("Sec-WebSocket-Key1");
        SEC_WEBSOCKET_KEY2_ENTITY = newEntity("Sec-WebSocket-Key2");
        SEC_WEBSOCKET_ORIGIN_ENTITY = newEntity("Sec-WebSocket-Origin");
        SEC_WEBSOCKET_LOCATION_ENTITY = newEntity("Sec-WebSocket-Location");
        EMPTY_HEADERS = new HttpHeaders() {
            @Override
            public String get(final String s) {
                return null;
            }
            
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
            public HttpHeaders add(final String s, final Object o) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders add(final String s, final Iterable iterable) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders set(final String s, final Object o) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders set(final String s, final Iterable iterable) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders remove(final String s) {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public HttpHeaders clear() {
                throw new UnsupportedOperationException("read only");
            }
            
            @Override
            public Iterator iterator() {
                return this.entries().iterator();
            }
        };
    }
    
    public static final class Values
    {
        public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String BASE64 = "base64";
        public static final String BINARY = "binary";
        public static final String BOUNDARY = "boundary";
        public static final String BYTES = "bytes";
        public static final String CHARSET = "charset";
        public static final String CHUNKED = "chunked";
        public static final String CLOSE = "close";
        public static final String COMPRESS = "compress";
        public static final String CONTINUE = "100-continue";
        public static final String DEFLATE = "deflate";
        public static final String GZIP = "gzip";
        public static final String IDENTITY = "identity";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String MAX_AGE = "max-age";
        public static final String MAX_STALE = "max-stale";
        public static final String MIN_FRESH = "min-fresh";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";
        public static final String MUST_REVALIDATE = "must-revalidate";
        public static final String NO_CACHE = "no-cache";
        public static final String NO_STORE = "no-store";
        public static final String NO_TRANSFORM = "no-transform";
        public static final String NONE = "none";
        public static final String ONLY_IF_CACHED = "only-if-cached";
        public static final String PRIVATE = "private";
        public static final String PROXY_REVALIDATE = "proxy-revalidate";
        public static final String PUBLIC = "public";
        public static final String QUOTED_PRINTABLE = "quoted-printable";
        public static final String S_MAXAGE = "s-maxage";
        public static final String TRAILERS = "trailers";
        public static final String UPGRADE = "Upgrade";
        public static final String WEBSOCKET = "WebSocket";
        
        private Values() {
        }
    }
    
    public static final class Names
    {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String ACCEPT_PATCH = "Accept-Patch";
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        public static final String AGE = "Age";
        public static final String ALLOW = "Allow";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_BASE = "Content-Base";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LANGUAGE = "Content-Language";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_LOCATION = "Content-Location";
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        public static final String CONTENT_MD5 = "Content-MD5";
        public static final String CONTENT_RANGE = "Content-Range";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String EXPECT = "Expect";
        public static final String EXPIRES = "Expires";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String ORIGIN = "Origin";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String RETRY_AFTER = "Retry-After";
        public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
        public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
        public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
        public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        public static final String SERVER = "Server";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String SET_COOKIE2 = "Set-Cookie2";
        public static final String TE = "TE";
        public static final String TRAILER = "Trailer";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VARY = "Vary";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
        public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
        public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
        public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
        
        private Names() {
        }
    }
}
