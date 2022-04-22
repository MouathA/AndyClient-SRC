package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class EnchantmentProtection extends Enchantment
{
    private static final String[] protectionName;
    private static final int[] baseEnchantability;
    private static final int[] levelEnchantability;
    private static final int[] thresholdEnchantability;
    public final int protectionType;
    private static final String __OBFID;
    private static final String[] lllllIlIIIlIlll;
    private static String[] lllllIlIIIllIIl;
    
    static {
        lIlllIIIlIIIIIII();
        lIlllIIIIlllllll();
        __OBFID = EnchantmentProtection.lllllIlIIIlIlll[0];
        protectionName = new String[] { EnchantmentProtection.lllllIlIIIlIlll[1], EnchantmentProtection.lllllIlIIIlIlll[2], EnchantmentProtection.lllllIlIIIlIlll[3], EnchantmentProtection.lllllIlIIIlIlll[4], EnchantmentProtection.lllllIlIIIlIlll[5] };
        baseEnchantability = new int[] { 1, 10, 5, 5, 3 };
        levelEnchantability = new int[] { 11, 8, 6, 8, 6 };
        thresholdEnchantability = new int[] { 20, 12, 10, 12, 15 };
    }
    
    public EnchantmentProtection(final int n, final ResourceLocation resourceLocation, final int n2, final int protectionType) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR);
        this.protectionType = protectionType;
        if (protectionType == 2) {
            this.type = EnumEnchantmentType.ARMOR_FEET;
        }
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return EnchantmentProtection.baseEnchantability[this.protectionType] + (n - 1) * EnchantmentProtection.levelEnchantability[this.protectionType];
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + EnchantmentProtection.thresholdEnchantability[this.protectionType];
    }
    
    @Override
    public int getMaxLevel() {
        return 4;
    }
    
    @Override
    public int calcModifierDamage(final int n, final DamageSource damageSource) {
        if (damageSource.canHarmInCreative()) {
            return 0;
        }
        final float n2 = (6 + n * n) / 3.0f;
        return (this.protectionType == 0) ? MathHelper.floor_float(n2 * 0.75f) : ((this.protectionType == 1 && damageSource.isFireDamage()) ? MathHelper.floor_float(n2 * 1.25f) : ((this.protectionType == 2 && damageSource == DamageSource.fall) ? MathHelper.floor_float(n2 * 2.5f) : ((this.protectionType == 3 && damageSource.isExplosion()) ? MathHelper.floor_float(n2 * 1.5f) : ((this.protectionType == 4 && damageSource.isProjectile()) ? MathHelper.floor_float(n2 * 1.5f) : 0))));
    }
    
    @Override
    public String getName() {
        return EnchantmentProtection.lllllIlIIIlIlll[6] + EnchantmentProtection.protectionName[this.protectionType];
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        if (enchantment instanceof EnchantmentProtection) {
            final EnchantmentProtection enchantmentProtection = (EnchantmentProtection)enchantment;
            return enchantmentProtection.protectionType != this.protectionType && (this.protectionType == 2 || enchantmentProtection.protectionType == 2);
        }
        return super.canApplyTogether(enchantment);
    }
    
    public static int getFireTimeForEntity(final Entity entity, int n) {
        final int maxEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, entity.getInventory());
        if (maxEnchantmentLevel > 0) {
            n -= MathHelper.floor_float(n * (float)maxEnchantmentLevel * 0.15f);
        }
        return n;
    }
    
    public static double func_92092_a(final Entity entity, double n) {
        final int maxEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, entity.getInventory());
        if (maxEnchantmentLevel > 0) {
            n -= MathHelper.floor_double(n * (maxEnchantmentLevel * 0.15f));
        }
        return n;
    }
    
    private static void lIlllIIIIlllllll() {
        (lllllIlIIIlIlll = new String[7])[0] = lIlllIIIIllllIII(EnchantmentProtection.lllllIlIIIllIIl[0], EnchantmentProtection.lllllIlIIIllIIl[1]);
        EnchantmentProtection.lllllIlIIIlIlll[1] = lIlllIIIIllllIlI(EnchantmentProtection.lllllIlIIIllIIl[2], EnchantmentProtection.lllllIlIIIllIIl[3]);
        EnchantmentProtection.lllllIlIIIlIlll[2] = lIlllIIIIllllIlI(EnchantmentProtection.lllllIlIIIllIIl[4], EnchantmentProtection.lllllIlIIIllIIl[5]);
        EnchantmentProtection.lllllIlIIIlIlll[3] = lIlllIIIIlllllIl(EnchantmentProtection.lllllIlIIIllIIl[6], EnchantmentProtection.lllllIlIIIllIIl[7]);
        EnchantmentProtection.lllllIlIIIlIlll[4] = lIlllIIIIllllllI(EnchantmentProtection.lllllIlIIIllIIl[8], EnchantmentProtection.lllllIlIIIllIIl[9]);
        EnchantmentProtection.lllllIlIIIlIlll[5] = lIlllIIIIllllIII(EnchantmentProtection.lllllIlIIIllIIl[10], EnchantmentProtection.lllllIlIIIllIIl[11]);
        EnchantmentProtection.lllllIlIIIlIlll[6] = lIlllIIIIlllllIl(EnchantmentProtection.lllllIlIIIllIIl[12], EnchantmentProtection.lllllIlIIIllIIl[13]);
        EnchantmentProtection.lllllIlIIIllIIl = null;
    }
    
    private static void lIlllIIIlIIIIIII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        EnchantmentProtection.lllllIlIIIllIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIlllIIIIllllIII(final String s, final String s2) {
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
    
    private static String lIlllIIIIlllllIl(String s, final String s2) {
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
    
    private static String lIlllIIIIllllIlI(final String s, final String s2) {
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
    
    private static String lIlllIIIIllllllI(final String s, final String s2) {
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
