package net.minecraft.nbt;

import java.io.*;

public class NBTTagInt extends NBTPrimitive
{
    private int data;
    private static final String __OBFID;
    
    NBTTagInt() {
    }
    
    public NBTTagInt(final int data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(32L);
        this.data = dataInput.readInt();
    }
    
    @Override
    public byte getId() {
        return 3;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(this.data).toString();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagInt(this.data);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagInt)o).data;
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
        return (short)(this.data & 0xFFFF);
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
        return (float)this.data;
    }
    
    static {
        __OBFID = "CL_00001223";
    }
}
