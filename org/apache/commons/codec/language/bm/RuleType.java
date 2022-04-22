package org.apache.commons.codec.language.bm;

public enum RuleType
{
    APPROX("APPROX", 0, "approx"), 
    EXACT("EXACT", 1, "exact"), 
    RULES("RULES", 2, "rules");
    
    private final String name;
    private static final RuleType[] $VALUES;
    
    private RuleType(final String s, final int n, final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    static {
        $VALUES = new RuleType[] { RuleType.APPROX, RuleType.EXACT, RuleType.RULES };
    }
}
