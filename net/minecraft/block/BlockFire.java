package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockFire extends Block
{
    public static final PropertyInteger field_176543_a;
    public static final PropertyBool field_176540_b;
    public static final PropertyBool field_176544_M;
    public static final PropertyBool field_176545_N;
    public static final PropertyBool field_176546_O;
    public static final PropertyBool field_176541_P;
    public static final PropertyBool field_176539_Q;
    public static final PropertyInteger field_176542_R;
    private final Map field_149849_a;
    private final Map field_149848_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000245";
        field_176543_a = PropertyInteger.create("age", 0, 15);
        field_176540_b = PropertyBool.create("flip");
        field_176544_M = PropertyBool.create("alt");
        field_176545_N = PropertyBool.create("north");
        field_176546_O = PropertyBool.create("east");
        field_176541_P = PropertyBool.create("south");
        field_176539_Q = PropertyBool.create("west");
        field_176542_R = PropertyInteger.create("upper", 0, 2);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        if (!World.doesBlockHaveSolidTopSurface(blockAccess, blockPos.offsetDown()) && !Blocks.fire.func_176535_e(blockAccess, blockPos.offsetDown())) {
            final boolean b = (x + y + z & 0x1) == 0x1;
            final boolean b2 = (x / 2 + y / 2 + z / 2 & 0x1) == 0x1;
            if (this.func_176535_e(blockAccess, blockPos.offsetUp())) {
                final int n = b ? 1 : 2;
            }
            return blockState.withProperty(BlockFire.field_176545_N, this.func_176535_e(blockAccess, blockPos.offsetNorth())).withProperty(BlockFire.field_176546_O, this.func_176535_e(blockAccess, blockPos.offsetEast())).withProperty(BlockFire.field_176541_P, this.func_176535_e(blockAccess, blockPos.offsetSouth())).withProperty(BlockFire.field_176539_Q, this.func_176535_e(blockAccess, blockPos.offsetWest())).withProperty(BlockFire.field_176542_R, 0).withProperty(BlockFire.field_176540_b, b2).withProperty(BlockFire.field_176544_M, b);
        }
        return this.getDefaultState();
    }
    
    protected BlockFire() {
        super(Material.fire);
        this.field_149849_a = Maps.newIdentityHashMap();
        this.field_149848_b = Maps.newIdentityHashMap();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFire.field_176543_a, 0).withProperty(BlockFire.field_176540_b, false).withProperty(BlockFire.field_176544_M, false).withProperty(BlockFire.field_176545_N, false).withProperty(BlockFire.field_176546_O, false).withProperty(BlockFire.field_176541_P, false).withProperty(BlockFire.field_176539_Q, false).withProperty(BlockFire.field_176542_R, 0));
        this.setTickRandomly(true);
    }
    
    public static void func_149843_e() {
        Blocks.fire.func_180686_a(Blocks.planks, 5, 20);
        Blocks.fire.func_180686_a(Blocks.double_wooden_slab, 5, 20);
        Blocks.fire.func_180686_a(Blocks.wooden_slab, 5, 20);
        Blocks.fire.func_180686_a(Blocks.oak_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.spruce_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.birch_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.jungle_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.dark_oak_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.acacia_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.oak_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.spruce_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.birch_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.jungle_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.dark_oak_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.acacia_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.oak_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.birch_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.spruce_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.jungle_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.log, 5, 5);
        Blocks.fire.func_180686_a(Blocks.log2, 5, 5);
        Blocks.fire.func_180686_a(Blocks.leaves, 30, 60);
        Blocks.fire.func_180686_a(Blocks.leaves2, 30, 60);
        Blocks.fire.func_180686_a(Blocks.bookshelf, 30, 20);
        Blocks.fire.func_180686_a(Blocks.tnt, 15, 100);
        Blocks.fire.func_180686_a(Blocks.tallgrass, 60, 100);
        Blocks.fire.func_180686_a(Blocks.double_plant, 60, 100);
        Blocks.fire.func_180686_a(Blocks.yellow_flower, 60, 100);
        Blocks.fire.func_180686_a(Blocks.red_flower, 60, 100);
        Blocks.fire.func_180686_a(Blocks.deadbush, 60, 100);
        Blocks.fire.func_180686_a(Blocks.wool, 30, 60);
        Blocks.fire.func_180686_a(Blocks.vine, 15, 100);
        Blocks.fire.func_180686_a(Blocks.coal_block, 5, 5);
        Blocks.fire.func_180686_a(Blocks.hay_block, 60, 20);
        Blocks.fire.func_180686_a(Blocks.carpet, 60, 20);
    }
    
    public void func_180686_a(final Block block, final int n, final int n2) {
        this.field_149849_a.put(block, n);
        this.field_149848_b.put(block, n2);
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
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public int tickRate(final World world) {
        return 30;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, IBlockState withProperty, final Random random) {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick")) {
            if (!this.canPlaceBlockAt(world, blockPos)) {
                world.setBlockToAir(blockPos);
            }
            final Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
            final boolean b = block == Blocks.netherrack;
            if (!(world.provider instanceof WorldProviderEnd) || block == Blocks.bedrock) {}
            if (!true && world.isRaining() && this.func_176537_d(world, blockPos)) {
                world.setBlockToAir(blockPos);
            }
            else {
                final int intValue = (int)withProperty.getValue(BlockFire.field_176543_a);
                if (intValue < 15) {
                    withProperty = withProperty.withProperty(BlockFire.field_176543_a, intValue + random.nextInt(3) / 2);
                    world.setBlockState(blockPos, withProperty, 4);
                }
                world.scheduleUpdate(blockPos, this, this.tickRate(world) + random.nextInt(10));
                if (!true) {
                    if (!this.func_176533_e(world, blockPos)) {
                        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) || intValue > 3) {
                            world.setBlockToAir(blockPos);
                        }
                        return;
                    }
                    if (!this.func_176535_e(world, blockPos.offsetDown()) && intValue == 15 && random.nextInt(4) == 0) {
                        world.setBlockToAir(blockPos);
                        return;
                    }
                }
                final boolean func_180502_D = world.func_180502_D(blockPos);
                if (func_180502_D) {}
                this.func_176536_a(world, blockPos.offsetEast(), 250, random, intValue);
                this.func_176536_a(world, blockPos.offsetWest(), 250, random, intValue);
                this.func_176536_a(world, blockPos.offsetDown(), 200, random, intValue);
                this.func_176536_a(world, blockPos.offsetUp(), 200, random, intValue);
                this.func_176536_a(world, blockPos.offsetNorth(), 250, random, intValue);
                this.func_176536_a(world, blockPos.offsetSouth(), 250, random, intValue);
                while (-1 <= 1) {
                    while (-1 <= 1) {
                        while (-1 <= 4) {
                            if (-1 != 0 || -1 != 0 || -1 != 0) {
                                if (-1 > 1) {}
                                final BlockPos add = blockPos.add(-1, -1, -1);
                                final int func_176538_m = this.func_176538_m(world, add);
                                if (func_176538_m > 0) {
                                    int n = (func_176538_m + 40 + world.getDifficulty().getDifficultyId() * 7) / (intValue + 30);
                                    if (func_180502_D) {
                                        n /= 2;
                                    }
                                    if (n > 0 && random.nextInt(100) <= n && (!world.isRaining() || !this.func_176537_d(world, add))) {
                                        final int n2 = intValue + random.nextInt(5) / 4;
                                        if (15 > 15) {}
                                        world.setBlockState(add, withProperty.withProperty(BlockFire.field_176543_a, 15), 3);
                                    }
                                }
                            }
                            int n3 = 0;
                            ++n3;
                        }
                        int n4 = 0;
                        ++n4;
                    }
                    int n5 = 0;
                    ++n5;
                }
            }
        }
    }
    
    protected boolean func_176537_d(final World world, final BlockPos blockPos) {
        return world.func_175727_C(blockPos) || world.func_175727_C(blockPos.offsetWest()) || world.func_175727_C(blockPos.offsetEast()) || world.func_175727_C(blockPos.offsetNorth()) || world.func_175727_C(blockPos.offsetSouth());
    }
    
    @Override
    public boolean requiresUpdates() {
        return false;
    }
    
    private int func_176532_c(final Block block) {
        final Integer n = this.field_149848_b.get(block);
        return (n == null) ? 0 : n;
    }
    
    private int func_176534_d(final Block block) {
        final Integer n = this.field_149849_a.get(block);
        return (n == null) ? 0 : n;
    }
    
    private void func_176536_a(final World world, final BlockPos blockToAir, final int n, final Random random, final int n2) {
        if (random.nextInt(n) < this.func_176532_c(world.getBlockState(blockToAir).getBlock())) {
            final IBlockState blockState = world.getBlockState(blockToAir);
            if (random.nextInt(n2 + 10) < 5 && !world.func_175727_C(blockToAir)) {
                final int n3 = n2 + random.nextInt(5) / 4;
                if (15 > 15) {}
                world.setBlockState(blockToAir, this.getDefaultState().withProperty(BlockFire.field_176543_a, 15), 3);
            }
            else {
                world.setBlockToAir(blockToAir);
            }
            if (blockState.getBlock() == Blocks.tnt) {
                Blocks.tnt.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty(BlockTNT.field_176246_a, true));
            }
        }
    }
    
    private boolean func_176533_e(final World world, final BlockPos blockPos) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            if (this.func_176535_e(world, blockPos.offset(values[0]))) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private int func_176538_m(final World world, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos)) {
            return 0;
        }
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            Math.max(this.func_176534_d(world.getBlockState(blockPos.offset(values[0])).getBlock()), 0);
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    @Override
    public boolean isCollidable() {
        return false;
    }
    
    public boolean func_176535_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.func_176534_d(blockAccess.getBlockState(blockPos).getBlock()) > 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) || this.func_176533_e(world, blockPos);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetDown()) && !this.func_176533_e(world, blockToAir)) {
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (world.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(world, blockToAir)) {
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetDown()) && !this.func_176533_e(world, blockToAir)) {
                world.setBlockToAir(blockToAir);
            }
            else {
                world.scheduleUpdate(blockToAir, this, this.tickRate(world) + world.rand.nextInt(10));
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (random.nextInt(24) == 0) {
            world.playSound(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, "fire.fire", 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
        }
        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) && !Blocks.fire.func_176535_e(world, blockPos.offsetDown())) {
            int n = 0;
            if (Blocks.fire.func_176535_e(world, blockPos.offsetWest())) {
                while (0 < 2) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble() * 0.10000000149011612, blockPos.getY() + random.nextDouble(), blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
                    ++n;
                }
            }
            if (Blocks.fire.func_176535_e(world, blockPos.offsetEast())) {
                while (0 < 2) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + 1 - random.nextDouble() * 0.10000000149011612, blockPos.getY() + random.nextDouble(), blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
                    ++n;
                }
            }
            if (Blocks.fire.func_176535_e(world, blockPos.offsetNorth())) {
                while (0 < 2) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + random.nextDouble(), blockPos.getZ() + random.nextDouble() * 0.10000000149011612, 0.0, 0.0, 0.0, new int[0]);
                    ++n;
                }
            }
            if (Blocks.fire.func_176535_e(world, blockPos.offsetSouth())) {
                while (0 < 2) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + random.nextDouble(), blockPos.getZ() + 1 - random.nextDouble() * 0.10000000149011612, 0.0, 0.0, 0.0, new int[0]);
                    ++n;
                }
            }
            if (Blocks.fire.func_176535_e(world, blockPos.offsetUp())) {
                while (0 < 2) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + 1 - random.nextDouble() * 0.10000000149011612, blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
                    ++n;
                }
            }
        }
        else {
            while (0 < 3) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + random.nextDouble() * 0.5 + 0.5, blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.tntColor;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockFire.field_176543_a, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockFire.field_176543_a);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFire.field_176543_a, BlockFire.field_176545_N, BlockFire.field_176546_O, BlockFire.field_176541_P, BlockFire.field_176539_Q, BlockFire.field_176542_R, BlockFire.field_176540_b, BlockFire.field_176544_M });
    }
}
