package gnu.trove.iterator;

public interface TObjectIntIterator extends TAdvancingIterator
{
    Object key();
    
    int value();
    
    int setValue(final int p0);
}
