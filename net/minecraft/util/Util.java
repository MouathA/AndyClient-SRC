package net.minecraft.util;

public class Util
{
    private static final String __OBFID;
    
    public static EnumOS getOSType() {
        final String lowerCase = System.getProperty("os.name").toLowerCase();
        return lowerCase.contains("win") ? EnumOS.WINDOWS : (lowerCase.contains("mac") ? EnumOS.OSX : (lowerCase.contains("solaris") ? EnumOS.SOLARIS : (lowerCase.contains("sunos") ? EnumOS.SOLARIS : (lowerCase.contains("linux") ? EnumOS.LINUX : (lowerCase.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }
    
    static {
        __OBFID = "CL_00001633";
    }
    
    public enum EnumOS
    {
        LINUX("LINUX", 0, "LINUX", 0), 
        SOLARIS("SOLARIS", 1, "SOLARIS", 1), 
        WINDOWS("WINDOWS", 2, "WINDOWS", 2), 
        OSX("OSX", 3, "OSX", 3), 
        UNKNOWN("UNKNOWN", 4, "UNKNOWN", 4);
        
        private static final EnumOS[] $VALUES;
        private static final String __OBFID;
        private static final EnumOS[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001660";
            ENUM$VALUES = new EnumOS[] { EnumOS.LINUX, EnumOS.SOLARIS, EnumOS.WINDOWS, EnumOS.OSX, EnumOS.UNKNOWN };
            $VALUES = new EnumOS[] { EnumOS.LINUX, EnumOS.SOLARIS, EnumOS.WINDOWS, EnumOS.OSX, EnumOS.UNKNOWN };
        }
        
        private EnumOS(final String s, final int n, final String s2, final int n2) {
        }
    }
}
