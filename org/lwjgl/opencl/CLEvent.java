package org.lwjgl.opencl;

public final class CLEvent extends CLObjectChild
{
    private static final CLEventUtil util;
    private final CLCommandQueue queue;
    
    CLEvent(final long n, final CLContext clContext) {
        this(n, clContext, null);
    }
    
    CLEvent(final long n, final CLCommandQueue clCommandQueue) {
        this(n, (CLContext)clCommandQueue.getParent(), clCommandQueue);
    }
    
    CLEvent(final long n, final CLContext clContext, final CLCommandQueue queue) {
        super(n, clContext);
        if (this.isValid()) {
            if ((this.queue = queue) == null) {
                clContext.getCLEventRegistry().registerObject(this);
            }
            else {
                queue.getCLEventRegistry().registerObject(this);
            }
        }
        else {
            this.queue = null;
        }
    }
    
    public CLCommandQueue getCLCommandQueue() {
        return this.queue;
    }
    
    public int getInfoInt(final int n) {
        return CLEvent.util.getInfoInt(this, n);
    }
    
    public long getProfilingInfoLong(final int n) {
        return CLEvent.util.getProfilingInfoLong(this, n);
    }
    
    CLObjectRegistry getParentRegistry() {
        if (this.queue == null) {
            return ((CLContext)this.getParent()).getCLEventRegistry();
        }
        return this.queue.getCLEventRegistry();
    }
    
    @Override
    int release() {
        final int release = super.release();
        if (!this.isValid()) {
            if (this.queue == null) {
                ((CLContext)this.getParent()).getCLEventRegistry().unregisterObject(this);
            }
            else {
                this.queue.getCLEventRegistry().unregisterObject(this);
            }
        }
        return release;
    }
    
    static {
        util = (CLEventUtil)CLPlatform.getInfoUtilInstance(CLEvent.class, "CL_EVENT_UTIL");
    }
    
    interface CLEventUtil extends InfoUtil
    {
        long getProfilingInfoLong(final CLEvent p0, final int p1);
    }
}
