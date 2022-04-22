package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVVertexBufferUnifiedMemory
{
    public static final int GL_VERTEX_ATTRIB_ARRAY_UNIFIED_NV = 36638;
    public static final int GL_ELEMENT_ARRAY_UNIFIED_NV = 36639;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ADDRESS_NV = 36640;
    public static final int GL_TEXTURE_COORD_ARRAY_ADDRESS_NV = 36645;
    public static final int GL_VERTEX_ARRAY_ADDRESS_NV = 36641;
    public static final int GL_NORMAL_ARRAY_ADDRESS_NV = 36642;
    public static final int GL_COLOR_ARRAY_ADDRESS_NV = 36643;
    public static final int GL_INDEX_ARRAY_ADDRESS_NV = 36644;
    public static final int GL_EDGE_FLAG_ARRAY_ADDRESS_NV = 36646;
    public static final int GL_SECONDARY_COLOR_ARRAY_ADDRESS_NV = 36647;
    public static final int GL_FOG_COORD_ARRAY_ADDRESS_NV = 36648;
    public static final int GL_ELEMENT_ARRAY_ADDRESS_NV = 36649;
    public static final int GL_VERTEX_ATTRIB_ARRAY_LENGTH_NV = 36650;
    public static final int GL_TEXTURE_COORD_ARRAY_LENGTH_NV = 36655;
    public static final int GL_VERTEX_ARRAY_LENGTH_NV = 36651;
    public static final int GL_NORMAL_ARRAY_LENGTH_NV = 36652;
    public static final int GL_COLOR_ARRAY_LENGTH_NV = 36653;
    public static final int GL_INDEX_ARRAY_LENGTH_NV = 36654;
    public static final int GL_EDGE_FLAG_ARRAY_LENGTH_NV = 36656;
    public static final int GL_SECONDARY_COLOR_ARRAY_LENGTH_NV = 36657;
    public static final int GL_FOG_COORD_ARRAY_LENGTH_NV = 36658;
    public static final int GL_ELEMENT_ARRAY_LENGTH_NV = 36659;
    
    private NVVertexBufferUnifiedMemory() {
    }
    
    public static void glBufferAddressRangeNV(final int n, final int n2, final long n3, final long n4) {
        final long glBufferAddressRangeNV = GLContext.getCapabilities().glBufferAddressRangeNV;
        BufferChecks.checkFunctionAddress(glBufferAddressRangeNV);
        nglBufferAddressRangeNV(n, n2, n3, n4, glBufferAddressRangeNV);
    }
    
    static native void nglBufferAddressRangeNV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static void glVertexFormatNV(final int n, final int n2, final int n3) {
        final long glVertexFormatNV = GLContext.getCapabilities().glVertexFormatNV;
        BufferChecks.checkFunctionAddress(glVertexFormatNV);
        nglVertexFormatNV(n, n2, n3, glVertexFormatNV);
    }
    
    static native void nglVertexFormatNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glNormalFormatNV(final int n, final int n2) {
        final long glNormalFormatNV = GLContext.getCapabilities().glNormalFormatNV;
        BufferChecks.checkFunctionAddress(glNormalFormatNV);
        nglNormalFormatNV(n, n2, glNormalFormatNV);
    }
    
    static native void nglNormalFormatNV(final int p0, final int p1, final long p2);
    
    public static void glColorFormatNV(final int n, final int n2, final int n3) {
        final long glColorFormatNV = GLContext.getCapabilities().glColorFormatNV;
        BufferChecks.checkFunctionAddress(glColorFormatNV);
        nglColorFormatNV(n, n2, n3, glColorFormatNV);
    }
    
    static native void nglColorFormatNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glIndexFormatNV(final int n, final int n2) {
        final long glIndexFormatNV = GLContext.getCapabilities().glIndexFormatNV;
        BufferChecks.checkFunctionAddress(glIndexFormatNV);
        nglIndexFormatNV(n, n2, glIndexFormatNV);
    }
    
    static native void nglIndexFormatNV(final int p0, final int p1, final long p2);
    
    public static void glTexCoordFormatNV(final int n, final int n2, final int n3) {
        final long glTexCoordFormatNV = GLContext.getCapabilities().glTexCoordFormatNV;
        BufferChecks.checkFunctionAddress(glTexCoordFormatNV);
        nglTexCoordFormatNV(n, n2, n3, glTexCoordFormatNV);
    }
    
    static native void nglTexCoordFormatNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glEdgeFlagFormatNV(final int n) {
        final long glEdgeFlagFormatNV = GLContext.getCapabilities().glEdgeFlagFormatNV;
        BufferChecks.checkFunctionAddress(glEdgeFlagFormatNV);
        nglEdgeFlagFormatNV(n, glEdgeFlagFormatNV);
    }
    
    static native void nglEdgeFlagFormatNV(final int p0, final long p1);
    
    public static void glSecondaryColorFormatNV(final int n, final int n2, final int n3) {
        final long glSecondaryColorFormatNV = GLContext.getCapabilities().glSecondaryColorFormatNV;
        BufferChecks.checkFunctionAddress(glSecondaryColorFormatNV);
        nglSecondaryColorFormatNV(n, n2, n3, glSecondaryColorFormatNV);
    }
    
    static native void nglSecondaryColorFormatNV(final int p0, final int p1, final int p2, final long p3);
    
    public static void glFogCoordFormatNV(final int n, final int n2) {
        final long glFogCoordFormatNV = GLContext.getCapabilities().glFogCoordFormatNV;
        BufferChecks.checkFunctionAddress(glFogCoordFormatNV);
        nglFogCoordFormatNV(n, n2, glFogCoordFormatNV);
    }
    
    static native void nglFogCoordFormatNV(final int p0, final int p1, final long p2);
    
    public static void glVertexAttribFormatNV(final int n, final int n2, final int n3, final boolean b, final int n4) {
        final long glVertexAttribFormatNV = GLContext.getCapabilities().glVertexAttribFormatNV;
        BufferChecks.checkFunctionAddress(glVertexAttribFormatNV);
        nglVertexAttribFormatNV(n, n2, n3, b, n4, glVertexAttribFormatNV);
    }
    
    static native void nglVertexAttribFormatNV(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5);
    
    public static void glVertexAttribIFormatNV(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribIFormatNV = GLContext.getCapabilities().glVertexAttribIFormatNV;
        BufferChecks.checkFunctionAddress(glVertexAttribIFormatNV);
        nglVertexAttribIFormatNV(n, n2, n3, n4, glVertexAttribIFormatNV);
    }
    
    static native void nglVertexAttribIFormatNV(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetIntegeruNV(final int n, final int n2, final LongBuffer longBuffer) {
        final long glGetIntegerui64i_vNV = GLContext.getCapabilities().glGetIntegerui64i_vNV;
        BufferChecks.checkFunctionAddress(glGetIntegerui64i_vNV);
        BufferChecks.checkBuffer(longBuffer, 1);
        nglGetIntegerui64i_vNV(n, n2, MemoryUtil.getAddress(longBuffer), glGetIntegerui64i_vNV);
    }
    
    static native void nglGetIntegerui64i_vNV(final int p0, final int p1, final long p2, final long p3);
    
    public static long glGetIntegerui64NV(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetIntegerui64i_vNV = capabilities.glGetIntegerui64i_vNV;
        BufferChecks.checkFunctionAddress(glGetIntegerui64i_vNV);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetIntegerui64i_vNV(n, n2, MemoryUtil.getAddress(bufferLong), glGetIntegerui64i_vNV);
        return bufferLong.get(0);
    }
}
