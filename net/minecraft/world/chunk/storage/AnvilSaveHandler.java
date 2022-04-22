package net.minecraft.world.chunk.storage;

import java.io.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.world.storage.*;

public class AnvilSaveHandler extends SaveHandler
{
    private static final String __OBFID;
    
    public AnvilSaveHandler(final File file, final String s, final boolean b) {
        super(file, s, b);
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        final File worldDirectory = this.getWorldDirectory();
        if (worldProvider instanceof WorldProviderHell) {
            final File file = new File(worldDirectory, "DIM-1");
            file.mkdirs();
            return new AnvilChunkLoader(file);
        }
        if (worldProvider instanceof WorldProviderEnd) {
            final File file2 = new File(worldDirectory, "DIM1");
            file2.mkdirs();
            return new AnvilChunkLoader(file2);
        }
        return new AnvilChunkLoader(worldDirectory);
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
        worldInfo.setSaveVersion(19133);
        super.saveWorldInfoWithPlayer(worldInfo, nbtTagCompound);
    }
    
    @Override
    public void flush() {
        ThreadedFileIOBase.func_178779_a().waitForFinish();
    }
    
    static {
        __OBFID = "CL_00000581";
    }
}
