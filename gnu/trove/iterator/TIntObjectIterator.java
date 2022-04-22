package gnu.trove.iterator;

public interface TIntObjectIterator extends TAdvancingIterator
{
    int key();
    
    Object value();
    
    Object setValue(final Object p0);
}
