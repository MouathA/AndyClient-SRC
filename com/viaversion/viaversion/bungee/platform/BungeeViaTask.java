package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.platform.*;
import net.md_5.bungee.api.scheduler.*;

public class BungeeViaTask implements PlatformTask
{
    private final ScheduledTask task;
    
    public BungeeViaTask(final ScheduledTask task) {
        this.task = task;
    }
    
    @Override
    public ScheduledTask getObject() {
        return this.task;
    }
    
    @Override
    public void cancel() {
        this.task.cancel();
    }
    
    @Override
    public Object getObject() {
        return this.getObject();
    }
}
