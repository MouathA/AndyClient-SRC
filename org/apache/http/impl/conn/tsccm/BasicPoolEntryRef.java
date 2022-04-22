package org.apache.http.impl.conn.tsccm;

import org.apache.http.conn.routing.*;
import java.lang.ref.*;
import org.apache.http.util.*;

@Deprecated
public class BasicPoolEntryRef extends WeakReference
{
    private final HttpRoute route;
    
    public BasicPoolEntryRef(final BasicPoolEntry basicPoolEntry, final ReferenceQueue referenceQueue) {
        super(basicPoolEntry, referenceQueue);
        Args.notNull(basicPoolEntry, "Pool entry");
        this.route = basicPoolEntry.getPlannedRoute();
    }
    
    public final HttpRoute getRoute() {
        return this.route;
    }
}
