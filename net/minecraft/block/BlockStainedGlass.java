package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockStainedGlass extends BlockBreakable
{
    public static final PropertyEnum field_176547_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000312";
        field_176547_a = PropertyEnum.create("color", EnumDyeColor.class);
    }
    
    public BlockStainedGlass(final Material material) {
        super(material, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStainedGlass.field_176547_a, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlass.field_176547_a)).func_176765_a();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumDyeColor[] values = EnumDyeColor.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176765_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlass.field_176547_a)).func_176768_e();
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStainedGlass.field_176547_a, EnumDyeColor.func_176764_b(n));
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
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockStainedGlass.field_176547_a)).func_176765_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStainedGlass.field_176547_a });
    }
}
