package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockMobSpawner extends BlockContainer
{
    private static final String __OBFID;
    
    protected BlockMobSpawner() {
        super(Material.rock);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityMobSpawner();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        this.dropXpOnBlockBreak(world, blockPos, 15 + world.rand.nextInt(15) + world.rand.nextInt(15));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    static {
        __OBFID = "CL_00000269";
    }
}
