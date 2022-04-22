package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import java.util.concurrent.*;
import org.apache.http.auth.*;
import org.apache.http.util.*;
import java.util.*;

@ThreadSafe
public class BasicCredentialsProvider implements CredentialsProvider
{
    private final ConcurrentHashMap credMap;
    
    public BasicCredentialsProvider() {
        this.credMap = new ConcurrentHashMap();
    }
    
    public void setCredentials(final AuthScope authScope, final Credentials credentials) {
        Args.notNull(authScope, "Authentication scope");
        this.credMap.put(authScope, credentials);
    }
    
    private static Credentials matchCredentials(final Map map, final AuthScope authScope) {
        Credentials credentials = map.get(authScope);
        if (credentials == null) {
            Object o = null;
            for (final AuthScope authScope2 : map.keySet()) {
                if (authScope.match(authScope2) > -1) {
                    o = authScope2;
                }
            }
            if (o != null) {
                credentials = map.get(o);
            }
        }
        return credentials;
    }
    
    public Credentials getCredentials(final AuthScope authScope) {
        Args.notNull(authScope, "Authentication scope");
        return matchCredentials(this.credMap, authScope);
    }
    
    public void clear() {
        this.credMap.clear();
    }
    
    @Override
    public String toString() {
        return this.credMap.toString();
    }
}
