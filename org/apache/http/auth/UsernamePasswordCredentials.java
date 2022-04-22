package org.apache.http.auth;

import java.io.*;
import org.apache.http.annotation.*;
import java.security.*;
import org.apache.http.util.*;

@Immutable
public class UsernamePasswordCredentials implements Credentials, Serializable
{
    private static final long serialVersionUID = 243343858802739403L;
    private final BasicUserPrincipal principal;
    private final String password;
    
    public UsernamePasswordCredentials(final String s) {
        Args.notNull(s, "Username:password string");
        final int index = s.indexOf(58);
        if (index >= 0) {
            this.principal = new BasicUserPrincipal(s.substring(0, index));
            this.password = s.substring(index + 1);
        }
        else {
            this.principal = new BasicUserPrincipal(s);
            this.password = null;
        }
    }
    
    public UsernamePasswordCredentials(final String s, final String password) {
        Args.notNull(s, "Username");
        this.principal = new BasicUserPrincipal(s);
        this.password = password;
    }
    
    public Principal getUserPrincipal() {
        return this.principal;
    }
    
    public String getUserName() {
        return this.principal.getName();
    }
    
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public int hashCode() {
        return this.principal.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof UsernamePasswordCredentials && LangUtils.equals(this.principal, ((UsernamePasswordCredentials)o).principal));
    }
    
    @Override
    public String toString() {
        return this.principal.toString();
    }
}
