package org.apache.logging.log4j.core.appender.rolling.helper;

import org.apache.logging.log4j.*;
import java.io.*;
import org.apache.logging.log4j.status.*;

public abstract class AbstractAction implements Action
{
    protected static final Logger LOGGER;
    private boolean complete;
    private boolean interrupted;
    
    protected AbstractAction() {
        this.complete = false;
        this.interrupted = false;
    }
    
    @Override
    public abstract boolean execute() throws IOException;
    
    @Override
    public synchronized void run() {
        if (!this.interrupted) {
            this.execute();
            this.complete = true;
            this.interrupted = true;
        }
    }
    
    @Override
    public synchronized void close() {
        this.interrupted = true;
    }
    
    @Override
    public boolean isComplete() {
        return this.complete;
    }
    
    protected void reportException(final Exception ex) {
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
