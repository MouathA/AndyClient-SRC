package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.debug.*;
import java.util.*;

public interface ViaManager
{
    ProtocolManager getProtocolManager();
    
    ViaPlatform getPlatform();
    
    ConnectionManager getConnectionManager();
    
    ViaProviders getProviders();
    
    ViaInjector getInjector();
    
    ViaVersionCommand getCommandHandler();
    
    ViaPlatformLoader getLoader();
    
    @Deprecated
    default boolean isDebug() {
        return this.debugHandler().enabled();
    }
    
    @Deprecated
    default void setDebug(final boolean enabled) {
        this.debugHandler().setEnabled(enabled);
    }
    
    DebugHandler debugHandler();
    
    Set getSubPlatforms();
    
    void addEnableListener(final Runnable p0);
}
