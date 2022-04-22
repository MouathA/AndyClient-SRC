package com.sun.jna.platform.win32;

import com.sun.jna.*;

public interface Guid
{
    public static class GUID extends Structure
    {
        public int Data1;
        public short Data2;
        public short Data3;
        public byte[] Data4;
        
        public GUID() {
            this.Data4 = new byte[8];
        }
        
        public GUID(final Pointer pointer) {
            super(pointer);
            this.Data4 = new byte[8];
            this.read();
        }
        
        public GUID(final byte[] array) {
            this.Data4 = new byte[8];
            if (array.length != 16) {
                throw new IllegalArgumentException("Invalid data length: " + array.length);
            }
            this.Data1 = (int)((((long)(array[3] & 0xFF) << 8 | (long)(array[2] & 0xFF)) << 8 | (long)(array[1] & 0xFF)) << 8 | (long)(array[0] & 0xFF));
            this.Data2 = (short)((array[5] & 0xFF) << 8 | (array[4] & 0xFF));
            this.Data3 = (short)((array[7] & 0xFF) << 8 | (array[6] & 0xFF));
            this.Data4[0] = array[8];
            this.Data4[1] = array[9];
            this.Data4[2] = array[10];
            this.Data4[3] = array[11];
            this.Data4[4] = array[12];
            this.Data4[5] = array[13];
            this.Data4[6] = array[14];
            this.Data4[7] = array[15];
        }
        
        public static class ByReference extends GUID implements Structure.ByReference
        {
            public ByReference() {
            }
            
            public ByReference(final GUID guid) {
                super(guid.getPointer());
                this.Data1 = guid.Data1;
                this.Data2 = guid.Data2;
                this.Data3 = guid.Data3;
                this.Data4 = guid.Data4;
            }
            
            public ByReference(final Pointer pointer) {
                super(pointer);
            }
        }
    }
}
