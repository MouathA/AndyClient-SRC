package gnu.trove.map;

import java.util.*;
import gnu.trove.*;
import gnu.trove.iterator.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;

public interface TObjectIntMap
{
    int getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final int p0);
    
    int get(final Object p0);
    
    int put(final Object p0, final int p1);
    
    int putIfAbsent(final Object p0, final int p1);
    
    int remove(final Object p0);
    
    void putAll(final Map p0);
    
    void putAll(final TObjectIntMap p0);
    
    void clear();
    
    Set keySet();
    
    Object[] keys();
    
    Object[] keys(final Object[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    TObjectIntIterator iterator();
    
    boolean increment(final Object p0);
    
    boolean adjustValue(final Object p0, final int p1);
    
    int adjustOrPutValue(final Object p0, final int p1, final int p2);
    
    boolean forEachKey(final TObjectProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TObjectIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TObjectIntProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
