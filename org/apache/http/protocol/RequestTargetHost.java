package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.net.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class RequestTargetHost implements HttpRequestInterceptor
{
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        final HttpCoreContext adapt = HttpCoreContext.adapt(httpContext);
        final ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") && protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
            return;
        }
        if (!httpRequest.containsHeader("Host")) {
            HttpHost targetHost = adapt.getTargetHost();
            if (targetHost == null) {
                final HttpConnection connection = adapt.getConnection();
                if (connection instanceof HttpInetConnection) {
                    final InetAddress remoteAddress = ((HttpInetConnection)connection).getRemoteAddress();
                    final int remotePort = ((HttpInetConnection)connection).getRemotePort();
                    if (remoteAddress != null) {
                        targetHost = new HttpHost(remoteAddress.getHostName(), remotePort);
                    }
                }
                if (targetHost == null) {
                    if (protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                        return;
                    }
                    throw new ProtocolException("Target host missing");
                }
            }
            httpRequest.addHeader("Host", targetHost.toHostString());
        }
    }
}
