package org.apache.http.conn.ssl;

import java.security.cert.*;

public interface TrustStrategy
{
    boolean isTrusted(final X509Certificate[] p0, final String p1) throws CertificateException;
}
