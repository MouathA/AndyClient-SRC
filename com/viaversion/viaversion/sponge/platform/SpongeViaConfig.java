package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.configuration.*;
import java.io.*;
import java.util.*;

public class SpongeViaConfig extends AbstractViaConfig
{
    private static final List UNSUPPORTED;
    
    public SpongeViaConfig(final File file) {
        super(new File(file, "config.yml"));
        this.reloadConfig();
    }
    
    @Override
    protected void handleConfig(final Map map) {
    }
    
    @Override
    public List getUnsupportedOptions() {
        return SpongeViaConfig.UNSUPPORTED;
    }
    
    static {
        UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "quick-move-action-fix", "change-1_9-hitbox", "change-1_14-hitbox", "blockconnection-method");
    }
}
