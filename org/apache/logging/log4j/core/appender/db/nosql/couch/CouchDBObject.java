package org.apache.logging.log4j.core.appender.db.nosql.couch;

import org.apache.logging.log4j.core.appender.db.nosql.*;
import java.util.*;

public final class CouchDBObject implements NoSQLObject
{
    private final Map map;
    
    public CouchDBObject() {
        this.map = new HashMap();
    }
    
    @Override
    public void set(final String s, final Object o) {
        this.map.put(s, o);
    }
    
    @Override
    public void set(final String s, final NoSQLObject noSQLObject) {
        this.map.put(s, noSQLObject.unwrap());
    }
    
    @Override
    public void set(final String s, final Object[] array) {
        this.map.put(s, Arrays.asList(array));
    }
    
    @Override
    public void set(final String s, final NoSQLObject[] array) {
        final ArrayList<Object> list = new ArrayList<Object>();
        while (0 < array.length) {
            list.add(array[0].unwrap());
            int n = 0;
            ++n;
        }
        this.map.put(s, list);
    }
    
    @Override
    public Map unwrap() {
        return this.map;
    }
    
    @Override
    public Object unwrap() {
        return this.unwrap();
    }
}
