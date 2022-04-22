package org.apache.commons.codec.digest;

import org.apache.commons.codec.*;
import java.util.regex.*;
import java.security.*;

public class Sha2Crypt
{
    private static final int ROUNDS_DEFAULT = 5000;
    private static final int ROUNDS_MAX = 999999999;
    private static final int ROUNDS_MIN = 1000;
    private static final String ROUNDS_PREFIX = "rounds=";
    private static final int SHA256_BLOCKSIZE = 32;
    static final String SHA256_PREFIX = "$5$";
    private static final int SHA512_BLOCKSIZE = 64;
    static final String SHA512_PREFIX = "$6$";
    private static final Pattern SALT_PATTERN;
    
    public static String sha256Crypt(final byte[] array) {
        return sha256Crypt(array, null);
    }
    
    public static String sha256Crypt(final byte[] array, String string) {
        if (string == null) {
            string = "$5$" + B64.getRandomSalt(8);
        }
        return sha2Crypt(array, string, "$5$", 32, "SHA-256");
    }
    
    private static String sha2Crypt(final byte[] array, final String s, final String s2, final int n, final String s3) {
        final int length = array.length;
        if (s == null) {
            throw new IllegalArgumentException("Salt must not be null");
        }
        final Matcher matcher = Sha2Crypt.SALT_PATTERN.matcher(s);
        if (matcher == null || !matcher.find()) {
            throw new IllegalArgumentException("Invalid salt value: " + s);
        }
        if (matcher.group(3) != null) {
            Integer.parseInt(matcher.group(3));
            Math.max(1000, Math.min(999999999, 5000));
        }
        final byte[] bytes = matcher.group(4).getBytes(Charsets.UTF_8);
        final int length2 = bytes.length;
        final MessageDigest digest = DigestUtils.getDigest(s3);
        digest.update(array);
        digest.update(bytes);
        final MessageDigest digest2 = DigestUtils.getDigest(s3);
        digest2.update(array);
        digest2.update(bytes);
        digest2.update(array);
        final byte[] digest3 = digest2.digest();
        int i;
        for (i = array.length; i > n; i -= n) {
            digest.update(digest3, 0, n);
        }
        digest.update(digest3, 0, i);
        for (int j = array.length; j > 0; j >>= 1) {
            if ((j & 0x1) != 0x0) {
                digest.update(digest3, 0, n);
            }
            else {
                digest.update(array);
            }
        }
        byte[] array2 = digest.digest();
        final MessageDigest digest4 = DigestUtils.getDigest(s3);
        while (1 <= length) {
            digest4.update(array);
            int n2 = 0;
            ++n2;
        }
        final byte[] digest5 = digest4.digest();
        final byte[] array3 = new byte[length];
        while (0 < length - n) {
            System.arraycopy(digest5, 0, array3, 0, n);
        }
        System.arraycopy(digest5, 0, array3, 0, length - 0);
        final MessageDigest digest6 = DigestUtils.getDigest(s3);
        while (1 <= 16 + (array2[0] & 0xFF)) {
            digest6.update(bytes);
            int n3 = 0;
            ++n3;
        }
        final byte[] digest7 = digest6.digest();
        final byte[] array4 = new byte[length2];
        while (0 < length2 - n) {
            System.arraycopy(digest7, 0, array4, 0, n);
        }
        System.arraycopy(digest7, 0, array4, 0, length2 - 0);
        while (true) {
            final MessageDigest digest8 = DigestUtils.getDigest(s3);
            digest8.update(array2, 0, n);
            digest8.update(array3, 0, length);
            array2 = digest8.digest();
            int n4 = 0;
            ++n4;
        }
    }
    
    public static String sha512Crypt(final byte[] array) {
        return sha512Crypt(array, null);
    }
    
    public static String sha512Crypt(final byte[] array, String string) {
        if (string == null) {
            string = "$6$" + B64.getRandomSalt(8);
        }
        return sha2Crypt(array, string, "$6$", 64, "SHA-512");
    }
    
    static {
        SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
    }
}
