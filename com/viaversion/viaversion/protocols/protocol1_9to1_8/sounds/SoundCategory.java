package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;

public enum SoundCategory
{
    MASTER("MASTER", 0, "master", 0), 
    MUSIC("MUSIC", 1, "music", 1), 
    RECORD("RECORD", 2, "record", 2), 
    WEATHER("WEATHER", 3, "weather", 3), 
    BLOCK("BLOCK", 4, "block", 4), 
    HOSTILE("HOSTILE", 5, "hostile", 5), 
    NEUTRAL("NEUTRAL", 6, "neutral", 6), 
    PLAYER("PLAYER", 7, "player", 7), 
    AMBIENT("AMBIENT", 8, "ambient", 8), 
    VOICE("VOICE", 9, "voice", 9);
    
    private final String name;
    private final int id;
    private static final SoundCategory[] $VALUES;
    
    private SoundCategory(final String s, final int n, final String name, final int id) {
        this.name = name;
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    static {
        $VALUES = new SoundCategory[] { SoundCategory.MASTER, SoundCategory.MUSIC, SoundCategory.RECORD, SoundCategory.WEATHER, SoundCategory.BLOCK, SoundCategory.HOSTILE, SoundCategory.NEUTRAL, SoundCategory.PLAYER, SoundCategory.AMBIENT, SoundCategory.VOICE };
    }
}
