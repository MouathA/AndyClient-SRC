package org.apache.http.auth;

import java.io.*;
import org.apache.http.annotation.*;
import java.util.*;
import java.security.*;
import org.apache.http.util.*;

@Immutable
public class NTCredentials implements Credentials, Serializable
{
    private static final long serialVersionUID = -7385699315228907265L;
    private final NTUserPrincipal principal;
    private final String password;
    private final String workstation;
    
    public NTCredentials(final String s) {
        Args.notNull(s, "Username:password string");
        final int index = s.indexOf(58);
        String substring;
        if (index >= 0) {
            substring = s.substring(0, index);
            this.password = s.substring(index + 1);
        }
        else {
            substring = s;
            this.password = null;
        }
        final int index2 = substring.indexOf(47);
        if (index2 >= 0) {
            this.principal = new NTUserPrincipal(substring.substring(0, index2).toUpperCase(Locale.ENGLISH), substring.substring(index2 + 1));
        }
        else {
            this.principal = new NTUserPrincipal(null, substring.substring(index2 + 1));
        }
        this.workstation = null;
    }
    
    public NTCredentials(final String s, final String password, final String s2, final String s3) {
        Args.notNull(s, "User name");
        this.principal = new NTUserPrincipal(s3, s);
        this.password = password;
        if (s2 != null) {
            this.workstation = s2.toUpperCase(Locale.ENGLISH);
        }
        else {
            this.workstation = null;
        }
    }
    
    public Principal getUserPrincipal() {
        return this.principal;
    }
    
    public String getUserName() {
        return this.principal.getUsername();
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getDomain() {
        return this.principal.getDomain();
    }
    
    public String getWorkstation() {
        return this.workstation;
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.principal);
        LangUtils.hashCode(17, this.workstation);
        return 17;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof NTCredentials) {
            final NTCredentials ntCredentials = (NTCredentials)o;
            if (LangUtils.equals(this.principal, ntCredentials.principal) && LangUtils.equals(this.workstation, ntCredentials.workstation)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[principal: ");
        sb.append(this.principal);
        sb.append("][workstation: ");
        sb.append(this.workstation);
        sb.append("]");
        return sb.toString();
    }
}
