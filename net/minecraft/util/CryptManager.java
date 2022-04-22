package net.minecraft.util;

import org.apache.logging.log4j.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.spec.*;

public class CryptManager
{
    private static final Logger field_180198_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001483";
        field_180198_a = LogManager.getLogger();
    }
    
    public static SecretKey createNewSharedKey() {
        final KeyGenerator instance = KeyGenerator.getInstance("AES");
        instance.init(128);
        return instance.generateKey();
    }
    
    public static KeyPair generateKeyPair() {
        final KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
        instance.initialize(1024);
        return instance.generateKeyPair();
    }
    
    public static byte[] getServerIdHash(final String s, final PublicKey publicKey, final SecretKey secretKey) {
        return digestOperation("SHA-1", new byte[][] { s.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
    }
    
    private static byte[] digestOperation(final String s, final byte[]... array) {
        final MessageDigest instance = MessageDigest.getInstance(s);
        while (0 < array.length) {
            instance.update(array[0]);
            int n = 0;
            ++n;
        }
        return instance.digest();
    }
    
    public static PublicKey decodePublicKey(final byte[] array) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(array));
    }
    
    public static SecretKey decryptSharedKey(final PrivateKey privateKey, final byte[] array) {
        return new SecretKeySpec(decryptData(privateKey, array), "AES");
    }
    
    public static byte[] encryptData(final Key key, final byte[] array) {
        return cipherOperation(1, key, array);
    }
    
    public static byte[] decryptData(final Key key, final byte[] array) {
        return cipherOperation(2, key, array);
    }
    
    private static byte[] cipherOperation(final int n, final Key key, final byte[] array) {
        return createTheCipherInstance(n, key.getAlgorithm(), key).doFinal(array);
    }
    
    private static Cipher createTheCipherInstance(final int n, final String s, final Key key) {
        final Cipher instance = Cipher.getInstance(s);
        instance.init(n, key);
        return instance;
    }
    
    public static Cipher func_151229_a(final int n, final Key key) {
        final Cipher instance = Cipher.getInstance("AES/CFB8/NoPadding");
        instance.init(n, key, new IvParameterSpec(key.getEncoded()));
        return instance;
    }
}
