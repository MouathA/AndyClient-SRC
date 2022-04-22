package io.netty.handler.ssl.util;

import java.util.regex.*;
import io.netty.util.concurrent.*;
import java.security.cert.*;
import io.netty.util.internal.*;
import io.netty.buffer.*;
import java.util.*;
import java.security.*;
import javax.net.ssl.*;

public final class FingerprintTrustManagerFactory extends SimpleTrustManagerFactory
{
    private static final Pattern FINGERPRINT_PATTERN;
    private static final Pattern FINGERPRINT_STRIP_PATTERN;
    private static final int SHA1_BYTE_LEN = 20;
    private static final int SHA1_HEX_LEN = 40;
    private static final FastThreadLocal tlmd;
    private final TrustManager tm;
    private final byte[][] fingerprints;
    
    public FingerprintTrustManagerFactory(final Iterable iterable) {
        this(toFingerprintArray(iterable));
    }
    
    public FingerprintTrustManagerFactory(final String... array) {
        this(toFingerprintArray(Arrays.asList(array)));
    }
    
    public FingerprintTrustManagerFactory(final byte[]... array) {
        this.tm = new X509TrustManager() {
            final FingerprintTrustManagerFactory this$0;
            
            @Override
            public void checkClientTrusted(final X509Certificate[] array, final String s) throws CertificateException {
                this.checkTrusted("client", array);
            }
            
            @Override
            public void checkServerTrusted(final X509Certificate[] array, final String s) throws CertificateException {
                this.checkTrusted("server", array);
            }
            
            private void checkTrusted(final String s, final X509Certificate[] array) throws CertificateException {
                final byte[] fingerprint = this.fingerprint(array[0]);
                final byte[][] access$000 = FingerprintTrustManagerFactory.access$000(this.this$0);
                while (0 < access$000.length && !Arrays.equals(fingerprint, access$000[0])) {
                    int n = 0;
                    ++n;
                }
            }
            
            private byte[] fingerprint(final X509Certificate x509Certificate) throws CertificateEncodingException {
                final MessageDigest messageDigest = (MessageDigest)FingerprintTrustManagerFactory.access$100().get();
                messageDigest.reset();
                return messageDigest.digest(x509Certificate.getEncoded());
            }
            
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return EmptyArrays.EMPTY_X509_CERTIFICATES;
            }
        };
        if (array == null) {
            throw new NullPointerException("fingerprints");
        }
        final ArrayList<byte[]> list = new ArrayList<byte[]>();
        while (0 < array.length) {
            final byte[] array2 = array[0];
            if (array2 == null) {
                break;
            }
            if (array2.length != 20) {
                throw new IllegalArgumentException("malformed fingerprint: " + ByteBufUtil.hexDump(Unpooled.wrappedBuffer(array2)) + " (expected: SHA1)");
            }
            list.add(array2.clone());
            int n = 0;
            ++n;
        }
        this.fingerprints = list.toArray(new byte[list.size()][]);
    }
    
    private static byte[][] toFingerprintArray(final Iterable iterable) {
        if (iterable == null) {
            throw new NullPointerException("fingerprints");
        }
        final ArrayList list = new ArrayList();
        for (final String s : iterable) {
            if (s == null) {
                break;
            }
            if (!FingerprintTrustManagerFactory.FINGERPRINT_PATTERN.matcher(s).matches()) {
                throw new IllegalArgumentException("malformed fingerprint: " + s);
            }
            final String replaceAll = FingerprintTrustManagerFactory.FINGERPRINT_STRIP_PATTERN.matcher(s).replaceAll("");
            if (replaceAll.length() != 40) {
                throw new IllegalArgumentException("malformed fingerprint: " + replaceAll + " (expected: SHA1)");
            }
            final byte[] array = new byte[20];
            while (0 < array.length) {
                array[0] = (byte)Integer.parseInt(replaceAll.substring(0, 2), 16);
                int n = 0;
                ++n;
            }
        }
        return (byte[][])list.toArray(new byte[list.size()][]);
    }
    
    @Override
    protected void engineInit(final KeyStore keyStore) throws Exception {
    }
    
    @Override
    protected void engineInit(final ManagerFactoryParameters managerFactoryParameters) throws Exception {
    }
    
    @Override
    protected TrustManager[] engineGetTrustManagers() {
        return new TrustManager[] { this.tm };
    }
    
    static byte[][] access$000(final FingerprintTrustManagerFactory fingerprintTrustManagerFactory) {
        return fingerprintTrustManagerFactory.fingerprints;
    }
    
    static FastThreadLocal access$100() {
        return FingerprintTrustManagerFactory.tlmd;
    }
    
    static {
        FINGERPRINT_PATTERN = Pattern.compile("^[0-9a-fA-F:]+$");
        FINGERPRINT_STRIP_PATTERN = Pattern.compile(":");
        tlmd = new FastThreadLocal() {
            @Override
            protected MessageDigest initialValue() {
                return MessageDigest.getInstance("SHA1");
            }
            
            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
    }
}
