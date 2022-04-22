package org.lwjgl.opengl;

import java.awt.*;
import java.awt.event.*;
import org.lwjgl.opengles.*;
import java.security.*;
import org.lwjgl.*;
import java.nio.*;
import java.io.*;
import java.util.*;

final class LinuxDisplay implements DisplayImplementation
{
    public static final int CurrentTime = 0;
    public static final int GrabSuccess = 0;
    public static final int AutoRepeatModeOff = 0;
    public static final int AutoRepeatModeOn = 1;
    public static final int AutoRepeatModeDefault = 2;
    public static final int None = 0;
    private static final int KeyPressMask = 1;
    private static final int KeyReleaseMask = 2;
    private static final int ButtonPressMask = 4;
    private static final int ButtonReleaseMask = 8;
    private static final int NotifyAncestor = 0;
    private static final int NotifyNonlinear = 3;
    private static final int NotifyPointer = 5;
    private static final int NotifyPointerRoot = 6;
    private static final int NotifyDetailNone = 7;
    private static final int SetModeInsert = 0;
    private static final int SaveSetRoot = 1;
    private static final int SaveSetUnmap = 1;
    private static final int X_SetInputFocus = 42;
    private static final int FULLSCREEN_LEGACY = 1;
    private static final int FULLSCREEN_NETWM = 2;
    private static final int WINDOWED = 3;
    private static final int XRANDR = 10;
    private static final int XF86VIDMODE = 11;
    private static final int NONE = 12;
    private static long display;
    private static long current_window;
    private static long saved_error_handler;
    private static int display_connection_usage_count;
    private final LinuxEvent event_buffer;
    private final LinuxEvent tmp_event_buffer;
    private int current_displaymode_extension;
    private long delete_atom;
    private PeerInfo peer_info;
    private ByteBuffer saved_gamma;
    private ByteBuffer current_gamma;
    private DisplayMode saved_mode;
    private DisplayMode current_mode;
    private boolean keyboard_grabbed;
    private boolean pointer_grabbed;
    private boolean input_released;
    private boolean grab;
    private boolean focused;
    private boolean minimized;
    private boolean dirty;
    private boolean close_requested;
    private long current_cursor;
    private long blank_cursor;
    private boolean mouseInside;
    private boolean resizable;
    private boolean resized;
    private int window_x;
    private int window_y;
    private int window_width;
    private int window_height;
    private Canvas parent;
    private long parent_window;
    private static boolean xembedded;
    private long parent_proxy_focus_window;
    private boolean parent_focused;
    private boolean parent_focus_changed;
    private long last_window_focus;
    private LinuxKeyboard keyboard;
    private LinuxMouse mouse;
    private String wm_class;
    private final FocusListener focus_listener;
    
    LinuxDisplay() {
        this.event_buffer = new LinuxEvent();
        this.tmp_event_buffer = new LinuxEvent();
        this.current_displaymode_extension = 12;
        this.mouseInside = true;
        this.last_window_focus = 0L;
        this.focus_listener = new FocusListener() {
            final LinuxDisplay this$0;
            
            public void focusGained(final FocusEvent focusEvent) {
                // monitorenter(lock = GlobalLock.lock)
                LinuxDisplay.access$002(this.this$0, true);
                LinuxDisplay.access$102(this.this$0, true);
            }
            // monitorexit(lock)
            
            public void focusLost(final FocusEvent focusEvent) {
                // monitorenter(lock = GlobalLock.lock)
                LinuxDisplay.access$002(this.this$0, false);
                LinuxDisplay.access$102(this.this$0, true);
            }
            // monitorexit(lock)
        };
    }
    
    private static ByteBuffer getCurrentGammaRamp() throws LWJGLException {
        if (isXF86VidModeSupported()) {
            return nGetCurrentGammaRamp(getDisplay(), getDefaultScreen());
        }
        return null;
    }
    
    private static native ByteBuffer nGetCurrentGammaRamp(final long p0, final int p1) throws LWJGLException;
    
    private static int getBestDisplayModeExtension() {
        if (isXrandrSupported()) {
            LWJGLUtil.log("Using Xrandr for display mode switching");
        }
        else if (isXF86VidModeSupported()) {
            LWJGLUtil.log("Using XF86VidMode for display mode switching");
        }
        else {
            LWJGLUtil.log("No display mode extensions available");
        }
        return 12;
    }
    
    private static boolean isXrandrSupported() {
        return !Display.getPrivilegedBoolean("LWJGL_DISABLE_XRANDR") && nIsXrandrSupported(getDisplay());
    }
    
    private static native boolean nIsXrandrSupported(final long p0) throws LWJGLException;
    
    private static boolean isXF86VidModeSupported() {
        return nIsXF86VidModeSupported(getDisplay());
    }
    
    private static native boolean nIsXF86VidModeSupported(final long p0) throws LWJGLException;
    
    private static boolean isNetWMFullscreenSupported() throws LWJGLException {
        return !Display.getPrivilegedBoolean("LWJGL_DISABLE_NETWM") && nIsNetWMFullscreenSupported(getDisplay(), getDefaultScreen());
    }
    
    private static native boolean nIsNetWMFullscreenSupported(final long p0, final int p1) throws LWJGLException;
    
    static void lockAWT() {
    }
    
    private static native void nLockAWT() throws LWJGLException;
    
    static void unlockAWT() {
    }
    
    private static native void nUnlockAWT() throws LWJGLException;
    
