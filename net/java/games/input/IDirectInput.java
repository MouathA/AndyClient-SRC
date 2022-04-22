package net.java.games.input;

import java.util.*;
import java.io.*;

final class IDirectInput
{
    private final List devices;
    private final long idirectinput_address;
    private final DummyWindow window;
    
    public IDirectInput(final DummyWindow window) throws IOException {
        this.devices = new ArrayList();
        this.window = window;
        this.idirectinput_address = createIDirectInput();
        this.enumDevices();
    }
    
    private static final native long createIDirectInput() throws IOException;
    
    public final List getDevices() {
        return this.devices;
    }
    
    private final void enumDevices() throws IOException {
        this.nEnumDevices(this.idirectinput_address);
    }
    
    private final native void nEnumDevices(final long p0) throws IOException;
    
    private final void addDevice(final long n, final byte[] array, final byte[] array2, final int n2, final int n3, final String s, final String s2) throws IOException {
        this.devices.add(new IDirectInputDevice(this.window, n, array, array2, n2, n3, s, s2));
    }
    
    public final void releaseDevices() {
        while (0 < this.devices.size()) {
            this.devices.get(0).release();
            int n = 0;
            ++n;
        }
    }
    
    public final void release() {
        nRelease(this.idirectinput_address);
    }
    
    private static final native void nRelease(final long p0);
}
