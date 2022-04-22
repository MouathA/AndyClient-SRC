package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class ItemSeedFood extends ItemFood
{
    private Block field_150908_b;
    private Block soilId;
    private static final String __OBFID;
    
    public ItemSeedFood(final int n, final float n2, final Block field_150908_b, final Block soilId) {
        super(n, n2, false);
        this.field_150908_b = field_150908_b;
        this.soilId = soilId;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        if (!entityPlayer.func_175151_a(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        if (world.getBlockState(blockPos).getBlock() == this.soilId && world.isAirBlock(blockPos.offsetUp())) {
            world.setBlockState(blockPos.offsetUp(), this.field_150908_b.getDefaultState());
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00000060";
    }
}
