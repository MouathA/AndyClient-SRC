package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.commons.codec.binary.*;
import org.apache.commons.logging.*;
import org.ietf.jgss.*;
import org.apache.http.protocol.*;
import org.apache.http.conn.routing.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.*;
import org.apache.http.auth.*;

@NotThreadSafe
public abstract class GGSSchemeBase extends AuthSchemeBase
{
    private final Log log;
    private final Base64 base64codec;
    private final boolean stripPort;
    private State state;
    private byte[] token;
    
    GGSSchemeBase(final boolean stripPort) {
        this.log = LogFactory.getLog(this.getClass());
        this.base64codec = new Base64(0);
        this.stripPort = stripPort;
        this.state = State.UNINITIATED;
    }
    
    GGSSchemeBase() {
        this(false);
    }
    
    protected GSSManager getManager() {
        return GSSManager.getInstance();
    }
    
    protected byte[] generateGSSToken(final byte[] array, final Oid oid, final String s) throws GSSException {
        byte[] array2 = array;
        if (array2 == null) {
            array2 = new byte[0];
        }
        final GSSManager manager = this.getManager();
        final GSSContext context = manager.createContext(manager.createName("HTTP@" + s, GSSName.NT_HOSTBASED_SERVICE).canonicalize(oid), oid, null, 0);
        context.requestMutualAuth(true);
        context.requestCredDeleg(true);
        return context.initSecContext(array2, 0, array2.length);
    }
    
    protected abstract byte[] generateToken(final byte[] p0, final String p1) throws GSSException;
    
    public boolean isComplete() {
        return this.state == State.TOKEN_GENERATED || this.state == State.FAILED;
    }
    
    @Deprecated
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, null);
    }
    
    @Override
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        Args.notNull(httpRequest, "HTTP request");
        switch (this.state) {
            case UNINITIATED: {
                throw new AuthenticationException(this.getSchemeName() + " authentication has not been initiated");
            }
            case FAILED: {
                throw new AuthenticationException(this.getSchemeName() + " authentication has failed");
            }
            case CHALLENGE_RECEIVED: {
                final HttpRoute httpRoute = (HttpRoute)httpContext.getAttribute("http.route");
                if (httpRoute == null) {
                    throw new AuthenticationException("Connection route is not available");
                }
                HttpHost httpHost;
                if (this.isProxy()) {
                    httpHost = httpRoute.getProxyHost();
                    if (httpHost == null) {
                        httpHost = httpRoute.getTargetHost();
                    }
                }
                else {
                    httpHost = httpRoute.getTargetHost();
                }
                String s;
                if (!this.stripPort && httpHost.getPort() > 0) {
                    s = httpHost.toHostString();
                }
                else {
                    s = httpHost.getHostName();
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("init " + s);
                }
                this.token = this.generateToken(this.token, s);
                this.state = State.TOKEN_GENERATED;
            }
            case TOKEN_GENERATED: {
                final String s2 = new String(this.base64codec.encode(this.token));
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Sending response '" + s2 + "' back to the auth server");
                }
                final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
                if (this.isProxy()) {
                    charArrayBuffer.append("Proxy-Authorization");
                }
                else {
                    charArrayBuffer.append("Authorization");
                }
                charArrayBuffer.append(": Negotiate ");
                charArrayBuffer.append(s2);
                return new BufferedHeader(charArrayBuffer);
            }
            default: {
                throw new IllegalStateException("Illegal state: " + this.state);
            }
        }
    }
    
    @Override
    protected void parseChallenge(final CharArrayBuffer charArrayBuffer, final int n, final int n2) throws MalformedChallengeException {
        final String substringTrimmed = charArrayBuffer.substringTrimmed(n, n2);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Received challenge '" + substringTrimmed + "' from the auth server");
        }
        if (this.state == State.UNINITIATED) {
            this.token = Base64.decodeBase64(substringTrimmed.getBytes());
            this.state = State.CHALLENGE_RECEIVED;
        }
        else {
            this.log.debug("Authentication already attempted");
            this.state = State.FAILED;
        }
    }
    
    enum State
    {
        UNINITIATED("UNINITIATED", 0), 
        CHALLENGE_RECEIVED("CHALLENGE_RECEIVED", 1), 
        TOKEN_GENERATED("TOKEN_GENERATED", 2), 
        FAILED("FAILED", 3);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.UNINITIATED, State.CHALLENGE_RECEIVED, State.TOKEN_GENERATED, State.FAILED };
        }
    }
}
