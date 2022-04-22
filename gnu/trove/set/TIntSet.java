package gnu.trove.set;

import gnu.trove.*;
import gnu.trove.iterator.*;
import java.util.*;
import gnu.trove.procedure.*;

public interface TIntSet extends TIntCollection
{
    int getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final int p0);
    
    TIntIterator iterator();
    
    int[] toArray();
    
    int[] toArray(final int[] p0);
    
    boolean add(final int p0);
    
    boolean remove(final int p0);
    
    boolean containsAll(final Collection p0);
    
    boolean containsAll(final TIntCollection p0);
    
    boolean containsAll(final int[] p0);
    
    boolean addAll(final Collection p0);
    
    boolean addAll(final TIntCollection p0);
    
    boolean addAll(final int[] p0);
    
    boolean retainAll(final Collection p0);
    
    boolean retainAll(final TIntCollection p0);
    
    boolean retainAll(final int[] p0);
    
    boolean removeAll(final Collection p0);
    
    boolean removeAll(final TIntCollection p0);
    
    boolean removeAll(final int[] p0);
    
    void clear();
    
    boolean forEach(final TIntProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
