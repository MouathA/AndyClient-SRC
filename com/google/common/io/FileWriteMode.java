package com.google.common.io;

public enum FileWriteMode
{
    APPEND("APPEND", 0);
    
    private static final FileWriteMode[] $VALUES;
    
    private FileWriteMode(final String s, final int n) {
    }
    
    static {
        $VALUES = new FileWriteMode[] { FileWriteMode.APPEND };
    }
}
