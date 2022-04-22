package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.util.*;
import org.apache.http.auth.*;
import java.util.concurrent.*;
import java.util.*;

@ThreadSafe
public class SystemDefaultCredentialsProvider implements CredentialsProvider
{
    private static final Map SCHEME_MAP;
    private final BasicCredentialsProvider internal;
    
    private static String translateScheme(final String s) {
        if (s == null) {
            return null;
        }
        final String s2 = SystemDefaultCredentialsProvider.SCHEME_MAP.get(s);
        return (s2 != null) ? s2 : s;
    }
    
    public SystemDefaultCredentialsProvider() {
        this.internal = new BasicCredentialsProvider();
    }
    
    public void setCredentials(final AuthScope authScope, final Credentials credentials) {
        this.internal.setCredentials(authScope, credentials);
    }
    
    private static PasswordAuthentication getSystemCreds(final AuthScope authScope, final Authenticator.RequestorType requestorType) {
        final String host = authScope.getHost();
        final int port = authScope.getPort();
        return Authenticator.requestPasswordAuthentication(host, null, port, (port == 443) ? "https" : "http", null, translateScheme(authScope.getScheme()), null, requestorType);
    }
    
    public Credentials getCredentials(final AuthScope authScope) {
        Args.notNull(authScope, "Auth scope");
        final Credentials credentials = this.internal.getCredentials(authScope);
        if (credentials != null) {
            return credentials;
        }
        if (authScope.getHost() != null) {
            PasswordAuthentication passwordAuthentication = getSystemCreds(authScope, Authenticator.RequestorType.SERVER);
            if (passwordAuthentication == null) {
                passwordAuthentication = getSystemCreds(authScope, Authenticator.RequestorType.PROXY);
            }
            if (passwordAuthentication != null) {
                final String property = System.getProperty("http.auth.ntlm.domain");
                if (property != null) {
                    return new NTCredentials(passwordAuthentication.getUserName(), new String(passwordAuthentication.getPassword()), null, property);
                }
                if ("NTLM".equalsIgnoreCase(authScope.getScheme())) {
                    return new NTCredentials(passwordAuthentication.getUserName(), new String(passwordAuthentication.getPassword()), null, null);
                }
                return new UsernamePasswordCredentials(passwordAuthentication.getUserName(), new String(passwordAuthentication.getPassword()));
            }
        }
        return null;
    }
    
    public void clear() {
        this.internal.clear();
    }
    
    static {
        (SCHEME_MAP = new ConcurrentHashMap()).put("Basic".toUpperCase(Locale.ENGLISH), "Basic");
        SystemDefaultCredentialsProvider.SCHEME_MAP.put("Digest".toUpperCase(Locale.ENGLISH), "Digest");
        SystemDefaultCredentialsProvider.SCHEME_MAP.put("NTLM".toUpperCase(Locale.ENGLISH), "NTLM");
        SystemDefaultCredentialsProvider.SCHEME_MAP.put("negotiate".toUpperCase(Locale.ENGLISH), "SPNEGO");
        SystemDefaultCredentialsProvider.SCHEME_MAP.put("Kerberos".toUpperCase(Locale.ENGLISH), "Kerberos");
    }
}
