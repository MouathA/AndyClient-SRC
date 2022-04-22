package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

interface ContextImplementation
{
    ByteBuffer create(final PeerInfo p0, final IntBuffer p1, final ByteBuffer p2) throws LWJGLException;
    
    void swapBuffers() throws LWJGLException;
    
    void releaseDrawable(final ByteBuffer p0) throws LWJGLException;
    
    void releaseCurrentContext() throws LWJGLException;
    
    void update(final ByteBuffer p0);
    
    void makeCurrent(final PeerInfo p0, final ByteBuffer p1) throws LWJGLException;
    
    boolean isCurrent(final ByteBuffer p0) throws LWJGLException;
    
    void setSwapInterval(final int p0);
    
    void destroy(final PeerInfo p0, final ByteBuffer p1) throws LWJGLException;
}
