package net.minecraft.world;

import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ColorizerFoliage
{
    private static int[] foliageBuffer;
    private static final String __OBFID;
    private static final String[] lIIlIllIlIIIlIIl;
    private static String[] lIIlIllIlIIIlIlI;
    
    static {
        llIlllIlllIIlIII();
        llIlllIlllIIIlll();
        __OBFID = ColorizerFoliage.lIIlIllIlIIIlIIl[0];
        ColorizerFoliage.foliageBuffer = new int[65536];
    }
    
    public static void setFoliageBiomeColorizer(final int[] foliageBuffer) {
        ColorizerFoliage.foliageBuffer = foliageBuffer;
    }
    
    public static int getFoliageColor(final double n, double n2) {
        n2 *= n;
        return ColorizerFoliage.foliageBuffer[(int)((1.0 - n2) * 255.0) << 8 | (int)((1.0 - n) * 255.0)];
    }
    
    private static void llIlllIlllIIIlll() {
        (lIIlIllIlIIIlIIl = new String[1])[0] = llIlllIlllIIIllI(ColorizerFoliage.lIIlIllIlIIIlIlI[0], ColorizerFoliage.lIIlIllIlIIIlIlI[1]);
        ColorizerFoliage.lIIlIllIlIIIlIlI = null;
    }
    
    private static void llIlllIlllIIlIII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ColorizerFoliage.lIIlIllIlIIIlIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llIlllIlllIIIllI(final String s, final String s2) {
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
}
