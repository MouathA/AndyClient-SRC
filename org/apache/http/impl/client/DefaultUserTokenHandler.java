package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.client.protocol.*;
import org.apache.http.conn.*;
import java.security.*;
import org.apache.http.*;
import javax.net.ssl.*;
import org.apache.http.auth.*;

@Immutable
public class DefaultUserTokenHandler implements UserTokenHandler
{
    public static final DefaultUserTokenHandler INSTANCE;
    
    public Object getUserToken(final HttpContext httpContext) {
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        Principal principal = null;
        final AuthState targetAuthState = adapt.getTargetAuthState();
        if (targetAuthState != null) {
            principal = getAuthPrincipal(targetAuthState);
            if (principal == null) {
                principal = getAuthPrincipal(adapt.getProxyAuthState());
            }
        }
        if (principal == null) {
            final HttpConnection connection = adapt.getConnection();
            if (connection.isOpen() && connection instanceof ManagedHttpClientConnection) {
                final SSLSession sslSession = ((ManagedHttpClientConnection)connection).getSSLSession();
                if (sslSession != null) {
                    principal = sslSession.getLocalPrincipal();
                }
            }
        }
        return principal;
    }
    
    private static Principal getAuthPrincipal(final AuthState authState) {
        final AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme != null && authScheme.isComplete() && authScheme.isConnectionBased()) {
            final Credentials credentials = authState.getCredentials();
            if (credentials != null) {
                return credentials.getUserPrincipal();
            }
        }
        return null;
    }
    
    static {
        INSTANCE = new DefaultUserTokenHandler();
    }
}
