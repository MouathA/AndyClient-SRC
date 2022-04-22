package gnu.trove.map;

import java.util.*;
import gnu.trove.*;
import gnu.trove.iterator.*;
import gnu.trove.procedure.*;
import gnu.trove.function.*;

public interface TObjectDoubleMap
{
    double getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final double p0);
    
    double get(final Object p0);
    
    double put(final Object p0, final double p1);
    
    double putIfAbsent(final Object p0, final double p1);
    
    double remove(final Object p0);
    
    void putAll(final Map p0);
    
    void putAll(final TObjectDoubleMap p0);
    
    void clear();
    
    Set keySet();
    
    Object[] keys();
    
    Object[] keys(final Object[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    TObjectDoubleIterator iterator();
    
    boolean increment(final Object p0);
    
    boolean adjustValue(final Object p0, final double p1);
    
    double adjustOrPutValue(final Object p0, final double p1, final double p2);
    
    boolean forEachKey(final TObjectProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TObjectDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TObjectDoubleProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
