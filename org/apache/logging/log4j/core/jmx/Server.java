package org.apache.logging.log4j.core.jmx;

import org.apache.logging.log4j.core.selector.*;
import org.apache.logging.log4j.status.*;
import java.lang.management.*;
import java.beans.*;
import java.util.concurrent.*;
import javax.management.*;
import org.apache.logging.log4j.core.config.*;
import java.util.*;
import org.apache.logging.log4j.core.*;

public final class Server
{
    private static final String PROPERTY_DISABLE_JMX = "log4j2.disable.jmx";
    
    private Server() {
    }
    
    public static String escape(final String s) {
        final StringBuilder sb = new StringBuilder(s.length() * 2);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            switch (char1) {
                case 42:
                case 44:
                case 58:
                case 61:
                case 63:
                case 92: {
                    sb.append('\\');
                    break;
                }
            }
            sb.append(char1);
            int n = 0;
            ++n;
        }
        if (true) {
            sb.insert(0, '\"');
            sb.append('\"');
        }
        return sb.toString();
    }
    
    public static void registerMBeans(final ContextSelector contextSelector) throws JMException {
        if (Boolean.getBoolean("log4j2.disable.jmx")) {
            StatusLogger.getLogger().debug("JMX disabled for log4j2. Not registering MBeans.");
            return;
        }
        registerMBeans(contextSelector, ManagementFactory.getPlatformMBeanServer());
    }
    
    public static void registerMBeans(final ContextSelector contextSelector, final MBeanServer mBeanServer) throws JMException {
        if (Boolean.getBoolean("log4j2.disable.jmx")) {
            StatusLogger.getLogger().debug("JMX disabled for log4j2. Not registering MBeans.");
            return;
        }
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        registerStatusLogger(mBeanServer, fixedThreadPool);
        registerContextSelector(contextSelector, mBeanServer, fixedThreadPool);
        final List loggerContexts = contextSelector.getLoggerContexts();
        registerContexts(loggerContexts, mBeanServer, fixedThreadPool);
        for (final LoggerContext loggerContext : loggerContexts) {
            loggerContext.addPropertyChangeListener(new PropertyChangeListener(loggerContext, mBeanServer, (Executor)fixedThreadPool) {
                final LoggerContext val$context;
                final MBeanServer val$mbs;
                final Executor val$executor;
                
                @Override
                public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
                    if (!"config".equals(propertyChangeEvent.getPropertyName())) {
                        return;
                    }
                    Server.access$000(this.val$context, this.val$mbs);
                    Server.access$100(this.val$context, this.val$mbs);
                    Server.access$200(this.val$context, this.val$mbs, this.val$executor);
                    Server.access$300(this.val$context, this.val$mbs, this.val$executor);
                }
            });
        }
    }
    
    private static void registerStatusLogger(final MBeanServer mBeanServer, final Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        final StatusLoggerAdmin statusLoggerAdmin = new StatusLoggerAdmin(executor);
        mBeanServer.registerMBean(statusLoggerAdmin, statusLoggerAdmin.getObjectName());
    }
    
    private static void registerContextSelector(final ContextSelector contextSelector, final MBeanServer mBeanServer, final Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        final ContextSelectorAdmin contextSelectorAdmin = new ContextSelectorAdmin(contextSelector);
        mBeanServer.registerMBean(contextSelectorAdmin, contextSelectorAdmin.getObjectName());
    }
    
    private static void registerContexts(final List list, final MBeanServer mBeanServer, final Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        final Iterator<LoggerContext> iterator = list.iterator();
        while (iterator.hasNext()) {
            final LoggerContextAdmin loggerContextAdmin = new LoggerContextAdmin(iterator.next(), executor);
            mBeanServer.registerMBean(loggerContextAdmin, loggerContextAdmin.getObjectName());
        }
    }
    
    private static void unregisterLoggerConfigs(final LoggerContext loggerContext, final MBeanServer mBeanServer) {
        unregisterAllMatching(String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s", loggerContext.getName(), "*"), mBeanServer);
    }
    
    private static void unregisterAppenders(final LoggerContext loggerContext, final MBeanServer mBeanServer) {
        unregisterAllMatching(String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s", loggerContext.getName(), "*"), mBeanServer);
    }
    
    private static void unregisterAllMatching(final String s, final MBeanServer mBeanServer) {
        final Iterator<ObjectName> iterator = mBeanServer.queryNames(new ObjectName(s), null).iterator();
        while (iterator.hasNext()) {
            mBeanServer.unregisterMBean(iterator.next());
        }
    }
    
    private static void registerLoggerConfigs(final LoggerContext loggerContext, final MBeanServer mBeanServer, final Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        final Map loggers = loggerContext.getConfiguration().getLoggers();
        final Iterator<String> iterator = loggers.keySet().iterator();
        while (iterator.hasNext()) {
            final LoggerConfigAdmin loggerConfigAdmin = new LoggerConfigAdmin(loggerContext.getName(), (LoggerConfig)loggers.get(iterator.next()));
            mBeanServer.registerMBean(loggerConfigAdmin, loggerConfigAdmin.getObjectName());
        }
    }
    
    private static void registerAppenders(final LoggerContext loggerContext, final MBeanServer mBeanServer, final Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        final Map appenders = loggerContext.getConfiguration().getAppenders();
        final Iterator<String> iterator = appenders.keySet().iterator();
        while (iterator.hasNext()) {
            final AppenderAdmin appenderAdmin = new AppenderAdmin(loggerContext.getName(), (Appender)appenders.get(iterator.next()));
            mBeanServer.registerMBean(appenderAdmin, appenderAdmin.getObjectName());
        }
    }
    
    static void access$000(final LoggerContext loggerContext, final MBeanServer mBeanServer) {
        unregisterLoggerConfigs(loggerContext, mBeanServer);
    }
    
    static void access$100(final LoggerContext loggerContext, final MBeanServer mBeanServer) {
        unregisterAppenders(loggerContext, mBeanServer);
    }
    
    static void access$200(final LoggerContext loggerContext, final MBeanServer mBeanServer, final Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        registerLoggerConfigs(loggerContext, mBeanServer, executor);
    }
    
    static void access$300(final LoggerContext loggerContext, final MBeanServer mBeanServer, final Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        registerAppenders(loggerContext, mBeanServer, executor);
    }
}
