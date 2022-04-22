package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.api.platform.*;
import org.spongepowered.api.scheduler.*;

public class SpongeViaTask implements PlatformTask
{
    private final ScheduledTask task;
    
    public SpongeViaTask(final ScheduledTask task) {
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
