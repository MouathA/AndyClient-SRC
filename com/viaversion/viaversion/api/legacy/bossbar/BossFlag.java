package com.viaversion.viaversion.api.legacy.bossbar;

public enum BossFlag
{
    DARKEN_SKY("DARKEN_SKY", 0, 1), 
    PLAY_BOSS_MUSIC("PLAY_BOSS_MUSIC", 1, 2), 
    CREATE_FOG("CREATE_FOG", 2, 4);
    
    private final int id;
    private static final BossFlag[] $VALUES;
    
    private BossFlag(final String s, final int n, final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    static {
        $VALUES = new BossFlag[] { BossFlag.DARKEN_SKY, BossFlag.PLAY_BOSS_MUSIC, BossFlag.CREATE_FOG };
    }
}
