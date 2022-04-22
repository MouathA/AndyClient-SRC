package org.lwjgl.opengl;

import java.awt.*;
import java.nio.*;
import java.util.*;
import org.lwjgl.*;

final class MacOSXDisplay implements DisplayImplementation
{
    private static final int PBUFFER_HANDLE_SIZE = 24;
    private static final int GAMMA_LENGTH = 256;
    private Canvas canvas;
    private Robot robot;
    private MacOSXMouseEventQueue mouse_queue;
    private KeyboardEventQueue keyboard_queue;
    private DisplayMode requested_mode;
    private MacOSXNativeMouse mouse;
    private MacOSXNativeKeyboard keyboard;
    private ByteBuffer window;
    private ByteBuffer context;
    private boolean skipViewportValue;
    private static final IntBuffer current_viewport;
    private boolean mouseInsideWindow;
    private boolean close_requested;
    private boolean native_mode;
    private boolean updateNativeCursor;
    private long currentNativeCursor;
    private boolean enableHighDPI;
    private float scaleFactor;
    
    MacOSXDisplay() {
        this.skipViewportValue = false;
        this.native_mode = true;
        this.updateNativeCursor = false;
        this.currentNativeCursor = 0L;
        this.enableHighDPI = false;
        this.scaleFactor = 1.0f;
    }
    
    private native ByteBuffer nCreateWindow(final int p0, final int p1, final int p2, final int p3, final boolean p4, final boolean p5, final boolean p6, final boolean p7, final boolean p8, final boolean p9, final ByteBuffer p10, final ByteBuffer p11) throws LWJGLException;
    
    private native Object nGetCurrentDisplayMode();
    
    private native void nGetDisplayModes(final Object p0);
    
    private native boolean nIsMiniaturized(final ByteBuffer p0);
    
    private native boolean nIsFocused(final ByteBuffer p0);
    
    private native void nSetResizable(final ByteBuffer p0, final boolean p1);
    
    private native void nResizeWindow(final ByteBuffer p0, final int p1, final int p2, final int p3, final int p4);
    
    private native boolean nWasResized(final ByteBuffer p0);
    
    private native int nGetX(final ByteBuffer p0);
    
    private native int nGetY(final ByteBuffer p0);
    
    private native int nGetWidth(final ByteBuffer p0);
    
    private native int nGetHeight(final ByteBuffer p0);
    
    private native boolean nIsNativeMode(final ByteBuffer p0);
    
    private static boolean isUndecorated() {
        return Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated");
    }
    
