package org.apache.logging.log4j.core.appender.db;

import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.appender.*;

public abstract class AbstractDatabaseManager extends AbstractManager
{
    private final ArrayList buffer;
    private final int bufferSize;
    private boolean connected;
    
    protected AbstractDatabaseManager(final String s, final int bufferSize) {
        super(s);
        this.connected = false;
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList(bufferSize + 1);
    }
    
    protected abstract void connectInternal() throws Exception;
    
    public final synchronized void connect() {
        if (!this.isConnected()) {
            this.connectInternal();
            this.connected = true;
        }
    }
    
    protected abstract void disconnectInternal() throws Exception;
    
    public final synchronized void disconnect() {
        this.flush();
        if (this.isConnected()) {
            this.disconnectInternal();
            this.connected = false;
        }
    }
    
    public final boolean isConnected() {
        return this.connected;
    }
    
    protected abstract void writeInternal(final LogEvent p0);
    
    public final synchronized void flush() {
        if (this.isConnected() && this.buffer.size() > 0) {
            final Iterator<LogEvent> iterator = this.buffer.iterator();
            while (iterator.hasNext()) {
                this.writeInternal(iterator.next());
            }
            this.buffer.clear();
        }
    }
    
    public final synchronized void write(final LogEvent logEvent) {
        if (this.bufferSize > 0) {
            this.buffer.add(logEvent);
            if (this.buffer.size() >= this.bufferSize || logEvent.isEndOfBatch()) {
                this.flush();
            }
        }
        else {
            this.writeInternal(logEvent);
        }
    }
    
    public final void releaseSub() {
        this.disconnect();
    }
    
    @Override
    public final String toString() {
        return this.getName();
    }
    
    protected static AbstractDatabaseManager getManager(final String s, final AbstractFactoryData abstractFactoryData, final ManagerFactory managerFactory) {
        return (AbstractDatabaseManager)AbstractManager.getManager(s, managerFactory, abstractFactoryData);
    }
    
    protected abstract static class AbstractFactoryData
    {
        private final int bufferSize;
        
        protected AbstractFactoryData(final int bufferSize) {
            this.bufferSize = bufferSize;
        }
        
        public int getBufferSize() {
            return this.bufferSize;
        }
    }
}
