package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.*;
import com.velocitypowered.api.plugin.*;
import java.util.logging.*;
import com.google.inject.*;
import java.nio.file.*;
import com.velocitypowered.api.plugin.annotation.*;
import com.velocitypowered.api.event.proxy.*;
import com.viaversion.viaversion.velocity.util.*;
import com.viaversion.viaversion.api.*;
import com.velocitypowered.api.event.*;
import java.io.*;

@Plugin(id = "viabackwards", name = "ViaBackwards", version = "4.2.0-22w06a-SNAPSHOT", authors = { "Matsv", "kennytv", "Gerrygames", "creeper123123321", "ForceUpdate1" }, description = "Allow older Minecraft versions to connect to a newer server version.", dependencies = { @Dependency(id = "viaversion") })
public class VelocityPlugin implements ViaBackwardsPlatform
{
    private Logger logger;
    @Inject
    private org.slf4j.Logger loggerSlf4j;
    @Inject
    @DataDirectory
    private Path configPath;
    
    @Subscribe(order = PostOrder.LATE)
    public void onProxyStart(final ProxyInitializeEvent proxyInitializeEvent) {
        this.logger = new LoggerWrapper(this.loggerSlf4j);
        Via.getManager().addEnableListener(this::lambda$onProxyStart$0);
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
    
    private void lambda$onProxyStart$0() {
        this.init(this.configPath.resolve("config.yml").toFile());
    }
}
