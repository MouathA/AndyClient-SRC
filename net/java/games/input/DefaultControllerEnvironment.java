package net.java.games.input;

import java.util.logging.*;
import java.util.*;
import java.io.*;
import java.security.*;
import net.java.games.util.plugins.*;

class DefaultControllerEnvironment extends ControllerEnvironment
{
    static String libPath;
    private static Logger log;
    private ArrayList controllers;
    private Collection loadedPlugins;
    static Class class$net$java$games$input$DefaultControllerEnvironment;
    static Class class$net$java$games$input$ControllerEnvironment;
    
    static void loadLibrary(final String s) {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction(s) {
            private final String val$lib_name;
            
            public final Object run() {
                final String property = System.getProperty("net.java.games.input.librarypath");
                if (property != null) {
                    System.load(property + File.separator + System.mapLibraryName(this.val$lib_name));
                }
                else {
                    System.loadLibrary(this.val$lib_name);
                }
                return null;
            }
        });
    }
    
    static String getPrivilegedProperty(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(s) {
            private final String val$property;
            
            public Object run() {
                return System.getProperty(this.val$property);
            }
        });
    }
    
    static String getPrivilegedProperty(final String s, final String s2) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(s, s2) {
            private final String val$property;
            private final String val$default_value;
            
            public Object run() {
                return System.getProperty(this.val$property, this.val$default_value);
            }
        });
    }
    
    public DefaultControllerEnvironment() {
        this.loadedPlugins = new ArrayList();
    }
    
    public Controller[] getControllers() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/java/games/input/DefaultControllerEnvironment.controllers:Ljava/util/ArrayList;
        //     4: ifnonnull       523
        //     7: aload_0        
        //     8: new             Ljava/util/ArrayList;
        //    11: dup            
        //    12: invokespecial   java/util/ArrayList.<init>:()V
        //    15: putfield        net/java/games/input/DefaultControllerEnvironment.controllers:Ljava/util/ArrayList;
        //    18: new             Lnet/java/games/input/DefaultControllerEnvironment$4;
        //    21: dup            
        //    22: aload_0        
        //    23: invokespecial   net/java/games/input/DefaultControllerEnvironment$4.<init>:(Lnet/java/games/input/DefaultControllerEnvironment;)V
        //    26: invokestatic    java/security/AccessController.doPrivileged:(Ljava/security/PrivilegedAction;)Ljava/lang/Object;
        //    29: pop            
        //    30: new             Ljava/lang/StringBuffer;
        //    33: dup            
        //    34: invokespecial   java/lang/StringBuffer.<init>:()V
        //    37: ldc             "jinput.plugins"
        //    39: ldc             ""
        //    41: invokestatic    net/java/games/input/DefaultControllerEnvironment.getPrivilegedProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    44: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    47: ldc             " "
        //    49: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    52: ldc             "net.java.games.input.plugins"
        //    54: ldc             ""
        //    56: invokestatic    net/java/games/input/DefaultControllerEnvironment.getPrivilegedProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    59: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    62: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //    65: astore_1       
        //    66: ldc             "jinput.useDefaultPlugin"
        //    68: ldc             "true"
        //    70: invokestatic    net/java/games/input/DefaultControllerEnvironment.getPrivilegedProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    73: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    76: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //    79: ldc             "false"
        //    81: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    84: ifne            369
        //    87: ldc             "net.java.games.input.useDefaultPlugin"
        //    89: ldc             "true"
        //    91: invokestatic    net/java/games/input/DefaultControllerEnvironment.getPrivilegedProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    94: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    97: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //   100: ldc             "false"
        //   102: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   105: ifne            369
        //   108: ldc             "os.name"
        //   110: ldc             ""
        //   112: invokestatic    net/java/games/input/DefaultControllerEnvironment.getPrivilegedProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   115: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //   118: astore_2       
        //   119: aload_2        
        //   120: ldc             "Linux"
        //   122: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   125: ifeq            151
        //   128: new             Ljava/lang/StringBuffer;
        //   131: dup            
        //   132: invokespecial   java/lang/StringBuffer.<init>:()V
        //   135: aload_1        
        //   136: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   139: ldc             " net.java.games.input.LinuxEnvironmentPlugin"
        //   141: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   144: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   147: astore_1       
        //   148: goto            369
        //   151: aload_2        
        //   152: ldc             "Mac OS X"
        //   154: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   157: ifeq            183
        //   160: new             Ljava/lang/StringBuffer;
        //   163: dup            
        //   164: invokespecial   java/lang/StringBuffer.<init>:()V
        //   167: aload_1        
        //   168: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   171: ldc             " net.java.games.input.OSXEnvironmentPlugin"
        //   173: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   176: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   179: astore_1       
        //   180: goto            369
        //   183: aload_2        
        //   184: ldc             "Windows XP"
        //   186: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   189: ifne            210
        //   192: aload_2        
        //   193: ldc             "Windows Vista"
        //   195: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   198: ifne            210
        //   201: aload_2        
        //   202: ldc             "Windows 7"
        //   204: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   207: ifeq            233
        //   210: new             Ljava/lang/StringBuffer;
        //   213: dup            
        //   214: invokespecial   java/lang/StringBuffer.<init>:()V
        //   217: aload_1        
        //   218: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   221: ldc             " net.java.games.input.DirectAndRawInputEnvironmentPlugin"
        //   223: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   226: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   229: astore_1       
        //   230: goto            369
        //   233: aload_2        
        //   234: ldc             "Windows 98"
        //   236: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   239: ifne            251
        //   242: aload_2        
        //   243: ldc             "Windows 2000"
        //   245: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   248: ifeq            274
        //   251: new             Ljava/lang/StringBuffer;
        //   254: dup            
        //   255: invokespecial   java/lang/StringBuffer.<init>:()V
        //   258: aload_1        
        //   259: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   262: ldc             " net.java.games.input.DirectInputEnvironmentPlugin"
        //   264: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   267: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   270: astore_1       
        //   271: goto            369
        //   274: aload_2        
        //   275: ldc             "Windows"
        //   277: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   280: ifeq            339
        //   283: getstatic       net/java/games/input/DefaultControllerEnvironment.log:Ljava/util/logging/Logger;
        //   286: new             Ljava/lang/StringBuffer;
        //   289: dup            
        //   290: invokespecial   java/lang/StringBuffer.<init>:()V
        //   293: ldc             "Found unknown Windows version: "
        //   295: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   298: aload_2        
        //   299: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   302: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   305: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
        //   308: getstatic       net/java/games/input/DefaultControllerEnvironment.log:Ljava/util/logging/Logger;
        //   311: ldc             "Attempting to use default windows plug-in."
        //   313: invokevirtual   java/util/logging/Logger.info:(Ljava/lang/String;)V
        //   316: new             Ljava/lang/StringBuffer;
        //   319: dup            
        //   320: invokespecial   java/lang/StringBuffer.<init>:()V
        //   323: aload_1        
        //   324: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   327: ldc             " net.java.games.input.DirectAndRawInputEnvironmentPlugin"
        //   329: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   332: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   335: astore_1       
        //   336: goto            369
        //   339: getstatic       net/java/games/input/DefaultControllerEnvironment.log:Ljava/util/logging/Logger;
        //   342: new             Ljava/lang/StringBuffer;
        //   345: dup            
        //   346: invokespecial   java/lang/StringBuffer.<init>:()V
        //   349: ldc             "Trying to use default plugin, OS name "
        //   351: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   354: aload_2        
        //   355: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   358: ldc             " not recognised"
        //   360: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   363: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   366: invokevirtual   java/util/logging/Logger.info:(Ljava/lang/String;)V
        //   369: new             Ljava/util/StringTokenizer;
        //   372: dup            
        //   373: aload_1        
        //   374: ldc             " \t\n\r\f,;:"
        //   376: invokespecial   java/util/StringTokenizer.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   379: astore_2       
        //   380: aload_2        
        //   381: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   384: ifeq            523
        //   387: aload_2        
        //   388: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   391: astore_3       
        //   392: aload_0        
        //   393: getfield        net/java/games/input/DefaultControllerEnvironment.loadedPlugins:Ljava/util/Collection;
        //   396: aload_3        
        //   397: invokeinterface java/util/Collection.contains:(Ljava/lang/Object;)Z
        //   402: ifne            510
        //   405: getstatic       net/java/games/input/DefaultControllerEnvironment.log:Ljava/util/logging/Logger;
        //   408: new             Ljava/lang/StringBuffer;
        //   411: dup            
        //   412: invokespecial   java/lang/StringBuffer.<init>:()V
        //   415: ldc             "Loading: "
        //   417: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   420: aload_3        
        //   421: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   424: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   427: invokevirtual   java/util/logging/Logger.info:(Ljava/lang/String;)V
        //   430: aload_3        
        //   431: invokestatic    java/lang/Class.forName:(Ljava/lang/String;)Ljava/lang/Class;
        //   434: astore          4
        //   436: aload           4
        //   438: invokevirtual   java/lang/Class.newInstance:()Ljava/lang/Object;
        //   441: checkcast       Lnet/java/games/input/ControllerEnvironment;
        //   444: astore          5
        //   446: aload           5
        //   448: invokevirtual   net/java/games/input/ControllerEnvironment.isSupported:()Z
        //   451: ifeq            484
        //   454: aload_0        
        //   455: aload           5
        //   457: invokevirtual   net/java/games/input/ControllerEnvironment.getControllers:()[Lnet/java/games/input/Controller;
        //   460: invokespecial   net/java/games/input/DefaultControllerEnvironment.addControllers:([Lnet/java/games/input/Controller;)V
        //   463: aload_0        
        //   464: getfield        net/java/games/input/DefaultControllerEnvironment.loadedPlugins:Ljava/util/Collection;
        //   467: aload           5
        //   469: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //   472: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   475: invokeinterface java/util/Collection.add:(Ljava/lang/Object;)Z
        //   480: pop            
        //   481: goto            510
        //   484: new             Ljava/lang/StringBuffer;
        //   487: dup            
        //   488: invokespecial   java/lang/StringBuffer.<init>:()V
        //   491: aload           4
        //   493: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   496: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   499: ldc             " is not supported"
        //   501: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   504: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   507: invokestatic    net/java/games/input/DefaultControllerEnvironment.logln:(Ljava/lang/String;)V
        //   510: goto            520
        //   513: astore          4
        //   515: aload           4
        //   517: invokevirtual   java/lang/Throwable.printStackTrace:()V
        //   520: goto            380
        //   523: aload_0        
        //   524: getfield        net/java/games/input/DefaultControllerEnvironment.controllers:Ljava/util/ArrayList;
        //   527: invokevirtual   java/util/ArrayList.size:()I
        //   530: anewarray       Lnet/java/games/input/Controller;
        //   533: astore_1       
        //   534: aload_0        
        //   535: getfield        net/java/games/input/DefaultControllerEnvironment.controllers:Ljava/util/ArrayList;
        //   538: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   541: astore_2       
        //   542: aload_2        
        //   543: invokeinterface java/util/Iterator.hasNext:()Z
        //   548: ifeq            569
        //   551: aload_1        
        //   552: iconst_0       
        //   553: aload_2        
        //   554: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   559: checkcast       Lnet/java/games/input/Controller;
        //   562: aastore        
        //   563: iinc            3, 1
        //   566: goto            542
        //   569: aload_1        
        //   570: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void scanControllers() {
        String privilegedProperty = getPrivilegedProperty("jinput.controllerPluginPath");
        if (privilegedProperty == null) {
            privilegedProperty = "controller";
        }
        this.scanControllersAt(getPrivilegedProperty("java.home") + File.separator + "lib" + File.separator + privilegedProperty);
        this.scanControllersAt(getPrivilegedProperty("user.dir") + File.separator + privilegedProperty);
    }
    
    private void scanControllersAt(final String s) {
        final File file = new File(s);
        if (!file.exists()) {
            return;
        }
        final Class[] extends1 = new Plugins(file).getExtends((DefaultControllerEnvironment.class$net$java$games$input$ControllerEnvironment == null) ? (DefaultControllerEnvironment.class$net$java$games$input$ControllerEnvironment = class$("net.java.games.input.ControllerEnvironment")) : DefaultControllerEnvironment.class$net$java$games$input$ControllerEnvironment);
        while (0 < extends1.length) {
            ControllerEnvironment.logln("ControllerEnvironment " + extends1[0].getName() + " loaded by " + extends1[0].getClassLoader());
            final ControllerEnvironment controllerEnvironment = extends1[0].newInstance();
            if (controllerEnvironment.isSupported()) {
                this.addControllers(controllerEnvironment.getControllers());
                this.loadedPlugins.add(controllerEnvironment.getClass().getName());
            }
            else {
                ControllerEnvironment.logln(extends1[0].getName() + " is not supported");
            }
            int n = 0;
            ++n;
        }
    }
    
    private void addControllers(final Controller[] array) {
        while (0 < array.length) {
            this.controllers.add(array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public boolean isSupported() {
        return true;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static void access$000(final DefaultControllerEnvironment defaultControllerEnvironment) {
        defaultControllerEnvironment.scanControllers();
    }
    
    static {
        DefaultControllerEnvironment.log = Logger.getLogger(((DefaultControllerEnvironment.class$net$java$games$input$DefaultControllerEnvironment == null) ? (DefaultControllerEnvironment.class$net$java$games$input$DefaultControllerEnvironment = class$("net.java.games.input.DefaultControllerEnvironment")) : DefaultControllerEnvironment.class$net$java$games$input$DefaultControllerEnvironment).getName());
    }
}
