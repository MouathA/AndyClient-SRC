package net.java.games.input;

import net.java.games.util.plugins.*;
import java.security.*;
import java.util.*;
import java.io.*;

public final class DirectInputEnvironmentPlugin extends ControllerEnvironment implements Plugin
{
    private static boolean supported;
    private final Controller[] controllers;
    private final List active_devices;
    private final DummyWindow window;
    
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
    
    public DirectInputEnvironmentPlugin() {
        this.active_devices = new ArrayList();
        final Controller[] controllers = new Controller[0];
        if (this.isSupported()) {
            final DummyWindow window = new DummyWindow();
            final Controller[] enumControllers = this.enumControllers(window);
            this.window = window;
            this.controllers = enumControllers;
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                private final DirectInputEnvironmentPlugin this$0;
                
                public final Object run() {
                    Runtime.getRuntime().addShutdownHook(this.this$0.new ShutdownHook(null));
                    return null;
                }
            });
        }
        else {
            this.window = null;
            this.controllers = controllers;
        }
    }
    
    public final Controller[] getControllers() {
        return this.controllers;
    }
    
    private final Component[] createComponents(final IDirectInputDevice directInputDevice, final boolean b) {
        final List objects = directInputDevice.getObjects();
        final ArrayList list = new ArrayList<DIComponent>();
        while (0 < objects.size()) {
            final DIDeviceObject diDeviceObject = objects.get(0);
            Component.Identifier identifier = diDeviceObject.getIdentifier();
            if (identifier != null) {
                if (b && identifier instanceof Component.Identifier.Button) {
                    identifier = DIIdentifierMap.mapMouseButtonIdentifier((Component.Identifier.Button)identifier);
                }
                final DIComponent diComponent = new DIComponent(identifier, diDeviceObject);
                list.add(diComponent);
                directInputDevice.registerComponent(diDeviceObject, diComponent);
            }
            int n = 0;
            ++n;
        }
        final Component[] array = new Component[list.size()];
        list.toArray(array);
        return array;
    }
    
    private final Mouse createMouseFromDevice(final IDirectInputDevice directInputDevice) {
        final DIMouse diMouse = new DIMouse(directInputDevice, this.createComponents(directInputDevice, true), new Controller[0], directInputDevice.getRumblers());
        if (diMouse.getX() != null && diMouse.getY() != null && diMouse.getPrimaryButton() != null) {
            return diMouse;
        }
        return null;
    }
    
    private final AbstractController createControllerFromDevice(final IDirectInputDevice directInputDevice, final Controller.Type type) {
        return new DIAbstractController(directInputDevice, this.createComponents(directInputDevice, false), new Controller[0], directInputDevice.getRumblers(), type);
    }
    
    private final Keyboard createKeyboardFromDevice(final IDirectInputDevice directInputDevice) {
        return new DIKeyboard(directInputDevice, this.createComponents(directInputDevice, false), new Controller[0], directInputDevice.getRumblers());
    }
    
    private final Controller createControllerFromDevice(final IDirectInputDevice directInputDevice) {
        switch (directInputDevice.getType()) {
            case 18: {
                return this.createMouseFromDevice(directInputDevice);
            }
            case 19: {
                return this.createKeyboardFromDevice(directInputDevice);
            }
            case 21: {
                return this.createControllerFromDevice(directInputDevice, Controller.Type.GAMEPAD);
            }
            case 22: {
                return this.createControllerFromDevice(directInputDevice, Controller.Type.WHEEL);
            }
            case 20:
            case 23:
            case 24: {
                return this.createControllerFromDevice(directInputDevice, Controller.Type.STICK);
            }
            default: {
                return this.createControllerFromDevice(directInputDevice, Controller.Type.UNKNOWN);
            }
        }
    }
    
    private final Controller[] enumControllers(final DummyWindow dummyWindow) throws IOException {
        final ArrayList list = new ArrayList<Controller>();
        final IDirectInput directInput = new IDirectInput(dummyWindow);
        final List devices = directInput.getDevices();
        while (0 < devices.size()) {
            final IDirectInputDevice directInputDevice = devices.get(0);
            final Controller controllerFromDevice = this.createControllerFromDevice(directInputDevice);
            if (controllerFromDevice != null) {
                list.add(controllerFromDevice);
                this.active_devices.add(directInputDevice);
            }
            else {
                directInputDevice.release();
            }
            int n = 0;
            ++n;
        }
        directInput.release();
        final Controller[] array = new Controller[list.size()];
        list.toArray(array);
        return array;
    }
    
    public boolean isSupported() {
        return DirectInputEnvironmentPlugin.supported;
    }
    
    static boolean access$002(final boolean supported) {
        return DirectInputEnvironmentPlugin.supported = supported;
    }
    
    static List access$200(final DirectInputEnvironmentPlugin directInputEnvironmentPlugin) {
        return directInputEnvironmentPlugin.active_devices;
    }
    
    static {
        DirectInputEnvironmentPlugin.supported = false;
        if (getPrivilegedProperty("os.name", "").trim().startsWith("Windows")) {
            DirectInputEnvironmentPlugin.supported = true;
            if ("x86".equals(getPrivilegedProperty("os.arch"))) {
                loadLibrary("jinput-dx8");
            }
            else {
                loadLibrary("jinput-dx8_64");
            }
        }
    }
    
    private final class ShutdownHook extends Thread
    {
        private final DirectInputEnvironmentPlugin this$0;
        
        private ShutdownHook(final DirectInputEnvironmentPlugin this$0) {
            this.this$0 = this$0;
        }
        
        public final void run() {
            while (0 < DirectInputEnvironmentPlugin.access$200(this.this$0).size()) {
                DirectInputEnvironmentPlugin.access$200(this.this$0).get(0).release();
                int n = 0;
                ++n;
            }
        }
        
        ShutdownHook(final DirectInputEnvironmentPlugin directInputEnvironmentPlugin, final DirectInputEnvironmentPlugin$1 privilegedAction) {
            this(directInputEnvironmentPlugin);
        }
    }
}
