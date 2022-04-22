package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class EnchantmentDamage extends Enchantment
{
    private static final String[] protectionName;
    private static final int[] baseEnchantability;
    private static final int[] levelEnchantability;
    private static final int[] thresholdEnchantability;
    public final int damageType;
    private static final String __OBFID;
    private static final String[] lIllIIIIIIlllllI;
    private static String[] lIllIIIIIlIIIIIl;
    
    static {
        lIIIIIIllIlIIlIlI();
        lIIIIIIllIlIIlIIl();
        __OBFID = EnchantmentDamage.lIllIIIIIIlllllI[0];
        protectionName = new String[] { EnchantmentDamage.lIllIIIIIIlllllI[1], EnchantmentDamage.lIllIIIIIIlllllI[2], EnchantmentDamage.lIllIIIIIIlllllI[3] };
        baseEnchantability = new int[] { 1, 5, 5 };
        levelEnchantability = new int[] { 11, 8, 8 };
        thresholdEnchantability = new int[] { 20, 20, 20 };
    }
    
    public EnchantmentDamage(final int n, final ResourceLocation resourceLocation, final int n2, final int damageType) {
        super(n, resourceLocation, n2, EnumEnchantmentType.WEAPON);
        this.damageType = damageType;
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return EnchantmentDamage.baseEnchantability[this.damageType] + (n - 1) * EnchantmentDamage.levelEnchantability[this.damageType];
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + EnchantmentDamage.thresholdEnchantability[this.damageType];
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    @Override
    public float func_152376_a(final int n, final EnumCreatureAttribute enumCreatureAttribute) {
        return (this.damageType == 0) ? (n * 1.25f) : ((this.damageType == 1 && enumCreatureAttribute == EnumCreatureAttribute.UNDEAD) ? (n * 2.5f) : ((this.damageType == 2 && enumCreatureAttribute == EnumCreatureAttribute.ARTHROPOD) ? (n * 2.5f) : 0.0f));
    }
    
    @Override
    public String getName() {
        return EnchantmentDamage.lIllIIIIIIlllllI[4] + EnchantmentDamage.protectionName[this.damageType];
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentDamage);
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemAxe || super.canApply(itemStack);
    }
    
    @Override
    public void func_151368_a(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase2 = (EntityLivingBase)entity;
            if (this.damageType == 2 && entityLivingBase2.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                entityLivingBase2.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20 + entityLivingBase.getRNG().nextInt(10 * n), 3));
            }
        }
    }
    
    private static void lIIIIIIllIlIIlIIl() {
        (lIllIIIIIIlllllI = new String[5])[0] = lIIIIIIllIlIIIIll(EnchantmentDamage.lIllIIIIIlIIIIIl[0], EnchantmentDamage.lIllIIIIIlIIIIIl[1]);
        EnchantmentDamage.lIllIIIIIIlllllI[1] = lIIIIIIllIlIIIlII(EnchantmentDamage.lIllIIIIIlIIIIIl[2], EnchantmentDamage.lIllIIIIIlIIIIIl[3]);
        EnchantmentDamage.lIllIIIIIIlllllI[2] = lIIIIIIllIlIIIlIl(EnchantmentDamage.lIllIIIIIlIIIIIl[4], EnchantmentDamage.lIllIIIIIlIIIIIl[5]);
        EnchantmentDamage.lIllIIIIIIlllllI[3] = lIIIIIIllIlIIIIll(EnchantmentDamage.lIllIIIIIlIIIIIl[6], EnchantmentDamage.lIllIIIIIlIIIIIl[7]);
        EnchantmentDamage.lIllIIIIIIlllllI[4] = lIIIIIIllIlIIIIll(EnchantmentDamage.lIllIIIIIlIIIIIl[8], EnchantmentDamage.lIllIIIIIlIIIIIl[9]);
        EnchantmentDamage.lIllIIIIIlIIIIIl = null;
    }
    
    private static void lIIIIIIllIlIIlIlI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        EnchantmentDamage.lIllIIIIIlIIIIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIIIIIllIlIIIIll(final String s, final String s2) {
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
    
    private static String lIIIIIIllIlIIIlIl(final String s, final String s2) {
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
    
    private static String lIIIIIIllIlIIIlII(final String s, final String s2) {
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
