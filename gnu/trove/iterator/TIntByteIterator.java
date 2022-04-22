package gnu.trove.iterator;

public interface TIntByteIterator extends TAdvancingIterator
{
    int key();
    
    byte value();
    
    byte setValue(final byte p0);
}
