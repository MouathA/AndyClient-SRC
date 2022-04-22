package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;

public class BlockPortal extends BlockBreakable
{
    public static final PropertyEnum field_176550_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000284";
        field_176550_a = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
    }
    
    public BlockPortal() {
        super(Material.portal, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPortal.field_176550_a, EnumFacing.Axis.X));
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.updateTick(world, blockPos, blockState, random);
        if (world.provider.isSurfaceWorld() && world.getGameRules().getGameRuleBooleanValue("doMobSpawning") && random.nextInt(2000) < world.getDifficulty().getDifficultyId()) {
            final int y = blockPos.getY();
            BlockPos offsetDown;
            for (offsetDown = blockPos; !World.doesBlockHaveSolidTopSurface(world, offsetDown) && offsetDown.getY() > 0; offsetDown = offsetDown.offsetDown()) {}
            if (y > 0 && !world.getBlockState(offsetDown.offsetUp()).getBlock().isNormalCube()) {
                final Entity spawnCreature = ItemMonsterPlacer.spawnCreature(world, 57, offsetDown.getX() + 0.5, offsetDown.getY() + 1.1, offsetDown.getZ() + 0.5);
                if (spawnCreature != null) {
                    spawnCreature.timeUntilPortal = spawnCreature.getPortalCooldown();
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing.Axis axis = (EnumFacing.Axis)blockAccess.getBlockState(blockPos).getValue(BlockPortal.field_176550_a);
        float n = 0.125f;
        float n2 = 0.125f;
        if (axis == EnumFacing.Axis.X) {
            n = 0.5f;
        }
        if (axis == EnumFacing.Axis.Z) {
            n2 = 0.5f;
        }
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n2, 0.5f + n, 1.0f, 0.5f + n2);
    }
    
    public static int func_176549_a(final EnumFacing.Axis axis) {
        return (axis == EnumFacing.Axis.X) ? 1 : ((axis == EnumFacing.Axis.Z) ? 2 : 0);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    public boolean func_176548_d(final World world, final BlockPos blockPos) {
        final Size size = new Size(world, blockPos, EnumFacing.Axis.X);
        if (size.func_150860_b() && Size.access$0(size) == 0) {
            size.func_150859_c();
            return true;
        }
        final Size size2 = new Size(world, blockPos, EnumFacing.Axis.Z);
        if (size2.func_150860_b() && Size.access$0(size2) == 0) {
            size2.func_150859_c();
            return true;
        }
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final EnumFacing.Axis axis = (EnumFacing.Axis)blockState.getValue(BlockPortal.field_176550_a);
        if (axis == EnumFacing.Axis.X) {
            final Size size = new Size(world, blockPos, EnumFacing.Axis.X);
            if (!size.func_150860_b() || Size.access$0(size) < Size.access$1(size) * Size.access$2(size)) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
        else if (axis == EnumFacing.Axis.Z) {
            final Size size2 = new Size(world, blockPos, EnumFacing.Axis.Z);
            if (!size2.func_150860_b() || Size.access$0(size2) < Size.access$1(size2) * Size.access$2(size2)) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        EnumFacing.Axis axis = null;
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockAccess.getBlockState(blockPos).getBlock() == this) {
            axis = (EnumFacing.Axis)blockState.getValue(BlockPortal.field_176550_a);
            if (axis == null) {
                return false;
            }
            if (axis == EnumFacing.Axis.Z && enumFacing != EnumFacing.EAST && enumFacing != EnumFacing.WEST) {
                return false;
            }
            if (axis == EnumFacing.Axis.X && enumFacing != EnumFacing.SOUTH && enumFacing != EnumFacing.NORTH) {
                return false;
            }
        }
        final boolean b = blockAccess.getBlockState(blockPos.offsetWest()).getBlock() == this && blockAccess.getBlockState(blockPos.offsetWest(2)).getBlock() != this;
        final boolean b2 = blockAccess.getBlockState(blockPos.offsetEast()).getBlock() == this && blockAccess.getBlockState(blockPos.offsetEast(2)).getBlock() != this;
        final boolean b3 = blockAccess.getBlockState(blockPos.offsetNorth()).getBlock() == this && blockAccess.getBlockState(blockPos.offsetNorth(2)).getBlock() != this;
        final boolean b4 = blockAccess.getBlockState(blockPos.offsetSouth()).getBlock() == this && blockAccess.getBlockState(blockPos.offsetSouth(2)).getBlock() != this;
        final boolean b5 = b || b2 || axis == EnumFacing.Axis.X;
        final boolean b6 = b3 || b4 || axis == EnumFacing.Axis.Z;
        return (b5 && enumFacing == EnumFacing.WEST) || (b5 && enumFacing == EnumFacing.EAST) || (b6 && enumFacing == EnumFacing.NORTH) || (b6 && enumFacing == EnumFacing.SOUTH);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (entity.ridingEntity == null && entity.riddenByEntity == null) {
            entity.setInPortal();
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "portal.portal", 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
        }
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPortal.field_176550_a, ((n & 0x3) == 0x2) ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return func_176549_a((EnumFacing.Axis)blockState.getValue(BlockPortal.field_176550_a));
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPortal.field_176550_a });
    }
    
    public static class Size
    {
        private final World field_150867_a;
        private final EnumFacing.Axis field_150865_b;
        private final EnumFacing field_150866_c;
        private final EnumFacing field_150863_d;
        private int field_150864_e;
        private BlockPos field_150861_f;
        private int field_150862_g;
        private int field_150868_h;
        private static final String __OBFID;
        
        public Size(final World field_150867_a, BlockPos offsetDown, final EnumFacing.Axis field_150865_b) {
            this.field_150864_e = 0;
            this.field_150867_a = field_150867_a;
            this.field_150865_b = field_150865_b;
            if (field_150865_b == EnumFacing.Axis.X) {
                this.field_150863_d = EnumFacing.EAST;
                this.field_150866_c = EnumFacing.WEST;
            }
            else {
                this.field_150863_d = EnumFacing.NORTH;
                this.field_150866_c = EnumFacing.SOUTH;
            }
            while (offsetDown.getY() > offsetDown.getY() - 21 && offsetDown.getY() > 0 && this.func_150857_a(field_150867_a.getBlockState(offsetDown.offsetDown()).getBlock())) {
                offsetDown = offsetDown.offsetDown();
            }
            final int n = this.func_180120_a(offsetDown, this.field_150863_d) - 1;
            if (n >= 0) {
                this.field_150861_f = offsetDown.offset(this.field_150863_d, n);
                this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
                if (this.field_150868_h < 2 || this.field_150868_h > 21) {
                    this.field_150861_f = null;
                    this.field_150868_h = 0;
                }
            }
            if (this.field_150861_f != null) {
                this.field_150862_g = this.func_150858_a();
            }
        }
        
        protected int func_180120_a(final BlockPos blockPos, final EnumFacing enumFacing) {
            while (0 < 22) {
                final BlockPos offset = blockPos.offset(enumFacing, 0);
                if (!this.func_150857_a(this.field_150867_a.getBlockState(offset).getBlock())) {
                    break;
                }
                if (this.field_150867_a.getBlockState(offset.offsetDown()).getBlock() != Blocks.obsidian) {
                    break;
                }
                int n = 0;
                ++n;
            }
            return (this.field_150867_a.getBlockState(blockPos.offset(enumFacing, 0)).getBlock() == Blocks.obsidian && false) ? 1 : 0;
        }
        
        protected int func_150858_a() {
            this.field_150862_g = 0;
            int n = 0;
        Label_0179:
            while (this.field_150862_g < 21) {
                while (0 < this.field_150868_h) {
                    final BlockPos offsetUp = this.field_150861_f.offset(this.field_150866_c, 0).offsetUp(this.field_150862_g);
                    final Block block = this.field_150867_a.getBlockState(offsetUp).getBlock();
                    if (!this.func_150857_a(block)) {
                        break Label_0179;
                    }
                    if (block == Blocks.portal) {
                        ++this.field_150864_e;
                    }
                    if (!false) {
                        if (this.field_150867_a.getBlockState(offsetUp.offset(this.field_150863_d)).getBlock() != Blocks.obsidian) {
                            break Label_0179;
                        }
                    }
                    else if (0 == this.field_150868_h - 1 && this.field_150867_a.getBlockState(offsetUp.offset(this.field_150866_c)).getBlock() != Blocks.obsidian) {
                        break Label_0179;
                    }
                    ++n;
                }
                ++this.field_150862_g;
            }
            while (0 < this.field_150868_h) {
                if (this.field_150867_a.getBlockState(this.field_150861_f.offset(this.field_150866_c, 0).offsetUp(this.field_150862_g)).getBlock() != Blocks.obsidian) {
                    this.field_150862_g = 0;
                    break;
                }
                ++n;
            }
            if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
                return this.field_150862_g;
            }
            this.field_150861_f = null;
            this.field_150868_h = 0;
            return this.field_150862_g = 0;
        }
        
        protected boolean func_150857_a(final Block block) {
            return block.blockMaterial == Material.air || block == Blocks.fire || block == Blocks.portal;
        }
        
        public boolean func_150860_b() {
            return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
        }
        
        public void func_150859_c() {
            while (0 < this.field_150868_h) {
                final BlockPos offset = this.field_150861_f.offset(this.field_150866_c, 0);
                while (0 < this.field_150862_g) {
                    this.field_150867_a.setBlockState(offset.offsetUp(0), Blocks.portal.getDefaultState().withProperty(BlockPortal.field_176550_a, this.field_150865_b), 2);
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        
        static int access$0(final Size size) {
            return size.field_150864_e;
        }
        
        static int access$1(final Size size) {
            return size.field_150868_h;
        }
        
        static int access$2(final Size size) {
            return size.field_150862_g;
        }
        
        static {
            __OBFID = "CL_00000285";
        }
    }
}
