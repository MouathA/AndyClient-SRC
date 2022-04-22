package org.apache.logging.log4j.core.jmx;

import javax.management.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.*;
import java.util.*;

public class LoggerConfigAdmin implements LoggerConfigAdminMBean
{
    private final String contextName;
    private final LoggerConfig loggerConfig;
    private final ObjectName objectName;
    
    public LoggerConfigAdmin(final String s, final LoggerConfig loggerConfig) {
        this.contextName = (String)Assert.isNotNull(s, "contextName");
        this.loggerConfig = (LoggerConfig)Assert.isNotNull(loggerConfig, "loggerConfig");
        this.objectName = new ObjectName(String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s", Server.escape(this.contextName), Server.escape(loggerConfig.getName())));
    }
    
    public ObjectName getObjectName() {
        return this.objectName;
    }
    
    @Override
    public String getName() {
        return this.loggerConfig.getName();
    }
    
    @Override
    public String getLevel() {
        return this.loggerConfig.getLevel().name();
    }
    
    @Override
    public void setLevel(final String s) {
        this.loggerConfig.setLevel(Level.valueOf(s));
    }
    
    @Override
    public boolean isAdditive() {
        return this.loggerConfig.isAdditive();
    }
    
    @Override
    public void setAdditive(final boolean additive) {
        this.loggerConfig.setAdditive(additive);
    }
    
    @Override
    public boolean isIncludeLocation() {
        return this.loggerConfig.isIncludeLocation();
    }
    
    @Override
    public String getFilter() {
        return String.valueOf(this.loggerConfig.getFilter());
    }
    
    @Override
    public String[] getAppenderRefs() {
        final List appenderRefs = this.loggerConfig.getAppenderRefs();
        final String[] array = new String[appenderRefs.size()];
        while (0 < array.length) {
            array[0] = appenderRefs.get(0).getRef();
            int n = 0;
            ++n;
        }
        return array;
    }
}
