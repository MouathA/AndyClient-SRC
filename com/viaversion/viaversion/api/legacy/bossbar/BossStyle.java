package com.viaversion.viaversion.api.legacy.bossbar;

public enum BossStyle
{
    SOLID("SOLID", 0, 0), 
    SEGMENTED_6("SEGMENTED_6", 1, 1), 
    SEGMENTED_10("SEGMENTED_10", 2, 2), 
    SEGMENTED_12("SEGMENTED_12", 3, 3), 
    SEGMENTED_20("SEGMENTED_20", 4, 4);
    
    private final int id;
    private static final BossStyle[] $VALUES;
    
    private BossStyle(final String s, final int n, final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    static {
        $VALUES = new BossStyle[] { BossStyle.SOLID, BossStyle.SEGMENTED_6, BossStyle.SEGMENTED_10, BossStyle.SEGMENTED_12, BossStyle.SEGMENTED_20 };
    }
}
