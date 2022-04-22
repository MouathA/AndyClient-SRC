package gnu.trove.map;

import gnu.trove.set.*;
import java.util.*;
import gnu.trove.iterator.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;

public interface TIntObjectMap
{
    int getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final int p0);
    
    boolean containsValue(final Object p0);
    
    Object get(final int p0);
    
    Object put(final int p0, final Object p1);
    
    Object putIfAbsent(final int p0, final Object p1);
    
    Object remove(final int p0);
    
    void putAll(final Map p0);
    
    void putAll(final TIntObjectMap p0);
    
    void clear();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    Collection valueCollection();
    
    Object[] values();
    
    Object[] values(final Object[] p0);
    
    TIntObjectIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TObjectProcedure p0);
    
    boolean forEachEntry(final TIntObjectProcedure p0);
    
    void transformValues(final TObjectFunction p0);
    
    boolean retainEntries(final TIntObjectProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
