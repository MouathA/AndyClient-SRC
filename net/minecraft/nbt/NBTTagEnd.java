package net.minecraft.nbt;

import java.io.*;

public class NBTTagEnd extends NBTBase
{
    private static final String __OBFID;
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
    }
    
    @Override
    public byte getId() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "END";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
    
    static {
        __OBFID = "CL_00001219";
    }
}
