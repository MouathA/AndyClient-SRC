package net.minecraft.world.border;

public enum EnumBorderStatus
{
    GROWING("GROWING", 0, "GROWING", 0, 4259712), 
    SHRINKING("SHRINKING", 1, "SHRINKING", 1, 16724016), 
    STATIONARY("STATIONARY", 2, "STATIONARY", 2, 2138367);
    
    private final int id;
    private static final EnumBorderStatus[] $VALUES;
    private static final String __OBFID;
    private static final EnumBorderStatus[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00002013";
        ENUM$VALUES = new EnumBorderStatus[] { EnumBorderStatus.GROWING, EnumBorderStatus.SHRINKING, EnumBorderStatus.STATIONARY };
        $VALUES = new EnumBorderStatus[] { EnumBorderStatus.GROWING, EnumBorderStatus.SHRINKING, EnumBorderStatus.STATIONARY };
    }
    
    private EnumBorderStatus(final String s, final int n, final String s2, final int n2, final int id) {
        this.id = id;
    }
    
    public int func_177766_a() {
        return this.id;
    }
}
