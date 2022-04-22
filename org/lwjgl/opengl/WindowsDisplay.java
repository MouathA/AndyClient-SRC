package org.lwjgl.opengl;

import java.util.concurrent.atomic.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.nio.*;
import org.lwjgl.input.*;
import java.lang.reflect.*;
import org.lwjgl.*;

final class WindowsDisplay implements DisplayImplementation
{
    private static final int GAMMA_LENGTH = 256;
    private static final int WM_WINDOWPOSCHANGED = 71;
    private static final int WM_MOVE = 3;
    private static final int WM_CANCELMODE = 31;
    private static final int WM_MOUSEMOVE = 512;
    private static final int WM_LBUTTONDOWN = 513;
    private static final int WM_LBUTTONUP = 514;
    private static final int WM_LBUTTONDBLCLK = 515;
    private static final int WM_RBUTTONDOWN = 516;
    private static final int WM_RBUTTONUP = 517;
    private static final int WM_RBUTTONDBLCLK = 518;
    private static final int WM_MBUTTONDOWN = 519;
    private static final int WM_MBUTTONUP = 520;
    private static final int WM_MBUTTONDBLCLK = 521;
    private static final int WM_XBUTTONDOWN = 523;
    private static final int WM_XBUTTONUP = 524;
    private static final int WM_XBUTTONDBLCLK = 525;
    private static final int WM_MOUSEWHEEL = 522;
    private static final int WM_CAPTURECHANGED = 533;
    private static final int WM_MOUSELEAVE = 675;
    private static final int WM_ENTERSIZEMOVE = 561;
    private static final int WM_EXITSIZEMOVE = 562;
    private static final int WM_SIZING = 532;
    private static final int WM_KEYDOWN = 256;
    private static final int WM_KEYUP = 257;
    private static final int WM_SYSKEYUP = 261;
    private static final int WM_SYSKEYDOWN = 260;
    private static final int WM_SYSCHAR = 262;
    private static final int WM_CHAR = 258;
    private static final int WM_GETICON = 127;
    private static final int WM_SETICON = 128;
    private static final int WM_SETCURSOR = 32;
    private static final int WM_MOUSEACTIVATE = 33;
    private static final int WM_QUIT = 18;
    private static final int WM_SYSCOMMAND = 274;
    private static final int WM_PAINT = 15;
    private static final int WM_KILLFOCUS = 8;
    private static final int WM_SETFOCUS = 7;
    private static final int SC_SIZE = 61440;
    private static final int SC_MOVE = 61456;
    private static final int SC_MINIMIZE = 61472;
    private static final int SC_MAXIMIZE = 61488;
    private static final int SC_NEXTWINDOW = 61504;
    private static final int SC_PREVWINDOW = 61520;
    private static final int SC_CLOSE = 61536;
    private static final int SC_VSCROLL = 61552;
    private static final int SC_HSCROLL = 61568;
    private static final int SC_MOUSEMENU = 61584;
    private static final int SC_KEYMENU = 61696;
    private static final int SC_ARRANGE = 61712;
    private static final int SC_RESTORE = 61728;
    private static final int SC_TASKLIST = 61744;
    private static final int SC_SCREENSAVE = 61760;
    private static final int SC_HOTKEY = 61776;
    private static final int SC_DEFAULT = 61792;
    private static final int SC_MONITORPOWER = 61808;
    private static final int SC_CONTEXTHELP = 61824;
    private static final int SC_SEPARATOR = 61455;
    static final int SM_CXCURSOR = 13;
    static final int SM_CYCURSOR = 14;
    static final int SM_CMOUSEBUTTONS = 43;
    static final int SM_MOUSEWHEELPRESENT = 75;
    private static final int SIZE_RESTORED = 0;
    private static final int SIZE_MINIMIZED = 1;
    private static final int SIZE_MAXIMIZED = 2;
    private static final int WM_SIZE = 5;
    private static final int WM_ACTIVATE = 6;
    private static final int WA_INACTIVE = 0;
    private static final int WA_ACTIVE = 1;
    private static final int WA_CLICKACTIVE = 2;
    private static final int SW_NORMAL = 1;
    private static final int SW_SHOWMINNOACTIVE = 7;
    private static final int SW_SHOWDEFAULT = 10;
    private static final int SW_RESTORE = 9;
    private static final int SW_MAXIMIZE = 3;
    private static final int ICON_SMALL = 0;
    private static final int ICON_BIG = 1;
    private static final IntBuffer rect_buffer;
    private static final Rect rect;
    private static final long HWND_TOP = 0L;
    private static final long HWND_BOTTOM = 1L;
    private static final long HWND_TOPMOST = -1L;
    private static final long HWND_NOTOPMOST = -2L;
    private static final int SWP_NOSIZE = 1;
    private static final int SWP_NOMOVE = 2;
    private static final int SWP_NOZORDER = 4;
    private static final int SWP_FRAMECHANGED = 32;
    private static final int GWL_STYLE = -16;
    private static final int GWL_EXSTYLE = -20;
    private static final int WS_THICKFRAME = 262144;
    private static final int WS_MAXIMIZEBOX = 65536;
    private static final int HTCLIENT = 1;
    private static final int MK_XBUTTON1 = 32;
    private static final int MK_XBUTTON2 = 64;
    private static final int XBUTTON1 = 1;
    private static final int XBUTTON2 = 2;
    private static WindowsDisplay current_display;
    private static boolean cursor_clipped;
    private WindowsDisplayPeerInfo peer_info;
    private Object current_cursor;
    private static boolean hasParent;
    private Canvas parent;
    private long parent_hwnd;
    private FocusAdapter parent_focus_tracker;
    private AtomicBoolean parent_focused;
    private WindowsKeyboard keyboard;
    private WindowsMouse mouse;
    private boolean close_requested;
    private boolean is_dirty;
    private ByteBuffer current_gamma;
    private ByteBuffer saved_gamma;
    private DisplayMode current_mode;
    private boolean mode_set;
    private boolean isMinimized;
    private boolean isFocused;
    private boolean redoMakeContextCurrent;
    private boolean inAppActivate;
    private boolean resized;
    private boolean resizable;
    private int x;
    private int y;
    private int width;
    private int height;
    private long hwnd;
    private long hdc;
    private long small_icon;
    private long large_icon;
    private boolean iconsLoaded;
    private int captureMouse;
    private boolean mouseInside;
    
