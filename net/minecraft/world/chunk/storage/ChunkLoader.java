package net.minecraft.world.chunk.storage;

import net.minecraft.world.biome.*;
import net.minecraft.nbt.*;

public class ChunkLoader
{
    private static final String __OBFID;
    
    public static AnvilConverterData load(final NBTTagCompound nbtTagCompound) {
        final AnvilConverterData anvilConverterData = new AnvilConverterData(nbtTagCompound.getInteger("xPos"), nbtTagCompound.getInteger("zPos"));
        anvilConverterData.blocks = nbtTagCompound.getByteArray("Blocks");
        anvilConverterData.data = new NibbleArrayReader(nbtTagCompound.getByteArray("Data"), 7);
        anvilConverterData.skyLight = new NibbleArrayReader(nbtTagCompound.getByteArray("SkyLight"), 7);
        anvilConverterData.blockLight = new NibbleArrayReader(nbtTagCompound.getByteArray("BlockLight"), 7);
        anvilConverterData.heightmap = nbtTagCompound.getByteArray("HeightMap");
        anvilConverterData.terrainPopulated = nbtTagCompound.getBoolean("TerrainPopulated");
        anvilConverterData.entities = nbtTagCompound.getTagList("Entities", 10);
        anvilConverterData.tileEntities = nbtTagCompound.getTagList("TileEntities", 10);
        anvilConverterData.tileTicks = nbtTagCompound.getTagList("TileTicks", 10);
        anvilConverterData.lastUpdated = nbtTagCompound.getLong("LastUpdate");
        return anvilConverterData;
    }
    
    public static void convertToAnvilFormat(final AnvilConverterData anvilConverterData, final NBTTagCompound nbtTagCompound, final WorldChunkManager worldChunkManager) {
        nbtTagCompound.setInteger("xPos", anvilConverterData.x);
        nbtTagCompound.setInteger("zPos", anvilConverterData.z);
        nbtTagCompound.setLong("LastUpdate", anvilConverterData.lastUpdated);
        final int[] array = new int[anvilConverterData.heightmap.length];
        while (0 < anvilConverterData.heightmap.length) {
            array[0] = anvilConverterData.heightmap[0];
            int n = 0;
            ++n;
        }
        nbtTagCompound.setIntArray("HeightMap", array);
        nbtTagCompound.setBoolean("TerrainPopulated", anvilConverterData.terrainPopulated);
        nbtTagCompound.setTag("Sections", new NBTTagList());
        nbtTagCompound.setByteArray("Biomes", new byte[256]);
        nbtTagCompound.setTag("Entities", anvilConverterData.entities);
        nbtTagCompound.setTag("TileEntities", anvilConverterData.tileEntities);
        if (anvilConverterData.tileTicks != null) {
            nbtTagCompound.setTag("TileTicks", anvilConverterData.tileTicks);
        }
    }
    
    static {
        __OBFID = "CL_00000379";
    }
    
    public static class AnvilConverterData
    {
        public long lastUpdated;
        public boolean terrainPopulated;
        public byte[] heightmap;
        public NibbleArrayReader blockLight;
        public NibbleArrayReader skyLight;
        public NibbleArrayReader data;
        public byte[] blocks;
        public NBTTagList entities;
        public NBTTagList tileEntities;
        public NBTTagList tileTicks;
        public final int x;
        public final int z;
        private static final String __OBFID;
        
        public AnvilConverterData(final int x, final int z) {
            this.x = x;
            this.z = z;
        }
        
        static {
            __OBFID = "CL_00000380";
        }
    }
}
