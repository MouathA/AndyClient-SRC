package net.minecraft.nbt;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public class NBTTagList extends NBTBase
{
    private static final Logger LOGGER;
    private List tagList;
    private byte tagType;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001224";
        LOGGER = LogManager.getLogger();
    }
    
    public NBTTagList() {
        this.tagList = Lists.newArrayList();
        this.tagType = 0;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.get(0).getId();
        }
        else {
            this.tagType = 0;
        }
        dataOutput.writeByte(this.tagType);
        dataOutput.writeInt(this.tagList.size());
        while (0 < this.tagList.size()) {
            this.tagList.get(0).write(dataOutput);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        if (n > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        nbtSizeTracker.read(8L);
        this.tagType = dataInput.readByte();
        final int int1 = dataInput.readInt();
        this.tagList = Lists.newArrayList();
        while (0 < int1) {
            final NBTBase newByType = NBTBase.createNewByType(this.tagType);
            newByType.read(dataInput, n + 1, nbtSizeTracker);
            this.tagList.add(newByType);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public byte getId() {
        return 9;
    }
    
    @Override
    public String toString() {
        String string = "[";
        final Iterator<NBTBase> iterator = this.tagList.iterator();
        while (iterator.hasNext()) {
            string = String.valueOf(string) + 0 + ':' + iterator.next() + ',';
            int n = 0;
            ++n;
        }
        return String.valueOf(string) + "]";
    }
    
    public void appendTag(final NBTBase nbtBase) {
        if (this.tagType == 0) {
            this.tagType = nbtBase.getId();
        }
        else if (this.tagType != nbtBase.getId()) {
            NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
            return;
        }
        this.tagList.add(nbtBase);
    }
    
    public void set(final int n, final NBTBase nbtBase) {
        if (n >= 0 && n < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = nbtBase.getId();
            }
            else if (this.tagType != nbtBase.getId()) {
                NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
                return;
            }
            this.tagList.set(n, nbtBase);
        }
        else {
            NBTTagList.LOGGER.warn("index out of bounds to set tag in tag list");
        }
    }
    
    public NBTBase removeTag(final int n) {
        return this.tagList.remove(n);
    }
    
    @Override
    public boolean hasNoTags() {
        return this.tagList.isEmpty();
    }
    
    public NBTTagCompound getCompoundTagAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (NBTTagCompound)((nbtBase.getId() == 10) ? nbtBase : new NBTTagCompound());
        }
        return new NBTTagCompound();
    }
    
    public int[] getIntArray(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 11) ? ((NBTTagIntArray)nbtBase).getIntArray() : new int[0];
        }
        return new int[0];
    }
    
    public double getDouble(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 6) ? ((NBTTagDouble)nbtBase).getDouble() : 0.0;
        }
        return 0.0;
    }
    
    public float getFloat(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 5) ? ((NBTTagFloat)nbtBase).getFloat() : 0.0f;
        }
        return 0.0f;
    }
    
    public String getStringTagAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 8) ? nbtBase.getString() : nbtBase.toString();
        }
        return "";
    }
    
    public NBTBase get(final int n) {
        return (n >= 0 && n < this.tagList.size()) ? this.tagList.get(n) : new NBTTagEnd();
    }
    
    public int tagCount() {
        return this.tagList.size();
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagList list = new NBTTagList();
        list.tagType = this.tagType;
        final Iterator<NBTBase> iterator = this.tagList.iterator();
        while (iterator.hasNext()) {
            list.tagList.add(iterator.next().copy());
        }
        return list;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (super.equals(o)) {
            final NBTTagList list = (NBTTagList)o;
            if (this.tagType == list.tagType) {
                return this.tagList.equals(list.tagList);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }
    
    public int getTagType() {
        return this.tagType;
    }
}
