package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockPlanks extends Block
{
    public static final PropertyEnum VARIANT_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002082";
        VARIANT_PROP = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockPlanks() {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPlanks.VARIANT_PROP, EnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPlanks.VARIANT_PROP)).func_176839_a();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176839_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPlanks.VARIANT_PROP, EnumType.func_176837_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPlanks.VARIANT_PROP)).func_176839_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPlanks.VARIANT_PROP });
    }
    
    public enum EnumType implements IStringSerializable
    {
        OAK("OAK", 0, "OAK", 0, 0, "oak"), 
        SPRUCE("SPRUCE", 1, "SPRUCE", 1, 1, "spruce"), 
        BIRCH("BIRCH", 2, "BIRCH", 2, 2, "birch"), 
        JUNGLE("JUNGLE", 3, "JUNGLE", 3, 3, "jungle"), 
        ACACIA("ACACIA", 4, "ACACIA", 4, 4, "acacia"), 
        DARK_OAK("DARK_OAK", 5, "DARK_OAK", 5, 5, "dark_oak", "big_oak");
        
        private static final EnumType[] field_176842_g;
        private final int field_176850_h;
        private final String field_176851_i;
        private final String field_176848_j;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002081";
            ENUM$VALUES = new EnumType[] { EnumType.OAK, EnumType.SPRUCE, EnumType.BIRCH, EnumType.JUNGLE, EnumType.ACACIA, EnumType.DARK_OAK };
            field_176842_g = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.OAK, EnumType.SPRUCE, EnumType.BIRCH, EnumType.JUNGLE, EnumType.ACACIA, EnumType.DARK_OAK };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176842_g[enumType.func_176839_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3) {
            this(s, n, s2, n2, n3, s3, s3);
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176850_h, final String field_176851_i, final String field_176848_j) {
            this.field_176850_h = field_176850_h;
            this.field_176851_i = field_176851_i;
            this.field_176848_j = field_176848_j;
        }
        
        public int func_176839_a() {
            return this.field_176850_h;
        }
        
        @Override
        public String toString() {
            return this.field_176851_i;
        }
        
        public static EnumType func_176837_a(final int n) {
            if (0 < 0 || 0 >= EnumType.field_176842_g.length) {}
            return EnumType.field_176842_g[0];
        }
        
        @Override
        public String getName() {
            return this.field_176851_i;
        }
        
        public String func_176840_c() {
            return this.field_176848_j;
        }
    }
}
