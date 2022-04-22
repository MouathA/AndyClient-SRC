package io.netty.handler.ssl.util;

import java.math.*;
import java.util.*;
import sun.security.x509.*;
import java.security.cert.*;
import java.security.*;

final class OpenJdkSelfSignedCertGenerator
{
    static String[] generate(final String s, final KeyPair keyPair, final SecureRandom secureRandom) throws Exception {
        final PrivateKey private1 = keyPair.getPrivate();
        final X509CertInfo x509CertInfo = new X509CertInfo();
        final X500Name x500Name = new X500Name("CN=" + s);
        x509CertInfo.set("version", new CertificateVersion(2));
        x509CertInfo.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, secureRandom)));
        x509CertInfo.set("subject", new CertificateSubjectName(x500Name));
        x509CertInfo.set("issuer", new CertificateIssuerName(x500Name));
        x509CertInfo.set("validity", new CertificateValidity(SelfSignedCertificate.NOT_BEFORE, SelfSignedCertificate.NOT_AFTER));
        x509CertInfo.set("key", new CertificateX509Key(keyPair.getPublic()));
        x509CertInfo.set("algorithmID", new CertificateAlgorithmId(new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid)));
        final X509CertImpl x509CertImpl = new X509CertImpl(x509CertInfo);
        x509CertImpl.sign(private1, "SHA1withRSA");
        x509CertInfo.set("algorithmID.algorithm", x509CertImpl.get("x509.algorithm"));
        final X509CertImpl x509CertImpl2 = new X509CertImpl(x509CertInfo);
        x509CertImpl2.sign(private1, "SHA1withRSA");
        x509CertImpl2.verify(keyPair.getPublic());
        return SelfSignedCertificate.newSelfSignedCertificate(s, private1, x509CertImpl2);
    }
    
    private OpenJdkSelfSignedCertGenerator() {
    }
}
