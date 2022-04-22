package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL15
{
    public static final int GL_ARRAY_BUFFER = 34962;
    public static final int GL_ELEMENT_ARRAY_BUFFER = 34963;
    public static final int GL_ARRAY_BUFFER_BINDING = 34964;
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 34965;
    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING = 34966;
    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING = 34967;
    public static final int GL_COLOR_ARRAY_BUFFER_BINDING = 34968;
    public static final int GL_INDEX_ARRAY_BUFFER_BINDING = 34969;
    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 34970;
    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = 34971;
    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 34972;
    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = 34973;
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING = 34974;
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 34975;
    public static final int GL_STREAM_DRAW = 35040;
    public static final int GL_STREAM_READ = 35041;
    public static final int GL_STREAM_COPY = 35042;
    public static final int GL_STATIC_DRAW = 35044;
    public static final int GL_STATIC_READ = 35045;
    public static final int GL_STATIC_COPY = 35046;
    public static final int GL_DYNAMIC_DRAW = 35048;
    public static final int GL_DYNAMIC_READ = 35049;
    public static final int GL_DYNAMIC_COPY = 35050;
    public static final int GL_READ_ONLY = 35000;
    public static final int GL_WRITE_ONLY = 35001;
    public static final int GL_READ_WRITE = 35002;
    public static final int GL_BUFFER_SIZE = 34660;
    public static final int GL_BUFFER_USAGE = 34661;
    public static final int GL_BUFFER_ACCESS = 35003;
    public static final int GL_BUFFER_MAPPED = 35004;
    public static final int GL_BUFFER_MAP_POINTER = 35005;
    public static final int GL_FOG_COORD_SRC = 33872;
    public static final int GL_FOG_COORD = 33873;
    public static final int GL_CURRENT_FOG_COORD = 33875;
    public static final int GL_FOG_COORD_ARRAY_TYPE = 33876;
    public static final int GL_FOG_COORD_ARRAY_STRIDE = 33877;
    public static final int GL_FOG_COORD_ARRAY_POINTER = 33878;
    public static final int GL_FOG_COORD_ARRAY = 33879;
    public static final int GL_FOG_COORD_ARRAY_BUFFER_BINDING = 34973;
    public static final int GL_SRC0_RGB = 34176;
    public static final int GL_SRC1_RGB = 34177;
    public static final int GL_SRC2_RGB = 34178;
    public static final int GL_SRC0_ALPHA = 34184;
    public static final int GL_SRC1_ALPHA = 34185;
    public static final int GL_SRC2_ALPHA = 34186;
    public static final int GL_SAMPLES_PASSED = 35092;
    public static final int GL_QUERY_COUNTER_BITS = 34916;
    public static final int GL_CURRENT_QUERY = 34917;
    public static final int GL_QUERY_RESULT = 34918;
    public static final int GL_QUERY_RESULT_AVAILABLE = 34919;
    
    private GL15() {
    }
    
    public static void glBindBuffer(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindBuffer = capabilities.glBindBuffer;
        BufferChecks.checkFunctionAddress(glBindBuffer);
        StateTracker.bindBuffer(capabilities, n, n2);
        nglBindBuffer(n, n2, glBindBuffer);
    }
    
    static native void nglBindBuffer(final int p0, final int p1, final long p2);
    
    public static void glDeleteBuffers(final IntBuffer intBuffer) {
        final long glDeleteBuffers = GLContext.getCapabilities().glDeleteBuffers;
        BufferChecks.checkFunctionAddress(glDeleteBuffers);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteBuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteBuffers);
    }
    
    static native void nglDeleteBuffers(final int p0, final long p1, final long p2);
    
    public static void glDeleteBuffers(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteBuffers = capabilities.glDeleteBuffers;
        BufferChecks.checkFunctionAddress(glDeleteBuffers);
        nglDeleteBuffers(1, APIUtil.getInt(capabilities, n), glDeleteBuffers);
    }
    
    public static void glGenBuffers(final IntBuffer intBuffer) {
        final long glGenBuffers = GLContext.getCapabilities().glGenBuffers;
        BufferChecks.checkFunctionAddress(glGenBuffers);
        BufferChecks.checkDirect(intBuffer);
        nglGenBuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenBuffers);
    }
    
    static native void nglGenBuffers(final int p0, final long p1, final long p2);
    
    public static int glGenBuffers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenBuffers = capabilities.glGenBuffers;
        BufferChecks.checkFunctionAddress(glGenBuffers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenBuffers(1, MemoryUtil.getAddress(bufferInt), glGenBuffers);
        return bufferInt.get(0);
    }
    
    public static boolean glIsBuffer(final int n) {
        final long glIsBuffer = GLContext.getCapabilities().glIsBuffer;
        BufferChecks.checkFunctionAddress(glIsBuffer);
        return nglIsBuffer(n, glIsBuffer);
    }
    
    static native boolean nglIsBuffer(final int p0, final long p1);
    
    public static void glBufferData(final int n, final long n2, final int n3) {
        final long glBufferData = GLContext.getCapabilities().glBufferData;
        BufferChecks.checkFunctionAddress(glBufferData);
        nglBufferData(n, n2, 0L, n3, glBufferData);
    }
    
    public static void glBufferData(final int n, final ByteBuffer byteBuffer, final int n2) {
        final long glBufferData = GLContext.getCapabilities().glBufferData;
        BufferChecks.checkFunctionAddress(glBufferData);
        BufferChecks.checkDirect(byteBuffer);
        nglBufferData(n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), n2, glBufferData);
    }
    
    public static void glBufferData(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        final long glBufferData = GLContext.getCapabilities().glBufferData;
        BufferChecks.checkFunctionAddress(glBufferData);
        BufferChecks.checkDirect(doubleBuffer);
        nglBufferData(n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), n2, glBufferData);
    }
    
    public static void glBufferData(final int n, final FloatBuffer floatBuffer, final int n2) {
        final long glBufferData = GLContext.getCapabilities().glBufferData;
        BufferChecks.checkFunctionAddress(glBufferData);
        BufferChecks.checkDirect(floatBuffer);
        nglBufferData(n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), n2, glBufferData);
    }
    
    public static void glBufferData(final int n, final IntBuffer intBuffer, final int n2) {
        final long glBufferData = GLContext.getCapabilities().glBufferData;
        BufferChecks.checkFunctionAddress(glBufferData);
        BufferChecks.checkDirect(intBuffer);
        nglBufferData(n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), n2, glBufferData);
    }
    
    public static void glBufferData(final int n, final ShortBuffer shortBuffer, final int n2) {
        final long glBufferData = GLContext.getCapabilities().glBufferData;
        BufferChecks.checkFunctionAddress(glBufferData);
        BufferChecks.checkDirect(shortBuffer);
        nglBufferData(n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), n2, glBufferData);
    }
    
    static native void nglBufferData(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    public static void glBufferSubData(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glBufferSubData = GLContext.getCapabilities().glBufferSubData;
        BufferChecks.checkFunctionAddress(glBufferSubData);
        BufferChecks.checkDirect(byteBuffer);
        nglBufferSubData(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glBufferSubData);
    }
    
    public static void glBufferSubData(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glBufferSubData = GLContext.getCapabilities().glBufferSubData;
        BufferChecks.checkFunctionAddress(glBufferSubData);
        BufferChecks.checkDirect(doubleBuffer);
        nglBufferSubData(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glBufferSubData);
    }
    
    public static void glBufferSubData(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glBufferSubData = GLContext.getCapabilities().glBufferSubData;
        BufferChecks.checkFunctionAddress(glBufferSubData);
        BufferChecks.checkDirect(floatBuffer);
        nglBufferSubData(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glBufferSubData);
    }
    
    public static void glBufferSubData(final int n, final long n2, final IntBuffer intBuffer) {
        final long glBufferSubData = GLContext.getCapabilities().glBufferSubData;
        BufferChecks.checkFunctionAddress(glBufferSubData);
        BufferChecks.checkDirect(intBuffer);
        nglBufferSubData(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glBufferSubData);
    }
    
    public static void glBufferSubData(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glBufferSubData = GLContext.getCapabilities().glBufferSubData;
        BufferChecks.checkFunctionAddress(glBufferSubData);
        BufferChecks.checkDirect(shortBuffer);
        nglBufferSubData(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glBufferSubData);
    }
    
    static native void nglBufferSubData(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glGetBufferSubData(final int n, final long n2, final ByteBuffer byteBuffer) {
        final long glGetBufferSubData = GLContext.getCapabilities().glGetBufferSubData;
        BufferChecks.checkFunctionAddress(glGetBufferSubData);
        BufferChecks.checkDirect(byteBuffer);
        nglGetBufferSubData(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glGetBufferSubData);
    }
    
    public static void glGetBufferSubData(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        final long glGetBufferSubData = GLContext.getCapabilities().glGetBufferSubData;
        BufferChecks.checkFunctionAddress(glGetBufferSubData);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetBufferSubData(n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), glGetBufferSubData);
    }
    
    public static void glGetBufferSubData(final int n, final long n2, final FloatBuffer floatBuffer) {
        final long glGetBufferSubData = GLContext.getCapabilities().glGetBufferSubData;
        BufferChecks.checkFunctionAddress(glGetBufferSubData);
        BufferChecks.checkDirect(floatBuffer);
        nglGetBufferSubData(n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), glGetBufferSubData);
    }
    
    public static void glGetBufferSubData(final int n, final long n2, final IntBuffer intBuffer) {
        final long glGetBufferSubData = GLContext.getCapabilities().glGetBufferSubData;
        BufferChecks.checkFunctionAddress(glGetBufferSubData);
        BufferChecks.checkDirect(intBuffer);
        nglGetBufferSubData(n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), glGetBufferSubData);
    }
    
    public static void glGetBufferSubData(final int n, final long n2, final ShortBuffer shortBuffer) {
        final long glGetBufferSubData = GLContext.getCapabilities().glGetBufferSubData;
        BufferChecks.checkFunctionAddress(glGetBufferSubData);
        BufferChecks.checkDirect(shortBuffer);
        nglGetBufferSubData(n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), glGetBufferSubData);
    }
    
    static native void nglGetBufferSubData(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public static ByteBuffer glMapBuffer(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glMapBuffer = GLContext.getCapabilities().glMapBuffer;
        BufferChecks.checkFunctionAddress(glMapBuffer);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapBuffer = nglMapBuffer(n, n2, glGetBufferParameteri(n, 34660), byteBuffer, glMapBuffer);
        return (LWJGLUtil.CHECKS && nglMapBuffer == null) ? null : nglMapBuffer.order(ByteOrder.nativeOrder());
    }
    
    public static ByteBuffer glMapBuffer(final int n, final int n2, final long n3, final ByteBuffer byteBuffer) {
        final long glMapBuffer = GLContext.getCapabilities().glMapBuffer;
        BufferChecks.checkFunctionAddress(glMapBuffer);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapBuffer = nglMapBuffer(n, n2, n3, byteBuffer, glMapBuffer);
        return (LWJGLUtil.CHECKS && nglMapBuffer == null) ? null : nglMapBuffer.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapBuffer(final int p0, final int p1, final long p2, final ByteBuffer p3, final long p4);
    
    public static boolean glUnmapBuffer(final int n) {
        final long glUnmapBuffer = GLContext.getCapabilities().glUnmapBuffer;
        BufferChecks.checkFunctionAddress(glUnmapBuffer);
        return nglUnmapBuffer(n, glUnmapBuffer);
    }
    
    static native boolean nglUnmapBuffer(final int p0, final long p1);
    
    public static void glGetBufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetBufferParameteriv = GLContext.getCapabilities().glGetBufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetBufferParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetBufferParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetBufferParameteriv);
    }
    
    static native void nglGetBufferParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetBufferParameter(final int n, final int n2) {
        return glGetBufferParameteri(n, n2);
    }
    
    public static int glGetBufferParameteri(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetBufferParameteriv = capabilities.glGetBufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetBufferParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetBufferParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetBufferParameteriv);
        return bufferInt.get(0);
    }
    
    public static ByteBuffer glGetBufferPointer(final int n, final int n2) {
        final long glGetBufferPointerv = GLContext.getCapabilities().glGetBufferPointerv;
        BufferChecks.checkFunctionAddress(glGetBufferPointerv);
        final ByteBuffer nglGetBufferPointerv = nglGetBufferPointerv(n, n2, glGetBufferParameteri(n, 34660), glGetBufferPointerv);
        return (LWJGLUtil.CHECKS && nglGetBufferPointerv == null) ? null : nglGetBufferPointerv.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetBufferPointerv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGenQueries(final IntBuffer intBuffer) {
        final long glGenQueries = GLContext.getCapabilities().glGenQueries;
        BufferChecks.checkFunctionAddress(glGenQueries);
        BufferChecks.checkDirect(intBuffer);
        nglGenQueries(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenQueries);
    }
    
    static native void nglGenQueries(final int p0, final long p1, final long p2);
    
    public static int glGenQueries() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenQueries = capabilities.glGenQueries;
        BufferChecks.checkFunctionAddress(glGenQueries);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenQueries(1, MemoryUtil.getAddress(bufferInt), glGenQueries);
        return bufferInt.get(0);
    }
    
    public static void glDeleteQueries(final IntBuffer intBuffer) {
        final long glDeleteQueries = GLContext.getCapabilities().glDeleteQueries;
        BufferChecks.checkFunctionAddress(glDeleteQueries);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteQueries(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteQueries);
    }
    
    static native void nglDeleteQueries(final int p0, final long p1, final long p2);
    
    public static void glDeleteQueries(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteQueries = capabilities.glDeleteQueries;
        BufferChecks.checkFunctionAddress(glDeleteQueries);
        nglDeleteQueries(1, APIUtil.getInt(capabilities, n), glDeleteQueries);
    }
    
    public static boolean glIsQuery(final int n) {
        final long glIsQuery = GLContext.getCapabilities().glIsQuery;
        BufferChecks.checkFunctionAddress(glIsQuery);
        return nglIsQuery(n, glIsQuery);
    }
    
    static native boolean nglIsQuery(final int p0, final long p1);
    
    public static void glBeginQuery(final int n, final int n2) {
        final long glBeginQuery = GLContext.getCapabilities().glBeginQuery;
        BufferChecks.checkFunctionAddress(glBeginQuery);
        nglBeginQuery(n, n2, glBeginQuery);
    }
    
    static native void nglBeginQuery(final int p0, final int p1, final long p2);
    
    public static void glEndQuery(final int n) {
        final long glEndQuery = GLContext.getCapabilities().glEndQuery;
        BufferChecks.checkFunctionAddress(glEndQuery);
        nglEndQuery(n, glEndQuery);
    }
    
    static native void nglEndQuery(final int p0, final long p1);
    
    public static void glGetQuery(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetQueryiv = GLContext.getCapabilities().glGetQueryiv;
        BufferChecks.checkFunctionAddress(glGetQueryiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetQueryiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetQueryiv);
    }
    
    static native void nglGetQueryiv(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetQuery(final int n, final int n2) {
        return glGetQueryi(n, n2);
    }
    
    public static int glGetQueryi(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryiv = capabilities.glGetQueryiv;
        BufferChecks.checkFunctionAddress(glGetQueryiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetQueryiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetQueryiv);
        return bufferInt.get(0);
    }
    
    public static void glGetQueryObject(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetQueryObjectiv = GLContext.getCapabilities().glGetQueryObjectiv;
        BufferChecks.checkFunctionAddress(glGetQueryObjectiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetQueryObjectiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetQueryObjectiv);
    }
    
    static native void nglGetQueryObjectiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetQueryObjecti(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjectiv = capabilities.glGetQueryObjectiv;
        BufferChecks.checkFunctionAddress(glGetQueryObjectiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetQueryObjectiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetQueryObjectiv);
        return bufferInt.get(0);
    }
    
    public static void glGetQueryObjectu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetQueryObjectuiv = GLContext.getCapabilities().glGetQueryObjectuiv;
        BufferChecks.checkFunctionAddress(glGetQueryObjectuiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetQueryObjectuiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetQueryObjectuiv);
    }
    
    static native void nglGetQueryObjectuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetQueryObjectui(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetQueryObjectuiv = capabilities.glGetQueryObjectuiv;
        BufferChecks.checkFunctionAddress(glGetQueryObjectuiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetQueryObjectuiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetQueryObjectuiv);
        return bufferInt.get(0);
    }
}
