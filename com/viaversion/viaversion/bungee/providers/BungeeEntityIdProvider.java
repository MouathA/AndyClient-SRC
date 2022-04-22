package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.bungee.storage.*;

public class BungeeEntityIdProvider extends EntityIdProvider
{
    private static Method getClientEntityId;
    
    @Override
    public int getEntityId(final UserConnection userConnection) throws Exception {
        return (int)BungeeEntityIdProvider.getClientEntityId.invoke(((BungeeStorage)userConnection.get(BungeeStorage.class)).getPlayer(), new Object[0]);
    }
    
    static {
        BungeeEntityIdProvider.getClientEntityId = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getClientEntityId", (Class<?>[])new Class[0]);
    }
}
