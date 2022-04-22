package org.apache.logging.log4j.core;

import org.apache.logging.log4j.status.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.io.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.spi.*;
import java.beans.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.*;

public class LoggerContext implements org.apache.logging.log4j.spi.LoggerContext, ConfigurationListener, LifeCycle
{
    public static final String PROPERTY_CONFIG = "config";
    private static final StatusLogger LOGGER;
    private final ConcurrentMap loggers;
    private final CopyOnWriteArrayList propertyChangeListeners;
    private Configuration config;
    private Object externalContext;
    private final String name;
    private URI configLocation;
    private ShutdownThread shutdownThread;
    private Status status;
    private final Lock configLock;
    
    public LoggerContext(final String s) {
        this(s, null, (URI)null);
    }
    
    public LoggerContext(final String s, final Object o) {
        this(s, o, (URI)null);
    }
    
    public LoggerContext(final String name, final Object externalContext, final URI configLocation) {
        this.loggers = new ConcurrentHashMap();
        this.propertyChangeListeners = new CopyOnWriteArrayList();
        this.config = new DefaultConfiguration();
        this.shutdownThread = null;
        this.status = Status.INITIALIZED;
        this.configLock = new ReentrantLock();
        this.name = name;
        this.externalContext = externalContext;
        this.configLocation = configLocation;
    }
    
    public LoggerContext(final String name, final Object externalContext, final String s) {
        this.loggers = new ConcurrentHashMap();
        this.propertyChangeListeners = new CopyOnWriteArrayList();
        this.config = new DefaultConfiguration();
        this.shutdownThread = null;
        this.status = Status.INITIALIZED;
        this.configLock = new ReentrantLock();
        this.name = name;
        this.externalContext = externalContext;
        if (s != null) {
            this.configLocation = new File(s).toURI();
        }
        else {
            this.configLocation = null;
        }
    }
    
    @Override
    public void start() {
        if (this.configLock.tryLock()) {
            if (this.status == Status.INITIALIZED || this.status == Status.STOPPED) {
                this.status = Status.STARTING;
                this.reconfigure();
                if (this.config.isShutdownHookEnabled()) {
                    this.shutdownThread = new ShutdownThread(this);
                    Runtime.getRuntime().addShutdownHook(this.shutdownThread);
                }
                this.status = Status.STARTED;
            }
            this.configLock.unlock();
        }
    }
    
    public void start(final Configuration configuration) {
        if (this.configLock.tryLock()) {
            if ((this.status == Status.INITIALIZED || this.status == Status.STOPPED) && configuration.isShutdownHookEnabled()) {
                this.shutdownThread = new ShutdownThread(this);
                Runtime.getRuntime().addShutdownHook(this.shutdownThread);
                this.status = Status.STARTED;
            }
            this.configLock.unlock();
        }
        this.setConfiguration(configuration);
    }
    
    @Override
    public void stop() {
        this.configLock.lock();
        if (this.status == Status.STOPPED) {
            this.configLock.unlock();
            return;
        }
        this.status = Status.STOPPING;
        if (this.shutdownThread != null) {
            Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
            this.shutdownThread = null;
        }
        final Configuration config = this.config;
        this.config = new NullConfiguration();
        this.updateLoggers();
        config.stop();
        this.externalContext = null;
        LogManager.getFactory().removeContext(this);
        this.status = Status.STOPPED;
        this.configLock.unlock();
    }
    
