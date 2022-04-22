package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class BlockFalling extends Block
{
    public static boolean fallInstantly;
    private static final String __OBFID;
    
    public BlockFalling() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public BlockFalling(final Material material) {
        super(material);
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
        if (!world.isRemote) {
            this.checkFallable(world, blockPos);
        }
    }
    
    private void checkFallable(final World world, final BlockPos blockToAir) {
        if (canFallInto(world, blockToAir.offsetDown()) && blockToAir.getY() >= 0) {
            if (!BlockFalling.fallInstantly && world.isAreaLoaded(blockToAir.add(-32, -32, -32), blockToAir.add(32, 32, 32))) {
                if (!world.isRemote) {
                    final EntityFallingBlock entityFallingBlock = new EntityFallingBlock(world, blockToAir.getX() + 0.5, blockToAir.getY(), blockToAir.getZ() + 0.5, world.getBlockState(blockToAir));
                    this.onStartFalling(entityFallingBlock);
                    world.spawnEntityInWorld(entityFallingBlock);
                }
            }
            else {
                world.setBlockToAir(blockToAir);
                BlockPos blockPos;
                for (blockPos = blockToAir.offsetDown(); canFallInto(world, blockPos) && blockPos.getY() > 0; blockPos = blockPos.offsetDown()) {}
                if (blockPos.getY() > 0) {
                    world.setBlockState(blockPos.offsetUp(), this.getDefaultState());
                }
            }
        }
    }
    
    protected void onStartFalling(final EntityFallingBlock entityFallingBlock) {
    }
    
    @Override
    public int tickRate(final World world) {
        return 2;
    }
    
    public static boolean canFallInto(final World world, final BlockPos blockPos) {
        final Block block = world.getBlockState(blockPos).getBlock();
        final Material blockMaterial = block.blockMaterial;
        return block == Blocks.fire || blockMaterial == Material.air || blockMaterial == Material.water || blockMaterial == Material.lava;
    }
    
    public void onEndFalling(final World world, final BlockPos blockPos) {
    }
    
    static {
        __OBFID = "CL_00000240";
    }
}
