package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

final class APPLEContextLoggingFunctions
{
    private APPLEContextLoggingFunctions() {
    }
    
    static void clLogMessagesToSystemLogAPPLE(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ByteBuffer byteBuffer3) {
        final long clLogMessagesToSystemLogAPPLE = CLCapabilities.clLogMessagesToSystemLogAPPLE;
        BufferChecks.checkFunctionAddress(clLogMessagesToSystemLogAPPLE);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(byteBuffer3);
        nclLogMessagesToSystemLogAPPLE(MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer3), clLogMessagesToSystemLogAPPLE);
    }
    
    static native void nclLogMessagesToSystemLogAPPLE(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    static void clLogMessagesToStdoutAPPLE(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ByteBuffer byteBuffer3) {
        final long clLogMessagesToStdoutAPPLE = CLCapabilities.clLogMessagesToStdoutAPPLE;
        BufferChecks.checkFunctionAddress(clLogMessagesToStdoutAPPLE);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(byteBuffer3);
        nclLogMessagesToStdoutAPPLE(MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer3), clLogMessagesToStdoutAPPLE);
    }
    
    static native void nclLogMessagesToStdoutAPPLE(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    static void clLogMessagesToStderrAPPLE(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ByteBuffer byteBuffer3) {
        final long clLogMessagesToStderrAPPLE = CLCapabilities.clLogMessagesToStderrAPPLE;
        BufferChecks.checkFunctionAddress(clLogMessagesToStderrAPPLE);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(byteBuffer3);
        nclLogMessagesToStderrAPPLE(MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), byteBuffer2.remaining(), MemoryUtil.getAddress(byteBuffer3), clLogMessagesToStderrAPPLE);
    }
    
    static native void nclLogMessagesToStderrAPPLE(final long p0, final long p1, final long p2, final long p3, final long p4);
}