    static void incDisplay() throws LWJGLException {
        if (LinuxDisplay.display_connection_usage_count == 0) {
            GLContext.loadOpenGLLibrary();
            LinuxDisplay.saved_error_handler = setErrorHandler();
            LinuxDisplay.display = openDisplay();
        }
        ++LinuxDisplay.display_connection_usage_count;
    }
    
    private static native int callErrorHandler(final long p0, final long p1, final long p2);
    
    private static native long setErrorHandler();
    
    private static native long resetErrorHandler(final long p0);
    
    private static native void synchronize(final long p0, final boolean p1);
    
    private static int globalErrorHandler(final long n, final long n2, final long n3, final long n4, final long n5, final long n6, final long n7) throws LWJGLException {
        if (LinuxDisplay.xembedded && n6 == 42L) {
            return 0;
        }
        if (n == getDisplay()) {
            throw new LWJGLException("X Error - disp: 0x" + Long.toHexString(n3) + " serial: " + n4 + " error: " + getErrorText(n, n5) + " request_code: " + n6 + " minor_code: " + n7);
        }
        if (false) {
            return callErrorHandler(LinuxDisplay.saved_error_handler, n, n2);
        }
        return 0;
    }
    
    private static native String getErrorText(final long p0, final long p1);
    
    static void decDisplay() {
    }
    
    static native long openDisplay() throws LWJGLException;
    
    static native void closeDisplay(final long p0);
    
    private int getWindowMode(final boolean b) throws LWJGLException {
        if (!b) {
            return 3;
        }
        if (this.current_displaymode_extension == 10 && isNetWMFullscreenSupported()) {
            LWJGLUtil.log("Using NetWM for fullscreen window");
            return 2;
        }
        LWJGLUtil.log("Using legacy mode for fullscreen window");
        return 1;
    }
    
    static long getDisplay() {
        if (LinuxDisplay.display_connection_usage_count <= 0) {
            throw new InternalError("display_connection_usage_count = " + LinuxDisplay.display_connection_usage_count);
        }
        return LinuxDisplay.display;
    }
    
    static int getDefaultScreen() {
        return nGetDefaultScreen(getDisplay());
    }
    
    static native int nGetDefaultScreen(final long p0);
    
    static long getWindow() {
        return LinuxDisplay.current_window;
    }
    
    private void ungrabKeyboard() {
        if (this.keyboard_grabbed) {
            nUngrabKeyboard(getDisplay());
            this.keyboard_grabbed = false;
        }
    }
    
    static native int nUngrabKeyboard(final long p0);
    
    private void grabKeyboard() {
        if (!this.keyboard_grabbed && nGrabKeyboard(getDisplay(), getWindow()) == 0) {
            this.keyboard_grabbed = true;
        }
    }
    
    static native int nGrabKeyboard(final long p0, final long p1);
    
    private void grabPointer() {
        if (!this.pointer_grabbed && nGrabPointer(getDisplay(), getWindow(), 0L) == 0) {
            this.pointer_grabbed = true;
            if (isLegacyFullscreen()) {
                nSetViewPort(getDisplay(), getWindow(), getDefaultScreen());
            }
        }
    }
    
    static native int nGrabPointer(final long p0, final long p1, final long p2);
    
    private static native void nSetViewPort(final long p0, final long p1, final int p2);
    
    private void ungrabPointer() {
        if (this.pointer_grabbed) {
            this.pointer_grabbed = false;
            nUngrabPointer(getDisplay());
        }
    }
    
    static native int nUngrabPointer(final long p0);
    
    private static boolean isFullscreen() {
        return 3 == 1 || 3 == 2;
    }
    
    private boolean shouldGrab() {
        return !this.input_released && this.grab && this.mouse != null;
    }
    
    private void updatePointerGrab() {
        if (isFullscreen() || this.shouldGrab()) {
            this.grabPointer();
        }
        else {
            this.ungrabPointer();
        }
        this.updateCursor();
    }
    
    private void updateCursor() {
        long n;
        if (this.shouldGrab()) {
            n = this.blank_cursor;
        }
        else {
            n = this.current_cursor;
        }
        nDefineCursor(getDisplay(), getWindow(), n);
    }
    
    private static native void nDefineCursor(final long p0, final long p1, final long p2);
    
    private static boolean isLegacyFullscreen() {
        return 3 == 1;
    }
    
    private void updateKeyboardGrab() {
        if (isLegacyFullscreen()) {
            this.grabKeyboard();
        }
        else {
            this.ungrabKeyboard();
        }
    }
    
