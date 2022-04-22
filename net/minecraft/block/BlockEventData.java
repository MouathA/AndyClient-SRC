package net.minecraft.block;

import net.minecraft.util.*;

public class BlockEventData
{
    private BlockPos field_180329_a;
    private Block field_151344_d;
    private int eventID;
    private int eventParameter;
    private static final String __OBFID;
    
    public BlockEventData(final BlockPos field_180329_a, final Block field_151344_d, final int eventID, final int eventParameter) {
        this.field_180329_a = field_180329_a;
        this.eventID = eventID;
        this.eventParameter = eventParameter;
        this.field_151344_d = field_151344_d;
    }
    
    public BlockPos func_180328_a() {
        return this.field_180329_a;
    }
    
    public int getEventID() {
        return this.eventID;
    }
    
    public int getEventParameter() {
        return this.eventParameter;
    }
    
    public Block getBlock() {
        return this.field_151344_d;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BlockEventData)) {
            return false;
        }
        final BlockEventData blockEventData = (BlockEventData)o;
        return this.field_180329_a.equals(blockEventData.field_180329_a) && this.eventID == blockEventData.eventID && this.eventParameter == blockEventData.eventParameter && this.field_151344_d == blockEventData.field_151344_d;
    }
    
    @Override
    public String toString() {
        return "TE(" + this.field_180329_a + ")," + this.eventID + "," + this.eventParameter + "," + this.field_151344_d;
    }
    
    static {
        __OBFID = "CL_00000131";
    }
}
