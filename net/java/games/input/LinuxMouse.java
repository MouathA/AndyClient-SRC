package net.java.games.input;

import java.io.*;

final class LinuxMouse extends Mouse
{
    private final Controller.PortType port;
    private final LinuxEventDevice device;
    
    protected LinuxMouse(final LinuxEventDevice device, final Component[] array, final Controller[] array2, final Rumbler[] array3) throws IOException {
        super(device.getName(), array, array2, array3);
        this.device = device;
        this.port = device.getPortType();
    }
    
    public final Controller.PortType getPortType() {
        return this.port;
    }
    
    public final void pollDevice() throws IOException {
        this.device.pollKeyStates();
    }
    
    protected final boolean getNextDeviceEvent(final Event event) throws IOException {
        return LinuxControllers.getNextDeviceEvent(event, this.device);
    }
}
