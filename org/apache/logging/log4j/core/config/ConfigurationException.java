package org.apache.logging.log4j.core.config;

public class ConfigurationException extends RuntimeException
{
    private static final long serialVersionUID = -2413951820300775294L;
    
    public ConfigurationException(final String s) {
        super(s);
    }
    
    public ConfigurationException(final String s, final Throwable t) {
        super(s, t);
    }
}
