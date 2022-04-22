package org.apache.logging.log4j.status;

import org.apache.logging.log4j.*;
import java.io.*;
import org.apache.logging.log4j.util.*;

public class StatusConsoleListener implements StatusListener
{
    private static final String STATUS_LEVEL = "org.apache.logging.log4j.StatusLevel";
    private Level level;
    private String[] filters;
    private final PrintStream stream;
    
    public StatusConsoleListener() {
        this.level = Level.FATAL;
        this.filters = null;
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.StatusLevel");
        if (stringProperty != null) {
            this.level = Level.toLevel(stringProperty, Level.FATAL);
        }
        this.stream = System.out;
    }
    
    public StatusConsoleListener(final Level level) {
        this.level = Level.FATAL;
        this.filters = null;
        this.level = level;
        this.stream = System.out;
    }
    
    public StatusConsoleListener(final Level level, final PrintStream stream) {
        this.level = Level.FATAL;
        this.filters = null;
        this.level = level;
        this.stream = stream;
    }
    
    public void setLevel(final Level level) {
        this.level = level;
    }
    
    @Override
    public Level getStatusLevel() {
        return this.level;
    }
    
    @Override
    public void log(final StatusData statusData) {
        if (statusData == null) {
            this.stream.println(statusData.getFormattedStatus());
        }
    }
    
    public void setFilters(final String... filters) {
        this.filters = filters;
    }
}
