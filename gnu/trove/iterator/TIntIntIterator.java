package gnu.trove.iterator;

public interface TIntIntIterator extends TAdvancingIterator
{
    int key();
    
    int value();
    
    int setValue(final int p0);
}
