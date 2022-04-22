package net.minecraft.world.storage;

import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import java.util.*;
import java.io.*;

public class MapStorage
{
    private ISaveHandler saveHandler;
    protected Map loadedDataMap;
    private List loadedDataList;
    private Map idCounts;
    private static final String __OBFID;
    
    public MapStorage(final ISaveHandler saveHandler) {
        this.loadedDataMap = Maps.newHashMap();
        this.loadedDataList = Lists.newArrayList();
        this.idCounts = Maps.newHashMap();
        this.saveHandler = saveHandler;
        this.loadIdCounts();
    }
    
    public WorldSavedData loadData(final Class clazz, final String s) {
        WorldSavedData worldSavedData = this.loadedDataMap.get(s);
        if (worldSavedData != null) {
            return worldSavedData;
        }
        if (this.saveHandler != null) {
            final File mapFileFromName = this.saveHandler.getMapFileFromName(s);
            if (mapFileFromName != null && mapFileFromName.exists()) {
                worldSavedData = clazz.getConstructor(String.class).newInstance(s);
                final FileInputStream fileInputStream = new FileInputStream(mapFileFromName);
                final NBTTagCompound compressed = CompressedStreamTools.readCompressed(fileInputStream);
                fileInputStream.close();
                worldSavedData.readFromNBT(compressed.getCompoundTag("data"));
            }
        }
        if (worldSavedData != null) {
            this.loadedDataMap.put(s, worldSavedData);
            this.loadedDataList.add(worldSavedData);
        }
        return worldSavedData;
    }
    
    public void setData(final String s, final WorldSavedData worldSavedData) {
        if (this.loadedDataMap.containsKey(s)) {
            this.loadedDataList.remove(this.loadedDataMap.remove(s));
        }
        this.loadedDataMap.put(s, worldSavedData);
        this.loadedDataList.add(worldSavedData);
    }
    
    public void saveAllData() {
        while (0 < this.loadedDataList.size()) {
            final WorldSavedData worldSavedData = this.loadedDataList.get(0);
            if (worldSavedData.isDirty()) {
                this.saveData(worldSavedData);
                worldSavedData.setDirty(false);
            }
            int n = 0;
            ++n;
        }
    }
    
    private void saveData(final WorldSavedData worldSavedData) {
        if (this.saveHandler != null) {
            final File mapFileFromName = this.saveHandler.getMapFileFromName(worldSavedData.mapName);
            if (mapFileFromName != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                worldSavedData.writeToNBT(nbtTagCompound);
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setTag("data", nbtTagCompound);
                final FileOutputStream fileOutputStream = new FileOutputStream(mapFileFromName);
                CompressedStreamTools.writeCompressed(nbtTagCompound2, fileOutputStream);
                fileOutputStream.close();
            }
        }
    }
    
    private void loadIdCounts() {
        this.idCounts.clear();
        if (this.saveHandler == null) {
            return;
        }
        final File mapFileFromName = this.saveHandler.getMapFileFromName("idcounts");
        if (mapFileFromName != null && mapFileFromName.exists()) {
            final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(mapFileFromName));
            final NBTTagCompound read = CompressedStreamTools.read(dataInputStream);
            dataInputStream.close();
            for (final String s : read.getKeySet()) {
                final NBTBase tag = read.getTag(s);
                if (tag instanceof NBTTagShort) {
                    this.idCounts.put(s, ((NBTTagShort)tag).getShort());
                }
            }
        }
    }
    
    public int getUniqueDataId(final String s) {
        final Short n = this.idCounts.get(s);
        Short n2;
        if (n == null) {
            n2 = 0;
        }
        else {
            n2 = (short)(n + 1);
        }
        this.idCounts.put(s, n2);
        if (this.saveHandler == null) {
            return n2;
        }
        final File mapFileFromName = this.saveHandler.getMapFileFromName("idcounts");
        if (mapFileFromName != null) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            for (final String s2 : this.idCounts.keySet()) {
                nbtTagCompound.setShort(s2, (short)this.idCounts.get(s2));
            }
            final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(mapFileFromName));
            CompressedStreamTools.write(nbtTagCompound, dataOutputStream);
            dataOutputStream.close();
        }
        return n2;
    }
    
    static {
        __OBFID = "CL_00000604";
    }
}
