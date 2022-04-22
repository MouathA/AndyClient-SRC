package org.apache.http.client.params;

import org.apache.http.annotation.*;

@Deprecated
@Immutable
public final class AuthPolicy
{
    public static final String NTLM = "NTLM";
    public static final String DIGEST = "Digest";
    public static final String BASIC = "Basic";
    public static final String SPNEGO = "negotiate";
    public static final String KERBEROS = "Kerberos";
    
    private AuthPolicy() {
    }
}
