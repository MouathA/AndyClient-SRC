package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockPrismarine extends Block
{
    public static final PropertyEnum VARIANTS;
    public static final int ROUGHMETA;
    public static final int BRICKSMETA;
    public static final int DARKMETA;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002077";
        VARIANTS = PropertyEnum.create("variant", EnumType.class);
        ROUGHMETA = EnumType.ROUGH.getMetadata();
        BRICKSMETA = EnumType.BRICKS.getMetadata();
        DARKMETA = EnumType.DARK.getMetadata();
    }
    
    public BlockPrismarine() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPrismarine.VARIANTS, EnumType.ROUGH));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPrismarine.VARIANTS)).getMetadata();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockPrismarine.VARIANTS)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPrismarine.VARIANTS });
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPrismarine.VARIANTS, EnumType.func_176810_a(n));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, BlockPrismarine.ROUGHMETA));
        list.add(new ItemStack(item, 1, BlockPrismarine.BRICKSMETA));
        list.add(new ItemStack(item, 1, BlockPrismarine.DARKMETA));
    }
    
    public enum EnumType implements IStringSerializable
    {
        ROUGH("ROUGH", 0, "ROUGH", 0, 0, "prismarine", "rough"), 
        BRICKS("BRICKS", 1, "BRICKS", 1, 1, "prismarine_bricks", "bricks"), 
        DARK("DARK", 2, "DARK", 2, 2, "dark_prismarine", "dark");
        
        private static final EnumType[] field_176813_d;
        private final int meta;
        private final String field_176811_f;
        private final String field_176812_g;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002076";
            ENUM$VALUES = new EnumType[] { EnumType.ROUGH, EnumType.BRICKS, EnumType.DARK };
            field_176813_d = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.ROUGH, EnumType.BRICKS, EnumType.DARK };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176813_d[enumType.getMetadata()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int meta, final String field_176811_f, final String field_176812_g) {
            this.meta = meta;
            this.field_176811_f = field_176811_f;
            this.field_176812_g = field_176812_g;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        @Override
        public String toString() {
            return this.field_176811_f;
        }
        
        public static EnumType func_176810_a(final int n) {
            if (0 >= EnumType.field_176813_d.length) {}
            return EnumType.field_176813_d[0];
        }
        
        @Override
        public String getName() {
            return this.field_176811_f;
        }
        
        public String func_176809_c() {
            return this.field_176812_g;
        }
    }
}
