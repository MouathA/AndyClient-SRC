package net.minecraft.world.chunk.storage;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import net.minecraft.world.storage.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import java.io.*;
import java.util.*;

public class AnvilSaveConverter extends SaveFormatOld
{
    private static final Logger logger;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000582";
        logger = LogManager.getLogger();
    }
    
    public AnvilSaveConverter(final File file) {
        super(file);
    }
    
    @Override
    public String func_154333_a() {
        return "Anvil";
    }
    
    @Override
    public List getSaveList() throws AnvilConverterException {
        if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
            final ArrayList arrayList = Lists.newArrayList();
            File[] listFiles;
            while (0 < (listFiles = this.savesDirectory.listFiles()).length) {
                final File file = listFiles[0];
                if (file.isDirectory()) {
                    final String name = file.getName();
                    final WorldInfo worldInfo = this.getWorldInfo(name);
                    if (worldInfo != null && (worldInfo.getSaveVersion() == 19132 || worldInfo.getSaveVersion() == 19133)) {
                        final boolean b = worldInfo.getSaveVersion() != this.getSaveVersion();
                        String worldName = worldInfo.getWorldName();
                        if (StringUtils.isEmpty(worldName)) {
                            worldName = name;
                        }
                        arrayList.add(new SaveFormatComparator(name, worldName, worldInfo.getLastTimePlayed(), 0L, worldInfo.getGameType(), b, worldInfo.isHardcoreModeEnabled(), worldInfo.areCommandsAllowed()));
                    }
                }
                int n = 0;
                ++n;
            }
            return arrayList;
        }
        throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
    }
    
    protected int getSaveVersion() {
        return 19133;
    }
    
    @Override
    public void flushCache() {
    }
    
    @Override
    public ISaveHandler getSaveLoader(final String s, final boolean b) {
        return new AnvilSaveHandler(this.savesDirectory, s, b);
    }
    
    @Override
    public boolean func_154334_a(final String s) {
        final WorldInfo worldInfo = this.getWorldInfo(s);
        return worldInfo != null && worldInfo.getSaveVersion() == 19132;
    }
    
    @Override
    public boolean isOldMapFormat(final String s) {
        final WorldInfo worldInfo = this.getWorldInfo(s);
        return worldInfo != null && worldInfo.getSaveVersion() != this.getSaveVersion();
    }
    
    @Override
    public boolean convertMapFormat(final String s, final IProgressUpdate progressUpdate) {
        progressUpdate.setLoadingProgress(0);
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList();
        final ArrayList arrayList3 = Lists.newArrayList();
        final File file = new File(this.savesDirectory, s);
        final File file2 = new File(file, "DIM-1");
        final File file3 = new File(file, "DIM1");
        AnvilSaveConverter.logger.info("Scanning folders...");
        this.addRegionFilesToCollection(file, arrayList);
        if (file2.exists()) {
            this.addRegionFilesToCollection(file2, arrayList2);
        }
        if (file3.exists()) {
            this.addRegionFilesToCollection(file3, arrayList3);
        }
        final int n = arrayList.size() + arrayList2.size() + arrayList3.size();
        AnvilSaveConverter.logger.info("Total conversion count is " + n);
        final WorldInfo worldInfo = this.getWorldInfo(s);
        WorldChunkManager worldChunkManager;
        if (worldInfo.getTerrainType() == WorldType.FLAT) {
            worldChunkManager = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5f);
        }
        else {
            worldChunkManager = new WorldChunkManager(worldInfo.getSeed(), worldInfo.getTerrainType(), worldInfo.getGeneratorOptions());
        }
        this.convertFile(new File(file, "region"), arrayList, worldChunkManager, 0, n, progressUpdate);
        this.convertFile(new File(file2, "region"), arrayList2, new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f), arrayList.size(), n, progressUpdate);
        this.convertFile(new File(file3, "region"), arrayList3, new WorldChunkManagerHell(BiomeGenBase.sky, 0.0f), arrayList.size() + arrayList2.size(), n, progressUpdate);
        worldInfo.setSaveVersion(19133);
        if (worldInfo.getTerrainType() == WorldType.DEFAULT_1_1) {
            worldInfo.setTerrainType(WorldType.DEFAULT);
        }
        this.createFile(s);
        this.getSaveLoader(s, false).saveWorldInfo(worldInfo);
        return true;
    }
    
    private void createFile(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (!file.exists()) {
            AnvilSaveConverter.logger.warn("Unable to create level.dat_mcr backup");
        }
        else {
            final File file2 = new File(file, "level.dat");
            if (!file2.exists()) {
                AnvilSaveConverter.logger.warn("Unable to create level.dat_mcr backup");
            }
            else if (!file2.renameTo(new File(file, "level.dat_mcr"))) {
                AnvilSaveConverter.logger.warn("Unable to create level.dat_mcr backup");
            }
        }
    }
    
    private void convertFile(final File file, final Iterable iterable, final WorldChunkManager worldChunkManager, int n, final int n2, final IProgressUpdate progressUpdate) {
        final Iterator<File> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            this.convertChunks(file, iterator.next(), worldChunkManager, n, n2, progressUpdate);
            ++n;
            progressUpdate.setLoadingProgress((int)Math.round(100.0 * n / n2));
        }
    }
    
    private void convertChunks(final File file, final File file2, final WorldChunkManager worldChunkManager, final int n, final int n2, final IProgressUpdate progressUpdate) {
        final String name = file2.getName();
        final RegionFile regionFile = new RegionFile(file2);
        final RegionFile regionFile2 = new RegionFile(new File(file, String.valueOf(name.substring(0, name.length() - 4)) + ".mca"));
        regionFile.close();
        regionFile2.close();
    }
    
    private void addRegionFilesToCollection(final File file, final Collection collection) {
        final File[] listFiles = new File(file, "region").listFiles(new FilenameFilter() {
            private static final String __OBFID;
            final AnvilSaveConverter this$0;
            
            @Override
            public boolean accept(final File file, final String s) {
                return s.endsWith(".mcr");
            }
            
            static {
                __OBFID = "CL_00000583";
            }
        });
        if (listFiles != null) {
            Collections.addAll(collection, listFiles);
        }
    }
}
