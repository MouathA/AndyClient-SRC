package io.netty.handler.ssl.util;

import java.util.*;
import java.security.*;
import io.netty.buffer.*;
import io.netty.handler.codec.base64.*;
import io.netty.util.*;
import java.io.*;
import java.security.cert.*;
import io.netty.util.internal.logging.*;

public final class SelfSignedCertificate
{
    private static final InternalLogger logger;
    static final Date NOT_BEFORE;
    static final Date NOT_AFTER;
    private final File certificate;
    private final File privateKey;
    
    public SelfSignedCertificate() throws CertificateException {
        this("example.com");
    }
    
    public SelfSignedCertificate(final String s) throws CertificateException {
        this(s, ThreadLocalInsecureRandom.current(), 1024);
    }
    
    public SelfSignedCertificate(final String s, final SecureRandom secureRandom, final int n) throws CertificateException {
        final KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
        instance.initialize(n, secureRandom);
        final String[] generate = OpenJdkSelfSignedCertGenerator.generate(s, instance.generateKeyPair(), secureRandom);
        this.certificate = new File(generate[0]);
        this.privateKey = new File(generate[1]);
    }
    
    public File certificate() {
        return this.certificate;
    }
    
    public File privateKey() {
        return this.privateKey;
    }
    
    public void delete() {
        safeDelete(this.certificate);
        safeDelete(this.privateKey);
    }
    
    static String[] newSelfSignedCertificate(final String s, final PrivateKey privateKey, final X509Certificate x509Certificate) throws IOException, CertificateEncodingException {
        final String string = "-----BEGIN PRIVATE KEY-----\n" + Base64.encode(Unpooled.wrappedBuffer(privateKey.getEncoded()), true).toString(CharsetUtil.US_ASCII) + "\n-----END PRIVATE KEY-----\n";
        final File tempFile = File.createTempFile("keyutil_" + s + '_', ".key");
        tempFile.deleteOnExit();
        final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        fileOutputStream.write(string.getBytes(CharsetUtil.US_ASCII));
        fileOutputStream.close();
        final OutputStream outputStream = null;
        if (outputStream != null) {
            safeClose(tempFile, outputStream);
            safeDelete(tempFile);
        }
        final String string2 = "-----BEGIN CERTIFICATE-----\n" + Base64.encode(Unpooled.wrappedBuffer(x509Certificate.getEncoded()), true).toString(CharsetUtil.US_ASCII) + "\n-----END CERTIFICATE-----\n";
        final File tempFile2 = File.createTempFile("keyutil_" + s + '_', ".crt");
        tempFile2.deleteOnExit();
        final FileOutputStream fileOutputStream2 = new FileOutputStream(tempFile2);
        fileOutputStream2.write(string2.getBytes(CharsetUtil.US_ASCII));
        fileOutputStream2.close();
        final OutputStream outputStream2 = null;
        if (outputStream2 != null) {
            safeClose(tempFile2, outputStream2);
            safeDelete(tempFile2);
            safeDelete(tempFile);
        }
        return new String[] { tempFile2.getPath(), tempFile.getPath() };
    }
    
    private static void safeDelete(final File file) {
        if (!file.delete()) {
            SelfSignedCertificate.logger.warn("Failed to delete a file: " + file);
        }
    }
    
    private static void safeClose(final File file, final OutputStream outputStream) {
        outputStream.close();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
        NOT_BEFORE = new Date(System.currentTimeMillis() - 31536000000L);
        NOT_AFTER = new Date(253402300799000L);
    }
}
