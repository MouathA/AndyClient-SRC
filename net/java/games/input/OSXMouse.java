package net.java.games.input;

import java.io.*;

final class OSXMouse extends Mouse
{
    private final Controller.PortType port;
    private final OSXHIDQueue queue;
    
    protected OSXMouse(final OSXHIDDevice osxhidDevice, final OSXHIDQueue queue, final Component[] array, final Controller[] array2, final Rumbler[] array3) {
        super(osxhidDevice.getProductName(), array, array2, array3);
        this.queue = queue;
        this.port = osxhidDevice.getPortType();
    }
    
    protected final boolean getNextDeviceEvent(final Event event) throws IOException {
        return OSXControllers.getNextDeviceEvent(event, this.queue);
    }
    
    protected final void setDeviceEventQueueSize(final int queueDepth) throws IOException {
        this.queue.setQueueDepth(queueDepth);
    }
    
    public final Controller.PortType getPortType() {
        return this.port;
    }
}
