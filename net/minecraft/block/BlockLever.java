package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockLever extends Block
{
    public static final PropertyEnum FACING;
    public static final PropertyBool POWERED;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000264";
        FACING = PropertyEnum.create("facing", EnumOrientation.class);
        POWERED = PropertyBool.create("powered");
    }
    
    protected BlockLever() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLever.FACING, EnumOrientation.NORTH).withProperty(BlockLever.POWERED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
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
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.UP && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())) || this.func_176358_d(world, blockPos.offset(enumFacing.getOpposite()));
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return this.func_176358_d(world, blockPos.offsetWest()) || this.func_176358_d(world, blockPos.offsetEast()) || this.func_176358_d(world, blockPos.offsetNorth()) || this.func_176358_d(world, blockPos.offsetSouth()) || World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) || this.func_176358_d(world, blockPos.offsetUp());
    }
    
    protected boolean func_176358_d(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().isNormalCube();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockLever.POWERED, false);
        if (this.func_176358_d(world, blockPos.offset(enumFacing.getOpposite()))) {
            return withProperty.withProperty(BlockLever.FACING, EnumOrientation.func_176856_a(enumFacing, entityLivingBase.func_174811_aO()));
        }
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (enumFacing2 != enumFacing && this.func_176358_d(world, blockPos.offset(enumFacing2.getOpposite()))) {
                return withProperty.withProperty(BlockLever.FACING, EnumOrientation.func_176856_a(enumFacing2, entityLivingBase.func_174811_aO()));
            }
        }
        if (World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())) {
            return withProperty.withProperty(BlockLever.FACING, EnumOrientation.func_176856_a(EnumFacing.UP, entityLivingBase.func_174811_aO()));
        }
        return withProperty;
    }
    
    public static int func_176357_a(final EnumFacing enumFacing) {
        switch (SwitchEnumFacing.FACING_LOOKUP[enumFacing.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 5;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 2;
            }
            case 6: {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (this.func_176356_e(world, blockToAir) && !this.func_176358_d(world, blockToAir.offset(((EnumOrientation)blockState.getValue(BlockLever.FACING)).func_176852_c().getOpposite()))) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    private boolean func_176356_e(final World world, final BlockPos blockToAir) {
        if (this.canPlaceBlockAt(world, blockToAir)) {
            return true;
        }
        this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
        world.setBlockToAir(blockToAir);
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.1875f;
        switch (SwitchEnumFacing.ORIENTATION_LOOKUP[((EnumOrientation)blockAccess.getBlockState(blockPos).getValue(BlockLever.FACING)).ordinal()]) {
            case 1: {
                this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
                break;
            }
            case 2: {
                this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
                break;
            }
            case 3: {
                this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
                break;
            }
            case 5:
            case 6: {
                final float n2 = 0.25f;
                this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.6f, 0.5f + n2);
                break;
            }
            case 7:
            case 8: {
                final float n3 = 0.25f;
                this.setBlockBounds(0.5f - n3, 0.4f, 0.5f - n3, 0.5f + n3, 1.0f, 0.5f + n3);
                break;
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        cycleProperty = cycleProperty.cycleProperty(BlockLever.POWERED);
        world.setBlockState(blockPos, cycleProperty, 3);
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, ((boolean)cycleProperty.getValue(BlockLever.POWERED)) ? 0.6f : 0.5f);
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(((EnumOrientation)cycleProperty.getValue(BlockLever.FACING)).func_176852_c().getOpposite()), this);
        return true;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockLever.POWERED)) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offset(((EnumOrientation)blockState.getValue(BlockLever.FACING)).func_176852_c().getOpposite()), this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockLever.POWERED) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockLever.POWERED) ? ((((EnumOrientation)blockState.getValue(BlockLever.FACING)).func_176852_c() == enumFacing) ? 15 : 0) : 0;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockLever.FACING, EnumOrientation.func_176853_a(n & 0x7)).withProperty(BlockLever.POWERED, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumOrientation)blockState.getValue(BlockLever.FACING)).func_176855_a();
        if (blockState.getValue(BlockLever.POWERED)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockLever.FACING, BlockLever.POWERED });
    }
    
    public enum EnumOrientation implements IStringSerializable
    {
        DOWN_X("DOWN_X", 0, "DOWN_X", 0, 0, "down_x", EnumFacing.DOWN), 
        EAST("EAST", 1, "EAST", 1, 1, "east", EnumFacing.EAST), 
        WEST("WEST", 2, "WEST", 2, 2, "west", EnumFacing.WEST), 
        SOUTH("SOUTH", 3, "SOUTH", 3, 3, "south", EnumFacing.SOUTH), 
        NORTH("NORTH", 4, "NORTH", 4, 4, "north", EnumFacing.NORTH), 
        UP_Z("UP_Z", 5, "UP_Z", 5, 5, "up_z", EnumFacing.UP), 
        UP_X("UP_X", 6, "UP_X", 6, 6, "up_x", EnumFacing.UP), 
        DOWN_Z("DOWN_Z", 7, "DOWN_Z", 7, 7, "down_z", EnumFacing.DOWN);
        
        private static final EnumOrientation[] field_176869_i;
        private final int field_176866_j;
        private final String field_176867_k;
        private final EnumFacing field_176864_l;
        private static final EnumOrientation[] $VALUES;
        private static final String __OBFID;
        private static final EnumOrientation[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002102";
            ENUM$VALUES = new EnumOrientation[] { EnumOrientation.DOWN_X, EnumOrientation.EAST, EnumOrientation.WEST, EnumOrientation.SOUTH, EnumOrientation.NORTH, EnumOrientation.UP_Z, EnumOrientation.UP_X, EnumOrientation.DOWN_Z };
            field_176869_i = new EnumOrientation[values().length];
            $VALUES = new EnumOrientation[] { EnumOrientation.DOWN_X, EnumOrientation.EAST, EnumOrientation.WEST, EnumOrientation.SOUTH, EnumOrientation.NORTH, EnumOrientation.UP_Z, EnumOrientation.UP_X, EnumOrientation.DOWN_Z };
            final EnumOrientation[] values = values();
            while (0 < values.length) {
                final EnumOrientation enumOrientation = values[0];
                EnumOrientation.field_176869_i[enumOrientation.func_176855_a()] = enumOrientation;
                int n = 0;
                ++n;
            }
        }
        
        private EnumOrientation(final String s, final int n, final String s2, final int n2, final int field_176866_j, final String field_176867_k, final EnumFacing field_176864_l) {
            this.field_176866_j = field_176866_j;
            this.field_176867_k = field_176867_k;
            this.field_176864_l = field_176864_l;
        }
        
        public int func_176855_a() {
            return this.field_176866_j;
        }
        
        public EnumFacing func_176852_c() {
            return this.field_176864_l;
        }
        
        @Override
        public String toString() {
            return this.field_176867_k;
        }
        
        public static EnumOrientation func_176853_a(final int n) {
            if (0 >= EnumOrientation.field_176869_i.length) {}
            return EnumOrientation.field_176869_i[0];
        }
        
        public static EnumOrientation func_176856_a(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
            switch (SwitchEnumFacing.FACING_LOOKUP[enumFacing.ordinal()]) {
                case 1: {
                    switch (SwitchEnumFacing.AXIS_LOOKUP[enumFacing2.getAxis().ordinal()]) {
                        case 1: {
                            return EnumOrientation.DOWN_X;
                        }
                        case 2: {
                            return EnumOrientation.DOWN_Z;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid entityFacing " + enumFacing2 + " for facing " + enumFacing);
                        }
                    }
                    break;
                }
                case 2: {
                    switch (SwitchEnumFacing.AXIS_LOOKUP[enumFacing2.getAxis().ordinal()]) {
                        case 1: {
                            return EnumOrientation.UP_X;
                        }
                        case 2: {
                            return EnumOrientation.UP_Z;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid entityFacing " + enumFacing2 + " for facing " + enumFacing);
                        }
                    }
                    break;
                }
                case 3: {
                    return EnumOrientation.NORTH;
                }
                case 4: {
                    return EnumOrientation.SOUTH;
                }
                case 5: {
                    return EnumOrientation.WEST;
                }
                case 6: {
                    return EnumOrientation.EAST;
                }
                default: {
                    throw new IllegalArgumentException("Invalid facing: " + enumFacing);
                }
            }
        }
        
        @Override
        public String getName() {
            return this.field_176867_k;
        }
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] FACING_LOOKUP;
        static final int[] ORIENTATION_LOOKUP;
        static final int[] AXIS_LOOKUP;
        private static final String __OBFID;
        private static final String[] lIllIIIIIlIlIIII;
        private static String[] lIllIIIIIlIlIIIl;
        
        static {
            lIIIIIIllIlIllIll();
            lIIIIIIllIlIllIlI();
            __OBFID = SwitchEnumFacing.lIllIIIIIlIlIIII[0];
            AXIS_LOOKUP = new int[EnumFacing.Axis.values().length];
            try {
                SwitchEnumFacing.AXIS_LOOKUP[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.AXIS_LOOKUP[EnumFacing.Axis.Z.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            ORIENTATION_LOOKUP = new int[EnumOrientation.values().length];
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.UP_Z.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.UP_X.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.DOWN_X.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.DOWN_Z.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            FACING_LOOKUP = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                SwitchEnumFacing.FACING_LOOKUP[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                SwitchEnumFacing.FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                SwitchEnumFacing.FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                SwitchEnumFacing.FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                SwitchEnumFacing.FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
        }
        
        private static void lIIIIIIllIlIllIlI() {
            (lIllIIIIIlIlIIII = new String[1])[0] = lIIIIIIllIlIllIIl(SwitchEnumFacing.lIllIIIIIlIlIIIl[0], SwitchEnumFacing.lIllIIIIIlIlIIIl[1]);
            SwitchEnumFacing.lIllIIIIIlIlIIIl = null;
        }
        
        private static void lIIIIIIllIlIllIll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIllIIIIIlIlIIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIIIIIllIlIllIIl(final String s, final String s2) {
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
