package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ItemSnow extends ItemBlock
{
    private static final String __OBFID;
    
    public ItemSnow(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (itemStack.stackSize == 0) {
            return false;
        }
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        IBlockState blockState = world.getBlockState(offset);
        Block block = blockState.getBlock();
        if (block != this.block && enumFacing != EnumFacing.UP) {
            offset = offset.offset(enumFacing);
            blockState = world.getBlockState(offset);
            block = blockState.getBlock();
        }
        if (block == this.block) {
            final int intValue = (int)blockState.getValue(BlockSnow.LAYERS_PROP);
            if (intValue <= 7) {
                final IBlockState withProperty = blockState.withProperty(BlockSnow.LAYERS_PROP, intValue + 1);
                if (world.checkNoEntityCollision(this.block.getCollisionBoundingBox(world, offset, withProperty)) && world.setBlockState(offset, withProperty, 2)) {
                    world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0f) / 2.0f, this.block.stepSound.getFrequency() * 0.8f);
                    --itemStack.stackSize;
                    return true;
                }
            }
        }
        return super.onItemUse(itemStack, entityPlayer, world, offset, enumFacing, n, n2, n3);
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    static {
        __OBFID = "CL_00000068";
    }
}
