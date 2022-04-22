package gnu.trove.map;

import gnu.trove.set.*;
import java.util.*;
import gnu.trove.iterator.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;

public interface TCharObjectMap
{
    char getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final char p0);
    
    boolean containsValue(final Object p0);
    
    Object get(final char p0);
    
    Object put(final char p0, final Object p1);
    
    Object putIfAbsent(final char p0, final Object p1);
    
    Object remove(final char p0);
    
    void putAll(final Map p0);
    
    void putAll(final TCharObjectMap p0);
    
    void clear();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    Collection valueCollection();
    
    Object[] values();
    
    Object[] values(final Object[] p0);
    
    TCharObjectIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TObjectProcedure p0);
    
    boolean forEachEntry(final TCharObjectProcedure p0);
    
    void transformValues(final TObjectFunction p0);
    
    boolean retainEntries(final TCharObjectProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
