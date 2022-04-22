package net.java.games.input;

import java.io.*;

final class IDirectInputEffect implements Rumbler
{
    private final long address;
    private final DIEffectInfo info;
    private boolean released;
    
    public IDirectInputEffect(final long address, final DIEffectInfo info) {
        this.address = address;
        this.info = info;
    }
    
    public final synchronized void rumble(final float n) {
        this.checkReleased();
        if (n > 0.0f) {
            this.setGain(Math.round(n * 10000.0f));
            this.start(1, 0);
        }
        else {
            this.stop();
        }
    }
    
    public final Component.Identifier getAxisIdentifier() {
        return null;
    }
    
    public final String getAxisName() {
        return null;
    }
    
    public final synchronized void release() {
        if (!this.released) {
            this.released = true;
            nRelease(this.address);
        }
    }
    
    private static final native void nRelease(final long p0);
    
    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException();
        }
    }
    
    private final void setGain(final int n) throws IOException {
        final int nSetGain = nSetGain(this.address, n);
        if (nSetGain != 3 && nSetGain != 4 && nSetGain != 0 && nSetGain != 8 && nSetGain != 12) {
            throw new IOException("Failed to set effect gain (0x" + Integer.toHexString(nSetGain) + ")");
        }
    }
    
    private static final native int nSetGain(final long p0, final int p1);
    
    private final void start(final int n, final int n2) throws IOException {
        final int nStart = nStart(this.address, n, n2);
        if (nStart != 0) {
            throw new IOException("Failed to start effect (0x" + Integer.toHexString(nStart) + ")");
        }
    }
    
    private static final native int nStart(final long p0, final int p1, final int p2);
    
    private final void stop() throws IOException {
        final int nStop = nStop(this.address);
        if (nStop != 0) {
            throw new IOException("Failed to stop effect (0x" + Integer.toHexString(nStop) + ")");
        }
    }
    
    private static final native int nStop(final long p0);
    
    protected void finalize() {
        this.release();
    }
}
