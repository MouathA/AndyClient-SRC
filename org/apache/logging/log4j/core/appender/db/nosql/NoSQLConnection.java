package org.apache.logging.log4j.core.appender.db.nosql;

import java.io.*;

public interface NoSQLConnection extends Closeable
{
    NoSQLObject createObject();
    
    NoSQLObject[] createList(final int p0);
    
    void insertObject(final NoSQLObject p0);
    
    void close();
    
    boolean isClosed();
}
