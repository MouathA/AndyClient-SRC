package com.viaversion.viabackwards;

import net.md_5.bungee.api.plugin.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.*;

public class BungeePlugin extends Plugin implements ViaBackwardsPlatform
{
    public void onLoad() {
        Via.getManager().addEnableListener(this::lambda$onLoad$0);
    }
    
    public void disable() {
    }
    
    private void lambda$onLoad$0() {
        this.init(this.getDataFolder());
    }
}
