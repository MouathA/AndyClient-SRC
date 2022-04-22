package com.viaversion.viaversion.api.legacy.bossbar;

public enum BossColor
{
    PINK("PINK", 0, 0), 
    BLUE("BLUE", 1, 1), 
    RED("RED", 2, 2), 
    GREEN("GREEN", 3, 3), 
    YELLOW("YELLOW", 4, 4), 
    PURPLE("PURPLE", 5, 5), 
    WHITE("WHITE", 6, 6);
    
    private final int id;
    private static final BossColor[] $VALUES;
    
    private BossColor(final String s, final int n, final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    static {
        $VALUES = new BossColor[] { BossColor.PINK, BossColor.BLUE, BossColor.RED, BossColor.GREEN, BossColor.YELLOW, BossColor.PURPLE, BossColor.WHITE };
    }
}
