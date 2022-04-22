package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.*;
import java.util.logging.*;
import java.io.*;
import org.apache.logging.log4j.*;
import com.viaversion.viabackwards.fabric.util.*;
import net.fabricmc.loader.api.*;

public class ViaFabricAddon implements ViaBackwardsPlatform, Runnable
{
    private final Logger logger;
    private File configDir;
    
    public ViaFabricAddon() {
        this.logger = new LoggerWrapper(LogManager.getLogger("ViaBackwards"));
    }
    
    @Override
    public void run() {
        this.configDir = FabricLoader.getInstance().getConfigDir().resolve("ViaBackwards").toFile();
        this.init(this.getDataFolder());
    }
    
    @Override
    public void disable() {
    }
    
    @Override
    public File getDataFolder() {
        return this.configDir;
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
