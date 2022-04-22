package net.java.games.input;

import net.java.games.util.plugins.*;
import java.security.*;
import java.io.*;
import java.util.*;

public final class LinuxEnvironmentPlugin extends ControllerEnvironment implements Plugin
{
    private static final String LIBNAME;
    private static final String POSTFIX64BIT;
    private static boolean supported;
    private final Controller[] controllers;
    private final List devices;
    private static final LinuxDeviceThread device_thread;
    
    static void loadLibrary(final String s) {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction(s) {
            private final String val$lib_name;
            
            public final Object run() {
                final String s = "\u369f\u3694\u3685\u36df\u369b\u3690\u3687\u3690\u36df\u3696\u3690\u369c\u3694\u3682\u36df\u3698\u369f\u3681\u3684\u3685\u36df\u369d\u3698\u3693\u3683\u3690\u3683\u3688\u3681\u3690\u3685\u3699";
                if (s != null) {
                    System.load(s + File.separator + System.mapLibraryName(this.val$lib_name));
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
    
    public static final Object execute(final LinuxDeviceTask linuxDeviceTask) throws IOException {
        return LinuxEnvironmentPlugin.device_thread.execute(linuxDeviceTask);
    }
    
    public LinuxEnvironmentPlugin() {
        this.devices = new ArrayList();
        if (this.isSupported()) {
            this.controllers = this.enumerateControllers();
            ControllerEnvironment.logln("Linux plugin claims to have found " + this.controllers.length + " controllers");
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                private final LinuxEnvironmentPlugin this$0;
                
                public final Object run() {
                    Runtime.getRuntime().addShutdownHook(this.this$0.new ShutdownHook(null));
                    return null;
                }
            });
        }
        else {
            this.controllers = new Controller[0];
        }
    }
    
    public final Controller[] getControllers() {
        return this.controllers;
    }
    
    private static final Component[] createComponents(final List list, final LinuxEventDevice linuxEventDevice) {
        final LinuxEventComponent[][] array = new LinuxEventComponent[4][2];
        final ArrayList list2 = new ArrayList<LinuxPOV>();
        int n = 0;
        while (0 < list.size()) {
            final LinuxEventComponent linuxEventComponent = list.get(0);
            final Component.Identifier identifier = linuxEventComponent.getIdentifier();
            if (identifier == Component.Identifier.Axis.POV) {
                final int code = linuxEventComponent.getDescriptor().getCode();
                switch (code) {
                    case 16: {
                        array[0][0] = linuxEventComponent;
                        break;
                    }
                    case 17: {
                        array[0][1] = linuxEventComponent;
                        break;
                    }
                    case 18: {
                        array[1][0] = linuxEventComponent;
                        break;
                    }
                    case 19: {
                        array[1][1] = linuxEventComponent;
                        break;
                    }
                    case 20: {
                        array[2][0] = linuxEventComponent;
                        break;
                    }
                    case 21: {
                        array[2][1] = linuxEventComponent;
                        break;
                    }
                    case 22: {
                        array[3][0] = linuxEventComponent;
                        break;
                    }
                    case 23: {
                        array[3][1] = linuxEventComponent;
                        break;
                    }
                    default: {
                        ControllerEnvironment.logln("Unknown POV instance: " + code);
                        break;
                    }
                }
            }
            else if (identifier != null) {
                final LinuxComponent linuxComponent = new LinuxComponent(linuxEventComponent);
                list2.add((LinuxPOV)linuxComponent);
                linuxEventDevice.registerComponent(linuxEventComponent.getDescriptor(), linuxComponent);
            }
            ++n;
        }
        while (0 < array.length) {
            final LinuxEventComponent linuxEventComponent2 = array[0][0];
            final LinuxEventComponent linuxEventComponent3 = array[0][1];
            if (linuxEventComponent2 != null && linuxEventComponent3 != null) {
                final LinuxPOV linuxPOV = new LinuxPOV(linuxEventComponent2, linuxEventComponent3);
                list2.add(linuxPOV);
                linuxEventDevice.registerComponent(linuxEventComponent2.getDescriptor(), linuxPOV);
                linuxEventDevice.registerComponent(linuxEventComponent3.getDescriptor(), linuxPOV);
            }
            ++n;
        }
        final Component[] array2 = new Component[list2.size()];
        list2.toArray(array2);
        return array2;
    }
    
    private static final Mouse createMouseFromDevice(final LinuxEventDevice linuxEventDevice, final Component[] array) throws IOException {
        final LinuxMouse linuxMouse = new LinuxMouse(linuxEventDevice, array, new Controller[0], linuxEventDevice.getRumblers());
        if (linuxMouse.getX() != null && linuxMouse.getY() != null && linuxMouse.getPrimaryButton() != null) {
            return linuxMouse;
        }
        return null;
    }
    
    private static final Keyboard createKeyboardFromDevice(final LinuxEventDevice linuxEventDevice, final Component[] array) throws IOException {
        return new LinuxKeyboard(linuxEventDevice, array, new Controller[0], linuxEventDevice.getRumblers());
    }
    
    private static final Controller createJoystickFromDevice(final LinuxEventDevice linuxEventDevice, final Component[] array, final Controller.Type type) throws IOException {
        return new LinuxAbstractController(linuxEventDevice, array, new Controller[0], linuxEventDevice.getRumblers(), type);
    }
    
    private static final Controller createControllerFromDevice(final LinuxEventDevice linuxEventDevice) throws IOException {
        final Component[] components = createComponents(linuxEventDevice.getComponents(), linuxEventDevice);
        final Controller.Type type = linuxEventDevice.getType();
        if (type == Controller.Type.MOUSE) {
            return createMouseFromDevice(linuxEventDevice, components);
        }
        if (type == Controller.Type.KEYBOARD) {
            return createKeyboardFromDevice(linuxEventDevice, components);
        }
        if (type == Controller.Type.STICK || type == Controller.Type.GAMEPAD) {
            return createJoystickFromDevice(linuxEventDevice, components, type);
        }
        return null;
    }
    
    private final Controller[] enumerateControllers() {
        final ArrayList<LinuxCombinedController> list = new ArrayList<LinuxCombinedController>();
        final ArrayList<Controller> list2 = new ArrayList<Controller>();
        final ArrayList<LinuxCombinedController> list3 = new ArrayList<LinuxCombinedController>();
        this.enumerateEventControllers(list2);
        this.enumerateJoystickControllers(list3);
        while (0 < list2.size()) {
            int n2 = 0;
            while (0 < list3.size()) {
                final Controller controller = list2.get(0);
                final LinuxJoystickAbstractController linuxJoystickAbstractController = list3.get(0);
                int n3 = 0;
                if (controller.getName().equals(linuxJoystickAbstractController.getName())) {
                    final Component[] components = controller.getComponents();
                    final Component[] components2 = linuxJoystickAbstractController.getComponents();
                    if (components.length == components2.length) {
                        while (0 < components.length) {
                            if (components[0].getIdentifier() != components2[0].getIdentifier()) {}
                            int n = 0;
                            ++n;
                        }
                        if (!true) {
                            list.add(new LinuxCombinedController(list2.remove(0), (LinuxJoystickAbstractController)list3.remove(0)));
                            --n2;
                            --n3;
                            break;
                        }
                    }
                }
                ++n3;
            }
            ++n2;
        }
        list.addAll((Collection<?>)list2);
        list.addAll((Collection<?>)list3);
        final Controller[] array = new Controller[list.size()];
        list.toArray((Object[])array);
        return array;
    }
    
    private static final Component.Identifier.Button getButtonIdentifier(final int n) {
        switch (n) {
            case 0: {
                return Component.Identifier.Button._0;
            }
            case 1: {
                return Component.Identifier.Button._1;
            }
            case 2: {
                return Component.Identifier.Button._2;
            }
            case 3: {
                return Component.Identifier.Button._3;
            }
            case 4: {
                return Component.Identifier.Button._4;
            }
            case 5: {
                return Component.Identifier.Button._5;
            }
            case 6: {
                return Component.Identifier.Button._6;
            }
            case 7: {
                return Component.Identifier.Button._7;
            }
            case 8: {
                return Component.Identifier.Button._8;
            }
            case 9: {
                return Component.Identifier.Button._9;
            }
            case 10: {
                return Component.Identifier.Button._10;
            }
            case 11: {
                return Component.Identifier.Button._11;
            }
            case 12: {
                return Component.Identifier.Button._12;
            }
            case 13: {
                return Component.Identifier.Button._13;
            }
            case 14: {
                return Component.Identifier.Button._14;
            }
            case 15: {
                return Component.Identifier.Button._15;
            }
            case 16: {
                return Component.Identifier.Button._16;
            }
            case 17: {
                return Component.Identifier.Button._17;
            }
            case 18: {
                return Component.Identifier.Button._18;
            }
            case 19: {
                return Component.Identifier.Button._19;
            }
            case 20: {
                return Component.Identifier.Button._20;
            }
            case 21: {
                return Component.Identifier.Button._21;
            }
            case 22: {
                return Component.Identifier.Button._22;
            }
            case 23: {
                return Component.Identifier.Button._23;
            }
            case 24: {
                return Component.Identifier.Button._24;
            }
            case 25: {
                return Component.Identifier.Button._25;
            }
            case 26: {
                return Component.Identifier.Button._26;
            }
            case 27: {
                return Component.Identifier.Button._27;
            }
            case 28: {
                return Component.Identifier.Button._28;
            }
            case 29: {
                return Component.Identifier.Button._29;
            }
            case 30: {
                return Component.Identifier.Button._30;
            }
            case 31: {
                return Component.Identifier.Button._31;
            }
            default: {
                return null;
            }
        }
    }
    
    private static final Controller createJoystickFromJoystickDevice(final LinuxJoystickDevice linuxJoystickDevice) {
        final ArrayList list = new ArrayList<LinuxJoystickButton>();
        final byte[] axisMap = linuxJoystickDevice.getAxisMap();
        final char[] buttonMap = linuxJoystickDevice.getButtonMap();
        final LinuxJoystickAxis[] array = new LinuxJoystickAxis[6];
        int n = 0;
        while (0 < linuxJoystickDevice.getNumButtons()) {
            final Component.Identifier buttonID = LinuxNativeTypesMap.getButtonID(buttonMap[0]);
            if (buttonID != null) {
                final LinuxJoystickButton linuxJoystickButton = new LinuxJoystickButton(buttonID);
                linuxJoystickDevice.registerButton(0, linuxJoystickButton);
                list.add(linuxJoystickButton);
            }
            ++n;
        }
        while (0 < linuxJoystickDevice.getNumAxes()) {
            final LinuxJoystickAxis linuxJoystickAxis = new LinuxJoystickAxis((Component.Identifier.Axis)LinuxNativeTypesMap.getAbsAxisID(axisMap[0]));
            linuxJoystickDevice.registerAxis(0, linuxJoystickAxis);
            if (axisMap[0] == 16) {
                array[0] = linuxJoystickAxis;
            }
            else if (axisMap[0] == 17) {
                array[1] = linuxJoystickAxis;
                final LinuxJoystickPOV linuxJoystickPOV = new LinuxJoystickPOV(Component.Identifier.Axis.POV, array[0], array[1]);
                linuxJoystickDevice.registerPOV(linuxJoystickPOV);
                list.add((LinuxJoystickButton)linuxJoystickPOV);
            }
            else if (axisMap[0] == 18) {
                array[2] = linuxJoystickAxis;
            }
            else if (axisMap[0] == 19) {
                array[3] = linuxJoystickAxis;
                final LinuxJoystickPOV linuxJoystickPOV2 = new LinuxJoystickPOV(Component.Identifier.Axis.POV, array[2], array[3]);
                linuxJoystickDevice.registerPOV(linuxJoystickPOV2);
                list.add((LinuxJoystickButton)linuxJoystickPOV2);
            }
            else if (axisMap[0] == 20) {
                array[4] = linuxJoystickAxis;
            }
            else if (axisMap[0] == 21) {
                array[5] = linuxJoystickAxis;
                final LinuxJoystickPOV linuxJoystickPOV3 = new LinuxJoystickPOV(Component.Identifier.Axis.POV, array[4], array[5]);
                linuxJoystickDevice.registerPOV(linuxJoystickPOV3);
                list.add((LinuxJoystickButton)linuxJoystickPOV3);
            }
            else {
                list.add((LinuxJoystickButton)linuxJoystickAxis);
            }
            ++n;
        }
        return new LinuxJoystickAbstractController(linuxJoystickDevice, (Component[])list.toArray(new Component[0]), new Controller[0], new Rumbler[0]);
    }
    
    private final void enumerateJoystickControllers(final List list) {
        File[] array = enumerateJoystickDeviceFiles("/dev/input");
        if (array == null || array.length == 0) {
            array = enumerateJoystickDeviceFiles("/dev");
            if (array == null) {
                return;
            }
        }
        while (0 < array.length) {
            final LinuxJoystickDevice linuxJoystickDevice = new LinuxJoystickDevice(getAbsolutePathPrivileged(array[0]));
            final Controller joystickFromJoystickDevice = createJoystickFromJoystickDevice(linuxJoystickDevice);
            if (joystickFromJoystickDevice != null) {
                list.add(joystickFromJoystickDevice);
                this.devices.add(linuxJoystickDevice);
            }
            else {
                linuxJoystickDevice.close();
            }
            int n = 0;
            ++n;
        }
    }
    
    private static final File[] enumerateJoystickDeviceFiles(final String s) {
        return listFilesPrivileged(new File(s), new FilenameFilter() {
            public final boolean accept(final File file, final String s) {
                return s.startsWith("js");
            }
        });
    }
    
    private static String getAbsolutePathPrivileged(final File file) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(file) {
            private final File val$file;
            
            public Object run() {
                return this.val$file.getAbsolutePath();
            }
        });
    }
    
