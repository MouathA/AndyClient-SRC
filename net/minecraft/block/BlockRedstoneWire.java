package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockRedstoneWire extends Block
{
    public static final PropertyEnum NORTH;
    public static final PropertyEnum EAST;
    public static final PropertyEnum SOUTH;
    public static final PropertyEnum WEST;
    public static final PropertyInteger POWER;
    private boolean canProvidePower;
    private final Set field_150179_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000295";
        NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
        EAST = PropertyEnum.create("east", EnumAttachPosition.class);
        SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
        WEST = PropertyEnum.create("west", EnumAttachPosition.class);
        POWER = PropertyInteger.create("power", 0, 15);
    }
    
    public BlockRedstoneWire() {
        super(Material.circuits);
        this.canProvidePower = true;
        this.field_150179_b = Sets.newHashSet();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedstoneWire.NORTH, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.EAST, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.SOUTH, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.WEST, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.POWER, 0));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        blockState = blockState.withProperty(BlockRedstoneWire.WEST, this.getAttachPosition(blockAccess, blockPos, EnumFacing.WEST));
        blockState = blockState.withProperty(BlockRedstoneWire.EAST, this.getAttachPosition(blockAccess, blockPos, EnumFacing.EAST));
        blockState = blockState.withProperty(BlockRedstoneWire.NORTH, this.getAttachPosition(blockAccess, blockPos, EnumFacing.NORTH));
        blockState = blockState.withProperty(BlockRedstoneWire.SOUTH, this.getAttachPosition(blockAccess, blockPos, EnumFacing.SOUTH));
        return blockState;
    }
    
    private EnumAttachPosition getAttachPosition(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing);
        final Block block = blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock();
        if (!func_176343_a(blockAccess.getBlockState(offset), enumFacing) && (block.isSolidFullCube() || !func_176346_d(blockAccess.getBlockState(offset.offsetDown())))) {
            return (!blockAccess.getBlockState(blockPos.offsetUp()).getBlock().isSolidFullCube() && block.isSolidFullCube() && func_176346_d(blockAccess.getBlockState(offset.offsetUp()))) ? EnumAttachPosition.UP : EnumAttachPosition.NONE;
        }
        return EnumAttachPosition.SIDE;
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
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        return (blockState.getBlock() != this) ? super.colorMultiplier(blockAccess, blockPos, n) : this.func_176337_b((int)blockState.getValue(BlockRedstoneWire.POWER));
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) || world.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.glowstone;
    }
    
    private IBlockState updateSurroundingRedstone(final World world, final BlockPos blockPos, IBlockState func_176345_a) {
        func_176345_a = this.func_176345_a(world, blockPos, blockPos, func_176345_a);
        final ArrayList arrayList = Lists.newArrayList(this.field_150179_b);
        this.field_150179_b.clear();
        final Iterator<BlockPos> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            world.notifyNeighborsOfStateChange(iterator.next(), this);
        }
        return func_176345_a;
    }
    
    private IBlockState func_176345_a(final World world, final BlockPos blockPos, final BlockPos blockPos2, IBlockState withProperty) {
        final IBlockState blockState = withProperty;
        final int intValue = (int)withProperty.getValue(BlockRedstoneWire.POWER);
        int func_176342_a = this.func_176342_a(world, blockPos2, 0);
        this.canProvidePower = false;
        final int func_175687_A = world.func_175687_A(blockPos);
        this.canProvidePower = true;
        if (func_175687_A > 0 && func_175687_A > -1) {
            func_176342_a = func_175687_A;
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final BlockPos offset = blockPos.offset(iterator.next());
            n = ((offset.getX() != blockPos2.getX() || offset.getZ() != blockPos2.getZ()) ? 1 : 0);
            if (false) {
                this.func_176342_a(world, offset, 0);
            }
            if (world.getBlockState(offset).getBlock().isNormalCube() && !world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube()) {
                if (!false || blockPos.getY() < blockPos2.getY()) {
                    continue;
                }
                this.func_176342_a(world, offset.offsetUp(), 0);
            }
            else {
                if (world.getBlockState(offset).getBlock().isNormalCube() || !false || blockPos.getY() > blockPos2.getY()) {
                    continue;
                }
                this.func_176342_a(world, offset.offsetDown(), 0);
            }
        }
        if (0 <= 0) {
            if (0 > 0) {
                --func_176342_a;
            }
        }
        if (func_175687_A > -1) {}
        if (intValue != 0) {
            withProperty = withProperty.withProperty(BlockRedstoneWire.POWER, 0);
            if (world.getBlockState(blockPos) == blockState) {
                world.setBlockState(blockPos, withProperty, 2);
            }
            this.field_150179_b.add(blockPos);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                this.field_150179_b.add(blockPos.offset(values[0]));
                ++n;
            }
        }
        return withProperty;
    }
    
    private void func_176344_d(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() == this) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            this.updateSurroundingRedstone(world, blockPos, blockState);
            final Iterator iterator = EnumFacing.Plane.VERTICAL.iterator();
            while (iterator.hasNext()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(iterator.next()), this);
            }
            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator2.hasNext()) {
                this.func_176344_d(world, blockPos.offset(iterator2.next()));
            }
            final Iterator iterator3 = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator3.hasNext()) {
                final BlockPos offset = blockPos.offset(iterator3.next());
                if (world.getBlockState(offset).getBlock().isNormalCube()) {
                    this.func_176344_d(world, offset.offsetUp());
                }
                else {
                    this.func_176344_d(world, offset.offsetDown());
                }
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        if (!world.isRemote) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
            this.updateSurroundingRedstone(world, blockPos, blockState);
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext()) {
                this.func_176344_d(world, blockPos.offset(iterator.next()));
            }
            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator2.hasNext()) {
                final BlockPos offset = blockPos.offset(iterator2.next());
                if (world.getBlockState(offset).getBlock().isNormalCube()) {
                    this.func_176344_d(world, offset.offsetUp());
                }
                else {
                    this.func_176344_d(world, offset.offsetDown());
                }
            }
        }
    }
    
    private int func_176342_a(final World world, final BlockPos blockPos, final int n) {
        if (world.getBlockState(blockPos).getBlock() != this) {
            return n;
        }
        final int intValue = (int)world.getBlockState(blockPos).getValue(BlockRedstoneWire.POWER);
        return (intValue > n) ? intValue : n;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            if (this.canPlaceBlockAt(world, blockToAir)) {
                this.updateSurroundingRedstone(world, blockToAir, blockState);
            }
            else {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
                world.setBlockToAir(blockToAir);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.redstone;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.canProvidePower ? this.isProvidingWeakPower(blockAccess, blockPos, blockState, enumFacing) : 0;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        if (!this.canProvidePower) {
            return 0;
        }
        final int intValue = (int)blockState.getValue(BlockRedstoneWire.POWER);
        if (intValue == 0) {
            return 0;
        }
        if (enumFacing == EnumFacing.UP) {
            return intValue;
        }
        final EnumSet<EnumFacing> none = EnumSet.noneOf(EnumFacing.class);
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (this.func_176339_d(blockAccess, blockPos, enumFacing2)) {
                none.add(enumFacing2);
            }
        }
        if (enumFacing.getAxis().isHorizontal() && none.isEmpty()) {
            return intValue;
        }
        if (none.contains(enumFacing) && !none.contains(enumFacing.rotateYCCW()) && !none.contains(enumFacing.rotateY())) {
            return intValue;
        }
        return 0;
    }
    
    private boolean func_176339_d(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing);
        final IBlockState blockState = blockAccess.getBlockState(offset);
        final Block block = blockState.getBlock();
        final boolean normalCube = block.isNormalCube();
        return (!blockAccess.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube() && normalCube && func_176340_e(blockAccess, offset.offsetUp())) || func_176343_a(blockState, enumFacing) || (block == Blocks.powered_repeater && blockState.getValue(BlockRedstoneDiode.AGE) == enumFacing) || (!normalCube && func_176340_e(blockAccess, offset.offsetDown()));
    }
    
    protected static boolean func_176340_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_176346_d(blockAccess.getBlockState(blockPos));
    }
    
    protected static boolean func_176346_d(final IBlockState blockState) {
        return func_176343_a(blockState, null);
    }
    
    protected static boolean func_176343_a(final IBlockState blockState, final EnumFacing enumFacing) {
        final Block block = blockState.getBlock();
        if (block == Blocks.redstone_wire) {
            return true;
        }
        if (Blocks.unpowered_repeater.func_149907_e(block)) {
            final EnumFacing enumFacing2 = (EnumFacing)blockState.getValue(BlockRedstoneRepeater.AGE);
            return enumFacing2 == enumFacing || enumFacing2.getOpposite() == enumFacing;
        }
        return block.canProvidePower() && enumFacing != null;
    }
    
    @Override
    public boolean canProvidePower() {
        return this.canProvidePower;
    }
    
    private int func_176337_b(final int n) {
        final float n2 = n / 15.0f;
        float n3 = n2 * 0.6f + 0.4f;
        if (n == 0) {
            n3 = 0.3f;
        }
        float n4 = n2 * n2 * 0.7f - 0.5f;
        float n5 = n2 * n2 * 0.6f - 0.7f;
        if (n4 < 0.0f) {
            n4 = 0.0f;
        }
        if (n5 < 0.0f) {
            n5 = 0.0f;
        }
        return 0xFF000000 | MathHelper.clamp_int((int)(n3 * 255.0f), 0, 255) << 16 | MathHelper.clamp_int((int)(n4 * 255.0f), 0, 255) << 8 | MathHelper.clamp_int((int)(n5 * 255.0f), 0, 255);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final int intValue = (int)blockState.getValue(BlockRedstoneWire.POWER);
        if (intValue != 0) {
            final double n = blockPos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            final double n2 = blockPos.getY() + 0.0625f;
            final double n3 = blockPos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            final float n4 = intValue / 15.0f;
            world.spawnParticle(EnumParticleTypes.REDSTONE, n, n2, n3, n4 * 0.6f + 0.4f, Math.max(0.0f, n4 * n4 * 0.7f - 0.5f), Math.max(0.0f, n4 * n4 * 0.6f - 0.7f), new int[0]);
        }
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.redstone;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRedstoneWire.POWER, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockRedstoneWire.POWER);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedstoneWire.NORTH, BlockRedstoneWire.EAST, BlockRedstoneWire.SOUTH, BlockRedstoneWire.WEST, BlockRedstoneWire.POWER });
    }
    
    enum EnumAttachPosition implements IStringSerializable
    {
        UP("UP", 0, "UP", 0, "up"), 
        SIDE("SIDE", 1, "SIDE", 1, "side"), 
        NONE("NONE", 2, "NONE", 2, "none");
        
        private final String field_176820_d;
        private static final EnumAttachPosition[] $VALUES;
        private static final String __OBFID;
        private static final EnumAttachPosition[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002070";
            ENUM$VALUES = new EnumAttachPosition[] { EnumAttachPosition.UP, EnumAttachPosition.SIDE, EnumAttachPosition.NONE };
            $VALUES = new EnumAttachPosition[] { EnumAttachPosition.UP, EnumAttachPosition.SIDE, EnumAttachPosition.NONE };
        }
        
        private EnumAttachPosition(final String s, final int n, final String s2, final int n2, final String field_176820_d) {
            this.field_176820_d = field_176820_d;
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        @Override
        public String getName() {
            return this.field_176820_d;
        }
    }
}
