package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;
import java.nio.charset.*;

public class BlockPistonExtension extends Block
{
    public static final PropertyDirection field_176326_a;
    public static final PropertyEnum field_176325_b;
    public static final PropertyBool field_176327_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000367";
        field_176326_a = PropertyDirection.create("facing");
        field_176325_b = PropertyEnum.create("type", EnumPistonType.class);
        field_176327_M = PropertyBool.create("short");
    }
    
    public BlockPistonExtension() {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPistonExtension.field_176326_a, EnumFacing.NORTH).withProperty(BlockPistonExtension.field_176325_b, EnumPistonType.DEFAULT).withProperty(BlockPistonExtension.field_176327_M, false));
        this.setStepSound(BlockPistonExtension.soundTypePiston);
        this.setHardness(0.5f);
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonExtension.field_176326_a);
            if (enumFacing != null) {
                final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
                final Block block = world.getBlockState(offset).getBlock();
                if (block == Blocks.piston || block == Blocks.sticky_piston) {
                    world.setBlockToAir(offset);
                }
            }
        }
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
    }
    
    @Override
    public void breakBlock(final World world, BlockPos offset, final IBlockState blockState) {
        super.breakBlock(world, offset, blockState);
        offset = offset.offset(((EnumFacing)blockState.getValue(BlockPistonExtension.field_176326_a)).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if ((blockState2.getBlock() == Blocks.piston || blockState2.getBlock() == Blocks.sticky_piston) && (boolean)blockState2.getValue(BlockPistonBase.EXTENDED)) {
            blockState2.getBlock().dropBlockAsItem(world, offset, blockState2, 0);
            world.setBlockToAir(offset);
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
        return false;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return false;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.func_176324_d(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.func_176323_e(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void func_176323_e(final IBlockState blockState) {
        switch (SwitchEnumFacing.field_177247_a[((EnumFacing)blockState.getValue(BlockPistonExtension.field_176326_a)).ordinal()]) {
            case 1: {
                this.setBlockBounds(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                break;
            }
            case 6: {
                this.setBlockBounds(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.func_176324_d(blockAccess.getBlockState(blockPos));
    }
    
    public void func_176324_d(final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonExtension.field_176326_a);
        if (enumFacing != null) {
            switch (SwitchEnumFacing.field_177247_a[enumFacing.ordinal()]) {
                case 1: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                    break;
                }
                case 6: {
                    this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        final BlockPos offset = blockToAir.offset(((EnumFacing)blockState.getValue(BlockPistonExtension.field_176326_a)).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if (blockState2.getBlock() != Blocks.piston && blockState2.getBlock() != Blocks.sticky_piston) {
            world.setBlockToAir(blockToAir);
        }
        else {
            blockState2.getBlock().onNeighborBlockChange(world, offset, blockState2, block);
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    public static EnumFacing func_176322_b(final int n) {
        final int n2 = n & 0x7;
        return (n2 > 5) ? null : EnumFacing.getFront(n2);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return (world.getBlockState(blockPos).getValue(BlockPistonExtension.field_176325_b) == EnumPistonType.STICKY) ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPistonExtension.field_176326_a, func_176322_b(n)).withProperty(BlockPistonExtension.field_176325_b, ((n & 0x8) > 0) ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockPistonExtension.field_176326_a)).getIndex();
        if (blockState.getValue(BlockPistonExtension.field_176325_b) == EnumPistonType.STICKY) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPistonExtension.field_176326_a, BlockPistonExtension.field_176325_b, BlockPistonExtension.field_176327_M });
    }
    
    public enum EnumPistonType implements IStringSerializable
    {
        DEFAULT("DEFAULT", 0, "DEFAULT", 0, "normal"), 
        STICKY("STICKY", 1, "STICKY", 1, "sticky");
        
        private final String field_176714_c;
        private static final EnumPistonType[] $VALUES;
        private static final String __OBFID;
        private static final EnumPistonType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002035";
            ENUM$VALUES = new EnumPistonType[] { EnumPistonType.DEFAULT, EnumPistonType.STICKY };
            $VALUES = new EnumPistonType[] { EnumPistonType.DEFAULT, EnumPistonType.STICKY };
        }
        
        private EnumPistonType(final String s, final int n, final String s2, final int n2, final String field_176714_c) {
            this.field_176714_c = field_176714_c;
        }
        
        @Override
        public String toString() {
            return this.field_176714_c;
        }
        
        @Override
        public String getName() {
            return this.field_176714_c;
        }
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177247_a;
        private static final String __OBFID;
        private static final String[] lIIIIllIlIIlIIIl;
        private static String[] lIIIIllIlIIlIIll;
        
        static {
            llIIIllIlIllIllI();
            llIIIllIlIllIlIl();
            __OBFID = SwitchEnumFacing.lIIIIllIlIIlIIIl[0];
            field_177247_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177247_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177247_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177247_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177247_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_177247_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_177247_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void llIIIllIlIllIlIl() {
            (lIIIIllIlIIlIIIl = new String[1])[0] = llIIIllIlIllIlII(SwitchEnumFacing.lIIIIllIlIIlIIll[0], SwitchEnumFacing.lIIIIllIlIIlIIll[1]);
            SwitchEnumFacing.lIIIIllIlIIlIIll = null;
        }
        
        private static void llIIIllIlIllIllI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIIIllIlIIlIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIIIllIlIllIlII(String s, final String s2) {
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
