package org.apache.logging.log4j.core.appender.db.nosql.couch;

import org.apache.logging.log4j.*;
import org.lightcouch.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.appender.db.nosql.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "CouchDb", category = "Core", printObject = true)
public final class CouchDBProvider implements NoSQLProvider
{
    private static final int HTTP = 80;
    private static final int HTTPS = 443;
    private static final Logger LOGGER;
    private final CouchDbClient client;
    private final String description;
    
    private CouchDBProvider(final CouchDbClient client, final String s) {
        this.client = client;
        this.description = "couchDb{ " + s + " }";
    }
    
    @Override
    public CouchDBConnection getConnection() {
        return new CouchDBConnection(this.client);
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @PluginFactory
    public static CouchDBProvider createNoSQLProvider(@PluginAttribute("databaseName") final String s, @PluginAttribute("protocol") String lowerCase, @PluginAttribute("server") String s2, @PluginAttribute("port") final String s3, @PluginAttribute("username") final String s4, @PluginAttribute("password") final String s5, @PluginAttribute("factoryClassName") final String s6, @PluginAttribute("factoryMethodName") final String s7) {
        CouchDbClient couchDbClient;
        String s8;
        if (s6 != null && s6.length() > 0 && s7 != null && s7.length() > 0) {
            final Object invoke = Class.forName(s6).getMethod(s7, (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            if (invoke instanceof CouchDbClient) {
                couchDbClient = (CouchDbClient)invoke;
                s8 = "uri=" + couchDbClient.getDBUri();
            }
            else if (invoke instanceof CouchDbProperties) {
                final CouchDbProperties couchDbProperties = (CouchDbProperties)invoke;
                couchDbClient = new CouchDbClient(couchDbProperties);
                s8 = "uri=" + couchDbClient.getDBUri() + ", username=" + couchDbProperties.getUsername() + ", passwordHash=" + NameUtil.md5(s5 + CouchDBProvider.class.getName()) + ", maxConnections=" + couchDbProperties.getMaxConnections() + ", connectionTimeout=" + couchDbProperties.getConnectionTimeout() + ", socketTimeout=" + couchDbProperties.getSocketTimeout();
            }
            else {
                if (invoke == null) {
                    CouchDBProvider.LOGGER.error("The factory method [{}.{}()] returned null.", s6, s7);
                    return null;
                }
                CouchDBProvider.LOGGER.error("The factory method [{}.{}()] returned an unsupported type [{}].", s6, s7, ((CouchDbProperties)invoke).getClass().getName());
                return null;
            }
        }
        else {
            if (s == null || s.length() <= 0) {
                CouchDBProvider.LOGGER.error("No factory method was provided so the database name is required.");
                return null;
            }
            if (lowerCase != null && lowerCase.length() > 0) {
                lowerCase = lowerCase.toLowerCase();
                if (!lowerCase.equals("http") && !lowerCase.equals("https")) {
                    CouchDBProvider.LOGGER.error("Only protocols [http] and [https] are supported, [{}] specified.", lowerCase);
                    return null;
                }
            }
            else {
                lowerCase = "http";
                CouchDBProvider.LOGGER.warn("No protocol specified, using default port [http].");
            }
            final int int1 = AbstractAppender.parseInt(s3, lowerCase.equals("https") ? 443 : 80);
            if (Strings.isEmpty(s2)) {
                s2 = "localhost";
                CouchDBProvider.LOGGER.warn("No server specified, using default server localhost.");
            }
            if (Strings.isEmpty(s4) || Strings.isEmpty(s5)) {
                CouchDBProvider.LOGGER.error("You must provide a username and password for the CouchDB provider.");
                return null;
            }
            couchDbClient = new CouchDbClient(s, false, lowerCase, s2, int1, s4, s5);
            s8 = "uri=" + couchDbClient.getDBUri() + ", username=" + s4 + ", passwordHash=" + NameUtil.md5(s5 + CouchDBProvider.class.getName());
        }
        return new CouchDBProvider(couchDbClient, s8);
    }
    
    @Override
    public NoSQLConnection getConnection() {
        return this.getConnection();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
