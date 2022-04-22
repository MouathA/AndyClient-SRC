package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import java.nio.*;

public class GLAllocation
{
    private static final String __OBFID;
    
    public static synchronized int generateDisplayLists(final int n) {
        final int glGenLists = GL11.glGenLists(n);
        if (glGenLists == 0) {
            final int glGetError = GL11.glGetError();
            String gluErrorString = "No error code reported";
            if (glGetError != 0) {
                gluErrorString = GLU.gluErrorString(glGetError);
            }
            throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + n + ", GL error (" + glGetError + "): " + gluErrorString);
        }
        return glGenLists;
    }
    
    public static synchronized void func_178874_a(final int n, final int n2) {
        GL11.glDeleteLists(n, n2);
    }
    
    public static synchronized void deleteDisplayLists(final int n) {
        GL11.glDeleteLists(n, 1);
    }
    
    public static synchronized ByteBuffer createDirectByteBuffer(final int n) {
        return ByteBuffer.allocateDirect(n).order(ByteOrder.nativeOrder());
    }
    
    public static IntBuffer createDirectIntBuffer(final int n) {
        return createDirectByteBuffer(n << 2).asIntBuffer();
    }
    
    public static FloatBuffer createDirectFloatBuffer(final int n) {
        return createDirectByteBuffer(n << 2).asFloatBuffer();
    }
    
    static {
        __OBFID = "CL_00000630";
    }
}
