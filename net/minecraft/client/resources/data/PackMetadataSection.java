package net.minecraft.client.resources.data;

import net.minecraft.util.*;

public class PackMetadataSection implements IMetadataSection
{
    private final IChatComponent packDescription;
    private final int packFormat;
    private static final String __OBFID;
    
    public PackMetadataSection(final IChatComponent packDescription, final int packFormat) {
        this.packDescription = packDescription;
        this.packFormat = packFormat;
    }
    
    public IChatComponent func_152805_a() {
        return this.packDescription;
    }
    
    public int getPackFormat() {
        return this.packFormat;
    }
    
    static {
        __OBFID = "CL_00001112";
    }
}
