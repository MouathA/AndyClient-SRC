package net.minecraft.nbt;

import java.io.*;
import net.minecraft.util.*;

public class NBTTagDouble extends NBTPrimitive
{
    private double data;
    private static final String __OBFID;
    
    NBTTagDouble() {
    }
    
    public NBTTagDouble(final double data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(64L);
        this.data = dataInput.readDouble();
    }
    
    @Override
    public byte getId() {
        return 6;
    }
    
    @Override
    public String toString() {
        return this.data + "d";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagDouble(this.data);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagDouble)o).data;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
    
    @Override
    public long getLong() {
        return (long)Math.floor(this.data);
    }
    
    @Override
    public int getInt() {
        return MathHelper.floor_double(this.data);
    }
    
    @Override
    public short getShort() {
        return (short)(MathHelper.floor_double(this.data) & 0xFFFF);
    }
    
    @Override
    public byte getByte() {
        return (byte)(MathHelper.floor_double(this.data) & 0xFF);
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
        __OBFID = "CL_00001218";
    }
}
