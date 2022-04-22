package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public abstract class BlockButton extends Block
{
    public static final PropertyDirection FACING_PROP;
    public static final PropertyBool POWERED_PROP;
    private final boolean wooden;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000209";
        FACING_PROP = PropertyDirection.create("facing");
        POWERED_PROP = PropertyBool.create("powered");
    }
    
    protected BlockButton(final boolean wooden) {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockButton.FACING_PROP, EnumFacing.NORTH).withProperty(BlockButton.POWERED_PROP, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.wooden = wooden;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public int tickRate(final World world) {
        return this.wooden ? 30 : 20;
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
        return world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            if (world.getBlockState(blockPos.offset(values[0])).getBlock().isNormalCube()) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube() ? this.getDefaultState().withProperty(BlockButton.FACING_PROP, enumFacing).withProperty(BlockButton.POWERED_PROP, false) : this.getDefaultState().withProperty(BlockButton.FACING_PROP, EnumFacing.DOWN).withProperty(BlockButton.POWERED_PROP, false);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (this.func_176583_e(world, blockToAir, blockState) && !world.getBlockState(blockToAir.offset(((EnumFacing)blockState.getValue(BlockButton.FACING_PROP)).getOpposite())).getBlock().isNormalCube()) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    private boolean func_176583_e(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (!this.canPlaceBlockAt(world, blockToAir)) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
            return false;
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.func_180681_d(blockAccess.getBlockState(blockPos));
    }
    
    private void func_180681_d(final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockButton.FACING_PROP);
        final float n = (blockState.getValue(BlockButton.POWERED_PROP) ? 1 : 2) / 16.0f;
        switch (SwitchEnumFacing.field_180420_a[enumFacing.ordinal()]) {
            case 1: {
                this.setBlockBounds(0.0f, 0.375f, 0.3125f, n, 0.625f, 0.6875f);
                break;
            }
            case 2: {
                this.setBlockBounds(1.0f - n, 0.375f, 0.3125f, 1.0f, 0.625f, 0.6875f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.3125f, 0.375f, 0.0f, 0.6875f, 0.625f, n);
                break;
            }
            case 4: {
                this.setBlockBounds(0.3125f, 0.375f, 1.0f - n, 0.6875f, 0.625f, 1.0f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.3125f, 0.0f, 0.375f, 0.6875f, 0.0f + n, 0.625f);
                break;
            }
            case 6: {
                this.setBlockBounds(0.3125f, 1.0f - n, 0.375f, 0.6875f, 1.0f, 0.625f);
                break;
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (blockState.getValue(BlockButton.POWERED_PROP)) {
            return true;
        }
        world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED_PROP, true), 3);
        world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.6f);
        this.func_176582_b(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING_PROP));
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
        return true;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockButton.POWERED_PROP)) {
            this.func_176582_b(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING_PROP));
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockButton.POWERED_PROP) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockButton.POWERED_PROP) ? ((blockState.getValue(BlockButton.FACING_PROP) == enumFacing) ? 15 : 0) : 0;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && (boolean)blockState.getValue(BlockButton.POWERED_PROP)) {
            if (this.wooden) {
                this.func_180680_f(world, blockPos, blockState);
            }
            else {
                world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED_PROP, false));
                this.func_176582_b(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING_PROP));
                world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.5f);
                world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.1875f;
        final float n2 = 0.125f;
        final float n3 = 0.125f;
        this.setBlockBounds(0.5f - n, 0.5f - n2, 0.5f - n3, 0.5f + n, 0.5f + n2, 0.5f + n3);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && this.wooden && !(boolean)blockState.getValue(BlockButton.POWERED_PROP)) {
            this.func_180680_f(world, blockPos, blockState);
        }
    }
    
    private void func_180680_f(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_180681_d(blockState);
        final boolean b = !world.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ)).isEmpty();
        final boolean booleanValue = (boolean)blockState.getValue(BlockButton.POWERED_PROP);
        if (b && !booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED_PROP, true));
            this.func_176582_b(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING_PROP));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!b && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED_PROP, false));
            this.func_176582_b(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING_PROP));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (b) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    private void func_176582_b(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = null;
        switch (n & 0x7) {
            case 0: {
                enumFacing = EnumFacing.DOWN;
                break;
            }
            case 1: {
                enumFacing = EnumFacing.EAST;
                break;
            }
            case 2: {
                enumFacing = EnumFacing.WEST;
                break;
            }
            case 3: {
                enumFacing = EnumFacing.SOUTH;
                break;
            }
            case 4: {
                enumFacing = EnumFacing.NORTH;
                break;
            }
            default: {
                enumFacing = EnumFacing.UP;
                break;
            }
        }
        return this.getDefaultState().withProperty(BlockButton.FACING_PROP, enumFacing).withProperty(BlockButton.POWERED_PROP, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        switch (SwitchEnumFacing.field_180420_a[((EnumFacing)blockState.getValue(BlockButton.FACING_PROP)).ordinal()]) {
            default: {}
            case 4:
            case 6: {
                if (blockState.getValue(BlockButton.POWERED_PROP)) {}
                return 0;
            }
            case 1: {}
            case 2: {}
            case 3: {}
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockButton.FACING_PROP, BlockButton.POWERED_PROP });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_180420_a;
        private static final String __OBFID;
        private static final String[] lIIlIlIIIlllllll;
        private static String[] lIIlIlIIlIIIIIII;
        
        static {
            llIllIlIlIlllIII();
            llIllIlIlIllIlll();
            __OBFID = SwitchEnumFacing.lIIlIlIIIlllllll[0];
            field_180420_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_180420_a[EnumFacing.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_180420_a[EnumFacing.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_180420_a[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_180420_a[EnumFacing.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_180420_a[EnumFacing.UP.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_180420_a[EnumFacing.DOWN.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void llIllIlIlIllIlll() {
            (lIIlIlIIIlllllll = new String[1])[0] = llIllIlIlIllIllI(SwitchEnumFacing.lIIlIlIIlIIIIIII[0], SwitchEnumFacing.lIIlIlIIlIIIIIII[1]);
            SwitchEnumFacing.lIIlIlIIlIIIIIII = null;
        }
        
        private static void llIllIlIlIlllIII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIlIlIIlIIIIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIllIlIlIllIllI(final String s, final String s2) {
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
