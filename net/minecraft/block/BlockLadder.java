package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockLadder extends Block
{
    public static final PropertyDirection field_176382_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000262";
        field_176382_a = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockLadder() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLadder.field_176382_a, EnumFacing.NORTH));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final float n = 0.125f;
            switch (SwitchEnumFacing.field_180190_a[((EnumFacing)blockState.getValue(BlockLadder.field_176382_a)).ordinal()]) {
                case 1: {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
                    break;
                }
                case 3: {
                    this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                default: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetWest()).getBlock().isNormalCube() || world.getBlockState(blockPos.offsetEast()).getBlock().isNormalCube() || world.getBlockState(blockPos.offsetNorth()).getBlock().isNormalCube() || world.getBlockState(blockPos.offsetSouth()).getBlock().isNormalCube();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (enumFacing.getAxis().isHorizontal() && this.func_176381_b(world, blockPos, enumFacing)) {
            return this.getDefaultState().withProperty(BlockLadder.field_176382_a, enumFacing);
        }
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (this.func_176381_b(world, blockPos, enumFacing2)) {
                return this.getDefaultState().withProperty(BlockLadder.field_176382_a, enumFacing2);
            }
        }
        return this.getDefaultState();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!this.func_176381_b(world, blockToAir, (EnumFacing)blockState.getValue(BlockLadder.field_176382_a))) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
    
    protected boolean func_176381_b(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockLadder.field_176382_a, enumFacing);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockLadder.field_176382_a)).getIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockLadder.field_176382_a });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_180190_a;
        private static final String __OBFID;
        private static final String[] lIIIlIlllIIlllll;
        private static String[] lIIIlIlllIlIIIII;
        
        static {
            llIIllIllllllIII();
            llIIllIlllllIlll();
            __OBFID = SwitchEnumFacing.lIIIlIlllIIlllll[0];
            field_180190_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_180190_a[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_180190_a[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_180190_a[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_180190_a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void llIIllIlllllIlll() {
            (lIIIlIlllIIlllll = new String[1])[0] = llIIllIlllllIllI(SwitchEnumFacing.lIIIlIlllIlIIIII[0], SwitchEnumFacing.lIIIlIlllIlIIIII[1]);
            SwitchEnumFacing.lIIIlIlllIlIIIII = null;
        }
        
        private static void llIIllIllllllIII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIIlIlllIlIIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIIllIlllllIllI(final String s, final String s2) {
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
