package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;

public class BlockRedstoneLight extends Block
{
    private final boolean isOn;
    private static final String __OBFID;
    
    public BlockRedstoneLight(final boolean isOn) {
        super(Material.redstoneLight);
        this.isOn = isOn;
        if (isOn) {
            this.setLightLevel(1.0f);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            if (this.isOn && !world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.redstone_lamp.getDefaultState(), 2);
            }
            else if (!this.isOn && world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            if (this.isOn && !world.isBlockPowered(blockPos)) {
                world.scheduleUpdate(blockPos, this, 4);
            }
            else if (!this.isOn && world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
            }
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && this.isOn && !world.isBlockPowered(blockPos)) {
            world.setBlockState(blockPos, Blocks.redstone_lamp.getDefaultState(), 2);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Blocks.redstone_lamp);
    }
    
    static {
        __OBFID = "CL_00000297";
    }
}
