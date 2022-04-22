package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBDirectStateAccess
{
    public static final int GL_TEXTURE_TARGET = 4102;
    public static final int GL_QUERY_TARGET = 33514;
    public static final int GL_TEXTURE_BINDING = 33515;
    
    private ARBDirectStateAccess() {
    }
    
    public static void glCreateTransformFeedbacks(final IntBuffer intBuffer) {
        GL45.glCreateTransformFeedbacks(intBuffer);
    }
    
    public static int glCreateTransformFeedbacks() {
        return GL45.glCreateTransformFeedbacks();
    }
    
    public static void glTransformFeedbackBufferBase(final int n, final int n2, final int n3) {
        GL45.glTransformFeedbackBufferBase(n, n2, n3);
    }
    
    public static void glTransformFeedbackBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        GL45.glTransformFeedbackBufferRange(n, n2, n3, n4, n5);
    }
    
    public static void glGetTransformFeedback(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetTransformFeedback(n, n2, intBuffer);
    }
    
    public static int glGetTransformFeedbacki(final int n, final int n2) {
        return GL45.glGetTransformFeedbacki(n, n2);
    }
    
    public static void glGetTransformFeedback(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL45.glGetTransformFeedback(n, n2, n3, intBuffer);
    }
    
    public static int glGetTransformFeedbacki(final int n, final int n2, final int n3) {
        return GL45.glGetTransformFeedbacki(n, n2, n3);
    }
    
    public static void glGetTransformFeedback(final int n, final int n2, final int n3, final LongBuffer longBuffer) {
        GL45.glGetTransformFeedback(n, n2, n3, longBuffer);
    }
    
    public static long glGetTransformFeedbacki64(final int n, final int n2, final int n3) {
        return GL45.glGetTransformFeedbacki64(n, n2, n3);
    }
    
    public static void glCreateBuffers(final IntBuffer intBuffer) {
        GL45.glCreateBuffers(intBuffer);
    }
    
    public static int glCreateBuffers() {
        return GL45.glCreateBuffers();
    }
    
    public static void glNamedBufferStorage(final int n, final ByteBuffer byteBuffer, final int n2) {
        GL45.glNamedBufferStorage(n, byteBuffer, n2);
    }
    
    public static void glNamedBufferStorage(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        GL45.glNamedBufferStorage(n, doubleBuffer, n2);
    }
    
    public static void glNamedBufferStorage(final int n, final FloatBuffer floatBuffer, final int n2) {
        GL45.glNamedBufferStorage(n, floatBuffer, n2);
    }
    
    public static void glNamedBufferStorage(final int n, final IntBuffer intBuffer, final int n2) {
        GL45.glNamedBufferStorage(n, intBuffer, n2);
    }
    
    public static void glNamedBufferStorage(final int n, final ShortBuffer shortBuffer, final int n2) {
        GL45.glNamedBufferStorage(n, shortBuffer, n2);
    }
    
    public static void glNamedBufferStorage(final int n, final LongBuffer longBuffer, final int n2) {
        GL45.glNamedBufferStorage(n, longBuffer, n2);
    }
    
    public static void glNamedBufferStorage(final int n, final long n2, final int n3) {
        GL45.glNamedBufferStorage(n, n2, n3);
    }
    
    public static void glNamedBufferData(final int n, final long n2, final int n3) {
        GL45.glNamedBufferData(n, n2, n3);
    }
    
    public static void glNamedBufferData(final int n, final ByteBuffer byteBuffer, final int n2) {
        GL45.glNamedBufferData(n, byteBuffer, n2);
    }
    
    public static void glNamedBufferData(final int n, final DoubleBuffer doubleBuffer, final int n2) {
        GL45.glNamedBufferData(n, doubleBuffer, n2);
    }
    
    public static void glNamedBufferData(final int n, final FloatBuffer floatBuffer, final int n2) {
        GL45.glNamedBufferData(n, floatBuffer, n2);
    }
    
    public static void glNamedBufferData(final int n, final IntBuffer intBuffer, final int n2) {
        GL45.glNamedBufferData(n, intBuffer, n2);
    }
    
    public static void glNamedBufferData(final int n, final ShortBuffer shortBuffer, final int n2) {
        GL45.glNamedBufferData(n, shortBuffer, n2);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final ByteBuffer byteBuffer) {
        GL45.glNamedBufferSubData(n, n2, byteBuffer);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        GL45.glNamedBufferSubData(n, n2, doubleBuffer);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final FloatBuffer floatBuffer) {
        GL45.glNamedBufferSubData(n, n2, floatBuffer);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final IntBuffer intBuffer) {
        GL45.glNamedBufferSubData(n, n2, intBuffer);
    }
    
    public static void glNamedBufferSubData(final int n, final long n2, final ShortBuffer shortBuffer) {
        GL45.glNamedBufferSubData(n, n2, shortBuffer);
    }
    
    public static void glCopyNamedBufferSubData(final int n, final int n2, final long n3, final long n4, final long n5) {
        GL45.glCopyNamedBufferSubData(n, n2, n3, n4, n5);
    }
    
    public static void glClearNamedBufferData(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        GL45.glClearNamedBufferData(n, n2, n3, n4, byteBuffer);
    }
    
    public static void glClearNamedBufferSubData(final int n, final int n2, final long n3, final long n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        GL45.glClearNamedBufferSubData(n, n2, n3, n4, n5, n6, byteBuffer);
    }
    
    public static ByteBuffer glMapNamedBuffer(final int n, final int n2, final ByteBuffer byteBuffer) {
        return GL45.glMapNamedBuffer(n, n2, byteBuffer);
    }
    
    public static ByteBuffer glMapNamedBuffer(final int n, final int n2, final long n3, final ByteBuffer byteBuffer) {
        return GL45.glMapNamedBuffer(n, n2, n3, byteBuffer);
    }
    
    public static ByteBuffer glMapNamedBufferRange(final int n, final long n2, final long n3, final int n4, final ByteBuffer byteBuffer) {
        return GL45.glMapNamedBufferRange(n, n2, n3, n4, byteBuffer);
    }
    
    public static boolean glUnmapNamedBuffer(final int n) {
        return GL45.glUnmapNamedBuffer(n);
    }
    
    public static void glFlushMappedNamedBufferRange(final int n, final long n2, final long n3) {
        GL45.glFlushMappedNamedBufferRange(n, n2, n3);
    }
    
    public static void glGetNamedBufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetNamedBufferParameter(n, n2, intBuffer);
    }
    
    public static int glGetNamedBufferParameteri(final int n, final int n2) {
        return GL45.glGetNamedBufferParameteri(n, n2);
    }
    
    public static void glGetNamedBufferParameter(final int n, final int n2, final LongBuffer longBuffer) {
        GL45.glGetNamedBufferParameter(n, n2, longBuffer);
    }
    
    public static long glGetNamedBufferParameteri64(final int n, final int n2) {
        return GL45.glGetNamedBufferParameteri64(n, n2);
    }
    
    public static ByteBuffer glGetNamedBufferPointer(final int n, final int n2) {
        return GL45.glGetNamedBufferPointer(n, n2);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final ByteBuffer byteBuffer) {
        GL45.glGetNamedBufferSubData(n, n2, byteBuffer);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final DoubleBuffer doubleBuffer) {
        GL45.glGetNamedBufferSubData(n, n2, doubleBuffer);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final FloatBuffer floatBuffer) {
        GL45.glGetNamedBufferSubData(n, n2, floatBuffer);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final IntBuffer intBuffer) {
        GL45.glGetNamedBufferSubData(n, n2, intBuffer);
    }
    
    public static void glGetNamedBufferSubData(final int n, final long n2, final ShortBuffer shortBuffer) {
        GL45.glGetNamedBufferSubData(n, n2, shortBuffer);
    }
    
    public static void glCreateFramebuffers(final IntBuffer intBuffer) {
        GL45.glCreateFramebuffers(intBuffer);
    }
    
    public static int glCreateFramebuffers() {
        return GL45.glCreateFramebuffers();
    }
    
    public static void glNamedFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        GL45.glNamedFramebufferRenderbuffer(n, n2, n3, n4);
    }
    
    public static void glNamedFramebufferParameteri(final int n, final int n2, final int n3) {
        GL45.glNamedFramebufferParameteri(n, n2, n3);
    }
    
    public static void glNamedFramebufferTexture(final int n, final int n2, final int n3, final int n4) {
        GL45.glNamedFramebufferTexture(n, n2, n3, n4);
    }
    
    public static void glNamedFramebufferTextureLayer(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL45.glNamedFramebufferTextureLayer(n, n2, n3, n4, n5);
    }
    
    public static void glNamedFramebufferDrawBuffer(final int n, final int n2) {
        GL45.glNamedFramebufferDrawBuffer(n, n2);
    }
    
    public static void glNamedFramebufferDrawBuffers(final int n, final IntBuffer intBuffer) {
        GL45.glNamedFramebufferDrawBuffers(n, intBuffer);
    }
    
    public static void glNamedFramebufferReadBuffer(final int n, final int n2) {
        GL45.glNamedFramebufferReadBuffer(n, n2);
    }
    
    public static void glInvalidateNamedFramebufferData(final int n, final IntBuffer intBuffer) {
        GL45.glInvalidateNamedFramebufferData(n, intBuffer);
    }
    
    public static void glInvalidateNamedFramebufferSubData(final int n, final IntBuffer intBuffer, final int n2, final int n3, final int n4, final int n5) {
        GL45.glInvalidateNamedFramebufferSubData(n, intBuffer, n2, n3, n4, n5);
    }
    
    public static void glClearNamedFramebuffer(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL45.glClearNamedFramebuffer(n, n2, n3, intBuffer);
    }
    
    public static void glClearNamedFramebufferu(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL45.glClearNamedFramebufferu(n, n2, n3, intBuffer);
    }
    
    public static void glClearNamedFramebuffer(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        GL45.glClearNamedFramebuffer(n, n2, n3, floatBuffer);
    }
    
    public static void glClearNamedFramebufferfi(final int n, final int n2, final float n3, final int n4) {
        GL45.glClearNamedFramebufferfi(n, n2, n3, n4);
    }
    
    public static void glBlitNamedFramebuffer(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12) {
        GL45.glBlitNamedFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);
    }
    
    public static int glCheckNamedFramebufferStatus(final int n, final int n2) {
        return GL45.glCheckNamedFramebufferStatus(n, n2);
    }
    
    public static void glGetNamedFramebufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetNamedFramebufferParameter(n, n2, intBuffer);
    }
    
    public static int glGetNamedFramebufferParameter(final int n, final int n2) {
        return GL45.glGetNamedFramebufferParameter(n, n2);
    }
    
    public static void glGetNamedFramebufferAttachmentParameter(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL45.glGetNamedFramebufferAttachmentParameter(n, n2, n3, intBuffer);
    }
    
    public static int glGetNamedFramebufferAttachmentParameter(final int n, final int n2, final int n3) {
        return GL45.glGetNamedFramebufferAttachmentParameter(n, n2, n3);
    }
    
    public static void glCreateRenderbuffers(final IntBuffer intBuffer) {
        GL45.glCreateRenderbuffers(intBuffer);
    }
    
    public static int glCreateRenderbuffers() {
        return GL45.glCreateRenderbuffers();
    }
    
    public static void glNamedRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        GL45.glNamedRenderbufferStorage(n, n2, n3, n4);
    }
    
    public static void glNamedRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL45.glNamedRenderbufferStorageMultisample(n, n2, n3, n4, n5);
    }
    
    public static void glGetNamedRenderbufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetNamedRenderbufferParameter(n, n2, intBuffer);
    }
    
    public static int glGetNamedRenderbufferParameter(final int n, final int n2) {
        return GL45.glGetNamedRenderbufferParameter(n, n2);
    }
    
    public static void glCreateTextures(final int n, final IntBuffer intBuffer) {
        GL45.glCreateTextures(n, intBuffer);
    }
    
    public static int glCreateTextures(final int n) {
        return GL45.glCreateTextures(n);
    }
    
    public static void glTextureBuffer(final int n, final int n2, final int n3) {
        GL45.glTextureBuffer(n, n2, n3);
    }
    
    public static void glTextureBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        GL45.glTextureBufferRange(n, n2, n3, n4, n5);
    }
    
    public static void glTextureStorage1D(final int n, final int n2, final int n3, final int n4) {
        GL45.glTextureStorage1D(n, n2, n3, n4);
    }
    
    public static void glTextureStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL45.glTextureStorage2D(n, n2, n3, n4, n5);
    }
    
    public static void glTextureStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        GL45.glTextureStorage3D(n, n2, n3, n4, n5, n6);
    }
    
    public static void glTextureStorage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        GL45.glTextureStorage2DMultisample(n, n2, n3, n4, n5, b);
    }
    
    public static void glTextureStorage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        GL45.glTextureStorage3DMultisample(n, n2, n3, n4, n5, n6, b);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        GL45.glTextureSubImage1D(n, n2, n3, n4, n5, n6, byteBuffer);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        GL45.glTextureSubImage1D(n, n2, n3, n4, n5, n6, doubleBuffer);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        GL45.glTextureSubImage1D(n, n2, n3, n4, n5, n6, floatBuffer);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        GL45.glTextureSubImage1D(n, n2, n3, n4, n5, n6, intBuffer);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        GL45.glTextureSubImage1D(n, n2, n3, n4, n5, n6, shortBuffer);
    }
    
    public static void glTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        GL45.glTextureSubImage1D(n, n2, n3, n4, n5, n6, n7);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        GL45.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final DoubleBuffer doubleBuffer) {
        GL45.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, doubleBuffer);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final FloatBuffer floatBuffer) {
        GL45.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, floatBuffer);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final IntBuffer intBuffer) {
        GL45.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ShortBuffer shortBuffer) {
        GL45.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, shortBuffer);
    }
    
    public static void glTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        GL45.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        GL45.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final DoubleBuffer doubleBuffer) {
        GL45.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, doubleBuffer);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final FloatBuffer floatBuffer) {
        GL45.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, floatBuffer);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final IntBuffer intBuffer) {
        GL45.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, intBuffer);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShortBuffer shortBuffer) {
        GL45.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, shortBuffer);
    }
    
    public static void glTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        GL45.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
    }
    
    public static void glCompressedTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        GL45.glCompressedTextureSubImage1D(n, n2, n3, n4, n5, byteBuffer);
    }
    
    public static void glCompressedTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        GL45.glCompressedTextureSubImage1D(n, n2, n3, n4, n5, n6, n7);
    }
    
    public static void glCompressedTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        GL45.glCompressedTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, byteBuffer);
    }
    
    public static void glCompressedTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        GL45.glCompressedTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public static void glCompressedTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        GL45.glCompressedTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
    }
    
    public static void glCompressedTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        GL45.glCompressedTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
    }
    
    public static void glCopyTextureSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        GL45.glCopyTextureSubImage1D(n, n2, n3, n4, n5, n6);
    }
    
    public static void glCopyTextureSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        GL45.glCopyTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    public static void glCopyTextureSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        GL45.glCopyTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public static void glTextureParameterf(final int n, final int n2, final float n3) {
        GL45.glTextureParameterf(n, n2, n3);
    }
    
    public static void glTextureParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL45.glTextureParameter(n, n2, floatBuffer);
    }
    
    public static void glTextureParameteri(final int n, final int n2, final int n3) {
        GL45.glTextureParameteri(n, n2, n3);
    }
    
    public static void glTextureParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glTextureParameterI(n, n2, intBuffer);
    }
    
    public static void glTextureParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glTextureParameterIu(n, n2, intBuffer);
    }
    
    public static void glTextureParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glTextureParameter(n, n2, intBuffer);
    }
    
    public static void glGenerateTextureMipmap(final int n) {
        GL45.glGenerateTextureMipmap(n);
    }
    
    public static void glBindTextureUnit(final int n, final int n2) {
        GL45.glBindTextureUnit(n, n2);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        GL45.glGetTextureImage(n, n2, n3, n4, byteBuffer);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final DoubleBuffer doubleBuffer) {
        GL45.glGetTextureImage(n, n2, n3, n4, doubleBuffer);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        GL45.glGetTextureImage(n, n2, n3, n4, floatBuffer);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        GL45.glGetTextureImage(n, n2, n3, n4, intBuffer);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        GL45.glGetTextureImage(n, n2, n3, n4, shortBuffer);
    }
    
    public static void glGetTextureImage(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        GL45.glGetTextureImage(n, n2, n3, n4, n5, n6);
    }
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final ByteBuffer byteBuffer) {
        GL45.glGetCompressedTextureImage(n, n2, byteBuffer);
    }
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetCompressedTextureImage(n, n2, intBuffer);
    }
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final ShortBuffer shortBuffer) {
        GL45.glGetCompressedTextureImage(n, n2, shortBuffer);
    }
    
    public static void glGetCompressedTextureImage(final int n, final int n2, final int n3, final long n4) {
        GL45.glGetCompressedTextureImage(n, n2, n3, n4);
    }
    
    public static void glGetTextureLevelParameter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        GL45.glGetTextureLevelParameter(n, n2, n3, floatBuffer);
    }
    
    public static float glGetTextureLevelParameterf(final int n, final int n2, final int n3) {
        return GL45.glGetTextureLevelParameterf(n, n2, n3);
    }
    
    public static void glGetTextureLevelParameter(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL45.glGetTextureLevelParameter(n, n2, n3, intBuffer);
    }
    
    public static int glGetTextureLevelParameteri(final int n, final int n2, final int n3) {
        return GL45.glGetTextureLevelParameteri(n, n2, n3);
    }
    
    public static void glGetTextureParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        GL45.glGetTextureParameter(n, n2, floatBuffer);
    }
    
    public static float glGetTextureParameterf(final int n, final int n2) {
        return GL45.glGetTextureParameterf(n, n2);
    }
    
    public static void glGetTextureParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetTextureParameterI(n, n2, intBuffer);
    }
    
    public static int glGetTextureParameterIi(final int n, final int n2) {
        return GL45.glGetTextureParameterIi(n, n2);
    }
    
    public static void glGetTextureParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetTextureParameterIu(n, n2, intBuffer);
    }
    
    public static int glGetTextureParameterIui(final int n, final int n2) {
        return GL45.glGetTextureParameterIui(n, n2);
    }
    
    public static void glGetTextureParameter(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetTextureParameter(n, n2, intBuffer);
    }
    
    public static int glGetTextureParameteri(final int n, final int n2) {
        return GL45.glGetTextureParameteri(n, n2);
    }
    
    public static void glCreateVertexArrays(final IntBuffer intBuffer) {
        GL45.glCreateVertexArrays(intBuffer);
    }
    
    public static int glCreateVertexArrays() {
        return GL45.glCreateVertexArrays();
    }
    
    public static void glDisableVertexArrayAttrib(final int n, final int n2) {
        GL45.glDisableVertexArrayAttrib(n, n2);
    }
    
    public static void glEnableVertexArrayAttrib(final int n, final int n2) {
        GL45.glEnableVertexArrayAttrib(n, n2);
    }
    
    public static void glVertexArrayElementBuffer(final int n, final int n2) {
        GL45.glVertexArrayElementBuffer(n, n2);
    }
    
    public static void glVertexArrayVertexBuffer(final int n, final int n2, final int n3, final long n4, final int n5) {
        GL45.glVertexArrayVertexBuffer(n, n2, n3, n4, n5);
    }
    
    public static void glVertexArrayVertexBuffers(final int n, final int n2, final int n3, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer2) {
        GL45.glVertexArrayVertexBuffers(n, n2, n3, intBuffer, pointerBuffer, intBuffer2);
    }
    
    public static void glVertexArrayAttribFormat(final int n, final int n2, final int n3, final int n4, final boolean b, final int n5) {
        GL45.glVertexArrayAttribFormat(n, n2, n3, n4, b, n5);
    }
    
    public static void glVertexArrayAttribIFormat(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL45.glVertexArrayAttribIFormat(n, n2, n3, n4, n5);
    }
    
    public static void glVertexArrayAttribLFormat(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL45.glVertexArrayAttribLFormat(n, n2, n3, n4, n5);
    }
    
    public static void glVertexArrayAttribBinding(final int n, final int n2, final int n3) {
        GL45.glVertexArrayAttribBinding(n, n2, n3);
    }
    
    public static void glVertexArrayBindingDivisor(final int n, final int n2, final int n3) {
        GL45.glVertexArrayBindingDivisor(n, n2, n3);
    }
    
    public static void glGetVertexArray(final int n, final int n2, final IntBuffer intBuffer) {
        GL45.glGetVertexArray(n, n2, intBuffer);
    }
    
    public static int glGetVertexArray(final int n, final int n2) {
        return GL45.glGetVertexArray(n, n2);
    }
    
    public static void glGetVertexArrayIndexed(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL45.glGetVertexArrayIndexed(n, n2, n3, intBuffer);
    }
    
    public static int glGetVertexArrayIndexed(final int n, final int n2, final int n3) {
        return GL45.glGetVertexArrayIndexed(n, n2, n3);
    }
    
    public static void glGetVertexArrayIndexed64i(final int n, final int n2, final int n3, final LongBuffer longBuffer) {
        GL45.glGetVertexArrayIndexed64i(n, n2, n3, longBuffer);
    }
    
    public static long glGetVertexArrayIndexed64i(final int n, final int n2, final int n3) {
        return GL45.glGetVertexArrayIndexed64i(n, n2, n3);
    }
    
    public static void glCreateSamplers(final IntBuffer intBuffer) {
        GL45.glCreateSamplers(intBuffer);
    }
    
    public static int glCreateSamplers() {
        return GL45.glCreateSamplers();
    }
    
    public static void glCreateProgramPipelines(final IntBuffer intBuffer) {
        GL45.glCreateProgramPipelines(intBuffer);
    }
    
    public static int glCreateProgramPipelines() {
        return GL45.glCreateProgramPipelines();
    }
    
    public static void glCreateQueries(final int n, final IntBuffer intBuffer) {
        GL45.glCreateQueries(n, intBuffer);
    }
    
    public static int glCreateQueries(final int n) {
        return GL45.glCreateQueries(n);
    }
}
