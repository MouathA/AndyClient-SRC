package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class CLProgram extends CLObjectChild
{
    private static final CLProgramUtil util;
    private final CLObjectRegistry clKernels;
    
    CLProgram(final long n, final CLContext clContext) {
        super(n, clContext);
        if (this.isValid()) {
            clContext.getCLProgramRegistry().registerObject(this);
            this.clKernels = new CLObjectRegistry();
        }
        else {
            this.clKernels = null;
        }
    }
    
    public CLKernel getCLKernel(final long n) {
        return (CLKernel)this.clKernels.getObject(n);
    }
    
    public CLKernel[] createKernelsInProgram() {
        return CLProgram.util.createKernelsInProgram(this);
    }
    
    public String getInfoString(final int n) {
        return CLProgram.util.getInfoString(this, n);
    }
    
    public int getInfoInt(final int n) {
        return CLProgram.util.getInfoInt(this, n);
    }
    
    public long[] getInfoSizeArray(final int n) {
        return CLProgram.util.getInfoSizeArray(this, n);
    }
    
    public CLDevice[] getInfoDevices() {
        return CLProgram.util.getInfoDevices(this);
    }
    
    public ByteBuffer getInfoBinaries(final ByteBuffer byteBuffer) {
        return CLProgram.util.getInfoBinaries(this, byteBuffer);
    }
    
    public ByteBuffer[] getInfoBinaries(final ByteBuffer[] array) {
        return CLProgram.util.getInfoBinaries(this, array);
    }
    
    public String getBuildInfoString(final CLDevice clDevice, final int n) {
        return CLProgram.util.getBuildInfoString(this, clDevice, n);
    }
    
    public int getBuildInfoInt(final CLDevice clDevice, final int n) {
        return CLProgram.util.getBuildInfoInt(this, clDevice, n);
    }
    
    CLObjectRegistry getCLKernelRegistry() {
        return this.clKernels;
    }
    
    void registerCLKernels(final PointerBuffer pointerBuffer) {
        for (int i = pointerBuffer.position(); i < pointerBuffer.limit(); ++i) {
            final long value = pointerBuffer.get(i);
            if (value != 0L) {
                new CLKernel(value, this);
            }
        }
    }
    
    @Override
    int release() {
        final int release = super.release();
        if (!this.isValid()) {
            ((CLContext)this.getParent()).getCLProgramRegistry().unregisterObject(this);
        }
        return release;
    }
    
    static {
        util = (CLProgramUtil)CLPlatform.getInfoUtilInstance(CLProgram.class, "CL_PROGRAM_UTIL");
    }
    
    interface CLProgramUtil extends InfoUtil
    {
        CLKernel[] createKernelsInProgram(final CLProgram p0);
        
        CLDevice[] getInfoDevices(final CLProgram p0);
        
        ByteBuffer getInfoBinaries(final CLProgram p0, final ByteBuffer p1);
        
        ByteBuffer[] getInfoBinaries(final CLProgram p0, final ByteBuffer[] p1);
        
        String getBuildInfoString(final CLProgram p0, final CLDevice p1, final int p2);
        
        int getBuildInfoInt(final CLProgram p0, final CLDevice p1, final int p2);
    }
}
