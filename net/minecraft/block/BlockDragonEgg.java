package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

public class BlockDragonEgg extends Block
{
    private static final String __OBFID;
    
    public BlockDragonEgg() {
        super(Material.dragonEgg);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.func_180683_d(world, blockPos);
    }
    
    private void func_180683_d(final World world, final BlockPos blockToAir) {
        if (BlockFalling.canFallInto(world, blockToAir.offsetDown()) && blockToAir.getY() >= 0) {
            if (!BlockFalling.fallInstantly && world.isAreaLoaded(blockToAir.add(-32, -32, -32), blockToAir.add(32, 32, 32))) {
                world.spawnEntityInWorld(new EntityFallingBlock(world, blockToAir.getX() + 0.5f, blockToAir.getY(), blockToAir.getZ() + 0.5f, this.getDefaultState()));
            }
            else {
                world.setBlockToAir(blockToAir);
                BlockPos offsetDown;
                for (offsetDown = blockToAir; BlockFalling.canFallInto(world, offsetDown) && offsetDown.getY() > 0; offsetDown = offsetDown.offsetDown()) {}
                if (offsetDown.getY() > 0) {
                    world.setBlockState(offsetDown, this.getDefaultState(), 2);
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.func_180684_e(world, blockPos);
        return true;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.func_180684_e(world, blockPos);
    }
    
    private void func_180684_e(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() == this) {}
    }
    
    @Override
    public int tickRate(final World world) {
        return 5;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    static {
        __OBFID = "CL_00000232";
    }
}
