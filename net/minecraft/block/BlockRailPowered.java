package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockRailPowered extends BlockRailBase
{
    public static final PropertyEnum field_176568_b;
    public static final PropertyBool field_176569_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000288";
        field_176568_b = PropertyEnum.create("shape", EnumRailDirection.class, new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180133_a(final EnumRailDirection enumRailDirection) {
                return enumRailDirection != EnumRailDirection.NORTH_EAST && enumRailDirection != EnumRailDirection.NORTH_WEST && enumRailDirection != EnumRailDirection.SOUTH_EAST && enumRailDirection != EnumRailDirection.SOUTH_WEST;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180133_a((EnumRailDirection)o);
            }
            
            static {
                __OBFID = "CL_00002080";
            }
        });
        field_176569_M = PropertyBool.create("powered");
    }
    
    protected BlockRailPowered() {
        super(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRailPowered.field_176568_b, EnumRailDirection.NORTH_SOUTH).withProperty(BlockRailPowered.field_176569_M, false));
    }
    
    protected boolean func_176566_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b, final int n) {
        if (n >= 8) {
            return false;
        }
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        EnumRailDirection enumRailDirection = (EnumRailDirection)blockState.getValue(BlockRailPowered.field_176568_b);
        switch (SwitchEnumRailDirection.field_180121_a[enumRailDirection.ordinal()]) {
            case 1: {
                if (b) {
                    ++z;
                    break;
                }
                --z;
                break;
            }
            case 2: {
                if (b) {
                    --x;
                    break;
                }
                ++x;
                break;
            }
            case 3: {
                if (b) {
                    --x;
                }
                else {
                    ++x;
                    ++y;
                }
                enumRailDirection = EnumRailDirection.EAST_WEST;
                break;
            }
            case 4: {
                if (b) {
                    --x;
                    ++y;
                }
                else {
                    ++x;
                }
                enumRailDirection = EnumRailDirection.EAST_WEST;
                break;
            }
            case 5: {
                if (b) {
                    ++z;
                }
                else {
                    --z;
                    ++y;
                }
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
                break;
            }
            case 6: {
                if (b) {
                    ++z;
                    ++y;
                }
                else {
                    --z;
                }
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
                break;
            }
        }
        return this.func_176567_a(world, new BlockPos(x, y, z), b, n, enumRailDirection) || (false && this.func_176567_a(world, new BlockPos(x, y - 1, z), b, n, enumRailDirection));
    }
    
    protected boolean func_176567_a(final World world, final BlockPos blockPos, final boolean b, final int n, final EnumRailDirection enumRailDirection) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != this) {
            return false;
        }
        final EnumRailDirection enumRailDirection2 = (EnumRailDirection)blockState.getValue(BlockRailPowered.field_176568_b);
        return (enumRailDirection != EnumRailDirection.EAST_WEST || (enumRailDirection2 != EnumRailDirection.NORTH_SOUTH && enumRailDirection2 != EnumRailDirection.ASCENDING_NORTH && enumRailDirection2 != EnumRailDirection.ASCENDING_SOUTH)) && (enumRailDirection != EnumRailDirection.NORTH_SOUTH || (enumRailDirection2 != EnumRailDirection.EAST_WEST && enumRailDirection2 != EnumRailDirection.ASCENDING_EAST && enumRailDirection2 != EnumRailDirection.ASCENDING_WEST)) && (boolean)blockState.getValue(BlockRailPowered.field_176569_M) && (world.isBlockPowered(blockPos) || this.func_176566_a(world, blockPos, blockState, b, n + 1));
    }
    
    @Override
    protected void func_176561_b(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final boolean booleanValue = (boolean)blockState.getValue(BlockRailPowered.field_176569_M);
        final boolean b = world.isBlockPowered(blockPos) || this.func_176566_a(world, blockPos, blockState, true, 0) || this.func_176566_a(world, blockPos, blockState, false, 0);
        if (b != booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockRailPowered.field_176569_M, b), 3);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
            if (((EnumRailDirection)blockState.getValue(BlockRailPowered.field_176568_b)).func_177018_c()) {
                world.notifyNeighborsOfStateChange(blockPos.offsetUp(), this);
            }
        }
    }
    
    @Override
    public IProperty func_176560_l() {
        return BlockRailPowered.field_176568_b;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRailPowered.field_176568_b, EnumRailDirection.func_177016_a(n & 0x7)).withProperty(BlockRailPowered.field_176569_M, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumRailDirection)blockState.getValue(BlockRailPowered.field_176568_b)).func_177015_a();
        if (blockState.getValue(BlockRailPowered.field_176569_M)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRailPowered.field_176568_b, BlockRailPowered.field_176569_M });
    }
    
    static final class SwitchEnumRailDirection
    {
        static final int[] field_180121_a;
        private static final String __OBFID;
        private static final String[] llIllIIIIlIlllI;
        private static String[] llIllIIIIllIIIl;
        
        static {
            lIIlllllIlIIIIII();
            lIIlllllIIllllll();
            __OBFID = SwitchEnumRailDirection.llIllIIIIlIlllI[0];
            field_180121_a = new int[EnumRailDirection.values().length];
            try {
                SwitchEnumRailDirection.field_180121_a[EnumRailDirection.NORTH_SOUTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumRailDirection.field_180121_a[EnumRailDirection.EAST_WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_NORTH.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void lIIlllllIIllllll() {
            (llIllIIIIlIlllI = new String[1])[0] = lIIlllllIIlllIll(SwitchEnumRailDirection.llIllIIIIllIIIl[0], SwitchEnumRailDirection.llIllIIIIllIIIl[1]);
            SwitchEnumRailDirection.llIllIIIIllIIIl = null;
        }
        
        private static void lIIlllllIlIIIIII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumRailDirection.llIllIIIIllIIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIlllllIIlllIll(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher instance = Cipher.getInstance("Blowfish");
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
