package net.java.games.input;

import java.io.*;

final class LinuxConstantFF extends LinuxForceFeedbackEffect
{
    public LinuxConstantFF(final LinuxEventDevice linuxEventDevice) throws IOException {
        super(linuxEventDevice);
    }
    
    protected final int upload(final int n, final float n2) throws IOException {
        return this.getDevice().uploadConstantEffect(n, 0, 0, 0, 0, 0, Math.round(n2 * 32767.0f), 0, 0, 0, 0);
    }
}
