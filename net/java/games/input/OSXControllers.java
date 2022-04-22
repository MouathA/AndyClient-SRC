package net.java.games.input;

import java.io.*;

final class OSXControllers
{
    private static final OSXEvent osx_event;
    
    public static final synchronized float poll(final OSXHIDElement osxhidElement) throws IOException {
        osxhidElement.getElementValue(OSXControllers.osx_event);
        return osxhidElement.convertValue((float)OSXControllers.osx_event.getValue());
    }
    
    public static final synchronized boolean getNextDeviceEvent(final Event event, final OSXHIDQueue osxhidQueue) throws IOException {
        if (osxhidQueue.getNextEvent(OSXControllers.osx_event)) {
            final OSXComponent mapEvent = osxhidQueue.mapEvent(OSXControllers.osx_event);
            event.set(mapEvent, mapEvent.getElement().convertValue((float)OSXControllers.osx_event.getValue()), OSXControllers.osx_event.getNanos());
            return true;
        }
        return false;
    }
    
    static {
        osx_event = new OSXEvent();
    }
}
