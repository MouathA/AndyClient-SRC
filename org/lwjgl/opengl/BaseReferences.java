package org.lwjgl.opengl;

import java.nio.*;
import java.util.*;

class BaseReferences
{
    int elementArrayBuffer;
    int arrayBuffer;
    final Buffer[] glVertexAttribPointer_buffer;
    final Buffer[] glTexCoordPointer_buffer;
    int glClientActiveTexture;
    int vertexArrayObject;
    int pixelPackBuffer;
    int pixelUnpackBuffer;
    int indirectBuffer;
    
    BaseReferences(final ContextCapabilities contextCapabilities) {
        if (contextCapabilities.OpenGL20 || contextCapabilities.GL_ARB_vertex_shader) {
            GL11.glGetInteger(34921);
        }
        this.glVertexAttribPointer_buffer = new Buffer[0];
        if (contextCapabilities.OpenGL20) {
            GL11.glGetInteger(34930);
        }
        else if (contextCapabilities.OpenGL13 || contextCapabilities.GL_ARB_multitexture) {
            GL11.glGetInteger(34018);
        }
        this.glTexCoordPointer_buffer = new Buffer[1];
    }
    
    void clear() {
        this.elementArrayBuffer = 0;
        this.arrayBuffer = 0;
        this.glClientActiveTexture = 0;
        Arrays.fill(this.glVertexAttribPointer_buffer, null);
        Arrays.fill(this.glTexCoordPointer_buffer, null);
        this.vertexArrayObject = 0;
        this.pixelPackBuffer = 0;
        this.pixelUnpackBuffer = 0;
        this.indirectBuffer = 0;
    }
    
    void copy(final BaseReferences baseReferences, final int n) {
        if ((n & 0x2) != 0x0) {
            this.elementArrayBuffer = baseReferences.elementArrayBuffer;
            this.arrayBuffer = baseReferences.arrayBuffer;
            this.glClientActiveTexture = baseReferences.glClientActiveTexture;
            System.arraycopy(baseReferences.glVertexAttribPointer_buffer, 0, this.glVertexAttribPointer_buffer, 0, this.glVertexAttribPointer_buffer.length);
            System.arraycopy(baseReferences.glTexCoordPointer_buffer, 0, this.glTexCoordPointer_buffer, 0, this.glTexCoordPointer_buffer.length);
            this.vertexArrayObject = baseReferences.vertexArrayObject;
            this.indirectBuffer = baseReferences.indirectBuffer;
        }
        if ((n & 0x1) != 0x0) {
            this.pixelPackBuffer = baseReferences.pixelPackBuffer;
            this.pixelUnpackBuffer = baseReferences.pixelUnpackBuffer;
        }
    }
}
