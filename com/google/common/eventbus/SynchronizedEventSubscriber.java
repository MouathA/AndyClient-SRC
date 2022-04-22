package com.google.common.eventbus;

import java.lang.reflect.*;

final class SynchronizedEventSubscriber extends EventSubscriber
{
    public SynchronizedEventSubscriber(final Object o, final Method method) {
        super(o, method);
    }
    
    @Override
    public void handleEvent(final Object o) throws InvocationTargetException {
        // monitorenter(this)
        super.handleEvent(o);
    }
    // monitorexit(this)
}
