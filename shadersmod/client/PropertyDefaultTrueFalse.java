package shadersmod.client;

import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class PropertyDefaultTrueFalse extends Property
{
    public static final String[] PROPERTY_VALUES;
    public static final String[] USER_VALUES;
    private static final String[] lIlllIIIIIIIIIIl;
    private static String[] lIlllIIIIIIIIIll;
    
    static {
        lIIIIlllIlIlIlllI();
        lIIIIlllIlIlIllIl();
        PROPERTY_VALUES = new String[] { PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[0], PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[1], PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[2] };
        USER_VALUES = new String[] { PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[3], PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[4], PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[5] };
    }
    
    public PropertyDefaultTrueFalse(final String s, final String s2, final int n) {
        super(s, PropertyDefaultTrueFalse.PROPERTY_VALUES, s2, PropertyDefaultTrueFalse.USER_VALUES, n);
    }
    
    public boolean isDefault() {
        return this.getValue() == 0;
    }
    
    public boolean isTrue() {
        return this.getValue() == 1;
    }
    
    public boolean isFalse() {
        return this.getValue() == 2;
    }
    
    private static void lIIIIlllIlIlIllIl() {
        (lIlllIIIIIIIIIIl = new String[6])[0] = lIIIIlllIlIlIlIlI(PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[0], PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[1]);
        PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[1] = lIIIIlllIlIlIlIll(PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[2], PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[3]);
        PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[2] = lIIIIlllIlIlIlIll(PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[4], PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[5]);
        PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[3] = lIIIIlllIlIlIllII(PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[6], PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[7]);
        PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[4] = lIIIIlllIlIlIlIlI(PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[8], PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[9]);
        PropertyDefaultTrueFalse.lIlllIIIIIIIIIIl[5] = lIIIIlllIlIlIlIll(PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[10], PropertyDefaultTrueFalse.lIlllIIIIIIIIIll[11]);
        PropertyDefaultTrueFalse.lIlllIIIIIIIIIll = null;
    }
    
    private static void lIIIIlllIlIlIlllI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        PropertyDefaultTrueFalse.lIlllIIIIIIIIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIIIlllIlIlIllII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIIlllIlIlIlIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIIlllIlIlIlIll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
}
