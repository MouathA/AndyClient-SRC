package org.lwjgl.opengl;

import java.nio.*;
import java.awt.*;
import org.lwjgl.*;
import java.security.*;

final class AWTSurfaceLock
{
    private static final int WAIT_DELAY_MILLIS = 100;
    private final ByteBuffer lock_buffer;
    private boolean firstLockSucceeded;
    
    AWTSurfaceLock() {
        this.lock_buffer = createHandle();
    }
    
    private static native ByteBuffer createHandle();
    
    public ByteBuffer lockAndGetHandle(final Canvas canvas) throws LWJGLException {
        while (!this.privilegedLockAndInitHandle(canvas)) {
            LWJGLUtil.log("Could not get drawing surface info, retrying...");
            Thread.sleep(100L);
        }
        return this.lock_buffer;
    }
    
    private boolean privilegedLockAndInitHandle(final Canvas canvas) throws LWJGLException {
        if (this.firstLockSucceeded) {
            return lockAndInitHandle(this.lock_buffer, canvas);
        }
        return this.firstLockSucceeded = AccessController.doPrivileged((PrivilegedExceptionAction<Boolean>)new PrivilegedExceptionAction(canvas) {
            final Canvas val$component;
            final AWTSurfaceLock this$0;
            
            public Boolean run() throws LWJGLException {
                return AWTSurfaceLock.access$100(AWTSurfaceLock.access$000(this.this$0), this.val$component);
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    private static native boolean lockAndInitHandle(final ByteBuffer p0, final Canvas p1) throws LWJGLException;
    
    void unlock() throws LWJGLException {
        nUnlock(this.lock_buffer);
    }
    
    private static native void nUnlock(final ByteBuffer p0) throws LWJGLException;
    
    static ByteBuffer access$000(final AWTSurfaceLock awtSurfaceLock) {
        return awtSurfaceLock.lock_buffer;
    }
    
    static boolean access$100(final ByteBuffer byteBuffer, final Canvas canvas) throws LWJGLException {
        return lockAndInitHandle(byteBuffer, canvas);
    }
}
