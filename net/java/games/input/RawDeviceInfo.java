package net.java.games.input;

import java.io.*;

abstract class RawDeviceInfo
{
    public abstract Controller createControllerFromDevice(final RawDevice p0, final SetupAPIDevice p1) throws IOException;
    
    public abstract int getUsage();
    
    public abstract int getUsagePage();
    
    public abstract long getHandle();
    
    public final boolean equals(final Object o) {
        if (!(o instanceof RawDeviceInfo)) {
            return false;
        }
        final RawDeviceInfo rawDeviceInfo = (RawDeviceInfo)o;
        return rawDeviceInfo.getUsage() == this.getUsage() && rawDeviceInfo.getUsagePage() == this.getUsagePage();
    }
    
    public final int hashCode() {
        return this.getUsage() ^ this.getUsagePage();
    }
}
