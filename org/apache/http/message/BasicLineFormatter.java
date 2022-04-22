package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@Immutable
public class BasicLineFormatter implements LineFormatter
{
    @Deprecated
    public static final BasicLineFormatter DEFAULT;
    public static final BasicLineFormatter INSTANCE;
    
    protected CharArrayBuffer initBuffer(final CharArrayBuffer charArrayBuffer) {
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 != null) {
            charArrayBuffer2.clear();
        }
        else {
            charArrayBuffer2 = new CharArrayBuffer(64);
        }
        return charArrayBuffer2;
    }
    
    public static String formatProtocolVersion(final ProtocolVersion protocolVersion, final LineFormatter lineFormatter) {
        return ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE).appendProtocolVersion(null, protocolVersion).toString();
    }
    
    public CharArrayBuffer appendProtocolVersion(final CharArrayBuffer charArrayBuffer, final ProtocolVersion protocolVersion) {
        Args.notNull(protocolVersion, "Protocol version");
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        final int estimateProtocolVersionLen = this.estimateProtocolVersionLen(protocolVersion);
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(estimateProtocolVersionLen);
        }
        else {
            charArrayBuffer2.ensureCapacity(estimateProtocolVersionLen);
        }
        charArrayBuffer2.append(protocolVersion.getProtocol());
        charArrayBuffer2.append('/');
        charArrayBuffer2.append(Integer.toString(protocolVersion.getMajor()));
        charArrayBuffer2.append('.');
        charArrayBuffer2.append(Integer.toString(protocolVersion.getMinor()));
        return charArrayBuffer2;
    }
    
    protected int estimateProtocolVersionLen(final ProtocolVersion protocolVersion) {
        return protocolVersion.getProtocol().length() + 4;
    }
    
    public static String formatRequestLine(final RequestLine requestLine, final LineFormatter lineFormatter) {
        return ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE).formatRequestLine(null, requestLine).toString();
    }
    
    public CharArrayBuffer formatRequestLine(final CharArrayBuffer charArrayBuffer, final RequestLine requestLine) {
        Args.notNull(requestLine, "Request line");
        final CharArrayBuffer initBuffer = this.initBuffer(charArrayBuffer);
        this.doFormatRequestLine(initBuffer, requestLine);
        return initBuffer;
    }
    
    protected void doFormatRequestLine(final CharArrayBuffer charArrayBuffer, final RequestLine requestLine) {
        final String method = requestLine.getMethod();
        final String uri = requestLine.getUri();
        charArrayBuffer.ensureCapacity(method.length() + 1 + uri.length() + 1 + this.estimateProtocolVersionLen(requestLine.getProtocolVersion()));
        charArrayBuffer.append(method);
        charArrayBuffer.append(' ');
        charArrayBuffer.append(uri);
        charArrayBuffer.append(' ');
        this.appendProtocolVersion(charArrayBuffer, requestLine.getProtocolVersion());
    }
    
    public static String formatStatusLine(final StatusLine statusLine, final LineFormatter lineFormatter) {
        return ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE).formatStatusLine(null, statusLine).toString();
    }
    
    public CharArrayBuffer formatStatusLine(final CharArrayBuffer charArrayBuffer, final StatusLine statusLine) {
        Args.notNull(statusLine, "Status line");
        final CharArrayBuffer initBuffer = this.initBuffer(charArrayBuffer);
        this.doFormatStatusLine(initBuffer, statusLine);
        return initBuffer;
    }
    
    protected void doFormatStatusLine(final CharArrayBuffer charArrayBuffer, final StatusLine statusLine) {
        int n = this.estimateProtocolVersionLen(statusLine.getProtocolVersion()) + 1 + 3 + 1;
        final String reasonPhrase = statusLine.getReasonPhrase();
        if (reasonPhrase != null) {
            n += reasonPhrase.length();
        }
        charArrayBuffer.ensureCapacity(n);
        this.appendProtocolVersion(charArrayBuffer, statusLine.getProtocolVersion());
        charArrayBuffer.append(' ');
        charArrayBuffer.append(Integer.toString(statusLine.getStatusCode()));
        charArrayBuffer.append(' ');
        if (reasonPhrase != null) {
            charArrayBuffer.append(reasonPhrase);
        }
    }
    
    public static String formatHeader(final Header header, final LineFormatter lineFormatter) {
        return ((lineFormatter != null) ? lineFormatter : BasicLineFormatter.INSTANCE).formatHeader(null, header).toString();
    }
    
    public CharArrayBuffer formatHeader(final CharArrayBuffer charArrayBuffer, final Header header) {
        Args.notNull(header, "Header");
        CharArrayBuffer charArrayBuffer2;
        if (header instanceof FormattedHeader) {
            charArrayBuffer2 = ((FormattedHeader)header).getBuffer();
        }
        else {
            charArrayBuffer2 = this.initBuffer(charArrayBuffer);
            this.doFormatHeader(charArrayBuffer2, header);
        }
        return charArrayBuffer2;
    }
    
    protected void doFormatHeader(final CharArrayBuffer charArrayBuffer, final Header header) {
        final String name = header.getName();
        final String value = header.getValue();
        int n = name.length() + 2;
        if (value != null) {
            n += value.length();
        }
        charArrayBuffer.ensureCapacity(n);
        charArrayBuffer.append(name);
        charArrayBuffer.append(": ");
        if (value != null) {
            charArrayBuffer.append(value);
        }
    }
    
    static {
        DEFAULT = new BasicLineFormatter();
        INSTANCE = new BasicLineFormatter();
    }
}
