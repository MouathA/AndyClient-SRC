package gnu.trove;

import gnu.trove.iterator.*;
import java.util.*;
import gnu.trove.procedure.*;

public interface TByteCollection
{
    public static final long serialVersionUID = 1L;
    
    byte getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final byte p0);
    
    TByteIterator iterator();
    
    byte[] toArray();
    
    byte[] toArray(final byte[] p0);
    
    boolean add(final byte p0);
    
    boolean remove(final byte p0);
    
    boolean containsAll(final Collection p0);
    
    boolean containsAll(final TByteCollection p0);
    
    boolean containsAll(final byte[] p0);
    
    boolean addAll(final Collection p0);
    
    boolean addAll(final TByteCollection p0);
    
    boolean addAll(final byte[] p0);
    
    boolean retainAll(final Collection p0);
    
    boolean retainAll(final TByteCollection p0);
    
    boolean retainAll(final byte[] p0);
    
    boolean removeAll(final Collection p0);
    
    boolean removeAll(final TByteCollection p0);
    
    boolean removeAll(final byte[] p0);
    
    void clear();
    
    boolean forEach(final TByteProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
