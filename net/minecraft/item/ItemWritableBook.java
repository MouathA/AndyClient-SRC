package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.nbt.*;

public class ItemWritableBook extends Item
{
    private static final String __OBFID;
    
    public ItemWritableBook() {
        this.setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.displayGUIBook(itemStack);
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    public static boolean validBookPageTagContents(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null) {
            return false;
        }
        if (!nbtTagCompound.hasKey("pages", 9)) {
            return false;
        }
        final NBTTagList tagList = nbtTagCompound.getTagList("pages", 8);
        while (0 < tagList.tagCount()) {
            final String stringTag = tagList.getStringTagAt(0);
            if (stringTag == null) {
                return false;
            }
            if (stringTag.length() > 32767) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    static {
        __OBFID = "CL_00000076";
    }
}
