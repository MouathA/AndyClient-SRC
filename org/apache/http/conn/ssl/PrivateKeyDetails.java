package org.apache.http.conn.ssl;

import java.security.cert.*;
import org.apache.http.util.*;
import java.util.*;

public final class PrivateKeyDetails
{
    private final String type;
    private final X509Certificate[] certChain;
    
    public PrivateKeyDetails(final String s, final X509Certificate[] certChain) {
        this.type = (String)Args.notNull(s, "Private key type");
        this.certChain = certChain;
    }
    
    public String getType() {
        return this.type;
    }
    
    public X509Certificate[] getCertChain() {
        return this.certChain;
    }
    
    @Override
    public String toString() {
        return this.type + ':' + Arrays.toString(this.certChain);
    }
}
