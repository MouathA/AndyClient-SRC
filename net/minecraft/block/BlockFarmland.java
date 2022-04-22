package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;

public class BlockFarmland extends Block
{
    public static final PropertyInteger field_176531_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000241";
        field_176531_a = PropertyInteger.create("moisture", 0, 7);
    }
    
    protected BlockFarmland() {
        super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFarmland.field_176531_a, 0));
        this.setTickRandomly(true);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(255);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1);
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
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final int intValue = (int)blockState.getValue(BlockFarmland.field_176531_a);
        if (!this.func_176530_e(world, blockPos) && !world.func_175727_C(blockPos.offsetUp())) {
            if (intValue > 0) {
                world.setBlockState(blockPos, blockState.withProperty(BlockFarmland.field_176531_a, intValue - 1), 2);
            }
            else if (!this.func_176529_d(world, blockPos)) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            }
        }
        else if (intValue < 7) {
            world.setBlockState(blockPos, blockState.withProperty(BlockFarmland.field_176531_a, 7), 2);
        }
    }
    
    @Override
    public void onFallenUpon(final World world, final BlockPos blockPos, final Entity entity, final float n) {
        if (entity instanceof EntityLivingBase) {
            if (!world.isRemote && world.rand.nextFloat() < n - 0.5f) {
                if (!(entity instanceof EntityPlayer) && !world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    return;
                }
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            }
            super.onFallenUpon(world, blockPos, entity, n);
        }
    }
    
    private boolean func_176529_d(final World world, final BlockPos blockPos) {
        final Block block = world.getBlockState(blockPos.offsetUp()).getBlock();
        return block instanceof BlockCrops || block instanceof BlockStem;
    }
    
    private boolean func_176530_e(final World world, final BlockPos blockPos) {
        final Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.getAllInBoxMutable(blockPos.add(-4, 0, -4), blockPos.add(4, 1, 4)).iterator();
        while (iterator.hasNext()) {
            if (world.getBlockState(iterator.next()).getBlock().getMaterial() == Material.water) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        if (world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial().isSolid()) {
            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, n);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.dirt);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockFarmland.field_176531_a, n & 0x7);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockFarmland.field_176531_a);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFarmland.field_176531_a });
    }
}
