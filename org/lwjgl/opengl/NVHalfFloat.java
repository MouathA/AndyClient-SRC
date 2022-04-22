package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class NVHalfFloat
{
    public static final int GL_HALF_FLOAT_NV = 5131;
    
    private NVHalfFloat() {
    }
    
    public static void glVertex2hNV(final short n, final short n2) {
        final long glVertex2hNV = GLContext.getCapabilities().glVertex2hNV;
        BufferChecks.checkFunctionAddress(glVertex2hNV);
        nglVertex2hNV(n, n2, glVertex2hNV);
    }
    
    static native void nglVertex2hNV(final short p0, final short p1, final long p2);
    
    public static void glVertex3hNV(final short n, final short n2, final short n3) {
        final long glVertex3hNV = GLContext.getCapabilities().glVertex3hNV;
        BufferChecks.checkFunctionAddress(glVertex3hNV);
        nglVertex3hNV(n, n2, n3, glVertex3hNV);
    }
    
    static native void nglVertex3hNV(final short p0, final short p1, final short p2, final long p3);
    
    public static void glVertex4hNV(final short n, final short n2, final short n3, final short n4) {
        final long glVertex4hNV = GLContext.getCapabilities().glVertex4hNV;
        BufferChecks.checkFunctionAddress(glVertex4hNV);
        nglVertex4hNV(n, n2, n3, n4, glVertex4hNV);
    }
    
    static native void nglVertex4hNV(final short p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glNormal3hNV(final short n, final short n2, final short n3) {
        final long glNormal3hNV = GLContext.getCapabilities().glNormal3hNV;
        BufferChecks.checkFunctionAddress(glNormal3hNV);
        nglNormal3hNV(n, n2, n3, glNormal3hNV);
    }
    
    static native void nglNormal3hNV(final short p0, final short p1, final short p2, final long p3);
    
    public static void glColor3hNV(final short n, final short n2, final short n3) {
        final long glColor3hNV = GLContext.getCapabilities().glColor3hNV;
        BufferChecks.checkFunctionAddress(glColor3hNV);
        nglColor3hNV(n, n2, n3, glColor3hNV);
    }
    
    static native void nglColor3hNV(final short p0, final short p1, final short p2, final long p3);
    
    public static void glColor4hNV(final short n, final short n2, final short n3, final short n4) {
        final long glColor4hNV = GLContext.getCapabilities().glColor4hNV;
        BufferChecks.checkFunctionAddress(glColor4hNV);
        nglColor4hNV(n, n2, n3, n4, glColor4hNV);
    }
    
    static native void nglColor4hNV(final short p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glTexCoord1hNV(final short n) {
        final long glTexCoord1hNV = GLContext.getCapabilities().glTexCoord1hNV;
        BufferChecks.checkFunctionAddress(glTexCoord1hNV);
        nglTexCoord1hNV(n, glTexCoord1hNV);
    }
    
    static native void nglTexCoord1hNV(final short p0, final long p1);
    
    public static void glTexCoord2hNV(final short n, final short n2) {
        final long glTexCoord2hNV = GLContext.getCapabilities().glTexCoord2hNV;
        BufferChecks.checkFunctionAddress(glTexCoord2hNV);
        nglTexCoord2hNV(n, n2, glTexCoord2hNV);
    }
    
    static native void nglTexCoord2hNV(final short p0, final short p1, final long p2);
    
    public static void glTexCoord3hNV(final short n, final short n2, final short n3) {
        final long glTexCoord3hNV = GLContext.getCapabilities().glTexCoord3hNV;
        BufferChecks.checkFunctionAddress(glTexCoord3hNV);
        nglTexCoord3hNV(n, n2, n3, glTexCoord3hNV);
    }
    
    static native void nglTexCoord3hNV(final short p0, final short p1, final short p2, final long p3);
    
    public static void glTexCoord4hNV(final short n, final short n2, final short n3, final short n4) {
        final long glTexCoord4hNV = GLContext.getCapabilities().glTexCoord4hNV;
        BufferChecks.checkFunctionAddress(glTexCoord4hNV);
        nglTexCoord4hNV(n, n2, n3, n4, glTexCoord4hNV);
    }
    
    static native void nglTexCoord4hNV(final short p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glMultiTexCoord1hNV(final int n, final short n2) {
        final long glMultiTexCoord1hNV = GLContext.getCapabilities().glMultiTexCoord1hNV;
        BufferChecks.checkFunctionAddress(glMultiTexCoord1hNV);
        nglMultiTexCoord1hNV(n, n2, glMultiTexCoord1hNV);
    }
    
    static native void nglMultiTexCoord1hNV(final int p0, final short p1, final long p2);
    
    public static void glMultiTexCoord2hNV(final int n, final short n2, final short n3) {
        final long glMultiTexCoord2hNV = GLContext.getCapabilities().glMultiTexCoord2hNV;
        BufferChecks.checkFunctionAddress(glMultiTexCoord2hNV);
        nglMultiTexCoord2hNV(n, n2, n3, glMultiTexCoord2hNV);
    }
    
    static native void nglMultiTexCoord2hNV(final int p0, final short p1, final short p2, final long p3);
    
    public static void glMultiTexCoord3hNV(final int n, final short n2, final short n3, final short n4) {
        final long glMultiTexCoord3hNV = GLContext.getCapabilities().glMultiTexCoord3hNV;
        BufferChecks.checkFunctionAddress(glMultiTexCoord3hNV);
        nglMultiTexCoord3hNV(n, n2, n3, n4, glMultiTexCoord3hNV);
    }
    
    static native void nglMultiTexCoord3hNV(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glMultiTexCoord4hNV(final int n, final short n2, final short n3, final short n4, final short n5) {
        final long glMultiTexCoord4hNV = GLContext.getCapabilities().glMultiTexCoord4hNV;
        BufferChecks.checkFunctionAddress(glMultiTexCoord4hNV);
        nglMultiTexCoord4hNV(n, n2, n3, n4, n5, glMultiTexCoord4hNV);
    }
    
    static native void nglMultiTexCoord4hNV(final int p0, final short p1, final short p2, final short p3, final short p4, final long p5);
    
    public static void glFogCoordhNV(final short n) {
        final long glFogCoordhNV = GLContext.getCapabilities().glFogCoordhNV;
        BufferChecks.checkFunctionAddress(glFogCoordhNV);
        nglFogCoordhNV(n, glFogCoordhNV);
    }
    
    static native void nglFogCoordhNV(final short p0, final long p1);
    
    public static void glSecondaryColor3hNV(final short n, final short n2, final short n3) {
        final long glSecondaryColor3hNV = GLContext.getCapabilities().glSecondaryColor3hNV;
        BufferChecks.checkFunctionAddress(glSecondaryColor3hNV);
        nglSecondaryColor3hNV(n, n2, n3, glSecondaryColor3hNV);
    }
    
    static native void nglSecondaryColor3hNV(final short p0, final short p1, final short p2, final long p3);
    
    public static void glVertexWeighthNV(final short n) {
        final long glVertexWeighthNV = GLContext.getCapabilities().glVertexWeighthNV;
        BufferChecks.checkFunctionAddress(glVertexWeighthNV);
        nglVertexWeighthNV(n, glVertexWeighthNV);
    }
    
    static native void nglVertexWeighthNV(final short p0, final long p1);
    
    public static void glVertexAttrib1hNV(final int n, final short n2) {
        final long glVertexAttrib1hNV = GLContext.getCapabilities().glVertexAttrib1hNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib1hNV);
        nglVertexAttrib1hNV(n, n2, glVertexAttrib1hNV);
    }
    
    static native void nglVertexAttrib1hNV(final int p0, final short p1, final long p2);
    
    public static void glVertexAttrib2hNV(final int n, final short n2, final short n3) {
        final long glVertexAttrib2hNV = GLContext.getCapabilities().glVertexAttrib2hNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib2hNV);
        nglVertexAttrib2hNV(n, n2, n3, glVertexAttrib2hNV);
    }
    
    static native void nglVertexAttrib2hNV(final int p0, final short p1, final short p2, final long p3);
    
    public static void glVertexAttrib3hNV(final int n, final short n2, final short n3, final short n4) {
        final long glVertexAttrib3hNV = GLContext.getCapabilities().glVertexAttrib3hNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib3hNV);
        nglVertexAttrib3hNV(n, n2, n3, n4, glVertexAttrib3hNV);
    }
    
    static native void nglVertexAttrib3hNV(final int p0, final short p1, final short p2, final short p3, final long p4);
    
    public static void glVertexAttrib4hNV(final int n, final short n2, final short n3, final short n4, final short n5) {
        final long glVertexAttrib4hNV = GLContext.getCapabilities().glVertexAttrib4hNV;
        BufferChecks.checkFunctionAddress(glVertexAttrib4hNV);
        nglVertexAttrib4hNV(n, n2, n3, n4, n5, glVertexAttrib4hNV);
    }
    
    static native void nglVertexAttrib4hNV(final int p0, final short p1, final short p2, final short p3, final short p4, final long p5);
    
    public static void glVertexAttribs1NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs1hvNV = GLContext.getCapabilities().glVertexAttribs1hvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs1hvNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs1hvNV(n, shortBuffer.remaining(), MemoryUtil.getAddress(shortBuffer), glVertexAttribs1hvNV);
    }
    
    static native void nglVertexAttribs1hvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs2NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs2hvNV = GLContext.getCapabilities().glVertexAttribs2hvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs2hvNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs2hvNV(n, shortBuffer.remaining() >> 1, MemoryUtil.getAddress(shortBuffer), glVertexAttribs2hvNV);
    }
    
    static native void nglVertexAttribs2hvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs3NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs3hvNV = GLContext.getCapabilities().glVertexAttribs3hvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs3hvNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs3hvNV(n, shortBuffer.remaining() / 3, MemoryUtil.getAddress(shortBuffer), glVertexAttribs3hvNV);
    }
    
    static native void nglVertexAttribs3hvNV(final int p0, final int p1, final long p2, final long p3);
    
    public static void glVertexAttribs4NV(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribs4hvNV = GLContext.getCapabilities().glVertexAttribs4hvNV;
        BufferChecks.checkFunctionAddress(glVertexAttribs4hvNV);
        BufferChecks.checkDirect(shortBuffer);
        nglVertexAttribs4hvNV(n, shortBuffer.remaining() >> 2, MemoryUtil.getAddress(shortBuffer), glVertexAttribs4hvNV);
    }
    
    static native void nglVertexAttribs4hvNV(final int p0, final int p1, final long p2, final long p3);
}
