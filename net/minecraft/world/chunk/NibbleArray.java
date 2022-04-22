package net.minecraft.world.chunk;

public class NibbleArray
{
    private final byte[] data;
    private static final String __OBFID;
    
    public NibbleArray() {
        this.data = new byte[2048];
    }
    
    public NibbleArray(final byte[] data) {
        this.data = data;
        if (data.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + data.length);
        }
    }
    
    public int get(final int n, final int n2, final int n3) {
        return this.getFromIndex(this.getCoordinateIndex(n, n2, n3));
    }
    
    public void set(final int n, final int n2, final int n3, final int n4) {
        this.setIndex(this.getCoordinateIndex(n, n2, n3), n4);
    }
    
    private int getCoordinateIndex(final int n, final int n2, final int n3) {
        return n2 << 8 | n3 << 4 | n;
    }
    
    public int getFromIndex(final int n) {
        final int func_177478_c = this.func_177478_c(n);
        return this.func_177479_b(n) ? (this.data[func_177478_c] & 0xF) : (this.data[func_177478_c] >> 4 & 0xF);
    }
    
    public void setIndex(final int n, final int n2) {
        final int func_177478_c = this.func_177478_c(n);
        if (this.func_177479_b(n)) {
            this.data[func_177478_c] = (byte)((this.data[func_177478_c] & 0xF0) | (n2 & 0xF));
        }
        else {
            this.data[func_177478_c] = (byte)((this.data[func_177478_c] & 0xF) | (n2 & 0xF) << 4);
        }
    }
    
    private boolean func_177479_b(final int n) {
        return (n & 0x1) == 0x0;
    }
    
    private int func_177478_c(final int n) {
        return n >> 1;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    static {
        __OBFID = "CL_00000371";
    }
}
