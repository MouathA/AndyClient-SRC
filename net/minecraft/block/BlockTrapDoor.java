package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockTrapDoor extends Block
{
    public static final PropertyDirection field_176284_a;
    public static final PropertyBool field_176283_b;
    public static final PropertyEnum field_176285_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000327";
        field_176284_a = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        field_176283_b = PropertyBool.create("open");
        field_176285_M = PropertyEnum.create("half", DoorHalf.class);
    }
    
    protected BlockTrapDoor(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTrapDoor.field_176284_a, EnumFacing.NORTH).withProperty(BlockTrapDoor.field_176283_b, false).withProperty(BlockTrapDoor.field_176285_M, DoorHalf.BOTTOM));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
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
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return !(boolean)blockAccess.getBlockState(blockPos).getValue(BlockTrapDoor.field_176283_b);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.func_180693_d(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.40625f, 0.0f, 1.0f, 0.59375f, 1.0f);
    }
    
    public void func_180693_d(final IBlockState blockState) {
        if (blockState.getBlock() == this) {
            final boolean b = blockState.getValue(BlockTrapDoor.field_176285_M) == DoorHalf.TOP;
            final Boolean b2 = (Boolean)blockState.getValue(BlockTrapDoor.field_176283_b);
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockTrapDoor.field_176284_a);
            if (b) {
                this.setBlockBounds(0.0f, 0.8125f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.0f);
            }
            if (b2) {
                if (enumFacing == EnumFacing.NORTH) {
                    this.setBlockBounds(0.0f, 0.0f, 0.8125f, 1.0f, 1.0f, 1.0f);
                }
                if (enumFacing == EnumFacing.SOUTH) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.1875f);
                }
                if (enumFacing == EnumFacing.WEST) {
                    this.setBlockBounds(0.8125f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                if (enumFacing == EnumFacing.EAST) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.1875f, 1.0f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        cycleProperty = cycleProperty.cycleProperty(BlockTrapDoor.field_176283_b);
        world.setBlockState(blockPos, cycleProperty, 2);
        world.playAuxSFXAtEntity(entityPlayer, ((boolean)cycleProperty.getValue(BlockTrapDoor.field_176283_b)) ? 1003 : 1006, blockPos, 0);
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            if (!isValidSupportBlock(world.getBlockState(blockToAir.offset(((EnumFacing)blockState.getValue(BlockTrapDoor.field_176284_a)).getOpposite())).getBlock())) {
                world.setBlockToAir(blockToAir);
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
            }
            else {
                final boolean blockPowered = world.isBlockPowered(blockToAir);
                if ((blockPowered || block.canProvidePower()) && (boolean)blockState.getValue(BlockTrapDoor.field_176283_b) != blockPowered) {
                    world.setBlockState(blockToAir, blockState.withProperty(BlockTrapDoor.field_176283_b, blockPowered), 2);
                    world.playAuxSFXAtEntity(null, blockPowered ? 1003 : 1006, blockToAir, 0);
                }
            }
        }
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        IBlockState blockState = this.getDefaultState();
        if (enumFacing.getAxis().isHorizontal()) {
            blockState = blockState.withProperty(BlockTrapDoor.field_176284_a, enumFacing).withProperty(BlockTrapDoor.field_176283_b, false).withProperty(BlockTrapDoor.field_176285_M, (n2 > 0.5f) ? DoorHalf.TOP : DoorHalf.BOTTOM);
        }
        return blockState;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return !enumFacing.getAxis().isVertical() && isValidSupportBlock(world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock());
    }
    
    protected static EnumFacing func_176281_b(final int n) {
        switch (n & 0x3) {
            case 0: {
                return EnumFacing.NORTH;
            }
            case 1: {
                return EnumFacing.SOUTH;
            }
            case 2: {
                return EnumFacing.WEST;
            }
            default: {
                return EnumFacing.EAST;
            }
        }
    }
    
    protected static int func_176282_a(final EnumFacing enumFacing) {
        switch (SwitchEnumFacing.field_177058_a[enumFacing.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            default: {
                return 3;
            }
        }
    }
    
    private static boolean isValidSupportBlock(final Block block) {
        return (block.blockMaterial.isOpaque() && block.isFullCube()) || block == Blocks.glowstone || block instanceof BlockSlab || block instanceof BlockStairs;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTrapDoor.field_176284_a, func_176281_b(n)).withProperty(BlockTrapDoor.field_176283_b, (n & 0x4) != 0x0).withProperty(BlockTrapDoor.field_176285_M, ((n & 0x8) == 0x0) ? DoorHalf.BOTTOM : DoorHalf.TOP);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | func_176282_a((EnumFacing)blockState.getValue(BlockTrapDoor.field_176284_a));
        if (blockState.getValue(BlockTrapDoor.field_176283_b)) {
            n |= 0x4;
        }
        if (blockState.getValue(BlockTrapDoor.field_176285_M) == DoorHalf.TOP) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTrapDoor.field_176284_a, BlockTrapDoor.field_176283_b, BlockTrapDoor.field_176285_M });
    }
    
    public enum DoorHalf implements IStringSerializable
    {
        TOP("TOP", 0, "TOP", 0, "top"), 
        BOTTOM("BOTTOM", 1, "BOTTOM", 1, "bottom");
        
        private final String field_176671_c;
        private static final DoorHalf[] $VALUES;
        private static final String __OBFID;
        private static final DoorHalf[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002051";
            ENUM$VALUES = new DoorHalf[] { DoorHalf.TOP, DoorHalf.BOTTOM };
            $VALUES = new DoorHalf[] { DoorHalf.TOP, DoorHalf.BOTTOM };
        }
        
        private DoorHalf(final String s, final int n, final String s2, final int n2, final String field_176671_c) {
            this.field_176671_c = field_176671_c;
        }
        
        @Override
        public String toString() {
            return this.field_176671_c;
        }
        
        @Override
        public String getName() {
            return this.field_176671_c;
        }
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177058_a;
        private static final String __OBFID;
        private static final String[] llIlIIllIlIIIII;
        private static String[] llIlIIllIlIIIlI;
        
        static {
            lIIllIllIlllllll();
            lIIllIllIllllllI();
            __OBFID = SwitchEnumFacing.llIlIIllIlIIIII[0];
            field_177058_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177058_a[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177058_a[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177058_a[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177058_a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIIllIllIllllllI() {
            (llIlIIllIlIIIII = new String[1])[0] = lIIllIllIlllllIl(SwitchEnumFacing.llIlIIllIlIIIlI[0], SwitchEnumFacing.llIlIIllIlIIIlI[1]);
            SwitchEnumFacing.llIlIIllIlIIIlI = null;
        }
        
        private static void lIIllIllIlllllll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.llIlIIllIlIIIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIllIllIlllllIl(final String s, final String s2) {
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
