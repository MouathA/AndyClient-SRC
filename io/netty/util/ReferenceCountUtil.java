package io.netty.util;

import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;

public final class ReferenceCountUtil
{
    private static final InternalLogger logger;
    
    public static Object retain(final Object o) {
        if (o instanceof ReferenceCounted) {
            return ((ReferenceCounted)o).retain();
        }
        return o;
    }
    
    public static Object retain(final Object o, final int n) {
        if (o instanceof ReferenceCounted) {
            return ((ReferenceCounted)o).retain(n);
        }
        return o;
    }
    
    public static boolean release(final Object o) {
        return o instanceof ReferenceCounted && ((ReferenceCounted)o).release();
    }
    
    public static boolean release(final Object o, final int n) {
        return o instanceof ReferenceCounted && ((ReferenceCounted)o).release(n);
    }
    
    public static void safeRelease(final Object o) {
        release(o);
    }
    
    public static void safeRelease(final Object o, final int n) {
        release(o, n);
    }
    
    public static Object releaseLater(final Object o) {
        return releaseLater(o, 1);
    }
    
    public static Object releaseLater(final Object o, final int n) {
        if (o instanceof ReferenceCounted) {
            ThreadDeathWatcher.watch(Thread.currentThread(), new ReleasingTask((ReferenceCounted)o, n));
        }
        return o;
    }
    
    private ReferenceCountUtil() {
    }
    
    static InternalLogger access$000() {
        return ReferenceCountUtil.logger;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);
    }
    
    private static final class ReleasingTask implements Runnable
    {
        private final ReferenceCounted obj;
        private final int decrement;
        
        ReleasingTask(final ReferenceCounted obj, final int decrement) {
            this.obj = obj;
            this.decrement = decrement;
        }
        
        @Override
        public void run() {
            if (!this.obj.release(this.decrement)) {
                ReferenceCountUtil.access$000().warn("Non-zero refCnt: {}", this);
            }
            else {
                ReferenceCountUtil.access$000().debug("Released: {}", this);
            }
        }
        
        @Override
        public String toString() {
            return StringUtil.simpleClassName(this.obj) + ".release(" + this.decrement + ") refCnt: " + this.obj.refCnt();
        }
    }
}
