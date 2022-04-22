package org.lwjgl.input;

import java.nio.*;
import org.lwjgl.opengl.*;
import java.util.*;
import org.lwjgl.*;
import java.lang.reflect.*;

public class Keyboard
{
    public static final int EVENT_SIZE = 18;
    public static final int CHAR_NONE = 0;
    public static final int KEY_NONE = 0;
    public static final int KEY_ESCAPE = 1;
    public static final int KEY_1 = 2;
    public static final int KEY_2 = 3;
    public static final int KEY_3 = 4;
    public static final int KEY_4 = 5;
    public static final int KEY_5 = 6;
    public static final int KEY_6 = 7;
    public static final int KEY_7 = 8;
    public static final int KEY_8 = 9;
    public static final int KEY_9 = 10;
    public static final int KEY_0 = 11;
    public static final int KEY_MINUS = 12;
    public static final int KEY_EQUALS = 13;
    public static final int KEY_BACK = 14;
    public static final int KEY_TAB = 15;
    public static final int KEY_Q = 16;
    public static final int KEY_W = 17;
    public static final int KEY_E = 18;
    public static final int KEY_R = 19;
    public static final int KEY_T = 20;
    public static final int KEY_Y = 21;
    public static final int KEY_U = 22;
    public static final int KEY_I = 23;
    public static final int KEY_O = 24;
    public static final int KEY_P = 25;
    public static final int KEY_LBRACKET = 26;
    public static final int KEY_RBRACKET = 27;
    public static final int KEY_RETURN = 28;
    public static final int KEY_LCONTROL = 29;
    public static final int KEY_A = 30;
    public static final int KEY_S = 31;
    public static final int KEY_D = 32;
    public static final int KEY_F = 33;
    public static final int KEY_G = 34;
    public static final int KEY_H = 35;
    public static final int KEY_J = 36;
    public static final int KEY_K = 37;
    public static final int KEY_L = 38;
    public static final int KEY_SEMICOLON = 39;
    public static final int KEY_APOSTROPHE = 40;
    public static final int KEY_GRAVE = 41;
    public static final int KEY_LSHIFT = 42;
    public static final int KEY_BACKSLASH = 43;
    public static final int KEY_Z = 44;
    public static final int KEY_X = 45;
    public static final int KEY_C = 46;
    public static final int KEY_V = 47;
    public static final int KEY_B = 48;
    public static final int KEY_N = 49;
    public static final int KEY_M = 50;
    public static final int KEY_COMMA = 51;
    public static final int KEY_PERIOD = 52;
    public static final int KEY_SLASH = 53;
    public static final int KEY_RSHIFT = 54;
    public static final int KEY_MULTIPLY = 55;
    public static final int KEY_LMENU = 56;
    public static final int KEY_SPACE = 57;
    public static final int KEY_CAPITAL = 58;
    public static final int KEY_F1 = 59;
    public static final int KEY_F2 = 60;
    public static final int KEY_F3 = 61;
    public static final int KEY_F4 = 62;
    public static final int KEY_F5 = 63;
    public static final int KEY_F6 = 64;
    public static final int KEY_F7 = 65;
    public static final int KEY_F8 = 66;
    public static final int KEY_F9 = 67;
    public static final int KEY_F10 = 68;
    public static final int KEY_NUMLOCK = 69;
    public static final int KEY_SCROLL = 70;
    public static final int KEY_NUMPAD7 = 71;
    public static final int KEY_NUMPAD8 = 72;
    public static final int KEY_NUMPAD9 = 73;
    public static final int KEY_SUBTRACT = 74;
    public static final int KEY_NUMPAD4 = 75;
    public static final int KEY_NUMPAD5 = 76;
    public static final int KEY_NUMPAD6 = 77;
    public static final int KEY_ADD = 78;
    public static final int KEY_NUMPAD1 = 79;
    public static final int KEY_NUMPAD2 = 80;
    public static final int KEY_NUMPAD3 = 81;
    public static final int KEY_NUMPAD0 = 82;
    public static final int KEY_DECIMAL = 83;
    public static final int KEY_F11 = 87;
    public static final int KEY_F12 = 88;
    public static final int KEY_F13 = 100;
    public static final int KEY_F14 = 101;
    public static final int KEY_F15 = 102;
    public static final int KEY_F16 = 103;
    public static final int KEY_F17 = 104;
    public static final int KEY_F18 = 105;
    public static final int KEY_KANA = 112;
    public static final int KEY_F19 = 113;
    public static final int KEY_CONVERT = 121;
    public static final int KEY_NOCONVERT = 123;
    public static final int KEY_YEN = 125;
    public static final int KEY_NUMPADEQUALS = 141;
    public static final int KEY_CIRCUMFLEX = 144;
    public static final int KEY_AT = 145;
    public static final int KEY_COLON = 146;
    public static final int KEY_UNDERLINE = 147;
    public static final int KEY_KANJI = 148;
    public static final int KEY_STOP = 149;
    public static final int KEY_AX = 150;
    public static final int KEY_UNLABELED = 151;
    public static final int KEY_NUMPADENTER = 156;
    public static final int KEY_RCONTROL = 157;
    public static final int KEY_SECTION = 167;
    public static final int KEY_NUMPADCOMMA = 179;
    public static final int KEY_DIVIDE = 181;
    public static final int KEY_SYSRQ = 183;
    public static final int KEY_RMENU = 184;
    public static final int KEY_FUNCTION = 196;
    public static final int KEY_PAUSE = 197;
    public static final int KEY_HOME = 199;
    public static final int KEY_UP = 200;
    public static final int KEY_PRIOR = 201;
    public static final int KEY_LEFT = 203;
    public static final int KEY_RIGHT = 205;
    public static final int KEY_END = 207;
    public static final int KEY_DOWN = 208;
    public static final int KEY_NEXT = 209;
    public static final int KEY_INSERT = 210;
    public static final int KEY_DELETE = 211;
    public static final int KEY_CLEAR = 218;
    public static final int KEY_LMETA = 219;
    @Deprecated
    public static final int KEY_LWIN = 219;
    public static final int KEY_RMETA = 220;
    @Deprecated
    public static final int KEY_RWIN = 220;
    public static final int KEY_APPS = 221;
    public static final int KEY_POWER = 222;
    public static final int KEY_SLEEP = 223;
    public static final int KEYBOARD_SIZE = 256;
    private static final int BUFFER_SIZE = 50;
    private static final Map keyMap;
    private static int counter;
    private static final int keyCount;
    private static boolean created;
    private static boolean repeat_enabled;
    private static final ByteBuffer keyDownBuffer;
    private static ByteBuffer readBuffer;
    private static KeyEvent current_event;
    private static KeyEvent tmp_event;
    private static boolean initialized;
    private static InputImplementation implementation;
    
