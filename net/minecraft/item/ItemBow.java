package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ItemBow extends Item
{
    public static final String[] bowPullIconNameArray;
    private static final String __OBFID;
    private static final String[] lIlllllIlIIlIII;
    private static String[] lIlllllIlIIlIIl;
    
    static {
        lIIIllIIIlIllIII();
        lIIIllIIIlIlIlll();
        __OBFID = ItemBow.lIlllllIlIIlIII[0];
        bowPullIconNameArray = new String[] { ItemBow.lIlllllIlIIlIII[1], ItemBow.lIlllllIlIIlIII[2], ItemBow.lIlllllIlIIlIII[3] };
    }
    
    public ItemBow() {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer, final int n) {
        final boolean b = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;
        if (b || entityPlayer.inventory.hasItem(Items.arrow)) {
            final float n2 = (this.getMaxItemUseDuration(itemStack) - n) / 20.0f;
            float n3 = (n2 * n2 + n2 * 2.0f) / 3.0f;
            if (n3 < 0.1) {
                return;
            }
            if (n3 > 1.0f) {
                n3 = 1.0f;
            }
            final EntityArrow entityArrow = new EntityArrow(world, entityPlayer, n3 * 2.0f);
            if (n3 == 1.0f) {
                entityArrow.setIsCritical(true);
            }
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
            if (enchantmentLevel > 0) {
                entityArrow.setDamage(entityArrow.getDamage() + enchantmentLevel * 0.5 + 0.5);
            }
            final int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
            if (enchantmentLevel2 > 0) {
                entityArrow.setKnockbackStrength(enchantmentLevel2);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0) {
                entityArrow.setFire(100);
            }
            itemStack.damageItem(1, entityPlayer);
            world.playSoundAtEntity(entityPlayer, ItemBow.lIlllllIlIIlIII[4], 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 1.2f) + n3 * 0.5f);
            if (b) {
                entityArrow.canBePickedUp = 2;
            }
            else {
                entityPlayer.inventory.consumeInventoryItem(Items.arrow);
            }
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            if (!world.isRemote) {
                world.spawnEntityInWorld(entityArrow);
            }
        }
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return itemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 72000;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.BOW;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode || entityPlayer.inventory.hasItem(Items.arrow)) {
            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    private static void lIIIllIIIlIlIlll() {
        (lIlllllIlIIlIII = new String[5])[0] = lIIIllIIIlIlIlII(ItemBow.lIlllllIlIIlIIl[0], ItemBow.lIlllllIlIIlIIl[1]);
        ItemBow.lIlllllIlIIlIII[1] = lIIIllIIIlIlIlII(ItemBow.lIlllllIlIIlIIl[2], ItemBow.lIlllllIlIIlIIl[3]);
        ItemBow.lIlllllIlIIlIII[2] = lIIIllIIIlIlIlIl(ItemBow.lIlllllIlIIlIIl[4], ItemBow.lIlllllIlIIlIIl[5]);
        ItemBow.lIlllllIlIIlIII[3] = lIIIllIIIlIlIllI(ItemBow.lIlllllIlIIlIIl[6], ItemBow.lIlllllIlIIlIIl[7]);
        ItemBow.lIlllllIlIIlIII[4] = lIIIllIIIlIlIllI(ItemBow.lIlllllIlIIlIIl[8], ItemBow.lIlllllIlIIlIIl[9]);
        ItemBow.lIlllllIlIIlIIl = null;
    }
    
    private static void lIIIllIIIlIllIII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ItemBow.lIlllllIlIIlIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIIllIIIlIlIllI(final String s, final String s2) {
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
    
    private static String lIIIllIIIlIlIlII(final String s, final String s2) {
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
    
    private static String lIIIllIIIlIlIlIl(final String s, final String s2) {
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
