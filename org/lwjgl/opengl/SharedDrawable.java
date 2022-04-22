package org.lwjgl.opengl;

import org.lwjgl.*;

public final class SharedDrawable extends DrawableGL
{
    public SharedDrawable(final Drawable drawable) throws LWJGLException {
        this.context = (ContextGL)((DrawableLWJGL)drawable).createSharedContext();
    }
    
    @Override
    public ContextGL createSharedContext() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void setCLSharingProperties(final PointerBuffer clSharingProperties) throws LWJGLException {
        super.setCLSharingProperties(clSharingProperties);
    }
    
    @Override
    public void destroy() {
        super.destroy();
    }
    
    @Override
    public void releaseContext() throws LWJGLException {
        super.releaseContext();
    }
    
    @Override
    public void makeCurrent() throws LWJGLException {
        super.makeCurrent();
    }
    
    @Override
    public boolean isCurrent() throws LWJGLException {
        return super.isCurrent();
    }
    
    @Override
    public void initContext(final float n, final float n2, final float n3) {
        super.initContext(n, n2, n3);
    }
    
    @Override
    public void swapBuffers() throws LWJGLException {
        super.swapBuffers();
    }
    
    @Override
    public void setSwapInterval(final int swapInterval) {
        super.setSwapInterval(swapInterval);
    }
    
    @Override
    public void checkGLError() {
        super.checkGLError();
    }
    
    @Override
    public ContextGL getContext() {
        return super.getContext();
    }
    
    @Override
    public PixelFormatLWJGL getPixelFormat() {
        return super.getPixelFormat();
    }
    
    @Override
    public void setPixelFormat(final PixelFormatLWJGL pixelFormatLWJGL, final ContextAttribs contextAttribs) throws LWJGLException {
        super.setPixelFormat(pixelFormatLWJGL, contextAttribs);
    }
    
    @Override
    public void setPixelFormat(final PixelFormatLWJGL pixelFormat) throws LWJGLException {
        super.setPixelFormat(pixelFormat);
    }
    
    @Override
    public Context createSharedContext() throws LWJGLException {
        return this.createSharedContext();
    }
}
