package org.lwjgl.opencl;

import org.lwjgl.*;

public final class EXTMigrateMemobject
{
    public static final int CL_MIGRATE_MEM_OBJECT_HOST_EXT = 1;
    public static final int CL_COMMAND_MIGRATE_MEM_OBJECT_EXT = 16448;
    
    private EXTMigrateMemobject() {
    }
    
    public static int clEnqueueMigrateMemObjectEXT(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer, final long n, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3) {
        final long clEnqueueMigrateMemObjectEXT = CLCapabilities.clEnqueueMigrateMemObjectEXT;
        BufferChecks.checkFunctionAddress(clEnqueueMigrateMemObjectEXT);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        if (pointerBuffer2 != null) {
            BufferChecks.checkDirect(pointerBuffer2);
        }
        if (pointerBuffer3 != null) {
            BufferChecks.checkBuffer(pointerBuffer3, 1);
        }
        final int nclEnqueueMigrateMemObjectEXT = nclEnqueueMigrateMemObjectEXT(clCommandQueue.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), n, (pointerBuffer2 == null) ? 0 : pointerBuffer2.remaining(), MemoryUtil.getAddressSafe(pointerBuffer2), MemoryUtil.getAddressSafe(pointerBuffer3), clEnqueueMigrateMemObjectEXT);
        if (nclEnqueueMigrateMemObjectEXT == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer3);
        }
        return nclEnqueueMigrateMemObjectEXT;
    }
    
    static native int nclEnqueueMigrateMemObjectEXT(final long p0, final int p1, final long p2, final long p3, final int p4, final long p5, final long p6, final long p7);
    
    public static int clEnqueueMigrateMemObjectEXT(final CLCommandQueue clCommandQueue, final CLMem clMem, final long n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueMigrateMemObjectEXT = CLCapabilities.clEnqueueMigrateMemObjectEXT;
        BufferChecks.checkFunctionAddress(clEnqueueMigrateMemObjectEXT);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueMigrateMemObjectEXT = nclEnqueueMigrateMemObjectEXT(clCommandQueue.getPointer(), 1, APIUtil.getPointer(clMem), n, (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueMigrateMemObjectEXT);
        if (nclEnqueueMigrateMemObjectEXT == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueMigrateMemObjectEXT;
    }
}
