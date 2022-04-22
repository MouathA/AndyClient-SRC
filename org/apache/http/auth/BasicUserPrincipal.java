package org.apache.http.auth;

import java.security.*;
import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;

@Immutable
public final class BasicUserPrincipal implements Principal, Serializable
{
    private static final long serialVersionUID = -2266305184969850467L;
    private final String username;
    
    public BasicUserPrincipal(final String username) {
        Args.notNull(username, "User name");
        this.username = username;
    }
    
    public String getName() {
        return this.username;
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.username);
        return 17;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof BasicUserPrincipal && LangUtils.equals(this.username, ((BasicUserPrincipal)o).username));
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[principal: ");
        sb.append(this.username);
        sb.append("]");
        return sb.toString();
    }
}
