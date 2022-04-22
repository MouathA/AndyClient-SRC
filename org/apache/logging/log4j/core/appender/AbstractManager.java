package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.logging.log4j.status.*;
import java.util.concurrent.locks.*;

public abstract class AbstractManager
{
    protected static final Logger LOGGER;
    private static final Map MAP;
    private static final Lock LOCK;
    protected int count;
    private final String name;
    
    protected AbstractManager(final String name) {
        this.name = name;
        AbstractManager.LOGGER.debug("Starting {} {}", this.getClass().getSimpleName(), name);
    }
    
    public static AbstractManager getManager(final String s, final ManagerFactory managerFactory, final Object o) {
        AbstractManager.LOCK.lock();
        AbstractManager abstractManager = AbstractManager.MAP.get(s);
        if (abstractManager == null) {
            abstractManager = (AbstractManager)managerFactory.createManager(s, o);
            if (abstractManager == null) {
                throw new IllegalStateException("Unable to create a manager");
            }
            AbstractManager.MAP.put(s, abstractManager);
        }
        final AbstractManager abstractManager2 = abstractManager;
        ++abstractManager2.count;
        final AbstractManager abstractManager3 = abstractManager;
        AbstractManager.LOCK.unlock();
        return abstractManager3;
    }
    
    public static boolean hasManager(final String s) {
        AbstractManager.LOCK.lock();
        final boolean containsKey = AbstractManager.MAP.containsKey(s);
        AbstractManager.LOCK.unlock();
        return containsKey;
    }
    
    protected void releaseSub() {
    }
    
    protected int getCount() {
        return this.count;
    }
    
    public void release() {
        AbstractManager.LOCK.lock();
        --this.count;
        if (this.count <= 0) {
            AbstractManager.MAP.remove(this.name);
            AbstractManager.LOGGER.debug("Shutting down {} {}", this.getClass().getSimpleName(), this.getName());
            this.releaseSub();
        }
        AbstractManager.LOCK.unlock();
    }
    
    public String getName() {
        return this.name;
    }
    
    public Map getContentFormat() {
        return new HashMap();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        MAP = new HashMap();
        LOCK = new ReentrantLock();
    }
}
