package com.viaversion.viaversion.api.platform;

import java.util.logging.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.configuration.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public interface ViaPlatform
{
    Logger getLogger();
    
    String getPlatformName();
    
    String getPlatformVersion();
    
    default boolean isProxy() {
        return false;
    }
    
    String getPluginVersion();
    
    PlatformTask runAsync(final Runnable p0);
    
    PlatformTask runSync(final Runnable p0);
    
    PlatformTask runSync(final Runnable p0, final long p1);
    
    PlatformTask runRepeatingSync(final Runnable p0, final long p1);
    
    ViaCommandSender[] getOnlinePlayers();
    
    void sendMessage(final UUID p0, final String p1);
    
    boolean kickPlayer(final UUID p0, final String p1);
    
    default boolean disconnect(final UserConnection userConnection, final String s) {
        if (userConnection.isClientSide()) {
            return false;
        }
        final UUID uuid = userConnection.getProtocolInfo().getUuid();
        return uuid != null && this.kickPlayer(uuid, s);
    }
    
    boolean isPluginEnabled();
    
    ViaAPI getApi();
    
    ViaVersionConfig getConf();
    
    ConfigurationProvider getConfigurationProvider();
    
    File getDataFolder();
    
    void onReload();
    
    JsonObject getDump();
    
    boolean isOldClientsAllowed();
    
    default Collection getUnsupportedSoftwareClasses() {
        return Collections.emptyList();
    }
}
