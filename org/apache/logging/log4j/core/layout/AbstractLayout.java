package org.apache.logging.log4j.core.layout;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.status.*;

public abstract class AbstractLayout implements Layout
{
    protected static final Logger LOGGER;
    protected byte[] header;
    protected byte[] footer;
    
    @Override
    public byte[] getHeader() {
        return this.header;
    }
    
    public void setHeader(final byte[] header) {
        this.header = header;
    }
    
    @Override
    public byte[] getFooter() {
        return this.footer;
    }
    
    public void setFooter(final byte[] footer) {
        this.footer = footer;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
