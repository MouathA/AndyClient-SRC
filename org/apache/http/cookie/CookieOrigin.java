package org.apache.http.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;

@Immutable
public final class CookieOrigin
{
    private final String host;
    private final int port;
    private final String path;
    private final boolean secure;
    
    public CookieOrigin(final String s, final int port, final String path, final boolean secure) {
        Args.notBlank(s, "Host");
        Args.notNegative(port, "Port");
        Args.notNull(path, "Path");
        this.host = s.toLowerCase(Locale.ENGLISH);
        this.port = port;
        if (path.trim().length() != 0) {
            this.path = path;
        }
        else {
            this.path = "/";
        }
        this.secure = secure;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public boolean isSecure() {
        return this.secure;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (this.secure) {
            sb.append("(secure)");
        }
        sb.append(this.host);
        sb.append(':');
        sb.append(Integer.toString(this.port));
        sb.append(this.path);
        sb.append(']');
        return sb.toString();
    }
}
