package net.minecraft.nbt;

import java.io.*;

public class NBTTagByte extends NBTPrimitive
{
    private byte data;
    private static final String __OBFID;
    
    NBTTagByte() {
    }
    
    public NBTTagByte(final byte data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(8L);
        this.data = dataInput.readByte();
    }
    
    @Override
    public byte getId() {
        return 1;
    }
    
    @Override
    public String toString() {
        return this.data + "b";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.data);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagByte)o).data;
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
        return this.data;
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
        __OBFID = "CL_00001214";
    }
}
