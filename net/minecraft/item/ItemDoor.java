package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ItemDoor extends Item
{
    private Block field_179236_a;
    private static final String __OBFID;
    
    public ItemDoor(final Block field_179236_a) {
        this.field_179236_a = field_179236_a;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            offset = offset.offset(enumFacing);
        }
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        if (!this.field_179236_a.canPlaceBlockAt(world, offset)) {
            return false;
        }
        func_179235_a(world, offset, EnumFacing.fromAngle(entityPlayer.rotationYaw), this.field_179236_a);
        --itemStack.stackSize;
        return true;
    }
    
    public static void func_179235_a(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final Block block) {
        final BlockPos offset = blockPos.offset(enumFacing.rotateY());
        final BlockPos offset2 = blockPos.offset(enumFacing.rotateYCCW());
        final int n = (world.getBlockState(offset2).getBlock().isNormalCube() + world.getBlockState(offset2.offsetUp()).getBlock().isNormalCube()) ? 1 : 0;
        final int n2 = (world.getBlockState(offset).getBlock().isNormalCube() + world.getBlockState(offset.offsetUp()).getBlock().isNormalCube()) ? 1 : 0;
        final boolean b = world.getBlockState(offset2).getBlock() == block || world.getBlockState(offset2.offsetUp()).getBlock() == block;
        final boolean b2 = world.getBlockState(offset).getBlock() == block || world.getBlockState(offset.offsetUp()).getBlock() == block;
        if ((b && !b2) || n2 > n) {}
        final BlockPos offsetUp = blockPos.offsetUp();
        final IBlockState withProperty = block.getDefaultState().withProperty(BlockDoor.FACING_PROP, enumFacing).withProperty(BlockDoor.HINGEPOSITION_PROP, true ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);
        world.setBlockState(blockPos, withProperty.withProperty(BlockDoor.HALF_PROP, BlockDoor.EnumDoorHalf.LOWER), 2);
        world.setBlockState(offsetUp, withProperty.withProperty(BlockDoor.HALF_PROP, BlockDoor.EnumDoorHalf.UPPER), 2);
        world.notifyNeighborsOfStateChange(blockPos, block);
        world.notifyNeighborsOfStateChange(offsetUp, block);
    }
    
    static {
        __OBFID = "CL_00000020";
    }
}
