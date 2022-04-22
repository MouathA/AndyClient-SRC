package net.minecraft.world.pathfinder;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.pathfinding.*;

public abstract class NodeProcessor
{
    protected IBlockAccess field_176169_a;
    protected IntHashMap field_176167_b;
    protected int field_176168_c;
    protected int field_176165_d;
    protected int field_176166_e;
    private static final String __OBFID;
    
    public NodeProcessor() {
        this.field_176167_b = new IntHashMap();
    }
    
    public void func_176162_a(final IBlockAccess field_176169_a, final Entity entity) {
        this.field_176169_a = field_176169_a;
        this.field_176167_b.clearMap();
        this.field_176168_c = MathHelper.floor_float(entity.width + 1.0f);
        this.field_176165_d = MathHelper.floor_float(entity.height + 1.0f);
        this.field_176166_e = MathHelper.floor_float(entity.width + 1.0f);
    }
    
    public void func_176163_a() {
    }
    
    protected PathPoint func_176159_a(final int n, final int n2, final int n3) {
        final int hash = PathPoint.makeHash(n, n2, n3);
        PathPoint pathPoint = (PathPoint)this.field_176167_b.lookup(hash);
        if (pathPoint == null) {
            pathPoint = new PathPoint(n, n2, n3);
            this.field_176167_b.addKey(hash, pathPoint);
        }
        return pathPoint;
    }
    
    public abstract PathPoint func_176161_a(final Entity p0);
    
    public abstract PathPoint func_176160_a(final Entity p0, final double p1, final double p2, final double p3);
    
    public abstract int func_176164_a(final PathPoint[] p0, final Entity p1, final PathPoint p2, final PathPoint p3, final float p4);
    
    static {
        __OBFID = "CL_00001967";
    }
}
