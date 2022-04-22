package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockNewLog extends BlockLog
{
    public static final PropertyEnum field_176300_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000277";
        field_176300_b = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180194_a(final BlockPlanks.EnumType enumType) {
                return enumType.func_176839_a() >= 4;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180194_a((BlockPlanks.EnumType)o);
            }
            
            static {
                __OBFID = "CL_00002089";
            }
        });
    }
    
    public BlockNewLog() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNewLog.field_176300_b, BlockPlanks.EnumType.ACACIA).withProperty(BlockNewLog.AXIS_PROP, EnumAxis.Y));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.ACACIA.func_176839_a() - 4));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockNewLog.field_176300_b, BlockPlanks.EnumType.func_176837_a((n & 0x3) + 4));
        IBlockState blockState = null;
        switch (n & 0xC) {
            case 0: {
                blockState = withProperty.withProperty(BlockNewLog.AXIS_PROP, EnumAxis.Y);
                break;
            }
            case 4: {
                blockState = withProperty.withProperty(BlockNewLog.AXIS_PROP, EnumAxis.X);
                break;
            }
            case 8: {
                blockState = withProperty.withProperty(BlockNewLog.AXIS_PROP, EnumAxis.Z);
                break;
            }
            default: {
                blockState = withProperty.withProperty(BlockNewLog.AXIS_PROP, EnumAxis.NONE);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockNewLog.field_176300_b)).func_176839_a() - 4;
        switch (SwitchEnumAxis.field_180191_a[((EnumAxis)blockState.getValue(BlockNewLog.AXIS_PROP)).ordinal()]) {
            case 1: {
                n |= 0x4;
                break;
            }
            case 2: {
                n |= 0x8;
                break;
            }
            case 3: {
                n |= 0xC;
                break;
            }
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockNewLog.field_176300_b, BlockNewLog.AXIS_PROP });
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockNewLog.field_176300_b)).func_176839_a() - 4);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockNewLog.field_176300_b)).func_176839_a() - 4;
    }
    
    static final class SwitchEnumAxis
    {
        static final int[] field_180191_a;
        private static final String __OBFID;
        private static final String[] lllllIIllllllll;
        private static String[] lllllIlIIIIIIII;
        
        static {
            lIlllIIIIlIIlIll();
            lIlllIIIIlIIlIlI();
            __OBFID = SwitchEnumAxis.lllllIIllllllll[0];
            field_180191_a = new int[EnumAxis.values().length];
            try {
                SwitchEnumAxis.field_180191_a[EnumAxis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumAxis.field_180191_a[EnumAxis.Z.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumAxis.field_180191_a[EnumAxis.NONE.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
        
        private static void lIlllIIIIlIIlIlI() {
            (lllllIIllllllll = new String[1])[0] = lIlllIIIIlIIlIIl(SwitchEnumAxis.lllllIlIIIIIIII[0], SwitchEnumAxis.lllllIlIIIIIIII[1]);
            SwitchEnumAxis.lllllIlIIIIIIII = null;
        }
        
        private static void lIlllIIIIlIIlIll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumAxis.lllllIlIIIIIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIlllIIIIlIIlIIl(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
                final Cipher instance = Cipher.getInstance("AES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
