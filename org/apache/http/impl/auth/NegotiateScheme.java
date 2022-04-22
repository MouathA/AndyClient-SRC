package org.apache.http.impl.auth;

import org.apache.commons.logging.*;
import org.apache.http.*;
import org.apache.http.protocol.*;
import org.apache.http.auth.*;
import org.ietf.jgss.*;
import org.apache.http.util.*;

@Deprecated
public class NegotiateScheme extends GGSSchemeBase
{
    private final Log log;
    private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
    private final SpnegoTokenGenerator spengoGenerator;
    
    public NegotiateScheme(final SpnegoTokenGenerator spengoGenerator, final boolean b) {
        super(b);
        this.log = LogFactory.getLog(this.getClass());
        this.spengoGenerator = spengoGenerator;
    }
    
    public NegotiateScheme(final SpnegoTokenGenerator spnegoTokenGenerator) {
        this(spnegoTokenGenerator, false);
    }
    
    public NegotiateScheme() {
        this(null, false);
    }
    
    public String getSchemeName() {
        return "Negotiate";
    }
    
    @Override
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, null);
    }
    
    @Override
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        return super.authenticate(credentials, httpRequest, httpContext);
    }
    
    @Override
    protected byte[] generateToken(final byte[] array, final String s) throws GSSException {
        byte[] array2 = this.generateGSSToken(array, new Oid("1.3.6.1.5.5.2"), s);
        if (true) {
            this.log.debug("Using Kerberos MECH 1.2.840.113554.1.2.2");
            array2 = this.generateGSSToken(array2, new Oid("1.2.840.113554.1.2.2"), s);
            if (array2 != null && this.spengoGenerator != null) {
                array2 = this.spengoGenerator.generateSpnegoDERObject(array2);
            }
        }
        return array2;
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
