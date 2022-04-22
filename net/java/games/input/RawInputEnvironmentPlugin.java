package net.java.games.input;

import net.java.games.util.plugins.*;
import java.security.*;
import java.util.*;
import java.io.*;

public final class RawInputEnvironmentPlugin extends ControllerEnvironment implements Plugin
{
    private static boolean supported;
    private final Controller[] controllers;
    
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
    
    public RawInputEnvironmentPlugin() {
        Controller[] enumControllers = new Controller[0];
        if (this.isSupported()) {
            enumControllers = this.enumControllers(new RawInputEventQueue());
        }
        this.controllers = enumControllers;
    }
    
    public final Controller[] getControllers() {
        return this.controllers;
    }
    
    private static final SetupAPIDevice lookupSetupAPIDevice(String upperCase, final List list) {
        upperCase = upperCase.replaceAll("#", "\\\\").toUpperCase();
        while (0 < list.size()) {
            final SetupAPIDevice setupAPIDevice = list.get(0);
            if (upperCase.indexOf(setupAPIDevice.getInstanceId().toUpperCase()) != -1) {
                return setupAPIDevice;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private static final void createControllersFromDevices(final RawInputEventQueue rawInputEventQueue, final List list, final List list2, final List list3) throws IOException {
        final ArrayList<RawDevice> list4 = new ArrayList<RawDevice>();
        while (0 < list2.size()) {
            final RawDevice rawDevice = list2.get(0);
            final SetupAPIDevice lookupSetupAPIDevice = lookupSetupAPIDevice(rawDevice.getName(), list3);
            if (lookupSetupAPIDevice != null) {
                final Controller controllerFromDevice = rawDevice.getInfo().createControllerFromDevice(rawDevice, lookupSetupAPIDevice);
                if (controllerFromDevice != null) {
                    list.add(controllerFromDevice);
                    list4.add(rawDevice);
                }
            }
            int n = 0;
            ++n;
        }
        rawInputEventQueue.start(list4);
    }
    
    private static final native void enumerateDevices(final RawInputEventQueue p0, final List p1) throws IOException;
    
    private final Controller[] enumControllers(final RawInputEventQueue rawInputEventQueue) throws IOException {
        final ArrayList list = new ArrayList();
        final ArrayList list2 = new ArrayList();
        enumerateDevices(rawInputEventQueue, list2);
        createControllersFromDevices(rawInputEventQueue, list, list2, enumSetupAPIDevices());
        final Controller[] array = new Controller[list.size()];
        list.toArray(array);
        return array;
    }
    
    public boolean isSupported() {
        return RawInputEnvironmentPlugin.supported;
    }
    
    private static final List enumSetupAPIDevices() throws IOException {
        final ArrayList list = new ArrayList();
        nEnumSetupAPIDevices(getKeyboardClassGUID(), list);
        nEnumSetupAPIDevices(getMouseClassGUID(), list);
        return list;
    }
    
    private static final native void nEnumSetupAPIDevices(final byte[] p0, final List p1) throws IOException;
    
    private static final native byte[] getKeyboardClassGUID();
    
    private static final native byte[] getMouseClassGUID();
    
    static boolean access$002(final boolean supported) {
        return RawInputEnvironmentPlugin.supported = supported;
    }
    
    static {
        RawInputEnvironmentPlugin.supported = false;
        if (getPrivilegedProperty("os.name", "").trim().startsWith("Windows")) {
            RawInputEnvironmentPlugin.supported = true;
            if ("x86".equals(getPrivilegedProperty("os.arch"))) {
                loadLibrary("jinput-raw");
            }
            else {
                loadLibrary("jinput-raw_64");
            }
        }
    }
}
