package net.minecraft.entity;

import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.concurrent.locks.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.*;
import java.io.*;
import com.google.common.collect.*;
import java.util.*;

public class DataWatcher
{
    private final Entity owner;
    private boolean isBlank;
    private static final Map dataTypes;
    private final Map watchedObjects;
    private boolean objectChanged;
    private ReadWriteLock lock;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001559";
        (dataTypes = Maps.newHashMap()).put(Byte.class, 0);
        DataWatcher.dataTypes.put(Short.class, 1);
        DataWatcher.dataTypes.put(Integer.class, 2);
        DataWatcher.dataTypes.put(Float.class, 3);
        DataWatcher.dataTypes.put(String.class, 4);
        DataWatcher.dataTypes.put(ItemStack.class, 5);
        DataWatcher.dataTypes.put(BlockPos.class, 6);
        DataWatcher.dataTypes.put(Rotations.class, 7);
    }
    
    public DataWatcher(final Entity owner) {
        this.isBlank = true;
        this.watchedObjects = Maps.newHashMap();
        this.lock = new ReentrantReadWriteLock();
        this.owner = owner;
    }
    
    public void addObject(final int n, final Object o) {
        final Integer n2 = DataWatcher.dataTypes.get(o.getClass());
        if (n2 == null) {
            throw new IllegalArgumentException("Unknown data type: " + o.getClass());
        }
        if (n > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + n + "! (Max is " + 31 + ")");
        }
        if (this.watchedObjects.containsKey(n)) {
            throw new IllegalArgumentException("Duplicate id value for " + n + "!");
        }
        final WatchableObject watchableObject = new WatchableObject(n2, n, o);
        this.lock.writeLock().lock();
        this.watchedObjects.put(n, watchableObject);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }
    
    public void addObjectByDataType(final int n, final int n2) {
        final WatchableObject watchableObject = new WatchableObject(n2, n, null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(n, watchableObject);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }
    
    public byte getWatchableObjectByte(final int n) {
        return (byte)this.getWatchedObject(n).getObject();
    }
    
    public short getWatchableObjectShort(final int n) {
        return (short)this.getWatchedObject(n).getObject();
    }
    
    public int getWatchableObjectInt(final int n) {
        return (int)this.getWatchedObject(n).getObject();
    }
    
    public float getWatchableObjectFloat(final int n) {
        return (float)this.getWatchedObject(n).getObject();
    }
    
    public String getWatchableObjectString(final int n) {
        return (String)this.getWatchedObject(n).getObject();
    }
    
    public ItemStack getWatchableObjectItemStack(final int n) {
        return (ItemStack)this.getWatchedObject(n).getObject();
    }
    
    private WatchableObject getWatchedObject(final int n) {
        this.lock.readLock().lock();
        final WatchableObject watchableObject = this.watchedObjects.get(n);
        this.lock.readLock().unlock();
        return watchableObject;
    }
    
    public Rotations getWatchableObjectRotations(final int n) {
        return (Rotations)this.getWatchedObject(n).getObject();
    }
    
    public void updateObject(final int n, final Object object) {
        final WatchableObject watchedObject = this.getWatchedObject(n);
        if (ObjectUtils.notEqual(object, watchedObject.getObject())) {
            watchedObject.setObject(object);
            this.owner.func_145781_i(n);
            watchedObject.setWatched(true);
            this.objectChanged = true;
        }
    }
    
    public void setObjectWatched(final int n) {
        WatchableObject.access$0(this.getWatchedObject(n), true);
        this.objectChanged = true;
    }
    
    public boolean hasObjectChanged() {
        return this.objectChanged;
    }
    
    public static void writeWatchedListToPacketBuffer(final List list, final PacketBuffer packetBuffer) throws IOException {
        if (list != null) {
            final Iterator<WatchableObject> iterator = list.iterator();
            while (iterator.hasNext()) {
                writeWatchableObjectToPacketBuffer(packetBuffer, iterator.next());
            }
        }
        packetBuffer.writeByte(127);
    }
    
    public List getChanged() {
        ArrayList<WatchableObject> arrayList = null;
        if (this.objectChanged) {
            this.lock.readLock().lock();
            for (final WatchableObject watchableObject : this.watchedObjects.values()) {
                if (watchableObject.isWatched()) {
                    watchableObject.setWatched(false);
                    if (arrayList == null) {
                        arrayList = (ArrayList<WatchableObject>)Lists.newArrayList();
                    }
                    arrayList.add(watchableObject);
                }
            }
            this.lock.readLock().unlock();
        }
        this.objectChanged = false;
        return arrayList;
    }
    
    public void writeTo(final PacketBuffer packetBuffer) throws IOException {
        this.lock.readLock().lock();
        final Iterator<WatchableObject> iterator = this.watchedObjects.values().iterator();
        while (iterator.hasNext()) {
            writeWatchableObjectToPacketBuffer(packetBuffer, iterator.next());
        }
        this.lock.readLock().unlock();
        packetBuffer.writeByte(127);
    }
    
    public List getAllWatched() {
        ArrayList<WatchableObject> arrayList = null;
        this.lock.readLock().lock();
        for (final WatchableObject watchableObject : this.watchedObjects.values()) {
            if (arrayList == null) {
                arrayList = (ArrayList<WatchableObject>)Lists.newArrayList();
            }
            arrayList.add(watchableObject);
        }
        this.lock.readLock().unlock();
        return arrayList;
    }
    
    private static void writeWatchableObjectToPacketBuffer(final PacketBuffer packetBuffer, final WatchableObject watchableObject) throws IOException {
        packetBuffer.writeByte((watchableObject.getObjectType() << 5 | (watchableObject.getDataValueId() & 0x1F)) & 0xFF);
        switch (watchableObject.getObjectType()) {
            case 0: {
                packetBuffer.writeByte((byte)watchableObject.getObject());
                break;
            }
            case 1: {
                packetBuffer.writeShort((short)watchableObject.getObject());
                break;
            }
            case 2: {
                packetBuffer.writeInt((int)watchableObject.getObject());
                break;
            }
            case 3: {
                packetBuffer.writeFloat((float)watchableObject.getObject());
                break;
            }
            case 4: {
                packetBuffer.writeString((String)watchableObject.getObject());
                break;
            }
            case 5: {
                packetBuffer.writeItemStackToBuffer((ItemStack)watchableObject.getObject());
                break;
            }
            case 6: {
                final BlockPos blockPos = (BlockPos)watchableObject.getObject();
                packetBuffer.writeInt(blockPos.getX());
                packetBuffer.writeInt(blockPos.getY());
                packetBuffer.writeInt(blockPos.getZ());
                break;
            }
            case 7: {
                final Rotations rotations = (Rotations)watchableObject.getObject();
                packetBuffer.writeFloat(rotations.func_179415_b());
                packetBuffer.writeFloat(rotations.func_179416_c());
                packetBuffer.writeFloat(rotations.func_179413_d());
                break;
            }
        }
    }
    
    public static List readWatchedListFromPacketBuffer(final PacketBuffer packetBuffer) throws IOException {
        ArrayList<Object> arrayList = null;
        for (byte b = packetBuffer.readByte(); b != 127; b = packetBuffer.readByte()) {
            if (arrayList == null) {
                arrayList = (ArrayList<Object>)Lists.newArrayList();
            }
            final int n = (b & 0xE0) >> 5;
            final int n2 = b & 0x1F;
            Object o = null;
            switch (n) {
                case 0: {
                    o = new WatchableObject(n, n2, packetBuffer.readByte());
                    break;
                }
                case 1: {
                    o = new WatchableObject(n, n2, packetBuffer.readShort());
                    break;
                }
                case 2: {
                    o = new WatchableObject(n, n2, packetBuffer.readInt());
                    break;
                }
                case 3: {
                    o = new WatchableObject(n, n2, packetBuffer.readFloat());
                    break;
                }
                case 4: {
                    o = new WatchableObject(n, n2, packetBuffer.readStringFromBuffer(32767));
                    break;
                }
                case 5: {
                    o = new WatchableObject(n, n2, packetBuffer.readItemStackFromBuffer());
                    break;
                }
                case 6: {
                    o = new WatchableObject(n, n2, new BlockPos(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt()));
                    break;
                }
                case 7: {
                    o = new WatchableObject(n, n2, new Rotations(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat()));
                    break;
                }
            }
            arrayList.add(o);
        }
        return arrayList;
    }
    
    public void updateWatchedObjectsFromList(final List list) {
        this.lock.writeLock().lock();
        for (final WatchableObject watchableObject : list) {
            final WatchableObject watchableObject2 = this.watchedObjects.get(watchableObject.getDataValueId());
            if (watchableObject2 != null) {
                watchableObject2.setObject(watchableObject.getObject());
                this.owner.func_145781_i(watchableObject.getDataValueId());
            }
        }
        this.lock.writeLock().unlock();
        this.objectChanged = true;
    }
    
    public boolean getIsBlank() {
        return this.isBlank;
    }
    
    public void func_111144_e() {
        this.objectChanged = false;
    }
    
    public static class WatchableObject
    {
        private final int objectType;
        private final int dataValueId;
        private Object watchedObject;
        private boolean watched;
        private static final String __OBFID;
        
        public WatchableObject(final int objectType, final int dataValueId, final Object watchedObject) {
            this.dataValueId = dataValueId;
            this.watchedObject = watchedObject;
            this.objectType = objectType;
            this.watched = true;
        }
        
        public int getDataValueId() {
            return this.dataValueId;
        }
        
        public void setObject(final Object watchedObject) {
            this.watchedObject = watchedObject;
        }
        
        public Object getObject() {
            return this.watchedObject;
        }
        
        public int getObjectType() {
            return this.objectType;
        }
        
        public boolean isWatched() {
            return this.watched;
        }
        
        public void setWatched(final boolean watched) {
            this.watched = watched;
        }
        
        static void access$0(final WatchableObject watchableObject, final boolean watched) {
            watchableObject.watched = watched;
        }
        
        static {
            __OBFID = "CL_00001560";
        }
    }
}
