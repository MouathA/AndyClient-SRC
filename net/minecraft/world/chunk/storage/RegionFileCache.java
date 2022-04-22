package net.minecraft.world.chunk.storage;

import com.google.common.collect.*;
import java.util.*;
import java.io.*;

public class RegionFileCache
{
    private static final Map regionsByFilename;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000383";
        regionsByFilename = Maps.newHashMap();
    }
    
    public static synchronized RegionFile createOrLoadRegionFile(final File file, final int n, final int n2) {
        final File file2 = new File(file, "region");
        final File file3 = new File(file2, "r." + (n >> 5) + "." + (n2 >> 5) + ".mca");
        final RegionFile regionFile = RegionFileCache.regionsByFilename.get(file3);
        if (regionFile != null) {
            return regionFile;
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        RegionFileCache.regionsByFilename.size();
        256;
        final RegionFile regionFile2 = new RegionFile(file3);
        RegionFileCache.regionsByFilename.put(file3, regionFile2);
        return regionFile2;
    }
    
    public static synchronized void clearRegionFileReferences() {
        for (final RegionFile regionFile : RegionFileCache.regionsByFilename.values()) {
            if (regionFile != null) {
                regionFile.close();
            }
        }
        RegionFileCache.regionsByFilename.clear();
    }
    
    public static DataInputStream getChunkInputStream(final File file, final int n, final int n2) {
        return createOrLoadRegionFile(file, n, n2).getChunkDataInputStream(n & 0x1F, n2 & 0x1F);
    }
    
    public static DataOutputStream getChunkOutputStream(final File file, final int n, final int n2) {
        return createOrLoadRegionFile(file, n, n2).getChunkDataOutputStream(n & 0x1F, n2 & 0x1F);
    }
}
