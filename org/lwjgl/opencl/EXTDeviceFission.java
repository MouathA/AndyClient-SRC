package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class EXTDeviceFission
{
    public static final int CL_DEVICE_PARTITION_EQUALLY_EXT = 16464;
    public static final int CL_DEVICE_PARTITION_BY_COUNTS_EXT = 16465;
    public static final int CL_DEVICE_PARTITION_BY_NAMES_EXT = 16466;
    public static final int CL_DEVICE_PARTITION_BY_AFFINITY_DOMAIN_EXT = 16467;
    public static final int CL_AFFINITY_DOMAIN_L1_CACHE_EXT = 1;
    public static final int CL_AFFINITY_DOMAIN_L2_CACHE_EXT = 2;
    public static final int CL_AFFINITY_DOMAIN_L3_CACHE_EXT = 3;
    public static final int CL_AFFINITY_DOMAIN_L4_CACHE_EXT = 4;
    public static final int CL_AFFINITY_DOMAIN_NUMA_EXT = 16;
    public static final int CL_AFFINITY_DOMAIN_NEXT_FISSIONABLE_EXT = 256;
    public static final int CL_DEVICE_PARENT_DEVICE_EXT = 16468;
    public static final int CL_DEVICE_PARITION_TYPES_EXT = 16469;
    public static final int CL_DEVICE_AFFINITY_DOMAINS_EXT = 16470;
    public static final int CL_DEVICE_REFERENCE_COUNT_EXT = 16471;
    public static final int CL_DEVICE_PARTITION_STYLE_EXT = 16472;
    public static final int CL_PROPERTIES_LIST_END_EXT = 0;
    public static final int CL_PARTITION_BY_COUNTS_LIST_END_EXT = 0;
    public static final int CL_PARTITION_BY_NAMES_LIST_END_EXT = -1;
    public static final int CL_DEVICE_PARTITION_FAILED_EXT = -1057;
    public static final int CL_INVALID_PARTITION_COUNT_EXT = -1058;
    public static final int CL_INVALID_PARTITION_NAME_EXT = -1059;
    
    private EXTDeviceFission() {
    }
    
    public static int clRetainDeviceEXT(final CLDevice clDevice) {
        final long clRetainDeviceEXT = CLCapabilities.clRetainDeviceEXT;
        BufferChecks.checkFunctionAddress(clRetainDeviceEXT);
        final int nclRetainDeviceEXT = nclRetainDeviceEXT(clDevice.getPointer(), clRetainDeviceEXT);
        if (nclRetainDeviceEXT == 0) {
            clDevice.retain();
        }
        return nclRetainDeviceEXT;
    }
    
    static native int nclRetainDeviceEXT(final long p0, final long p1);
    
    public static int clReleaseDeviceEXT(final CLDevice clDevice) {
        final long clReleaseDeviceEXT = CLCapabilities.clReleaseDeviceEXT;
        BufferChecks.checkFunctionAddress(clReleaseDeviceEXT);
        APIUtil.releaseObjects(clDevice);
        final int nclReleaseDeviceEXT = nclReleaseDeviceEXT(clDevice.getPointer(), clReleaseDeviceEXT);
        if (nclReleaseDeviceEXT == 0) {
            clDevice.release();
        }
        return nclReleaseDeviceEXT;
    }
    
    static native int nclReleaseDeviceEXT(final long p0, final long p1);
    
    public static int clCreateSubDevicesEXT(final CLDevice clDevice, final LongBuffer longBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        final long clCreateSubDevicesEXT = CLCapabilities.clCreateSubDevicesEXT;
        BufferChecks.checkFunctionAddress(clCreateSubDevicesEXT);
        BufferChecks.checkDirect(longBuffer);
        BufferChecks.checkNullTerminated(longBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final int nclCreateSubDevicesEXT = nclCreateSubDevicesEXT(clDevice.getPointer(), MemoryUtil.getAddress(longBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateSubDevicesEXT);
        if (nclCreateSubDevicesEXT == 0 && pointerBuffer != null) {
            clDevice.registerSubCLDevices(pointerBuffer);
        }
        return nclCreateSubDevicesEXT;
    }
    
    static native int nclCreateSubDevicesEXT(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5);
}
