package org.apache.logging.log4j.core.config;

import java.util.concurrent.*;

public class Loggers
{
    private final ConcurrentMap map;
    private final LoggerConfig root;
    
    public Loggers(final ConcurrentMap map, final LoggerConfig root) {
        this.map = map;
        this.root = root;
    }
    
    public ConcurrentMap getMap() {
        return this.map;
    }
    
    public LoggerConfig getRoot() {
        return this.root;
    }
}
