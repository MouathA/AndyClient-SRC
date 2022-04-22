package shadersmod.client;

import optifine.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class PropertyDefaultFastFancyOff extends Property
{
    public static final String[] PROPERTY_VALUES;
    public static final String[] USER_VALUES;
    private static final String[] lIIIIllIIIllIIII;
    private static String[] lIIIIllIIIllIIIl;
    
    static {
        llIIIllIIlIIIIlI();
        llIIIllIIlIIIIIl();
        PROPERTY_VALUES = new String[] { PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[0], PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[1], PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[2], PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[3] };
        USER_VALUES = new String[] { PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[4], PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[5], PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[6], PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[7] };
    }
    
    public PropertyDefaultFastFancyOff(final String s, final String s2, final int n) {
        super(s, PropertyDefaultFastFancyOff.PROPERTY_VALUES, s2, PropertyDefaultFastFancyOff.USER_VALUES, n);
    }
    
    public boolean isDefault() {
        return this.getValue() == 0;
    }
    
    public boolean isFast() {
        return this.getValue() == 1;
    }
    
    public boolean isFancy() {
        return this.getValue() == 2;
    }
    
    public boolean isOff() {
        return this.getValue() == 3;
    }
    
    @Override
    public boolean setPropertyValue(String propertyValue) {
        if (Config.equals(propertyValue, PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[8])) {
            propertyValue = PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[9];
        }
        return super.setPropertyValue(propertyValue);
    }
    
    private static void llIIIllIIlIIIIIl() {
        (lIIIIllIIIllIIII = new String[10])[0] = llIIIllIIIlllllI(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[0], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[1]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[1] = llIIIllIIIllllll(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[2], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[3]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[2] = llIIIllIIIlllllI(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[4], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[5]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[3] = llIIIllIIIlllllI(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[6], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[7]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[4] = llIIIllIIlIIIIII(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[8], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[9]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[5] = llIIIllIIIllllll(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[10], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[11]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[6] = llIIIllIIlIIIIII(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[12], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[13]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[7] = llIIIllIIIlllllI(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[14], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[15]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[8] = llIIIllIIIllllll(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[16], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[17]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIII[9] = llIIIllIIIlllllI(PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[18], PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl[19]);
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl = null;
    }
    
    private static void llIIIllIIlIIIIlI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        PropertyDefaultFastFancyOff.lIIIIllIIIllIIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llIIIllIIIllllll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llIIIllIIIlllllI(final String s, final String s2) {
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
    
    private static String llIIIllIIlIIIIII(final String s, final String s2) {
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
}
