package org.apache.http.params;

import org.apache.http.config.*;
import java.nio.charset.*;

@Deprecated
public final class HttpParamConfig
{
    private HttpParamConfig() {
    }
    
    public static SocketConfig getSocketConfig(final HttpParams httpParams) {
        return SocketConfig.custom().setSoTimeout(httpParams.getIntParameter("http.socket.timeout", 0)).setSoReuseAddress(httpParams.getBooleanParameter("http.socket.reuseaddr", false)).setSoKeepAlive(httpParams.getBooleanParameter("http.socket.keepalive", false)).setSoLinger(httpParams.getIntParameter("http.socket.linger", -1)).setTcpNoDelay(httpParams.getBooleanParameter("http.tcp.nodelay", true)).build();
    }
    
    public static MessageConstraints getMessageConstraints(final HttpParams httpParams) {
        return MessageConstraints.custom().setMaxHeaderCount(httpParams.getIntParameter("http.connection.max-header-count", -1)).setMaxLineLength(httpParams.getIntParameter("http.connection.max-line-length", -1)).build();
    }
    
    public static ConnectionConfig getConnectionConfig(final HttpParams httpParams) {
        final MessageConstraints messageConstraints = getMessageConstraints(httpParams);
        final String s = (String)httpParams.getParameter("http.protocol.element-charset");
        return ConnectionConfig.custom().setCharset((s != null) ? Charset.forName(s) : null).setMalformedInputAction((CodingErrorAction)httpParams.getParameter("http.malformed.input.action")).setMalformedInputAction((CodingErrorAction)httpParams.getParameter("http.unmappable.input.action")).setMessageConstraints(messageConstraints).build();
    }
}
