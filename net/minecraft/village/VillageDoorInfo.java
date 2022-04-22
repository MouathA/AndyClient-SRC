package net.minecraft.village;

import net.minecraft.util.*;

public class VillageDoorInfo
{
    private final BlockPos field_179859_a;
    private final BlockPos field_179857_b;
    private final EnumFacing field_179858_c;
    private int lastActivityTimestamp;
    private boolean isDetachedFromVillageFlag;
    private int doorOpeningRestrictionCounter;
    private static final String __OBFID;
    
    public VillageDoorInfo(final BlockPos blockPos, final int n, final int n2, final int n3) {
        this(blockPos, func_179854_a(n, n2), n3);
    }
    
    private static EnumFacing func_179854_a(final int n, final int n2) {
        return (n < 0) ? EnumFacing.WEST : ((n > 0) ? EnumFacing.EAST : ((n2 < 0) ? EnumFacing.NORTH : EnumFacing.SOUTH));
    }
    
    public VillageDoorInfo(final BlockPos field_179859_a, final EnumFacing field_179858_c, final int lastActivityTimestamp) {
        this.field_179859_a = field_179859_a;
        this.field_179858_c = field_179858_c;
        this.field_179857_b = field_179859_a.offset(field_179858_c, 2);
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
    
    public int getDistanceSquared(final int n, final int n2, final int n3) {
        return (int)this.field_179859_a.distanceSq(n, n2, n3);
    }
    
    public int func_179848_a(final BlockPos blockPos) {
        return (int)blockPos.distanceSq(this.func_179852_d());
    }
    
    public int func_179846_b(final BlockPos blockPos) {
        return (int)this.field_179857_b.distanceSq(blockPos);
    }
    
    public boolean func_179850_c(final BlockPos blockPos) {
        return (blockPos.getX() - this.field_179859_a.getX()) * this.field_179858_c.getFrontOffsetX() + (blockPos.getZ() - this.field_179859_a.getY()) * this.field_179858_c.getFrontOffsetZ() >= 0;
    }
    
    public void resetDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter = 0;
    }
    
    public void incrementDoorOpeningRestrictionCounter() {
        ++this.doorOpeningRestrictionCounter;
    }
    
    public int getDoorOpeningRestrictionCounter() {
        return this.doorOpeningRestrictionCounter;
    }
    
    public BlockPos func_179852_d() {
        return this.field_179859_a;
    }
    
    public BlockPos func_179856_e() {
        return this.field_179857_b;
    }
    
    public int func_179847_f() {
        return this.field_179858_c.getFrontOffsetX() * 2;
    }
    
    public int func_179855_g() {
        return this.field_179858_c.getFrontOffsetZ() * 2;
    }
    
    public int getInsidePosY() {
        return this.lastActivityTimestamp;
    }
    
    public void func_179849_a(final int lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
    
    public boolean func_179851_i() {
        return this.isDetachedFromVillageFlag;
    }
    
    public void func_179853_a(final boolean isDetachedFromVillageFlag) {
        this.isDetachedFromVillageFlag = isDetachedFromVillageFlag;
    }
    
    static {
        __OBFID = "CL_00001630";
    }
}
