package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;

public final class Pbuffer extends DrawableGL
{
    public static final int PBUFFER_SUPPORTED = 1;
    public static final int RENDER_TEXTURE_SUPPORTED = 2;
    public static final int RENDER_TEXTURE_RECTANGLE_SUPPORTED = 4;
    public static final int RENDER_DEPTH_TEXTURE_SUPPORTED = 8;
    public static final int MIPMAP_LEVEL = 8315;
    public static final int CUBE_MAP_FACE = 8316;
    public static final int TEXTURE_CUBE_MAP_POSITIVE_X = 8317;
    public static final int TEXTURE_CUBE_MAP_NEGATIVE_X = 8318;
    public static final int TEXTURE_CUBE_MAP_POSITIVE_Y = 8319;
    public static final int TEXTURE_CUBE_MAP_NEGATIVE_Y = 8320;
    public static final int TEXTURE_CUBE_MAP_POSITIVE_Z = 8321;
    public static final int TEXTURE_CUBE_MAP_NEGATIVE_Z = 8322;
    public static final int FRONT_LEFT_BUFFER = 8323;
    public static final int FRONT_RIGHT_BUFFER = 8324;
    public static final int BACK_LEFT_BUFFER = 8325;
    public static final int BACK_RIGHT_BUFFER = 8326;
    public static final int DEPTH_BUFFER = 8359;
    private final int width;
    private final int height;
    
    public Pbuffer(final int n, final int n2, final PixelFormat pixelFormat, final Drawable drawable) throws LWJGLException {
        this(n, n2, pixelFormat, null, drawable);
    }
    
    public Pbuffer(final int n, final int n2, final PixelFormat pixelFormat, final RenderTexture renderTexture, final Drawable drawable) throws LWJGLException {
        this(n, n2, pixelFormat, renderTexture, drawable, null);
    }
    
    public Pbuffer(final int width, final int height, final PixelFormat pixelFormat, final RenderTexture renderTexture, Drawable drawable, final ContextAttribs contextAttribs) throws LWJGLException {
        if (pixelFormat == null) {
            throw new NullPointerException("Pixel format must be non-null");
        }
        this.width = width;
        this.height = height;
        this.peer_info = createPbuffer(width, height, pixelFormat, contextAttribs, renderTexture);
        Context context = null;
        if (drawable == null) {
            drawable = Display.getDrawable();
        }
        if (drawable != null) {
            context = ((DrawableLWJGL)drawable).getContext();
        }
        this.context = new ContextGL(this.peer_info, contextAttribs, (ContextGL)context);
    }
    
    private static PeerInfo createPbuffer(final int n, final int n2, final PixelFormat pixelFormat, final ContextAttribs contextAttribs, final RenderTexture renderTexture) throws LWJGLException {
        if (renderTexture == null) {
            return Display.getImplementation().createPbuffer(n, n2, pixelFormat, contextAttribs, null, BufferUtils.createIntBuffer(1));
        }
        return Display.getImplementation().createPbuffer(n, n2, pixelFormat, contextAttribs, renderTexture.pixelFormatCaps, renderTexture.pBufferAttribs);
    }
    
    public synchronized boolean isBufferLost() {
        this.checkDestroyed();
        return Display.getImplementation().isBufferLost(this.peer_info);
    }
    
    public static int getCapabilities() {
        return Display.getImplementation().getPbufferCapabilities();
    }
    
    public synchronized void setAttrib(final int n, final int n2) {
        this.checkDestroyed();
        Display.getImplementation().setPbufferAttrib(this.peer_info, n, n2);
    }
    
    public synchronized void bindTexImage(final int n) {
        this.checkDestroyed();
        Display.getImplementation().bindTexImageToPbuffer(this.peer_info, n);
    }
    
    public synchronized void releaseTexImage(final int n) {
        this.checkDestroyed();
        Display.getImplementation().releaseTexImageFromPbuffer(this.peer_info, n);
    }
    
    public synchronized int getHeight() {
        this.checkDestroyed();
        return this.height;
    }
    
    public synchronized int getWidth() {
        this.checkDestroyed();
        return this.width;
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
    public ContextGL createSharedContext() throws LWJGLException {
        return super.createSharedContext();
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
}
