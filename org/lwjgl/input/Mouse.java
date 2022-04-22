package org.lwjgl.input;

import java.nio.*;
import org.lwjgl.opengl.*;
import java.security.*;
import java.util.*;
import org.lwjgl.*;

public class Mouse
{
    public static final int EVENT_SIZE = 22;
    private static boolean created;
    private static ByteBuffer buttons;
    private static int x;
    private static int y;
    private static int absolute_x;
    private static int absolute_y;
    private static IntBuffer coord_buffer;
    private static int dx;
    private static int dy;
    private static int dwheel;
    private static boolean hasWheel;
    private static Cursor currentCursor;
    private static String[] buttonName;
    private static final Map buttonMap;
    private static boolean initialized;
    private static ByteBuffer readBuffer;
    private static int eventButton;
    private static boolean eventState;
    private static int event_dx;
    private static int event_dy;
    private static int event_dwheel;
    private static int event_x;
    private static int event_y;
    private static long event_nanos;
    private static int grab_x;
    private static int grab_y;
    private static int last_event_raw_x;
    private static int last_event_raw_y;
    private static final int BUFFER_SIZE = 50;
    private static boolean isGrabbed;
    private static InputImplementation implementation;
    private static final boolean emulateCursorAnimation;
    private static boolean clipMouseCoordinatesToWindow;
    
    private Mouse() {
    }
    
