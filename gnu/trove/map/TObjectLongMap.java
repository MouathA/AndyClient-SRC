package gnu.trove.map;

import java.util.*;
import gnu.trove.*;
import gnu.trove.iterator.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;

public interface TObjectLongMap
{
    long getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final long p0);
    
    long get(final Object p0);
    
    long put(final Object p0, final long p1);
    
    long putIfAbsent(final Object p0, final long p1);
    
    long remove(final Object p0);
    
    void putAll(final Map p0);
    
    void putAll(final TObjectLongMap p0);
    
    void clear();
    
    Set keySet();
    
    Object[] keys();
    
    Object[] keys(final Object[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    TObjectLongIterator iterator();
    
    boolean increment(final Object p0);
    
    boolean adjustValue(final Object p0, final long p1);
    
    long adjustOrPutValue(final Object p0, final long p1, final long p2);
    
    boolean forEachKey(final TObjectProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TObjectLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TObjectLongProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
