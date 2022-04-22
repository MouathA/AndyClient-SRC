package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockSand extends BlockFalling
{
    public static final PropertyEnum VARIANT_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000303";
        VARIANT_PROP = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockSand() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSand.VARIANT_PROP, EnumType.SAND));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSand.VARIANT_PROP)).func_176688_a();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176688_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSand.VARIANT_PROP)).func_176687_c();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSand.VARIANT_PROP, EnumType.func_176686_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSand.VARIANT_PROP)).func_176688_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSand.VARIANT_PROP });
    }
    
    public enum EnumType implements IStringSerializable
    {
        SAND("SAND", 0, "SAND", 0, 0, "sand", "default", MapColor.sandColor), 
        RED_SAND("RED_SAND", 1, "RED_SAND", 1, 1, "red_sand", "red", MapColor.dirtColor);
        
        private static final EnumType[] field_176695_c;
        private final int field_176692_d;
        private final String field_176693_e;
        private final MapColor field_176690_f;
        private final String field_176691_g;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002069";
            ENUM$VALUES = new EnumType[] { EnumType.SAND, EnumType.RED_SAND };
            field_176695_c = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.SAND, EnumType.RED_SAND };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176695_c[enumType.func_176688_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176692_d, final String field_176693_e, final String field_176691_g, final MapColor field_176690_f) {
            this.field_176692_d = field_176692_d;
            this.field_176693_e = field_176693_e;
            this.field_176690_f = field_176690_f;
            this.field_176691_g = field_176691_g;
        }
        
        public int func_176688_a() {
            return this.field_176692_d;
        }
        
        @Override
        public String toString() {
            return this.field_176693_e;
        }
        
        public MapColor func_176687_c() {
            return this.field_176690_f;
        }
        
        public static EnumType func_176686_a(final int n) {
            if (0 >= EnumType.field_176695_c.length) {}
            return EnumType.field_176695_c[0];
        }
        
        @Override
        public String getName() {
            return this.field_176693_e;
        }
        
        public String func_176685_d() {
            return this.field_176691_g;
        }
    }
}
