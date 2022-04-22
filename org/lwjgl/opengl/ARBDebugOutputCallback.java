package org.lwjgl.opengl;

import org.lwjgl.*;

public final class ARBDebugOutputCallback extends PointerWrapperAbstract
{
    private static final int GL_DEBUG_SEVERITY_HIGH_ARB = 37190;
    private static final int GL_DEBUG_SEVERITY_MEDIUM_ARB = 37191;
    private static final int GL_DEBUG_SEVERITY_LOW_ARB = 37192;
    private static final int GL_DEBUG_SOURCE_API_ARB = 33350;
    private static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM_ARB = 33351;
    private static final int GL_DEBUG_SOURCE_SHADER_COMPILER_ARB = 33352;
    private static final int GL_DEBUG_SOURCE_THIRD_PARTY_ARB = 33353;
    private static final int GL_DEBUG_SOURCE_APPLICATION_ARB = 33354;
    private static final int GL_DEBUG_SOURCE_OTHER_ARB = 33355;
    private static final int GL_DEBUG_TYPE_ERROR_ARB = 33356;
    private static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_ARB = 33357;
    private static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_ARB = 33358;
    private static final int GL_DEBUG_TYPE_PORTABILITY_ARB = 33359;
    private static final int GL_DEBUG_TYPE_PERFORMANCE_ARB = 33360;
    private static final int GL_DEBUG_TYPE_OTHER_ARB = 33361;
    private static final long CALLBACK_POINTER;
    private final Handler handler;
    
    public ARBDebugOutputCallback() {
        this(new Handler() {
            public void handleMessage(final int n, final int n2, final int n3, final int n4, final String s) {
                System.err.println("[LWJGL] ARB_debug_output message");
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
    
    public ARBDebugOutputCallback(final Handler handler) {
        super(ARBDebugOutputCallback.CALLBACK_POINTER);
        this.handler = handler;
    }
    
    Handler getHandler() {
        return this.handler;
    }
    
    static {
        CALLBACK_POINTER = (long)Class.forName("org.lwjgl.opengl.CallbackUtil").getDeclaredMethod("getDebugOutputCallbackARB", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
    }
    
    public interface Handler
    {
        void handleMessage(final int p0, final int p1, final int p2, final int p3, final String p4);
    }
}
