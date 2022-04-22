package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class ItemSeeds extends Item
{
    private Block field_150925_a;
    private Block soilBlockID;
    private static final String __OBFID;
    
    public ItemSeeds(final Block field_150925_a, final Block soilBlockID) {
        this.field_150925_a = field_150925_a;
        this.soilBlockID = soilBlockID;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        if (!entityPlayer.func_175151_a(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        if (world.getBlockState(blockPos).getBlock() == this.soilBlockID && world.isAirBlock(blockPos.offsetUp())) {
            world.setBlockState(blockPos.offsetUp(), this.field_150925_a.getDefaultState());
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00000061";
    }
}
