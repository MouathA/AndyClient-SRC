package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import java.util.*;

public class BlockDaylightDetector extends BlockContainer
{
    public static final PropertyInteger field_176436_a;
    private final boolean field_176435_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000223";
        field_176436_a = PropertyInteger.create("power", 0, 15);
    }
    
    public BlockDaylightDetector(final boolean field_176435_b) {
        super(Material.wood);
        this.field_176435_b = field_176435_b;
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDaylightDetector.field_176436_a, 0));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setHardness(0.2f);
        this.setStepSound(BlockDaylightDetector.soundTypeWood);
        this.setUnlocalizedName("daylightDetector");
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (int)blockState.getValue(BlockDaylightDetector.field_176436_a);
    }
    
    public void func_180677_d(final World world, final BlockPos blockPos) {
        if (!world.provider.getHasNoSky()) {
            final IBlockState blockState = world.getBlockState(blockPos);
            final int n = world.getLightFor(EnumSkyBlock.SKY, blockPos) - world.getSkylightSubtracted();
            final float celestialAngleRadians = world.getCelestialAngleRadians(1.0f);
            int clamp_int = MathHelper.clamp_int(Math.round(n * MathHelper.cos(celestialAngleRadians + (((celestialAngleRadians < 3.1415927f) ? 0.0f : 6.2831855f) - celestialAngleRadians) * 0.2f)), 0, 15);
            if (this.field_176435_b) {
                clamp_int = 15 - clamp_int;
            }
            if ((int)blockState.getValue(BlockDaylightDetector.field_176436_a) != clamp_int) {
                world.setBlockState(blockPos, blockState.withProperty(BlockDaylightDetector.field_176436_a, clamp_int), 3);
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.func_175142_cm()) {
            return super.onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3);
        }
        if (world.isRemote) {
            return true;
        }
        if (this.field_176435_b) {
            world.setBlockState(blockPos, Blocks.daylight_detector.getDefaultState().withProperty(BlockDaylightDetector.field_176436_a, blockState.getValue(BlockDaylightDetector.field_176436_a)), 4);
            Blocks.daylight_detector.func_180677_d(world, blockPos);
        }
        else {
            world.setBlockState(blockPos, Blocks.daylight_detector_inverted.getDefaultState().withProperty(BlockDaylightDetector.field_176436_a, blockState.getValue(BlockDaylightDetector.field_176436_a)), 4);
            Blocks.daylight_detector_inverted.func_180677_d(world, blockPos);
        }
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
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
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDaylightDetector();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockDaylightDetector.field_176436_a, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockDaylightDetector.field_176436_a);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDaylightDetector.field_176436_a });
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (!this.field_176435_b) {
            super.getSubBlocks(item, creativeTabs, list);
        }
    }
}
