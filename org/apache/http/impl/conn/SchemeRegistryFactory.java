package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.*;

@Deprecated
@ThreadSafe
public final class SchemeRegistryFactory
{
    public static SchemeRegistry createDefault() {
        final SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        return schemeRegistry;
    }
    
    public static SchemeRegistry createSystemDefault() {
        final SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSystemSocketFactory()));
        return schemeRegistry;
    }
}
