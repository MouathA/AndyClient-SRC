package com.viaversion.viaversion.api.minecraft;

public enum Environment
{
    NORMAL("NORMAL", 0, 0), 
    NETHER("NETHER", 1, -1), 
    END("END", 2, 1);
    
    private final int id;
    private static final Environment[] $VALUES;
    
    private Environment(final String s, final int n, final int id) {
        this.id = id;
    }
    
    public int id() {
        return this.id;
    }
    
    @Deprecated
    public int getId() {
        return this.id;
    }
    
    public static Environment getEnvironmentById(final int n) {
        switch (n) {
            default: {
                return Environment.NETHER;
            }
            case 0: {
                return Environment.NORMAL;
            }
            case 1: {
                return Environment.END;
            }
        }
    }
    
    static {
        $VALUES = new Environment[] { Environment.NORMAL, Environment.NETHER, Environment.END };
    }
}
