package io.netty.handler.ssl.util;

import org.bouncycastle.asn1.x500.*;
import java.math.*;
import java.util.*;
import org.bouncycastle.cert.jcajce.*;
import org.bouncycastle.operator.jcajce.*;
import java.security.*;
import java.security.cert.*;
import org.bouncycastle.jce.provider.*;
import org.bouncycastle.cert.*;

final class BouncyCastleSelfSignedCertGenerator
{
    private static final Provider PROVIDER;
    
    static String[] generate(final String s, final KeyPair keyPair, final SecureRandom secureRandom) throws Exception {
        final PrivateKey private1 = keyPair.getPrivate();
        final X500Name x500Name = new X500Name("CN=" + s);
        final X509Certificate certificate = new JcaX509CertificateConverter().setProvider(BouncyCastleSelfSignedCertGenerator.PROVIDER).getCertificate(((X509v3CertificateBuilder)new JcaX509v3CertificateBuilder(x500Name, new BigInteger(64, secureRandom), SelfSignedCertificate.NOT_BEFORE, SelfSignedCertificate.NOT_AFTER, x500Name, keyPair.getPublic())).build(new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(private1)));
        certificate.verify(keyPair.getPublic());
        return SelfSignedCertificate.newSelfSignedCertificate(s, private1, certificate);
    }
    
    private BouncyCastleSelfSignedCertGenerator() {
    }
    
    static {
        PROVIDER = (Provider)new BouncyCastleProvider();
    }
}
