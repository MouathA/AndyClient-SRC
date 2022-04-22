package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.platform.*;
import com.velocitypowered.api.scheduler.*;

public class VelocityViaTask implements PlatformTask
{
    private final ScheduledTask task;
    
    public VelocityViaTask(final ScheduledTask task) {
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
