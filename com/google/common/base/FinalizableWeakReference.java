package com.google.common.base;

import java.lang.ref.*;

public abstract class FinalizableWeakReference extends WeakReference implements FinalizableReference
{
    protected FinalizableWeakReference(final Object o, final FinalizableReferenceQueue finalizableReferenceQueue) {
        super(o, finalizableReferenceQueue.queue);
        finalizableReferenceQueue.cleanUp();
    }
}
