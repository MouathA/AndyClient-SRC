package org.apache.http.impl;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import java.io.*;

@Deprecated
@NotThreadSafe
public class DefaultHttpServerConnection extends SocketHttpServerConnection
{
    public void bind(final Socket socket, final HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        Args.notNull(httpParams, "HTTP parameters");
        this.assertNotOpen();
        socket.setTcpNoDelay(httpParams.getBooleanParameter("http.tcp.nodelay", true));
        socket.setSoTimeout(httpParams.getIntParameter("http.socket.timeout", 0));
        socket.setKeepAlive(httpParams.getBooleanParameter("http.socket.keepalive", false));
        final int intParameter = httpParams.getIntParameter("http.socket.linger", -1);
        if (intParameter >= 0) {
            socket.setSoLinger(intParameter > 0, intParameter);
        }
        if (intParameter >= 0) {
            socket.setSoLinger(intParameter > 0, intParameter);
        }
        super.bind(socket, httpParams);
    }
}
