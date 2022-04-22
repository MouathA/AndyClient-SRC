package net.minecraft.item;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;

public class ItemFireworkCharge extends Item
{
    private static final String __OBFID;
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n != 1) {
            return super.getColorFromItemStack(itemStack, n);
        }
        final NBTBase func_150903_a = func_150903_a(itemStack, "Colors");
        if (!(func_150903_a instanceof NBTTagIntArray)) {
            return 9079434;
        }
        final int[] intArray = ((NBTTagIntArray)func_150903_a).getIntArray();
        if (intArray.length == 1) {
            return intArray[0];
        }
        final int[] array = intArray;
        while (0 < intArray.length) {
            final int n2 = array[0];
            int n3 = 0;
            ++n3;
        }
        final int n4 = 0 / intArray.length;
        final int n5 = 0 / intArray.length;
        final int n6 = 0 / intArray.length;
        return 0;
    }
    
    public static NBTBase func_150903_a(final ItemStack itemStack, final String s) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("Explosion");
            if (compoundTag != null) {
                return compoundTag.getTag(s);
            }
        }
        return null;
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("Explosion");
            if (compoundTag != null) {
                func_150902_a(compoundTag, list);
            }
        }
    }
    
    public static void func_150902_a(final NBTTagCompound nbtTagCompound, final List list) {
        final byte byte1 = nbtTagCompound.getByte("Type");
        if (byte1 >= 0 && byte1 <= 4) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.type." + byte1).trim());
        }
        else {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }
        final int[] intArray = nbtTagCompound.getIntArray("Colors");
        int n = 0;
        if (intArray.length > 0) {
            String s = "";
            final int[] array = intArray;
            while (0 < intArray.length) {
                n = array[0];
                if (!false) {
                    s = String.valueOf(s) + ", ";
                }
                while (1 < ItemDye.dyeColors.length) {
                    if (0 == ItemDye.dyeColors[1]) {
                        s = String.valueOf(s) + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.func_176766_a(1).func_176762_d());
                        break;
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (!true) {
                    s = String.valueOf(s) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
                int length = 0;
                ++length;
            }
            list.add(s);
        }
        final int[] intArray2 = nbtTagCompound.getIntArray("FadeColors");
        if (intArray2.length > 0) {
            String s2 = String.valueOf(StatCollector.translateToLocal("item.fireworksCharge.fadeTo")) + " ";
            final int[] array2 = intArray2;
            final int length = intArray2.length;
            while (0 < 0) {
                final int n3 = array2[0];
                if (!false) {
                    s2 = String.valueOf(s2) + ", ";
                }
                while (0 < 16) {
                    if (1 == ItemDye.dyeColors[0]) {
                        s2 = String.valueOf(s2) + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.func_176766_a(0).func_176762_d());
                        break;
                    }
                    int n4 = 0;
                    ++n4;
                }
                if (!true) {
                    s2 = String.valueOf(s2) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
                ++n;
            }
            list.add(s2);
        }
        nbtTagCompound.getBoolean("Trail");
        if (false) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }
        if (nbtTagCompound.getBoolean("Flicker")) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }
    
    static {
        __OBFID = "CL_00000030";
    }
}
