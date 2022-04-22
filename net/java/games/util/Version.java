package net.java.games.util;

public final class Version
{
    private static final String version;
    
    private Version() {
    }
    
    public static String getVersion() {
        return "1.0.0-b01";
    }
    
    static {
        version = "1.0.0-b01";
    }
}
