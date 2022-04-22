package net.minecraft.nbt;

import java.io.*;
import java.util.*;

public class NBTTagIntArray extends NBTBase
{
    private int[] intArray;
    private static final String __OBFID;
    
    NBTTagIntArray() {
    }
    
    public NBTTagIntArray(final int[] intArray) {
        this.intArray = intArray;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.intArray.length);
        while (0 < this.intArray.length) {
            dataOutput.writeInt(this.intArray[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final int int1 = dataInput.readInt();
        nbtSizeTracker.read(32 * int1);
        this.intArray = new int[int1];
        while (0 < int1) {
            this.intArray[0] = dataInput.readInt();
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public byte getId() {
        return 11;
    }
    
    @Override
    public String toString() {
        String string = "[";
        final int[] intArray = this.intArray;
        while (0 < intArray.length) {
            string = String.valueOf(string) + intArray[0] + ",";
            int n = 0;
            ++n;
        }
        return String.valueOf(string) + "]";
    }
    
    @Override
    public NBTBase copy() {
        final int[] array = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, array, 0, this.intArray.length);
        return new NBTTagIntArray(array);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && Arrays.equals(this.intArray, ((NBTTagIntArray)o).intArray);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }
    
    public int[] getIntArray() {
        return this.intArray;
    }
    
    static {
        __OBFID = "CL_00001221";
    }
}
