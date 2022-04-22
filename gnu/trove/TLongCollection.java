package gnu.trove;

import gnu.trove.iterator.*;
import java.util.*;
import gnu.trove.procedure.*;

public interface TLongCollection
{
    public static final long serialVersionUID = 1L;
    
    long getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final long p0);
    
    TLongIterator iterator();
    
    long[] toArray();
    
    long[] toArray(final long[] p0);
    
    boolean add(final long p0);
    
    boolean remove(final long p0);
    
    boolean containsAll(final Collection p0);
    
    boolean containsAll(final TLongCollection p0);
    
    boolean containsAll(final long[] p0);
    
    boolean addAll(final Collection p0);
    
    boolean addAll(final TLongCollection p0);
    
    boolean addAll(final long[] p0);
    
    boolean retainAll(final Collection p0);
    
    boolean retainAll(final TLongCollection p0);
    
    boolean retainAll(final long[] p0);
    
    boolean removeAll(final Collection p0);
    
    boolean removeAll(final TLongCollection p0);
    
    boolean removeAll(final long[] p0);
    
    void clear();
    
    boolean forEach(final TLongProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
