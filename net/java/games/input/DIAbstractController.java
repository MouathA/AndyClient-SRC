package net.java.games.input;

import java.io.*;

final class DIAbstractController extends AbstractController
{
    private final IDirectInputDevice device;
    private final Controller.Type type;
    
    protected DIAbstractController(final IDirectInputDevice device, final Component[] array, final Controller[] array2, final Rumbler[] array3, final Controller.Type type) {
        super(device.getProductName(), array, array2, array3);
        this.device = device;
        this.type = type;
    }
    
    public final void pollDevice() throws IOException {
        this.device.pollAll();
    }
    
    protected final boolean getNextDeviceEvent(final Event event) throws IOException {
        return DIControllers.getNextDeviceEvent(event, this.device);
    }
    
    protected final void setDeviceEventQueueSize(final int bufferSize) throws IOException {
        this.device.setBufferSize(bufferSize);
    }
    
    public final Controller.Type getType() {
        return this.type;
    }
}
