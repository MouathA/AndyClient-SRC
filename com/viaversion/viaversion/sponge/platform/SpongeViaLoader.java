package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.*;
import java.util.*;
import org.spongepowered.api.*;
import com.viaversion.viaversion.sponge.listeners.*;
import java.util.function.*;
import com.viaversion.viaversion.api.platform.*;

public class SpongeViaLoader implements ViaPlatformLoader
{
    private final SpongePlugin plugin;
    private final Set listeners;
    private final Set tasks;
    
    public SpongeViaLoader(final SpongePlugin plugin) {
        this.listeners = new HashSet();
        this.tasks = new HashSet();
        this.plugin = plugin;
    }
    
    private void registerListener(final Object o) {
        Sponge.eventManager().registerListeners(this.plugin.container(), this.storeListener(o));
    }
    
    private Object storeListener(final Object o) {
        this.listeners.add(o);
        return o;
    }
    
    @Override
    public void load() {
        this.registerListener(new UpdateListener());
    }
    
    @Override
    public void unload() {
        this.listeners.forEach(Sponge.eventManager()::unregisterListeners);
        this.listeners.clear();
        this.tasks.forEach(PlatformTask::cancel);
        this.tasks.clear();
    }
}
