package org.lwjgl.opencl;

import org.lwjgl.*;

public final class CLCommandQueue extends CLObjectChild
{
    private static final InfoUtil util;
    private final CLDevice device;
    private final CLObjectRegistry clEvents;
    
    CLCommandQueue(final long n, final CLContext clContext, final CLDevice device) {
        super(n, clContext);
        if (this.isValid()) {
            this.device = device;
            this.clEvents = new CLObjectRegistry();
            clContext.getCLCommandQueueRegistry().registerObject(this);
        }
        else {
            this.device = null;
            this.clEvents = null;
        }
    }
    
    public CLDevice getCLDevice() {
        return this.device;
    }
    
    public CLEvent getCLEvent(final long n) {
        return (CLEvent)this.clEvents.getObject(n);
    }
    
    public int getInfoInt(final int n) {
        return CLCommandQueue.util.getInfoInt(this, n);
    }
    
    CLObjectRegistry getCLEventRegistry() {
        return this.clEvents;
    }
    
    void registerCLEvent(final PointerBuffer pointerBuffer) {
        if (pointerBuffer != null) {
            new CLEvent(pointerBuffer.get(pointerBuffer.position()), this);
        }
    }
    
    @Override
    int release() {
        final int release = super.release();
        if (!this.isValid()) {
            ((CLContext)this.getParent()).getCLCommandQueueRegistry().unregisterObject(this);
        }
        return release;
    }
    
    static {
        util = CLPlatform.getInfoUtilInstance(CLCommandQueue.class, "CL_COMMAND_QUEUE_UTIL");
    }
}
