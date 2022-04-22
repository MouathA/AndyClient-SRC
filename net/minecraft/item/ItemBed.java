package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class ItemBed extends Item
{
    private static final String __OBFID;
    
    public ItemBed() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offsetUp, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        if (enumFacing != EnumFacing.UP) {
            return false;
        }
        final Block block = world.getBlockState(offsetUp).getBlock();
        final boolean replaceable = block.isReplaceable(world, offsetUp);
        if (!replaceable) {
            offsetUp = offsetUp.offsetUp();
        }
        final EnumFacing horizontal = EnumFacing.getHorizontal(MathHelper.floor_double(entityPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        final BlockPos offset = offsetUp.offset(horizontal);
        final boolean replaceable2 = block.isReplaceable(world, offset);
        final boolean b = world.isAirBlock(offsetUp) || replaceable;
        final boolean b2 = world.isAirBlock(offset) || replaceable2;
        if (!entityPlayer.func_175151_a(offsetUp, enumFacing, itemStack) || !entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        if (b && b2 && World.doesBlockHaveSolidTopSurface(world, offsetUp.offsetDown()) && World.doesBlockHaveSolidTopSurface(world, offset.offsetDown())) {
            horizontal.getHorizontalIndex();
            final IBlockState withProperty = Blocks.bed.getDefaultState().withProperty(BlockBed.OCCUPIED_PROP, false).withProperty(BlockBed.AGE, horizontal).withProperty(BlockBed.PART_PROP, BlockBed.EnumPartType.FOOT);
            if (world.setBlockState(offsetUp, withProperty, 3)) {
                world.setBlockState(offset, withProperty.withProperty(BlockBed.PART_PROP, BlockBed.EnumPartType.HEAD), 3);
            }
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00001771";
    }
}
