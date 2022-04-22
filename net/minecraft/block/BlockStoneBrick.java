package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockStoneBrick extends Block
{
    public static final PropertyEnum VARIANT_PROP;
    public static final int DEFAULT_META;
    public static final int MOSSY_META;
    public static final int CRACKED_META;
    public static final int CHISELED_META;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000318";
        VARIANT_PROP = PropertyEnum.create("variant", EnumType.class);
        DEFAULT_META = EnumType.DEFAULT.getMetaFromState();
        MOSSY_META = EnumType.MOSSY.getMetaFromState();
        CRACKED_META = EnumType.CRACKED.getMetaFromState();
        CHISELED_META = EnumType.CHISELED.getMetaFromState();
    }
    
    public BlockStoneBrick() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStoneBrick.VARIANT_PROP, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneBrick.VARIANT_PROP)).getMetaFromState();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetaFromState()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, EnumType.getStateFromMeta(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStoneBrick.VARIANT_PROP)).getMetaFromState();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStoneBrick.VARIANT_PROP });
    }
    
    public enum EnumType implements IStringSerializable
    {
        DEFAULT("DEFAULT", 0, "DEFAULT", 0, 0, "stonebrick", "default"), 
        MOSSY("MOSSY", 1, "MOSSY", 1, 1, "mossy_stonebrick", "mossy"), 
        CRACKED("CRACKED", 2, "CRACKED", 2, 2, "cracked_stonebrick", "cracked"), 
        CHISELED("CHISELED", 3, "CHISELED", 3, 3, "chiseled_stonebrick", "chiseled");
        
        private static final EnumType[] TYPES_ARRAY;
        private final int field_176615_f;
        private final String field_176616_g;
        private final String field_176622_h;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002057";
            ENUM$VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.MOSSY, EnumType.CRACKED, EnumType.CHISELED };
            TYPES_ARRAY = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.MOSSY, EnumType.CRACKED, EnumType.CHISELED };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.TYPES_ARRAY[enumType.getMetaFromState()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176615_f, final String field_176616_g, final String field_176622_h) {
            this.field_176615_f = field_176615_f;
            this.field_176616_g = field_176616_g;
            this.field_176622_h = field_176622_h;
        }
        
        public int getMetaFromState() {
            return this.field_176615_f;
        }
        
        @Override
        public String toString() {
            return this.field_176616_g;
        }
        
        public static EnumType getStateFromMeta(final int n) {
            if (0 < 0 || 0 >= EnumType.TYPES_ARRAY.length) {}
            return EnumType.TYPES_ARRAY[0];
        }
        
        @Override
        public String getName() {
            return this.field_176616_g;
        }
        
        public String getVariantName() {
            return this.field_176622_h;
        }
    }
}
