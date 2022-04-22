package org.apache.http.conn.ssl;

import org.apache.http.annotation.*;
import javax.net.ssl.*;
import java.security.*;

@Immutable
public class SSLContexts
{
    public static SSLContext createDefault() throws SSLInitializationException {
        final SSLContext instance = SSLContext.getInstance("TLS");
        instance.init(null, null, null);
        return instance;
    }
    
    public static SSLContext createSystemDefault() throws SSLInitializationException {
        return SSLContext.getInstance("Default");
    }
    
    public static SSLContextBuilder custom() {
        return new SSLContextBuilder();
    }
}
