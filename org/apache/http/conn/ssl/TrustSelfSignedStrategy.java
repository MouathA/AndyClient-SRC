package org.apache.http.conn.ssl;

import java.security.cert.*;

public class TrustSelfSignedStrategy implements TrustStrategy
{
    public boolean isTrusted(final X509Certificate[] array, final String s) throws CertificateException {
        return array.length == 1;
    }
}
