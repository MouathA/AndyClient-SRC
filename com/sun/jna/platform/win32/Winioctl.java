package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;
import com.sun.jna.*;

public interface Winioctl extends StdCallLibrary
{
    public static final int IOCTL_STORAGE_GET_DEVICE_NUMBER = 2953344;
    
    public static class STORAGE_DEVICE_NUMBER extends Structure
    {
        public int DeviceType;
        public int DeviceNumber;
        public int PartitionNumber;
        
        public STORAGE_DEVICE_NUMBER() {
        }
        
        public STORAGE_DEVICE_NUMBER(final Pointer pointer) {
            super(pointer);
            this.read();
        }
        
        public static class ByReference extends STORAGE_DEVICE_NUMBER implements Structure.ByReference
        {
            public ByReference() {
            }
            
            public ByReference(final Pointer pointer) {
                super(pointer);
            }
        }
    }
}
