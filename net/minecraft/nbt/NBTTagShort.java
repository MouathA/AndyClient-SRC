package net.minecraft.nbt;

import java.io.*;

public class NBTTagShort extends NBTPrimitive
{
    private short data;
    private static final String __OBFID;
    
    public NBTTagShort() {
    }
    
    public NBTTagShort(final short data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(16L);
        this.data = dataInput.readShort();
    }
    
    @Override
    public byte getId() {
        return 2;
    }
    
    @Override
    public String toString() {
        return this.data + "s";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagShort(this.data);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagShort)o).data;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public int getInt() {
        return this.data;
    }
    
    @Override
    public short getShort() {
        return this.data;
    }
    
    @Override
    public byte getByte() {
        return (byte)(this.data & 0xFF);
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    static {
        __OBFID = "CL_00001227";
    }
}
