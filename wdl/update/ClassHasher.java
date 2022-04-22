package wdl.update;

import java.security.*;
import java.io.*;

public class ClassHasher
{
    private static final char[] hexArray;
    
    static {
        hexArray = "0123456789ABCDEF".toCharArray();
    }
    
    public static String bytesToHex(final byte[] array) {
        final char[] array2 = new char[array.length * 2];
        while (0 < array.length) {
            final int n = array[0] & 0xFF;
            array2[0] = ClassHasher.hexArray[n >>> 4];
            array2[1] = ClassHasher.hexArray[n & 0xF];
            int n2 = 0;
            ++n2;
        }
        return new String(array2);
    }
    
    public static String hash(final String s, final String s2) throws ClassNotFoundException, FileNotFoundException, Exception {
        final Class<?> forName = Class.forName(s);
        final MessageDigest instance = MessageDigest.getInstance("MD5");
        final InputStream resourceAsStream = forName.getResourceAsStream(s2);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(String.valueOf(s2) + " relative to " + s);
        }
        final DigestInputStream digestInputStream = new DigestInputStream(resourceAsStream, instance);
        while (digestInputStream.read() != -1) {}
        if (digestInputStream != null) {
            digestInputStream.close();
        }
        if (resourceAsStream != null) {
            resourceAsStream.close();
        }
        return bytesToHex(instance.digest());
    }
}
