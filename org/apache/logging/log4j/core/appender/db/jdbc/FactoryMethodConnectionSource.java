package org.apache.logging.log4j.core.appender.db.jdbc;

import org.apache.logging.log4j.*;
import javax.sql.*;
import java.sql.*;
import org.apache.logging.log4j.core.helpers.*;
import java.lang.reflect.*;
import java.io.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "ConnectionFactory", category = "Core", elementType = "connectionSource", printObject = true)
public final class FactoryMethodConnectionSource implements ConnectionSource
{
    private static final Logger LOGGER;
    private final DataSource dataSource;
    private final String description;
    
    private FactoryMethodConnectionSource(final DataSource dataSource, final String s, final String s2, final String s3) {
        this.dataSource = dataSource;
        this.description = "factory{ public static " + s3 + " " + s + "." + s2 + "() }";
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @PluginFactory
    public static FactoryMethodConnectionSource createConnectionSource(@PluginAttribute("class") final String s, @PluginAttribute("method") final String s2) {
        if (Strings.isEmpty(s) || Strings.isEmpty(s2)) {
            FactoryMethodConnectionSource.LOGGER.error("No class name or method name specified for the connection factory method.");
            return null;
        }
        final Method method = Class.forName(s).getMethod(s2, (Class<?>[])new Class[0]);
        final Class<?> returnType = method.getReturnType();
        String s3 = returnType.getName();
        DataSource dataSource;
        if (returnType == DataSource.class) {
            dataSource = (DataSource)method.invoke(null, new Object[0]);
            s3 = s3 + "[" + dataSource + "]";
        }
        else {
            if (returnType != Connection.class) {
                FactoryMethodConnectionSource.LOGGER.error("Method [{}.{}()] returns unsupported type [{}].", s, s2, returnType.getName());
                return null;
            }
            dataSource = new DataSource(method) {
                final Method val$method;
                
                @Override
                public Connection getConnection() throws SQLException {
                    return (Connection)this.val$method.invoke(null, new Object[0]);
                }
                
                @Override
                public Connection getConnection(final String s, final String s2) throws SQLException {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public int getLoginTimeout() throws SQLException {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public PrintWriter getLogWriter() throws SQLException {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public java.util.logging.Logger getParentLogger() {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public boolean isWrapperFor(final Class clazz) throws SQLException {
                    return false;
                }
                
                @Override
                public void setLoginTimeout(final int n) throws SQLException {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void setLogWriter(final PrintWriter printWriter) throws SQLException {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public Object unwrap(final Class clazz) throws SQLException {
                    return null;
                }
            };
        }
        return new FactoryMethodConnectionSource(dataSource, s, s2, s3);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