    WindowsDisplay() {
        this.captureMouse = -1;
        WindowsDisplay.current_display = this;
    }
    
    public void createWindow(final DrawableLWJGL drawableLWJGL, final DisplayMode displayMode, final Canvas parent, final int n, final int n2) throws LWJGLException {
        this.parent = parent;
        WindowsDisplay.hasParent = (parent != null);
        this.parent_hwnd = ((parent != null) ? getHwnd(parent) : 0L);
        this.hwnd = nCreateWindow(n, n2, displayMode.getWidth(), displayMode.getHeight(), Display.isFullscreen() || isUndecorated(), parent != null, this.parent_hwnd);
        if (Display.isResizable() && parent == null) {
            this.setResizable(true);
        }
        if (this.hwnd == 0L) {
            throw new LWJGLException("Failed to create window");
        }
        this.hdc = getDC(this.hwnd);
        if (this.hdc == 0L) {
            nDestroyWindow(this.hwnd);
            throw new LWJGLException("Failed to get dc");
        }
        if (drawableLWJGL instanceof DrawableGL) {
            WindowsPeerInfo.setPixelFormat(this.getHdc(), WindowsPeerInfo.choosePixelFormat(this.getHdc(), 0, 0, (PixelFormat)drawableLWJGL.getPixelFormat(), null, true, true, false, true));
        }
        else {
            this.peer_info = new WindowsDisplayPeerInfo(true);
            ((DrawableGLES)drawableLWJGL).initialize(this.hwnd, this.hdc, 4, (org.lwjgl.opengles.PixelFormat)drawableLWJGL.getPixelFormat());
        }
        this.peer_info.initDC(this.getHwnd(), this.getHdc());
        showWindow(this.getHwnd(), 10);
        this.updateWidthAndHeight();
        if (parent == null) {
            setForegroundWindow(this.getHwnd());
        }
        else {
            this.parent_focused = new AtomicBoolean(false);
            parent.addFocusListener(this.parent_focus_tracker = new FocusAdapter() {
                final WindowsDisplay this$0;
                
                @Override
                public void focusGained(final FocusEvent focusEvent) {
                    WindowsDisplay.access$100(this.this$0).set(true);
                    WindowsDisplay.access$200(this.this$0);
                }
            });
            SwingUtilities.invokeLater(new Runnable() {
                final WindowsDisplay this$0;
                
                public void run() {
                    WindowsDisplay.access$200(this.this$0);
                }
            });
        }
        this.grabFocus();
    }
    
    private void updateWidthAndHeight() {
        getClientRect(this.hwnd, WindowsDisplay.rect_buffer);
        WindowsDisplay.rect.copyFromBuffer(WindowsDisplay.rect_buffer);
        this.width = WindowsDisplay.rect.right - WindowsDisplay.rect.left;
        this.height = WindowsDisplay.rect.bottom - WindowsDisplay.rect.top;
    }
    
    private static native long nCreateWindow(final int p0, final int p1, final int p2, final int p3, final boolean p4, final boolean p5, final long p6) throws LWJGLException;
    
