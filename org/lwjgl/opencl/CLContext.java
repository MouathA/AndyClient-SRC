package org.lwjgl.opencl;

import java.util.*;
import java.nio.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.lwjgl.opencl.api.*;

public final class CLContext extends CLObjectChild
{
    private static final CLContextUtil util;
    private final CLObjectRegistry clCommandQueues;
    private final CLObjectRegistry clMems;
    private final CLObjectRegistry clSamplers;
    private final CLObjectRegistry clPrograms;
    private final CLObjectRegistry clEvents;
    private long contextCallback;
    private long printfCallback;
    
    CLContext(final long n, final CLPlatform clPlatform) {
        super(n, clPlatform);
        if (this.isValid()) {
            this.clCommandQueues = new CLObjectRegistry();
            this.clMems = new CLObjectRegistry();
            this.clSamplers = new CLObjectRegistry();
            this.clPrograms = new CLObjectRegistry();
            this.clEvents = new CLObjectRegistry();
        }
        else {
            this.clCommandQueues = null;
            this.clMems = null;
            this.clSamplers = null;
            this.clPrograms = null;
            this.clEvents = null;
        }
    }
    
    public CLCommandQueue getCLCommandQueue(final long n) {
        return (CLCommandQueue)this.clCommandQueues.getObject(n);
    }
    
    public CLMem getCLMem(final long n) {
        return (CLMem)this.clMems.getObject(n);
    }
    
    public CLSampler getCLSampler(final long n) {
        return (CLSampler)this.clSamplers.getObject(n);
    }
    
    public CLProgram getCLProgram(final long n) {
        return (CLProgram)this.clPrograms.getObject(n);
    }
    
    public CLEvent getCLEvent(final long n) {
        return (CLEvent)this.clEvents.getObject(n);
    }
    
    public static CLContext create(final CLPlatform clPlatform, final List list, final IntBuffer intBuffer) throws LWJGLException {
        return create(clPlatform, list, null, null, intBuffer);
    }
    
    public static CLContext create(final CLPlatform clPlatform, final List list, final CLContextCallback clContextCallback, final IntBuffer intBuffer) throws LWJGLException {
        return create(clPlatform, list, clContextCallback, null, intBuffer);
    }
    
    public static CLContext create(final CLPlatform clPlatform, final List list, final CLContextCallback clContextCallback, final Drawable drawable, final IntBuffer intBuffer) throws LWJGLException {
        return CLContext.util.create(clPlatform, list, clContextCallback, drawable, intBuffer);
    }
    
    public static CLContext createFromType(final CLPlatform clPlatform, final long n, final IntBuffer intBuffer) throws LWJGLException {
        return CLContext.util.createFromType(clPlatform, n, null, null, intBuffer);
    }
    
    public static CLContext createFromType(final CLPlatform clPlatform, final long n, final CLContextCallback clContextCallback, final IntBuffer intBuffer) throws LWJGLException {
        return CLContext.util.createFromType(clPlatform, n, clContextCallback, null, intBuffer);
    }
    
    public static CLContext createFromType(final CLPlatform clPlatform, final long n, final CLContextCallback clContextCallback, final Drawable drawable, final IntBuffer intBuffer) throws LWJGLException {
        return CLContext.util.createFromType(clPlatform, n, clContextCallback, drawable, intBuffer);
    }
    
    public int getInfoInt(final int n) {
        return CLContext.util.getInfoInt(this, n);
    }
    
    public List getInfoDevices() {
        return CLContext.util.getInfoDevices(this);
    }
    
    public List getSupportedImageFormats(final long n, final int n2) {
        return this.getSupportedImageFormats(n, n2, null);
    }
    
    public List getSupportedImageFormats(final long n, final int n2, final Filter filter) {
        return CLContext.util.getSupportedImageFormats(this, n, n2, filter);
    }
    
    CLObjectRegistry getCLCommandQueueRegistry() {
        return this.clCommandQueues;
    }
    
    CLObjectRegistry getCLMemRegistry() {
        return this.clMems;
    }
    
    CLObjectRegistry getCLSamplerRegistry() {
        return this.clSamplers;
    }
    
    CLObjectRegistry getCLProgramRegistry() {
        return this.clPrograms;
    }
    
    CLObjectRegistry getCLEventRegistry() {
        return this.clEvents;
    }
    
    private boolean checkCallback(final long n, final int n2) {
        if (n2 == 0 && (n == 0L || this.isValid())) {
            return true;
        }
        if (n != 0L) {
            CallbackUtil.deleteGlobalRef(n);
        }
        return false;
    }
    
    void setContextCallback(final long contextCallback) {
        if (this.checkCallback(contextCallback, 0)) {
            this.contextCallback = contextCallback;
        }
    }
    
    void setPrintfCallback(final long printfCallback, final int n) {
        if (this.checkCallback(printfCallback, n)) {
            this.printfCallback = printfCallback;
        }
    }
    
    void releaseImpl() {
        if (this.release() > 0) {
            return;
        }
        if (this.contextCallback != 0L) {
            CallbackUtil.deleteGlobalRef(this.contextCallback);
        }
        if (this.printfCallback != 0L) {
            CallbackUtil.deleteGlobalRef(this.printfCallback);
        }
    }
    
    static {
        util = (CLContextUtil)CLPlatform.getInfoUtilInstance(CLContext.class, "CL_CONTEXT_UTIL");
    }
    
    interface CLContextUtil extends InfoUtil
    {
        List getInfoDevices(final CLContext p0);
        
        CLContext create(final CLPlatform p0, final List p1, final CLContextCallback p2, final Drawable p3, final IntBuffer p4) throws LWJGLException;
        
        CLContext createFromType(final CLPlatform p0, final long p1, final CLContextCallback p2, final Drawable p3, final IntBuffer p4) throws LWJGLException;
        
        List getSupportedImageFormats(final CLContext p0, final long p1, final int p2, final Filter p3);
    }
}
