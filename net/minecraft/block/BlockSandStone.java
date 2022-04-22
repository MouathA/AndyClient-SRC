package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockSandStone extends Block
{
    public static final PropertyEnum field_176297_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000304";
        field_176297_a = PropertyEnum.create("type", EnumType.class);
    }
    
    public BlockSandStone() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSandStone.field_176297_a, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSandStone.field_176297_a)).func_176675_a();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176675_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSandStone.field_176297_a, EnumType.func_176673_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSandStone.field_176297_a)).func_176675_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSandStone.field_176297_a });
    }
    
    public enum EnumType implements IStringSerializable
    {
        DEFAULT("DEFAULT", 0, "DEFAULT", 0, 0, "sandstone", "default"), 
        CHISELED("CHISELED", 1, "CHISELED", 1, 1, "chiseled_sandstone", "chiseled"), 
        SMOOTH("SMOOTH", 2, "SMOOTH", 2, 2, "smooth_sandstone", "smooth");
        
        private static final EnumType[] field_176679_d;
        private final int field_176680_e;
        private final String field_176677_f;
        private final String field_176678_g;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002068";
            ENUM$VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.SMOOTH };
            field_176679_d = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.SMOOTH };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176679_d[enumType.func_176675_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176680_e, final String field_176677_f, final String field_176678_g) {
            this.field_176680_e = field_176680_e;
            this.field_176677_f = field_176677_f;
            this.field_176678_g = field_176678_g;
        }
        
        public int func_176675_a() {
            return this.field_176680_e;
        }
        
        @Override
        public String toString() {
            return this.field_176677_f;
        }
        
        public static EnumType func_176673_a(final int n) {
            if (0 >= EnumType.field_176679_d.length) {}
            return EnumType.field_176679_d[0];
        }
        
        @Override
        public String getName() {
            return this.field_176677_f;
        }
        
        public String func_176676_c() {
            return this.field_176678_g;
        }
    }
}
