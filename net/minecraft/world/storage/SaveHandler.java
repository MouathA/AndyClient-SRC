package net.minecraft.world.storage;

import org.apache.logging.log4j.*;
import net.minecraft.server.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class SaveHandler implements ISaveHandler, IPlayerFileData
{
    private static final Logger logger;
    private final File worldDirectory;
    private final File playersDirectory;
    private final File mapDataDir;
    private final long initializationTime;
    private final String saveDirectoryName;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000585";
        logger = LogManager.getLogger();
    }
    
    public SaveHandler(final File file, final String saveDirectoryName, final boolean b) {
        this.initializationTime = MinecraftServer.getCurrentTimeMillis();
        (this.worldDirectory = new File(file, saveDirectoryName)).mkdirs();
        this.playersDirectory = new File(this.worldDirectory, "playerdata");
        (this.mapDataDir = new File(this.worldDirectory, "data")).mkdirs();
        this.saveDirectoryName = saveDirectoryName;
        if (b) {
            this.playersDirectory.mkdirs();
        }
        this.setSessionLock();
    }
    
    private void setSessionLock() {
        final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(this.worldDirectory, "session.lock")));
        dataOutputStream.writeLong(this.initializationTime);
        dataOutputStream.close();
    }
    
    @Override
    public File getWorldDirectory() {
        return this.worldDirectory;
    }
    
    @Override
    public void checkSessionLock() throws MinecraftException {
        final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(this.worldDirectory, "session.lock")));
        if (dataInputStream.readLong() != this.initializationTime) {
            throw new MinecraftException("The save is being accessed from another location, aborting");
        }
        dataInputStream.close();
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }
    
    @Override
    public WorldInfo loadWorldInfo() {
        final File file = new File(this.worldDirectory, "level.dat");
        if (file.exists()) {
            return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file)).getCompoundTag("Data"));
        }
        final File file2 = new File(this.worldDirectory, "level.dat_old");
        if (file2.exists()) {
            return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file2)).getCompoundTag("Data"));
        }
        return null;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound cloneNBTCompound = worldInfo.cloneNBTCompound(nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setTag("Data", cloneNBTCompound);
        final File file = new File(this.worldDirectory, "level.dat_new");
        final File file2 = new File(this.worldDirectory, "level.dat_old");
        final File file3 = new File(this.worldDirectory, "level.dat");
        CompressedStreamTools.writeCompressed(nbtTagCompound2, new FileOutputStream(file));
        if (file2.exists()) {
            file2.delete();
        }
        file3.renameTo(file2);
        if (file3.exists()) {
            file3.delete();
        }
        file.renameTo(file3);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Override
    public void saveWorldInfo(final WorldInfo worldInfo) {
        final NBTTagCompound nbtTagCompound = worldInfo.getNBTTagCompound();
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setTag("Data", nbtTagCompound);
        final File file = new File(this.worldDirectory, "level.dat_new");
        final File file2 = new File(this.worldDirectory, "level.dat_old");
        final File file3 = new File(this.worldDirectory, "level.dat");
        CompressedStreamTools.writeCompressed(nbtTagCompound2, new FileOutputStream(file));
        if (file2.exists()) {
            file2.delete();
        }
        file3.renameTo(file2);
        if (file3.exists()) {
            file3.delete();
        }
        file.renameTo(file3);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Override
    public void writePlayerData(final EntityPlayer entityPlayer) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        entityPlayer.writeToNBT(nbtTagCompound);
        final File file = new File(this.playersDirectory, String.valueOf(entityPlayer.getUniqueID().toString()) + ".dat.tmp");
        final File file2 = new File(this.playersDirectory, String.valueOf(entityPlayer.getUniqueID().toString()) + ".dat");
        CompressedStreamTools.writeCompressed(nbtTagCompound, new FileOutputStream(file));
        if (file2.exists()) {
            file2.delete();
        }
        file.renameTo(file2);
    }
    
    @Override
    public NBTTagCompound readPlayerData(final EntityPlayer entityPlayer) {
        NBTTagCompound compressed = null;
        final File file = new File(this.playersDirectory, String.valueOf(entityPlayer.getUniqueID().toString()) + ".dat");
        if (file.exists() && file.isFile()) {
            compressed = CompressedStreamTools.readCompressed(new FileInputStream(file));
        }
        if (compressed != null) {
            entityPlayer.readFromNBT(compressed);
        }
        return compressed;
    }
    
    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return this;
    }
    
    @Override
    public String[] getAvailablePlayerDat() {
        String[] list = this.playersDirectory.list();
        if (list == null) {
            list = new String[0];
        }
        while (0 < list.length) {
            if (list[0].endsWith(".dat")) {
                list[0] = list[0].substring(0, list[0].length() - 4);
            }
            int n = 0;
            ++n;
        }
        return list;
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public File getMapFileFromName(final String s) {
        return new File(this.mapDataDir, String.valueOf(s) + ".dat");
    }
    
    @Override
    public String getWorldDirectoryName() {
        return this.saveDirectoryName;
    }
}
