package net.minecraft.world.storage;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public interface ISaveFormat
{
    String func_154333_a();
    
    ISaveHandler getSaveLoader(final String p0, final boolean p1);
    
    List getSaveList() throws AnvilConverterException;
    
    void flushCache();
    
    WorldInfo getWorldInfo(final String p0);
    
    boolean func_154335_d(final String p0);
    
    boolean deleteWorldDirectory(final String p0);
    
    void renameWorld(final String p0, final String p1);
    
    boolean func_154334_a(final String p0);
    
    boolean isOldMapFormat(final String p0);
    
    boolean convertMapFormat(final String p0, final IProgressUpdate p1);
    
    boolean canLoadWorld(final String p0);
}
