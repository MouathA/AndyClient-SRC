package io.netty.util.internal;

import java.util.concurrent.atomic.*;

public abstract class MpscLinkedQueueNode
{
    private static final AtomicReferenceFieldUpdater nextUpdater;
    private MpscLinkedQueueNode next;
    
    final MpscLinkedQueueNode next() {
        return this.next;
    }
    
    final void setNext(final MpscLinkedQueueNode mpscLinkedQueueNode) {
        MpscLinkedQueueNode.nextUpdater.lazySet(this, mpscLinkedQueueNode);
    }
    
    public abstract Object value();
    
    protected Object clearMaybe() {
        return this.value();
    }
    
    void unlink() {
        this.setNext(null);
    }
    
    static {
        AtomicReferenceFieldUpdater<MpscLinkedQueueNode, MpscLinkedQueueNode> nextUpdater2 = (AtomicReferenceFieldUpdater<MpscLinkedQueueNode, MpscLinkedQueueNode>)PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueNode.class, "next");
        if (nextUpdater2 == null) {
            nextUpdater2 = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueNode.class, MpscLinkedQueueNode.class, "next");
        }
        nextUpdater = nextUpdater2;
    }
}
