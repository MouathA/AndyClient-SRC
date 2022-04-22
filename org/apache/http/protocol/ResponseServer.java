package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class ResponseServer implements HttpResponseInterceptor
{
    private final String originServer;
    
    public ResponseServer(final String originServer) {
        this.originServer = originServer;
    }
    
    public ResponseServer() {
        this(null);
    }
    
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        if (!httpResponse.containsHeader("Server") && this.originServer != null) {
            httpResponse.addHeader("Server", this.originServer);
        }
    }
}
