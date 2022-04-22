package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class ItemHangingEntity extends Item
{
    private final Class hangingEntityClass;
    private static final String __OBFID;
    
    public ItemHangingEntity(final Class hangingEntityClass) {
        this.hangingEntityClass = hangingEntityClass;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        if (enumFacing == EnumFacing.UP) {
            return false;
        }
        final BlockPos offset = blockPos.offset(enumFacing);
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        final EntityHanging func_179233_a = this.func_179233_a(world, offset, enumFacing);
        if (func_179233_a != null && func_179233_a.onValidSurface()) {
            if (!world.isRemote) {
                world.spawnEntityInWorld(func_179233_a);
            }
            --itemStack.stackSize;
        }
        return true;
    }
    
    private EntityHanging func_179233_a(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return (this.hangingEntityClass == EntityPainting.class) ? new EntityPainting(world, blockPos, enumFacing) : ((this.hangingEntityClass == EntityItemFrame.class) ? new EntityItemFrame(world, blockPos, enumFacing) : null);
    }
    
    static {
        __OBFID = "CL_00000038";
    }
}
