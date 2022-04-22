package com.sun.jna.platform;

import java.awt.*;
import com.sun.jna.*;
import com.sun.jna.platform.unix.*;
import com.sun.jna.platform.win32.*;

public class KeyboardUtils
{
    static final NativeKeyboardUtils INSTANCE;
    
    public static boolean isPressed(final int n, final int n2) {
        return KeyboardUtils.INSTANCE.isPressed(n, n2);
    }
    
    public static boolean isPressed(final int n) {
        return KeyboardUtils.INSTANCE.isPressed(n);
    }
    
    static {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("KeyboardUtils requires a keyboard");
        }
        if (Platform.isWindows()) {
            INSTANCE = new W32KeyboardUtils(null);
        }
        else {
            if (Platform.isMac()) {
                INSTANCE = new MacKeyboardUtils(null);
                throw new UnsupportedOperationException("No support (yet) for " + System.getProperty("os.name"));
            }
            INSTANCE = new X11KeyboardUtils(null);
        }
    }
    
    private static class X11KeyboardUtils extends NativeKeyboardUtils
    {
        private X11KeyboardUtils() {
            super(null);
        }
        
        private int toKeySym(final int n, final int n2) {
            if (n >= 65 && n <= 90) {
                return 97 + (n - 65);
            }
            if (n >= 48 && n <= 57) {
                return 48 + (n - 48);
            }
            if (n == 16) {
                if ((n2 & 0x3) != 0x0) {
                    return 65505;
                }
                return 65505;
            }
            else if (n == 17) {
                if ((n2 & 0x3) != 0x0) {
                    return 65508;
                }
                return 65507;
            }
            else if (n == 18) {
                if ((n2 & 0x3) != 0x0) {
                    return 65514;
                }
                return 65513;
            }
            else {
                if (n != 157) {
                    return 0;
                }
                if ((n2 & 0x3) != 0x0) {
                    return 65512;
                }
                return 65511;
            }
        }
        
        @Override
        public boolean isPressed(final int n, final int n2) {
            final X11 instance = X11.INSTANCE;
            final X11.Display xOpenDisplay = instance.XOpenDisplay(null);
            if (xOpenDisplay == null) {
                throw new Error("Can't open X Display");
            }
            final byte[] array = new byte[32];
            instance.XQueryKeymap(xOpenDisplay, array);
            final int keySym = this.toKeySym(n, n2);
            while (5 < 256) {
                if ((array[0] & 0x20) != 0x0 && instance.XKeycodeToKeysym(xOpenDisplay, (byte)5, 0).intValue() == keySym) {
                    instance.XCloseDisplay(xOpenDisplay);
                    return true;
                }
                int n3 = 0;
                ++n3;
            }
            instance.XCloseDisplay(xOpenDisplay);
            return false;
        }
        
        X11KeyboardUtils(final KeyboardUtils$1 object) {
            this();
        }
    }
    
    private abstract static class NativeKeyboardUtils
    {
        private NativeKeyboardUtils() {
        }
        
        public abstract boolean isPressed(final int p0, final int p1);
        
        public boolean isPressed(final int n) {
            return this.isPressed(n, 0);
        }
        
        NativeKeyboardUtils(final KeyboardUtils$1 object) {
            this();
        }
    }
    
    private static class MacKeyboardUtils extends NativeKeyboardUtils
    {
        private MacKeyboardUtils() {
            super(null);
        }
        
        @Override
        public boolean isPressed(final int n, final int n2) {
            return false;
        }
        
        MacKeyboardUtils(final KeyboardUtils$1 object) {
            this();
        }
    }
    
    private static class W32KeyboardUtils extends NativeKeyboardUtils
    {
        private W32KeyboardUtils() {
            super(null);
        }
        
        private int toNative(final int n, final int n2) {
            if ((n >= 65 && n <= 90) || (n >= 48 && n <= 57)) {
                return n;
            }
            if (n == 16) {
                if ((n2 & 0x3) != 0x0) {
                    return 161;
                }
                if ((n2 & 0x2) != 0x0) {
                    return 160;
                }
                return 16;
            }
            else if (n == 17) {
                if ((n2 & 0x3) != 0x0) {
                    return 163;
                }
                if ((n2 & 0x2) != 0x0) {
                    return 162;
                }
                return 17;
            }
            else {
                if (n != 18) {
                    return 0;
                }
                if ((n2 & 0x3) != 0x0) {
                    return 165;
                }
                if ((n2 & 0x2) != 0x0) {
                    return 164;
                }
                return 18;
            }
        }
        
        @Override
        public boolean isPressed(final int n, final int n2) {
            return (User32.INSTANCE.GetAsyncKeyState(this.toNative(n, n2)) & 0x8000) != 0x0;
        }
        
        W32KeyboardUtils(final KeyboardUtils$1 object) {
            this();
        }
    }
}
