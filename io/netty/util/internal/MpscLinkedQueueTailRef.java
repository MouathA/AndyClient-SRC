package io.netty.util.internal;

import java.util.concurrent.atomic.*;

abstract class MpscLinkedQueueTailRef extends MpscLinkedQueuePad1
{
    private static final long serialVersionUID = 8717072462993327429L;
    private static final AtomicReferenceFieldUpdater UPDATER;
    private transient MpscLinkedQueueNode tailRef;
    
    protected final MpscLinkedQueueNode tailRef() {
        return this.tailRef;
    }
    
    protected final void setTailRef(final MpscLinkedQueueNode tailRef) {
        this.tailRef = tailRef;
    }
    
    protected final MpscLinkedQueueNode getAndSetTailRef(final MpscLinkedQueueNode mpscLinkedQueueNode) {
        return MpscLinkedQueueTailRef.UPDATER.getAndSet(this, mpscLinkedQueueNode);
    }
    
    static {
        AtomicReferenceFieldUpdater<MpscLinkedQueueTailRef, MpscLinkedQueueNode> updater = (AtomicReferenceFieldUpdater<MpscLinkedQueueTailRef, MpscLinkedQueueNode>)PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueTailRef.class, "tailRef");
        if (updater == null) {
            updater = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueTailRef.class, MpscLinkedQueueNode.class, "tailRef");
        }
        UPDATER = updater;
    }
}
