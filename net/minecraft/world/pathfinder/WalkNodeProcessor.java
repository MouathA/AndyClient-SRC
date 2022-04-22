package net.minecraft.world.pathfinder;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WalkNodeProcessor extends NodeProcessor
{
    private boolean field_176180_f;
    private boolean field_176181_g;
    private boolean field_176183_h;
    private boolean field_176184_i;
    private boolean field_176182_j;
    private static final String __OBFID;
    
    @Override
    public void func_176162_a(final IBlockAccess blockAccess, final Entity entity) {
        super.func_176162_a(blockAccess, entity);
        this.field_176182_j = this.field_176183_h;
    }
    
    @Override
    public void func_176163_a() {
        super.func_176163_a();
        this.field_176183_h = this.field_176182_j;
    }
    
    @Override
    public PathPoint func_176161_a(final Entity entity) {
        int floor_double;
        if (this.field_176184_i && entity.isInWater()) {
            floor_double = (int)entity.getEntityBoundingBox().minY;
            for (Block block = this.field_176169_a.getBlockState(new BlockPos(MathHelper.floor_double(entity.posX), floor_double, MathHelper.floor_double(entity.posZ))).getBlock(); block == Blocks.flowing_water || block == Blocks.water; block = this.field_176169_a.getBlockState(new BlockPos(MathHelper.floor_double(entity.posX), floor_double, MathHelper.floor_double(entity.posZ))).getBlock()) {
                ++floor_double;
            }
            this.field_176183_h = false;
        }
        else {
            floor_double = MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5);
        }
        return this.func_176159_a(MathHelper.floor_double(entity.getEntityBoundingBox().minX), floor_double, MathHelper.floor_double(entity.getEntityBoundingBox().minZ));
    }
    
    @Override
    public PathPoint func_176160_a(final Entity entity, final double n, final double n2, final double n3) {
        return this.func_176159_a(MathHelper.floor_double(n - entity.width / 2.0f), MathHelper.floor_double(n2), MathHelper.floor_double(n3 - entity.width / 2.0f));
    }
    
    @Override
    public int func_176164_a(final PathPoint[] array, final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        if (this.func_176177_a(entity, pathPoint.xCoord, pathPoint.yCoord + 1, pathPoint.zCoord) == 1) {}
        final PathPoint func_176171_a = this.func_176171_a(entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord + 1, 1);
        final PathPoint func_176171_a2 = this.func_176171_a(entity, pathPoint.xCoord - 1, pathPoint.yCoord, pathPoint.zCoord, 1);
        final PathPoint func_176171_a3 = this.func_176171_a(entity, pathPoint.xCoord + 1, pathPoint.yCoord, pathPoint.zCoord, 1);
        final PathPoint func_176171_a4 = this.func_176171_a(entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord - 1, 1);
        int n3 = 0;
        if (func_176171_a != null && !func_176171_a.visited && func_176171_a.distanceTo(pathPoint2) < n) {
            final int n2 = 0;
            ++n3;
            array[n2] = func_176171_a;
        }
        if (func_176171_a2 != null && !func_176171_a2.visited && func_176171_a2.distanceTo(pathPoint2) < n) {
            final int n4 = 0;
            ++n3;
            array[n4] = func_176171_a2;
        }
        if (func_176171_a3 != null && !func_176171_a3.visited && func_176171_a3.distanceTo(pathPoint2) < n) {
            final int n5 = 0;
            ++n3;
            array[n5] = func_176171_a3;
        }
        if (func_176171_a4 != null && !func_176171_a4.visited && func_176171_a4.distanceTo(pathPoint2) < n) {
            final int n6 = 0;
            ++n3;
            array[n6] = func_176171_a4;
        }
        return 0;
    }
    
    private PathPoint func_176171_a(final Entity entity, final int n, int i, final int n2, final int n3) {
        PathPoint pathPoint = null;
        final int func_176177_a = this.func_176177_a(entity, n, i, n2);
        if (func_176177_a == 2) {
            return this.func_176159_a(n, i, n2);
        }
        if (func_176177_a == 1) {
            pathPoint = this.func_176159_a(n, i, n2);
        }
        if (pathPoint == null && n3 > 0 && func_176177_a != -3 && func_176177_a != -4 && this.func_176177_a(entity, n, i + n3, n2) == 1) {
            pathPoint = this.func_176159_a(n, i + n3, n2);
            i += n3;
        }
        if (pathPoint != null) {
            while (i > 0) {
                this.func_176177_a(entity, n, i - 1, n2);
                if (this.field_176183_h && 0 == -1) {
                    return null;
                }
                if (false != true) {
                    break;
                }
                final int n4 = 0;
                int n5 = 0;
                ++n5;
                if (n4 >= entity.getMaxFallHeight()) {
                    return null;
                }
                if (--i <= 0) {
                    return null;
                }
                pathPoint = this.func_176159_a(n, i, n2);
            }
            if (0 == -2) {
                return null;
            }
        }
        return pathPoint;
    }
    
    private int func_176177_a(final Entity entity, final int n, final int n2, final int n3) {
        return func_176170_a(this.field_176169_a, entity, n, n2, n3, this.field_176168_c, this.field_176165_d, this.field_176166_e, this.field_176183_h, this.field_176181_g, this.field_176180_f);
    }
    
    public static int func_176170_a(final IBlockAccess blockAccess, final Entity entity, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final boolean b2, final boolean b3) {
        final BlockPos blockPos = new BlockPos(entity);
        for (int i = n; i < n + n4; ++i) {
            for (int j = n2; j < n2 + n5; ++j) {
                for (int k = n3; k < n3 + n6; ++k) {
                    final BlockPos blockPos2 = new BlockPos(i, j, k);
                    final Block block = blockAccess.getBlockState(blockPos2).getBlock();
                    if (block.getMaterial() != Material.air) {
                        if (block != Blocks.trapdoor && block != Blocks.iron_trapdoor) {
                            if (block != Blocks.flowing_water && block != Blocks.water) {
                                if (!b3 && block instanceof BlockDoor && block.getMaterial() == Material.wood) {
                                    return 0;
                                }
                            }
                            else if (b) {
                                return -1;
                            }
                        }
                        if (entity.worldObj.getBlockState(blockPos2).getBlock() instanceof BlockRailBase) {
                            if (!(entity.worldObj.getBlockState(blockPos).getBlock() instanceof BlockRailBase) && !(entity.worldObj.getBlockState(blockPos.offsetDown()).getBlock() instanceof BlockRailBase)) {
                                return -3;
                            }
                        }
                        else if (!block.isPassable(blockAccess, blockPos2) && (!b2 || !(block instanceof BlockDoor) || block.getMaterial() != Material.wood)) {
                            if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
                                return -3;
                            }
                            if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor) {
                                return -4;
                            }
                            if (block.getMaterial() != Material.lava) {
                                return 0;
                            }
                            if (!entity.func_180799_ab()) {
                                return -2;
                            }
                        }
                    }
                }
            }
        }
        return true ? 2 : 1;
    }
    
    public void func_176175_a(final boolean field_176180_f) {
        this.field_176180_f = field_176180_f;
    }
    
    public void func_176172_b(final boolean field_176181_g) {
        this.field_176181_g = field_176181_g;
    }
    
    public void func_176176_c(final boolean field_176183_h) {
        this.field_176183_h = field_176183_h;
    }
    
    public void func_176178_d(final boolean field_176184_i) {
        this.field_176184_i = field_176184_i;
    }
    
    public boolean func_176179_b() {
        return this.field_176180_f;
    }
    
    public boolean func_176174_d() {
        return this.field_176184_i;
    }
    
    public boolean func_176173_e() {
        return this.field_176183_h;
    }
    
    static {
        __OBFID = "CL_00001965";
    }
}
