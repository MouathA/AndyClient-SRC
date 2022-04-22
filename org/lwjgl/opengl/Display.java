package org.lwjgl.opengl;

import java.awt.*;
import java.util.*;
import org.lwjgl.*;
import java.nio.*;
import org.lwjgl.input.*;
import java.security.*;
import java.awt.event.*;

public final class Display
{
    private static final Thread shutdown_hook;
    private static final DisplayImplementation display_impl;
    private static final DisplayMode initial_mode;
    private static Canvas parent;
    private static DisplayMode current_mode;
    private static ByteBuffer[] cached_icons;
    private static String title;
    private static boolean fullscreen;
    private static int swap_interval;
    private static DrawableLWJGL drawable;
    private static boolean window_created;
    private static boolean parent_resized;
    private static boolean window_resized;
    private static boolean window_resizable;
    private static float r;
    private static float g;
    private static float b;
    private static final ComponentListener component_listener;
    
    public static Drawable getDrawable() {
        return Display.drawable;
    }
    
    private static DisplayImplementation createDisplayImplementation() {
        switch (LWJGLUtil.getPlatform()) {
            case 1: {
                return new LinuxDisplay();
            }
            case 3: {
                return new WindowsDisplay();
            }
            case 2: {
                return new MacOSXDisplay();
            }
            default: {
                throw new IllegalStateException("Unsupported platform");
            }
        }
    }
    
    private Display() {
    }
    
