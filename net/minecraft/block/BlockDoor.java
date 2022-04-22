package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockDoor extends Block
{
    public static final PropertyDirection FACING_PROP;
    public static final PropertyBool OPEN_PROP;
    public static final PropertyEnum HINGEPOSITION_PROP;
    public static final PropertyBool POWERED_PROP;
    public static final PropertyEnum HALF_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000230";
        FACING_PROP = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        OPEN_PROP = PropertyBool.create("open");
        HINGEPOSITION_PROP = PropertyEnum.create("hinge", EnumHingePosition.class);
        POWERED_PROP = PropertyBool.create("powered");
        HALF_PROP = PropertyEnum.create("half", EnumDoorHalf.class);
    }
    
    protected BlockDoor(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDoor.FACING_PROP, EnumFacing.NORTH).withProperty(BlockDoor.OPEN_PROP, false).withProperty(BlockDoor.HINGEPOSITION_PROP, EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED_PROP, false).withProperty(BlockDoor.HALF_PROP, EnumDoorHalf.LOWER));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_176516_g(func_176515_e(blockAccess, blockPos));
    }
    
    @Override
    public boolean isFullCube() {
        return false;
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
        this.func_150011_b(func_176515_e(blockAccess, blockPos));
    }
    
    private void func_150011_b(final int n) {
        final float n2 = 0.1875f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        final EnumFacing func_176511_f = func_176511_f(n);
        final boolean func_176516_g = func_176516_g(n);
        final boolean func_176513_j = func_176513_j(n);
        if (func_176516_g) {
            if (func_176511_f == EnumFacing.EAST) {
                if (!func_176513_j) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
                }
            }
            else if (func_176511_f == EnumFacing.SOUTH) {
                if (!func_176513_j) {
                    this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
                }
            }
            else if (func_176511_f == EnumFacing.WEST) {
                if (!func_176513_j) {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
                }
            }
            else if (func_176511_f == EnumFacing.NORTH) {
                if (!func_176513_j) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
            }
        }
        else if (func_176511_f == EnumFacing.EAST) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
        }
        else if (func_176511_f == EnumFacing.SOUTH) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
        }
        else if (func_176511_f == EnumFacing.WEST) {
            this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (func_176511_f == EnumFacing.NORTH) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        final BlockPos blockPos2 = (cycleProperty.getValue(BlockDoor.HALF_PROP) == EnumDoorHalf.LOWER) ? blockPos : blockPos.offsetDown();
        final IBlockState blockState = blockPos.equals(blockPos2) ? cycleProperty : world.getBlockState(blockPos2);
        if (blockState.getBlock() != this) {
            return false;
        }
        cycleProperty = blockState.cycleProperty(BlockDoor.OPEN_PROP);
        world.setBlockState(blockPos2, cycleProperty, 2);
        world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
        world.playAuxSFXAtEntity(entityPlayer, ((boolean)cycleProperty.getValue(BlockDoor.OPEN_PROP)) ? 1003 : 1006, blockPos, 0);
        return true;
    }
    
    public void func_176512_a(final World world, final BlockPos blockPos, final boolean b) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final BlockPos blockPos2 = (blockState.getValue(BlockDoor.HALF_PROP) == EnumDoorHalf.LOWER) ? blockPos : blockPos.offsetDown();
            final IBlockState blockState2 = (blockPos == blockPos2) ? blockState : world.getBlockState(blockPos2);
            if (blockState2.getBlock() == this && (boolean)blockState2.getValue(BlockDoor.OPEN_PROP) != b) {
                world.setBlockState(blockPos2, blockState2.withProperty(BlockDoor.OPEN_PROP, b), 2);
                world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
                world.playAuxSFXAtEntity(null, b ? 1003 : 1006, blockPos, 0);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (blockState.getValue(BlockDoor.HALF_PROP) == EnumDoorHalf.UPPER) {
            final BlockPos offsetDown = blockToAir.offsetDown();
            final IBlockState blockState2 = world.getBlockState(offsetDown);
            if (blockState2.getBlock() != this) {
                world.setBlockToAir(blockToAir);
            }
            else if (block != this) {
                this.onNeighborBlockChange(world, offsetDown, blockState2, block);
            }
        }
        else {
            final BlockPos offsetUp = blockToAir.offsetUp();
            final IBlockState blockState3 = world.getBlockState(offsetUp);
            if (blockState3.getBlock() != this) {
                world.setBlockToAir(blockToAir);
            }
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetDown())) {
                world.setBlockToAir(blockToAir);
                if (blockState3.getBlock() == this) {
                    world.setBlockToAir(offsetUp);
                }
            }
            if (!world.isRemote) {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (blockState.getValue(BlockDoor.HALF_PROP) == EnumDoorHalf.UPPER) ? null : this.func_176509_j();
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return blockPos.getY() < 255 && (World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) && super.canPlaceBlockAt(world, blockPos) && super.canPlaceBlockAt(world, blockPos.offsetUp()));
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    public static int func_176515_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final int metaFromState = blockState.getBlock().getMetaFromState(blockState);
        final boolean func_176518_i = func_176518_i(metaFromState);
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offsetDown());
        final int metaFromState2 = blockState2.getBlock().getMetaFromState(blockState2);
        final int n = func_176518_i ? metaFromState2 : metaFromState;
        final IBlockState blockState3 = blockAccess.getBlockState(blockPos.offsetUp());
        final int metaFromState3 = blockState3.getBlock().getMetaFromState(blockState3);
        final int n2 = func_176518_i ? metaFromState : metaFromState3;
        return func_176510_b(n) | (func_176518_i ? 8 : 0) | (((n2 & 0x1) != 0x0) ? 16 : 0) | (((n2 & 0x2) != 0x0) ? 32 : 0);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return this.func_176509_j();
    }
    
    private Item func_176509_j() {
        return (this == Blocks.iron_door) ? Items.iron_door : ((this == Blocks.spruce_door) ? Items.spruce_door : ((this == Blocks.birch_door) ? Items.birch_door : ((this == Blocks.jungle_door) ? Items.jungle_door : ((this == Blocks.acacia_door) ? Items.acacia_door : ((this == Blocks.dark_oak_door) ? Items.dark_oak_door : Items.oak_door)))));
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        final BlockPos offsetDown = blockPos.offsetDown();
        if (entityPlayer.capabilities.isCreativeMode && blockState.getValue(BlockDoor.HALF_PROP) == EnumDoorHalf.UPPER && world.getBlockState(offsetDown).getBlock() == this) {
            world.setBlockToAir(offsetDown);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockState.getValue(BlockDoor.HALF_PROP) == EnumDoorHalf.LOWER) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offsetUp());
            if (blockState2.getBlock() == this) {
                blockState = blockState.withProperty(BlockDoor.HINGEPOSITION_PROP, blockState2.getValue(BlockDoor.HINGEPOSITION_PROP)).withProperty(BlockDoor.POWERED_PROP, blockState2.getValue(BlockDoor.POWERED_PROP));
            }
        }
        else {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.offsetDown());
            if (blockState3.getBlock() == this) {
                blockState = blockState.withProperty(BlockDoor.FACING_PROP, blockState3.getValue(BlockDoor.FACING_PROP)).withProperty(BlockDoor.OPEN_PROP, blockState3.getValue(BlockDoor.OPEN_PROP));
            }
        }
        return blockState;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return ((n & 0x8) > 0) ? this.getDefaultState().withProperty(BlockDoor.HALF_PROP, EnumDoorHalf.UPPER).withProperty(BlockDoor.HINGEPOSITION_PROP, ((n & 0x1) > 0) ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED_PROP, (n & 0x2) > 0) : this.getDefaultState().withProperty(BlockDoor.HALF_PROP, EnumDoorHalf.LOWER).withProperty(BlockDoor.FACING_PROP, EnumFacing.getHorizontal(n & 0x3).rotateYCCW()).withProperty(BlockDoor.OPEN_PROP, (n & 0x4) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockDoor.HALF_PROP) == EnumDoorHalf.UPPER) {
            if (blockState.getValue(BlockDoor.HINGEPOSITION_PROP) == EnumHingePosition.RIGHT) {}
            if (blockState.getValue(BlockDoor.POWERED_PROP)) {}
        }
        else {
            final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockDoor.FACING_PROP)).rotateY().getHorizontalIndex();
            if (blockState.getValue(BlockDoor.OPEN_PROP)) {}
        }
        return 8;
    }
    
    protected static int func_176510_b(final int n) {
        return n & 0x7;
    }
    
    public static boolean func_176514_f(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_176516_g(func_176515_e(blockAccess, blockPos));
    }
    
    public static EnumFacing func_176517_h(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_176511_f(func_176515_e(blockAccess, blockPos));
    }
    
    public static EnumFacing func_176511_f(final int n) {
        return EnumFacing.getHorizontal(n & 0x3).rotateYCCW();
    }
    
    protected static boolean func_176516_g(final int n) {
        return (n & 0x4) != 0x0;
    }
    
    protected static boolean func_176518_i(final int n) {
        return (n & 0x8) != 0x0;
    }
    
    protected static boolean func_176513_j(final int n) {
        return (n & 0x10) != 0x0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDoor.HALF_PROP, BlockDoor.FACING_PROP, BlockDoor.OPEN_PROP, BlockDoor.HINGEPOSITION_PROP, BlockDoor.POWERED_PROP });
    }
    
    public enum EnumDoorHalf implements IStringSerializable
    {
        UPPER("UPPER", 0, "UPPER", 0), 
        LOWER("LOWER", 1, "LOWER", 1);
        
        private static final EnumDoorHalf[] $VALUES;
        private static final String __OBFID;
        private static final EnumDoorHalf[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002124";
            ENUM$VALUES = new EnumDoorHalf[] { EnumDoorHalf.UPPER, EnumDoorHalf.LOWER };
            $VALUES = new EnumDoorHalf[] { EnumDoorHalf.UPPER, EnumDoorHalf.LOWER };
        }
        
        private EnumDoorHalf(final String s, final int n, final String s2, final int n2) {
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        @Override
        public String getName() {
            return (this == EnumDoorHalf.UPPER) ? "upper" : "lower";
        }
    }
    
    public enum EnumHingePosition implements IStringSerializable
    {
        LEFT("LEFT", 0, "LEFT", 0), 
        RIGHT("RIGHT", 1, "RIGHT", 1);
        
        private static final EnumHingePosition[] $VALUES;
        private static final String __OBFID;
        private static final EnumHingePosition[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002123";
            ENUM$VALUES = new EnumHingePosition[] { EnumHingePosition.LEFT, EnumHingePosition.RIGHT };
            $VALUES = new EnumHingePosition[] { EnumHingePosition.LEFT, EnumHingePosition.RIGHT };
        }
        
        private EnumHingePosition(final String s, final int n, final String s2, final int n2) {
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        @Override
        public String getName() {
            return (this == EnumHingePosition.LEFT) ? "left" : "right";
        }
    }
}
