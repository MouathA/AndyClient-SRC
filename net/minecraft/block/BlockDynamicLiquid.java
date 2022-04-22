package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;

public class BlockDynamicLiquid extends BlockLiquid
{
    int field_149815_a;
    private static final String __OBFID;
    
    protected BlockDynamicLiquid(final Material material) {
        super(material);
    }
    
    private void func_180690_f(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, BlockLiquid.getStaticLiquidForMaterial(this.blockMaterial).getDefaultState().withProperty(BlockDynamicLiquid.LEVEL, blockState.getValue(BlockDynamicLiquid.LEVEL)), 2);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, IBlockState withProperty, final Random random) {
        (int)withProperty.getValue(BlockDynamicLiquid.LEVEL);
        if (this.blockMaterial != Material.lava || !world.provider.func_177500_n()) {}
        int tickRate = this.tickRate(world);
        if (0 > 0) {
            this.field_149815_a = 0;
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext()) {
                this.func_176371_a(world, blockToAir.offset(iterator.next()), -100);
            }
            if (0 >= 8 || -100 < 0) {}
            if (this.func_176362_e(world, blockToAir.offsetUp()) >= 0) {
                this.func_176362_e(world, blockToAir.offsetUp());
                if (1 >= 8) {}
            }
            if (this.field_149815_a >= 2 && this.blockMaterial == Material.water) {
                final IBlockState blockState = world.getBlockState(blockToAir.offsetDown());
                if (!blockState.getBlock().getMaterial().isSolid()) {
                    if (blockState.getBlock().getMaterial() != this.blockMaterial || (int)blockState.getValue(BlockDynamicLiquid.LEVEL) == 0) {}
                }
            }
            if (this.blockMaterial == Material.lava && 0 < 8 && 0 < 8 && 0 > 0 && random.nextInt(4) != 0) {
                tickRate *= 4;
            }
            if (!false) {
                this.func_180690_f(world, blockToAir, withProperty);
            }
            else if (0 < 0) {
                world.setBlockToAir(blockToAir);
            }
            else {
                withProperty = withProperty.withProperty(BlockDynamicLiquid.LEVEL, 0);
                world.setBlockState(blockToAir, withProperty, 2);
                world.scheduleUpdate(blockToAir, this, tickRate);
                world.notifyNeighborsOfStateChange(blockToAir, this);
            }
        }
        else {
            this.func_180690_f(world, blockToAir, withProperty);
        }
        final IBlockState blockState2 = world.getBlockState(blockToAir.offsetDown());
        if (this.func_176373_h(world, blockToAir.offsetDown(), blockState2)) {
            if (this.blockMaterial == Material.lava && world.getBlockState(blockToAir.offsetDown()).getBlock().getMaterial() == Material.water) {
                world.setBlockState(blockToAir.offsetDown(), Blocks.stone.getDefaultState());
                this.func_180688_d(world, blockToAir.offsetDown());
                return;
            }
            if (0 >= 8) {
                this.func_176375_a(world, blockToAir.offsetDown(), blockState2, 0);
            }
            else {
                this.func_176375_a(world, blockToAir.offsetDown(), blockState2, 8);
            }
        }
        else if (0 >= 0 && (!false || this.func_176372_g(world, blockToAir.offsetDown(), blockState2))) {
            final Set func_176376_e = this.func_176376_e(world, blockToAir);
            if (0 >= 8) {}
            if (1 >= 8) {
                return;
            }
            for (final EnumFacing enumFacing : func_176376_e) {
                this.func_176375_a(world, blockToAir.offset(enumFacing), world.getBlockState(blockToAir.offset(enumFacing)), 1);
            }
        }
    }
    
    private void func_176375_a(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (this.func_176373_h(world, blockPos, blockState)) {
            if (blockState.getBlock() != Blocks.air) {
                if (this.blockMaterial == Material.lava) {
                    this.func_180688_d(world, blockPos);
                }
                else {
                    blockState.getBlock().dropBlockAsItem(world, blockPos, blockState, 0);
                }
            }
            world.setBlockState(blockPos, this.getDefaultState().withProperty(BlockDynamicLiquid.LEVEL, n), 3);
        }
    }
    
    private int func_176374_a(final World world, final BlockPos blockPos, final int n, final EnumFacing enumFacing) {
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (enumFacing2 != enumFacing) {
                final BlockPos offset = blockPos.offset(enumFacing2);
                final IBlockState blockState = world.getBlockState(offset);
                if (this.func_176372_g(world, offset, blockState) || (blockState.getBlock().getMaterial() == this.blockMaterial && (int)blockState.getValue(BlockDynamicLiquid.LEVEL) <= 0)) {
                    continue;
                }
                if (!this.func_176372_g(world, offset.offsetDown(), blockState)) {
                    return n;
                }
                if (n < 4 && this.func_176374_a(world, offset, n + 1, enumFacing2.getOpposite()) < 1000) {
                    continue;
                }
                continue;
            }
        }
        return 1000;
    }
    
    private Set func_176376_e(final World world, final BlockPos blockPos) {
        final EnumSet<EnumFacing> none = EnumSet.noneOf(EnumFacing.class);
        for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
            final BlockPos offset = blockPos.offset(enumFacing);
            final IBlockState blockState = world.getBlockState(offset);
            if (!this.func_176372_g(world, offset, blockState) && (blockState.getBlock().getMaterial() != this.blockMaterial || (int)blockState.getValue(BlockDynamicLiquid.LEVEL) > 0)) {
                if (this.func_176372_g(world, offset.offsetDown(), world.getBlockState(offset.offsetDown()))) {
                    this.func_176374_a(world, offset, 1, enumFacing.getOpposite());
                }
                if (0 < 1000) {
                    none.clear();
                }
                if (0 > 1000) {
                    continue;
                }
                none.add(enumFacing);
            }
        }
        return none;
    }
    
    private boolean func_176372_g(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final Block block = world.getBlockState(blockPos).getBlock();
        return block instanceof BlockDoor || block == Blocks.standing_sign || block == Blocks.ladder || block == Blocks.reeds || block.blockMaterial == Material.portal || block.blockMaterial.blocksMovement();
    }
    
    protected int func_176371_a(final World world, final BlockPos blockPos, final int n) {
        this.func_176362_e(world, blockPos);
        if (0 < 0) {
            return n;
        }
        if (!false) {
            ++this.field_149815_a;
        }
        if (0 >= 8) {}
        return (n >= 0 && 0 >= n) ? n : 0;
    }
    
    private boolean func_176373_h(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final Material material = blockState.getBlock().getMaterial();
        return material != this.blockMaterial && material != Material.lava && !this.func_176372_g(world, blockPos, blockState);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.func_176365_e(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    static {
        __OBFID = "CL_00000234";
    }
}
