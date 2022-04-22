package org.lwjgl.opencl;

public final class CLKernel extends CLObjectChild
{
    private static final CLKernelUtil util;
    
    CLKernel(final long n, final CLProgram clProgram) {
        super(n, clProgram);
        if (this.isValid()) {
            clProgram.getCLKernelRegistry().registerObject(this);
        }
    }
    
    public CLKernel setArg(final int n, final byte b) {
        CLKernel.util.setArg(this, n, b);
        return this;
    }
    
    public CLKernel setArg(final int n, final short n2) {
        CLKernel.util.setArg(this, n, n2);
        return this;
    }
    
    public CLKernel setArg(final int n, final int n2) {
        CLKernel.util.setArg(this, n, n2);
        return this;
    }
    
    public CLKernel setArg(final int n, final long n2) {
        CLKernel.util.setArg(this, n, n2);
        return this;
    }
    
    public CLKernel setArg(final int n, final float n2) {
        CLKernel.util.setArg(this, n, n2);
        return this;
    }
    
    public CLKernel setArg(final int n, final double n2) {
        CLKernel.util.setArg(this, n, n2);
        return this;
    }
    
    public CLKernel setArg(final int n, final CLObject clObject) {
        CLKernel.util.setArg(this, n, clObject);
        return this;
    }
    
    public CLKernel setArgSize(final int n, final long n2) {
        CLKernel.util.setArgSize(this, n, n2);
        return this;
    }
    
    public String getInfoString(final int n) {
        return CLKernel.util.getInfoString(this, n);
    }
    
    public int getInfoInt(final int n) {
        return CLKernel.util.getInfoInt(this, n);
    }
    
    public long getWorkGroupInfoSize(final CLDevice clDevice, final int n) {
        return CLKernel.util.getWorkGroupInfoSize(this, clDevice, n);
    }
    
    public long[] getWorkGroupInfoSizeArray(final CLDevice clDevice, final int n) {
        return CLKernel.util.getWorkGroupInfoSizeArray(this, clDevice, n);
    }
    
    public long getWorkGroupInfoLong(final CLDevice clDevice, final int n) {
        return CLKernel.util.getWorkGroupInfoLong(this, clDevice, n);
    }
    
    @Override
    int release() {
        final int release = super.release();
        if (!this.isValid()) {
            ((CLProgram)this.getParent()).getCLKernelRegistry().unregisterObject(this);
        }
        return release;
    }
    
    static {
        util = (CLKernelUtil)CLPlatform.getInfoUtilInstance(CLKernel.class, "CL_KERNEL_UTIL");
    }
    
    interface CLKernelUtil extends InfoUtil
    {
        void setArg(final CLKernel p0, final int p1, final byte p2);
        
        void setArg(final CLKernel p0, final int p1, final short p2);
        
        void setArg(final CLKernel p0, final int p1, final int p2);
        
        void setArg(final CLKernel p0, final int p1, final long p2);
        
        void setArg(final CLKernel p0, final int p1, final float p2);
        
        void setArg(final CLKernel p0, final int p1, final double p2);
        
        void setArg(final CLKernel p0, final int p1, final CLObject p2);
        
        void setArgSize(final CLKernel p0, final int p1, final long p2);
        
        long getWorkGroupInfoSize(final CLKernel p0, final CLDevice p1, final int p2);
        
        long[] getWorkGroupInfoSizeArray(final CLKernel p0, final CLDevice p1, final int p2);
        
        long getWorkGroupInfoLong(final CLKernel p0, final CLDevice p1, final int p2);
    }
}
