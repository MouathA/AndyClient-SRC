package gnu.trove.iterator;

public interface TObjectDoubleIterator extends TAdvancingIterator
{
    Object key();
    
    double value();
    
    double setValue(final double p0);
}
