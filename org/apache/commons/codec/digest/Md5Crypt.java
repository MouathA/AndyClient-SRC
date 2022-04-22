package org.apache.commons.codec.digest;

import org.apache.commons.codec.*;
import java.util.*;
import java.util.regex.*;
import java.security.*;

public class Md5Crypt
{
    static final String APR1_PREFIX = "$apr1$";
    private static final int BLOCKSIZE = 16;
    static final String MD5_PREFIX = "$1$";
    private static final int ROUNDS = 1000;
    
    public static String apr1Crypt(final byte[] array) {
        return apr1Crypt(array, "$apr1$" + B64.getRandomSalt(8));
    }
    
    public static String apr1Crypt(final byte[] array, String string) {
        if (string != null && !string.startsWith("$apr1$")) {
            string = "$apr1$" + string;
        }
        return md5Crypt(array, string, "$apr1$");
    }
    
    public static String apr1Crypt(final String s) {
        return apr1Crypt(s.getBytes(Charsets.UTF_8));
    }
    
    public static String apr1Crypt(final String s, final String s2) {
        return apr1Crypt(s.getBytes(Charsets.UTF_8), s2);
    }
    
    public static String md5Crypt(final byte[] array) {
        return md5Crypt(array, "$1$" + B64.getRandomSalt(8));
    }
    
    public static String md5Crypt(final byte[] array, final String s) {
        return md5Crypt(array, s, "$1$");
    }
    
    public static String md5Crypt(final byte[] array, final String s, final String s2) {
        final int length = array.length;
        String s3;
        if (s == null) {
            s3 = B64.getRandomSalt(8);
        }
        else {
            final Matcher matcher = Pattern.compile("^" + s2.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*").matcher(s);
            if (matcher == null || !matcher.find()) {
                throw new IllegalArgumentException("Invalid salt value: " + s);
            }
            s3 = matcher.group(1);
        }
        final byte[] bytes = s3.getBytes(Charsets.UTF_8);
        final MessageDigest md5Digest = DigestUtils.getMd5Digest();
        md5Digest.update(array);
        md5Digest.update(s2.getBytes(Charsets.UTF_8));
        md5Digest.update(bytes);
        MessageDigest messageDigest = DigestUtils.getMd5Digest();
        messageDigest.update(array);
        messageDigest.update(bytes);
        messageDigest.update(array);
        final byte[] digest = messageDigest.digest();
        for (int i = length; i > 0; i -= 16) {
            md5Digest.update(digest, 0, (i > 16) ? 16 : i);
        }
        Arrays.fill(digest, (byte)0);
        for (int j = length; j > 0; j >>= 1) {
            if ((j & 0x1) == 0x1) {
                md5Digest.update(digest[0]);
            }
            else {
                md5Digest.update(array[0]);
            }
        }
        final StringBuilder sb = new StringBuilder(s2 + s3 + "$");
        byte[] array2 = md5Digest.digest();
        while (0 < 1000) {
            messageDigest = DigestUtils.getMd5Digest();
            if (false) {
                messageDigest.update(array);
            }
            else {
                messageDigest.update(array2, 0, 16);
            }
            if (false) {
                messageDigest.update(bytes);
            }
            if (false) {
                messageDigest.update(array);
            }
            if (false) {
                messageDigest.update(array2, 0, 16);
            }
            else {
                messageDigest.update(array);
            }
            array2 = messageDigest.digest();
            int n = 0;
            ++n;
        }
        B64.b64from24bit(array2[0], array2[6], array2[12], 4, sb);
        B64.b64from24bit(array2[1], array2[7], array2[13], 4, sb);
        B64.b64from24bit(array2[2], array2[8], array2[14], 4, sb);
        B64.b64from24bit(array2[3], array2[9], array2[15], 4, sb);
        B64.b64from24bit(array2[4], array2[10], array2[5], 4, sb);
        B64.b64from24bit((byte)0, (byte)0, array2[11], 2, sb);
        md5Digest.reset();
        messageDigest.reset();
        Arrays.fill(array, (byte)0);
        Arrays.fill(bytes, (byte)0);
        Arrays.fill(array2, (byte)0);
        return sb.toString();
    }
}
