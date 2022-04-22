package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.state.*;

public abstract class BlockWoodSlab extends BlockSlab
{
    public static final PropertyEnum field_176557_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000337";
        field_176557_b = PropertyEnum.create("variant", BlockPlanks.EnumType.class);
    }
    
    public BlockWoodSlab() {
        super(Material.wood);
        IBlockState blockState = this.blockState.getBaseState();
        if (!this.isDouble()) {
            blockState = blockState.withProperty(BlockWoodSlab.HALF_PROP, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockWoodSlab.field_176557_b, BlockPlanks.EnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }
    
    @Override
    public String getFullSlabName(final int n) {
        return String.valueOf(super.getUnlocalizedName()) + "." + BlockPlanks.EnumType.func_176837_a(n).func_176840_c();
    }
    
    @Override
    public IProperty func_176551_l() {
        return BlockWoodSlab.field_176557_b;
    }
    
    @Override
    public Object func_176553_a(final ItemStack itemStack) {
        return BlockPlanks.EnumType.func_176837_a(itemStack.getMetadata() & 0x7);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (item != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
            final BlockPlanks.EnumType[] values = BlockPlanks.EnumType.values();
            while (0 < values.length) {
                list.add(new ItemStack(item, 1, values[0].func_176839_a()));
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        IBlockState blockState = this.getDefaultState().withProperty(BlockWoodSlab.field_176557_b, BlockPlanks.EnumType.func_176837_a(n & 0x7));
        if (!this.isDouble()) {
            blockState = blockState.withProperty(BlockWoodSlab.HALF_PROP, ((n & 0x8) == 0x0) ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }
        return blockState;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockWoodSlab.field_176557_b)).func_176839_a();
        if (!this.isDouble() && blockState.getValue(BlockWoodSlab.HALF_PROP) == EnumBlockHalf.TOP) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return this.isDouble() ? new BlockState(this, new IProperty[] { BlockWoodSlab.field_176557_b }) : new BlockState(this, new IProperty[] { BlockWoodSlab.HALF_PROP, BlockWoodSlab.field_176557_b });
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockWoodSlab.field_176557_b)).func_176839_a();
    }
}
