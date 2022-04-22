package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.configuration.*;
import com.viaversion.viaversion.api.platform.*;
import com.google.common.base.*;

public final class Via
{
    private static ViaManager manager;
    
    public static ViaAPI getAPI() {
        return manager().getPlatform().getApi();
    }
    
    public static ViaManager getManager() {
        return manager();
    }
    
    public static ViaVersionConfig getConfig() {
        return manager().getPlatform().getConf();
    }
    
    public static ViaPlatform getPlatform() {
        return manager().getPlatform();
    }
    
    public static void init(final ViaManager manager) {
        Preconditions.checkArgument(Via.manager == null, (Object)"ViaManager is already set");
        Via.manager = manager;
    }
    
    private static ViaManager manager() {
        Preconditions.checkArgument(Via.manager != null, (Object)"ViaVersion has not loaded the platform yet");
        return Via.manager;
    }
}
