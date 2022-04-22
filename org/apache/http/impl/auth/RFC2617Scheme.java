package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.auth.*;
import org.apache.http.*;
import java.util.*;

@NotThreadSafe
public abstract class RFC2617Scheme extends AuthSchemeBase
{
    private final Map params;
    private final Charset credentialsCharset;
    
    @Deprecated
    public RFC2617Scheme(final ChallengeState challengeState) {
        super(challengeState);
        this.params = new HashMap();
        this.credentialsCharset = Consts.ASCII;
    }
    
    public RFC2617Scheme(final Charset charset) {
        this.params = new HashMap();
        this.credentialsCharset = ((charset != null) ? charset : Consts.ASCII);
    }
    
    public RFC2617Scheme() {
        this(Consts.ASCII);
    }
    
    public Charset getCredentialsCharset() {
        return this.credentialsCharset;
    }
    
    String getCredentialsCharset(final HttpRequest httpRequest) {
        String name = (String)httpRequest.getParams().getParameter("http.auth.credential-charset");
        if (name == null) {
            name = this.getCredentialsCharset().name();
        }
        return name;
    }
    
    @Override
    protected void parseChallenge(final CharArrayBuffer charArrayBuffer, final int n, final int n2) throws MalformedChallengeException {
        final HeaderElement[] elements = BasicHeaderValueParser.INSTANCE.parseElements(charArrayBuffer, new ParserCursor(n, charArrayBuffer.length()));
        if (elements.length == 0) {
            throw new MalformedChallengeException("Authentication challenge is empty");
        }
        this.params.clear();
        final HeaderElement[] array = elements;
        while (0 < array.length) {
            final HeaderElement headerElement = array[0];
            this.params.put(headerElement.getName(), headerElement.getValue());
            int n3 = 0;
            ++n3;
        }
    }
    
    protected Map getParameters() {
        return this.params;
    }
    
    public String getParameter(final String s) {
        if (s == null) {
            return null;
        }
        return this.params.get(s.toLowerCase(Locale.ENGLISH));
    }
    
    public String getRealm() {
        return this.getParameter("realm");
    }
}
