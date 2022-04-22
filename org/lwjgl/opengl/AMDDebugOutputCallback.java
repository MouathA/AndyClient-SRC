package org.lwjgl.opengl;

import org.lwjgl.*;

public final class AMDDebugOutputCallback extends PointerWrapperAbstract
{
    private static final int GL_DEBUG_SEVERITY_HIGH_AMD = 37190;
    private static final int GL_DEBUG_SEVERITY_MEDIUM_AMD = 37191;
    private static final int GL_DEBUG_SEVERITY_LOW_AMD = 37192;
    private static final int GL_DEBUG_CATEGORY_API_ERROR_AMD = 37193;
    private static final int GL_DEBUG_CATEGORY_WINDOW_SYSTEM_AMD = 37194;
    private static final int GL_DEBUG_CATEGORY_DEPRECATION_AMD = 37195;
    private static final int GL_DEBUG_CATEGORY_UNDEFINED_BEHAVIOR_AMD = 37196;
    private static final int GL_DEBUG_CATEGORY_PERFORMANCE_AMD = 37197;
    private static final int GL_DEBUG_CATEGORY_SHADER_COMPILER_AMD = 37198;
    private static final int GL_DEBUG_CATEGORY_APPLICATION_AMD = 37199;
    private static final int GL_DEBUG_CATEGORY_OTHER_AMD = 37200;
    private static final long CALLBACK_POINTER;
    private final Handler handler;
    
    public AMDDebugOutputCallback() {
        this(new Handler() {
            public void handleMessage(final int n, final int n2, final int n3, final String s) {
                System.err.println("[LWJGL] AMD_debug_output message");
                System.err.println("\tID: " + n);
                String printUnknownToken = null;
                switch (n2) {
                    case 37193: {
                        printUnknownToken = "API ERROR";
                        break;
                    }
                    case 37194: {
                        printUnknownToken = "WINDOW SYSTEM";
                        break;
                    }
                    case 37195: {
                        printUnknownToken = "DEPRECATION";
                        break;
                    }
                    case 37196: {
                        printUnknownToken = "UNDEFINED BEHAVIOR";
                        break;
                    }
                    case 37197: {
                        printUnknownToken = "PERFORMANCE";
                        break;
                    }
                    case 37198: {
                        printUnknownToken = "SHADER COMPILER";
                        break;
                    }
                    case 37199: {
                        printUnknownToken = "APPLICATION";
                        break;
                    }
                    case 37200: {
                        printUnknownToken = "OTHER";
                        break;
                    }
                    default: {
                        printUnknownToken = this.printUnknownToken(n2);
                        break;
                    }
                }
                System.err.println("\tCategory: " + printUnknownToken);
                String printUnknownToken2 = null;
                switch (n3) {
                    case 37190: {
                        printUnknownToken2 = "HIGH";
                        break;
                    }
                    case 37191: {
                        printUnknownToken2 = "MEDIUM";
                        break;
                    }
                    case 37192: {
                        printUnknownToken2 = "LOW";
                        break;
                    }
                    default: {
                        printUnknownToken2 = this.printUnknownToken(n3);
                        break;
                    }
                }
                System.err.println("\tSeverity: " + printUnknownToken2);
                System.err.println("\tMessage: " + s);
            }
            
            private String printUnknownToken(final int n) {
                return "Unknown (0x" + Integer.toHexString(n).toUpperCase() + ")";
            }
        });
    }
    
    public AMDDebugOutputCallback(final Handler handler) {
        super(AMDDebugOutputCallback.CALLBACK_POINTER);
        this.handler = handler;
    }
    
    Handler getHandler() {
        return this.handler;
    }
    
    static {
        CALLBACK_POINTER = (long)Class.forName("org.lwjgl.opengl.CallbackUtil").getDeclaredMethod("getDebugOutputCallbackAMD", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
    }
    
    public interface Handler
    {
        void handleMessage(final int p0, final int p1, final int p2, final String p3);
    }
}
