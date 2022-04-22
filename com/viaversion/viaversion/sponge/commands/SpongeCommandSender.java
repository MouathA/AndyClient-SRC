package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.api.command.*;
import org.spongepowered.api.command.*;
import net.kyori.adventure.identity.*;
import com.viaversion.viaversion.*;
import net.kyori.adventure.text.*;
import java.util.*;
import org.spongepowered.api.util.*;

public class SpongeCommandSender implements ViaCommandSender
{
    private final CommandCause source;
    
    public SpongeCommandSender(final CommandCause source) {
        this.source = source;
    }
    
    @Override
    public boolean hasPermission(final String s) {
        return this.source.hasPermission(s);
    }
    
    @Override
    public void sendMessage(final String s) {
        this.source.sendMessage(Identity.nil(), (Component)SpongePlugin.LEGACY_SERIALIZER.deserialize(s));
    }
    
    @Override
    public UUID getUUID() {
        if (this.source instanceof Identifiable) {
            return ((Identifiable)this.source).uniqueId();
        }
        return UUID.fromString(this.getName());
    }
    
    @Override
    public String getName() {
        return this.source.friendlyIdentifier().orElse(this.source.identifier());
    }
}
