package org.apache.logging.log4j.core.appender;

import java.util.concurrent.locks.*;
import org.apache.logging.log4j.core.*;

public abstract class AbstractOutputStreamAppender extends AbstractAppender
{
    protected final boolean immediateFlush;
    private OutputStreamManager manager;
    private final ReadWriteLock rwLock;
    private final Lock readLock;
    private final Lock writeLock;
    
    protected AbstractOutputStreamAppender(final String s, final Layout layout, final Filter filter, final boolean b, final boolean immediateFlush, final OutputStreamManager manager) {
        super(s, filter, layout, b);
        this.rwLock = new ReentrantReadWriteLock();
        this.readLock = this.rwLock.readLock();
        this.writeLock = this.rwLock.writeLock();
        this.manager = manager;
        this.immediateFlush = immediateFlush;
    }
    
    protected OutputStreamManager getManager() {
        return this.manager;
    }
    
    protected void replaceManager(final OutputStreamManager manager) {
        this.writeLock.lock();
        final OutputStreamManager manager2 = this.manager;
        this.manager = manager;
        manager2.release();
        this.writeLock.unlock();
    }
    
    @Override
    public void start() {
        if (this.getLayout() == null) {
            AbstractOutputStreamAppender.LOGGER.error("No layout set for the appender named [" + this.getName() + "].");
        }
        if (this.manager == null) {
            AbstractOutputStreamAppender.LOGGER.error("No OutputStreamManager set for the appender named [" + this.getName() + "].");
        }
        super.start();
    }
    
    @Override
    public void stop() {
        super.stop();
        this.manager.release();
    }
    
    @Override
    public void append(final LogEvent logEvent) {
        this.readLock.lock();
        final byte[] byteArray = this.getLayout().toByteArray(logEvent);
        if (byteArray.length > 0) {
            this.manager.write(byteArray);
            if (this.immediateFlush || logEvent.isEndOfBatch()) {
                this.manager.flush();
            }
        }
        this.readLock.unlock();
    }
}
