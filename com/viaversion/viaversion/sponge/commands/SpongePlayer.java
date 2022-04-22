package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.api.command.*;
import org.spongepowered.api.entity.living.player.server.*;
import com.viaversion.viaversion.*;
import net.kyori.adventure.text.*;
import java.util.*;

public class SpongePlayer implements ViaCommandSender
{
    private final ServerPlayer player;
    
    public SpongePlayer(final ServerPlayer player) {
        this.player = player;
    }
    
    @Override
    public boolean hasPermission(final String s) {
        return this.player.hasPermission(s);
    }
    
    @Override
    public void sendMessage(final String s) {
        this.player.sendMessage((Component)SpongePlugin.LEGACY_SERIALIZER.deserialize(s));
    }
    
    @Override
    public UUID getUUID() {
        return this.player.uniqueId();
    }
    
    @Override
    public String getName() {
        return this.player.friendlyIdentifier().orElse(this.player.identifier());
    }
}
