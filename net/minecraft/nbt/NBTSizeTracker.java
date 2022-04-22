package net.minecraft.nbt;

public class NBTSizeTracker
{
    public static final NBTSizeTracker INFINITE;
    private final long max;
    private long read;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001903";
        INFINITE = new NBTSizeTracker() {
            private static final String __OBFID;
            
            @Override
            public void read(final long n) {
            }
            
            static {
                __OBFID = "CL_00001902";
            }
        };
    }
    
    public NBTSizeTracker(final long max) {
        this.max = max;
    }
    
    public void read(final long n) {
        this.read += n / 8L;
        if (this.read > this.max) {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.read + "bytes where max allowed: " + this.max);
        }
    }
}
