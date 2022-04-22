package org.apache.logging.log4j.core.config;

import java.io.*;
import java.util.*;

public class FileConfigurationMonitor implements ConfigurationMonitor
{
    private static final int MASK = 15;
    private static final int MIN_INTERVAL = 5;
    private static final int MILLIS_PER_SECOND = 1000;
    private final File file;
    private long lastModified;
    private final List listeners;
    private final int interval;
    private long nextCheck;
    private int counter;
    private final Reconfigurable reconfigurable;
    
    public FileConfigurationMonitor(final Reconfigurable reconfigurable, final File file, final List listeners, final int n) {
        this.counter = 0;
        this.reconfigurable = reconfigurable;
        this.file = file;
        this.lastModified = file.lastModified();
        this.listeners = listeners;
        this.interval = ((n < 5) ? 5 : n) * 1000;
        this.nextCheck = System.currentTimeMillis() + n;
    }
    
    @Override
    public void checkConfiguration() {
        if ((++this.counter & 0xF) == 0x0) {
            // monitorenter(this)
            final long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis >= this.nextCheck) {
                this.nextCheck = currentTimeMillis + this.interval;
                if (this.file.lastModified() > this.lastModified) {
                    this.lastModified = this.file.lastModified();
                    final Iterator<ConfigurationListener> iterator = this.listeners.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().onChange(this.reconfigurable);
                    }
                }
            }
        }
        // monitorexit(this)
    }
}
