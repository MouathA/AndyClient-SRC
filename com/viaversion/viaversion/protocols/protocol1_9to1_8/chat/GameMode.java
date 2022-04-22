package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

public enum GameMode
{
    SURVIVAL("SURVIVAL", 0, 0, "Survival Mode"), 
    CREATIVE("CREATIVE", 1, 1, "Creative Mode"), 
    ADVENTURE("ADVENTURE", 2, 2, "Adventure Mode"), 
    SPECTATOR("SPECTATOR", 3, 3, "Spectator Mode");
    
    private final int id;
    private final String text;
    private static final GameMode[] $VALUES;
    
    private GameMode(final String s, final int n, final int id, final String text) {
        this.id = id;
        this.text = text;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getText() {
        return this.text;
    }
    
    public static GameMode getById(final int n) {
        final GameMode[] values = values();
        while (0 < values.length) {
            final GameMode gameMode = values[0];
            if (gameMode.getId() == n) {
                return gameMode;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    static {
        $VALUES = new GameMode[] { GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR };
    }
}
