package de.gerrygames.viarewind;

import de.gerrygames.viarewind.api.*;

public class ViaRewind
{
    private static ViaRewindPlatform platform;
    private static ViaRewindConfig config;
    
    public static void init(final ViaRewindPlatform platform, final ViaRewindConfig config) {
        ViaRewind.platform = platform;
        ViaRewind.config = config;
    }
    
    public static ViaRewindPlatform getPlatform() {
        return ViaRewind.platform;
    }
    
    public static ViaRewindConfig getConfig() {
        return ViaRewind.config;
    }
}
