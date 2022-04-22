package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class ItemLilyPad extends ItemColored
{
    private static final String __OBFID;
    
    public ItemLilyPad(final Block block) {
        super(block, false);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos func_178782_a = movingObjectPositionFromPlayer.func_178782_a();
            if (!world.isBlockModifiable(entityPlayer, func_178782_a)) {
                return itemStack;
            }
            if (!entityPlayer.func_175151_a(func_178782_a.offset(movingObjectPositionFromPlayer.field_178784_b), movingObjectPositionFromPlayer.field_178784_b, itemStack)) {
                return itemStack;
            }
            final BlockPos offsetUp = func_178782_a.offsetUp();
            final IBlockState blockState = world.getBlockState(func_178782_a);
            if (blockState.getBlock().getMaterial() == Material.water && (int)blockState.getValue(BlockLiquid.LEVEL) == 0 && world.isAirBlock(offsetUp)) {
                world.setBlockState(offsetUp, Blocks.waterlily.getDefaultState());
                if (!entityPlayer.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStack;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(itemStack.getMetadata()));
    }
    
    static {
        __OBFID = "CL_00000074";
    }
}