    private Keyboard() {
    }
    
    private static void initialize() {
        if (Keyboard.initialized) {
            return;
        }
        Keyboard.initialized = true;
    }
    
    private static void create(final InputImplementation implementation) throws LWJGLException {
        if (Keyboard.created) {
            return;
        }
        Keyboard.initialized;
        (Keyboard.implementation = implementation).createKeyboard();
        Keyboard.created = true;
        Keyboard.readBuffer = ByteBuffer.allocate(900);
    }
    
    public static void create() throws LWJGLException {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Display.isCreated()) {
            throw new IllegalStateException("Display must be created.");
        }
        create(OpenGLPackageAccess.createImplementation());
    }
    // monitorexit(global_lock)
    
    private static void reset() {
        Keyboard.readBuffer.limit(0);
        while (0 < Keyboard.keyDownBuffer.remaining()) {
            Keyboard.keyDownBuffer.put(0, (byte)0);
            int n = 0;
            ++n;
        }
        KeyEvent.access$100(Keyboard.current_event);
    }
    
    public static boolean isCreated() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Keyboard.created;
    }
    
    public static void destroy() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Keyboard.created) {
            // monitorexit(global_lock)
            return;
        }
        Keyboard.created = false;
        Keyboard.implementation.destroyKeyboard();
    }
    // monitorexit(global_lock)
    
    public static void poll() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Keyboard.created) {
            throw new IllegalStateException("Keyboard must be created before you can poll the device");
        }
        Keyboard.implementation.pollKeyboard(Keyboard.keyDownBuffer);
    }
    // monitorexit(global_lock)
    
    private static void read() {
        Keyboard.readBuffer.compact();
        Keyboard.implementation.readKeyboard(Keyboard.readBuffer);
        Keyboard.readBuffer.flip();
    }
    
    public static boolean isKeyDown(final int n) {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Keyboard.created) {
            throw new IllegalStateException("Keyboard must be created before you can query key state");
        }
        // monitorexit(global_lock)
        return Keyboard.keyDownBuffer.get(n) != 0;
    }
    
    public static synchronized String getKeyName(final int n) {
        return Keyboard.keyName[n];
    }
    
    public static synchronized int getKeyIndex(final String s) {
        final Integer n = Keyboard.keyMap.get(s);
        if (n == null) {
            return 0;
        }
        return n;
    }
    
    public static int getNumKeyboardEvents() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Keyboard.created) {
            throw new IllegalStateException("Keyboard must be created before you can read events");
        }
        final int position = Keyboard.readBuffer.position();
        while (readNext(Keyboard.tmp_event) && (!KeyEvent.access$200(Keyboard.tmp_event) || Keyboard.repeat_enabled)) {
            int n = 0;
            ++n;
        }
        Keyboard.readBuffer.position(position);
        // monitorexit(global_lock)
        return 0;
    }
    
    public static boolean next() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Keyboard.created) {
            throw new IllegalStateException("Keyboard must be created before you can read events");
        }
        boolean next;
        while ((next = readNext(Keyboard.current_event)) && KeyEvent.access$200(Keyboard.current_event) && !Keyboard.repeat_enabled) {}
        // monitorexit(global_lock)
        return next;
    }
    
    public static void enableRepeatEvents(final boolean repeat_enabled) {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        Keyboard.repeat_enabled = repeat_enabled;
    }
    // monitorexit(global_lock)
    
    public static boolean areRepeatEventsEnabled() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return Keyboard.repeat_enabled;
    }
    
    private static boolean readNext(final KeyEvent keyEvent) {
        if (Keyboard.readBuffer.hasRemaining()) {
            KeyEvent.access$302(keyEvent, Keyboard.readBuffer.getInt() & 0xFF);
            KeyEvent.access$402(keyEvent, Keyboard.readBuffer.get() != 0);
            KeyEvent.access$502(keyEvent, Keyboard.readBuffer.getInt());
            KeyEvent.access$602(keyEvent, Keyboard.readBuffer.getLong());
            KeyEvent.access$202(keyEvent, Keyboard.readBuffer.get() == 1);
            return true;
        }
        return false;
    }
    
    public static int getKeyCount() {
        return Keyboard.keyCount;
    }
    
    public static char getEventCharacter() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return (char)KeyEvent.access$500(Keyboard.current_event);
    }
    
    public static int getEventKey() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return KeyEvent.access$300(Keyboard.current_event);
    }
    
    public static boolean getEventKeyState() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return KeyEvent.access$400(Keyboard.current_event);
    }
    
    public static long getEventNanoseconds() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return KeyEvent.access$600(Keyboard.current_event);
    }
    
    public static boolean isRepeatEvent() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        // monitorexit(global_lock)
        return KeyEvent.access$200(Keyboard.current_event);
    }
    
    static {
        Keyboard.keyName = new String[256];
        keyMap = new HashMap(253);
        final Field[] fields = Keyboard.class.getFields();
        while (0 < fields.length) {
            final Field field = fields[0];
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getType().equals(Integer.TYPE) && field.getName().startsWith("KEY_") && !field.getName().endsWith("WIN")) {
                final int int1 = field.getInt(null);
                final String substring = field.getName().substring(4);
                Keyboard.keyName[int1] = substring;
                Keyboard.keyMap.put(substring, int1);
                ++Keyboard.counter;
            }
            int n = 0;
            ++n;
        }
        keyCount = Keyboard.counter;
        keyDownBuffer = BufferUtils.createByteBuffer(256);
        Keyboard.current_event = new KeyEvent(null);
        Keyboard.tmp_event = new KeyEvent(null);
    }
    
    private static final class KeyEvent
    {
        private int character;
        private int key;
        private boolean state;
        private long nanos;
        private boolean repeat;
        
        private KeyEvent() {
        }
        
        private void reset() {
            this.character = 0;
            this.key = 0;
            this.state = false;
            this.repeat = false;
        }
        
        KeyEvent(final Keyboard$1 object) {
            this();
        }
        
        static void access$100(final KeyEvent keyEvent) {
            keyEvent.reset();
        }
        
        static boolean access$200(final KeyEvent keyEvent) {
            return keyEvent.repeat;
        }
        
        static int access$302(final KeyEvent keyEvent, final int key) {
            return keyEvent.key = key;
        }
        
        static boolean access$402(final KeyEvent keyEvent, final boolean state) {
            return keyEvent.state = state;
        }
        
        static int access$502(final KeyEvent keyEvent, final int character) {
            return keyEvent.character = character;
        }
        
        static long access$602(final KeyEvent keyEvent, final long nanos) {
            return keyEvent.nanos = nanos;
        }
        
        static boolean access$202(final KeyEvent keyEvent, final boolean repeat) {
            return keyEvent.repeat = repeat;
        }
        
        static int access$500(final KeyEvent keyEvent) {
            return keyEvent.character;
        }
        
        static int access$300(final KeyEvent keyEvent) {
            return keyEvent.key;
        }
        
        static boolean access$400(final KeyEvent keyEvent) {
            return keyEvent.state;
        }
        
        static long access$600(final KeyEvent keyEvent) {
            return keyEvent.nanos;
        }
    }
}
