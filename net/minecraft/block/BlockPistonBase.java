package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockPistonBase extends Block
{
    public static final PropertyDirection FACING;
    public static final PropertyBool EXTENDED;
    private final boolean isSticky;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000366";
        FACING = PropertyDirection.create("facing");
        EXTENDED = PropertyBool.create("extended");
    }
    
    public BlockPistonBase(final boolean isSticky) {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPistonBase.FACING, EnumFacing.NORTH).withProperty(BlockPistonBase.EXTENDED, false));
        this.isSticky = isSticky;
        this.setStepSound(BlockPistonBase.soundTypePiston);
        this.setHardness(0.5f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockPistonBase.FACING, func_180695_a(world, blockPos, entityLivingBase)), 2);
        if (!world.isRemote) {
            this.func_176316_e(world, blockPos, blockState);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            this.func_176316_e(world, blockPos, blockState);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote && world.getTileEntity(blockPos) == null) {
            this.func_176316_e(world, blockPos, blockState);
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockPistonBase.FACING, func_180695_a(world, blockPos, entityLivingBase)).withProperty(BlockPistonBase.EXTENDED, false);
    }
    
    private void func_176316_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonBase.FACING);
        final boolean func_176318_b = this.func_176318_b(world, blockPos, enumFacing);
        if (func_176318_b && !(boolean)blockState.getValue(BlockPistonBase.EXTENDED)) {
            if (new BlockPistonStructureHelper(world, blockPos, enumFacing, true).func_177253_a()) {
                world.addBlockEvent(blockPos, this, 0, enumFacing.getIndex());
            }
        }
        else if (!func_176318_b && (boolean)blockState.getValue(BlockPistonBase.EXTENDED)) {
            world.setBlockState(blockPos, blockState.withProperty(BlockPistonBase.EXTENDED, false), 2);
            world.addBlockEvent(blockPos, this, 1, enumFacing.getIndex());
        }
    }
    
    private boolean func_176318_b(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing2 = values[0];
            if (enumFacing2 != enumFacing && world.func_175709_b(blockPos.offset(enumFacing2), enumFacing2)) {
                return true;
            }
            int length = 0;
            ++length;
        }
        if (world.func_175709_b(blockPos, EnumFacing.NORTH)) {
            return true;
        }
        final BlockPos offsetUp = blockPos.offsetUp();
        final EnumFacing[] values2 = EnumFacing.values();
        int length = values2.length;
        while (0 < 0) {
            final EnumFacing enumFacing3 = values2[0];
            if (enumFacing3 != EnumFacing.DOWN && world.func_175709_b(offsetUp.offset(enumFacing3), enumFacing3)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonBase.FACING);
        if (!world.isRemote) {
            final boolean func_176318_b = this.func_176318_b(world, blockPos, enumFacing);
            if (func_176318_b && n == 1) {
                world.setBlockState(blockPos, blockState.withProperty(BlockPistonBase.EXTENDED, true), 2);
                return false;
            }
            if (!func_176318_b && n == 0) {
                return false;
            }
        }
        if (n == 0) {
            if (!this.func_176319_a(world, blockPos, enumFacing, true)) {
                return false;
            }
            world.setBlockState(blockPos, blockState.withProperty(BlockPistonBase.EXTENDED, true), 2);
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "tile.piston.out", 0.5f, world.rand.nextFloat() * 0.25f + 0.6f);
        }
        else if (n == 1) {
            final TileEntity tileEntity = world.getTileEntity(blockPos.offset(enumFacing));
            if (tileEntity instanceof TileEntityPiston) {
                ((TileEntityPiston)tileEntity).clearPistonTileEntity();
            }
            world.setBlockState(blockPos, Blocks.piston_extension.getDefaultState().withProperty(BlockPistonMoving.field_176426_a, enumFacing).withProperty(BlockPistonMoving.field_176425_b, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
            world.setTileEntity(blockPos, BlockPistonMoving.func_176423_a(this.getStateFromMeta(n2), enumFacing, false, true));
            if (this.isSticky) {
                final BlockPos add = blockPos.add(enumFacing.getFrontOffsetX() * 2, enumFacing.getFrontOffsetY() * 2, enumFacing.getFrontOffsetZ() * 2);
                final Block block = world.getBlockState(add).getBlock();
                if (block == Blocks.piston_extension) {
                    final TileEntity tileEntity2 = world.getTileEntity(add);
                    if (tileEntity2 instanceof TileEntityPiston) {
                        final TileEntityPiston tileEntityPiston = (TileEntityPiston)tileEntity2;
                        if (tileEntityPiston.func_174930_e() == enumFacing && tileEntityPiston.isExtending()) {
                            tileEntityPiston.clearPistonTileEntity();
                        }
                    }
                }
                if (!true && block.getMaterial() != Material.air && func_180696_a(block, world, add, enumFacing.getOpposite(), false) && (block.getMobilityFlag() == 0 || block == Blocks.piston || block == Blocks.sticky_piston)) {
                    this.func_176319_a(world, blockPos, enumFacing, false);
                }
            }
            else {
                world.setBlockToAir(blockPos.offset(enumFacing));
            }
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "tile.piston.in", 0.5f, world.rand.nextFloat() * 0.15f + 0.6f);
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this && (boolean)blockState.getValue(BlockPistonBase.EXTENDED)) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonBase.FACING);
            if (enumFacing != null) {
                switch (SwitchEnumFacing.field_177243_a[enumFacing.ordinal()]) {
                    case 1: {
                        this.setBlockBounds(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 2: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                        break;
                    }
                    case 3: {
                        this.setBlockBounds(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 4: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                        break;
                    }
                    case 5: {
                        this.setBlockBounds(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 6: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                        break;
                    }
                }
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    public static EnumFacing func_176317_b(final int n) {
        final int n2 = n & 0x7;
        return (n2 > 5) ? null : EnumFacing.getFront(n2);
    }
    
    public static EnumFacing func_180695_a(final World world, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (MathHelper.abs((float)entityLivingBase.posX - blockPos.getX()) < 2.0f && MathHelper.abs((float)entityLivingBase.posZ - blockPos.getZ()) < 2.0f) {
            final double n = entityLivingBase.posY + entityLivingBase.getEyeHeight();
            if (n - blockPos.getY() > 2.0) {
                return EnumFacing.UP;
            }
            if (blockPos.getY() - n > 0.0) {
                return EnumFacing.DOWN;
            }
        }
        return entityLivingBase.func_174811_aO().getOpposite();
    }
    
    public static boolean func_180696_a(final Block block, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final boolean b) {
        if (block == Blocks.obsidian) {
            return false;
        }
        if (!world.getWorldBorder().contains(blockPos)) {
            return false;
        }
        if (blockPos.getY() < 0 || (enumFacing == EnumFacing.DOWN && blockPos.getY() == 0)) {
            return false;
        }
        if (blockPos.getY() <= world.getHeight() - 1 && (enumFacing != EnumFacing.UP || blockPos.getY() != world.getHeight() - 1)) {
            if (block != Blocks.piston && block != Blocks.sticky_piston) {
                if (block.getBlockHardness(world, blockPos) == -1.0f) {
                    return false;
                }
                if (block.getMobilityFlag() == 2) {
                    return false;
                }
                if (block.getMobilityFlag() == 1) {
                    return b;
                }
            }
            else if (world.getBlockState(blockPos).getValue(BlockPistonBase.EXTENDED)) {
                return false;
            }
            return !(block instanceof ITileEntityProvider);
        }
        return false;
    }
    
    private boolean func_176319_a(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final boolean b) {
        if (!b) {
            world.setBlockToAir(blockPos.offset(enumFacing));
        }
        final BlockPistonStructureHelper blockPistonStructureHelper = new BlockPistonStructureHelper(world, blockPos, enumFacing, b);
        final List func_177254_c = blockPistonStructureHelper.func_177254_c();
        final List func_177252_d = blockPistonStructureHelper.func_177252_d();
        if (!blockPistonStructureHelper.func_177253_a()) {
            return false;
        }
        int n = func_177254_c.size() + func_177252_d.size();
        final Block[] array = new Block[n];
        final EnumFacing enumFacing2 = b ? enumFacing : enumFacing.getOpposite();
        for (int i = func_177252_d.size() - 1; i >= 0; --i) {
            final BlockPos blockToAir = func_177252_d.get(i);
            final Block block = world.getBlockState(blockToAir).getBlock();
            block.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
            world.setBlockToAir(blockToAir);
            --n;
            array[n] = block;
        }
        for (int j = func_177254_c.size() - 1; j >= 0; --j) {
            final BlockPos blockToAir2 = func_177254_c.get(j);
            final IBlockState blockState = world.getBlockState(blockToAir2);
            final Block block2 = blockState.getBlock();
            block2.getMetaFromState(blockState);
            world.setBlockToAir(blockToAir2);
            final BlockPos offset = blockToAir2.offset(enumFacing2);
            world.setBlockState(offset, Blocks.piston_extension.getDefaultState().withProperty(BlockPistonBase.FACING, enumFacing), 4);
            world.setTileEntity(offset, BlockPistonMoving.func_176423_a(blockState, enumFacing, b, false));
            --n;
            array[n] = block2;
        }
        final BlockPos offset2 = blockPos.offset(enumFacing);
        if (b) {
            final IBlockState withProperty = Blocks.piston_head.getDefaultState().withProperty(BlockPistonExtension.field_176326_a, enumFacing).withProperty(BlockPistonExtension.field_176325_b, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
            world.setBlockState(offset2, Blocks.piston_extension.getDefaultState().withProperty(BlockPistonMoving.field_176426_a, enumFacing).withProperty(BlockPistonMoving.field_176425_b, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 4);
            world.setTileEntity(offset2, BlockPistonMoving.func_176423_a(withProperty, enumFacing, true, false));
        }
        for (int k = func_177252_d.size() - 1; k >= 0; --k) {
            world.notifyNeighborsOfStateChange(func_177252_d.get(k), array[n++]);
        }
        for (int l = func_177254_c.size() - 1; l >= 0; --l) {
            world.notifyNeighborsOfStateChange(func_177254_c.get(l), array[n++]);
        }
        if (b) {
            world.notifyNeighborsOfStateChange(offset2, Blocks.piston_head);
            world.notifyNeighborsOfStateChange(blockPos, this);
        }
        return true;
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty(BlockPistonBase.FACING, EnumFacing.UP);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPistonBase.FACING, func_176317_b(n)).withProperty(BlockPistonBase.EXTENDED, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockPistonBase.FACING)).getIndex();
        if (blockState.getValue(BlockPistonBase.EXTENDED)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPistonBase.FACING, BlockPistonBase.EXTENDED });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177243_a;
        private static final String __OBFID;
        private static final String[] llIlIIlIIIIIIIl;
        private static String[] llIlIIlIIIIIIlI;
        
        static {
            lIIllIlIlIIIlIlI();
            lIIllIlIlIIIlIIl();
            __OBFID = SwitchEnumFacing.llIlIIlIIIIIIIl[0];
            field_177243_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177243_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177243_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177243_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177243_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_177243_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_177243_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void lIIllIlIlIIIlIIl() {
            (llIlIIlIIIIIIIl = new String[1])[0] = lIIllIlIlIIIlIII(SwitchEnumFacing.llIlIIlIIIIIIlI[0], SwitchEnumFacing.llIlIIlIIIIIIlI[1]);
            SwitchEnumFacing.llIlIIlIIIIIIlI = null;
        }
        
        private static void lIIllIlIlIIIlIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.llIlIIlIIIIIIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIllIlIlIIIlIII(final String s, final String s2) {
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
