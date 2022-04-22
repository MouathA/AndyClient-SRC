package oshi;

public enum PlatformEnum
{
    WINDOWS("WINDOWS", 0), 
    LINUX("LINUX", 1), 
    MACOSX("MACOSX", 2), 
    UNKNOWN("UNKNOWN", 3);
    
    private static final PlatformEnum[] $VALUES;
    
    private PlatformEnum(final String s, final int n) {
    }
    
    static {
        $VALUES = new PlatformEnum[] { PlatformEnum.WINDOWS, PlatformEnum.LINUX, PlatformEnum.MACOSX, PlatformEnum.UNKNOWN };
    }
}
