package net.java.games.input;

import java.io.*;

class RawHIDInfo extends RawDeviceInfo
{
    private final RawDevice device;
    private final int vendor_id;
    private final int product_id;
    private final int version;
    private final int page;
    private final int usage;
    
    public RawHIDInfo(final RawDevice device, final int vendor_id, final int product_id, final int version, final int page, final int usage) {
        this.device = device;
        this.vendor_id = vendor_id;
        this.product_id = product_id;
        this.version = version;
        this.page = page;
        this.usage = usage;
    }
    
    public final int getUsage() {
        return this.usage;
    }
    
    public final int getUsagePage() {
        return this.page;
    }
    
    public final long getHandle() {
        return this.device.getHandle();
    }
    
    public final Controller createControllerFromDevice(final RawDevice rawDevice, final SetupAPIDevice setupAPIDevice) throws IOException {
        return null;
    }
}
