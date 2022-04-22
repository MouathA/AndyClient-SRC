package io.netty.util.internal;

import java.io.*;
import java.util.concurrent.atomic.*;

abstract class MpscLinkedQueueHeadRef extends MpscLinkedQueuePad0 implements Serializable
{
    private static final long serialVersionUID = 8467054865577874285L;
    private static final AtomicReferenceFieldUpdater UPDATER;
    private transient MpscLinkedQueueNode headRef;
    
    protected final MpscLinkedQueueNode headRef() {
        return this.headRef;
    }
    
    protected final void setHeadRef(final MpscLinkedQueueNode headRef) {
        this.headRef = headRef;
    }
    
    protected final void lazySetHeadRef(final MpscLinkedQueueNode mpscLinkedQueueNode) {
        MpscLinkedQueueHeadRef.UPDATER.lazySet(this, mpscLinkedQueueNode);
    }
    
    static {
        AtomicReferenceFieldUpdater<MpscLinkedQueueHeadRef, MpscLinkedQueueNode> updater = (AtomicReferenceFieldUpdater<MpscLinkedQueueHeadRef, MpscLinkedQueueNode>)PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueHeadRef.class, "headRef");
        if (updater == null) {
            updater = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueHeadRef.class, MpscLinkedQueueNode.class, "headRef");
        }
        UPDATER = updater;
    }
}
