package org.apache.http.params;

import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import java.nio.charset.*;

@Deprecated
public final class HttpProtocolParams implements CoreProtocolPNames
{
    private HttpProtocolParams() {
    }
    
    public static String getHttpElementCharset(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        String name = (String)httpParams.getParameter("http.protocol.element-charset");
        if (name == null) {
            name = HTTP.DEF_PROTOCOL_CHARSET.name();
        }
        return name;
    }
    
    public static void setHttpElementCharset(final HttpParams httpParams, final String s) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.element-charset", s);
    }
    
    public static String getContentCharset(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        String name = (String)httpParams.getParameter("http.protocol.content-charset");
        if (name == null) {
            name = HTTP.DEF_CONTENT_CHARSET.name();
        }
        return name;
    }
    
    public static void setContentCharset(final HttpParams httpParams, final String s) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.content-charset", s);
    }
    
    public static ProtocolVersion getVersion(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        final Object parameter = httpParams.getParameter("http.protocol.version");
        if (parameter == null) {
            return HttpVersion.HTTP_1_1;
        }
        return (ProtocolVersion)parameter;
    }
    
    public static void setVersion(final HttpParams httpParams, final ProtocolVersion protocolVersion) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.version", protocolVersion);
    }
    
    public static String getUserAgent(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return (String)httpParams.getParameter("http.useragent");
    }
    
    public static void setUserAgent(final HttpParams httpParams, final String s) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.useragent", s);
    }
    
    public static boolean useExpectContinue(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.protocol.expect-continue", false);
    }
    
    public static void setUseExpectContinue(final HttpParams httpParams, final boolean b) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.protocol.expect-continue", b);
    }
    
    public static CodingErrorAction getMalformedInputAction(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        final Object parameter = httpParams.getParameter("http.malformed.input.action");
        if (parameter == null) {
            return CodingErrorAction.REPORT;
        }
        return (CodingErrorAction)parameter;
    }
    
    public static void setMalformedInputAction(final HttpParams httpParams, final CodingErrorAction codingErrorAction) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.malformed.input.action", codingErrorAction);
    }
    
    public static CodingErrorAction getUnmappableInputAction(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        final Object parameter = httpParams.getParameter("http.unmappable.input.action");
        if (parameter == null) {
            return CodingErrorAction.REPORT;
        }
        return (CodingErrorAction)parameter;
    }
    
    public static void setUnmappableInputAction(final HttpParams httpParams, final CodingErrorAction codingErrorAction) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.unmappable.input.action", codingErrorAction);
    }
}
