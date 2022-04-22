package org.apache.logging.log4j.core.appender.db.nosql.mongo;

import org.apache.logging.log4j.*;
import com.mongodb.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.appender.db.nosql.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "MongoDb", category = "Core", printObject = true)
public final class MongoDBProvider implements NoSQLProvider
{
    private static final Logger LOGGER;
    private final String collectionName;
    private final DB database;
    private final String description;
    private final WriteConcern writeConcern;
    
    private MongoDBProvider(final DB database, final WriteConcern writeConcern, final String collectionName, final String s) {
        this.database = database;
        this.writeConcern = writeConcern;
        this.collectionName = collectionName;
        this.description = "mongoDb{ " + s + " }";
    }
    
    @Override
    public MongoDBConnection getConnection() {
        return new MongoDBConnection(this.database, this.writeConcern, this.collectionName);
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @PluginFactory
    public static MongoDBProvider createNoSQLProvider(@PluginAttribute("collectionName") final String s, @PluginAttribute("writeConcernConstant") final String s2, @PluginAttribute("writeConcernConstantClass") final String s3, @PluginAttribute("databaseName") final String s4, @PluginAttribute("server") final String s5, @PluginAttribute("port") final String s6, @PluginAttribute("username") final String s7, @PluginAttribute("password") final String s8, @PluginAttribute("factoryClassName") final String s9, @PluginAttribute("factoryMethodName") final String s10) {
        DB db;
        String s11;
        if (s9 != null && s9.length() > 0 && s10 != null && s10.length() > 0) {
            final Object invoke = Class.forName(s9).getMethod(s10, (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            if (invoke instanceof DB) {
                db = (DB)invoke;
            }
            else if (invoke instanceof MongoClient) {
                if (s4 == null || s4.length() <= 0) {
                    MongoDBProvider.LOGGER.error("The factory method [{}.{}()] returned a MongoClient so the database name is required.", s9, s10);
                    return null;
                }
                db = ((MongoClient)invoke).getDB(s4);
            }
            else {
                if (invoke == null) {
                    MongoDBProvider.LOGGER.error("The factory method [{}.{}()] returned null.", s9, s10);
                    return null;
                }
                MongoDBProvider.LOGGER.error("The factory method [{}.{}()] returned an unsupported type [{}].", s9, s10, ((MongoClient)invoke).getClass().getName());
                return null;
            }
            final String string = "database=" + db.getName();
            final List allAddress = db.getMongo().getAllAddress();
            if (allAddress.size() == 1) {
                s11 = string + ", server=" + allAddress.get(0).getHost() + ", port=" + allAddress.get(0).getPort();
            }
            else {
                String s12 = string + ", servers=[";
                for (final ServerAddress serverAddress : allAddress) {
                    s12 = s12 + " { " + serverAddress.getHost() + ", " + serverAddress.getPort() + " } ";
                }
                s11 = s12 + "]";
            }
        }
        else {
            if (s4 == null || s4.length() <= 0) {
                MongoDBProvider.LOGGER.error("No factory method was provided so the database name is required.");
                return null;
            }
            s11 = "database=" + s4;
            if (s5 != null && s5.length() > 0) {
                final int int1 = AbstractAppender.parseInt(s6, 0);
                s11 = s11 + ", server=" + s5;
                if (int1 > 0) {
                    s11 = s11 + ", port=" + int1;
                    db = new MongoClient(s5, int1).getDB(s4);
                }
                else {
                    db = new MongoClient(s5).getDB(s4);
                }
            }
            else {
                db = new MongoClient().getDB(s4);
            }
        }
        if (!db.isAuthenticated()) {
            if (s7 == null || s7.length() <= 0 || s8 == null || s8.length() <= 0) {
                MongoDBProvider.LOGGER.error("The database is not already authenticated so you must supply a username and password for the MongoDB provider.");
                return null;
            }
            s11 = s11 + ", username=" + s7 + ", passwordHash=" + NameUtil.md5(s8 + MongoDBProvider.class.getName());
            MongoDBConnection.authenticate(db, s7, s8);
        }
        WriteConcern writeConcern;
        if (s2 != null && s2.length() > 0) {
            if (s3 != null && s3.length() > 0) {
                writeConcern = (WriteConcern)Class.forName(s3).getField(s2).get(null);
            }
            else {
                writeConcern = WriteConcern.valueOf(s2);
                if (writeConcern == null) {
                    MongoDBProvider.LOGGER.warn("Write concern constant [{}] not found, using default.", s2);
                    writeConcern = WriteConcern.ACKNOWLEDGED;
                }
            }
        }
        else {
            writeConcern = WriteConcern.ACKNOWLEDGED;
        }
        return new MongoDBProvider(db, writeConcern, s, s11);
    }
    
    @Override
    public NoSQLConnection getConnection() {
        return this.getConnection();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
