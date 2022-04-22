package net.java.games.input;

import java.io.*;

final class LinuxAbstractController extends AbstractController
{
    private final Controller.PortType port;
    private final LinuxEventDevice device;
    private final Controller.Type type;
    
    protected LinuxAbstractController(final LinuxEventDevice device, final Component[] array, final Controller[] array2, final Rumbler[] array3, final Controller.Type type) throws IOException {
        super(device.getName(), array, array2, array3);
        this.device = device;
        this.port = device.getPortType();
        this.type = type;
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
    
    public Controller.Type getType() {
        return this.type;
    }
}