    private static File[] listFilesPrivileged(final File file, final FilenameFilter filenameFilter) {
        return AccessController.doPrivileged((PrivilegedAction<File[]>)new PrivilegedAction(file, filenameFilter) {
            private final File val$dir;
            private final FilenameFilter val$filter;
            
            public Object run() {
                final File[] listFiles = this.val$dir.listFiles(this.val$filter);
                Arrays.sort(listFiles, new Comparator() {
                    private final LinuxEnvironmentPlugin$7 this$0;
                    
                    public int compare(final Object o, final Object o2) {
                        return ((File)o).getName().compareTo(((File)o2).getName());
                    }
                });
                return listFiles;
            }
        });
    }
    
    private final void enumerateEventControllers(final List list) {
        final File[] listFilesPrivileged = listFilesPrivileged(new File("/dev/input"), new FilenameFilter() {
            private final LinuxEnvironmentPlugin this$0;
            
            public final boolean accept(final File file, final String s) {
                return s.startsWith("event");
            }
        });
        if (listFilesPrivileged == null) {
            return;
        }
        while (0 < listFilesPrivileged.length) {
            final LinuxEventDevice linuxEventDevice = new LinuxEventDevice(getAbsolutePathPrivileged(listFilesPrivileged[0]));
            final Controller controllerFromDevice = createControllerFromDevice(linuxEventDevice);
            if (controllerFromDevice != null) {
                list.add(controllerFromDevice);
                this.devices.add(linuxEventDevice);
            }
            else {
                linuxEventDevice.close();
            }
            int n = 0;
            ++n;
        }
    }
    
