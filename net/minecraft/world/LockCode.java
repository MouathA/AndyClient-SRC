package net.minecraft.world;

import net.minecraft.nbt.*;

public class LockCode
{
    public static final LockCode EMPTY_CODE;
    private final String lock;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002260";
        EMPTY_CODE = new LockCode("");
    }
    
    public LockCode(final String lock) {
        this.lock = lock;
    }
    
    public boolean isEmpty() {
        return this.lock == null || this.lock.isEmpty();
    }
    
    public String getLock() {
        return this.lock;
    }
    
    public void toNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("Lock", this.lock);
    }
    
    public static LockCode fromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("Lock", 8)) {
            return new LockCode(nbtTagCompound.getString("Lock"));
        }
        return LockCode.EMPTY_CODE;
    }
}