    private static boolean isUndecorated() {
        return Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated");
    }
    
    private static long getHwnd(final Canvas canvas) throws LWJGLException {
        final WindowsPeerInfo windowsPeerInfo = (WindowsPeerInfo)AWTGLCanvas.createImplementation().createPeerInfo(canvas, null, null);
        windowsPeerInfo.lockAndGetHandle();
        final long hwnd = windowsPeerInfo.getHwnd();
        windowsPeerInfo.unlock();
        return hwnd;
    }
    
    public void destroyWindow() {
        if (this.parent != null) {
            this.parent.removeFocusListener(this.parent_focus_tracker);
            this.parent_focus_tracker = null;
        }
        nReleaseDC(this.hwnd, this.hdc);
        nDestroyWindow(this.hwnd);
        this.freeLargeIcon();
        this.freeSmallIcon();
        this.close_requested = false;
        this.is_dirty = false;
        this.isMinimized = false;
        this.isFocused = false;
        this.redoMakeContextCurrent = false;
        this.mouseInside = false;
    }
    
    private static native void nReleaseDC(final long p0, final long p1);
    
    private static native void nDestroyWindow(final long p0);
    
    static void resetCursorClipping() {
        if (WindowsDisplay.cursor_clipped) {
            clipCursor(null);
            WindowsDisplay.cursor_clipped = false;
        }
    }
    
    private static void getGlobalClientRect(final long n, final Rect rect) {
        WindowsDisplay.rect_buffer.put(0, 0).put(1, 0);
        clientToScreen(n, WindowsDisplay.rect_buffer);
        final int value = WindowsDisplay.rect_buffer.get(0);
        final int value2 = WindowsDisplay.rect_buffer.get(1);
        getClientRect(n, WindowsDisplay.rect_buffer);
        rect.copyFromBuffer(WindowsDisplay.rect_buffer);
        rect.offset(value, value2);
    }
    
    static void setupCursorClipping(final long n) throws LWJGLException {
        WindowsDisplay.cursor_clipped = true;
        getGlobalClientRect(n, WindowsDisplay.rect);
        WindowsDisplay.rect.copyToBuffer(WindowsDisplay.rect_buffer);
        clipCursor(WindowsDisplay.rect_buffer);
    }
    
    private static native void clipCursor(final IntBuffer p0) throws LWJGLException;
    
    public void switchDisplayMode(final DisplayMode current_mode) throws LWJGLException {
        nSwitchDisplayMode(current_mode);
        this.current_mode = current_mode;
        this.mode_set = true;
    }
    
    private static native void nSwitchDisplayMode(final DisplayMode p0) throws LWJGLException;
    
    private void appActivate(final boolean isFocused, final long n) {
        if (this.inAppActivate) {
            return;
        }
        this.inAppActivate = true;
        this.isFocused = isFocused;
        if (isFocused) {
            if (Display.isFullscreen()) {
                this.restoreDisplayMode();
            }
            if (this.parent == null) {
                setForegroundWindow(this.getHwnd());
            }
            setFocus(this.getHwnd());
            this.redoMakeContextCurrent = true;
        }
        else {
            if (this.keyboard != null) {
                this.keyboard.releaseAll(n);
            }
            if (Display.isFullscreen()) {
                showWindow(this.getHwnd(), 7);
                this.resetDisplayMode();
            }
        }
        this.updateCursor();
        this.inAppActivate = false;
    }
    
    private static native void showWindow(final long p0, final int p1);
    
    private static native void setForegroundWindow(final long p0);
    
    private static native void setFocus(final long p0);
    
    private void clearAWTFocus() {
        this.parent.setFocusable(false);
        this.parent.setFocusable(true);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
    }
    
