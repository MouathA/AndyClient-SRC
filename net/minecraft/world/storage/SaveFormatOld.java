package net.minecraft.world.storage;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class SaveFormatOld implements ISaveFormat
{
    private static final Logger logger;
    protected final File savesDirectory;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000586";
        logger = LogManager.getLogger();
    }
    
    public SaveFormatOld(final File savesDirectory) {
        if (!savesDirectory.exists()) {
            savesDirectory.mkdirs();
        }
        this.savesDirectory = savesDirectory;
    }
    
    @Override
    public String func_154333_a() {
        return "Old Format";
    }
    
    @Override
    public List getSaveList() throws AnvilConverterException {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < 5) {
            final String string = "World" + 1;
            final WorldInfo worldInfo = this.getWorldInfo(string);
            if (worldInfo != null) {
                arrayList.add(new SaveFormatComparator(string, "", worldInfo.getLastTimePlayed(), worldInfo.getSizeOnDisk(), worldInfo.getGameType(), false, worldInfo.isHardcoreModeEnabled(), worldInfo.areCommandsAllowed()));
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    @Override
    public void flushCache() {
    }
    
    @Override
    public WorldInfo getWorldInfo(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (!file.exists()) {
            return null;
        }
        final File file2 = new File(file, "level.dat");
        if (file2.exists()) {
            return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file2)).getCompoundTag("Data"));
        }
        final File file3 = new File(file, "level.dat_old");
        if (file3.exists()) {
            return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file3)).getCompoundTag("Data"));
        }
        return null;
    }
    
    @Override
    public void renameWorld(final String s, final String s2) {
        final File file = new File(this.savesDirectory, s);
        if (file.exists()) {
            final File file2 = new File(file, "level.dat");
            if (file2.exists()) {
                final NBTTagCompound compressed = CompressedStreamTools.readCompressed(new FileInputStream(file2));
                compressed.getCompoundTag("Data").setString("LevelName", s2);
                CompressedStreamTools.writeCompressed(compressed, new FileOutputStream(file2));
            }
        }
    }
    
    @Override
    public boolean func_154335_d(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (file.exists()) {
            return false;
        }
        file.mkdir();
        file.delete();
        return true;
    }
    
    @Override
    public boolean deleteWorldDirectory(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (!file.exists()) {
            return true;
        }
        SaveFormatOld.logger.info("Deleting level " + s);
        while (1 <= 5) {
            SaveFormatOld.logger.info("Attempt " + 1 + "...");
            if (deleteFiles(file.listFiles())) {
                break;
            }
            SaveFormatOld.logger.warn("Unsuccessful in deleting contents.");
            if (1 < 5) {
                Thread.sleep(500L);
            }
            int n = 0;
            ++n;
        }
        return file.delete();
    }
    
    protected static boolean deleteFiles(final File[] array) {
        while (0 < array.length) {
            final File file = array[0];
            SaveFormatOld.logger.debug("Deleting " + file);
            if (file.isDirectory() && !deleteFiles(file.listFiles())) {
                SaveFormatOld.logger.warn("Couldn't delete directory " + file);
                return false;
            }
            if (!file.delete()) {
                SaveFormatOld.logger.warn("Couldn't delete file " + file);
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public ISaveHandler getSaveLoader(final String s, final boolean b) {
        return new SaveHandler(this.savesDirectory, s, b);
    }
    
    @Override
    public boolean func_154334_a(final String s) {
        return false;
    }
    
    @Override
    public boolean isOldMapFormat(final String s) {
        return false;
    }
    
    @Override
    public boolean convertMapFormat(final String s, final IProgressUpdate progressUpdate) {
        return false;
    }
    
    @Override
    public boolean canLoadWorld(final String s) {
        return new File(this.savesDirectory, s).isDirectory();
    }
}
