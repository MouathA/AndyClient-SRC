package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.util.*;

public class DefaultConfiguration extends BaseConfiguration
{
    public static final String DEFAULT_NAME = "Default";
    public static final String DEFAULT_LEVEL = "org.apache.logging.log4j.level";
    
    public DefaultConfiguration() {
        this.setName("Default");
        final ConsoleAppender appender = ConsoleAppender.createAppender(PatternLayout.createLayout("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", null, null, null, null), null, "SYSTEM_OUT", "Console", "false", "true");
        appender.start();
        this.addAppender(appender);
        final LoggerConfig rootLogger = this.getRootLogger();
        rootLogger.addAppender(appender, null, null);
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level");
        rootLogger.setLevel((stringProperty != null && Level.valueOf(stringProperty) != null) ? Level.valueOf(stringProperty) : Level.ERROR);
    }
    
    @Override
    protected void doConfigure() {
    }
}
