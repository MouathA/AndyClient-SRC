package org.lwjgl.opencl;

import org.lwjgl.opencl.api.*;
import java.nio.*;

public final class CLMem extends CLObjectChild
{
    private static final CLMemUtil util;
    
    CLMem(final long n, final CLContext clContext) {
        super(n, clContext);
        if (this.isValid()) {
            clContext.getCLMemRegistry().registerObject(this);
        }
    }
    
    public static CLMem createImage2D(final CLContext clContext, final long n, final CLImageFormat clImageFormat, final long n2, final long n3, final long n4, final Buffer buffer, final IntBuffer intBuffer) {
        return CLMem.util.createImage2D(clContext, n, clImageFormat, n2, n3, n4, buffer, intBuffer);
    }
    
    public static CLMem createImage3D(final CLContext clContext, final long n, final CLImageFormat clImageFormat, final long n2, final long n3, final long n4, final long n5, final long n6, final Buffer buffer, final IntBuffer intBuffer) {
        return CLMem.util.createImage3D(clContext, n, clImageFormat, n2, n3, n4, n5, n6, buffer, intBuffer);
    }
    
    public CLMem createSubBuffer(final long n, final int n2, final CLBufferRegion clBufferRegion, final IntBuffer intBuffer) {
        return CLMem.util.createSubBuffer(this, n, n2, clBufferRegion, intBuffer);
    }
    
    public int getInfoInt(final int n) {
        return CLMem.util.getInfoInt(this, n);
    }
    
    public long getInfoSize(final int n) {
        return CLMem.util.getInfoSize(this, n);
    }
    
    public long getInfoLong(final int n) {
        return CLMem.util.getInfoLong(this, n);
    }
    
    public ByteBuffer getInfoHostBuffer() {
        return CLMem.util.getInfoHostBuffer(this);
    }
    
    public long getImageInfoSize(final int n) {
        return CLMem.util.getImageInfoSize(this, n);
    }
    
    public CLImageFormat getImageFormat() {
        return CLMem.util.getImageInfoFormat(this);
    }
    
    public int getImageChannelOrder() {
        return CLMem.util.getImageInfoFormat(this, 0);
    }
    
    public int getImageChannelType() {
        return CLMem.util.getImageInfoFormat(this, 1);
    }
    
    public int getGLObjectType() {
        return CLMem.util.getGLObjectType(this);
    }
    
    public int getGLObjectName() {
        return CLMem.util.getGLObjectName(this);
    }
    
    public int getGLTextureInfoInt(final int n) {
        return CLMem.util.getGLTextureInfoInt(this, n);
    }
    
    static CLMem create(final long n, final CLContext clContext) {
        CLMem clMem = (CLMem)clContext.getCLMemRegistry().getObject(n);
        if (clMem == null) {
            clMem = new CLMem(n, clContext);
        }
        else {
            clMem.retain();
        }
        return clMem;
    }
    
    @Override
    int release() {
        final int release = super.release();
        if (!this.isValid()) {
            ((CLContext)this.getParent()).getCLMemRegistry().unregisterObject(this);
        }
        return release;
    }
    
    static {
        util = (CLMemUtil)CLPlatform.getInfoUtilInstance(CLMem.class, "CL_MEM_UTIL");
    }
    
    interface CLMemUtil extends InfoUtil
    {
        CLMem createImage2D(final CLContext p0, final long p1, final CLImageFormat p2, final long p3, final long p4, final long p5, final Buffer p6, final IntBuffer p7);
        
        CLMem createImage3D(final CLContext p0, final long p1, final CLImageFormat p2, final long p3, final long p4, final long p5, final long p6, final long p7, final Buffer p8, final IntBuffer p9);
        
        CLMem createSubBuffer(final CLMem p0, final long p1, final int p2, final CLBufferRegion p3, final IntBuffer p4);
        
        ByteBuffer getInfoHostBuffer(final CLMem p0);
        
        long getImageInfoSize(final CLMem p0, final int p1);
        
        CLImageFormat getImageInfoFormat(final CLMem p0);
        
        int getImageInfoFormat(final CLMem p0, final int p1);
        
        int getGLObjectType(final CLMem p0);
        
        int getGLObjectName(final CLMem p0);
        
        int getGLTextureInfoInt(final CLMem p0, final int p1);
    }
}
