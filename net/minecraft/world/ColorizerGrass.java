package net.minecraft.world;

import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ColorizerGrass
{
    private static int[] grassBuffer;
    private static final String __OBFID;
    private static final String[] lIIllllllIIIlllI;
    private static String[] lIIllllllIIIllll;
    
    static {
        lllIlIlIlIllIllI();
        lllIlIlIlIllIlIl();
        __OBFID = ColorizerGrass.lIIllllllIIIlllI[0];
        ColorizerGrass.grassBuffer = new int[65536];
    }
    
    public static void setGrassBiomeColorizer(final int[] grassBuffer) {
        ColorizerGrass.grassBuffer = grassBuffer;
    }
    
    public static int getGrassColor(final double n, double n2) {
        n2 *= n;
        final int n3 = (int)((1.0 - n2) * 255.0) << 8 | (int)((1.0 - n) * 255.0);
        return (n3 > ColorizerGrass.grassBuffer.length) ? -65281 : ColorizerGrass.grassBuffer[n3];
    }
    
    private static void lllIlIlIlIllIlIl() {
        (lIIllllllIIIlllI = new String[1])[0] = lllIlIlIlIllIlII(ColorizerGrass.lIIllllllIIIllll[0], ColorizerGrass.lIIllllllIIIllll[1]);
        ColorizerGrass.lIIllllllIIIllll = null;
    }
    
    private static void lllIlIlIlIllIllI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ColorizerGrass.lIIllllllIIIllll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllIlIlIlIllIlII(final String s, final String s2) {
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
}
