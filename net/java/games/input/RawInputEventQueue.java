package net.java.games.input;

import java.io.*;
import java.util.*;

final class RawInputEventQueue
{
    private final Object monitor;
    private List devices;
    
    RawInputEventQueue() {
        this.monitor = new Object();
    }
    
    public final void start(final List devices) throws IOException {
        this.devices = devices;
        final QueueThread queueThread = new QueueThread();
        // monitorenter(monitor = this.monitor)
        queueThread.start();
        while (!queueThread.isInitialized()) {
            this.monitor.wait();
        }
        // monitorexit(monitor)
        if (queueThread.getException() != null) {
            throw queueThread.getException();
        }
    }
    
    private final RawDevice lookupDevice(final long n) {
        while (0 < this.devices.size()) {
            final RawDevice rawDevice = this.devices.get(0);
            if (rawDevice.getHandle() == n) {
                return rawDevice;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    private final void addMouseEvent(final long n, final long n2, final int n3, final int n4, final int n5, final long n6, final long n7, final long n8, final long n9) {
        final RawDevice lookupDevice = this.lookupDevice(n);
        if (lookupDevice == null) {
            return;
        }
        lookupDevice.addMouseEvent(n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    private final void addKeyboardEvent(final long n, final long n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final RawDevice lookupDevice = this.lookupDevice(n);
        if (lookupDevice == null) {
            return;
        }
        lookupDevice.addKeyboardEvent(n2, n3, n4, n5, n6, n7);
    }
    
    private final void poll(final DummyWindow dummyWindow) throws IOException {
        this.nPoll(dummyWindow.getHwnd());
    }
    
    private final native void nPoll(final long p0) throws IOException;
    
    private static final void registerDevices(final DummyWindow dummyWindow, final RawDeviceInfo[] array) throws IOException {
        nRegisterDevices(0, dummyWindow.getHwnd(), array);
    }
    
    private static final native void nRegisterDevices(final int p0, final long p1, final RawDeviceInfo[] p2) throws IOException;
    
    static Object access$000(final RawInputEventQueue rawInputEventQueue) {
        return rawInputEventQueue.monitor;
    }
    
    static List access$100(final RawInputEventQueue rawInputEventQueue) {
        return rawInputEventQueue.devices;
    }
    
    static void access$200(final DummyWindow dummyWindow, final RawDeviceInfo[] array) throws IOException {
        registerDevices(dummyWindow, array);
    }
    
    static void access$300(final RawInputEventQueue rawInputEventQueue, final DummyWindow dummyWindow) throws IOException {
        rawInputEventQueue.poll(dummyWindow);
    }
    
    private final class QueueThread extends Thread
    {
        private boolean initialized;
        private DummyWindow window;
        private IOException exception;
        private final RawInputEventQueue this$0;
        
        public QueueThread(final RawInputEventQueue this$0) {
            this.this$0 = this$0;
            this.setDaemon(true);
        }
        
        public final boolean isInitialized() {
            return this.initialized;
        }
        
        public final IOException getException() {
            return this.exception;
        }
        
        public final void run() {
            this.window = new DummyWindow();
            this.initialized = true;
            // monitorenter(access$000 = RawInputEventQueue.access$000(this.this$0))
            RawInputEventQueue.access$000(this.this$0).notify();
            // monitorexit(access$000)
            if (this.exception != null) {
                return;
            }
            final HashSet set = new HashSet<RawDeviceInfo>();
            while (0 < RawInputEventQueue.access$100(this.this$0).size()) {
                set.add(RawInputEventQueue.access$100(this.this$0).get(0).getInfo());
                int n = 0;
                ++n;
            }
            final RawDeviceInfo[] array = new RawDeviceInfo[set.size()];
            set.toArray(array);
            RawInputEventQueue.access$200(this.window, array);
            while (!this.isInterrupted()) {
                RawInputEventQueue.access$300(this.this$0, this.window);
            }
            this.window.destroy();
        }
    }
}
