package net.minecraft.block;

import com.google.common.collect.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class BlockRedstoneTorch extends BlockTorch
{
    private static Map field_150112_b;
    private final boolean field_150113_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000298";
        BlockRedstoneTorch.field_150112_b = Maps.newHashMap();
    }
    
    private boolean func_176598_a(final World world, final BlockPos blockPos, final boolean b) {
        if (!BlockRedstoneTorch.field_150112_b.containsKey(world)) {
            BlockRedstoneTorch.field_150112_b.put(world, Lists.newArrayList());
        }
        final List<Toggle> list = BlockRedstoneTorch.field_150112_b.get(world);
        if (b) {
            list.add(new Toggle(blockPos, world.getTotalWorldTime()));
        }
        while (0 < list.size()) {
            if (list.get(0).field_180111_a.equals(blockPos)) {
                int n = 0;
                ++n;
                if (0 >= 8) {
                    return true;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    protected BlockRedstoneTorch(final boolean field_150113_a) {
        this.field_150113_a = field_150113_a;
        this.setTickRandomly(true);
        this.setCreativeTab(null);
    }
    
    @Override
    public int tickRate(final World world) {
        return 2;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.field_150113_a) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.field_150113_a) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (this.field_150113_a && blockState.getValue(BlockRedstoneTorch.FACING_PROP) != enumFacing) ? 15 : 0;
    }
    
    private boolean func_176597_g(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing opposite = ((EnumFacing)blockState.getValue(BlockRedstoneTorch.FACING_PROP)).getOpposite();
        return world.func_175709_b(blockPos.offset(opposite), opposite);
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final boolean func_176597_g = this.func_176597_g(world, blockPos, blockState);
        final List<Toggle> list = BlockRedstoneTorch.field_150112_b.get(world);
        while (list != null && !list.isEmpty() && world.getTotalWorldTime() - list.get(0).field_150844_d > 60L) {
            list.remove(0);
        }
        if (this.field_150113_a) {
            if (func_176597_g) {
                world.setBlockState(blockPos, Blocks.unlit_redstone_torch.getDefaultState().withProperty(BlockRedstoneTorch.FACING_PROP, blockState.getValue(BlockRedstoneTorch.FACING_PROP)), 3);
                if (this.func_176598_a(world, blockPos, true)) {
                    world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                    while (0 < 5) {
                        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX() + random.nextDouble() * 0.6 + 0.2, blockPos.getY() + random.nextDouble() * 0.6 + 0.2, blockPos.getZ() + random.nextDouble() * 0.6 + 0.2, 0.0, 0.0, 0.0, new int[0]);
                        int n = 0;
                        ++n;
                    }
                    world.scheduleUpdate(blockPos, world.getBlockState(blockPos).getBlock(), 160);
                }
            }
        }
        else if (!func_176597_g && !this.func_176598_a(world, blockPos, false)) {
            world.setBlockState(blockPos, Blocks.redstone_torch.getDefaultState().withProperty(BlockRedstoneTorch.FACING_PROP, blockState.getValue(BlockRedstoneTorch.FACING_PROP)), 3);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.func_176592_e(world, blockPos, blockState) && this.field_150113_a == this.func_176597_g(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.DOWN) ? this.isProvidingWeakPower(blockAccess, blockPos, blockState, enumFacing) : 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.field_150113_a) {
            double n = blockPos.getX() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            double n2 = blockPos.getY() + 0.7f + (random.nextFloat() - 0.5f) * 0.2;
            double n3 = blockPos.getZ() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneTorch.FACING_PROP);
            if (enumFacing.getAxis().isHorizontal()) {
                final EnumFacing opposite = enumFacing.getOpposite();
                n += 0.27000001072883606 * opposite.getFrontOffsetX();
                n2 += 0.2199999988079071;
                n3 += 0.27000001072883606 * opposite.getFrontOffsetZ();
            }
            world.spawnParticle(EnumParticleTypes.REDSTONE, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    @Override
    public boolean isAssociatedBlock(final Block block) {
        return block == Blocks.unlit_redstone_torch || block == Blocks.redstone_torch;
    }
    
    static class Toggle
    {
        BlockPos field_180111_a;
        long field_150844_d;
        private static final String __OBFID;
        
        public Toggle(final BlockPos field_180111_a, final long field_150844_d) {
            this.field_180111_a = field_180111_a;
            this.field_150844_d = field_150844_d;
        }
        
        static {
            __OBFID = "CL_00000299";
        }
    }
}
