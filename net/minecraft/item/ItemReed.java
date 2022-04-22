package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class ItemReed extends Item
{
    private Block field_150935_a;
    private static final String __OBFID;
    
    public ItemReed(final Block field_150935_a) {
        this.field_150935_a = field_150935_a;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, EnumFacing up, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(offset);
        final Block block = blockState.getBlock();
        if (block == Blocks.snow_layer && (int)blockState.getValue(BlockSnow.LAYERS_PROP) < 1) {
            up = EnumFacing.UP;
        }
        else if (!block.isReplaceable(world, offset)) {
            offset = offset.offset(up);
        }
        if (!entityPlayer.func_175151_a(offset, up, itemStack)) {
            return false;
        }
        if (itemStack.stackSize == 0) {
            return false;
        }
        if (world.canBlockBePlaced(this.field_150935_a, offset, false, up, null, itemStack) && world.setBlockState(offset, this.field_150935_a.onBlockPlaced(world, offset, up, n, n2, n3, 0, entityPlayer), 3)) {
            final IBlockState blockState2 = world.getBlockState(offset);
            if (blockState2.getBlock() == this.field_150935_a) {
                ItemBlock.setTileEntityNBT(world, offset, itemStack);
                blockState2.getBlock().onBlockPlacedBy(world, offset, blockState2, entityPlayer, itemStack);
            }
            world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, this.field_150935_a.stepSound.getPlaceSound(), (this.field_150935_a.stepSound.getVolume() + 1.0f) / 2.0f, this.field_150935_a.stepSound.getFrequency() * 0.8f);
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00001773";
    }
}
