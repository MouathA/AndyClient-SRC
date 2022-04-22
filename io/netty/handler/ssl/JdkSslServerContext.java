package io.netty.handler.ssl;

import java.security.cert.*;
import java.util.*;
import io.netty.buffer.*;
import javax.net.ssl.*;
import javax.crypto.spec.*;
import java.io.*;
import javax.crypto.*;
import java.security.spec.*;
import java.security.*;

public final class JdkSslServerContext extends JdkSslContext
{
    private final SSLContext ctx;
    private final List nextProtocols;
    
    public JdkSslServerContext(final File file, final File file2) throws SSLException {
        this(file, file2, null);
    }
    
    public JdkSslServerContext(final File file, final File file2, final String s) throws SSLException {
        this(file, file2, s, null, null, 0L, 0L);
    }
    
    public JdkSslServerContext(final File file, final File file2, String s, final Iterable iterable, final Iterable iterable2, final long n, final long n2) throws SSLException {
        super(iterable);
        if (file == null) {
            throw new NullPointerException("certChainFile");
        }
        if (file2 == null) {
            throw new NullPointerException("keyFile");
        }
        if (s == null) {
            s = "";
        }
        if (iterable2 != null && iterable2.iterator().hasNext()) {
            if (!JettyNpnSslEngine.isAvailable()) {
                throw new SSLException("NPN/ALPN unsupported: " + iterable2);
            }
            final ArrayList<String> list = new ArrayList<String>();
            for (final String s2 : iterable2) {
                if (s2 == null) {
                    break;
                }
                list.add(s2);
            }
            this.nextProtocols = Collections.unmodifiableList((List<?>)list);
        }
        else {
            this.nextProtocols = Collections.emptyList();
        }
        String s3 = "\uf5c4\uf5c4\uf5db\uf599\uf5fc\uf5d2\uf5ce\uf5fa\uf5d6\uf5d9\uf5d6\uf5d0\uf5d2\uf5c5\uf5f1\uf5d6\uf5d4\uf5c3\uf5d8\uf5c5\uf5ce\uf599\uf5d6\uf5db\uf5d0\uf5d8\uf5c5\uf5de\uf5c3\uf5df\uf5da";
        if (s3 == null) {
            s3 = "SunX509";
        }
        final KeyStore instance = KeyStore.getInstance("JKS");
        instance.load(null, null);
        final CertificateFactory instance2 = CertificateFactory.getInstance("X.509");
        final KeyFactory instance3 = KeyFactory.getInstance("RSA");
        KeyFactory.getInstance("DSA");
        final ByteBuf privateKey = PemReader.readPrivateKey(file2);
        final byte[] array = new byte[privateKey.readableBytes()];
        privateKey.readBytes(array).release();
        final char[] charArray = s.toCharArray();
        final PrivateKey generatePrivate = instance3.generatePrivate(generateKeySpec(charArray, array));
        final ArrayList<Certificate> list2 = new ArrayList<Certificate>();
        final ByteBuf[] certificates;
        final ByteBuf[] array2 = certificates = PemReader.readCertificates(file);
        int n3 = 0;
        while (0 < certificates.length) {
            list2.add(instance2.generateCertificate(new ByteBufInputStream(certificates[0])));
            ++n3;
        }
        final ByteBuf[] array3 = array2;
        while (0 < array3.length) {
            array3[0].release();
            ++n3;
        }
        instance.setKeyEntry("key", generatePrivate, charArray, list2.toArray(new Certificate[list2.size()]));
        final KeyManagerFactory instance4 = KeyManagerFactory.getInstance(s3);
        instance4.init(instance, charArray);
        (this.ctx = SSLContext.getInstance("TLS")).init(instance4.getKeyManagers(), null, null);
        final SSLSessionContext serverSessionContext = this.ctx.getServerSessionContext();
        if (n > 0L) {
            serverSessionContext.setSessionCacheSize((int)Math.min(n, 2147483647L));
        }
        if (n2 > 0L) {
            serverSessionContext.setSessionTimeout((int)Math.min(n2, 2147483647L));
        }
    }
    
    @Override
    public boolean isClient() {
        return false;
    }
    
    @Override
    public List nextProtocols() {
        return this.nextProtocols;
    }
    
    @Override
    public SSLContext context() {
        return this.ctx;
    }
    
    private static PKCS8EncodedKeySpec generateKeySpec(final char[] array, final byte[] array2) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (array == null || array.length == 0) {
            return new PKCS8EncodedKeySpec(array2);
        }
        final EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(array2);
        final SecretKey generateSecret = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName()).generateSecret(new PBEKeySpec(array));
        final Cipher instance = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
        instance.init(2, generateSecret, encryptedPrivateKeyInfo.getAlgParameters());
        return encryptedPrivateKeyInfo.getKeySpec(instance);
    }
}
