package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public abstract class BlockRailBase extends Block
{
    protected final boolean isPowered;
    private static final String __OBFID;
    
    public static boolean func_176562_d(final World world, final BlockPos blockPos) {
        return func_176563_d(world.getBlockState(blockPos));
    }
    
    public static boolean func_176563_d(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        return block == Blocks.rail || block == Blocks.golden_rail || block == Blocks.detector_rail || block == Blocks.activator_rail;
    }
    
    protected BlockRailBase(final boolean isPowered) {
        super(Material.circuits);
        this.isPowered = isPowered;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabTransport);
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
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumRailDirection enumRailDirection = (blockState.getBlock() == this) ? ((EnumRailDirection)blockState.getValue(this.func_176560_l())) : null;
        if (enumRailDirection != null && enumRailDirection.func_177018_c()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, IBlockState func_176564_a) {
        if (!world.isRemote) {
            func_176564_a = this.func_176564_a(world, blockPos, func_176564_a, true);
            if (this.isPowered) {
                this.onNeighborBlockChange(world, blockPos, func_176564_a, this);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final EnumRailDirection enumRailDirection = (EnumRailDirection)blockState.getValue(this.func_176560_l());
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetDown())) {}
            if (enumRailDirection != EnumRailDirection.ASCENDING_EAST || World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetEast())) {
                if (enumRailDirection != EnumRailDirection.ASCENDING_WEST || World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetWest())) {
                    if (enumRailDirection != EnumRailDirection.ASCENDING_NORTH || World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetNorth())) {
                        if (enumRailDirection != EnumRailDirection.ASCENDING_SOUTH || !World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetSouth())) {}
                    }
                }
            }
            if (true) {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
                world.setBlockToAir(blockToAir);
            }
            else {
                this.func_176561_b(world, blockToAir, blockState, block);
            }
        }
    }
    
    protected void func_176561_b(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
    }
    
    protected IBlockState func_176564_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return world.isRemote ? blockState : new Rail(world, blockPos, blockState).func_180364_a(world.isBlockPowered(blockPos), b).func_180362_b();
    }
    
    @Override
    public int getMobilityFlag() {
        return 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        if (((EnumRailDirection)blockState.getValue(this.func_176560_l())).func_177018_c()) {
            world.notifyNeighborsOfStateChange(blockPos.offsetUp(), this);
        }
        if (this.isPowered) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
        }
    }
    
    public abstract IProperty func_176560_l();
    
    static {
        __OBFID = "CL_00000195";
    }
    
    public enum EnumRailDirection implements IStringSerializable
    {
        NORTH_SOUTH("NORTH_SOUTH", 0, "NORTH_SOUTH", 0, 0, "north_south"), 
        EAST_WEST("EAST_WEST", 1, "EAST_WEST", 1, 1, "east_west"), 
        ASCENDING_EAST("ASCENDING_EAST", 2, "ASCENDING_EAST", 2, 2, "ascending_east"), 
        ASCENDING_WEST("ASCENDING_WEST", 3, "ASCENDING_WEST", 3, 3, "ascending_west"), 
        ASCENDING_NORTH("ASCENDING_NORTH", 4, "ASCENDING_NORTH", 4, 4, "ascending_north"), 
        ASCENDING_SOUTH("ASCENDING_SOUTH", 5, "ASCENDING_SOUTH", 5, 5, "ascending_south"), 
        SOUTH_EAST("SOUTH_EAST", 6, "SOUTH_EAST", 6, 6, "south_east"), 
        SOUTH_WEST("SOUTH_WEST", 7, "SOUTH_WEST", 7, 7, "south_west"), 
        NORTH_WEST("NORTH_WEST", 8, "NORTH_WEST", 8, 8, "north_west"), 
        NORTH_EAST("NORTH_EAST", 9, "NORTH_EAST", 9, 9, "north_east");
        
        private static final EnumRailDirection[] field_177030_k;
        private final int field_177027_l;
        private final String field_177028_m;
        private static final EnumRailDirection[] $VALUES;
        private static final String __OBFID;
        private static final EnumRailDirection[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002137";
            ENUM$VALUES = new EnumRailDirection[] { EnumRailDirection.NORTH_SOUTH, EnumRailDirection.EAST_WEST, EnumRailDirection.ASCENDING_EAST, EnumRailDirection.ASCENDING_WEST, EnumRailDirection.ASCENDING_NORTH, EnumRailDirection.ASCENDING_SOUTH, EnumRailDirection.SOUTH_EAST, EnumRailDirection.SOUTH_WEST, EnumRailDirection.NORTH_WEST, EnumRailDirection.NORTH_EAST };
            field_177030_k = new EnumRailDirection[values().length];
            $VALUES = new EnumRailDirection[] { EnumRailDirection.NORTH_SOUTH, EnumRailDirection.EAST_WEST, EnumRailDirection.ASCENDING_EAST, EnumRailDirection.ASCENDING_WEST, EnumRailDirection.ASCENDING_NORTH, EnumRailDirection.ASCENDING_SOUTH, EnumRailDirection.SOUTH_EAST, EnumRailDirection.SOUTH_WEST, EnumRailDirection.NORTH_WEST, EnumRailDirection.NORTH_EAST };
            final EnumRailDirection[] values = values();
            while (0 < values.length) {
                final EnumRailDirection enumRailDirection = values[0];
                EnumRailDirection.field_177030_k[enumRailDirection.func_177015_a()] = enumRailDirection;
                int n = 0;
                ++n;
            }
        }
        
        private EnumRailDirection(final String s, final int n, final String s2, final int n2, final int field_177027_l, final String field_177028_m) {
            this.field_177027_l = field_177027_l;
            this.field_177028_m = field_177028_m;
        }
        
        public int func_177015_a() {
            return this.field_177027_l;
        }
        
        @Override
        public String toString() {
            return this.field_177028_m;
        }
        
        public boolean func_177018_c() {
            return this == EnumRailDirection.ASCENDING_NORTH || this == EnumRailDirection.ASCENDING_EAST || this == EnumRailDirection.ASCENDING_SOUTH || this == EnumRailDirection.ASCENDING_WEST;
        }
        
        public static EnumRailDirection func_177016_a(final int n) {
            if (0 < 0 || 0 >= EnumRailDirection.field_177030_k.length) {}
            return EnumRailDirection.field_177030_k[0];
        }
        
        @Override
        public String getName() {
            return this.field_177028_m;
        }
    }
    
    public class Rail
    {
        private final World field_150660_b;
        private final BlockPos field_180367_c;
        private final BlockRailBase field_180365_d;
        private IBlockState field_180366_e;
        private final boolean field_150656_f;
        private final List field_150657_g;
        private static final String __OBFID;
        final BlockRailBase this$0;
        
        public Rail(final BlockRailBase this$0, final World field_150660_b, final BlockPos field_180367_c, final IBlockState field_180366_e) {
            this.this$0 = this$0;
            this.field_150657_g = Lists.newArrayList();
            this.field_150660_b = field_150660_b;
            this.field_180367_c = field_180367_c;
            this.field_180366_e = field_180366_e;
            this.field_180365_d = (BlockRailBase)field_180366_e.getBlock();
            final EnumRailDirection enumRailDirection = (EnumRailDirection)field_180366_e.getValue(this$0.func_176560_l());
            this.field_150656_f = this.field_180365_d.isPowered;
            this.func_180360_a(enumRailDirection);
        }
        
        private void func_180360_a(final EnumRailDirection enumRailDirection) {
            this.field_150657_g.clear();
            switch (SwitchEnumRailDirection.field_180371_a[enumRailDirection.ordinal()]) {
                case 1: {
                    this.field_150657_g.add(this.field_180367_c.offsetNorth());
                    this.field_150657_g.add(this.field_180367_c.offsetSouth());
                    break;
                }
                case 2: {
                    this.field_150657_g.add(this.field_180367_c.offsetWest());
                    this.field_150657_g.add(this.field_180367_c.offsetEast());
                    break;
                }
                case 3: {
                    this.field_150657_g.add(this.field_180367_c.offsetWest());
                    this.field_150657_g.add(this.field_180367_c.offsetEast().offsetUp());
                    break;
                }
                case 4: {
                    this.field_150657_g.add(this.field_180367_c.offsetWest().offsetUp());
                    this.field_150657_g.add(this.field_180367_c.offsetEast());
                    break;
                }
                case 5: {
                    this.field_150657_g.add(this.field_180367_c.offsetNorth().offsetUp());
                    this.field_150657_g.add(this.field_180367_c.offsetSouth());
                    break;
                }
                case 6: {
                    this.field_150657_g.add(this.field_180367_c.offsetNorth());
                    this.field_150657_g.add(this.field_180367_c.offsetSouth().offsetUp());
                    break;
                }
                case 7: {
                    this.field_150657_g.add(this.field_180367_c.offsetEast());
                    this.field_150657_g.add(this.field_180367_c.offsetSouth());
                    break;
                }
                case 8: {
                    this.field_150657_g.add(this.field_180367_c.offsetWest());
                    this.field_150657_g.add(this.field_180367_c.offsetSouth());
                    break;
                }
                case 9: {
                    this.field_150657_g.add(this.field_180367_c.offsetWest());
                    this.field_150657_g.add(this.field_180367_c.offsetNorth());
                    break;
                }
                case 10: {
                    this.field_150657_g.add(this.field_180367_c.offsetEast());
                    this.field_150657_g.add(this.field_180367_c.offsetNorth());
                    break;
                }
            }
        }
        
        private void func_150651_b() {
            while (0 < this.field_150657_g.size()) {
                final Rail func_180697_b = this.func_180697_b(this.field_150657_g.get(0));
                int n2 = 0;
                if (func_180697_b != null && func_180697_b.func_150653_a(this)) {
                    this.field_150657_g.set(0, func_180697_b.field_180367_c);
                }
                else {
                    final List field_150657_g = this.field_150657_g;
                    final int n = 0;
                    --n2;
                    field_150657_g.remove(n);
                }
                ++n2;
            }
        }
        
        private boolean func_180359_a(final BlockPos blockPos) {
            return BlockRailBase.func_176562_d(this.field_150660_b, blockPos) || BlockRailBase.func_176562_d(this.field_150660_b, blockPos.offsetUp()) || BlockRailBase.func_176562_d(this.field_150660_b, blockPos.offsetDown());
        }
        
        private Rail func_180697_b(final BlockPos blockPos) {
            final IBlockState blockState = this.field_150660_b.getBlockState(blockPos);
            if (BlockRailBase.func_176563_d(blockState)) {
                return this.this$0.new Rail(this.field_150660_b, blockPos, blockState);
            }
            final BlockPos offsetUp = blockPos.offsetUp();
            final IBlockState blockState2 = this.field_150660_b.getBlockState(offsetUp);
            if (BlockRailBase.func_176563_d(blockState2)) {
                return this.this$0.new Rail(this.field_150660_b, offsetUp, blockState2);
            }
            final BlockPos offsetDown = blockPos.offsetDown();
            final IBlockState blockState3 = this.field_150660_b.getBlockState(offsetDown);
            return BlockRailBase.func_176563_d(blockState3) ? this.this$0.new Rail(this.field_150660_b, offsetDown, blockState3) : null;
        }
        
        private boolean func_150653_a(final Rail rail) {
            return this.func_180363_c(rail.field_180367_c);
        }
        
        private boolean func_180363_c(final BlockPos blockPos) {
            while (0 < this.field_150657_g.size()) {
                final BlockPos blockPos2 = this.field_150657_g.get(0);
                if (blockPos2.getX() == blockPos.getX() && blockPos2.getZ() == blockPos.getZ()) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        protected int countAdjacentRails() {
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext()) {
                if (this.func_180359_a(this.field_180367_c.offset(iterator.next()))) {
                    int n = 0;
                    ++n;
                }
            }
            return 0;
        }
        
        private boolean func_150649_b(final Rail rail) {
            return this.func_150653_a(rail) || this.field_150657_g.size() != 2;
        }
        
        private void func_150645_c(final Rail rail) {
            this.field_150657_g.add(rail.field_180367_c);
            final BlockPos offsetNorth = this.field_180367_c.offsetNorth();
            final BlockPos offsetSouth = this.field_180367_c.offsetSouth();
            final BlockPos offsetWest = this.field_180367_c.offsetWest();
            final BlockPos offsetEast = this.field_180367_c.offsetEast();
            final boolean func_180363_c = this.func_180363_c(offsetNorth);
            final boolean func_180363_c2 = this.func_180363_c(offsetSouth);
            final boolean func_180363_c3 = this.func_180363_c(offsetWest);
            final boolean func_180363_c4 = this.func_180363_c(offsetEast);
            EnumRailDirection enumRailDirection = null;
            if (func_180363_c || func_180363_c2) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            if (func_180363_c3 || func_180363_c4) {
                enumRailDirection = EnumRailDirection.EAST_WEST;
            }
            if (!this.field_150656_f) {
                if (func_180363_c2 && func_180363_c4 && !func_180363_c && !func_180363_c3) {
                    enumRailDirection = EnumRailDirection.SOUTH_EAST;
                }
                if (func_180363_c2 && func_180363_c3 && !func_180363_c && !func_180363_c4) {
                    enumRailDirection = EnumRailDirection.SOUTH_WEST;
                }
                if (func_180363_c && func_180363_c3 && !func_180363_c2 && !func_180363_c4) {
                    enumRailDirection = EnumRailDirection.NORTH_WEST;
                }
                if (func_180363_c && func_180363_c4 && !func_180363_c2 && !func_180363_c3) {
                    enumRailDirection = EnumRailDirection.NORTH_EAST;
                }
            }
            if (enumRailDirection == EnumRailDirection.NORTH_SOUTH) {
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetNorth.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_NORTH;
                }
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetSouth.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_SOUTH;
                }
            }
            if (enumRailDirection == EnumRailDirection.EAST_WEST) {
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetEast.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_EAST;
                }
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetWest.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_WEST;
                }
            }
            if (enumRailDirection == null) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            this.field_180366_e = this.field_180366_e.withProperty(this.field_180365_d.func_176560_l(), enumRailDirection);
            this.field_150660_b.setBlockState(this.field_180367_c, this.field_180366_e, 3);
        }
        
        private boolean func_180361_d(final BlockPos blockPos) {
            final Rail func_180697_b = this.func_180697_b(blockPos);
            if (func_180697_b == null) {
                return false;
            }
            func_180697_b.func_150651_b();
            return func_180697_b.func_150649_b(this);
        }
        
        public Rail func_180364_a(final boolean b, final boolean b2) {
            final BlockPos offsetNorth = this.field_180367_c.offsetNorth();
            final BlockPos offsetSouth = this.field_180367_c.offsetSouth();
            final BlockPos offsetWest = this.field_180367_c.offsetWest();
            final BlockPos offsetEast = this.field_180367_c.offsetEast();
            final boolean func_180361_d = this.func_180361_d(offsetNorth);
            final boolean func_180361_d2 = this.func_180361_d(offsetSouth);
            final boolean func_180361_d3 = this.func_180361_d(offsetWest);
            final boolean func_180361_d4 = this.func_180361_d(offsetEast);
            EnumRailDirection enumRailDirection = null;
            if ((func_180361_d || func_180361_d2) && !func_180361_d3 && !func_180361_d4) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            if ((func_180361_d3 || func_180361_d4) && !func_180361_d && !func_180361_d2) {
                enumRailDirection = EnumRailDirection.EAST_WEST;
            }
            if (!this.field_150656_f) {
                if (func_180361_d2 && func_180361_d4 && !func_180361_d && !func_180361_d3) {
                    enumRailDirection = EnumRailDirection.SOUTH_EAST;
                }
                if (func_180361_d2 && func_180361_d3 && !func_180361_d && !func_180361_d4) {
                    enumRailDirection = EnumRailDirection.SOUTH_WEST;
                }
                if (func_180361_d && func_180361_d3 && !func_180361_d2 && !func_180361_d4) {
                    enumRailDirection = EnumRailDirection.NORTH_WEST;
                }
                if (func_180361_d && func_180361_d4 && !func_180361_d2 && !func_180361_d3) {
                    enumRailDirection = EnumRailDirection.NORTH_EAST;
                }
            }
            if (enumRailDirection == null) {
                if (func_180361_d || func_180361_d2) {
                    enumRailDirection = EnumRailDirection.NORTH_SOUTH;
                }
                if (func_180361_d3 || func_180361_d4) {
                    enumRailDirection = EnumRailDirection.EAST_WEST;
                }
                if (!this.field_150656_f) {
                    if (b) {
                        if (func_180361_d2 && func_180361_d4) {
                            enumRailDirection = EnumRailDirection.SOUTH_EAST;
                        }
                        if (func_180361_d3 && func_180361_d2) {
                            enumRailDirection = EnumRailDirection.SOUTH_WEST;
                        }
                        if (func_180361_d4 && func_180361_d) {
                            enumRailDirection = EnumRailDirection.NORTH_EAST;
                        }
                        if (func_180361_d && func_180361_d3) {
                            enumRailDirection = EnumRailDirection.NORTH_WEST;
                        }
                    }
                    else {
                        if (func_180361_d && func_180361_d3) {
                            enumRailDirection = EnumRailDirection.NORTH_WEST;
                        }
                        if (func_180361_d4 && func_180361_d) {
                            enumRailDirection = EnumRailDirection.NORTH_EAST;
                        }
                        if (func_180361_d3 && func_180361_d2) {
                            enumRailDirection = EnumRailDirection.SOUTH_WEST;
                        }
                        if (func_180361_d2 && func_180361_d4) {
                            enumRailDirection = EnumRailDirection.SOUTH_EAST;
                        }
                    }
                }
            }
            if (enumRailDirection == EnumRailDirection.NORTH_SOUTH) {
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetNorth.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_NORTH;
                }
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetSouth.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_SOUTH;
                }
            }
            if (enumRailDirection == EnumRailDirection.EAST_WEST) {
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetEast.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_EAST;
                }
                if (BlockRailBase.func_176562_d(this.field_150660_b, offsetWest.offsetUp())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_WEST;
                }
            }
            if (enumRailDirection == null) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            this.func_180360_a(enumRailDirection);
            this.field_180366_e = this.field_180366_e.withProperty(this.field_180365_d.func_176560_l(), enumRailDirection);
            if (b2 || this.field_150660_b.getBlockState(this.field_180367_c) != this.field_180366_e) {
                this.field_150660_b.setBlockState(this.field_180367_c, this.field_180366_e, 3);
                while (0 < this.field_150657_g.size()) {
                    final Rail func_180697_b = this.func_180697_b(this.field_150657_g.get(0));
                    if (func_180697_b != null) {
                        func_180697_b.func_150651_b();
                        if (func_180697_b.func_150649_b(this)) {
                            func_180697_b.func_150645_c(this);
                        }
                    }
                    int n = 0;
                    ++n;
                }
            }
            return this;
        }
        
        public IBlockState func_180362_b() {
            return this.field_180366_e;
        }
        
        static {
            __OBFID = "CL_00000196";
        }
    }
    
    static final class SwitchEnumRailDirection
    {
        static final int[] field_180371_a;
        private static final String __OBFID;
        private static final String[] lIlIIlllIIIIlIll;
        private static String[] lIlIIlllIIIIllII;
        
        static {
            llllIllIIIIIlIlI();
            llllIllIIIIIlIIl();
            __OBFID = SwitchEnumRailDirection.lIlIIlllIIIIlIll[0];
            field_180371_a = new int[EnumRailDirection.values().length];
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.NORTH_SOUTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.EAST_WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.ASCENDING_EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.ASCENDING_WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.ASCENDING_NORTH.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.SOUTH_EAST.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.SOUTH_WEST.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.NORTH_WEST.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchEnumRailDirection.field_180371_a[EnumRailDirection.NORTH_EAST.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
        }
        
        private static void llllIllIIIIIlIIl() {
            (lIlIIlllIIIIlIll = new String[1])[0] = llllIllIIIIIlIII(SwitchEnumRailDirection.lIlIIlllIIIIllII[0], SwitchEnumRailDirection.lIlIIlllIIIIllII[1]);
            SwitchEnumRailDirection.lIlIIlllIIIIllII = null;
        }
        
        private static void llllIllIIIIIlIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumRailDirection.lIlIIlllIIIIllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llllIllIIIIIlIII(final String s, final String s2) {
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
