package com.viaversion.viaversion.sponge.listeners;

import java.lang.reflect.*;
import com.viaversion.viaversion.*;
import org.spongepowered.api.*;

public class ViaSpongeListener extends ViaListener
{
    private static Field entityIdField;
    private final SpongePlugin plugin;
    
    public ViaSpongeListener(final SpongePlugin plugin, final Class clazz) {
        super(clazz);
        this.plugin = plugin;
    }
    
    @Override
    public void register() {
        if (this.isRegistered()) {
            return;
        }
        Sponge.eventManager().registerListeners(this.plugin.container(), (Object)this);
        this.setRegistered(true);
    }
}
