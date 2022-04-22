package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.lang.management.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "OnStartupTriggeringPolicy", category = "Core", printObject = true)
public class OnStartupTriggeringPolicy implements TriggeringPolicy
{
    private static long JVM_START_TIME;
    private static final Logger LOGGER;
    private boolean evaluated;
    private RollingFileManager manager;
    
    public OnStartupTriggeringPolicy() {
        this.evaluated = false;
    }
    
    @Override
    public void initialize(final RollingFileManager manager) {
        this.manager = manager;
        if (!false) {
            this.evaluated = true;
        }
    }
    
    @Override
    public boolean isTriggeringEvent(final LogEvent logEvent) {
        if (this.evaluated) {
            return false;
        }
        this.evaluated = true;
        return this.manager.getFileTime() < OnStartupTriggeringPolicy.JVM_START_TIME;
    }
    
    @Override
    public String toString() {
        return "OnStartupTriggeringPolicy";
    }
    
    @PluginFactory
    public static OnStartupTriggeringPolicy createPolicy() {
        return new OnStartupTriggeringPolicy();
    }
    
    static {
        OnStartupTriggeringPolicy.JVM_START_TIME = ManagementFactory.getRuntimeMXBean().getStartTime();
        LOGGER = StatusLogger.getLogger();
    }
}
