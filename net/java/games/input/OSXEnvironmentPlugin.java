package net.java.games.input;

import net.java.games.util.plugins.*;
import java.security.*;
import java.io.*;
import java.util.*;

public final class OSXEnvironmentPlugin extends ControllerEnvironment implements Plugin
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
    
    private static final boolean isMacOSXEqualsOrBetterThan(final int n, final int n2) {
        final StringTokenizer stringTokenizer = new StringTokenizer(System.getProperty("os.version"), ".");
        final String nextToken = stringTokenizer.nextToken();
        final String nextToken2 = stringTokenizer.nextToken();
        final int int1 = Integer.parseInt(nextToken);
        final int int2 = Integer.parseInt(nextToken2);
        return int1 > n || (int1 == n && int2 >= n2);
    }
    
    public OSXEnvironmentPlugin() {
        if (this.isSupported()) {
            this.controllers = enumerateControllers();
        }
        else {
            this.controllers = new Controller[0];
        }
    }
    
    public final Controller[] getControllers() {
        return this.controllers;
    }
    
    public boolean isSupported() {
        return OSXEnvironmentPlugin.supported;
    }
    
    private static final void addElements(final OSXHIDQueue osxhidQueue, final List list, final List list2, final boolean b) throws IOException {
        for (final OSXHIDElement osxhidElement : list) {
            Component.Identifier identifier = osxhidElement.getIdentifier();
            if (identifier == null) {
                continue;
            }
            if (b) {
                if (identifier == Component.Identifier.Button._0) {
                    identifier = Component.Identifier.Button.LEFT;
                }
                else if (identifier == Component.Identifier.Button._1) {
                    identifier = Component.Identifier.Button.RIGHT;
                }
                else if (identifier == Component.Identifier.Button._2) {
                    identifier = Component.Identifier.Button.MIDDLE;
                }
            }
            final OSXComponent osxComponent = new OSXComponent(identifier, osxhidElement);
            list2.add(osxComponent);
            osxhidQueue.addElement(osxhidElement, osxComponent);
        }
    }
    
    private static final Keyboard createKeyboardFromDevice(final OSXHIDDevice osxhidDevice, final List list) throws IOException {
        final ArrayList list2 = new ArrayList();
        final OSXHIDQueue queue = osxhidDevice.createQueue(32);
        addElements(queue, list, list2, false);
        final Component[] array = new Component[list2.size()];
        list2.toArray(array);
        return new OSXKeyboard(osxhidDevice, queue, array, new Controller[0], new Rumbler[0]);
    }
    
    private static final Mouse createMouseFromDevice(final OSXHIDDevice osxhidDevice, final List list) throws IOException {
        final ArrayList list2 = new ArrayList();
        final OSXHIDQueue queue = osxhidDevice.createQueue(32);
        addElements(queue, list, list2, true);
        final Component[] array = new Component[list2.size()];
        list2.toArray(array);
        final OSXMouse osxMouse = new OSXMouse(osxhidDevice, queue, array, new Controller[0], new Rumbler[0]);
        if (osxMouse.getPrimaryButton() != null && osxMouse.getX() != null && osxMouse.getY() != null) {
            return osxMouse;
        }
        queue.release();
        return null;
    }
    
    private static final AbstractController createControllerFromDevice(final OSXHIDDevice osxhidDevice, final List list, final Controller.Type type) throws IOException {
        final ArrayList list2 = new ArrayList();
        final OSXHIDQueue queue = osxhidDevice.createQueue(32);
        addElements(queue, list, list2, false);
        final Component[] array = new Component[list2.size()];
        list2.toArray(array);
        return new OSXAbstractController(osxhidDevice, queue, array, new Controller[0], new Rumbler[0], type);
    }
    
    private static final void createControllersFromDevice(final OSXHIDDevice osxhidDevice, final List list) throws IOException {
        final UsagePair usagePair = osxhidDevice.getUsagePair();
        if (usagePair == null) {
            return;
        }
        final List elements = osxhidDevice.getElements();
        if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && (usagePair.getUsage() == GenericDesktopUsage.MOUSE || usagePair.getUsage() == GenericDesktopUsage.POINTER)) {
            final Mouse mouseFromDevice = createMouseFromDevice(osxhidDevice, elements);
            if (mouseFromDevice != null) {
                list.add(mouseFromDevice);
            }
        }
        else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && (usagePair.getUsage() == GenericDesktopUsage.KEYBOARD || usagePair.getUsage() == GenericDesktopUsage.KEYPAD)) {
            final Keyboard keyboardFromDevice = createKeyboardFromDevice(osxhidDevice, elements);
            if (keyboardFromDevice != null) {
                list.add(keyboardFromDevice);
            }
        }
        else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usagePair.getUsage() == GenericDesktopUsage.JOYSTICK) {
            final AbstractController controllerFromDevice = createControllerFromDevice(osxhidDevice, elements, Controller.Type.STICK);
            if (controllerFromDevice != null) {
                list.add(controllerFromDevice);
            }
        }
        else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usagePair.getUsage() == GenericDesktopUsage.MULTI_AXIS_CONTROLLER) {
            final AbstractController controllerFromDevice2 = createControllerFromDevice(osxhidDevice, elements, Controller.Type.STICK);
            if (controllerFromDevice2 != null) {
                list.add(controllerFromDevice2);
            }
        }
        else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usagePair.getUsage() == GenericDesktopUsage.GAME_PAD) {
            final AbstractController controllerFromDevice3 = createControllerFromDevice(osxhidDevice, elements, Controller.Type.GAMEPAD);
            if (controllerFromDevice3 != null) {
                list.add(controllerFromDevice3);
            }
        }
    }
    
    private static final Controller[] enumerateControllers() {
        final ArrayList list = new ArrayList();
        final OSXHIDDeviceIterator osxhidDeviceIterator = new OSXHIDDeviceIterator();
        while (true) {
            final OSXHIDDevice next = osxhidDeviceIterator.next();
            if (next == null) {
                break;
            }
            final int size = list.size();
            createControllersFromDevice(next, list);
            final boolean b = size != list.size();
            if (false) {
                continue;
            }
            next.release();
        }
        osxhidDeviceIterator.close();
        final Controller[] array = new Controller[list.size()];
        list.toArray(array);
        return array;
    }
    
    static boolean access$002(final boolean supported) {
        return OSXEnvironmentPlugin.supported = supported;
    }
    
    static {
        OSXEnvironmentPlugin.supported = false;
        if (getPrivilegedProperty("os.name", "").trim().equals("Mac OS X")) {
            OSXEnvironmentPlugin.supported = true;
            loadLibrary("jinput-osx");
        }
    }
}
