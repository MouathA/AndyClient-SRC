package org.lwjgl.opengl;

public final class ARBVertexAttribBinding
{
    public static final int GL_VERTEX_ATTRIB_BINDING = 33492;
    public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 33493;
    public static final int GL_VERTEX_BINDING_DIVISOR = 33494;
    public static final int GL_VERTEX_BINDING_OFFSET = 33495;
    public static final int GL_VERTEX_BINDING_STRIDE = 33496;
    public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 33497;
    public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = 33498;
    
    private ARBVertexAttribBinding() {
    }
    
    public static void glBindVertexBuffer(final int n, final int n2, final long n3, final int n4) {
        GL43.glBindVertexBuffer(n, n2, n3, n4);
    }
    
    public static void glVertexAttribFormat(final int n, final int n2, final int n3, final boolean b, final int n4) {
        GL43.glVertexAttribFormat(n, n2, n3, b, n4);
    }
    
    public static void glVertexAttribIFormat(final int n, final int n2, final int n3, final int n4) {
        GL43.glVertexAttribIFormat(n, n2, n3, n4);
    }
    
    public static void glVertexAttribLFormat(final int n, final int n2, final int n3, final int n4) {
        GL43.glVertexAttribLFormat(n, n2, n3, n4);
    }
    
    public static void glVertexAttribBinding(final int n, final int n2) {
        GL43.glVertexAttribBinding(n, n2);
    }
    
    public static void glVertexBindingDivisor(final int n, final int n2) {
        GL43.glVertexBindingDivisor(n, n2);
    }
}
