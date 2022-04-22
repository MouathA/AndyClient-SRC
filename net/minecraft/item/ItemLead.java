package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class ItemLead extends Item
{
    private static final String __OBFID;
    
    public ItemLead() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!(world.getBlockState(blockPos).getBlock() instanceof BlockFence)) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        func_180618_a(entityPlayer, world, blockPos);
        return true;
    }
    
    public static boolean func_180618_a(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos) {
        EntityLeashKnot entityLeashKnot = EntityLeashKnot.func_174863_b(world, blockPos);
        final double n = 7.0;
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        for (final EntityLiving entityLiving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(x - n, y - n, z - n, x + n, y + n, z + n))) {
            if (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() == entityPlayer) {
                if (entityLeashKnot == null) {
                    entityLeashKnot = EntityLeashKnot.func_174862_a(world, blockPos);
                }
                entityLiving.setLeashedToEntity(entityLeashKnot, true);
            }
        }
        return true;
    }
    
    static {
        __OBFID = "CL_00000045";
    }
}
