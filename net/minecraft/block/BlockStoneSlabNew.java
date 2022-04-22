package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public abstract class BlockStoneSlabNew extends BlockSlab
{
    public static final PropertyBool field_176558_b;
    public static final PropertyEnum field_176559_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002087";
        field_176558_b = PropertyBool.create("seamless");
        field_176559_M = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockStoneSlabNew() {
        super(Material.rock);
        final IBlockState baseState = this.blockState.getBaseState();
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = baseState.withProperty(BlockStoneSlabNew.field_176558_b, false);
        }
        else {
            blockState = baseState.withProperty(BlockStoneSlabNew.HALF_PROP, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockStoneSlabNew.field_176559_M, EnumType.RED_SANDSTONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }
    
    @Override
    public String getFullSlabName(final int n) {
        return String.valueOf(super.getUnlocalizedName()) + "." + EnumType.func_176916_a(n).func_176918_c();
    }
    
    @Override
    public IProperty func_176551_l() {
        return BlockStoneSlabNew.field_176559_M;
    }
    
    @Override
    public Object func_176553_a(final ItemStack itemStack) {
        return EnumType.func_176916_a(itemStack.getMetadata() & 0x7);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab2)) {
            final EnumType[] values = EnumType.values();
            while (0 < values.length) {
                list.add(new ItemStack(item, 1, values[0].func_176915_a()));
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockStoneSlabNew.field_176559_M, EnumType.func_176916_a(n & 0x7));
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = withProperty.withProperty(BlockStoneSlabNew.field_176558_b, (n & 0x8) != 0x0);
        }
        else {
            blockState = withProperty.withProperty(BlockStoneSlabNew.HALF_PROP, ((n & 0x8) == 0x0) ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }
        return blockState;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumType)blockState.getValue(BlockStoneSlabNew.field_176559_M)).func_176915_a();
        if (this.isDouble()) {
            if (blockState.getValue(BlockStoneSlabNew.field_176558_b)) {
                n |= 0x8;
            }
        }
        else if (blockState.getValue(BlockStoneSlabNew.HALF_PROP) == EnumBlockHalf.TOP) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return this.isDouble() ? new BlockState(this, new IProperty[] { BlockStoneSlabNew.field_176558_b, BlockStoneSlabNew.field_176559_M }) : new BlockState(this, new IProperty[] { BlockStoneSlabNew.HALF_PROP, BlockStoneSlabNew.field_176559_M });
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneSlabNew.field_176559_M)).func_176915_a();
    }
    
    public enum EnumType implements IStringSerializable
    {
        RED_SANDSTONE("RED_SANDSTONE", 0, "RED_SANDSTONE", 0, 0, "red_sandstone");
        
        private static final EnumType[] field_176921_b;
        private final int field_176922_c;
        private final String field_176919_d;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002086";
            ENUM$VALUES = new EnumType[] { EnumType.RED_SANDSTONE };
            field_176921_b = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.RED_SANDSTONE };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176921_b[enumType.func_176915_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176922_c, final String field_176919_d) {
            this.field_176922_c = field_176922_c;
            this.field_176919_d = field_176919_d;
        }
        
        public int func_176915_a() {
            return this.field_176922_c;
        }
        
        @Override
        public String toString() {
            return this.field_176919_d;
        }
        
        public static EnumType func_176916_a(final int n) {
            if (0 < 0 || 0 >= EnumType.field_176921_b.length) {}
            return EnumType.field_176921_b[0];
        }
        
        @Override
        public String getName() {
            return this.field_176919_d;
        }
        
        public String func_176918_c() {
            return this.field_176919_d;
        }
    }
}
