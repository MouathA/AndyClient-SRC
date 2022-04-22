package net.minecraft.world.storage;

import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.nbt.*;
import java.io.*;

public class SaveHandlerMP implements ISaveHandler
{
    private static final String __OBFID;
    
    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }
    
    @Override
    public void checkSessionLock() throws MinecraftException {
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        return null;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public void saveWorldInfo(final WorldInfo worldInfo) {
    }
    
    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public File getMapFileFromName(final String s) {
        return null;
    }
    
    @Override
    public String getWorldDirectoryName() {
        return "none";
    }
    
    @Override
    public File getWorldDirectory() {
        return null;
    }
    
    static {
        __OBFID = "CL_00000602";
    }
}
