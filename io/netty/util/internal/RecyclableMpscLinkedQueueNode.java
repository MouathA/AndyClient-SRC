package io.netty.util.internal;

import io.netty.util.*;

public abstract class RecyclableMpscLinkedQueueNode extends MpscLinkedQueueNode
{
    private final Recycler.Handle handle;
    
    protected RecyclableMpscLinkedQueueNode(final Recycler.Handle handle) {
        if (handle == null) {
            throw new NullPointerException("handle");
        }
        this.handle = handle;
    }
    
    @Override
    final void unlink() {
        super.unlink();
        this.recycle(this.handle);
    }
    
    protected abstract void recycle(final Recycler.Handle p0);
}
