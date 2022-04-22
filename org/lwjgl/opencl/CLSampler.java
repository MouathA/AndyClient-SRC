package org.lwjgl.opencl;

public final class CLSampler extends CLObjectChild
{
    private static final InfoUtil util;
    
    CLSampler(final long n, final CLContext clContext) {
        super(n, clContext);
        if (this.isValid()) {
            clContext.getCLSamplerRegistry().registerObject(this);
        }
    }
    
    public int getInfoInt(final int n) {
        return CLSampler.util.getInfoInt(this, n);
    }
    
    public long getInfoLong(final int n) {
        return CLSampler.util.getInfoLong(this, n);
    }
    
    @Override
    int release() {
        final int release = super.release();
        if (!this.isValid()) {
            ((CLContext)this.getParent()).getCLSamplerRegistry().unregisterObject(this);
        }
        return release;
    }
    
    static {
        util = CLPlatform.getInfoUtilInstance(CLSampler.class, "CL_SAMPLER_UTIL");
    }
}
