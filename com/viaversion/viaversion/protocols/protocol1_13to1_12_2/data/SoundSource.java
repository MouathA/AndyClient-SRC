package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.*;

public enum SoundSource
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
    private static final SoundSource[] $VALUES;
    
    private SoundSource(final String s, final int n, final String name, final int id) {
        this.name = name;
        this.id = id;
    }
    
    public static Optional findBySource(final String s) {
        final SoundSource[] values = values();
        while (0 < values.length) {
            final SoundSource soundSource = values[0];
            if (soundSource.name.equalsIgnoreCase(s)) {
                return Optional.of(soundSource);
            }
            int n = 0;
            ++n;
        }
        return Optional.empty();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getId() {
        return this.id;
    }
    
    static {
        $VALUES = new SoundSource[] { SoundSource.MASTER, SoundSource.MUSIC, SoundSource.RECORD, SoundSource.WEATHER, SoundSource.BLOCK, SoundSource.HOSTILE, SoundSource.NEUTRAL, SoundSource.PLAYER, SoundSource.AMBIENT, SoundSource.VOICE };
    }
}
