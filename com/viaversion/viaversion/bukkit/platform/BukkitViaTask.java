package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.platform.*;
import org.bukkit.scheduler.*;
import com.google.common.base.*;

public class BukkitViaTask implements PlatformTask
{
    private final BukkitTask task;
    
    public BukkitViaTask(final BukkitTask task) {
        this.task = task;
    }
    
    @Override
    public BukkitTask getObject() {
        return this.task;
    }
    
    @Override
    public void cancel() {
        Preconditions.checkArgument(this.task != null, (Object)"Task cannot be cancelled");
        this.task.cancel();
    }
    
    @Override
    public Object getObject() {
        return this.getObject();
    }
}
