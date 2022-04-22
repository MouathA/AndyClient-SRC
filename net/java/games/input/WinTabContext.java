package net.java.games.input;

import java.util.*;

public class WinTabContext
{
    private DummyWindow window;
    private long hCTX;
    private Controller[] controllers;
    
    public WinTabContext(final DummyWindow window) {
        this.window = window;
    }
    
    public Controller[] getControllers() {
        if (this.hCTX == 0L) {
            throw new IllegalStateException("Context must be open before getting the controllers");
        }
        return this.controllers;
    }
    
    public synchronized void open() {
        this.hCTX = nOpen(this.window.getHwnd());
        final ArrayList list = new ArrayList<WinTabDevice>();
        while (0 < nGetNumberOfSupportedDevices()) {
            final WinTabDevice device = WinTabDevice.createDevice(this, 0);
            if (device != null) {
                list.add(device);
            }
            int n = 0;
            ++n;
        }
        this.controllers = (Controller[])list.toArray(new Controller[0]);
    }
    
    public synchronized void close() {
        nClose(this.hCTX);
    }
    
    public synchronized void processEvents() {
        final WinTabPacket[] nGetPackets = nGetPackets(this.hCTX);
        while (0 < nGetPackets.length) {
            ((WinTabDevice)this.getControllers()[0]).processPacket(nGetPackets[0]);
            int n = 0;
            ++n;
        }
    }
    
    private static final native int nGetNumberOfSupportedDevices();
    
    private static final native long nOpen(final long p0);
    
    private static final native void nClose(final long p0);
    
    private static final native WinTabPacket[] nGetPackets(final long p0);
}
