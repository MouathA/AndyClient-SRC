package net.minecraft.client.resources;

import java.io.*;

public class ResourcePackFileNotFoundException extends FileNotFoundException
{
    private static final String __OBFID;
    
    public ResourcePackFileNotFoundException(final File file, final String s) {
        super(String.format("'%s' in ResourcePack '%s'", s, file));
    }
    
    static {
        __OBFID = "CL_00001086";
    }
}
