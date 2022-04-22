package net.java.games.input;

import java.io.*;

final class DIControllers
{
    private static final DIDeviceObjectData di_event;
    
    public static final synchronized boolean getNextDeviceEvent(final Event event, final IDirectInputDevice directInputDevice) throws IOException {
        if (!directInputDevice.getNextEvent(DIControllers.di_event)) {
            return false;
        }
        final DIDeviceObject mapEvent = directInputDevice.mapEvent(DIControllers.di_event);
        final DIComponent mapObject = directInputDevice.mapObject(mapEvent);
        if (mapObject == null) {
            return false;
        }
        int n;
        if (mapEvent.isRelative()) {
            n = mapEvent.getRelativeEventValue(DIControllers.di_event.getData());
        }
        else {
            n = DIControllers.di_event.getData();
        }
        event.set(mapObject, mapObject.getDeviceObject().convertValue((float)n), DIControllers.di_event.getNanos());
        return true;
    }
    
    public static final float poll(final Component component, final DIDeviceObject diDeviceObject) throws IOException {
        final int pollData = diDeviceObject.getDevice().getPollData(diDeviceObject);
        float n;
        if (diDeviceObject.isRelative()) {
            n = (float)diDeviceObject.getRelativePollValue(pollData);
        }
        else {
            n = (float)pollData;
        }
        return diDeviceObject.convertValue(n);
    }
    
    static {
        di_event = new DIDeviceObjectData();
    }
}
