package net.java.games.input;

import java.util.*;
import java.io.*;

final class OSXHIDQueue
{
    private final Map map;
    private final long queue_address;
    private boolean released;
    
    public OSXHIDQueue(final long queue_address, final int n) throws IOException {
        this.map = new HashMap();
        this.queue_address = queue_address;
        this.createQueue(n);
    }
    
    public final synchronized void setQueueDepth(final int n) throws IOException {
        this.checkReleased();
        this.stop();
        this.close();
        this.createQueue(n);
    }
    
    private final void createQueue(final int n) throws IOException {
        this.open(n);
        this.start();
    }
    
    public final OSXComponent mapEvent(final OSXEvent osxEvent) {
        return this.map.get(new Long(osxEvent.getCookie()));
    }
    
    private final void open(final int n) throws IOException {
        nOpen(this.queue_address, n);
    }
    
    private static final native void nOpen(final long p0, final int p1) throws IOException;
    
    private final void close() throws IOException {
        nClose(this.queue_address);
    }
    
    private static final native void nClose(final long p0) throws IOException;
    
    private final void start() throws IOException {
        nStart(this.queue_address);
    }
    
    private static final native void nStart(final long p0) throws IOException;
    
    private final void stop() throws IOException {
        nStop(this.queue_address);
    }
    
    private static final native void nStop(final long p0) throws IOException;
    
    public final synchronized void release() throws IOException {
        if (!this.released) {
            this.released = true;
            this.stop();
            this.close();
            nReleaseQueue(this.queue_address);
        }
    }
    
    private static final native void nReleaseQueue(final long p0) throws IOException;
    
    public final void addElement(final OSXHIDElement osxhidElement, final OSXComponent osxComponent) throws IOException {
        nAddElement(this.queue_address, osxhidElement.getCookie());
        this.map.put(new Long(osxhidElement.getCookie()), osxComponent);
    }
    
    private static final native void nAddElement(final long p0, final long p1) throws IOException;
    
    public final void removeElement(final OSXHIDElement osxhidElement) throws IOException {
        nRemoveElement(this.queue_address, osxhidElement.getCookie());
        this.map.remove(new Long(osxhidElement.getCookie()));
    }
    
    private static final native void nRemoveElement(final long p0, final long p1) throws IOException;
    
    public final synchronized boolean getNextEvent(final OSXEvent osxEvent) throws IOException {
        this.checkReleased();
        return nGetNextEvent(this.queue_address, osxEvent);
    }
    
    private static final native boolean nGetNextEvent(final long p0, final OSXEvent p1) throws IOException;
    
    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException("Queue is released");
        }
    }
}
