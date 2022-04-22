package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import com.viaversion.viaversion.bungee.storage.*;

public class BungeeBossBarProvider extends BossBarProvider
{
    @Override
    public void handleAdd(final UserConnection userConnection, final UUID uuid) {
        if (userConnection.has(BungeeStorage.class)) {
            final BungeeStorage bungeeStorage = (BungeeStorage)userConnection.get(BungeeStorage.class);
            if (bungeeStorage.getBossbar() != null) {
                bungeeStorage.getBossbar().add(uuid);
            }
        }
    }
    
    @Override
    public void handleRemove(final UserConnection userConnection, final UUID uuid) {
        if (userConnection.has(BungeeStorage.class)) {
            final BungeeStorage bungeeStorage = (BungeeStorage)userConnection.get(BungeeStorage.class);
            if (bungeeStorage.getBossbar() != null) {
                bungeeStorage.getBossbar().remove(uuid);
            }
        }
    }
}
