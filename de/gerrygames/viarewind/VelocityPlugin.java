package de.gerrygames.viarewind;

import com.velocitypowered.api.plugin.*;
import java.util.logging.*;
import com.google.inject.*;
import java.nio.file.*;
import com.velocitypowered.api.plugin.annotation.*;
import com.velocitypowered.api.event.proxy.*;
import com.viaversion.viaversion.sponge.util.*;
import de.gerrygames.viarewind.api.*;
import com.velocitypowered.api.event.*;

@Plugin(id = "viarewind", name = "ViaRewind", version = "2.0.3-SNAPSHOT", authors = { "Gerrygames" }, dependencies = { @Dependency(id = "viaversion"), @Dependency(id = "viabackwards", optional = true) }, url = "https://viaversion.com/rewind")
public class VelocityPlugin implements ViaRewindPlatform
{
    private Logger logger;
    @Inject
    private org.slf4j.Logger loggerSlf4j;
    @Inject
    @DataDirectory
    private Path configDir;
    
    @Subscribe(order = PostOrder.LATE)
    public void onProxyStart(final ProxyInitializeEvent proxyInitializeEvent) {
        this.logger = new LoggerWrapper(this.loggerSlf4j);
        final ViaRewindConfigImpl viaRewindConfigImpl = new ViaRewindConfigImpl(this.configDir.resolve("config.yml").toFile());
        viaRewindConfigImpl.reloadConfig();
        this.init(viaRewindConfigImpl);
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
