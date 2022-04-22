package com.sun.jna;

public final class Platform
{
    public static final int UNSPECIFIED = -1;
    public static final int MAC = 0;
    public static final int LINUX = 1;
    public static final int WINDOWS = 2;
    public static final int SOLARIS = 3;
    public static final int FREEBSD = 4;
    public static final int OPENBSD = 5;
    public static final int WINDOWSCE = 6;
    public static final boolean RO_FIELDS;
    public static final boolean HAS_BUFFERS;
    public static final boolean HAS_AWT;
    public static final String MATH_LIBRARY_NAME;
    public static final String C_LIBRARY_NAME;
    
    private Platform() {
    }
    
    public static final int getOSType() {
        return -1;
    }
    
    public static final boolean isMac() {
        return -1 == 0;
    }
    
    public static final boolean isLinux() {
        return -1 == 1;
    }
    
    public static final boolean isWindowsCE() {
        return -1 == 6;
    }
    
    public static final boolean isWindows() {
        return -1 == 2 || -1 == 6;
    }
    
    public static final boolean isSolaris() {
        return -1 == 3;
    }
    
    public static final boolean isFreeBSD() {
        return -1 == 4;
    }
    
    public static final boolean isOpenBSD() {
        return -1 == 5;
    }
    
    public static final boolean isX11() {
        return !isWindows() && !isMac();
    }
    
    public static final boolean hasRuntimeExec() {
        return !isWindowsCE() || !"J9".equals(System.getProperty("java.vm.name"));
    }
    
    public static final boolean is64Bit() {
        final String property = System.getProperty("sun.arch.data.model", System.getProperty("com.ibm.vm.bitmode"));
        if (property != null) {
            return "64".equals(property);
        }
        final String lowerCase = System.getProperty("os.arch").toLowerCase();
        return "x86_64".equals(lowerCase) || "ia64".equals(lowerCase) || "ppc64".equals(lowerCase) || "sparcv9".equals(lowerCase) || "amd64".equals(lowerCase) || Native.POINTER_SIZE == 8;
    }
    
    public static final boolean isIntel() {
        final String trim = System.getProperty("os.arch").toLowerCase().trim();
        return trim.equals("i386") || trim.equals("x86") || trim.equals("x86_64") || trim.equals("amd64");
    }
    
    public static final boolean isPPC() {
        final String trim = System.getProperty("os.arch").toLowerCase().trim();
        return trim.equals("ppc") || trim.equals("ppc64") || trim.equals("powerpc") || trim.equals("powerpc64");
    }
    
    public static final boolean isARM() {
        return System.getProperty("os.arch").toLowerCase().trim().equals("arm");
    }
    
    static {
        final String property = System.getProperty("os.name");
        if (!property.startsWith("Linux")) {
            if (!property.startsWith("Mac") && !property.startsWith("Darwin")) {
                if (!property.startsWith("Windows CE")) {
                    if (!property.startsWith("Windows")) {
                        if (!property.startsWith("Solaris") && !property.startsWith("SunOS")) {
                            if (!property.startsWith("FreeBSD")) {
                                if (property.startsWith("OpenBSD")) {}
                            }
                        }
                    }
                }
            }
        }
        Class.forName("java.awt.Component");
        HAS_AWT = true;
        Class.forName("java.nio.Buffer");
        HAS_BUFFERS = true;
        RO_FIELDS = (-1 != 6);
        C_LIBRARY_NAME = ((-1 == 2) ? "msvcrt" : ((-1 == 6) ? "coredll" : "c"));
        MATH_LIBRARY_NAME = ((-1 == 2) ? "msvcrt" : ((-1 == 6) ? "coredll" : "m"));
    }
}
