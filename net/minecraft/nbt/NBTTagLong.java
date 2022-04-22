package net.minecraft.nbt;

import java.io.*;

public class NBTTagLong extends NBTPrimitive
{
    private long data;
    private static final String __OBFID;
    
    NBTTagLong() {
    }
    
    public NBTTagLong(final long data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(64L);
        this.data = dataInput.readLong();
    }
    
    @Override
    public byte getId() {
        return 4;
    }
    
    @Override
    public String toString() {
        return this.data + "L";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagLong(this.data);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagLong)o).data;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public int getInt() {
        return (int)(this.data & -1L);
    }
    
    @Override
    public short getShort() {
        return (short)(this.data & 0xFFFFL);
    }
    
    @Override
    public byte getByte() {
        return (byte)(this.data & 0xFFL);
    }
    
    @Override
    public double getDouble() {
        return (double)this.data;
    }
    
    @Override
    public float getFloat() {
        return (float)this.data;
    }
    
    static {
        __OBFID = "CL_00001225";
    }
}
