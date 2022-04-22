package org.lwjgl.opencl;

import org.lwjgl.*;

public abstract class CLEventCallback extends PointerWrapperAbstract
{
    private CLObjectRegistry eventRegistry;
    
    protected CLEventCallback() {
        super(CallbackUtil.getEventCallback());
    }
    
    void setRegistry(final CLObjectRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }
    
    private void handleMessage(final long n, final int n2) {
        this.handleMessage((CLEvent)this.eventRegistry.getObject(n), n2);
    }
    
    protected abstract void handleMessage(final CLEvent p0, final int p1);
}
