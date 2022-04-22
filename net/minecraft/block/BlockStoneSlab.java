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

public abstract class BlockStoneSlab extends BlockSlab
{
    public static final PropertyBool field_176555_b;
    public static final PropertyEnum field_176556_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000320";
        field_176555_b = PropertyBool.create("seamless");
        field_176556_M = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockStoneSlab() {
        super(Material.rock);
        final IBlockState baseState = this.blockState.getBaseState();
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = baseState.withProperty(BlockStoneSlab.field_176555_b, false);
        }
        else {
            blockState = baseState.withProperty(BlockStoneSlab.HALF_PROP, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockStoneSlab.field_176556_M, EnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }
    
    @Override
    public String getFullSlabName(final int n) {
        return String.valueOf(super.getUnlocalizedName()) + "." + EnumType.func_176625_a(n).func_176627_c();
    }
    
    @Override
    public IProperty func_176551_l() {
        return BlockStoneSlab.field_176556_M;
    }
    
    @Override
    public Object func_176553_a(final ItemStack itemStack) {
        return EnumType.func_176625_a(itemStack.getMetadata() & 0x7);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab)) {
            final EnumType[] values = EnumType.values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                if (enumType != EnumType.WOOD) {
                    list.add(new ItemStack(item, 1, enumType.func_176624_a()));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockStoneSlab.field_176556_M, EnumType.func_176625_a(n & 0x7));
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = withProperty.withProperty(BlockStoneSlab.field_176555_b, (n & 0x8) != 0x0);
        }
        else {
            blockState = withProperty.withProperty(BlockStoneSlab.HALF_PROP, ((n & 0x8) == 0x0) ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }
        return blockState;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumType)blockState.getValue(BlockStoneSlab.field_176556_M)).func_176624_a();
        if (this.isDouble()) {
            if (blockState.getValue(BlockStoneSlab.field_176555_b)) {
                n |= 0x8;
            }
        }
        else if (blockState.getValue(BlockStoneSlab.HALF_PROP) == EnumBlockHalf.TOP) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return this.isDouble() ? new BlockState(this, new IProperty[] { BlockStoneSlab.field_176555_b, BlockStoneSlab.field_176556_M }) : new BlockState(this, new IProperty[] { BlockStoneSlab.HALF_PROP, BlockStoneSlab.field_176556_M });
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneSlab.field_176556_M)).func_176624_a();
    }
    
    public enum EnumType implements IStringSerializable
    {
        STONE("STONE", 0, "STONE", 0, 0, "stone"), 
        SAND("SAND", 1, "SAND", 1, 1, "sandstone", "sand"), 
        WOOD("WOOD", 2, "WOOD", 2, 2, "wood_old", "wood"), 
        COBBLESTONE("COBBLESTONE", 3, "COBBLESTONE", 3, 3, "cobblestone", "cobble"), 
        BRICK("BRICK", 4, "BRICK", 4, 4, "brick"), 
        SMOOTHBRICK("SMOOTHBRICK", 5, "SMOOTHBRICK", 5, 5, "stone_brick", "smoothStoneBrick"), 
        NETHERBRICK("NETHERBRICK", 6, "NETHERBRICK", 6, 6, "nether_brick", "netherBrick"), 
        QUARTZ("QUARTZ", 7, "QUARTZ", 7, 7, "quartz");
        
        private static final EnumType[] field_176640_i;
        private final int field_176637_j;
        private final String field_176638_k;
        private final String field_176635_l;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002056";
            ENUM$VALUES = new EnumType[] { EnumType.STONE, EnumType.SAND, EnumType.WOOD, EnumType.COBBLESTONE, EnumType.BRICK, EnumType.SMOOTHBRICK, EnumType.NETHERBRICK, EnumType.QUARTZ };
            field_176640_i = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.STONE, EnumType.SAND, EnumType.WOOD, EnumType.COBBLESTONE, EnumType.BRICK, EnumType.SMOOTHBRICK, EnumType.NETHERBRICK, EnumType.QUARTZ };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176640_i[enumType.func_176624_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3) {
            this(s, n, s2, n2, n3, s3, s3);
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176637_j, final String field_176638_k, final String field_176635_l) {
            this.field_176637_j = field_176637_j;
            this.field_176638_k = field_176638_k;
            this.field_176635_l = field_176635_l;
        }
        
        public int func_176624_a() {
            return this.field_176637_j;
        }
        
        @Override
        public String toString() {
            return this.field_176638_k;
        }
        
        public static EnumType func_176625_a(final int n) {
            if (0 >= EnumType.field_176640_i.length) {}
            return EnumType.field_176640_i[0];
        }
        
        @Override
        public String getName() {
            return this.field_176638_k;
        }
        
        public String func_176627_c() {
            return this.field_176635_l;
        }
    }
}
