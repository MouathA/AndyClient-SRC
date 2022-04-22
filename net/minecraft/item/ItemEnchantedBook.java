package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;

public class ItemEnchantedBook extends Item
{
    private static final String __OBFID;
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        return false;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemStack) {
        return (this.func_92110_g(itemStack).tagCount() > 0) ? EnumRarity.UNCOMMON : super.getRarity(itemStack);
    }
    
    public NBTTagList func_92110_g(final ItemStack itemStack) {
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        return (NBTTagList)((tagCompound != null && tagCompound.hasKey("StoredEnchantments", 9)) ? tagCompound.getTag("StoredEnchantments") : new NBTTagList());
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        super.addInformation(itemStack, entityPlayer, list, b);
        final NBTTagList func_92110_g = this.func_92110_g(itemStack);
        if (func_92110_g != null) {
            while (0 < func_92110_g.tagCount()) {
                final short short1 = func_92110_g.getCompoundTagAt(0).getShort("id");
                final short short2 = func_92110_g.getCompoundTagAt(0).getShort("lvl");
                if (Enchantment.func_180306_c(short1) != null) {
                    list.add(Enchantment.func_180306_c(short1).getTranslatedName(short2));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public void addEnchantment(final ItemStack itemStack, final EnchantmentData enchantmentData) {
        final NBTTagList func_92110_g = this.func_92110_g(itemStack);
        while (0 < func_92110_g.tagCount()) {
            final NBTTagCompound compoundTag = func_92110_g.getCompoundTagAt(0);
            if (compoundTag.getShort("id") == enchantmentData.enchantmentobj.effectId) {
                if (compoundTag.getShort("lvl") < enchantmentData.enchantmentLevel) {
                    compoundTag.setShort("lvl", (short)enchantmentData.enchantmentLevel);
                }
                break;
            }
            int n = 0;
            ++n;
        }
        if (false) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setShort("id", (short)enchantmentData.enchantmentobj.effectId);
            nbtTagCompound.setShort("lvl", (short)enchantmentData.enchantmentLevel);
            func_92110_g.appendTag(nbtTagCompound);
        }
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        itemStack.getTagCompound().setTag("StoredEnchantments", func_92110_g);
    }
    
    public ItemStack getEnchantedItemStack(final EnchantmentData enchantmentData) {
        final ItemStack itemStack = new ItemStack(this);
        this.addEnchantment(itemStack, enchantmentData);
        return itemStack;
    }
    
    public void func_92113_a(final Enchantment enchantment, final List list) {
        for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
            list.add(this.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
        }
    }
    
    public WeightedRandomChestContent getRandomEnchantedBook(final Random random) {
        return this.func_92112_a(random, 1, 1, 1);
    }
    
    public WeightedRandomChestContent func_92112_a(final Random random, final int n, final int n2, final int n3) {
        final ItemStack itemStack = new ItemStack(Items.book, 1, 0);
        EnchantmentHelper.addRandomEnchantment(random, itemStack, 30);
        return new WeightedRandomChestContent(itemStack, n, n2, n3);
    }
    
    public void getAll(final Enchantment enchantment, final List list) {
        for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
            list.add(this.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
        }
    }
    
    static {
        __OBFID = "CL_00000025";
    }
}
