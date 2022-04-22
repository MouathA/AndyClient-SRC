package gnu.trove.iterator;

public interface TCharObjectIterator extends TAdvancingIterator
{
    char key();
    
    Object value();
    
    Object setValue(final Object p0);
}
