package org.apache.logging.log4j.core.appender.db;

import org.apache.logging.log4j.core.appender.*;
import java.util.concurrent.locks.*;
import org.apache.logging.log4j.core.*;

public abstract class AbstractDatabaseAppender extends AbstractAppender
{
    private final ReadWriteLock lock;
    private final Lock readLock;
    private final Lock writeLock;
    private AbstractDatabaseManager manager;
    
    protected AbstractDatabaseAppender(final String s, final Filter filter, final boolean b, final AbstractDatabaseManager manager) {
        super(s, filter, null, b);
        this.lock = new ReentrantReadWriteLock();
        this.readLock = this.lock.readLock();
        this.writeLock = this.lock.writeLock();
        this.manager = manager;
    }
    
    @Override
    public final Layout getLayout() {
        return null;
    }
    
    public final AbstractDatabaseManager getManager() {
        return this.manager;
    }
    
    @Override
    public final void start() {
        if (this.getManager() == null) {
            AbstractDatabaseAppender.LOGGER.error("No AbstractDatabaseManager set for the appender named [{}].", this.getName());
        }
        super.start();
        if (this.getManager() != null) {
            this.getManager().connect();
        }
    }
    
    @Override
    public final void stop() {
        super.stop();
        if (this.getManager() != null) {
            this.getManager().release();
        }
    }
    
    @Override
    public final void append(final LogEvent logEvent) {
        this.readLock.lock();
        this.getManager().write(logEvent);
        this.readLock.unlock();
    }
    
    protected final void replaceManager(final AbstractDatabaseManager manager) {
        this.writeLock.lock();
        final AbstractDatabaseManager manager2 = this.getManager();
        if (!manager.isConnected()) {
            manager.connect();
        }
        this.manager = manager;
        manager2.release();
        this.writeLock.unlock();
    }
}
