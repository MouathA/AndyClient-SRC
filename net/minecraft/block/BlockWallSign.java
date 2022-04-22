package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockWallSign extends BlockSign
{
    public static final PropertyDirection field_176412_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002047";
        field_176412_a = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }
    
    public BlockWallSign() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWallSign.field_176412_a, EnumFacing.NORTH));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing enumFacing = (EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockWallSign.field_176412_a);
        final float n = 0.28125f;
        final float n2 = 0.78125f;
        final float n3 = 0.0f;
        final float n4 = 1.0f;
        final float n5 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        switch (SwitchEnumFacing.field_177331_a[enumFacing.ordinal()]) {
            case 1: {
                this.setBlockBounds(n3, n, 1.0f - n5, n4, n2, 1.0f);
                break;
            }
            case 2: {
                this.setBlockBounds(n3, n, 0.0f, n4, n2, n5);
                break;
            }
            case 3: {
                this.setBlockBounds(1.0f - n5, n, n3, 1.0f, n2, n4);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, n, n3, n5, n2, n4);
                break;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.getBlockState(blockToAir.offset(((EnumFacing)blockState.getValue(BlockWallSign.field_176412_a)).getOpposite())).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockWallSign.field_176412_a, enumFacing);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockWallSign.field_176412_a)).getIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockWallSign.field_176412_a });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177331_a;
        private static final String __OBFID;
        private static final String[] llIIlIllIIIIllI;
        private static String[] llIIlIllIIIIlll;
        
        static {
            lIIlIlIllIIlIIIl();
            lIIlIlIllIIlIIII();
            __OBFID = SwitchEnumFacing.llIIlIllIIIIllI[0];
            field_177331_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177331_a[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177331_a[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177331_a[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177331_a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIIlIlIllIIlIIII() {
            (llIIlIllIIIIllI = new String[1])[0] = lIIlIlIllIIIllll(SwitchEnumFacing.llIIlIllIIIIlll[0], SwitchEnumFacing.llIIlIllIIIIlll[1]);
            SwitchEnumFacing.llIIlIllIIIIlll = null;
        }
        
        private static void lIIlIlIllIIlIIIl() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.llIIlIllIIIIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIlIlIllIIIllll(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
                final Cipher instance = Cipher.getInstance("DES");
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
