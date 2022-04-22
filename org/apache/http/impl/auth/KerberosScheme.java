package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.ietf.jgss.*;
import org.apache.http.util.*;

@NotThreadSafe
public class KerberosScheme extends GGSSchemeBase
{
    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
    
    public KerberosScheme(final boolean b) {
        super(b);
    }
    
    public KerberosScheme() {
        super(false);
    }
    
    public String getSchemeName() {
        return "Kerberos";
    }
    
    @Override
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        return super.authenticate(credentials, httpRequest, httpContext);
    }
    
    @Override
    protected byte[] generateToken(final byte[] array, final String s) throws GSSException {
        return this.generateGSSToken(array, new Oid("1.2.840.113554.1.2.2"), s);
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
