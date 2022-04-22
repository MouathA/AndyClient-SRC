package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import java.io.*;

@Deprecated
@NotThreadSafe
public class SocketOutputBuffer extends AbstractSessionOutputBuffer
{
    public SocketOutputBuffer(final Socket socket, final int n, final HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        if (1024 < 0) {
            socket.getSendBufferSize();
        }
        if (1024 < 1024) {}
        this.init(socket.getOutputStream(), 1024, httpParams);
    }
}
