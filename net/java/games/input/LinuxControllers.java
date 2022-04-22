package net.java.games.input;

import java.io.*;

final class LinuxControllers
{
    private static final LinuxEvent linux_event;
    private static final LinuxAbsInfo abs_info;
    
    public static final synchronized boolean getNextDeviceEvent(final Event event, final LinuxEventDevice linuxEventDevice) throws IOException {
        while (linuxEventDevice.getNextEvent(LinuxControllers.linux_event)) {
            final LinuxAxisDescriptor descriptor = LinuxControllers.linux_event.getDescriptor();
            final LinuxComponent mapDescriptor = linuxEventDevice.mapDescriptor(descriptor);
            if (mapDescriptor != null) {
                event.set(mapDescriptor, mapDescriptor.convertValue((float)LinuxControllers.linux_event.getValue(), descriptor), LinuxControllers.linux_event.getNanos());
                return true;
            }
        }
        return false;
    }
    
    public static final synchronized float poll(final LinuxEventComponent linuxEventComponent) throws IOException {
        final int type = linuxEventComponent.getDescriptor().getType();
        switch (type) {
            case 1: {
                return linuxEventComponent.getDevice().isKeySet(linuxEventComponent.getDescriptor().getCode()) ? 1.0f : 0.0f;
            }
            case 3: {
                linuxEventComponent.getAbsInfo(LinuxControllers.abs_info);
                return (float)LinuxControllers.abs_info.getValue();
            }
            default: {
                throw new RuntimeException("Unkown native_type: " + type);
            }
        }
    }
    
    static {
        linux_event = new LinuxEvent();
        abs_info = new LinuxAbsInfo();
    }
}
