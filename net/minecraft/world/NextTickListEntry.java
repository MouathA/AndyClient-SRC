package net.minecraft.world;

import net.minecraft.block.*;
import net.minecraft.util.*;

public class NextTickListEntry implements Comparable
{
    private static long nextTickEntryID;
    private final Block field_151352_g;
    public final BlockPos field_180282_a;
    public long scheduledTime;
    public int priority;
    private long tickEntryID;
    private static final String __OBFID;
    
    public NextTickListEntry(final BlockPos field_180282_a, final Block field_151352_g) {
        this.tickEntryID = NextTickListEntry.nextTickEntryID++;
        this.field_180282_a = field_180282_a;
        this.field_151352_g = field_151352_g;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        return this.field_180282_a.equals(nextTickListEntry.field_180282_a) && Block.isEqualTo(this.field_151352_g, nextTickListEntry.field_151352_g);
    }
    
    @Override
    public int hashCode() {
        return this.field_180282_a.hashCode();
    }
    
    public NextTickListEntry setScheduledTime(final long scheduledTime) {
        this.scheduledTime = scheduledTime;
        return this;
    }
    
    public void setPriority(final int priority) {
        this.priority = priority;
    }
    
    public int compareTo(final NextTickListEntry nextTickListEntry) {
        return (this.scheduledTime < nextTickListEntry.scheduledTime) ? -1 : ((this.scheduledTime > nextTickListEntry.scheduledTime) ? 1 : ((this.priority != nextTickListEntry.priority) ? (this.priority - nextTickListEntry.priority) : ((this.tickEntryID < nextTickListEntry.tickEntryID) ? -1 : ((this.tickEntryID > nextTickListEntry.tickEntryID) ? 1 : 0))));
    }
    
    @Override
    public String toString() {
        return String.valueOf(Block.getIdFromBlock(this.field_151352_g)) + ": " + this.field_180282_a + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
    }
    
    public Block func_151351_a() {
        return this.field_151352_g;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((NextTickListEntry)o);
    }
    
    static {
        __OBFID = "CL_00000156";
    }
}
