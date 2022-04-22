package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public abstract class BlockLog extends BlockRotatedPillar
{
    public static final PropertyEnum AXIS_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000266";
        AXIS_PROP = PropertyEnum.create("axis", EnumAxis.class);
    }
    
    public BlockLog() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setStepSound(BlockLog.soundTypeWood);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (world.isAreaLoaded(blockPos.add(-5, -5, -5), blockPos.add(5, 5, 5))) {
            for (final BlockPos blockPos2 : BlockPos.getAllInBox(blockPos.add(-4, -4, -4), blockPos.add(4, 4, 4))) {
                final IBlockState blockState2 = world.getBlockState(blockPos2);
                if (blockState2.getBlock().getMaterial() == Material.leaves && !(boolean)blockState2.getValue(BlockLeaves.field_176236_b)) {
                    world.setBlockState(blockPos2, blockState2.withProperty(BlockLeaves.field_176236_b, true), 4);
                }
            }
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockLog.AXIS_PROP, EnumAxis.func_176870_a(enumFacing.getAxis()));
    }
    
    public enum EnumAxis implements IStringSerializable
    {
        X("X", 0, "X", 0, "x"), 
        Y("Y", 1, "Y", 1, "y"), 
        Z("Z", 2, "Z", 2, "z"), 
        NONE("NONE", 3, "NONE", 3, "none");
        
        private final String field_176874_e;
        private static final EnumAxis[] $VALUES;
        private static final String __OBFID;
        private static final EnumAxis[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002100";
            ENUM$VALUES = new EnumAxis[] { EnumAxis.X, EnumAxis.Y, EnumAxis.Z, EnumAxis.NONE };
            $VALUES = new EnumAxis[] { EnumAxis.X, EnumAxis.Y, EnumAxis.Z, EnumAxis.NONE };
        }
        
        private EnumAxis(final String s, final int n, final String s2, final int n2, final String field_176874_e) {
            this.field_176874_e = field_176874_e;
        }
        
        @Override
        public String toString() {
            return this.field_176874_e;
        }
        
        public static EnumAxis func_176870_a(final EnumFacing.Axis axis) {
            switch (SwitchAxis.field_180167_a[axis.ordinal()]) {
                case 1: {
                    return EnumAxis.X;
                }
                case 2: {
                    return EnumAxis.Y;
                }
                case 3: {
                    return EnumAxis.Z;
                }
                default: {
                    return EnumAxis.NONE;
                }
            }
        }
        
        @Override
        public String getName() {
            return this.field_176874_e;
        }
    }
    
    static final class SwitchAxis
    {
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002101";
            (SwitchAxis.field_180167_a = new int[EnumFacing.Axis.values().length])[EnumFacing.Axis.X.ordinal()] = 1;
            SwitchAxis.field_180167_a[EnumFacing.Axis.Y.ordinal()] = 2;
            SwitchAxis.field_180167_a[EnumFacing.Axis.Z.ordinal()] = 3;
        }
    }
}
