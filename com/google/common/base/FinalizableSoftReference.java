package com.google.common.base;

import java.lang.ref.*;

public abstract class FinalizableSoftReference extends SoftReference implements FinalizableReference
{
    protected FinalizableSoftReference(final Object o, final FinalizableReferenceQueue finalizableReferenceQueue) {
        super(o, finalizableReferenceQueue.queue);
        finalizableReferenceQueue.cleanUp();
    }
}
