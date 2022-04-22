package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockRedstoneRepeater extends BlockRedstoneDiode
{
    public static final PropertyBool field_176411_a;
    public static final PropertyInteger field_176410_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000301";
        field_176411_a = PropertyBool.create("locked");
        field_176410_b = PropertyInteger.create("delay", 1, 4);
    }
    
    protected BlockRedstoneRepeater(final boolean b) {
        super(b);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedstoneRepeater.AGE, EnumFacing.NORTH).withProperty(BlockRedstoneRepeater.field_176410_b, 1).withProperty(BlockRedstoneRepeater.field_176411_a, false));
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockRedstoneRepeater.field_176411_a, this.func_176405_b(blockAccess, blockPos, blockState));
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.capabilities.allowEdit) {
            return false;
        }
        world.setBlockState(blockPos, blockState.cycleProperty(BlockRedstoneRepeater.field_176410_b), 3);
        return true;
    }
    
    @Override
    protected int func_176403_d(final IBlockState blockState) {
        return (int)blockState.getValue(BlockRedstoneRepeater.field_176410_b) * 2;
    }
    
    @Override
    protected IBlockState func_180674_e(final IBlockState blockState) {
        return Blocks.powered_repeater.getDefaultState().withProperty(BlockRedstoneRepeater.AGE, blockState.getValue(BlockRedstoneRepeater.AGE)).withProperty(BlockRedstoneRepeater.field_176410_b, blockState.getValue(BlockRedstoneRepeater.field_176410_b)).withProperty(BlockRedstoneRepeater.field_176411_a, blockState.getValue(BlockRedstoneRepeater.field_176411_a));
    }
    
    @Override
    protected IBlockState func_180675_k(final IBlockState blockState) {
        return Blocks.unpowered_repeater.getDefaultState().withProperty(BlockRedstoneRepeater.AGE, blockState.getValue(BlockRedstoneRepeater.AGE)).withProperty(BlockRedstoneRepeater.field_176410_b, blockState.getValue(BlockRedstoneRepeater.field_176410_b)).withProperty(BlockRedstoneRepeater.field_176411_a, blockState.getValue(BlockRedstoneRepeater.field_176411_a));
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.repeater;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.repeater;
    }
    
    @Override
    public boolean func_176405_b(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return this.func_176407_c(blockAccess, blockPos, blockState) > 0;
    }
    
    @Override
    protected boolean func_149908_a(final Block block) {
        return isRedstoneRepeaterBlockID(block);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isRepeaterPowered) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneRepeater.AGE);
            final double n = blockPos.getX() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            final double n2 = blockPos.getY() + 0.4f + (random.nextFloat() - 0.5f) * 0.2;
            final double n3 = blockPos.getZ() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            float n4 = -5.0f;
            if (random.nextBoolean()) {
                n4 = (float)((int)blockState.getValue(BlockRedstoneRepeater.field_176410_b) * 2 - 1);
            }
            final float n5 = n4 / 16.0f;
            world.spawnParticle(EnumParticleTypes.REDSTONE, n + n5 * enumFacing.getFrontOffsetX(), n2, n3 + n5 * enumFacing.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        this.func_176400_h(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRedstoneRepeater.AGE, EnumFacing.getHorizontal(n)).withProperty(BlockRedstoneRepeater.field_176411_a, false).withProperty(BlockRedstoneRepeater.field_176410_b, 1 + (n >> 2));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0x0 | ((EnumFacing)blockState.getValue(BlockRedstoneRepeater.AGE)).getHorizontalIndex() | (int)blockState.getValue(BlockRedstoneRepeater.field_176410_b) - 1 << 2;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedstoneRepeater.AGE, BlockRedstoneRepeater.field_176410_b, BlockRedstoneRepeater.field_176411_a });
    }
}
