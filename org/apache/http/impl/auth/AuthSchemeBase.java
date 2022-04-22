package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.protocol.*;
import org.apache.http.auth.*;
import java.util.*;

@NotThreadSafe
public abstract class AuthSchemeBase implements ContextAwareAuthScheme
{
    private ChallengeState challengeState;
    
    @Deprecated
    public AuthSchemeBase(final ChallengeState challengeState) {
        this.challengeState = challengeState;
    }
    
    public AuthSchemeBase() {
    }
    
    public void processChallenge(final Header header) throws MalformedChallengeException {
        Args.notNull(header, "Header");
        final String name = header.getName();
        if (name.equalsIgnoreCase("WWW-Authenticate")) {
            this.challengeState = ChallengeState.TARGET;
        }
        else {
            if (!name.equalsIgnoreCase("Proxy-Authenticate")) {
                throw new MalformedChallengeException("Unexpected header name: " + name);
            }
            this.challengeState = ChallengeState.PROXY;
        }
        CharArrayBuffer buffer;
        int valuePos;
        if (header instanceof FormattedHeader) {
            buffer = ((FormattedHeader)header).getBuffer();
            valuePos = ((FormattedHeader)header).getValuePos();
        }
        else {
            final String value = header.getValue();
            if (value == null) {
                throw new MalformedChallengeException("Header value is null");
            }
            buffer = new CharArrayBuffer(value.length());
            buffer.append(value);
        }
        while (0 < buffer.length() && HTTP.isWhitespace(buffer.charAt(0))) {
            ++valuePos;
        }
        while (0 < buffer.length() && !HTTP.isWhitespace(buffer.charAt(0))) {
            ++valuePos;
        }
        final String substring = buffer.substring(0, 0);
        if (!substring.equalsIgnoreCase(this.getSchemeName())) {
            throw new MalformedChallengeException("Invalid scheme identifier: " + substring);
        }
        this.parseChallenge(buffer, 0, buffer.length());
    }
    
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest);
    }
    
    protected abstract void parseChallenge(final CharArrayBuffer p0, final int p1, final int p2) throws MalformedChallengeException;
    
    public boolean isProxy() {
        return this.challengeState != null && this.challengeState == ChallengeState.PROXY;
    }
    
    public ChallengeState getChallengeState() {
        return this.challengeState;
    }
    
    @Override
    public String toString() {
        final String schemeName = this.getSchemeName();
        if (schemeName != null) {
            return schemeName.toUpperCase(Locale.US);
        }
        return super.toString();
    }
}
