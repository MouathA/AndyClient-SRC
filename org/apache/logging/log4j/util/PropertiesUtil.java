package org.apache.logging.log4j.util;

import org.apache.logging.log4j.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.status.*;

public class PropertiesUtil
{
    private static final PropertiesUtil LOG4J_PROPERTIES;
    private static final Logger LOGGER;
    private final Properties props;
    
    public PropertiesUtil(final Properties props) {
        this.props = props;
    }
    
    static Properties loadClose(final InputStream inputStream, final Object o) {
        final Properties properties = new Properties();
        if (null != inputStream) {
            properties.load(inputStream);
            inputStream.close();
        }
        return properties;
    }
    
    public PropertiesUtil(final String s) {
        this.props = loadClose(ProviderUtil.findClassLoader().getResourceAsStream(s), s);
    }
    
    public static PropertiesUtil getProperties() {
        return PropertiesUtil.LOG4J_PROPERTIES;
    }
    
    public String getStringProperty(final String s) {
        final String property = System.getProperty(s);
        return (property == null) ? this.props.getProperty(s) : property;
    }
    
    public int getIntegerProperty(final String s, final int n) {
        String s2 = System.getProperty(s);
        if (s2 == null) {
            s2 = this.props.getProperty(s);
        }
        if (s2 != null) {
            return Integer.parseInt(s2);
        }
        return n;
    }
    
    public long getLongProperty(final String s, final long n) {
        String s2 = System.getProperty(s);
        if (s2 == null) {
            s2 = this.props.getProperty(s);
        }
        if (s2 != null) {
            return Long.parseLong(s2);
        }
        return n;
    }
    
    public String getStringProperty(final String s, final String s2) {
        final String stringProperty = this.getStringProperty(s);
        return (stringProperty == null) ? s2 : stringProperty;
    }
    
    public boolean getBooleanProperty(final String s) {
        return this.getBooleanProperty(s, false);
    }
    
    public boolean getBooleanProperty(final String s, final boolean b) {
        final String stringProperty = this.getStringProperty(s);
        return (stringProperty == null) ? b : "true".equalsIgnoreCase(stringProperty);
    }
    
    public static Properties getSystemProperties() {
        return new Properties(System.getProperties());
    }
    
    static {
        LOG4J_PROPERTIES = new PropertiesUtil("log4j2.component.properties");
        LOGGER = StatusLogger.getLogger();
    }
}
