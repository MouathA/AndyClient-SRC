package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BreakingFour extends BakedQuad
{
    private final TextureAtlasSprite texture;
    private static final String __OBFID;
    
    public BreakingFour(final BakedQuad bakedQuad, final TextureAtlasSprite texture) {
        super(Arrays.copyOf(bakedQuad.func_178209_a(), bakedQuad.func_178209_a().length), bakedQuad.field_178213_b, FaceBakery.func_178410_a(bakedQuad.func_178209_a()));
        this.texture = texture;
        this.func_178217_e();
    }
    
    private void func_178217_e() {
        while (0 < 4) {
            this.func_178216_a(0);
            int n = 0;
            ++n;
        }
    }
    
    private void func_178216_a(final int n) {
        final int n2 = this.field_178215_a.length / 4 * n;
        final float intBitsToFloat = Float.intBitsToFloat(this.field_178215_a[n2]);
        final float intBitsToFloat2 = Float.intBitsToFloat(this.field_178215_a[n2 + 1]);
        final float intBitsToFloat3 = Float.intBitsToFloat(this.field_178215_a[n2 + 2]);
        float n3 = 0.0f;
        float n4 = 0.0f;
        switch (SwitchEnumFacing.field_178419_a[this.face.ordinal()]) {
            case 1: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat3) * 16.0f;
                break;
            }
            case 2: {
                n3 = intBitsToFloat * 16.0f;
                n4 = intBitsToFloat3 * 16.0f;
                break;
            }
            case 3: {
                n3 = (1.0f - intBitsToFloat) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 4: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 5: {
                n3 = intBitsToFloat3 * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 6: {
                n3 = (1.0f - intBitsToFloat3) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
        }
        this.field_178215_a[n2 + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU(n3));
        this.field_178215_a[n2 + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV(n4));
    }
    
    static {
        __OBFID = "CL_00002492";
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178419_a;
        private static final String __OBFID;
        private static final String[] lIIIlllIllllIllI;
        private static String[] lIIIlllIllllIlll;
        
        static {
            llIlIIlIlllIllIl();
            llIlIIlIlllIllII();
            __OBFID = SwitchEnumFacing.lIIIlllIllllIllI[0];
            field_178419_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_178419_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void llIlIIlIlllIllII() {
            (lIIIlllIllllIllI = new String[1])[0] = llIlIIlIlllIlIll(SwitchEnumFacing.lIIIlllIllllIlll[0], SwitchEnumFacing.lIIIlllIllllIlll[1]);
            SwitchEnumFacing.lIIIlllIllllIlll = null;
        }
        
        private static void llIlIIlIlllIllIl() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIIlllIllllIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIlIIlIlllIlIll(final String s, final String s2) {
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
}
