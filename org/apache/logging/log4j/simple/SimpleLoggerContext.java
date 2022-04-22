package org.apache.logging.log4j.simple;

import java.util.*;
import org.apache.logging.log4j.util.*;
import java.util.concurrent.*;
import java.io.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.spi.*;

public class SimpleLoggerContext implements LoggerContext
{
    protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    protected static final String SYSTEM_PREFIX = "org.apache.logging.log4j.simplelog.";
    private final Properties simpleLogProps;
    private final PropertiesUtil props;
    private final boolean showLogName;
    private final boolean showShortName;
    private final boolean showDateTime;
    private final boolean showContextMap;
    private final String dateTimeFormat;
    private final Level defaultLevel;
    private final PrintStream stream;
    private final ConcurrentMap loggers;
    
    public SimpleLoggerContext() {
        this.simpleLogProps = new Properties();
        this.loggers = new ConcurrentHashMap();
        this.props = new PropertiesUtil("log4j2.simplelog.properties");
        this.showContextMap = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showContextMap", false);
        this.showLogName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showlogname", false);
        this.showShortName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showShortLogname", true);
        this.showDateTime = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showdatetime", false);
        this.defaultLevel = Level.toLevel(this.props.getStringProperty("org.apache.logging.log4j.simplelog.level"), Level.ERROR);
        this.dateTimeFormat = (this.showDateTime ? this.props.getStringProperty("org.apache.logging.log4j.simplelog.dateTimeFormat", "yyyy/MM/dd HH:mm:ss:SSS zzz") : null);
        final String stringProperty = this.props.getStringProperty("org.apache.logging.log4j.simplelog.logFile", "system.err");
        PrintStream stream;
        if ("system.err".equalsIgnoreCase(stringProperty)) {
            stream = System.err;
        }
        else if ("system.out".equalsIgnoreCase(stringProperty)) {
            stream = System.out;
        }
        else {
            stream = new PrintStream(new FileOutputStream(stringProperty));
        }
        this.stream = stream;
    }
    
    @Override
    public Logger getLogger(final String s) {
        return this.getLogger(s, null);
    }
    
    @Override
    public Logger getLogger(final String s, final MessageFactory messageFactory) {
        if (this.loggers.containsKey(s)) {
            final Logger logger = (Logger)this.loggers.get(s);
            AbstractLogger.checkMessageFactory(logger, messageFactory);
            return logger;
        }
        this.loggers.putIfAbsent(s, new SimpleLogger(s, this.defaultLevel, this.showLogName, this.showShortName, this.showDateTime, this.showContextMap, this.dateTimeFormat, messageFactory, this.props, this.stream));
        return (Logger)this.loggers.get(s);
    }
    
    @Override
    public boolean hasLogger(final String s) {
        return false;
    }
    
    @Override
    public Object getExternalContext() {
        return null;
    }
}
