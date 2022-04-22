package org.apache.logging.log4j.core.appender.db.nosql.mongo;

import org.apache.logging.log4j.core.appender.db.nosql.*;
import org.apache.logging.log4j.core.appender.*;
import com.mongodb.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.*;
import org.bson.*;

public final class MongoDBConnection implements NoSQLConnection
{
    private static final Logger LOGGER;
    private final DBCollection collection;
    private final Mongo mongo;
    private final WriteConcern writeConcern;
    
    public MongoDBConnection(final DB db, final WriteConcern writeConcern, final String s) {
        this.mongo = db.getMongo();
        this.collection = db.getCollection(s);
        this.writeConcern = writeConcern;
    }
    
    @Override
    public MongoDBObject createObject() {
        return new MongoDBObject();
    }
    
    @Override
    public MongoDBObject[] createList(final int n) {
        return new MongoDBObject[n];
    }
    
    @Override
    public void insertObject(final NoSQLObject noSQLObject) {
        final WriteResult insert = this.collection.insert((DBObject)noSQLObject.unwrap(), this.writeConcern);
        if (insert.getError() != null && insert.getError().length() > 0) {
            throw new AppenderLoggingException("Failed to write log event to MongoDB due to error: " + insert.getError() + ".");
        }
    }
    
    @Override
    public void close() {
        this.mongo.close();
    }
    
    @Override
    public boolean isClosed() {
        return !this.mongo.getConnector().isOpen();
    }
    
    static void authenticate(final DB db, final String s, final String s2) {
        if (!db.authenticate(s, s2.toCharArray())) {
            MongoDBConnection.LOGGER.error("Failed to authenticate against MongoDB server. Unknown error.");
        }
    }
    
    @Override
    public NoSQLObject[] createList(final int n) {
        return this.createList(n);
    }
    
    @Override
    public NoSQLObject createObject() {
        return this.createObject();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        BSON.addDecodingHook((Class)Level.class, (Transformer)new Transformer() {
            public Object transform(final Object o) {
                if (o instanceof Level) {
                    return ((Level)o).name();
                }
                return o;
            }
        });
    }
}
