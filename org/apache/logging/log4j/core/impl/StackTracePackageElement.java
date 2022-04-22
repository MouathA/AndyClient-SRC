package org.apache.logging.log4j.core.impl;

import java.io.*;

public class StackTracePackageElement implements Serializable
{
    private static final long serialVersionUID = -2171069569241280505L;
    private final String location;
    private final String version;
    private final boolean isExact;
    
    public StackTracePackageElement(final String location, final String version, final boolean isExact) {
        this.location = location;
        this.version = version;
        this.isExact = isExact;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public boolean isExact() {
        return this.isExact;
    }
    
    @Override
    public String toString() {
        return (this.isExact ? "" : "~") + "[" + this.location + ":" + this.version + "]";
    }
}
