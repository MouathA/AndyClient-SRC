package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.ietf.jgss.*;
import org.apache.http.util.*;

@NotThreadSafe
public class SPNegoScheme extends GGSSchemeBase
{
    private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
    
    public SPNegoScheme(final boolean b) {
        super(b);
    }
    
    public SPNegoScheme() {
        super(false);
    }
    
    public String getSchemeName() {
        return "Negotiate";
    }
    
    @Override
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        return super.authenticate(credentials, httpRequest, httpContext);
    }
    
    @Override
    protected byte[] generateToken(final byte[] array, final String s) throws GSSException {
        return this.generateGSSToken(array, new Oid("1.3.6.1.5.5.2"), s);
    }
    
    public String getParameter(final String s) {
        Args.notNull(s, "Parameter name");
        return null;
    }
    
    public String getRealm() {
        return null;
    }
    
    public boolean isConnectionBased() {
        return true;
    }
}
