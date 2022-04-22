package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockRedSandstone extends Block
{
    public static final PropertyEnum TYPE;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002072";
        TYPE = PropertyEnum.create("type", EnumType.class);
    }
    
    public BlockRedSandstone() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedSandstone.TYPE, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockRedSandstone.TYPE)).getMetaFromState();
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
        return this.getDefaultState().withProperty(BlockRedSandstone.TYPE, EnumType.func_176825_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockRedSandstone.TYPE)).getMetaFromState();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedSandstone.TYPE });
    }
    
    public enum EnumType implements IStringSerializable
    {
        DEFAULT("DEFAULT", 0, "DEFAULT", 0, 0, "red_sandstone", "default"), 
        CHISELED("CHISELED", 1, "CHISELED", 1, 1, "chiseled_red_sandstone", "chiseled"), 
        SMOOTH("SMOOTH", 2, "SMOOTH", 2, 2, "smooth_red_sandstone", "smooth");
        
        private static final EnumType[] field_176831_d;
        private final int field_176832_e;
        private final String field_176829_f;
        private final String field_176830_g;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002071";
            ENUM$VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.SMOOTH };
            field_176831_d = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.SMOOTH };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176831_d[enumType.getMetaFromState()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176832_e, final String field_176829_f, final String field_176830_g) {
            this.field_176832_e = field_176832_e;
            this.field_176829_f = field_176829_f;
            this.field_176830_g = field_176830_g;
        }
        
        public int getMetaFromState() {
            return this.field_176832_e;
        }
        
        @Override
        public String toString() {
            return this.field_176829_f;
        }
        
        public static EnumType func_176825_a(final int n) {
            if (0 >= EnumType.field_176831_d.length) {}
            return EnumType.field_176831_d[0];
        }
        
        @Override
        public String getName() {
            return this.field_176829_f;
        }
        
        public String func_176828_c() {
            return this.field_176830_g;
        }
    }
}
