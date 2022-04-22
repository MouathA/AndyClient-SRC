package net.minecraft.world.pathfinder;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class SwimNodeProcessor extends NodeProcessor
{
    private static final String __OBFID;
    
    @Override
    public void func_176162_a(final IBlockAccess blockAccess, final Entity entity) {
        super.func_176162_a(blockAccess, entity);
    }
    
    @Override
    public void func_176163_a() {
        super.func_176163_a();
    }
    
    @Override
    public PathPoint func_176161_a(final Entity entity) {
        return this.func_176159_a(MathHelper.floor_double(entity.getEntityBoundingBox().minX), MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5), MathHelper.floor_double(entity.getEntityBoundingBox().minZ));
    }
    
    @Override
    public PathPoint func_176160_a(final Entity entity, final double n, final double n2, final double n3) {
        return this.func_176159_a(MathHelper.floor_double(n - entity.width / 2.0f), MathHelper.floor_double(n2 + 0.5), MathHelper.floor_double(n3 - entity.width / 2.0f));
    }
    
    @Override
    public int func_176164_a(final PathPoint[] array, final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final PathPoint func_176185_a = this.func_176185_a(entity, pathPoint.xCoord + enumFacing.getFrontOffsetX(), pathPoint.yCoord + enumFacing.getFrontOffsetY(), pathPoint.zCoord + enumFacing.getFrontOffsetZ());
            if (func_176185_a != null && !func_176185_a.visited && func_176185_a.distanceTo(pathPoint2) < n) {
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                array[n2] = func_176185_a;
            }
            int n4 = 0;
            ++n4;
        }
        return 0;
    }
    
    private PathPoint func_176185_a(final Entity entity, final int n, final int n2, final int n3) {
        return (this.func_176186_b(entity, n, n2, n3) == -1) ? this.func_176159_a(n, n2, n3) : null;
    }
    
    private int func_176186_b(final Entity entity, final int n, final int n2, final int n3) {
        for (int i = n; i < n + this.field_176168_c; ++i) {
            for (int j = n2; j < n2 + this.field_176165_d; ++j) {
                for (int k = n3; k < n3 + this.field_176166_e; ++k) {
                    if (this.field_176169_a.getBlockState(new BlockPos(i, j, k)).getBlock().getMaterial() != Material.water) {
                        return 0;
                    }
                }
            }
        }
        return -1;
    }
    
    static {
        __OBFID = "CL_00001966";
    }
}
