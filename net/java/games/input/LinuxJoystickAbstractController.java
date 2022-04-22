package net.java.games.input;

import java.io.*;

final class LinuxJoystickAbstractController extends AbstractController
{
    private final LinuxJoystickDevice device;
    
    protected LinuxJoystickAbstractController(final LinuxJoystickDevice device, final Component[] array, final Controller[] array2, final Rumbler[] array3) {
        super(device.getName(), array, array2, array3);
        this.device = device;
    }
    
    protected final void setDeviceEventQueueSize(final int bufferSize) throws IOException {
        this.device.setBufferSize(bufferSize);
    }
    
    public final void pollDevice() throws IOException {
        this.device.poll();
    }
    
    protected final boolean getNextDeviceEvent(final Event event) throws IOException {
        return this.device.getNextEvent(event);
    }
    
    public Controller.Type getType() {
        return Controller.Type.STICK;
    }
}
