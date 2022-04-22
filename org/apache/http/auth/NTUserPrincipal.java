package org.apache.http.auth;

import java.security.*;
import java.io.*;
import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.util.*;

@Immutable
public class NTUserPrincipal implements Principal, Serializable
{
    private static final long serialVersionUID = -6870169797924406894L;
    private final String username;
    private final String domain;
    private final String ntname;
    
    public NTUserPrincipal(final String s, final String username) {
        Args.notNull(username, "User name");
        this.username = username;
        if (s != null) {
            this.domain = s.toUpperCase(Locale.ENGLISH);
        }
        else {
            this.domain = null;
        }
        if (this.domain != null && this.domain.length() > 0) {
            final StringBuilder sb = new StringBuilder();
            sb.append(this.domain);
            sb.append('\\');
            sb.append(this.username);
            this.ntname = sb.toString();
        }
        else {
            this.ntname = this.username;
        }
    }
    
    public String getName() {
        return this.ntname;
    }
    
    public String getDomain() {
        return this.domain;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.username);
        LangUtils.hashCode(17, this.domain);
        return 17;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof NTUserPrincipal) {
            final NTUserPrincipal ntUserPrincipal = (NTUserPrincipal)o;
            if (LangUtils.equals(this.username, ntUserPrincipal.username) && LangUtils.equals(this.domain, ntUserPrincipal.domain)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.ntname;
    }
}
