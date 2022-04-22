package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;
import com.sun.jna.*;

public interface Wdm extends StdCallLibrary
{
    public abstract static class KEY_INFORMATION_CLASS
    {
        public static final int KeyBasicInformation = 0;
        public static final int KeyNodeInformation = 1;
        public static final int KeyFullInformation = 2;
        public static final int KeyNameInformation = 3;
        public static final int KeyCachedInformation = 4;
        public static final int KeyVirtualizationInformation = 5;
    }
    
    public static class KEY_BASIC_INFORMATION extends Structure
    {
        public long LastWriteTime;
        public int TitleIndex;
        public int NameLength;
        public char[] Name;
        
        public KEY_BASIC_INFORMATION() {
        }
        
        public KEY_BASIC_INFORMATION(final int n) {
            this.NameLength = n - 16;
            this.Name = new char[this.NameLength];
            this.allocateMemory();
        }
        
        public KEY_BASIC_INFORMATION(final Pointer pointer) {
            super(pointer);
            this.read();
        }
        
        public String getName() {
            return Native.toString(this.Name);
        }
        
        @Override
        public void read() {
            super.read();
            this.Name = new char[this.NameLength / 2];
            this.readField("Name");
        }
    }
}
