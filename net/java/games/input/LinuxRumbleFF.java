package net.java.games.input;

import java.io.*;

final class LinuxRumbleFF extends LinuxForceFeedbackEffect
{
    public LinuxRumbleFF(final LinuxEventDevice linuxEventDevice) throws IOException {
        super(linuxEventDevice);
    }
    
    protected final int upload(final int n, final float n2) throws IOException {
        if (n2 > 0.666666f) {
            final int n3 = (int)(32768.0f * n2);
            final int n4 = (int)(49152.0f * n2);
        }
        else if (n2 > 0.3333333f) {
            final int n5 = (int)(32768.0f * n2);
        }
        else {
            final int n6 = (int)(49152.0f * n2);
        }
        return this.getDevice().uploadRumbleEffect(n, 0, 0, 0, -1, 0, 0, 0);
    }
}
