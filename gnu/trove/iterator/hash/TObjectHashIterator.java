package gnu.trove.iterator.hash;

import gnu.trove.impl.hash.*;

public class TObjectHashIterator extends THashIterator
{
    protected final TObjectHash _objectHash;
    
    public TObjectHashIterator(final TObjectHash objectHash) {
        super(objectHash);
        this._objectHash = objectHash;
    }
    
    @Override
    protected Object objectAtIndex(final int n) {
        final Object o = this._objectHash._set[n];
        if (o == TObjectHash.FREE || o == TObjectHash.REMOVED) {
            return null;
        }
        return o;
    }
}
