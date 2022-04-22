package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import java.util.*;

public class ItemFirework extends Item
{
    private static final String __OBFID;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityFireworkRocket(world, blockPos.getX() + n, blockPos.getY() + n2, blockPos.getZ() + n3, itemStack));
            if (!entityPlayer.capabilities.isCreativeMode) {
                --itemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("Fireworks");
            if (compoundTag != null) {
                if (compoundTag.hasKey("Flight", 99)) {
                    list.add(String.valueOf(StatCollector.translateToLocal("item.fireworks.flight")) + " " + compoundTag.getByte("Flight"));
                }
                final NBTTagList tagList = compoundTag.getTagList("Explosions", 10);
                if (tagList != null && tagList.tagCount() > 0) {
                    while (0 < tagList.tagCount()) {
                        final NBTTagCompound compoundTag2 = tagList.getCompoundTagAt(0);
                        final ArrayList arrayList = Lists.newArrayList();
                        ItemFireworkCharge.func_150902_a(compoundTag2, arrayList);
                        if (arrayList.size() > 0) {
                            while (1 < arrayList.size()) {
                                arrayList.set(1, "  " + arrayList.get(1));
                                int n = 0;
                                ++n;
                            }
                            list.addAll(arrayList);
                        }
                        int n2 = 0;
                        ++n2;
                    }
                }
            }
        }
    }
    
    static {
        __OBFID = "CL_00000031";
    }
}