    private void grabFocus() {
        if (this.parent == null) {
            setFocus(this.getHwnd());
        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                final WindowsDisplay this$0;
                
                public void run() {
                    WindowsDisplay.access$300(this.this$0).requestFocus();
                }
            });
        }
    }
    
    private void restoreDisplayMode() {
        this.doSetGammaRamp(this.current_gamma);
        if (!this.mode_set) {
            this.mode_set = true;
            nSwitchDisplayMode(this.current_mode);
        }
    }
    
    public void resetDisplayMode() {
        this.doSetGammaRamp(this.saved_gamma);
        this.current_gamma = this.saved_gamma;
        if (this.mode_set) {
            this.mode_set = false;
        }
    }
    
    private static native void nResetDisplayMode();
    
    public int getGammaRampLength() {
        return 256;
    }
    
    public void setGammaRamp(final FloatBuffer floatBuffer) throws LWJGLException {
        this.doSetGammaRamp(convertToNativeRamp(floatBuffer));
    }
    
    private static native ByteBuffer convertToNativeRamp(final FloatBuffer p0) throws LWJGLException;
    
    private static native ByteBuffer getCurrentGammaRamp() throws LWJGLException;
    
    private void doSetGammaRamp(final ByteBuffer current_gamma) throws LWJGLException {
        nSetGammaRamp(current_gamma);
        this.current_gamma = current_gamma;
    }
    
    private static native void nSetGammaRamp(final ByteBuffer p0) throws LWJGLException;
    
    public String getAdapter() {
        final char char1 = WindowsRegistry.queryRegistrationKey(3, "HARDWARE\\DeviceMap\\Video", "MaxObjectNumber").charAt(0);
        String s = "";
        while ('\0' < char1) {
            final String queryRegistrationKey = WindowsRegistry.queryRegistrationKey(3, "HARDWARE\\DeviceMap\\Video", "\\Device\\Video" + 0);
            final String s2 = "\\registry\\machine\\";
            if (queryRegistrationKey.toLowerCase().startsWith(s2)) {
                final String queryRegistrationKey2 = WindowsRegistry.queryRegistrationKey(3, queryRegistrationKey.substring(s2.length()), "InstalledDisplayDrivers");
                if (queryRegistrationKey2.toUpperCase().startsWith("VGA")) {
                    s = queryRegistrationKey2;
                }
                else if (!queryRegistrationKey2.toUpperCase().startsWith("RDP") && !queryRegistrationKey2.toUpperCase().startsWith("NMNDD")) {
                    return queryRegistrationKey2;
                }
            }
            int n = 0;
            ++n;
        }
        if (!s.equals("")) {
            return s;
        }
        return null;
    }
    
    public String getVersion() {
        final String adapter = this.getAdapter();
        if (adapter != null) {
            final String[] split = adapter.split(",");
            if (split.length > 0) {
                final WindowsFileVersion nGetVersion = this.nGetVersion(split[0] + ".dll");
                if (nGetVersion != null) {
                    return nGetVersion.toString();
                }
            }
        }
        return null;
    }
    
    private native WindowsFileVersion nGetVersion(final String p0);
    
    public DisplayMode init() throws LWJGLException {
        final ByteBuffer currentGammaRamp = getCurrentGammaRamp();
        this.saved_gamma = currentGammaRamp;
        this.current_gamma = currentGammaRamp;
        return this.current_mode = getCurrentDisplayMode();
    }
    
    private static native DisplayMode getCurrentDisplayMode() throws LWJGLException;
    
    public void setTitle(final String s) {
        nSetTitle(this.hwnd, MemoryUtil.getAddress0(MemoryUtil.encodeUTF16(s)));
    }
    
    private static native void nSetTitle(final long p0, final long p1);
    
    public boolean isCloseRequested() {
        final boolean close_requested = this.close_requested;
        this.close_requested = false;
        return close_requested;
    }
    
    public boolean isVisible() {
        return !this.isMinimized;
    }
    
    public boolean isActive() {
        return this.isFocused;
    }
    
    public boolean isDirty() {
        final boolean is_dirty = this.is_dirty;
        this.is_dirty = false;
        return is_dirty;
    }
    
    public PeerInfo createPeerInfo(final PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        return this.peer_info = new WindowsDisplayPeerInfo(false);
    }
    
    public void update() {
        if (!this.isFocused && this.parent != null && this.parent_focused.compareAndSet(true, false)) {
            setFocus(this.getHwnd());
        }
        if (this.redoMakeContextCurrent) {
            this.redoMakeContextCurrent = false;
            final Context context = ((DrawableLWJGL)Display.getDrawable()).getContext();
            if (context != null && context.isCurrent()) {
                context.makeCurrent();
            }
        }
    }
    
    private static native void nUpdate();
    
    public void reshape(final int n, final int n2, final int n3, final int n4) {
        nReshape(this.getHwnd(), n, n2, n3, n4, Display.isFullscreen() || isUndecorated(), this.parent != null);
    }
    
    private static native void nReshape(final long p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final boolean p6);
    
    public native DisplayMode[] getAvailableDisplayModes() throws LWJGLException;
    
    public boolean hasWheel() {
        return this.mouse.hasWheel();
    }
    
    public int getButtonCount() {
        return this.mouse.getButtonCount();
    }
    
    public void createMouse() throws LWJGLException {
        this.mouse = new WindowsMouse(this.getHwnd());
    }
    
    public void destroyMouse() {
        if (this.mouse != null) {
            this.mouse.destroy();
        }
        this.mouse = null;
    }
    
    public void pollMouse(final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.mouse.poll(intBuffer, byteBuffer, this);
    }
    
    public void readMouse(final ByteBuffer byteBuffer) {
        this.mouse.read(byteBuffer);
    }
    
    public void grabMouse(final boolean b) {
        this.mouse.grab(b);
        this.updateCursor();
    }
    
    public int getNativeCursorCapabilities() {
        return 1;
    }
    
    public void setCursorPosition(final int n, final int n2) {
        getGlobalClientRect(this.getHwnd(), WindowsDisplay.rect);
        nSetCursorPosition(WindowsDisplay.rect.left + n, WindowsDisplay.rect.bottom - 1 - n2);
        this.setMousePosition(n, n2);
    }
    
    private static native void nSetCursorPosition(final int p0, final int p1);
    
    public void setNativeCursor(final Object current_cursor) throws LWJGLException {
        this.current_cursor = current_cursor;
        this.updateCursor();
    }
    
    private void updateCursor() {
        if (this.mouse != null && this.shouldGrab()) {
            centerCursor(this.hwnd);
            nSetNativeCursor(this.getHwnd(), this.mouse.getBlankCursor());
        }
        else {
            nSetNativeCursor(this.getHwnd(), this.current_cursor);
        }
        this.updateClipping();
    }
    
    static native void nSetNativeCursor(final long p0, final Object p1) throws LWJGLException;
    
    public int getMinCursorSize() {
        return getSystemMetrics(13);
    }
    
    public int getMaxCursorSize() {
        return getSystemMetrics(13);
    }
    
    static native int getSystemMetrics(final int p0);
    
    private static native long getDllInstance();
    
    private long getHwnd() {
        return this.hwnd;
    }
    
    private long getHdc() {
        return this.hdc;
    }
    
    private static native long getDC(final long p0);
    
    private static native long getDesktopWindow();
    
    private static native long getForegroundWindow();
    
    static void centerCursor(final long n) {
        if (getForegroundWindow() != n && !WindowsDisplay.hasParent) {
            return;
        }
        getGlobalClientRect(n, WindowsDisplay.rect);
        final int left = WindowsDisplay.rect.left;
        final int top = WindowsDisplay.rect.top;
        final int n2 = (WindowsDisplay.rect.left + WindowsDisplay.rect.right) / 2;
        final int n3 = (WindowsDisplay.rect.top + WindowsDisplay.rect.bottom) / 2;
        nSetCursorPosition(n2, n3);
        final int n4 = n2 - left;
        final int n5 = n3 - top;
        if (WindowsDisplay.current_display != null) {
            WindowsDisplay.current_display.setMousePosition(n4, transformY(n, n5));
        }
    }
    
    private void setMousePosition(final int n, final int n2) {
        if (this.mouse != null) {
            this.mouse.setPosition(n, n2);
        }
    }
    
    public void createKeyboard() throws LWJGLException {
        this.keyboard = new WindowsKeyboard();
    }
    
    public void destroyKeyboard() {
        this.keyboard = null;
    }
    
    public void pollKeyboard(final ByteBuffer byteBuffer) {
        this.keyboard.poll(byteBuffer);
    }
    
    public void readKeyboard(final ByteBuffer byteBuffer) {
        this.keyboard.read(byteBuffer);
    }
    
    public static native ByteBuffer nCreateCursor(final int p0, final int p1, final int p2, final int p3, final int p4, final IntBuffer p5, final int p6, final IntBuffer p7, final int p8) throws LWJGLException;
    
    public Object createCursor(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        return doCreateCursor(n, n2, n3, n4, n5, intBuffer, intBuffer2);
    }
    
    static Object doCreateCursor(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        return nCreateCursor(n, n2, n3, n4, n5, intBuffer, intBuffer.position(), intBuffer2, (intBuffer2 != null) ? intBuffer2.position() : -1);
    }
    
    public void destroyCursor(final Object o) {
        doDestroyCursor(o);
    }
    
    static native void doDestroyCursor(final Object p0);
    
    public int getPbufferCapabilities() {
        return this.nGetPbufferCapabilities(new PixelFormat(0, 0, 0, 0, 0, 0, 0, 0, false));
    }
    
    private native int nGetPbufferCapabilities(final PixelFormat p0) throws LWJGLException;
    
    public boolean isBufferLost(final PeerInfo peerInfo) {
        return ((WindowsPbufferPeerInfo)peerInfo).isBufferLost();
    }
    
    public PeerInfo createPbuffer(final int n, final int n2, final PixelFormat pixelFormat, final ContextAttribs contextAttribs, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        return new WindowsPbufferPeerInfo(n, n2, pixelFormat, intBuffer, intBuffer2);
    }
    
    public void setPbufferAttrib(final PeerInfo peerInfo, final int n, final int n2) {
        ((WindowsPbufferPeerInfo)peerInfo).setPbufferAttrib(n, n2);
    }
    
    public void bindTexImageToPbuffer(final PeerInfo peerInfo, final int n) {
        ((WindowsPbufferPeerInfo)peerInfo).bindTexImageToPbuffer(n);
    }
    
    public void releaseTexImageFromPbuffer(final PeerInfo peerInfo, final int n) {
        ((WindowsPbufferPeerInfo)peerInfo).releaseTexImageFromPbuffer(n);
    }
    
    private void freeSmallIcon() {
        if (this.small_icon != 0L) {
            destroyIcon(this.small_icon);
            this.small_icon = 0L;
        }
    }
    
    private void freeLargeIcon() {
        if (this.large_icon != 0L) {
            destroyIcon(this.large_icon);
            this.large_icon = 0L;
        }
    }
    
    public int setIcon(final ByteBuffer[] array) {
        while (0 < array.length) {
            final ByteBuffer byteBuffer = array[0];
            final int n = byteBuffer.limit() / 4;
            int n2 = 0;
            if ((int)Math.sqrt(n) == 16 && !true) {
                final long icon = createIcon(16, 16, byteBuffer.asIntBuffer());
                sendMessage(this.hwnd, 128L, 0L, icon);
                this.freeSmallIcon();
                this.small_icon = icon;
                ++n2;
            }
            if ((int)Math.sqrt(n) == 32 && !true) {
                final long icon2 = createIcon(32, 32, byteBuffer.asIntBuffer());
                sendMessage(this.hwnd, 128L, 1L, icon2);
                this.freeLargeIcon();
                this.large_icon = icon2;
                ++n2;
                this.iconsLoaded = false;
                final long nanoTime = System.nanoTime();
                final long n3 = 500000000L;
                while (!this.iconsLoaded) {
                    if (n3 < System.nanoTime() - nanoTime) {
                        break;
                    }
                    Thread.yield();
                }
            }
            int n4 = 0;
            ++n4;
        }
        return 0;
    }
    
    private static native long createIcon(final int p0, final int p1, final IntBuffer p2);
    
    private static native void destroyIcon(final long p0);
    
    private static native long sendMessage(final long p0, final long p1, final long p2, final long p3);
    
    private static native long setWindowLongPtr(final long p0, final int p1, final long p2);
    
    private static native long getWindowLongPtr(final long p0, final int p1);
    
    private static native boolean setWindowPos(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    private void handleMouseButton(final int captureMouse, final int n, final long n2) {
        if (this.mouse != null) {
            this.mouse.handleMouseButton((byte)captureMouse, (byte)n, n2);
            if (this.captureMouse == -1 && captureMouse != -1 && n == 1) {
                this.captureMouse = captureMouse;
                nSetCapture(this.hwnd);
            }
            if (this.captureMouse != -1 && captureMouse == this.captureMouse && n == 0) {
                this.captureMouse = -1;
                nReleaseCapture();
            }
        }
    }
    
    private boolean shouldGrab() {
        return !this.isMinimized && this.isFocused && Mouse.isGrabbed();
    }
    
    private static native long nSetCapture(final long p0);
    
    private static native boolean nReleaseCapture();
    
    private void handleMouseScrolled(final int n, final long n2) {
        if (this.mouse != null) {
            this.mouse.handleMouseScrolled(n, n2);
        }
    }
    
    private static native void getClientRect(final long p0, final IntBuffer p1);
    
    private void handleChar(final long n, final long n2, final long n3) {
        final boolean b = (byte)(1L - (n2 >>> 31 & 0x1L)) == (byte)(n2 >>> 30 & 0x1L);
        if (this.keyboard != null) {
            this.keyboard.handleChar((int)(n & 0xFFFFL), n3, b);
        }
    }
    
    private void handleKeyButton(final long n, final long n2, final long n3) {
        if (this.keyboard == null) {
            return;
        }
        final byte b = (byte)(n2 >>> 30 & 0x1L);
        final byte b2 = (byte)(1L - (n2 >>> 31 & 0x1L));
        this.keyboard.handleKey((int)n, (int)(n2 >>> 16 & 0xFFL), (byte)(n2 >>> 24 & 0x1L) != 0, b2, n3, b2 == b);
    }
    
    private static int transformY(final long n, final int n2) {
        getClientRect(n, WindowsDisplay.rect_buffer);
        WindowsDisplay.rect.copyFromBuffer(WindowsDisplay.rect_buffer);
        return WindowsDisplay.rect.bottom - WindowsDisplay.rect.top - 1 - n2;
    }
    
    private static native void clientToScreen(final long p0, final IntBuffer p1);
    
    private static native void setWindowProc(final Method p0);
    
    private static long handleMessage(final long n, final int n2, final long n3, final long n4, final long n5) {
        if (WindowsDisplay.current_display != null) {
            return WindowsDisplay.current_display.doHandleMessage(n, n2, n3, n4, n5);
        }
        return defWindowProc(n, n2, n3, n4);
    }
    
    private static native long defWindowProc(final long p0, final int p1, final long p2, final long p3);
    
    private void updateClipping() {
        if ((Display.isFullscreen() || (this.mouse != null && this.mouse.isGrabbed())) && !this.isMinimized && this.isFocused && (getForegroundWindow() == this.getHwnd() || WindowsDisplay.hasParent)) {
            setupCursorClipping(this.getHwnd());
        }
    }
    
    private void setMinimized(final boolean isMinimized) {
        if (isMinimized != this.isMinimized) {
            this.isMinimized = isMinimized;
            this.updateClipping();
        }
    }
    
    private long doHandleMessage(final long n, final int n2, final long n3, final long n4, final long n5) {
        if (this.parent != null && !this.isFocused) {
            switch (n2) {
                case 513:
                case 516:
                case 519:
                case 523: {
                    sendMessage(this.parent_hwnd, n2, n3, n4);
                    break;
                }
            }
        }
        Label_0919: {
            switch (n2) {
                case 6: {
                    return 0L;
                }
                case 5: {
                    switch ((int)n3) {
                        case 0:
                        case 2: {
                            this.resized = true;
                            this.updateWidthAndHeight();
                            this.setMinimized(false);
                            break;
                        }
                        case 1: {
                            this.setMinimized(true);
                            break;
                        }
                    }
                    break;
                }
                case 532: {
                    this.resized = true;
                    this.updateWidthAndHeight();
                    break;
                }
                case 8: {
                    this.appActivate(false, n5);
                    return 0L;
                }
                case 7: {
                    this.appActivate(true, n5);
                    return 0L;
                }
                case 33: {
                    if (this.parent != null) {
                        if (!this.isFocused) {
                            this.grabFocus();
                        }
                        return 3L;
                    }
                    break;
                }
                case 512: {
                    if (this.mouse != null) {
                        this.mouse.handleMouseMoved((short)(n4 & 0xFFFFL), transformY(this.getHwnd(), (short)(n4 >>> 16)), n5);
                    }
                    if (!this.mouseInside) {
                        this.mouseInside = true;
                        this.updateCursor();
                        this.nTrackMouseEvent(n);
                    }
                    return 0L;
                }
                case 522: {
                    this.handleMouseScrolled((short)(n3 >> 16 & 0xFFFFL), n5);
                    return 0L;
                }
                case 513: {
                    this.handleMouseButton(0, 1, n5);
                    return 0L;
                }
                case 514: {
                    this.handleMouseButton(0, 0, n5);
                    return 0L;
                }
                case 516: {
                    this.handleMouseButton(1, 1, n5);
                    return 0L;
                }
                case 517: {
                    this.handleMouseButton(1, 0, n5);
                    return 0L;
                }
                case 519: {
                    this.handleMouseButton(2, 1, n5);
                    return 0L;
                }
                case 520: {
                    this.handleMouseButton(2, 0, n5);
                    return 0L;
                }
                case 524: {
                    if (n3 >> 16 == 1L) {
                        this.handleMouseButton(3, 0, n5);
                    }
                    else {
                        this.handleMouseButton(4, 0, n5);
                    }
                    return 1L;
                }
                case 523: {
                    if ((n3 & 0xFFL) == 0x20L) {
                        this.handleMouseButton(3, 1, n5);
                    }
                    else {
                        this.handleMouseButton(4, 1, n5);
                    }
                    return 1L;
                }
                case 258:
                case 262: {
                    this.handleChar(n3, n4, n5);
                    return 0L;
                }
                case 261: {
                    if (n3 == 18L || n3 == 121L) {
                        this.handleKeyButton(n3, n4, n5);
                        return 0L;
                    }
                }
                case 257: {
                    if (n3 == 44L && this.keyboard != null && !this.keyboard.isKeyDown(183)) {
                        this.handleKeyButton(n3, n4 & 0x7FFFFFFFL & 0xFFFFFFFFBFFFFFFFL, n5);
                    }
                }
                case 256:
                case 260: {
                    this.handleKeyButton(n3, n4, n5);
                    break;
                }
                case 18: {
                    this.close_requested = true;
                    return 0L;
                }
                case 274: {
                    switch ((int)(n3 & 0xFFF0L)) {
                        case 61760:
                        case 61808: {
                            return 0L;
                        }
                        case 61536: {
                            this.close_requested = true;
                            return 0L;
                        }
                        default: {
                            break Label_0919;
                        }
                    }
                    break;
                }
                case 15: {
                    this.is_dirty = true;
                    break;
                }
                case 675: {
                    this.mouseInside = false;
                    break;
                }
                case 31: {
                    nReleaseCapture();
                }
                case 533: {
                    if (this.captureMouse != -1) {
                        this.handleMouseButton(this.captureMouse, 0, n5);
                        this.captureMouse = -1;
                    }
                    return 0L;
                }
                case 71: {
                    if (this.getWindowRect(n, WindowsDisplay.rect_buffer)) {
                        WindowsDisplay.rect.copyFromBuffer(WindowsDisplay.rect_buffer);
                        this.x = WindowsDisplay.rect.left;
                        this.y = WindowsDisplay.rect.top;
                        break;
                    }
                    LWJGLUtil.log("WM_WINDOWPOSCHANGED: Unable to get window rect");
                    break;
                }
                case 127: {
                    this.iconsLoaded = true;
                    break;
                }
            }
        }
        return defWindowProc(n, n2, n3, n4);
    }
    
    private native boolean getWindowRect(final long p0, final IntBuffer p1);
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    private native boolean nTrackMouseEvent(final long p0);
    
    public boolean isInsideWindow() {
        return this.mouseInside;
    }
    
    public void setResizable(final boolean resizable) {
        if (this.resizable == resizable) {
            return;
        }
        this.resized = false;
        this.resizable = resizable;
        final int n = (int)getWindowLongPtr(this.hwnd, -16);
        final int n2 = (int)getWindowLongPtr(this.hwnd, -20);
        final int n3;
        setWindowLongPtr(this.hwnd, -16, n3 = ((resizable && !Display.isFullscreen()) ? (n | 0x50000) : (n & 0xFFFAFFFF)));
        getGlobalClientRect(this.hwnd, WindowsDisplay.rect);
        WindowsDisplay.rect.copyToBuffer(WindowsDisplay.rect_buffer);
        this.adjustWindowRectEx(WindowsDisplay.rect_buffer, n3, false, n2);
        WindowsDisplay.rect.copyFromBuffer(WindowsDisplay.rect_buffer);
        setWindowPos(this.hwnd, 0L, WindowsDisplay.rect.left, WindowsDisplay.rect.top, WindowsDisplay.rect.right - WindowsDisplay.rect.left, WindowsDisplay.rect.bottom - WindowsDisplay.rect.top, 36L);
        this.updateWidthAndHeight();
    }
    
    private native boolean adjustWindowRectEx(final IntBuffer p0, final int p1, final boolean p2, final int p3);
    
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
    
    static AtomicBoolean access$100(final WindowsDisplay windowsDisplay) {
        return windowsDisplay.parent_focused;
    }
    
    static void access$200(final WindowsDisplay windowsDisplay) {
        windowsDisplay.clearAWTFocus();
    }
    
    static Canvas access$300(final WindowsDisplay windowsDisplay) {
        return windowsDisplay.parent;
    }
    
    static {
        rect_buffer = BufferUtils.createIntBuffer(4);
        rect = new Rect(null);
        setWindowProc(WindowsDisplay.class.getDeclaredMethod("handleMessage", Long.TYPE, Integer.TYPE, Long.TYPE, Long.TYPE, Long.TYPE));
    }
    
    private static final class Rect
    {
        public int left;
        public int top;
        public int right;
        public int bottom;
        
        private Rect() {
        }
        
        public void copyToBuffer(final IntBuffer intBuffer) {
            intBuffer.put(0, this.left).put(1, this.top).put(2, this.right).put(3, this.bottom);
        }
        
        public void copyFromBuffer(final IntBuffer intBuffer) {
            this.left = intBuffer.get(0);
            this.top = intBuffer.get(1);
            this.right = intBuffer.get(2);
            this.bottom = intBuffer.get(3);
        }
        
        public void offset(final int n, final int n2) {
            this.left += n;
            this.top += n2;
            this.right += n;
            this.bottom += n2;
        }
        
        public static void intersect(final Rect rect, final Rect rect2, final Rect rect3) {
            rect3.left = Math.max(rect.left, rect2.left);
            rect3.top = Math.max(rect.top, rect2.top);
            rect3.right = Math.min(rect.right, rect2.right);
            rect3.bottom = Math.min(rect.bottom, rect2.bottom);
        }
        
        @Override
        public String toString() {
            return "Rect: left = " + this.left + " top = " + this.top + " right = " + this.right + " bottom = " + this.bottom + ", width: " + (this.right - this.left) + ", height: " + (this.bottom - this.top);
        }
        
        Rect(final WindowsDisplay$1 focusAdapter) {
            this();
        }
    }
}
