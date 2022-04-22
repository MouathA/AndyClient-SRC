package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockStainedGlassPane extends BlockPane
{
    public static final PropertyEnum field_176245_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000313";
        field_176245_a = PropertyEnum.create("color", EnumDyeColor.class);
    }
    
    public BlockStainedGlassPane() {
        super(Material.glass, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStainedGlassPane.NORTH, false).withProperty(BlockStainedGlassPane.EAST, false).withProperty(BlockStainedGlassPane.SOUTH, false).withProperty(BlockStainedGlassPane.WEST, false).withProperty(BlockStainedGlassPane.field_176245_a, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlassPane.field_176245_a)).func_176765_a();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        while (0 < EnumDyeColor.values().length) {
            list.add(new ItemStack(item, 1, 0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStainedGlassPane.field_176245_a, EnumDyeColor.func_176764_b(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlassPane.field_176245_a)).func_176765_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStainedGlassPane.NORTH, BlockStainedGlassPane.EAST, BlockStainedGlassPane.WEST, BlockStainedGlassPane.SOUTH, BlockStainedGlassPane.field_176245_a });
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.func_176450_d(world, blockPos);
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.func_176450_d(world, blockPos);
        }
    }
}
