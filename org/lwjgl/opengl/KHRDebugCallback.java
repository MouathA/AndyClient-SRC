package org.lwjgl.opengl;

import org.lwjgl.*;

public final class KHRDebugCallback extends PointerWrapperAbstract
{
    private static final int GL_DEBUG_SEVERITY_HIGH = 37190;
    private static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    private static final int GL_DEBUG_SEVERITY_LOW = 37192;
    private static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    private static final int GL_DEBUG_SOURCE_API = 33350;
    private static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    private static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    private static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    private static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
    private static final int GL_DEBUG_SOURCE_OTHER = 33355;
    private static final int GL_DEBUG_TYPE_ERROR = 33356;
    private static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    private static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    private static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
    private static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    private static final int GL_DEBUG_TYPE_OTHER = 33361;
    private static final int GL_DEBUG_TYPE_MARKER = 33384;
    private static final long CALLBACK_POINTER;
    private final Handler handler;
    
    public KHRDebugCallback() {
        this(new Handler() {
            public void handleMessage(final int n, final int n2, final int n3, final int n4, final String s) {
                System.err.println("[LWJGL] KHR_debug message");
                System.err.println("\tID: " + n3);
                String printUnknownToken = null;
                switch (n) {
                    case 33350: {
                        printUnknownToken = "API";
                        break;
                    }
                    case 33351: {
                        printUnknownToken = "WINDOW SYSTEM";
                        break;
                    }
                    case 33352: {
                        printUnknownToken = "SHADER COMPILER";
                        break;
                    }
                    case 33353: {
                        printUnknownToken = "THIRD PARTY";
                        break;
                    }
                    case 33354: {
                        printUnknownToken = "APPLICATION";
                        break;
                    }
                    case 33355: {
                        printUnknownToken = "OTHER";
                        break;
                    }
                    default: {
                        printUnknownToken = this.printUnknownToken(n);
                        break;
                    }
                }
                System.err.println("\tSource: " + printUnknownToken);
                String printUnknownToken2 = null;
                switch (n2) {
                    case 33356: {
                        printUnknownToken2 = "ERROR";
                        break;
                    }
                    case 33357: {
                        printUnknownToken2 = "DEPRECATED BEHAVIOR";
                        break;
                    }
                    case 33358: {
                        printUnknownToken2 = "UNDEFINED BEHAVIOR";
                        break;
                    }
                    case 33359: {
                        printUnknownToken2 = "PORTABILITY";
                        break;
                    }
                    case 33360: {
                        printUnknownToken2 = "PERFORMANCE";
                        break;
                    }
                    case 33361: {
                        printUnknownToken2 = "OTHER";
                        break;
                    }
                    case 33384: {
                        printUnknownToken2 = "MARKER";
                        break;
                    }
                    default: {
                        printUnknownToken2 = this.printUnknownToken(n2);
                        break;
                    }
                }
                System.err.println("\tType: " + printUnknownToken2);
                String printUnknownToken3 = null;
                switch (n4) {
                    case 37190: {
                        printUnknownToken3 = "HIGH";
                        break;
                    }
                    case 37191: {
                        printUnknownToken3 = "MEDIUM";
                        break;
                    }
                    case 37192: {
                        printUnknownToken3 = "LOW";
                        break;
                    }
                    case 33387: {
                        printUnknownToken3 = "NOTIFICATION";
                        break;
                    }
                    default: {
                        printUnknownToken3 = this.printUnknownToken(n4);
                        break;
                    }
                }
                System.err.println("\tSeverity: " + printUnknownToken3);
                System.err.println("\tMessage: " + s);
            }
            
            private String printUnknownToken(final int n) {
                return "Unknown (0x" + Integer.toHexString(n).toUpperCase() + ")";
            }
        });
    }
    
    public KHRDebugCallback(final Handler handler) {
        super(KHRDebugCallback.CALLBACK_POINTER);
        this.handler = handler;
    }
    
    Handler getHandler() {
        return this.handler;
    }
    
    static {
        CALLBACK_POINTER = (long)Class.forName("org.lwjgl.opengl.CallbackUtil").getDeclaredMethod("getDebugCallbackKHR", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
    }
    
    public interface Handler
    {
        void handleMessage(final int p0, final int p1, final int p2, final int p3, final String p4);
    }
}
