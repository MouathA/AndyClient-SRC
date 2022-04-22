package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.commons.codec.binary.*;
import java.nio.charset.*;
import org.apache.http.*;
import org.apache.http.protocol.*;
import org.apache.http.auth.*;
import org.apache.http.util.*;
import org.apache.http.message.*;

@NotThreadSafe
public class BasicScheme extends RFC2617Scheme
{
    private final Base64 base64codec;
    private boolean complete;
    
    public BasicScheme(final Charset charset) {
        super(charset);
        this.base64codec = new Base64(0);
        this.complete = false;
    }
    
    @Deprecated
    public BasicScheme(final ChallengeState challengeState) {
        super(challengeState);
        this.base64codec = new Base64(0);
    }
    
    public BasicScheme() {
        this(Consts.ASCII);
    }
    
    public String getSchemeName() {
        return "basic";
    }
    
    @Override
    public void processChallenge(final Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
    }
    
    public boolean isComplete() {
        return this.complete;
    }
    
    public boolean isConnectionBased() {
        return false;
    }
    
    @Deprecated
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, new BasicHttpContext());
    }
    
    @Override
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        Args.notNull(credentials, "Credentials");
        Args.notNull(httpRequest, "HTTP request");
        final StringBuilder sb = new StringBuilder();
        sb.append(credentials.getUserPrincipal().getName());
        sb.append(":");
        sb.append((credentials.getPassword() == null) ? "null" : credentials.getPassword());
        final byte[] encode = this.base64codec.encode(EncodingUtils.getBytes(sb.toString(), this.getCredentialsCharset(httpRequest)));
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
        if (this.isProxy()) {
            charArrayBuffer.append("Proxy-Authorization");
        }
        else {
            charArrayBuffer.append("Authorization");
        }
        charArrayBuffer.append(": Basic ");
        charArrayBuffer.append(encode, 0, encode.length);
        return new BufferedHeader(charArrayBuffer);
    }
    
    @Deprecated
    public static Header authenticate(final Credentials credentials, final String s, final boolean b) {
        Args.notNull(credentials, "Credentials");
        Args.notNull(s, "charset");
        final StringBuilder sb = new StringBuilder();
        sb.append(credentials.getUserPrincipal().getName());
        sb.append(":");
        sb.append((credentials.getPassword() == null) ? "null" : credentials.getPassword());
        final byte[] encodeBase64 = Base64.encodeBase64(EncodingUtils.getBytes(sb.toString(), s), false);
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
        if (b) {
            charArrayBuffer.append("Proxy-Authorization");
        }
        else {
            charArrayBuffer.append("Authorization");
        }
        charArrayBuffer.append(": Basic ");
        charArrayBuffer.append(encodeBase64, 0, encodeBase64.length);
        return new BufferedHeader(charArrayBuffer);
    }
}