    public boolean isSupported() {
        return LinuxEnvironmentPlugin.supported;
    }
    
    static boolean access$002(final boolean supported) {
        return LinuxEnvironmentPlugin.supported = supported;
    }
    
    static List access$200(final LinuxEnvironmentPlugin linuxEnvironmentPlugin) {
        return linuxEnvironmentPlugin.devices;
    }
    
    static {
        POSTFIX64BIT = "64";
        LIBNAME = "jinput-linux";
        LinuxEnvironmentPlugin.supported = false;
        device_thread = new LinuxDeviceThread();
        if (getPrivilegedProperty("os.name", "").trim().equals("Linux")) {
            LinuxEnvironmentPlugin.supported = true;
            if ("i386".equals(getPrivilegedProperty("os.arch"))) {
                loadLibrary("jinput-linux");
            }
            else {
                loadLibrary("jinput-linux64");
            }
        }
    }
    
    private final class ShutdownHook extends Thread
    {
        private final LinuxEnvironmentPlugin this$0;
        
        private ShutdownHook(final LinuxEnvironmentPlugin this$0) {
            this.this$0 = this$0;
        }
        
        public final void run() {
            while (0 < LinuxEnvironmentPlugin.access$200(this.this$0).size()) {
                LinuxEnvironmentPlugin.access$200(this.this$0).get(0).close();
                int n = 0;
                ++n;
            }
        }
        
        ShutdownHook(final LinuxEnvironmentPlugin linuxEnvironmentPlugin, final LinuxEnvironmentPlugin$1 privilegedAction) {
            this(linuxEnvironmentPlugin);
        }
    }
}
