package org.lwjgl.opengles;

import java.nio.*;
import org.lwjgl.*;

public final class ContextAttribs
{
    private int version;
    
    public ContextAttribs() {
        this(2);
    }
    
    public ContextAttribs(final int version) {
        if (3 < version) {
            throw new IllegalArgumentException("Invalid OpenGL ES version specified: " + version);
        }
        this.version = version;
    }
    
    private ContextAttribs(final ContextAttribs contextAttribs) {
        this.version = contextAttribs.version;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public IntBuffer getAttribList() {
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(3);
        intBuffer.put(12440).put(this.version);
        intBuffer.put(12344);
        intBuffer.rewind();
        return intBuffer;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(32);
        sb.append("ContextAttribs:");
        sb.append(" Version=").append(this.version);
        return sb.toString();
    }
}
