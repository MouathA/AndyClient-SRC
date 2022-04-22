package io.netty.handler.codec.http;

import java.util.regex.*;
import io.netty.util.*;
import io.netty.buffer.*;

public class HttpVersion implements Comparable
{
    private static final Pattern VERSION_PATTERN;
    private static final String HTTP_1_0_STRING = "HTTP/1.0";
    private static final String HTTP_1_1_STRING = "HTTP/1.1";
    public static final HttpVersion HTTP_1_0;
    public static final HttpVersion HTTP_1_1;
    private final String protocolName;
    private final int majorVersion;
    private final int minorVersion;
    private final String text;
    private final boolean keepAliveDefault;
    private final byte[] bytes;
    
    public static HttpVersion valueOf(String s) {
        if (s == null) {
            throw new NullPointerException("text");
        }
        s = s.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("text is empty");
        }
        HttpVersion httpVersion = version0(s);
        if (httpVersion == null) {
            s = s.toUpperCase();
            httpVersion = version0(s);
            if (httpVersion == null) {
                httpVersion = new HttpVersion(s, true);
            }
        }
        return httpVersion;
    }
    
    private static HttpVersion version0(final String s) {
        if ("HTTP/1.1".equals(s)) {
            return HttpVersion.HTTP_1_1;
        }
        if ("HTTP/1.0".equals(s)) {
            return HttpVersion.HTTP_1_0;
        }
        return null;
    }
    
    public HttpVersion(String upperCase, final boolean keepAliveDefault) {
        if (upperCase == null) {
            throw new NullPointerException("text");
        }
        upperCase = upperCase.trim().toUpperCase();
        if (upperCase.isEmpty()) {
            throw new IllegalArgumentException("empty text");
        }
        final Matcher matcher = HttpVersion.VERSION_PATTERN.matcher(upperCase);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("invalid version format: " + upperCase);
        }
        this.protocolName = matcher.group(1);
        this.majorVersion = Integer.parseInt(matcher.group(2));
        this.minorVersion = Integer.parseInt(matcher.group(3));
        this.text = this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion;
        this.keepAliveDefault = keepAliveDefault;
        this.bytes = null;
    }
    
    public HttpVersion(final String s, final int n, final int n2, final boolean b) {
        this(s, n, n2, b, false);
    }
    
    private HttpVersion(String upperCase, final int majorVersion, final int minorVersion, final boolean keepAliveDefault, final boolean b) {
        if (upperCase == null) {
            throw new NullPointerException("protocolName");
        }
        upperCase = upperCase.trim().toUpperCase();
        if (upperCase.isEmpty()) {
            throw new IllegalArgumentException("empty protocolName");
        }
        while (0 < upperCase.length()) {
            if (Character.isISOControl(upperCase.charAt(0)) || Character.isWhitespace(upperCase.charAt(0))) {
                throw new IllegalArgumentException("invalid character in protocolName");
            }
            int n = 0;
            ++n;
        }
        if (majorVersion < 0) {
            throw new IllegalArgumentException("negative majorVersion");
        }
        if (minorVersion < 0) {
            throw new IllegalArgumentException("negative minorVersion");
        }
        this.protocolName = upperCase;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.text = upperCase + '/' + majorVersion + '.' + minorVersion;
        this.keepAliveDefault = keepAliveDefault;
        if (b) {
            this.bytes = this.text.getBytes(CharsetUtil.US_ASCII);
        }
        else {
            this.bytes = null;
        }
    }
    
    public String protocolName() {
        return this.protocolName;
    }
    
    public int majorVersion() {
        return this.majorVersion;
    }
    
    public int minorVersion() {
        return this.minorVersion;
    }
    
    public String text() {
        return this.text;
    }
    
    public boolean isKeepAliveDefault() {
        return this.keepAliveDefault;
    }
    
    @Override
    public String toString() {
        return this.text();
    }
    
    @Override
    public int hashCode() {
        return (this.protocolName().hashCode() * 31 + this.majorVersion()) * 31 + this.minorVersion();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof HttpVersion)) {
            return false;
        }
        final HttpVersion httpVersion = (HttpVersion)o;
        return this.minorVersion() == httpVersion.minorVersion() && this.majorVersion() == httpVersion.majorVersion() && this.protocolName().equals(httpVersion.protocolName());
    }
    
    public int compareTo(final HttpVersion httpVersion) {
        final int compareTo = this.protocolName().compareTo(httpVersion.protocolName());
        if (compareTo != 0) {
            return compareTo;
        }
        final int n = this.majorVersion() - httpVersion.majorVersion();
        if (n != 0) {
            return n;
        }
        return this.minorVersion() - httpVersion.minorVersion();
    }
    
    void encode(final ByteBuf byteBuf) {
        if (this.bytes == null) {
            HttpHeaders.encodeAscii0(this.text, byteBuf);
        }
        else {
            byteBuf.writeBytes(this.bytes);
        }
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((HttpVersion)o);
    }
    
    static {
        VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
        HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false, true);
        HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true, true);
    }
}
