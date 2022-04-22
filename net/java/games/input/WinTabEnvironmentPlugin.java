package net.java.games.input;

import net.java.games.util.plugins.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class WinTabEnvironmentPlugin extends ControllerEnvironment implements Plugin
{
    private static boolean supported;
    private final Controller[] controllers;
    private final List active_devices;
    private final WinTabContext winTabContext;
    
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
    
    public WinTabEnvironmentPlugin() {
        this.active_devices = new ArrayList();
        if (this.isSupported()) {
            final Controller[] array = new Controller[0];
            final WinTabContext winTabContext = new WinTabContext(new DummyWindow());
            winTabContext.open();
            this.controllers = winTabContext.getControllers();
            this.winTabContext = winTabContext;
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                private final WinTabEnvironmentPlugin this$0;
                
                public final Object run() {
                    Runtime.getRuntime().addShutdownHook(this.this$0.new ShutdownHook(null));
                    return null;
                }
            });
        }
        else {
            this.winTabContext = null;
            this.controllers = new Controller[0];
        }
    }
    
    public boolean isSupported() {
        return WinTabEnvironmentPlugin.supported;
    }
    
    public Controller[] getControllers() {
        return this.controllers;
    }
    
    static boolean access$002(final boolean supported) {
        return WinTabEnvironmentPlugin.supported = supported;
    }
    
    static List access$200(final WinTabEnvironmentPlugin winTabEnvironmentPlugin) {
        return winTabEnvironmentPlugin.active_devices;
    }
    
    static WinTabContext access$300(final WinTabEnvironmentPlugin winTabEnvironmentPlugin) {
        return winTabEnvironmentPlugin.winTabContext;
    }
    
    static {
        WinTabEnvironmentPlugin.supported = false;
        if (getPrivilegedProperty("os.name", "").trim().startsWith("Windows")) {
            WinTabEnvironmentPlugin.supported = true;
            loadLibrary("jinput-wintab");
        }
    }
    
    private final class ShutdownHook extends Thread
    {
        private final WinTabEnvironmentPlugin this$0;
        
        private ShutdownHook(final WinTabEnvironmentPlugin this$0) {
            this.this$0 = this$0;
        }
        
        public final void run() {
            while (0 < WinTabEnvironmentPlugin.access$200(this.this$0).size()) {
                int n = 0;
                ++n;
            }
            WinTabEnvironmentPlugin.access$300(this.this$0).close();
        }
        
        ShutdownHook(final WinTabEnvironmentPlugin winTabEnvironmentPlugin, final WinTabEnvironmentPlugin$1 privilegedAction) {
            this(winTabEnvironmentPlugin);
        }
    }
}
