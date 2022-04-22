package net.minecraft.nbt;

import java.io.*;
import net.minecraft.util.*;

public class NBTTagFloat extends NBTPrimitive
{
    private float data;
    private static final String __OBFID;
    
    NBTTagFloat() {
    }
    
    public NBTTagFloat(final float data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(32L);
        this.data = dataInput.readFloat();
    }
    
    @Override
    public byte getId() {
        return 5;
    }
    
    @Override
    public String toString() {
        return this.data + "f";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagFloat(this.data);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagFloat)o).data;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
    
    @Override
    public long getLong() {
        return (long)this.data;
    }
    
    @Override
    public int getInt() {
        return MathHelper.floor_float(this.data);
    }
    
    @Override
    public short getShort() {
        return (short)(MathHelper.floor_float(this.data) & 0xFFFF);
    }
    
    @Override
    public byte getByte() {
        return (byte)(MathHelper.floor_float(this.data) & 0xFF);
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
        __OBFID = "CL_00001220";
    }
}
