package de.gerrygames.viarewind;

import org.spongepowered.api.plugin.*;
import java.util.logging.*;
import com.google.inject.*;
import java.nio.file.*;
import org.spongepowered.api.config.*;
import org.spongepowered.api.event.game.state.*;
import com.viaversion.viaversion.sponge.util.*;
import de.gerrygames.viarewind.api.*;
import org.spongepowered.api.event.*;

@Plugin(id = "viarewind", name = "ViaRewind", version = "2.0.3-SNAPSHOT", authors = { "Gerrygames" }, dependencies = { @Dependency(id = "viaversion"), @Dependency(id = "viabackwards", optional = true) }, url = "https://viaversion.com/rewind")
public class SpongePlugin implements ViaRewindPlatform
{
    private Logger logger;
    @Inject
    private org.slf4j.Logger loggerSlf4j;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;
    
    @Listener(order = Order.LATE)
    public void onGameStart(final GameInitializationEvent gameInitializationEvent) {
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
