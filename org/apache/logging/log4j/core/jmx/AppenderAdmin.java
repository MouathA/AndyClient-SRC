package org.apache.logging.log4j.core.jmx;

import org.apache.logging.log4j.core.*;
import javax.management.*;
import org.apache.logging.log4j.core.helpers.*;

public class AppenderAdmin implements AppenderAdminMBean
{
    private final String contextName;
    private final Appender appender;
    private final ObjectName objectName;
    
    public AppenderAdmin(final String s, final Appender appender) {
        this.contextName = (String)Assert.isNotNull(s, "contextName");
        this.appender = (Appender)Assert.isNotNull(appender, "appender");
        this.objectName = new ObjectName(String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s", Server.escape(this.contextName), Server.escape(appender.getName())));
    }
    
    public ObjectName getObjectName() {
        return this.objectName;
    }
    
    @Override
    public String getName() {
        return this.appender.getName();
    }
    
    @Override
    public String getLayout() {
        return String.valueOf(this.appender.getLayout());
    }
    
    @Override
    public boolean isExceptionSuppressed() {
        return this.appender.ignoreExceptions();
    }
    
    @Override
    public String getErrorHandler() {
        return String.valueOf(this.appender.getHandler());
    }
}
