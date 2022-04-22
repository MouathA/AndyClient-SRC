package com.google.common.base;

import java.lang.ref.*;

public abstract class FinalizablePhantomReference extends PhantomReference implements FinalizableReference
{
    protected FinalizablePhantomReference(final Object o, final FinalizableReferenceQueue finalizableReferenceQueue) {
        super(o, finalizableReferenceQueue.queue);
        finalizableReferenceQueue.cleanUp();
    }
}