    public String getName() {
        return this.name;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    @Override
    public boolean isStarted() {
        return this.status == Status.STARTED;
    }
    
    public void setExternalContext(final Object externalContext) {
        this.externalContext = externalContext;
    }
    
    @Override
    public Object getExternalContext() {
        return this.externalContext;
    }
    
    @Override
    public Logger getLogger(final String s) {
        return this.getLogger(s, (MessageFactory)null);
    }
    
    @Override
    public Logger getLogger(final String s, final MessageFactory messageFactory) {
        final Logger logger = (Logger)this.loggers.get(s);
        if (logger != null) {
            AbstractLogger.checkMessageFactory(logger, messageFactory);
            return logger;
        }
        final Logger instance = this.newInstance(this, s, messageFactory);
        final Logger logger2 = this.loggers.putIfAbsent(s, instance);
        return (logger2 == null) ? instance : logger2;
    }
    
    @Override
    public boolean hasLogger(final String s) {
        return this.loggers.containsKey(s);
    }
    
    public Configuration getConfiguration() {
        return this.config;
    }
    
    public void addFilter(final Filter filter) {
        this.config.addFilter(filter);
    }
    
    public void removeFilter(final Filter filter) {
        this.config.removeFilter(filter);
    }
    
    private synchronized Configuration setConfiguration(final Configuration config) {
        if (config == null) {
            throw new NullPointerException("No Configuration was provided");
        }
        final Configuration config2 = this.config;
        config.addListener(this);
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("hostName", NetUtils.getLocalHostname());
        hashMap.put("contextName", this.name);
        config.addComponent("ContextProperties", hashMap);
        config.start();
        this.config = config;
        this.updateLoggers();
        if (config2 != null) {
            config2.removeListener(this);
            config2.stop();
        }
        final PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(this, "config", config2, config);
        final Iterator<PropertyChangeListener> iterator = (Iterator<PropertyChangeListener>)this.propertyChangeListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().propertyChange(propertyChangeEvent);
        }
        return config2;
    }
    
    public void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
        this.propertyChangeListeners.add(Assert.isNotNull(propertyChangeListener, "listener"));
    }
    
    public void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
        this.propertyChangeListeners.remove(propertyChangeListener);
    }
    
    public synchronized URI getConfigLocation() {
        return this.configLocation;
    }
    
    public synchronized void setConfigLocation(final URI configLocation) {
        this.configLocation = configLocation;
        this.reconfigure();
    }
    
    public synchronized void reconfigure() {
        LoggerContext.LOGGER.debug("Reconfiguration started for context " + this.name);
        this.setConfiguration(ConfigurationFactory.getInstance().getConfiguration(this.name, this.configLocation));
        LoggerContext.LOGGER.debug("Reconfiguration completed");
    }
    
    public void updateLoggers() {
        this.updateLoggers(this.config);
    }
    
    public void updateLoggers(final Configuration configuration) {
        final Iterator iterator = this.loggers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().updateConfiguration(configuration);
        }
    }
    
    @Override
    public synchronized void onChange(final Reconfigurable reconfigurable) {
        LoggerContext.LOGGER.debug("Reconfiguration started for context " + this.name);
        final Configuration reconfigure = reconfigurable.reconfigure();
        if (reconfigure != null) {
            this.setConfiguration(reconfigure);
            LoggerContext.LOGGER.debug("Reconfiguration completed");
        }
        else {
            LoggerContext.LOGGER.debug("Reconfiguration failed");
        }
    }
    
    protected Logger newInstance(final LoggerContext loggerContext, final String s, final MessageFactory messageFactory) {
        return new Logger(loggerContext, s, messageFactory);
    }
    
    @Override
    public org.apache.logging.log4j.Logger getLogger(final String s, final MessageFactory messageFactory) {
        return this.getLogger(s, messageFactory);
    }
    
    @Override
    public org.apache.logging.log4j.Logger getLogger(final String s) {
        return this.getLogger(s);
    }
    
    static ShutdownThread access$002(final LoggerContext loggerContext, final ShutdownThread shutdownThread) {
        return loggerContext.shutdownThread = shutdownThread;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
    
    private class ShutdownThread extends Thread
    {
        private final LoggerContext context;
        final LoggerContext this$0;
        
        public ShutdownThread(final LoggerContext this$0, final LoggerContext context) {
            this.this$0 = this$0;
            this.context = context;
        }
        
        @Override
        public void run() {
            LoggerContext.access$002(this.context, null);
            this.context.stop();
        }
    }
    
    public enum Status
    {
        INITIALIZED("INITIALIZED", 0), 
        STARTING("STARTING", 1), 
        STARTED("STARTED", 2), 
        STOPPING("STOPPING", 3), 
        STOPPED("STOPPED", 4);
        
        private static final Status[] $VALUES;
        
        private Status(final String s, final int n) {
        }
        
        static {
            $VALUES = new Status[] { Status.INITIALIZED, Status.STARTING, Status.STARTED, Status.STOPPING, Status.STOPPED };
        }
    }
}
