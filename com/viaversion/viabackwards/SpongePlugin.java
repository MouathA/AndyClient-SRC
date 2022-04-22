package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.*;
import org.spongepowered.plugin.builtin.jvm.*;
import java.util.logging.*;
import java.nio.file.*;
import com.google.inject.*;
import org.spongepowered.api.config.*;
import com.viaversion.viaversion.sponge.util.*;
import org.spongepowered.api.event.lifecycle.*;
import com.viaversion.viaversion.api.*;
import org.spongepowered.api.event.*;
import java.io.*;

@Plugin("viabackwards")
public class SpongePlugin implements ViaBackwardsPlatform
{
    private final Logger logger;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configPath;
    
    @Inject
    SpongePlugin(final org.apache.logging.log4j.Logger logger) {
        this.logger = new LoggerWrapper(logger);
    }
    
    @Listener
    public void constructPlugin(final ConstructPluginEvent constructPluginEvent) {
        Via.getManager().addEnableListener(this::lambda$constructPlugin$0);
    }
    
    @Override
    public void disable() {
    }
    
    @Override
    public File getDataFolder() {
        return this.configPath.toFile();
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
    
    private void lambda$constructPlugin$0() {
        this.init(this.configPath.resolve("config.yml").toFile());
    }
}
