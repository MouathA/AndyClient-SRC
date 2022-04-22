package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class ItemBucket extends Item
{
    private Block isFull;
    private static final String __OBFID;
    
    public ItemBucket(final Block isFull) {
        this.maxStackSize = 1;
        this.isFull = isFull;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final boolean b = this.isFull == Blocks.air;
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, b);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos func_178782_a = movingObjectPositionFromPlayer.func_178782_a();
            if (!world.isBlockModifiable(entityPlayer, func_178782_a)) {
                return itemStack;
            }
            if (b) {
                if (!entityPlayer.func_175151_a(func_178782_a.offset(movingObjectPositionFromPlayer.field_178784_b), movingObjectPositionFromPlayer.field_178784_b, itemStack)) {
                    return itemStack;
                }
                final IBlockState blockState = world.getBlockState(func_178782_a);
                final Material material = blockState.getBlock().getMaterial();
                if (material == Material.water && (int)blockState.getValue(BlockLiquid.LEVEL) == 0) {
                    world.setBlockToAir(func_178782_a);
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.func_150910_a(itemStack, entityPlayer, Items.water_bucket);
                }
                if (material == Material.lava && (int)blockState.getValue(BlockLiquid.LEVEL) == 0) {
                    world.setBlockToAir(func_178782_a);
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.func_150910_a(itemStack, entityPlayer, Items.lava_bucket);
                }
            }
            else {
                if (this.isFull == Blocks.air) {
                    return new ItemStack(Items.bucket);
                }
                final BlockPos offset = func_178782_a.offset(movingObjectPositionFromPlayer.field_178784_b);
                if (!entityPlayer.func_175151_a(offset, movingObjectPositionFromPlayer.field_178784_b, itemStack)) {
                    return itemStack;
                }
                if (this.func_180616_a(world, offset) && !entityPlayer.capabilities.isCreativeMode) {
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return new ItemStack(Items.bucket);
                }
            }
        }
        return itemStack;
    }
    
    private ItemStack func_150910_a(final ItemStack itemStack, final EntityPlayer entityPlayer, final Item item) {
        if (entityPlayer.capabilities.isCreativeMode) {
            return itemStack;
        }
        if (--itemStack.stackSize <= 0) {
            return new ItemStack(item);
        }
        if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(item))) {
            entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(item, 1, 0), false);
        }
        return itemStack;
    }
    
    public boolean func_180616_a(final World world, final BlockPos blockPos) {
        if (this.isFull == Blocks.air) {
            return false;
        }
        final Material material = world.getBlockState(blockPos).getBlock().getMaterial();
        final boolean b = !material.isSolid();
        if (!world.isAirBlock(blockPos) && !b) {
            return false;
        }
        if (world.provider.func_177500_n() && this.isFull == Blocks.flowing_water) {
            final int x = blockPos.getX();
            final int y = blockPos.getY();
            final int z = blockPos.getZ();
            world.playSoundEffect(x + 0.5f, y + 0.5f, z + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            while (0 < 8) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + Math.random(), y + Math.random(), z + Math.random(), 0.0, 0.0, 0.0, new int[0]);
                int n = 0;
                ++n;
            }
        }
        else {
            if (!world.isRemote && b && !material.isLiquid()) {
                world.destroyBlock(blockPos, true);
            }
            world.setBlockState(blockPos, this.isFull.getDefaultState(), 3);
        }
        return true;
    }
    
    static {
        __OBFID = "CL_00000000";
    }
}