    public void createWindow(final DrawableLWJGL drawableLWJGL, final DisplayMode displayMode, final Canvas parent, int xPos, int yPos) throws LWJGLException {
        if (drawableLWJGL instanceof DrawableGLES) {
            this.peer_info = new LinuxDisplayPeerInfo();
        }
        final ByteBuffer lockAndGetHandle = this.peer_info.lockAndGetHandle();
        LinuxDisplay.current_window_mode = this.getWindowMode(Display.isFullscreen());
        if (3 != 3) {
            Compiz.setLegacyFullscreenSupport(true);
        }
        final boolean b = Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated") || (3 != 3 && Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated_fs"));
        this.parent = parent;
        this.parent_window = ((parent != null) ? getHandle(parent) : getRootWindow(getDisplay(), getDefaultScreen()));
        this.resizable = Display.isResizable();
        this.resized = false;
        this.window_x = xPos;
        this.window_y = yPos;
        this.window_width = displayMode.getWidth();
        this.window_height = displayMode.getHeight();
        if (displayMode.isFullscreenCapable() && this.current_displaymode_extension == 10) {
            final XRandR.Screen displayModetoScreen = XRandR.DisplayModetoScreen(Display.getDisplayMode());
            xPos = displayModetoScreen.xPos;
            yPos = displayModetoScreen.yPos;
        }
        LinuxDisplay.current_window = nCreateWindow(getDisplay(), getDefaultScreen(), lockAndGetHandle, displayMode, 3, xPos, yPos, b, this.parent_window, this.resizable);
        this.wm_class = Display.getPrivilegedString("LWJGL_WM_CLASS");
        if (this.wm_class == null) {
            this.wm_class = Display.getTitle();
        }
        this.setClassHint(Display.getTitle(), this.wm_class);
        mapRaised(getDisplay(), LinuxDisplay.current_window);
        LinuxDisplay.xembedded = (parent != null && isAncestorXEmbedded(this.parent_window));
        this.blank_cursor = createBlankCursor();
        this.current_cursor = 0L;
        this.focused = false;
        this.input_released = false;
        this.pointer_grabbed = false;
        this.keyboard_grabbed = false;
        this.close_requested = false;
        this.grab = false;
        this.minimized = false;
        this.dirty = true;
        if (drawableLWJGL instanceof DrawableGLES) {
            ((DrawableGLES)drawableLWJGL).initialize(LinuxDisplay.current_window, getDisplay(), 4, (PixelFormat)drawableLWJGL.getPixelFormat());
        }
        if (parent != null) {
            parent.addFocusListener(this.focus_listener);
            this.parent_focused = parent.isFocusOwner();
            this.parent_focus_changed = true;
        }
        this.peer_info.unlock();
    }
    
    private static native long nCreateWindow(final long p0, final int p1, final ByteBuffer p2, final DisplayMode p3, final int p4, final int p5, final int p6, final boolean p7, final long p8, final boolean p9) throws LWJGLException;
    
    private static native long getRootWindow(final long p0, final int p1);
    
    private static native boolean hasProperty(final long p0, final long p1, final long p2);
    
    private static native long getParentWindow(final long p0, final long p1) throws LWJGLException;
    
    private static native int getChildCount(final long p0, final long p1) throws LWJGLException;
    
    private static native void mapRaised(final long p0, final long p1);
    
    private static native void reparentWindow(final long p0, final long p1, final long p2, final int p3, final int p4);
    
    private static native long nGetInputFocus(final long p0) throws LWJGLException;
    
    private static native void nSetInputFocus(final long p0, final long p1, final long p2);
    
    private static native void nSetWindowSize(final long p0, final long p1, final int p2, final int p3, final boolean p4);
    
    private static native int nGetX(final long p0, final long p1);
    
    private static native int nGetY(final long p0, final long p1);
    
    private static native int nGetWidth(final long p0, final long p1);
    
    private static native int nGetHeight(final long p0, final long p1);
    
    private static boolean isAncestorXEmbedded(final long n) throws LWJGLException {
        final long internAtom = internAtom("_XEMBED_INFO", true);
        if (internAtom != 0L) {
            for (long parentWindow = n; parentWindow != 0L; parentWindow = getParentWindow(getDisplay(), parentWindow)) {
                if (hasProperty(getDisplay(), parentWindow, internAtom)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static long getHandle(final Canvas canvas) throws LWJGLException {
        final LinuxPeerInfo linuxPeerInfo = (LinuxPeerInfo)AWTGLCanvas.createImplementation().createPeerInfo(canvas, null, null);
        linuxPeerInfo.lockAndGetHandle();
        final long drawable = linuxPeerInfo.getDrawable();
        linuxPeerInfo.unlock();
        return drawable;
    }
    
    private void updateInputGrab() {
        this.updatePointerGrab();
        this.updateKeyboardGrab();
    }
    
    public void destroyWindow() {
        if (this.parent != null) {
            this.parent.removeFocusListener(this.focus_listener);
        }
        this.setNativeCursor(null);
        nDestroyCursor(getDisplay(), this.blank_cursor);
        this.blank_cursor = 0L;
        this.ungrabKeyboard();
        nDestroyWindow(getDisplay(), getWindow());
        if (3 != 3) {
            Compiz.setLegacyFullscreenSupport(false);
        }
    }
    
    static native void nDestroyWindow(final long p0, final long p1);
    
    public void switchDisplayMode(final DisplayMode current_mode) throws LWJGLException {
        this.switchDisplayModeOnTmpDisplay(current_mode);
        this.current_mode = current_mode;
    }
    
    private void switchDisplayModeOnTmpDisplay(final DisplayMode displayMode) throws LWJGLException {
        if (this.current_displaymode_extension == 10) {
            XRandR.setConfiguration(false, XRandR.DisplayModetoScreen(displayMode));
        }
        else {
            nSwitchDisplayMode(getDisplay(), getDefaultScreen(), this.current_displaymode_extension, displayMode);
        }
    }
    
    private static native void nSwitchDisplayMode(final long p0, final int p1, final int p2, final DisplayMode p3) throws LWJGLException;
    
    private static long internAtom(final String s, final boolean b) throws LWJGLException {
        return nInternAtom(getDisplay(), s, b);
    }
    
    static native long nInternAtom(final long p0, final String p1, final boolean p2);
    
    public void resetDisplayMode() {
        if (this.current_displaymode_extension == 10) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                final LinuxDisplay this$0;
                
                public Object run() {
                    return null;
                }
            });
        }
        else {
            this.switchDisplayMode(this.saved_mode);
        }
        if (isXF86VidModeSupported()) {
            this.doSetGamma(this.saved_gamma);
        }
        Compiz.setLegacyFullscreenSupport(false);
    }
    
    public int getGammaRampLength() {
        if (!isXF86VidModeSupported()) {
            return 0;
        }
        return nGetGammaRampLength(getDisplay(), getDefaultScreen());
    }
    
    private static native int nGetGammaRampLength(final long p0, final int p1) throws LWJGLException;
    
    public void setGammaRamp(final FloatBuffer floatBuffer) throws LWJGLException {
        if (!isXF86VidModeSupported()) {
            throw new LWJGLException("No gamma ramp support (Missing XF86VM extension)");
        }
        this.doSetGamma(convertToNativeRamp(floatBuffer));
    }
    
    private void doSetGamma(final ByteBuffer byteBuffer) throws LWJGLException {
        setGammaRampOnTmpDisplay(byteBuffer);
        this.current_gamma = byteBuffer;
    }
    
    private static void setGammaRampOnTmpDisplay(final ByteBuffer byteBuffer) throws LWJGLException {
        nSetGammaRamp(getDisplay(), getDefaultScreen(), byteBuffer);
    }
    
    private static native void nSetGammaRamp(final long p0, final int p1, final ByteBuffer p2) throws LWJGLException;
    
    private static ByteBuffer convertToNativeRamp(final FloatBuffer floatBuffer) throws LWJGLException {
        return nConvertToNativeRamp(floatBuffer, floatBuffer.position(), floatBuffer.remaining());
    }
    
    private static native ByteBuffer nConvertToNativeRamp(final FloatBuffer p0, final int p1, final int p2) throws LWJGLException;
    
    public String getAdapter() {
        return null;
    }
    
    public String getVersion() {
        return null;
    }
    
    public DisplayMode init() throws LWJGLException {
        this.delete_atom = internAtom("WM_DELETE_WINDOW", false);
        this.current_displaymode_extension = getBestDisplayModeExtension();
        if (this.current_displaymode_extension == 12) {
            throw new LWJGLException("No display mode extension is available");
        }
        final DisplayMode[] availableDisplayModes = this.getAvailableDisplayModes();
        if (availableDisplayModes == null || availableDisplayModes.length == 0) {
            throw new LWJGLException("No modes available");
        }
        switch (this.current_displaymode_extension) {
            case 10: {
                this.saved_mode = AccessController.doPrivileged((PrivilegedAction<DisplayMode>)new PrivilegedAction() {
                    final LinuxDisplay this$0;
                    
                    public DisplayMode run() {
                        return XRandR.ScreentoDisplayMode(XRandR.getConfiguration());
                    }
                    
                    public Object run() {
                        return this.run();
                    }
                });
                break;
            }
            case 11: {
                this.saved_mode = availableDisplayModes[0];
                break;
            }
            default: {
                throw new LWJGLException("Unknown display mode extension: " + this.current_displaymode_extension);
            }
        }
        this.current_mode = this.saved_mode;
        this.saved_gamma = getCurrentGammaRamp();
        this.current_gamma = this.saved_gamma;
        return this.saved_mode;
    }
    
    private static DisplayMode getCurrentXRandrMode() throws LWJGLException {
        return nGetCurrentXRandrMode(getDisplay(), getDefaultScreen());
    }
    
    private static native DisplayMode nGetCurrentXRandrMode(final long p0, final int p1) throws LWJGLException;
    
    public void setTitle(final String s) {
        final ByteBuffer encodeUTF8 = MemoryUtil.encodeUTF8(s);
        nSetTitle(getDisplay(), getWindow(), MemoryUtil.getAddress(encodeUTF8), encodeUTF8.remaining() - 1);
    }
    
    private static native void nSetTitle(final long p0, final long p1, final long p2, final int p3);
    
    private void setClassHint(final String s, final String s2) {
        nSetClassHint(getDisplay(), getWindow(), MemoryUtil.getAddress(MemoryUtil.encodeUTF8(s)), MemoryUtil.getAddress(MemoryUtil.encodeUTF8(s2)));
    }
    
    private static native void nSetClassHint(final long p0, final long p1, final long p2, final long p3);
    
    public boolean isCloseRequested() {
        final boolean close_requested = this.close_requested;
        this.close_requested = false;
        return close_requested;
    }
    
    public boolean isVisible() {
        return !this.minimized;
    }
    
    public boolean isActive() {
        return this.focused || isLegacyFullscreen();
    }
    
    public boolean isDirty() {
        final boolean dirty = this.dirty;
        this.dirty = false;
        return dirty;
    }
    
    public PeerInfo createPeerInfo(final org.lwjgl.opengl.PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        return this.peer_info = new LinuxDisplayPeerInfo(pixelFormat);
    }
    
    private void relayEventToParent(final LinuxEvent linuxEvent, final int n) {
        this.tmp_event_buffer.copyFrom(linuxEvent);
        this.tmp_event_buffer.setWindow(this.parent_window);
        this.tmp_event_buffer.sendEvent(getDisplay(), this.parent_window, true, n);
    }
    
    private void relayEventToParent(final LinuxEvent linuxEvent) {
        if (this.parent == null) {
            return;
        }
        switch (linuxEvent.getType()) {
            case 2: {
                this.relayEventToParent(linuxEvent, 1);
                break;
            }
            case 3: {
                this.relayEventToParent(linuxEvent, 1);
                break;
            }
            case 4: {
                if (LinuxDisplay.xembedded || !this.focused) {
                    this.relayEventToParent(linuxEvent, 1);
                    break;
                }
                break;
            }
            case 5: {
                if (LinuxDisplay.xembedded || !this.focused) {
                    this.relayEventToParent(linuxEvent, 1);
                    break;
                }
                break;
            }
        }
    }
    
    private void processEvents() {
        while (LinuxEvent.getPending(getDisplay()) > 0) {
            this.event_buffer.nextEvent(getDisplay());
            final long window = this.event_buffer.getWindow();
            this.relayEventToParent(this.event_buffer);
            if (window == getWindow() && !this.event_buffer.filterEvent(window) && (this.mouse == null || !this.mouse.filterEvent(this.grab, this.shouldWarpPointer(), this.event_buffer))) {
                if (this.keyboard != null && this.keyboard.filterEvent(this.event_buffer)) {
                    continue;
                }
                switch (this.event_buffer.getType()) {
                    case 9: {
                        this.setFocused(true, this.event_buffer.getFocusDetail());
                        continue;
                    }
                    case 10: {
                        this.setFocused(false, this.event_buffer.getFocusDetail());
                        continue;
                    }
                    case 33: {
                        if (this.event_buffer.getClientFormat() == 32 && this.event_buffer.getClientData(0) == this.delete_atom) {
                            this.close_requested = true;
                            continue;
                        }
                        continue;
                    }
                    case 19: {
                        this.dirty = true;
                        this.minimized = false;
                        continue;
                    }
                    case 18: {
                        this.dirty = true;
                        this.minimized = true;
                        continue;
                    }
                    case 12: {
                        this.dirty = true;
                        continue;
                    }
                    case 22: {
                        final int nGetX = nGetX(getDisplay(), getWindow());
                        final int nGetY = nGetY(getDisplay(), getWindow());
                        final int nGetWidth = nGetWidth(getDisplay(), getWindow());
                        final int nGetHeight = nGetHeight(getDisplay(), getWindow());
                        this.window_x = nGetX;
                        this.window_y = nGetY;
                        if (this.window_width != nGetWidth || this.window_height != nGetHeight) {
                            this.resized = true;
                            this.window_width = nGetWidth;
                            this.window_height = nGetHeight;
                            continue;
                        }
                        continue;
                    }
                    case 7: {
                        this.mouseInside = true;
                        continue;
                    }
                    case 8: {
                        this.mouseInside = false;
                        continue;
                    }
                }
            }
        }
    }
    
    public void update() {
        this.processEvents();
        this.checkInput();
    }
    
    public void reshape(final int n, final int n2, final int n3, final int n4) {
        nReshape(getDisplay(), getWindow(), n, n2, n3, n4);
    }
    
    private static native void nReshape(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5);
    
    public DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
        if (this.current_displaymode_extension == 10) {
            final DisplayMode[] nGetAvailableDisplayModes = nGetAvailableDisplayModes(getDisplay(), getDefaultScreen(), this.current_displaymode_extension);
            if (nGetAvailableDisplayModes.length > 0) {
                nGetAvailableDisplayModes[0].getBitsPerPixel();
            }
            final XRandR.Screen[] resolutions = XRandR.getResolutions(XRandR.getScreenNames()[0]);
            final DisplayMode[] array = new DisplayMode[resolutions.length];
            while (0 < array.length) {
                array[0] = new DisplayMode(resolutions[0].width, resolutions[0].height, 24, resolutions[0].freq);
                int n = 0;
                ++n;
            }
            return array;
        }
        return nGetAvailableDisplayModes(getDisplay(), getDefaultScreen(), this.current_displaymode_extension);
    }
    
    private static native DisplayMode[] nGetAvailableDisplayModes(final long p0, final int p1, final int p2) throws LWJGLException;
    
    public boolean hasWheel() {
        return true;
    }
    
    public int getButtonCount() {
        return this.mouse.getButtonCount();
    }
    
    public void createMouse() throws LWJGLException {
        this.mouse = new LinuxMouse(getDisplay(), getWindow(), getWindow());
    }
    
    public void destroyMouse() {
        this.mouse = null;
        this.updateInputGrab();
    }
    
    public void pollMouse(final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.mouse.poll(this.grab, intBuffer, byteBuffer);
    }
    
    public void readMouse(final ByteBuffer byteBuffer) {
        this.mouse.read(byteBuffer);
    }
    
    public void setCursorPosition(final int n, final int n2) {
        this.mouse.setCursorPosition(n, n2);
    }
    
    private void checkInput() {
        if (this.parent == null) {
            return;
        }
        if (LinuxDisplay.xembedded) {
            final long last_window_focus = 0L;
            if (this.last_window_focus != last_window_focus || this.parent_focused != this.focused) {
                if (this.isParentWindowActive(last_window_focus)) {
                    if (this.parent_focused) {
                        nSetInputFocus(getDisplay(), LinuxDisplay.current_window, 0L);
                        this.last_window_focus = LinuxDisplay.current_window;
                        this.focused = true;
                    }
                    else {
                        nSetInputFocus(getDisplay(), this.parent_proxy_focus_window, 0L);
                        this.last_window_focus = this.parent_proxy_focus_window;
                        this.focused = false;
                    }
                }
                else {
                    this.last_window_focus = last_window_focus;
                    this.focused = false;
                }
            }
        }
        else if (this.parent_focus_changed && this.parent_focused) {
            this.setInputFocusUnsafe(getWindow());
            this.parent_focus_changed = false;
        }
    }
    
    private void setInputFocusUnsafe(final long n) {
        nSetInputFocus(getDisplay(), n, 0L);
        nSync(getDisplay(), false);
    }
    
    private static native void nSync(final long p0, final boolean p1) throws LWJGLException;
    
    private boolean isParentWindowActive(final long parent_proxy_focus_window) {
        if (parent_proxy_focus_window == LinuxDisplay.current_window) {
            return true;
        }
        if (getChildCount(getDisplay(), parent_proxy_focus_window) != 0) {
            return false;
        }
        final long parentWindow = getParentWindow(getDisplay(), parent_proxy_focus_window);
        if (parentWindow == 0L) {
            return false;
        }
        long n = LinuxDisplay.current_window;
        while (n != 0L) {
            n = getParentWindow(getDisplay(), n);
            if (n == parentWindow) {
                this.parent_proxy_focus_window = parent_proxy_focus_window;
                return true;
            }
        }
        return false;
    }
    
    private void setFocused(final boolean focused, final int n) {
        if (this.focused == focused || n == 7 || n == 5 || n == 6 || LinuxDisplay.xembedded) {
            return;
        }
        this.focused = focused;
        if (this.focused) {
            this.acquireInput();
        }
        else {
            this.releaseInput();
        }
    }
    
    private void releaseInput() {
        if (isLegacyFullscreen() || this.input_released) {
            return;
        }
        if (this.keyboard != null) {
            this.keyboard.releaseAll();
        }
        this.input_released = true;
        this.updateInputGrab();
        if (3 == 2) {
            nIconifyWindow(getDisplay(), getWindow(), getDefaultScreen());
            if (this.current_displaymode_extension == 10) {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                    final LinuxDisplay this$0;
                    
                    public Object run() {
                        return null;
                    }
                });
            }
            else {
                this.switchDisplayModeOnTmpDisplay(this.saved_mode);
            }
            setGammaRampOnTmpDisplay(this.saved_gamma);
        }
    }
    
    private static native void nIconifyWindow(final long p0, final long p1, final int p2);
    
    private void acquireInput() {
        if (isLegacyFullscreen() || !this.input_released) {
            return;
        }
        this.input_released = false;
        this.updateInputGrab();
        if (3 == 2) {
            this.switchDisplayModeOnTmpDisplay(this.current_mode);
            setGammaRampOnTmpDisplay(this.current_gamma);
        }
    }
    
    public void grabMouse(final boolean grab) {
        if (grab != this.grab) {
            this.grab = grab;
            this.updateInputGrab();
            this.mouse.changeGrabbed(this.grab, this.shouldWarpPointer());
        }
    }
    
    private boolean shouldWarpPointer() {
        return this.pointer_grabbed && this.shouldGrab();
    }
    
    public int getNativeCursorCapabilities() {
        return nGetNativeCursorCapabilities(getDisplay());
    }
    
    private static native int nGetNativeCursorCapabilities(final long p0) throws LWJGLException;
    
    public void setNativeCursor(final Object o) throws LWJGLException {
        this.current_cursor = getCursorHandle(o);
        this.updateCursor();
    }
    
    public int getMinCursorSize() {
        return nGetMinCursorSize(getDisplay(), getWindow());
    }
    
    private static native int nGetMinCursorSize(final long p0, final long p1);
    
    public int getMaxCursorSize() {
        return nGetMaxCursorSize(getDisplay(), getWindow());
    }
    
    private static native int nGetMaxCursorSize(final long p0, final long p1);
    
    public void createKeyboard() throws LWJGLException {
        this.keyboard = new LinuxKeyboard(getDisplay(), getWindow());
    }
    
    public void destroyKeyboard() {
        this.keyboard.destroy(getDisplay());
        this.keyboard = null;
    }
    
    public void pollKeyboard(final ByteBuffer byteBuffer) {
        this.keyboard.poll(byteBuffer);
    }
    
    public void readKeyboard(final ByteBuffer byteBuffer) {
        this.keyboard.read(byteBuffer);
    }
    
    private static native long nCreateCursor(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final IntBuffer p6, final int p7, final IntBuffer p8, final int p9) throws LWJGLException;
    
    private static long createBlankCursor() {
        return nCreateBlankCursor(getDisplay(), getWindow());
    }
    
    static native long nCreateBlankCursor(final long p0, final long p1);
    
    public Object createCursor(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        return nCreateCursor(getDisplay(), n, n2, n3, n4, n5, intBuffer, intBuffer.position(), intBuffer2, (intBuffer2 != null) ? intBuffer2.position() : -1);
    }
    
    private static long getCursorHandle(final Object o) {
        return (long)((o != null) ? o : 0L);
    }
    
    public void destroyCursor(final Object o) {
        nDestroyCursor(getDisplay(), getCursorHandle(o));
    }
    
    static native void nDestroyCursor(final long p0, final long p1);
    
    public int getPbufferCapabilities() {
        return nGetPbufferCapabilities(getDisplay(), getDefaultScreen());
    }
    
    private static native int nGetPbufferCapabilities(final long p0, final int p1);
    
    public boolean isBufferLost(final PeerInfo peerInfo) {
        return false;
    }
    
    public PeerInfo createPbuffer(final int n, final int n2, final org.lwjgl.opengl.PixelFormat pixelFormat, final ContextAttribs contextAttribs, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        return new LinuxPbufferPeerInfo(n, n2, pixelFormat);
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
    
    private static ByteBuffer convertIcons(final ByteBuffer[] p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore_2       
        //     2: aload_2        
        //     3: arraylength    
        //     4: istore_3       
        //     5: iconst_0       
        //     6: iload_3        
        //     7: if_icmpge       57
        //    10: aload_2        
        //    11: iconst_0       
        //    12: aaload         
        //    13: astore          5
        //    15: aload           5
        //    17: invokevirtual   java/nio/ByteBuffer.limit:()I
        //    20: iconst_4       
        //    21: idiv           
        //    22: istore          6
        //    24: iload           6
        //    26: i2d            
        //    27: invokestatic    java/lang/Math.sqrt:(D)D
        //    30: d2i            
        //    31: istore          7
        //    33: iload           7
        //    35: ifle            51
        //    38: iinc            1, 8
        //    41: iconst_0       
        //    42: iload           7
        //    44: iload           7
        //    46: imul           
        //    47: iconst_4       
        //    48: imul           
        //    49: iadd           
        //    50: istore_1       
        //    51: iinc            4, 1
        //    54: goto            5
        //    57: iconst_0       
        //    58: ifne            63
        //    61: aconst_null    
        //    62: areturn        
        //    63: iconst_0       
        //    64: invokestatic    org/lwjgl/BufferUtils.createByteBuffer:(I)Ljava/nio/ByteBuffer;
        //    67: astore_2       
        //    68: aload_2        
        //    69: getstatic       java/nio/ByteOrder.BIG_ENDIAN:Ljava/nio/ByteOrder;
        //    72: invokevirtual   java/nio/ByteBuffer.order:(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
        //    75: pop            
        //    76: aload_0        
        //    77: astore_3       
        //    78: aload_3        
        //    79: arraylength    
        //    80: istore          4
        //    82: iconst_0       
        //    83: iconst_0       
        //    84: if_icmpge       248
        //    87: aload_3        
        //    88: iconst_0       
        //    89: aaload         
        //    90: astore          6
        //    92: aload           6
        //    94: invokevirtual   java/nio/ByteBuffer.limit:()I
        //    97: iconst_4       
        //    98: idiv           
        //    99: istore          7
        //   101: iload           7
        //   103: i2d            
        //   104: invokestatic    java/lang/Math.sqrt:(D)D
        //   107: d2i            
        //   108: istore          8
        //   110: aload_2        
        //   111: iload           8
        //   113: invokevirtual   java/nio/ByteBuffer.putInt:(I)Ljava/nio/ByteBuffer;
        //   116: pop            
        //   117: aload_2        
        //   118: iload           8
        //   120: invokevirtual   java/nio/ByteBuffer.putInt:(I)Ljava/nio/ByteBuffer;
        //   123: pop            
        //   124: iconst_0       
        //   125: iload           8
        //   127: if_icmpge       242
        //   130: iconst_0       
        //   131: iload           8
        //   133: if_icmpge       236
        //   136: aload           6
        //   138: iconst_0       
        //   139: iconst_0       
        //   140: iload           8
        //   142: imul           
        //   143: iconst_4       
        //   144: imul           
        //   145: iadd           
        //   146: invokevirtual   java/nio/ByteBuffer.get:(I)B
        //   149: istore          11
        //   151: aload           6
        //   153: iconst_0       
        //   154: iconst_0       
        //   155: iload           8
        //   157: imul           
        //   158: iconst_4       
        //   159: imul           
        //   160: iadd           
        //   161: iconst_1       
        //   162: iadd           
        //   163: invokevirtual   java/nio/ByteBuffer.get:(I)B
        //   166: istore          12
        //   168: aload           6
        //   170: iconst_0       
        //   171: iconst_0       
        //   172: iload           8
        //   174: imul           
        //   175: iconst_4       
        //   176: imul           
        //   177: iadd           
        //   178: iconst_2       
        //   179: iadd           
        //   180: invokevirtual   java/nio/ByteBuffer.get:(I)B
        //   183: istore          13
        //   185: aload           6
        //   187: iconst_0       
        //   188: iconst_0       
        //   189: iload           8
        //   191: imul           
        //   192: iconst_4       
        //   193: imul           
        //   194: iadd           
        //   195: iconst_3       
        //   196: iadd           
        //   197: invokevirtual   java/nio/ByteBuffer.get:(I)B
        //   200: istore          14
        //   202: aload_2        
        //   203: iload           14
        //   205: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   208: pop            
        //   209: aload_2        
        //   210: iload           11
        //   212: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   215: pop            
        //   216: aload_2        
        //   217: iload           12
        //   219: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   222: pop            
        //   223: aload_2        
        //   224: iload           13
        //   226: invokevirtual   java/nio/ByteBuffer.put:(B)Ljava/nio/ByteBuffer;
        //   229: pop            
        //   230: iinc            10, 1
        //   233: goto            130
        //   236: iinc            9, 1
        //   239: goto            124
        //   242: iinc            5, 1
        //   245: goto            82
        //   248: aload_2        
        //   249: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int setIcon(final ByteBuffer[] array) {
        final ByteBuffer convertIcons = convertIcons(array);
        if (convertIcons == null) {
            return 0;
        }
        nSetWindowIcon(getDisplay(), getWindow(), convertIcons, convertIcons.capacity());
        final int length = array.length;
        return 0;
    }
    
    private static native void nSetWindowIcon(final long p0, final long p1, final ByteBuffer p2, final int p3);
    
    public int getX() {
        return this.window_x;
    }
    
    public int getY() {
        return this.window_y;
    }
    
    public int getWidth() {
        return this.window_width;
    }
    
    public int getHeight() {
        return this.window_height;
    }
    
    public boolean isInsideWindow() {
        return this.mouseInside;
    }
    
    public void setResizable(final boolean resizable) {
        if (this.resizable == resizable) {
            return;
        }
        this.resizable = resizable;
        nSetWindowSize(getDisplay(), getWindow(), this.window_width, this.window_height, resizable);
    }
    
    public boolean wasResized() {
        if (this.resized) {
            this.resized = false;
            return true;
        }
        return false;
    }
    
    public float getPixelScaleFactor() {
        return 1.0f;
    }
    
    static boolean access$002(final LinuxDisplay linuxDisplay, final boolean parent_focused) {
        return linuxDisplay.parent_focused = parent_focused;
    }
    
    static boolean access$102(final LinuxDisplay linuxDisplay, final boolean parent_focus_changed) {
        return linuxDisplay.parent_focus_changed = parent_focus_changed;
    }
    
    private static final class Compiz
    {
        private static boolean applyFix;
        private static Provider provider;
        
        static void init() {
            if (Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.nocompiz_lfs")) {
                return;
            }
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                public Object run() {
                    if (!Compiz.access$200("compiz")) {
                        return null;
                    }
                    Compiz.access$302(null);
                    String s;
                    if (Compiz.access$200("dbus-daemon")) {
                        s = "Dbus";
                        Compiz.access$302(new Provider() {
                            private static final String KEY = "/org/freedesktop/compiz/workarounds/allscreens/legacy_fullscreen";
                            final LinuxDisplay$Compiz$1 this$0;
                            
                            public boolean hasLegacyFullscreenSupport() throws LWJGLException {
                                final List access$400 = Compiz.access$400(new String[] { "dbus-send", "--print-reply", "--type=method_call", "--dest=org.freedesktop.compiz", "/org/freedesktop/compiz/workarounds/allscreens/legacy_fullscreen", "org.freedesktop.compiz.get" });
                                if (access$400 == null || access$400.size() < 2) {
                                    throw new LWJGLException("Invalid Dbus reply.");
                                }
                                if (!access$400.get(0).startsWith("method return")) {
                                    throw new LWJGLException("Invalid Dbus reply.");
                                }
                                final String trim = access$400.get(1).trim();
                                if (!trim.startsWith("boolean") || trim.length() < 12) {
                                    throw new LWJGLException("Invalid Dbus reply.");
                                }
                                return "true".equalsIgnoreCase(trim.substring(8));
                            }
                            
                            public void setLegacyFullscreenSupport(final boolean b) throws LWJGLException {
                                if (Compiz.access$400(new String[] { "dbus-send", "--type=method_call", "--dest=org.freedesktop.compiz", "/org/freedesktop/compiz/workarounds/allscreens/legacy_fullscreen", "org.freedesktop.compiz.set", "boolean:" + Boolean.toString(b) }) == null) {
                                    throw new LWJGLException("Failed to apply Compiz LFS workaround.");
                                }
                            }
                        });
                    }
                    else {
                        Runtime.getRuntime().exec("gconftool");
                        s = "gconftool";
                        Compiz.access$302(new Provider() {
                            private static final String KEY = "/apps/compiz/plugins/workarounds/allscreens/options/legacy_fullscreen";
                            final LinuxDisplay$Compiz$1 this$0;
                            
                            public boolean hasLegacyFullscreenSupport() throws LWJGLException {
                                final List access$400 = Compiz.access$400(new String[] { "gconftool", "-g", "/apps/compiz/plugins/workarounds/allscreens/options/legacy_fullscreen" });
                                if (access$400 == null || access$400.size() == 0) {
                                    throw new LWJGLException("Invalid gconftool reply.");
                                }
                                return Boolean.parseBoolean(access$400.get(0).trim());
                            }
                            
                            public void setLegacyFullscreenSupport(final boolean b) throws LWJGLException {
                                if (Compiz.access$400(new String[] { "gconftool", "-s", "/apps/compiz/plugins/workarounds/allscreens/options/legacy_fullscreen", "-s", Boolean.toString(b), "-t", "bool" }) == null) {
                                    throw new LWJGLException("Failed to apply Compiz LFS workaround.");
                                }
                                if (b) {
                                    Thread.sleep(200L);
                                }
                            }
                        });
                    }
                    if (Compiz.access$300() != null && !Compiz.access$300().hasLegacyFullscreenSupport()) {
                        Compiz.access$502(true);
                        LWJGLUtil.log("Using " + s + " to apply Compiz LFS workaround.");
                    }
                    return null;
                }
            });
        }
        
        static void setLegacyFullscreenSupport(final boolean b) {
            if (!Compiz.applyFix) {
                return;
            }
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction(b) {
                final boolean val$enabled;
                
                public Object run() {
                    Compiz.access$300().setLegacyFullscreenSupport(this.val$enabled);
                    return null;
                }
            });
        }
        
        private static List run(final String... array) throws LWJGLException {
            final ArrayList<String> list = new ArrayList<String>();
            final Process exec = Runtime.getRuntime().exec(array);
            if (exec.waitFor() != 0) {
                return null;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            bufferedReader.close();
            return list;
        }
        
        private static boolean isProcessActive(final String s) throws LWJGLException {
            final List run = run("ps", "-C", s);
            if (run == null) {
                return false;
            }
            final Iterator<String> iterator = run.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().contains(s)) {
                    return true;
                }
            }
            return false;
        }
        
        static boolean access$200(final String s) throws LWJGLException {
            return isProcessActive(s);
        }
        
        static Provider access$302(final Provider provider) {
            return Compiz.provider = provider;
        }
        
        static List access$400(final String[] array) throws LWJGLException {
            return run(array);
        }
        
        static Provider access$300() {
            return Compiz.provider;
        }
        
        static boolean access$502(final boolean applyFix) {
            return Compiz.applyFix = applyFix;
        }
        
        private interface Provider
        {
            boolean hasLegacyFullscreenSupport() throws LWJGLException;
            
            void setLegacyFullscreenSupport(final boolean p0) throws LWJGLException;
        }
    }
}
