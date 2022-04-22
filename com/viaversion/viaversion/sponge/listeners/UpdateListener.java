package com.viaversion.viaversion.sponge.listeners;

import org.spongepowered.api.event.network.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.update.*;
import org.spongepowered.api.event.*;

public class UpdateListener
{
    @Listener
    public void onJoin(final ServerSideConnectionEvent.Join join) {
        if (join.player().hasPermission("viaversion.update") && Via.getConfig().isCheckForUpdates()) {
            UpdateUtil.sendUpdateMessage(join.player().uniqueId());
        }
    }
}
