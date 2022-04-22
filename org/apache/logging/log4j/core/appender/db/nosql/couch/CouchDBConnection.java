package org.apache.logging.log4j.core.appender.db.nosql.couch;

import org.apache.logging.log4j.core.appender.db.nosql.*;
import org.apache.logging.log4j.core.appender.*;
import org.lightcouch.*;

public final class CouchDBConnection implements NoSQLConnection
{
    private final CouchDbClient client;
    private boolean closed;
    
    public CouchDBConnection(final CouchDbClient client) {
        this.closed = false;
        this.client = client;
    }
    
    @Override
    public CouchDBObject createObject() {
        return new CouchDBObject();
    }
    
    @Override
    public CouchDBObject[] createList(final int n) {
        return new CouchDBObject[n];
    }
    
    @Override
    public void insertObject(final NoSQLObject noSQLObject) {
        final Response save = this.client.save(noSQLObject.unwrap());
        if (save.getError() != null && save.getError().length() > 0) {
            throw new AppenderLoggingException("Failed to write log event to CouchDB due to error: " + save.getError() + ".");
        }
    }
    
    @Override
    public synchronized void close() {
        this.closed = true;
        this.client.shutdown();
    }
    
    @Override
    public synchronized boolean isClosed() {
        return this.closed;
    }
    
    @Override
    public NoSQLObject[] createList(final int n) {
        return this.createList(n);
    }
    
    @Override
    public NoSQLObject createObject() {
        return this.createObject();
    }
}
