package org.apache.logging.log4j.core.appender.db.nosql;

public interface NoSQLObject
{
    void set(final String p0, final Object p1);
    
    void set(final String p0, final NoSQLObject p1);
    
    void set(final String p0, final Object[] p1);
    
    void set(final String p0, final NoSQLObject[] p1);
    
    Object unwrap();
}
