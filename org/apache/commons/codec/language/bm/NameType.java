package org.apache.commons.codec.language.bm;

public enum NameType
{
    ASHKENAZI("ASHKENAZI", 0, "ash"), 
    GENERIC("GENERIC", 1, "gen"), 
    SEPHARDIC("SEPHARDIC", 2, "sep");
    
    private final String name;
    private static final NameType[] $VALUES;
    
    private NameType(final String s, final int n, final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    static {
        $VALUES = new NameType[] { NameType.ASHKENAZI, NameType.GENERIC, NameType.SEPHARDIC };
    }
}
