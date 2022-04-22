package org.lwjgl.opencl;

import org.lwjgl.*;

public final class CLDevice extends CLObjectChild
{
    private static final InfoUtil util;
    private final CLPlatform platform;
    private final CLObjectRegistry subCLDevices;
    private Object caps;
    
    CLDevice(final long n, final CLPlatform clPlatform) {
        this(n, null, clPlatform);
    }
    
    CLDevice(final long n, final CLDevice clDevice) {
        this(n, clDevice, clDevice.getPlatform());
    }
    
    CLDevice(final long n, final CLDevice clDevice, final CLPlatform platform) {
        super(n, clDevice);
        if (this.isValid()) {
            this.platform = platform;
            platform.getCLDeviceRegistry().registerObject(this);
            this.subCLDevices = new CLObjectRegistry();
            if (clDevice != null) {
                clDevice.subCLDevices.registerObject(this);
            }
        }
        else {
            this.platform = null;
            this.subCLDevices = null;
        }
    }
    
    public CLPlatform getPlatform() {
        return this.platform;
    }
    
    public CLDevice getSubCLDevice(final long n) {
        return (CLDevice)this.subCLDevices.getObject(n);
    }
    
    public String getInfoString(final int n) {
        return CLDevice.util.getInfoString(this, n);
    }
    
    public int getInfoInt(final int n) {
        return CLDevice.util.getInfoInt(this, n);
    }
    
    public boolean getInfoBoolean(final int n) {
        return CLDevice.util.getInfoInt(this, n) != 0;
    }
    
    public long getInfoSize(final int n) {
        return CLDevice.util.getInfoSize(this, n);
    }
    
    public long[] getInfoSizeArray(final int n) {
        return CLDevice.util.getInfoSizeArray(this, n);
    }
    
    public long getInfoLong(final int n) {
        return CLDevice.util.getInfoLong(this, n);
    }
    
    void setCapabilities(final Object caps) {
        this.caps = caps;
    }
    
    Object getCapabilities() {
        return this.caps;
    }
    
    @Override
    int retain() {
        if (this.getParent() == null) {
            return this.getReferenceCount();
        }
        return super.retain();
    }
    
    @Override
    int release() {
        if (this.getParent() == null) {
            return this.getReferenceCount();
        }
        final int release = super.release();
        if (!this.isValid()) {
            ((CLDevice)this.getParent()).subCLDevices.unregisterObject(this);
        }
        return release;
    }
    
    CLObjectRegistry getSubCLDeviceRegistry() {
        return this.subCLDevices;
    }
    
    void registerSubCLDevices(final PointerBuffer pointerBuffer) {
        for (int i = pointerBuffer.position(); i < pointerBuffer.limit(); ++i) {
            final long value = pointerBuffer.get(i);
            if (value != 0L) {
                new CLDevice(value, this);
            }
        }
    }
    
    static {
        util = CLPlatform.getInfoUtilInstance(CLDevice.class, "CL_DEVICE_UTIL");
    }
}
