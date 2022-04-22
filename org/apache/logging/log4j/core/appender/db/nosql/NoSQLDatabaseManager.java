package org.apache.logging.log4j.core.appender.db.nosql;

import org.apache.logging.log4j.core.appender.db.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.appender.*;

public final class NoSQLDatabaseManager extends AbstractDatabaseManager
{
    private static final NoSQLDatabaseManagerFactory FACTORY;
    private final NoSQLProvider provider;
    private NoSQLConnection connection;
    
    private NoSQLDatabaseManager(final String s, final int n, final NoSQLProvider provider) {
        super(s, n);
        this.provider = provider;
    }
    
    @Override
    protected void connectInternal() {
        this.connection = this.provider.getConnection();
    }
    
    @Override
    protected void disconnectInternal() {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }
    
    @Override
    protected void writeInternal(final LogEvent logEvent) {
        if (!this.isConnected() || this.connection == null || this.connection.isClosed()) {
            throw new AppenderLoggingException("Cannot write logging event; NoSQL manager not connected to the database.");
        }
        final NoSQLObject object = this.connection.createObject();
        object.set("level", logEvent.getLevel());
        object.set("loggerName", logEvent.getLoggerName());
        object.set("message", (logEvent.getMessage() == null) ? null : logEvent.getMessage().getFormattedMessage());
        final StackTraceElement source = logEvent.getSource();
        if (source == null) {
            object.set("source", (Object)null);
        }
        else {
            object.set("source", this.convertStackTraceElement(source));
        }
        Marker marker = logEvent.getMarker();
        if (marker == null) {
            object.set("marker", (Object)null);
        }
        else {
            NoSQLObject object2;
            final NoSQLObject noSQLObject = object2 = this.connection.createObject();
            object2.set("name", marker.getName());
            while (marker.getParent() != null) {
                marker = marker.getParent();
                final NoSQLObject object3 = this.connection.createObject();
                object3.set("name", marker.getName());
                object2.set("parent", object3);
                object2 = object3;
            }
            object.set("marker", noSQLObject);
        }
        object.set("threadName", logEvent.getThreadName());
        object.set("millis", logEvent.getMillis());
        object.set("date", new Date(logEvent.getMillis()));
        Throwable t = logEvent.getThrown();
        if (t == null) {
            object.set("thrown", (Object)null);
        }
        else {
            NoSQLObject object4;
            final NoSQLObject noSQLObject2 = object4 = this.connection.createObject();
            object4.set("type", t.getClass().getName());
            object4.set("message", t.getMessage());
            object4.set("stackTrace", this.convertStackTrace(t.getStackTrace()));
            while (t.getCause() != null) {
                t = t.getCause();
                final NoSQLObject object5 = this.connection.createObject();
                object5.set("type", t.getClass().getName());
                object5.set("message", t.getMessage());
                object5.set("stackTrace", this.convertStackTrace(t.getStackTrace()));
                object4.set("cause", object5);
                object4 = object5;
            }
            object.set("thrown", noSQLObject2);
        }
        final Map contextMap = logEvent.getContextMap();
        if (contextMap == null) {
            object.set("contextMap", (Object)null);
        }
        else {
            final NoSQLObject object6 = this.connection.createObject();
            for (final Map.Entry<String, V> entry : contextMap.entrySet()) {
                object6.set(entry.getKey(), entry.getValue());
            }
            object.set("contextMap", object6);
        }
        final ThreadContext.ContextStack contextStack = logEvent.getContextStack();
        if (contextStack == null) {
            object.set("contextStack", (Object)null);
        }
        else {
            object.set("contextStack", contextStack.asList().toArray());
        }
        this.connection.insertObject(object);
    }
    
    private NoSQLObject[] convertStackTrace(final StackTraceElement[] array) {
        final NoSQLObject[] list = this.connection.createList(array.length);
        while (0 < array.length) {
            list[0] = this.convertStackTraceElement(array[0]);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    private NoSQLObject convertStackTraceElement(final StackTraceElement stackTraceElement) {
        final NoSQLObject object = this.connection.createObject();
        object.set("className", stackTraceElement.getClassName());
        object.set("methodName", stackTraceElement.getMethodName());
        object.set("fileName", stackTraceElement.getFileName());
        object.set("lineNumber", stackTraceElement.getLineNumber());
        return object;
    }
    
    public static NoSQLDatabaseManager getNoSQLDatabaseManager(final String s, final int n, final NoSQLProvider noSQLProvider) {
        return (NoSQLDatabaseManager)AbstractDatabaseManager.getManager(s, new FactoryData(n, noSQLProvider), NoSQLDatabaseManager.FACTORY);
    }
    
    NoSQLDatabaseManager(final String s, final int n, final NoSQLProvider noSQLProvider, final NoSQLDatabaseManager$1 object) {
        this(s, n, noSQLProvider);
    }
    
    static {
        FACTORY = new NoSQLDatabaseManagerFactory(null);
    }
    
    private static final class NoSQLDatabaseManagerFactory implements ManagerFactory
    {
        private NoSQLDatabaseManagerFactory() {
        }
        
        public NoSQLDatabaseManager createManager(final String s, final FactoryData factoryData) {
            return new NoSQLDatabaseManager(s, factoryData.getBufferSize(), FactoryData.access$100(factoryData), null);
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        NoSQLDatabaseManagerFactory(final NoSQLDatabaseManager$1 object) {
            this();
        }
    }
    
    private static final class FactoryData extends AbstractFactoryData
    {
        private final NoSQLProvider provider;
        
        protected FactoryData(final int n, final NoSQLProvider provider) {
            super(n);
            this.provider = provider;
        }
        
        static NoSQLProvider access$100(final FactoryData factoryData) {
            return factoryData.provider;
        }
    }
}
