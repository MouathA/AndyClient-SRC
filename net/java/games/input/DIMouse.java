package net.java.games.input;

import java.io.*;

final class DIMouse extends Mouse
{
    private final IDirectInputDevice device;
    
    protected DIMouse(final IDirectInputDevice device, final Component[] array, final Controller[] array2, final Rumbler[] array3) {
        super(device.getProductName(), array, array2, array3);
        this.device = device;
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
}