    public static DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        final DisplayMode[] availableDisplayModes = Display.display_impl.getAvailableDisplayModes();
        if (availableDisplayModes == null) {
            // monitorexit(lock)
            return new DisplayMode[0];
        }
        final HashSet set = new HashSet(availableDisplayModes.length);
        set.addAll((Collection)Arrays.asList(availableDisplayModes));
        final DisplayMode[] array2 = new DisplayMode[set.size()];
        set.toArray((Object[])array2);
        LWJGLUtil.log("Removed " + (availableDisplayModes.length - array2.length) + " duplicate displaymodes");
        // monitorexit(lock)
        return array2;
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        return Display.initial_mode;
    }
    
    public static DisplayMode getDisplayMode() {
        return Display.current_mode;
    }
    
    public static void setDisplayMode(final DisplayMode p0) throws LWJGLException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: astore_1       
        //     5: monitorenter   
        //     6: aload_0        
        //     7: ifnonnull       20
        //    10: new             Ljava/lang/NullPointerException;
        //    13: dup            
        //    14: ldc             "mode must be non-null"
        //    16: invokespecial   java/lang/NullPointerException.<init>:(Ljava/lang/String;)V
        //    19: athrow         
        //    20: invokestatic    org/lwjgl/opengl/Display.isFullscreen:()Z
        //    23: istore_2       
        //    24: aload_0        
        //    25: putstatic       org/lwjgl/opengl/Display.current_mode:Lorg/lwjgl/opengl/DisplayMode;
        //    28: invokestatic    org/lwjgl/opengl/Display.isCreated:()Z
        //    31: ifeq            80
        //    34: iload_2        
        //    35: ifeq            55
        //    38: invokestatic    org/lwjgl/opengl/Display.isFullscreen:()Z
        //    41: ifne            55
        //    44: getstatic       org/lwjgl/opengl/Display.display_impl:Lorg/lwjgl/opengl/DisplayImplementation;
        //    47: invokeinterface org/lwjgl/opengl/DisplayImplementation.resetDisplayMode:()V
        //    52: goto            58
        //    55: invokestatic    org/lwjgl/opengl/Display.isFullscreen:()Z
        //    58: goto            80
        //    61: astore_3       
        //    62: getstatic       org/lwjgl/opengl/Display.drawable:Lorg/lwjgl/opengl/DrawableLWJGL;
        //    65: invokeinterface org/lwjgl/opengl/DrawableLWJGL.destroy:()V
        //    70: getstatic       org/lwjgl/opengl/Display.display_impl:Lorg/lwjgl/opengl/DisplayImplementation;
        //    73: invokeinterface org/lwjgl/opengl/DisplayImplementation.resetDisplayMode:()V
        //    78: aload_3        
        //    79: athrow         
        //    80: aload_1        
        //    81: monitorexit    
        //    82: goto            92
        //    85: astore          4
        //    87: aload_1        
        //    88: monitorexit    
        //    89: aload           4
        //    91: athrow         
        //    92: return         
        //    Exceptions:
        //  throws org.lwjgl.LWJGLException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0080 (coming from #0058).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static DisplayMode getEffectiveMode() {
        return (!isFullscreen() && Display.parent != null) ? new DisplayMode(Display.parent.getWidth(), Display.parent.getHeight()) : Display.current_mode;
    }
    
    private static int getWindowX() {
        if (isFullscreen() || Display.parent != null) {
            return 0;
        }
        if (-1 == -1) {
            return Math.max(0, (Display.initial_mode.getWidth() - Display.current_mode.getWidth()) / 2);
        }
        return -1;
    }
    
    private static int getWindowY() {
        if (isFullscreen() || Display.parent != null) {
            return 0;
        }
        if (-1 == -1) {
            return Math.max(0, (Display.initial_mode.getHeight() - Display.current_mode.getHeight()) / 2);
        }
        return -1;
    }
    
    private static void createWindow() throws LWJGLException {
        if (Display.window_created) {
            return;
        }
        final Canvas canvas = isFullscreen() ? null : Display.parent;
        if (canvas != null && !canvas.isDisplayable()) {
            throw new LWJGLException("Parent.isDisplayable() must be true");
        }
        if (canvas != null) {
            canvas.addComponentListener(Display.component_listener);
        }
        Display.display_impl.createWindow(Display.drawable, getEffectiveMode(), canvas, getWindowX(), getWindowY());
        Display.window_created = true;
        Display.width = getDisplayMode().getWidth();
        Display.height = getDisplayMode().getHeight();
        setTitle(Display.title);
        if (Display.cached_icons != null) {
            setIcon(Display.cached_icons);
        }
        else {
            setIcon(new ByteBuffer[] { LWJGLUtil.LWJGLIcon32x32, LWJGLUtil.LWJGLIcon16x16 });
        }
    }
    
    private static void releaseDrawable() {
        final Context context = Display.drawable.getContext();
        if (context != null && context.isCurrent()) {
            context.releaseCurrent();
            context.releaseDrawable();
        }
    }
    
    private static void destroyWindow() {
        if (!Display.window_created) {
            return;
        }
        if (Display.parent != null) {
            Display.parent.removeComponentListener(Display.component_listener);
        }
        Mouse.isCreated();
        Keyboard.isCreated();
        Display.display_impl.destroyWindow();
        Display.window_created = false;
    }
    
    private static void switchDisplayMode() throws LWJGLException {
        if (!Display.current_mode.isFullscreenCapable()) {
            throw new IllegalStateException("Only modes acquired from getAvailableDisplayModes() can be used for fullscreen display");
        }
        Display.display_impl.switchDisplayMode(Display.current_mode);
    }
    
    public static void setDisplayConfiguration(final float n, final float n2, final float n3) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new LWJGLException("Display not yet created.");
        }
        if (n2 < -1.0f || n2 > 1.0f) {
            throw new IllegalArgumentException("Invalid brightness value");
        }
        if (n3 < 0.0f) {
            throw new IllegalArgumentException("Invalid contrast value");
        }
        final int gammaRampLength = Display.display_impl.getGammaRampLength();
        if (gammaRampLength == 0) {
            throw new LWJGLException("Display configuration not supported");
        }
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(gammaRampLength);
        while (0 < gammaRampLength) {
            float n4 = ((float)Math.pow(0 / (float)(gammaRampLength - 1), n) + n2 - 0.5f) * n3 + 0.5f;
            if (n4 > 1.0f) {
                n4 = 1.0f;
            }
            else if (n4 < 0.0f) {
                n4 = 0.0f;
            }
            floatBuffer.put(0, n4);
            int n5 = 0;
            ++n5;
        }
        Display.display_impl.setGammaRamp(floatBuffer);
        LWJGLUtil.log("Gamma set, gamma = " + n + ", brightness = " + n2 + ", contrast = " + n3);
    }
    // monitorexit(lock)
    
    public static void sync(final int n) {
        Sync.sync(n);
    }
    
    public static String getTitle() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return Display.title;
    }
    
    public static Canvas getParent() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return Display.parent;
    }
    
    public static void setParent(final Canvas parent) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        if (Display.parent != parent) {
            Display.parent = parent;
            if (!isCreated()) {
                // monitorexit(lock)
                return;
            }
            if (!isFullscreen()) {
                Display.display_impl.resetDisplayMode();
            }
        }
    }
    // monitorexit(lock)
    
    public static void setFullscreen(final boolean b) throws LWJGLException {
        setDisplayModeAndFullscreenInternal(b, Display.current_mode);
    }
    
    public static void setDisplayModeAndFullscreen(final DisplayMode displayMode) throws LWJGLException {
        setDisplayModeAndFullscreenInternal(displayMode.isFullscreenCapable(), displayMode);
    }
    
    private static void setDisplayModeAndFullscreenInternal(final boolean fullscreen, final DisplayMode current_mode) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        if (current_mode == null) {
            throw new NullPointerException("mode must be non-null");
        }
        final DisplayMode current_mode2 = Display.current_mode;
        Display.current_mode = current_mode;
        final boolean fullscreen2 = isFullscreen();
        Display.fullscreen = fullscreen;
        if (fullscreen2 != isFullscreen() || !current_mode.equals(current_mode2)) {
            if (!isCreated()) {
                // monitorexit(lock)
                return;
            }
            if (!isFullscreen()) {
                Display.display_impl.resetDisplayMode();
            }
        }
    }
    // monitorexit(lock)
    
    public static boolean isFullscreen() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return Display.fullscreen && Display.current_mode.isFullscreenCapable();
    }
    
    public static void setTitle(String title) {
        // monitorenter(lock = GlobalLock.lock)
        if (title == null) {
            title = "";
        }
        Display.title = title;
        if (isCreated()) {
            Display.display_impl.setTitle(Display.title);
        }
    }
    // monitorexit(lock)
    
    public static boolean isCloseRequested() {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new IllegalStateException("Cannot determine close requested state of uncreated window");
        }
        // monitorexit(lock)
        return Display.display_impl.isCloseRequested();
    }
    
    public static boolean isVisible() {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new IllegalStateException("Cannot determine minimized state of uncreated window");
        }
        // monitorexit(lock)
        return Display.display_impl.isVisible();
    }
    
    public static boolean isActive() {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new IllegalStateException("Cannot determine focused state of uncreated window");
        }
        // monitorexit(lock)
        return Display.display_impl.isActive();
    }
    
    public static boolean isDirty() {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new IllegalStateException("Cannot determine dirty state of uncreated window");
        }
        // monitorexit(lock)
        return Display.display_impl.isDirty();
    }
    
    public static void processMessages() {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new IllegalStateException("Display not created");
        }
        Display.display_impl.update();
    }
    // monitorexit(lock)
    
    public static void swapBuffers() throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new IllegalStateException("Display not created");
        }
        if (LWJGLUtil.DEBUG) {
            Display.drawable.checkGLError();
        }
        Display.drawable.swapBuffers();
    }
    // monitorexit(lock)
    
    public static void update() {
        update(true);
    }
    
    public static void update(final boolean b) {
        // monitorenter(lock = GlobalLock.lock)
        if (!isCreated()) {
            throw new IllegalStateException("Display not created");
        }
        if (Display.display_impl.isVisible() || Display.display_impl.isDirty()) {}
        Display.window_resized = (!isFullscreen() && Display.parent == null && Display.display_impl.wasResized());
        if (Display.window_resized) {
            Display.width = Display.display_impl.getWidth();
            Display.height = Display.display_impl.getHeight();
        }
        if (Display.parent_resized) {
            Display.parent_resized = false;
            Display.window_resized = true;
        }
    }
    // monitorexit(lock)
    
    static void pollDevices() {
        Mouse.isCreated();
        Keyboard.isCreated();
        Controllers.isCreated();
    }
    
    public static void releaseContext() throws LWJGLException {
        Display.drawable.releaseContext();
    }
    
    public static boolean isCurrent() throws LWJGLException {
        return Display.drawable.isCurrent();
    }
    
    public static void makeCurrent() throws LWJGLException {
        Display.drawable.makeCurrent();
    }
    
    private static void removeShutdownHook() {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            public Object run() {
                Runtime.getRuntime().removeShutdownHook(Display.access$200());
                return null;
            }
        });
    }
    
    private static void registerShutdownHook() {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            public Object run() {
                Runtime.getRuntime().addShutdownHook(Display.access$200());
                return null;
            }
        });
    }
    
    public static void create() throws LWJGLException {
        create(new PixelFormat());
    }
    
    public static void create(final PixelFormat pixelFormat) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        create(pixelFormat, null, null);
    }
    // monitorexit(lock)
    
    public static void create(final PixelFormat pixelFormat, final Drawable drawable) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        create(pixelFormat, drawable, null);
    }
    // monitorexit(lock)
    
    public static void create(final PixelFormat pixelFormat, final ContextAttribs contextAttribs) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        create(pixelFormat, null, contextAttribs);
    }
    // monitorexit(lock)
    
    public static void create(final PixelFormat pixelFormat, final Drawable drawable, final ContextAttribs contextAttribs) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        if (isCreated()) {
            throw new IllegalStateException("Only one LWJGL context may be instantiated at any one time.");
        }
        if (pixelFormat == null) {
            throw new NullPointerException("pixel_format cannot be null");
        }
        isFullscreen();
        final DrawableGL drawable2 = new DrawableGL() {
            @Override
            public void destroy() {
                // monitorenter(lock = GlobalLock.lock)
                if (!Display.isCreated()) {
                    // monitorexit(lock)
                    return;
                }
                super.destroy();
                Display.access$502(Display.access$602(-1));
                Display.access$702(null);
            }
            // monitorexit(lock)
        };
        ((DrawableGL)(Display.drawable = drawable2)).setPixelFormat(pixelFormat, contextAttribs);
        drawable2.context = new ContextGL(drawable2.peer_info, contextAttribs, (drawable != null) ? ((DrawableGL)drawable).getContext() : null);
    }
    // monitorexit(lock)
    
    public static void create(final PixelFormatLWJGL pixelFormatLWJGL) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        create(pixelFormatLWJGL, null, null);
    }
    // monitorexit(lock)
    
    public static void create(final PixelFormatLWJGL pixelFormatLWJGL, final Drawable drawable) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        create(pixelFormatLWJGL, drawable, null);
    }
    // monitorexit(lock)
    
    public static void create(final PixelFormatLWJGL pixelFormatLWJGL, final org.lwjgl.opengles.ContextAttribs contextAttribs) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        create(pixelFormatLWJGL, null, contextAttribs);
    }
    // monitorexit(lock)
    
    public static void create(final PixelFormatLWJGL pixelFormat, final Drawable drawable, final org.lwjgl.opengles.ContextAttribs contextAttribs) throws LWJGLException {
        // monitorenter(lock = GlobalLock.lock)
        if (isCreated()) {
            throw new IllegalStateException("Only one LWJGL context may be instantiated at any one time.");
        }
        if (pixelFormat == null) {
            throw new NullPointerException("pixel_format cannot be null");
        }
        isFullscreen();
        final DrawableGLES drawable2 = new DrawableGLES() {
            public void setPixelFormat(final PixelFormatLWJGL pixelFormatLWJGL, final ContextAttribs contextAttribs) throws LWJGLException {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void destroy() {
                // monitorenter(lock = GlobalLock.lock)
                if (!Display.isCreated()) {
                    // monitorexit(lock)
                    return;
                }
                super.destroy();
                Display.access$502(Display.access$602(-1));
                Display.access$702(null);
            }
            // monitorexit(lock)
        };
        ((DrawableGLES)(Display.drawable = drawable2)).setPixelFormat(pixelFormat);
        drawable2.createContext(contextAttribs, drawable);
    }
    // monitorexit(lock)
    
    public static void setInitialBackground(final float r, final float g, final float b) {
        Display.r = r;
        Display.g = g;
        Display.b = b;
    }
    
    private static void makeCurrentAndSetSwapInterval() throws LWJGLException {
        Display.drawable.checkGLError();
        setSwapInterval(Display.swap_interval);
    }
    
    private static void initContext() {
        Display.drawable.initContext(Display.r, Display.g, Display.b);
    }
    
    static DisplayImplementation getImplementation() {
        return Display.display_impl;
    }
    
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
    
    static String getPrivilegedString(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(s) {
            final String val$property_name;
            
            public String run() {
                return System.getProperty(this.val$property_name);
            }
            
            public Object run() {
                return this.run();
            }
        });
    }
    
    private static void initControls() {
        if (!getPrivilegedBoolean("org.lwjgl.opengl.Display.noinput")) {
            if (Mouse.isCreated() || !getPrivilegedBoolean("org.lwjgl.opengl.Display.nomouse")) {}
            if (Keyboard.isCreated() || !getPrivilegedBoolean("org.lwjgl.opengl.Display.nokeyboard")) {}
        }
    }
    
    public static void destroy() {
        if (isCreated()) {
            Display.drawable.destroy();
        }
    }
    
    private static void reset() {
        Display.display_impl.resetDisplayMode();
        Display.current_mode = Display.initial_mode;
    }
    
    public static boolean isCreated() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return Display.window_created;
    }
    
    public static void setSwapInterval(final int swap_interval) {
        // monitorenter(lock = GlobalLock.lock)
        Display.swap_interval = swap_interval;
        if (isCreated()) {
            Display.drawable.setSwapInterval(Display.swap_interval);
        }
    }
    // monitorexit(lock)
    
    public static void setVSyncEnabled(final boolean swapInterval) {
        // monitorenter(lock = GlobalLock.lock)
        setSwapInterval(swapInterval ? 1 : 0);
    }
    // monitorexit(lock)
    
    public static void setLocation(final int p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: astore_2       
        //     5: monitorenter   
        //     6: iload_0        
        //     7: putstatic       org/lwjgl/opengl/Display.x:I
        //    10: iload_1        
        //    11: putstatic       org/lwjgl/opengl/Display.y:I
        //    14: invokestatic    org/lwjgl/opengl/Display.isCreated:()Z
        //    17: ifeq            23
        //    20: invokestatic    org/lwjgl/opengl/Display.isFullscreen:()Z
        //    23: aload_2        
        //    24: monitorexit    
        //    25: goto            33
        //    28: astore_3       
        //    29: aload_2        
        //    30: monitorexit    
        //    31: aload_3        
        //    32: athrow         
        //    33: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0023 (coming from #0020).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static void reshape() {
        final DisplayMode effectiveMode = getEffectiveMode();
        Display.display_impl.reshape(getWindowX(), getWindowY(), effectiveMode.getWidth(), effectiveMode.getHeight());
    }
    
    public static String getAdapter() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return Display.display_impl.getAdapter();
    }
    
    public static String getVersion() {
        // monitorenter(lock = GlobalLock.lock)
        // monitorexit(lock)
        return Display.display_impl.getVersion();
    }
    
    public static int setIcon(final ByteBuffer[] array) {
        // monitorenter(lock = GlobalLock.lock)
        if (Display.cached_icons != array) {
            Display.cached_icons = new ByteBuffer[array.length];
            while (0 < array.length) {
                Display.cached_icons[0] = BufferUtils.createByteBuffer(array[0].capacity());
                final int position = array[0].position();
                Display.cached_icons[0].put(array[0]);
                array[0].position(position);
                Display.cached_icons[0].flip();
                int n = 0;
                ++n;
            }
        }
        if (isCreated() && Display.parent == null) {
            // monitorexit(lock)
            return Display.display_impl.setIcon(Display.cached_icons);
        }
        // monitorexit(lock)
        return 0;
    }
    
    public static void setResizable(final boolean b) {
        Display.window_resizable = b;
        if (isCreated()) {
            Display.display_impl.setResizable(b);
        }
    }
    
    public static boolean isResizable() {
        return Display.window_resizable;
    }
    
    public static boolean wasResized() {
        return Display.window_resized;
    }
    
    public static int getX() {
        if (isFullscreen()) {
            return 0;
        }
        if (Display.parent != null) {
            return Display.parent.getX();
        }
        return Display.display_impl.getX();
    }
    
    public static int getY() {
        if (isFullscreen()) {
            return 0;
        }
        if (Display.parent != null) {
            return Display.parent.getY();
        }
        return Display.display_impl.getY();
    }
    
    public static int getWidth() {
        if (isFullscreen()) {
            return getDisplayMode().getWidth();
        }
        if (Display.parent != null) {
            return Display.parent.getWidth();
        }
        return 0;
    }
    
    public static int getHeight() {
        if (isFullscreen()) {
            return getDisplayMode().getHeight();
        }
        if (Display.parent != null) {
            return Display.parent.getHeight();
        }
        return 0;
    }
    
    public static float getPixelScaleFactor() {
        return Display.display_impl.getPixelScaleFactor();
    }
    
    static void access$000() {
    }
    
    static boolean access$102(final boolean parent_resized) {
        return Display.parent_resized = parent_resized;
    }
    
    static Thread access$200() {
        return Display.shutdown_hook;
    }
    
    static void access$300() {
    }
    
    static void access$400() {
    }
    
    static int access$502(final int x) {
        return Display.x = x;
    }
    
    static int access$602(final int y) {
        return Display.y = y;
    }
    
    static ByteBuffer[] access$702(final ByteBuffer[] cached_icons) {
        return Display.cached_icons = cached_icons;
    }
    
    static void access$800() {
    }
    
    static {
        shutdown_hook = new Thread() {
            @Override
            public void run() {
            }
        };
        Display.title = "Game";
        component_listener = new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent componentEvent) {
                // monitorenter(lock = GlobalLock.lock)
                Display.access$102(true);
            }
            // monitorexit(lock)
        };
        display_impl = createDisplayImplementation();
        Display.current_mode = (initial_mode = Display.display_impl.init());
        LWJGLUtil.log("Initial mode: " + Display.initial_mode);
    }
}
