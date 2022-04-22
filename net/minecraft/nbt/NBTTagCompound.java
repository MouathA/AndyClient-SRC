package net.minecraft.nbt;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;

public class NBTTagCompound extends NBTBase
{
    private static final Logger logger;
    private Map tagMap;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001215";
        logger = LogManager.getLogger();
    }
    
    public NBTTagCompound() {
        this.tagMap = Maps.newHashMap();
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        for (final String s : this.tagMap.keySet()) {
            writeEntry(s, (NBTBase)this.tagMap.get(s), dataOutput);
        }
        dataOutput.writeByte(0);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        if (n > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.tagMap.clear();
        byte type;
        while ((type = readType(dataInput, nbtSizeTracker)) != 0) {
            final String key = readKey(dataInput, nbtSizeTracker);
            nbtSizeTracker.read(16 * key.length());
            this.tagMap.put(key, readNBT(type, key, dataInput, n + 1, nbtSizeTracker));
        }
    }
    
    public Set getKeySet() {
        return this.tagMap.keySet();
    }
    
    @Override
    public byte getId() {
        return 10;
    }
    
    public void setTag(final String s, final NBTBase nbtBase) {
        this.tagMap.put(s, nbtBase);
    }
    
    public void setByte(final String s, final byte b) {
        this.tagMap.put(s, new NBTTagByte(b));
    }
    
    public void setShort(final String s, final short n) {
        this.tagMap.put(s, new NBTTagShort(n));
    }
    
    public void setInteger(final String s, final int n) {
        this.tagMap.put(s, new NBTTagInt(n));
    }
    
    public void setLong(final String s, final long n) {
        this.tagMap.put(s, new NBTTagLong(n));
    }
    
    public void setFloat(final String s, final float n) {
        this.tagMap.put(s, new NBTTagFloat(n));
    }
    
    public void setDouble(final String s, final double n) {
        this.tagMap.put(s, new NBTTagDouble(n));
    }
    
    public void setString(final String s, final String s2) {
        this.tagMap.put(s, new NBTTagString(s2));
    }
    
    public void setByteArray(final String s, final byte[] array) {
        this.tagMap.put(s, new NBTTagByteArray(array));
    }
    
    public void setIntArray(final String s, final int[] array) {
        this.tagMap.put(s, new NBTTagIntArray(array));
    }
    
    public void setBoolean(final String s, final boolean b) {
        this.setByte(s, (byte)(b ? 1 : 0));
    }
    
    public NBTBase getTag(final String s) {
        return this.tagMap.get(s);
    }
    
    public byte getTagType(final String s) {
        final NBTBase nbtBase = this.tagMap.get(s);
        return (byte)((nbtBase != null) ? nbtBase.getId() : 0);
    }
    
    public boolean hasKey(final String s) {
        return this.tagMap.containsKey(s);
    }
    
    public boolean hasKey(final String s, final int n) {
        final byte tagType = this.getTagType(s);
        return tagType == n || (n == 99 && (tagType == 1 || tagType == 2 || tagType == 3 || tagType == 4 || tagType == 5 || tagType == 6));
    }
    
    public byte getByte(final String s) {
        return (byte)(this.hasKey(s, 99) ? this.tagMap.get(s).getByte() : 0);
    }
    
    public short getShort(final String s) {
        return (short)(this.hasKey(s, 99) ? this.tagMap.get(s).getShort() : 0);
    }
    
    public int getInteger(final String s) {
        return this.hasKey(s, 99) ? this.tagMap.get(s).getInt() : 0;
    }
    
    public long getLong(final String s) {
        return this.hasKey(s, 99) ? this.tagMap.get(s).getLong() : 0L;
    }
    
    public float getFloat(final String s) {
        return this.hasKey(s, 99) ? this.tagMap.get(s).getFloat() : 0.0f;
    }
    
    public double getDouble(final String s) {
        return this.hasKey(s, 99) ? this.tagMap.get(s).getDouble() : 0.0;
    }
    
    public String getString(final String s) {
        return this.hasKey(s, 8) ? this.tagMap.get(s).getString() : "";
    }
    
    public byte[] getByteArray(final String s) {
        return this.hasKey(s, 7) ? this.tagMap.get(s).getByteArray() : new byte[0];
    }
    
    public int[] getIntArray(final String s) {
        return this.hasKey(s, 11) ? this.tagMap.get(s).getIntArray() : new int[0];
    }
    
    public NBTTagCompound getCompoundTag(final String s) {
        return this.hasKey(s, 10) ? this.tagMap.get(s) : new NBTTagCompound();
    }
    
    public NBTTagList getTagList(final String s, final int n) {
        if (this.getTagType(s) != 9) {
            return new NBTTagList();
        }
        final NBTTagList list = this.tagMap.get(s);
        return (list.tagCount() > 0 && list.getTagType() != n) ? new NBTTagList() : list;
    }
    
    public boolean getBoolean(final String s) {
        return this.getByte(s) != 0;
    }
    
    public void removeTag(final String s) {
        this.tagMap.remove(s);
    }
    
    @Override
    public String toString() {
        String string = "{";
        for (final String s : this.tagMap.keySet()) {
            string = String.valueOf(string) + s + ':' + this.tagMap.get(s) + ',';
        }
        return String.valueOf(string) + "}";
    }
    
    @Override
    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }
    
    private CrashReport createCrashReport(final String s, final int n, final ClassCastException ex) {
        final CrashReport crashReport = CrashReport.makeCrashReport(ex, "Reading NBT data");
        final CrashReportCategory categoryDepth = crashReport.makeCategoryDepth("Corrupt NBT tag", 1);
        categoryDepth.addCrashSectionCallable("Tag type found", new Callable(s) {
            private static final String __OBFID;
            final NBTTagCompound this$0;
            private final String val$key;
            
            @Override
            public String call() {
                return NBTBase.NBT_TYPES[NBTTagCompound.access$0(this.this$0).get(this.val$key).getId()];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001216";
            }
        });
        categoryDepth.addCrashSectionCallable("Tag type expected", new Callable(n) {
            private static final String __OBFID;
            final NBTTagCompound this$0;
            private final int val$expectedType;
            
            @Override
            public String call() {
                return NBTBase.NBT_TYPES[this.val$expectedType];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001217";
            }
        });
        categoryDepth.addCrashSection("Tag name", s);
        return crashReport;
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        for (final String s : this.tagMap.keySet()) {
            nbtTagCompound.setTag(s, ((NBTBase)this.tagMap.get(s)).copy());
        }
        return nbtTagCompound;
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.tagMap.entrySet().equals(((NBTTagCompound)o).tagMap.entrySet());
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }
    
    private static void writeEntry(final String s, final NBTBase nbtBase, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(nbtBase.getId());
        if (nbtBase.getId() != 0) {
            dataOutput.writeUTF(s);
            nbtBase.write(dataOutput);
        }
    }
    
    private static byte readType(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        return dataInput.readByte();
    }
    
    private static String readKey(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        return dataInput.readUTF();
    }
    
    static NBTBase readNBT(final byte b, final String s, final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) {
        final NBTBase newByType = NBTBase.createNewByType(b);
        newByType.read(dataInput, n, nbtSizeTracker);
        return newByType;
    }
    
    public void merge(final NBTTagCompound nbtTagCompound) {
        for (final String s : nbtTagCompound.tagMap.keySet()) {
            final NBTBase nbtBase = nbtTagCompound.tagMap.get(s);
            if (nbtBase.getId() == 10) {
                if (this.hasKey(s, 10)) {
                    this.getCompoundTag(s).merge((NBTTagCompound)nbtBase);
                }
                else {
                    this.setTag(s, nbtBase.copy());
                }
            }
            else {
                this.setTag(s, nbtBase.copy());
            }
        }
    }
    
    static Map access$0(final NBTTagCompound nbtTagCompound) {
        return nbtTagCompound.tagMap;
    }
}