    public void createWindow(final DrawableLWJGL drawableLWJGL, final DisplayMode displayMode, final Canvas canvas, final int n, final int n2) throws LWJGLException {
        final boolean fullscreen = Display.isFullscreen();
        final boolean resizable = Display.isResizable();
        final boolean b = canvas != null && !fullscreen;
        final boolean b2 = LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 7) && canvas == null && !Display.getPrivilegedBoolean("org.lwjgl.opengl.Display.disableOSXFullscreenModeAPI");
        this.enableHighDPI = (LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 7) && canvas == null && (Display.getPrivilegedBoolean("org.lwjgl.opengl.Display.enableHighDPI") || fullscreen));
        if (b) {
            this.canvas = canvas;
        }
        else {
            this.canvas = null;
        }
        this.close_requested = false;
        final PeerInfo peer_info = ((DrawableGL)Display.getDrawable()).peer_info;
        final ByteBuffer lockAndGetHandle = peer_info.lockAndGetHandle();
        this.window = this.nCreateWindow(n, n2, displayMode.getWidth(), displayMode.getHeight(), fullscreen, isUndecorated(), resizable, b, b2, this.enableHighDPI, lockAndGetHandle, b ? ((MacOSXCanvasPeerInfo)peer_info).window_handle : this.window);
        if (fullscreen) {
            this.skipViewportValue = true;
            MacOSXDisplay.current_viewport.put(2, displayMode.getWidth());
            MacOSXDisplay.current_viewport.put(3, displayMode.getHeight());
        }
        if (!(this.native_mode = this.nIsNativeMode(lockAndGetHandle))) {
            this.robot = AWTUtil.createRobot(this.canvas);
        }
        peer_info.unlock();
    }
    
    public void doHandleQuit() {
        // monitorenter(this)
        this.close_requested = true;
    }
    // monitorexit(this)
    
    public void mouseInsideWindow(final boolean mouseInsideWindow) {
        // monitorenter(this)
        this.mouseInsideWindow = mouseInsideWindow;
        // monitorexit(this)
        this.updateNativeCursor = true;
    }
    
    public void setScaleFactor(final float scaleFactor) {
        // monitorenter(this)
        this.scaleFactor = scaleFactor;
    }
    // monitorexit(this)
    
    public native void nDestroyCALayer(final ByteBuffer p0);
    
    public native void nDestroyWindow(final ByteBuffer p0);
    
    public void destroyWindow() {
        if (!this.native_mode) {
            final PeerInfo peer_info = ((DrawableGL)Display.getDrawable()).peer_info;
            if (peer_info != null) {
                this.nDestroyCALayer(peer_info.getHandle());
            }
            this.robot = null;
        }
        this.nDestroyWindow(this.window);
    }
    
    public int getGammaRampLength() {
        return 256;
    }
    
    public native void setGammaRamp(final FloatBuffer p0) throws LWJGLException;
    
    public String getAdapter() {
        return null;
    }
    
    public String getVersion() {
        return null;
    }
    
    private static boolean equals(final DisplayMode displayMode, final DisplayMode displayMode2) {
        return displayMode.getWidth() == displayMode2.getWidth() && displayMode.getHeight() == displayMode2.getHeight() && displayMode.getBitsPerPixel() == displayMode2.getBitsPerPixel() && displayMode.getFrequency() == displayMode2.getFrequency();
    }
    
    public void switchDisplayMode(final DisplayMode displayMode) throws LWJGLException {
        final DisplayMode[] availableDisplayModes = this.getAvailableDisplayModes();
        while (0 < availableDisplayModes.length) {
            final DisplayMode requested_mode = availableDisplayModes[0];
            if (equals(requested_mode, displayMode)) {
                this.requested_mode = requested_mode;
                return;
            }
            int n = 0;
            ++n;
        }
        throw new LWJGLException(displayMode + " is not supported");
    }
    
    public void resetDisplayMode() {
        this.requested_mode = null;
        this.restoreGamma();
    }
    
    private native void restoreGamma();
    
    public Object createDisplayMode(final int n, final int n2, final int n3, final int n4) {
        return new DisplayMode(n, n2, n3, n4);
    }
    
    public DisplayMode init() throws LWJGLException {
        return (DisplayMode)this.nGetCurrentDisplayMode();
    }
    
    public void addDisplayMode(final Object o, final int n, final int n2, final int n3, final int n4) {
        ((List)o).add(new DisplayMode(n, n2, n3, n4));
    }
    
    public DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
        final ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
        this.nGetDisplayModes(list);
        list.add(Display.getDesktopDisplayMode());
        return list.toArray(new DisplayMode[list.size()]);
    }
    
    private native void nSetTitle(final ByteBuffer p0, final ByteBuffer p1);
    
    public void setTitle(final String s) {
        this.nSetTitle(this.window, MemoryUtil.encodeUTF8(s));
    }
    
    public boolean isCloseRequested() {
        // monitorenter(this)
        final boolean close_requested = this.close_requested;
        this.close_requested = false;
        // monitorexit(this)
        return close_requested;
    }
    
    public boolean isVisible() {
        return true;
    }
    
    public boolean isActive() {
        if (this.native_mode) {
            return this.nIsFocused(this.window);
        }
        return Display.getParent().hasFocus();
    }
    
    public Canvas getCanvas() {
        return this.canvas;
    }
    
    public boolean isDirty() {
        return false;
    }
    
    public PeerInfo createPeerInfo(final PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        return new MacOSXDisplayPeerInfo(pixelFormat, contextAttribs, true);
    }
    
    public void update() {
        final DrawableGL drawableGL = (DrawableGL)Display.getDrawable();
        if (true) {
            if (this.skipViewportValue) {
                this.skipViewportValue = false;
            }
            else {
                GL11.glGetInteger(2978, MacOSXDisplay.current_viewport);
            }
            drawableGL.context.update();
            GL11.glViewport(MacOSXDisplay.current_viewport.get(0), MacOSXDisplay.current_viewport.get(1), MacOSXDisplay.current_viewport.get(2), MacOSXDisplay.current_viewport.get(3));
        }
        if (this.native_mode && this.updateNativeCursor) {
            this.updateNativeCursor = false;
            this.setNativeCursor(this.currentNativeCursor);
        }
    }
    
    public void reshape(final int n, final int n2, final int n3, final int n4) {
    }
    
    public boolean hasWheel() {
        return AWTUtil.hasWheel();
    }
    
    public int getButtonCount() {
        return 3;
    }
    
    public void createMouse() throws LWJGLException {
        if (this.native_mode) {
            (this.mouse = new MacOSXNativeMouse(this, this.window)).register();
        }
        else {
            (this.mouse_queue = new MacOSXMouseEventQueue(this.canvas)).register();
        }
    }
    
    public void destroyMouse() {
        if (this.native_mode) {
            MacOSXNativeMouse.setCursor(0L);
            this.grabMouse(false);
            if (this.mouse != null) {
                this.mouse.unregister();
            }
            this.mouse = null;
        }
        else {
            if (this.mouse_queue != null) {
                MacOSXMouseEventQueue.nGrabMouse(false);
                this.mouse_queue.unregister();
            }
            this.mouse_queue = null;
        }
    }
    
    public void pollMouse(final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        if (this.native_mode) {
            this.mouse.poll(intBuffer, byteBuffer);
        }
        else {
            this.mouse_queue.poll(intBuffer, byteBuffer);
        }
    }
    
    public void readMouse(final ByteBuffer byteBuffer) {
        if (this.native_mode) {
            this.mouse.copyEvents(byteBuffer);
        }
        else {
            this.mouse_queue.copyEvents(byteBuffer);
        }
    }
    
    public void grabMouse(final boolean b) {
        if (this.native_mode) {
            this.mouse.setGrabbed(b);
        }
        else {
            this.mouse_queue.setGrabbed(b);
        }
    }
    
    public int getNativeCursorCapabilities() {
        if (this.native_mode) {
            return 7;
        }
        return AWTUtil.getNativeCursorCapabilities();
    }
    
    public void setCursorPosition(final int n, final int n2) {
        if (this.native_mode && this.mouse != null) {
            this.mouse.setCursorPosition(n, n2);
        }
    }
    
    public void setNativeCursor(final Object o) throws LWJGLException {
        if (this.native_mode) {
            this.currentNativeCursor = getCursorHandle(o);
            if (Display.isCreated()) {
                if (this.mouseInsideWindow) {
                    MacOSXNativeMouse.setCursor(this.currentNativeCursor);
                }
                else {
                    MacOSXNativeMouse.setCursor(0L);
                }
            }
        }
    }
    
    public int getMinCursorSize() {
        return 1;
    }
    
    public int getMaxCursorSize() {
        final DisplayMode desktopDisplayMode = Display.getDesktopDisplayMode();
        return Math.min(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) / 2;
    }
    
    public void createKeyboard() throws LWJGLException {
        if (this.native_mode) {
            (this.keyboard = new MacOSXNativeKeyboard(this.window)).register();
        }
        else {
            (this.keyboard_queue = new KeyboardEventQueue(this.canvas)).register();
        }
    }
    
    public void destroyKeyboard() {
        if (this.native_mode) {
            if (this.keyboard != null) {
                this.keyboard.unregister();
            }
            this.keyboard = null;
        }
        else {
            if (this.keyboard_queue != null) {
                this.keyboard_queue.unregister();
            }
            this.keyboard_queue = null;
        }
    }
    
    public void pollKeyboard(final ByteBuffer byteBuffer) {
        if (this.native_mode) {
            this.keyboard.poll(byteBuffer);
        }
        else {
            this.keyboard_queue.poll(byteBuffer);
        }
    }
    
    public void readKeyboard(final ByteBuffer byteBuffer) {
        if (this.native_mode) {
            this.keyboard.copyEvents(byteBuffer);
        }
        else {
            this.keyboard_queue.copyEvents(byteBuffer);
        }
    }
    
    public Object createCursor(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        if (this.native_mode) {
            return MacOSXNativeMouse.createCursor(n, n2, n3, n4, n5, intBuffer, intBuffer2);
        }
        return AWTUtil.createCursor(n, n2, n3, n4, n5, intBuffer, intBuffer2);
    }
    
    public void destroyCursor(final Object o) {
        final long cursorHandle = getCursorHandle(o);
        if (this.currentNativeCursor == cursorHandle) {
            this.currentNativeCursor = 0L;
        }
        MacOSXNativeMouse.destroyCursor(cursorHandle);
    }
    
    private static long getCursorHandle(final Object o) {
        return (long)((o != null) ? o : 0L);
    }
    
    public int getPbufferCapabilities() {
        return 1;
    }
    
    public boolean isBufferLost(final PeerInfo peerInfo) {
        return false;
    }
    
    public PeerInfo createPbuffer(final int n, final int n2, final PixelFormat pixelFormat, final ContextAttribs contextAttribs, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        return new MacOSXPbufferPeerInfo(n, n2, pixelFormat, contextAttribs);
    }
    
    public void setPbufferAttrib(final PeerInfo peerInfo, final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    public void bindTexImageToPbuffer(final PeerInfo peerInfo, final int n) {
        throw new UnsupportedOperationException();
    }
    
    public void releaseTexImageFromPbuffer(final PeerInfo peerInfo, final int n) {
        throw new UnsupportedOperationException();
    }
    
    public int setIcon(final ByteBuffer[] array) {
        return 0;
    }
    
    public int getX() {
        return this.nGetX(this.window);
    }
    
    public int getY() {
        return this.nGetY(this.window);
    }
    
    public int getWidth() {
        return this.nGetWidth(this.window);
    }
    
    public int getHeight() {
        return this.nGetHeight(this.window);
    }
    
    public boolean isInsideWindow() {
        return this.mouseInsideWindow;
    }
    
    public void setResizable(final boolean b) {
        this.nSetResizable(this.window, b);
    }
    
    public boolean wasResized() {
        return this.nWasResized(this.window);
    }
    
    public float getPixelScaleFactor() {
        return (this.enableHighDPI && !Display.isFullscreen()) ? this.scaleFactor : 1.0f;
    }
    
    static {
        current_viewport = BufferUtils.createIntBuffer(16);
    }
}
