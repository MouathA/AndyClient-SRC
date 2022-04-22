package org.apache.logging.log4j.core.appender.db.jdbc;

import org.apache.logging.log4j.*;
import java.sql.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "DriverManager", category = "Core", elementType = "connectionSource", printObject = true)
public final class DriverManagerConnectionSource implements ConnectionSource
{
    private static final Logger LOGGER;
    private final String databasePassword;
    private final String databaseUrl;
    private final String databaseUsername;
    private final String description;
    
    private DriverManagerConnectionSource(final String databaseUrl, final String databaseUsername, final String databasePassword) {
        this.databaseUrl = databaseUrl;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.description = "driverManager{ url=" + this.databaseUrl + ", username=" + this.databaseUsername + ", passwordHash=" + NameUtil.md5(this.databasePassword + this.getClass().getName()) + " }";
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        if (this.databaseUsername == null) {
            return DriverManager.getConnection(this.databaseUrl);
        }
        return DriverManager.getConnection(this.databaseUrl, this.databaseUsername, this.databasePassword);
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @PluginFactory
    public static DriverManagerConnectionSource createConnectionSource(@PluginAttribute("url") final String s, @PluginAttribute("username") String s2, @PluginAttribute("password") String s3) {
        if (Strings.isEmpty(s)) {
            DriverManagerConnectionSource.LOGGER.error("No JDBC URL specified for the database.");
            return null;
        }
        if (DriverManager.getDriver(s) == null) {
            DriverManagerConnectionSource.LOGGER.error("No matching driver found for database URL [" + s + "].");
            return null;
        }
        if (s2 == null || s2.trim().isEmpty()) {
            s2 = null;
            s3 = null;
        }
        return new DriverManagerConnectionSource(s, s2, s3);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
