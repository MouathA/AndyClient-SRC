package net.minecraft.nbt;

import java.io.*;
import java.util.*;

public class NBTTagByteArray extends NBTBase
{
    private byte[] data;
    private static final String __OBFID;
    
    NBTTagByteArray() {
    }
    
    public NBTTagByteArray(final byte[] data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        dataOutput.write(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final int int1 = dataInput.readInt();
        nbtSizeTracker.read(8 * int1);
        dataInput.readFully(this.data = new byte[int1]);
    }
    
    @Override
    public byte getId() {
        return 7;
    }
    
    @Override
    public String toString() {
        return "[" + this.data.length + " bytes]";
    }
    
    @Override
    public NBTBase copy() {
        final byte[] array = new byte[this.data.length];
        System.arraycopy(this.data, 0, array, 0, this.data.length);
        return new NBTTagByteArray(array);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && Arrays.equals(this.data, ((NBTTagByteArray)o).data);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }
    
    public byte[] getByteArray() {
        return this.data;
    }
    
    static {
        __OBFID = "CL_00001213";
    }
}
