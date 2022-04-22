package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class ARBMultiBind
{
    private ARBMultiBind() {
    }
    
    public static void glBindBuffersBase(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL44.glBindBuffersBase(n, n2, n3, intBuffer);
    }
    
    public static void glBindBuffersRange(final int n, final int n2, final int n3, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        GL44.glBindBuffersRange(n, n2, n3, intBuffer, pointerBuffer, pointerBuffer2);
    }
    
    public static void glBindTextures(final int n, final int n2, final IntBuffer intBuffer) {
        GL44.glBindTextures(n, n2, intBuffer);
    }
    
    public static void glBindSamplers(final int n, final int n2, final IntBuffer intBuffer) {
        GL44.glBindSamplers(n, n2, intBuffer);
    }
    
    public static void glBindImageTextures(final int n, final int n2, final IntBuffer intBuffer) {
        GL44.glBindImageTextures(n, n2, intBuffer);
    }
    
    public static void glBindVertexBuffers(final int n, final int n2, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer2) {
        GL44.glBindVertexBuffers(n, n2, intBuffer, pointerBuffer, intBuffer2);
    }
}
