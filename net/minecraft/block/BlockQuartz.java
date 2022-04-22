package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;
import java.nio.charset.*;

public class BlockQuartz extends Block
{
    public static final PropertyEnum VARIANT_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000292";
        VARIANT_PROP = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockQuartz() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockQuartz.VARIANT_PROP, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (n4 != EnumType.LINES_Y.getMetaFromState()) {
            return (n4 == EnumType.CHISELED.getMetaFromState()) ? this.getDefaultState().withProperty(BlockQuartz.VARIANT_PROP, EnumType.CHISELED) : this.getDefaultState().withProperty(BlockQuartz.VARIANT_PROP, EnumType.DEFAULT);
        }
        switch (SwitchAxis.field_180101_a[enumFacing.getAxis().ordinal()]) {
            case 1: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT_PROP, EnumType.LINES_Z);
            }
            case 2: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT_PROP, EnumType.LINES_X);
            }
            default: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT_PROP, EnumType.LINES_Y);
            }
        }
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        final EnumType enumType = (EnumType)blockState.getValue(BlockQuartz.VARIANT_PROP);
        return (enumType != EnumType.LINES_X && enumType != EnumType.LINES_Z) ? enumType.getMetaFromState() : EnumType.LINES_Y.getMetaFromState();
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        final EnumType enumType = (EnumType)blockState.getValue(BlockQuartz.VARIANT_PROP);
        return (enumType != EnumType.LINES_X && enumType != EnumType.LINES_Z) ? super.createStackedBlock(blockState) : new ItemStack(Item.getItemFromBlock(this), 1, EnumType.LINES_Y.getMetaFromState());
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, EnumType.DEFAULT.getMetaFromState()));
        list.add(new ItemStack(item, 1, EnumType.CHISELED.getMetaFromState()));
        list.add(new ItemStack(item, 1, EnumType.LINES_Y.getMetaFromState()));
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.quartzColor;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockQuartz.VARIANT_PROP, EnumType.func_176794_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockQuartz.VARIANT_PROP)).getMetaFromState();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockQuartz.VARIANT_PROP });
    }
    
    public enum EnumType implements IStringSerializable
    {
        DEFAULT("DEFAULT", 0, "DEFAULT", 0, 0, "default", "default"), 
        CHISELED("CHISELED", 1, "CHISELED", 1, 1, "chiseled", "chiseled"), 
        LINES_Y("LINES_Y", 2, "LINES_Y", 2, 2, "lines_y", "lines"), 
        LINES_X("LINES_X", 3, "LINES_X", 3, 3, "lines_x", "lines"), 
        LINES_Z("LINES_Z", 4, "LINES_Z", 4, 4, "lines_z", "lines");
        
        private static final EnumType[] TYPES_ARRAY;
        private final int field_176798_g;
        private final String field_176805_h;
        private final String field_176806_i;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002074";
            ENUM$VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.LINES_Y, EnumType.LINES_X, EnumType.LINES_Z };
            TYPES_ARRAY = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.LINES_Y, EnumType.LINES_X, EnumType.LINES_Z };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.TYPES_ARRAY[enumType.getMetaFromState()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176798_g, final String field_176805_h, final String field_176806_i) {
            this.field_176798_g = field_176798_g;
            this.field_176805_h = field_176805_h;
            this.field_176806_i = field_176806_i;
        }
        
        public int getMetaFromState() {
            return this.field_176798_g;
        }
        
        @Override
        public String toString() {
            return this.field_176806_i;
        }
        
        public static EnumType func_176794_a(final int n) {
            if (0 < 0 || 0 >= EnumType.TYPES_ARRAY.length) {}
            return EnumType.TYPES_ARRAY[0];
        }
        
        @Override
        public String getName() {
            return this.field_176805_h;
        }
    }
    
    static final class SwitchAxis
    {
        static final int[] field_180101_a;
        private static final String __OBFID;
        private static final String[] lIIlIllIIIlIlllI;
        private static String[] lIIlIllIIIlIllll;
        
        static {
            llIlllIlIIlllIII();
            llIlllIlIIllIlll();
            __OBFID = SwitchAxis.lIIlIllIIIlIlllI[0];
            field_180101_a = new int[EnumFacing.Axis.values().length];
            try {
                SwitchAxis.field_180101_a[EnumFacing.Axis.Z.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchAxis.field_180101_a[EnumFacing.Axis.X.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchAxis.field_180101_a[EnumFacing.Axis.Y.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
        
        private static void llIlllIlIIllIlll() {
            (lIIlIllIIIlIlllI = new String[1])[0] = llIlllIlIIllIllI(SwitchAxis.lIIlIllIIIlIllll[0], SwitchAxis.lIIlIllIIIlIllll[1]);
            SwitchAxis.lIIlIllIIIlIllll = null;
        }
        
        private static void llIlllIlIIlllIII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchAxis.lIIlIllIIIlIllll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIlllIlIIllIllI(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
}
