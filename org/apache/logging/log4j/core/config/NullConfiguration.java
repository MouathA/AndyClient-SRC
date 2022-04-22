package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.*;

public class NullConfiguration extends BaseConfiguration
{
    public static final String NULL_NAME = "Null";
    
    public NullConfiguration() {
        this.setName("Null");
        this.getRootLogger().setLevel(Level.OFF);
    }
}
