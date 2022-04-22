package net.java.games.input;

import java.io.*;

final class LinuxKeyboard extends Keyboard
{
    private final Controller.PortType port;
    private final LinuxEventDevice device;
    
    protected LinuxKeyboard(final LinuxEventDevice device, final Component[] array, final Controller[] array2, final Rumbler[] array3) throws IOException {
        super(device.getName(), array, array2, array3);
        this.device = device;
        this.port = device.getPortType();
    }
    
    public final Controller.PortType getPortType() {
        return this.port;
    }
    
    protected final boolean getNextDeviceEvent(final Event event) throws IOException {
        return LinuxControllers.getNextDeviceEvent(event, this.device);
    }
    
    public final void pollDevice() throws IOException {
        this.device.pollKeyStates();
    }
}