    public static Cursor getNativeCursor() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.currentCursor;
    }
    
    public static Cursor setNativeCursor(final Cursor currentCursor) throws LWJGLException {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if ((Cursor.getCapabilities() & 0x1) == 0x0) {
            throw new IllegalStateException("Mouse doesn't support native cursors");
        }
        final Cursor currentCursor2 = Mouse.currentCursor;
        Mouse.currentCursor = currentCursor;
        if (isCreated()) {
            if (Mouse.currentCursor != null) {
                Mouse.implementation.setNativeCursor(Mouse.currentCursor.getHandle());
                Mouse.currentCursor.setTimeout();
            }
            else {
                Mouse.implementation.setNativeCursor(null);
            }
        }
        // monitorexit(global_lock)
        return currentCursor2;
    }
    
    public static boolean isClipMouseCoordinatesToWindow() {
        return Mouse.clipMouseCoordinatesToWindow;
    }
    
    public static void setClipMouseCoordinatesToWindow(final boolean clipMouseCoordinatesToWindow) {
        Mouse.clipMouseCoordinatesToWindow = clipMouseCoordinatesToWindow;
    }
    
    public static void setCursorPosition(final int grab_x, final int grab_y) {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!isCreated()) {
            throw new IllegalStateException("Mouse is not created");
        }
        Mouse.event_x = grab_x;
        Mouse.x = grab_x;
        Mouse.event_y = grab_y;
        Mouse.y = grab_y;
        if (!isGrabbed() && (Cursor.getCapabilities() & 0x1) != 0x0) {
            Mouse.implementation.setCursorPosition(Mouse.x, Mouse.y);
        }
        else {
            Mouse.grab_x = grab_x;
            Mouse.grab_y = grab_y;
        }
    }
    // monitorexit(global_lock)
    
    private static void initialize() {
        Mouse.buttonName = new String[16];
        while (true) {
            Mouse.buttonName[0] = "BUTTON" + 0;
            Mouse.buttonMap.put(Mouse.buttonName[0], 0);
            int n = 0;
            ++n;
        }
    }
    
    private static void resetMouse() {
        final int n = 0;
        Mouse.dwheel = 0;
        Mouse.dy = n;
        Mouse.dx = n;
        Mouse.readBuffer.position(Mouse.readBuffer.limit());
    }
    
    static InputImplementation getImplementation() {
        return Mouse.implementation;
    }
    
    private static void create(final InputImplementation implementation) throws LWJGLException {
        if (Mouse.created) {
            return;
        }
        Mouse.initialized;
        (Mouse.implementation = implementation).createMouse();
        Mouse.hasWheel = Mouse.implementation.hasWheel();
        Mouse.created = true;
        Mouse.buttonCount = Mouse.implementation.getButtonCount();
        Mouse.buttons = BufferUtils.createByteBuffer(-1);
        Mouse.coord_buffer = BufferUtils.createIntBuffer(3);
        if (Mouse.currentCursor != null && Mouse.implementation.getNativeCursorCapabilities() != 0) {
            setNativeCursor(Mouse.currentCursor);
        }
        (Mouse.readBuffer = ByteBuffer.allocate(1100)).limit(0);
        setGrabbed(Mouse.isGrabbed);
    }
    
    public static void create() throws LWJGLException {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Display.isCreated()) {
            throw new IllegalStateException("Display must be created.");
        }
        create(OpenGLPackageAccess.createImplementation());
    }
    // monitorexit(global_lock)
    
    public static boolean isCreated() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.created;
    }
    
    public static void destroy() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Mouse.created) {
            // monitorexit(global_lock)
            return;
        }
        Mouse.created = false;
        Mouse.buttons = null;
        Mouse.coord_buffer = null;
        Mouse.implementation.destroyMouse();
    }
    // monitorexit(global_lock)
    
    public static void poll() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Mouse.created) {
            throw new IllegalStateException("Mouse must be created before you can poll it");
        }
        Mouse.implementation.pollMouse(Mouse.coord_buffer, Mouse.buttons);
        final int value = Mouse.coord_buffer.get(0);
        final int value2 = Mouse.coord_buffer.get(1);
        final int value3 = Mouse.coord_buffer.get(2);
        if (isGrabbed()) {
            Mouse.dx += value;
            Mouse.dy += value2;
            Mouse.x += value;
            Mouse.y += value2;
            Mouse.absolute_x += value;
            Mouse.absolute_y += value2;
        }
        else {
            Mouse.dx = value - Mouse.absolute_x;
            Mouse.dy = value2 - Mouse.absolute_y;
            Mouse.absolute_x = (Mouse.x = value);
            Mouse.absolute_y = (Mouse.y = value2);
        }
        if (Mouse.clipMouseCoordinatesToWindow) {
            Mouse.x = Math.min(Display.getWidth() - 1, Math.max(0, Mouse.x));
            Mouse.y = Math.min(Display.getHeight() - 1, Math.max(0, Mouse.y));
        }
        Mouse.dwheel += value3;
    }
    // monitorexit(global_lock)
    
    private static void read() {
        Mouse.readBuffer.compact();
        Mouse.implementation.readMouse(Mouse.readBuffer);
        Mouse.readBuffer.flip();
    }
    
    public static boolean isButtonDown(final int n) {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Mouse.created) {
            throw new IllegalStateException("Mouse must be created before you can poll the button state");
        }
        if (n >= -1 || n < 0) {
            // monitorexit(global_lock)
            return false;
        }
        // monitorexit(global_lock)
        return Mouse.buttons.get(n) == 1;
    }
    
    public static String getButtonName(final int n) {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (n >= Mouse.buttonName.length || n < 0) {
            // monitorexit(global_lock)
            return null;
        }
        // monitorexit(global_lock)
        return Mouse.buttonName[n];
    }
    
    public static int getButtonIndex(final String s) {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        final Integer n = Mouse.buttonMap.get(s);
        if (n == null) {
            // monitorexit(global_lock)
            return -1;
        }
        // monitorexit(global_lock)
        return n;
    }
    
    public static boolean next() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Mouse.created) {
            throw new IllegalStateException("Mouse must be created before you can read events");
        }
        if (Mouse.readBuffer.hasRemaining()) {
            Mouse.eventButton = Mouse.readBuffer.get();
            Mouse.eventState = (Mouse.readBuffer.get() != 0);
            if (isGrabbed()) {
                Mouse.event_dx = Mouse.readBuffer.getInt();
                Mouse.event_dy = Mouse.readBuffer.getInt();
                Mouse.event_x += Mouse.event_dx;
                Mouse.event_y += Mouse.event_dy;
                Mouse.last_event_raw_x = Mouse.event_x;
                Mouse.last_event_raw_y = Mouse.event_y;
            }
            else {
                final int int1 = Mouse.readBuffer.getInt();
                final int int2 = Mouse.readBuffer.getInt();
                Mouse.event_dx = int1 - Mouse.last_event_raw_x;
                Mouse.event_dy = int2 - Mouse.last_event_raw_y;
                Mouse.event_x = int1;
                Mouse.event_y = int2;
                Mouse.last_event_raw_x = int1;
                Mouse.last_event_raw_y = int2;
            }
            if (Mouse.clipMouseCoordinatesToWindow) {
                Mouse.event_x = Math.min(Display.getWidth() - 1, Math.max(0, Mouse.event_x));
                Mouse.event_y = Math.min(Display.getHeight() - 1, Math.max(0, Mouse.event_y));
            }
            Mouse.event_dwheel = Mouse.readBuffer.getInt();
            Mouse.event_nanos = Mouse.readBuffer.getLong();
            // monitorexit(global_lock)
            return true;
        }
        // monitorexit(global_lock)
        return false;
    }
    
    public static int getEventButton() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.eventButton;
    }
    
    public static boolean getEventButtonState() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.eventState;
    }
    
    public static int getEventDX() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.event_dx;
    }
    
    public static int getEventDY() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.event_dy;
    }
    
    public static int getEventX() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.event_x;
    }
    
    public static int getEventY() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.event_y;
    }
    
    public static int getEventDWheel() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.event_dwheel;
    }
    
    public static long getEventNanoseconds() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.event_nanos;
    }
    
    public static int getX() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.x;
    }
    
    public static int getY() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.y;
    }
    
    public static int getDX() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        final int dx = Mouse.dx;
        Mouse.dx = 0;
        // monitorexit(global_lock)
        return dx;
    }
    
    public static int getDY() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        final int dy = Mouse.dy;
        Mouse.dy = 0;
        // monitorexit(global_lock)
        return dy;
    }
    
    public static int getDWheel() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        final int dwheel = Mouse.dwheel;
        Mouse.dwheel = 0;
        // monitorexit(global_lock)
        return dwheel;
    }
    
    public static int getButtonCount() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return -1;
    }
    
    public static boolean hasWheel() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.hasWheel;
    }
    
    public static boolean isGrabbed() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Mouse.isGrabbed;
    }
    
    public static void setGrabbed(final boolean isGrabbed) {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        final boolean isGrabbed2 = Mouse.isGrabbed;
        Mouse.isGrabbed = isGrabbed;
        if (isCreated()) {
            if (isGrabbed && !isGrabbed2) {
                Mouse.grab_x = Mouse.x;
                Mouse.grab_y = Mouse.y;
            }
            else if (!isGrabbed && isGrabbed2 && (Cursor.getCapabilities() & 0x1) != 0x0) {
                Mouse.implementation.setCursorPosition(Mouse.grab_x, Mouse.grab_y);
            }
            Mouse.implementation.grabMouse(isGrabbed);
            Mouse.event_x = Mouse.x;
            Mouse.event_y = Mouse.y;
            Mouse.last_event_raw_x = Mouse.x;
            Mouse.last_event_raw_y = Mouse.y;
        }
    }
    // monitorexit(global_lock)
    
    public static void updateCursor() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (Mouse.emulateCursorAnimation && Mouse.currentCursor != null && Mouse.currentCursor.hasTimedOut() && isInsideWindow()) {
            Mouse.currentCursor.nextCursor();
            setNativeCursor(Mouse.currentCursor);
        }
    }
    // monitorexit(global_lock)
    
    static boolean getPrivilegedBoolean(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction(s) {
            final String val$property_name;
            
            public Boolean run() {
                return Boolean.getBoolean(this.val$property_name);
            }
            
            public Object run() {
                return this.run();
            }
        });
    }
    
    public static boolean isInsideWindow() {
        return Mouse.implementation.isInsideWindow();
    }
    
    static {
        buttonMap = new HashMap(16);
        emulateCursorAnimation = (LWJGLUtil.getPlatform() == 3 || LWJGLUtil.getPlatform() == 2);
        Mouse.clipMouseCoordinatesToWindow = !getPrivilegedBoolean("org.lwjgl.input.Mouse.allowNegativeMouseCoords");
    }
}
