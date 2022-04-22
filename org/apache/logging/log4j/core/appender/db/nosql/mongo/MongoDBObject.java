package org.apache.logging.log4j.core.appender.db.nosql.mongo;

import org.apache.logging.log4j.core.appender.db.nosql.*;
import com.mongodb.*;
import java.util.*;

public final class MongoDBObject implements NoSQLObject
{
    private final BasicDBObject mongoObject;
    
    public MongoDBObject() {
        this.mongoObject = new BasicDBObject();
    }
    
    @Override
    public void set(final String s, final Object o) {
        this.mongoObject.append(s, o);
    }
    
    @Override
    public void set(final String s, final NoSQLObject noSQLObject) {
        this.mongoObject.append(s, noSQLObject.unwrap());
    }
    
    @Override
    public void set(final String s, final Object[] array) {
        final BasicDBList list = new BasicDBList();
        Collections.addAll((Collection<? super Object>)list, array);
        this.mongoObject.append(s, (Object)list);
    }
    
    @Override
    public void set(final String s, final NoSQLObject[] array) {
        final BasicDBList list = new BasicDBList();
        while (0 < array.length) {
            list.add(array[0].unwrap());
            int n = 0;
            ++n;
        }
        this.mongoObject.append(s, (Object)list);
    }
    
    @Override
    public BasicDBObject unwrap() {
        return this.mongoObject;
    }
    
    @Override
    public Object unwrap() {
        return this.unwrap();
    }
}
