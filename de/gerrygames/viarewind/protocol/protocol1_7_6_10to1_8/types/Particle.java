package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import java.util.*;

public enum Particle
{
    EXPLOSION_NORMAL("EXPLOSION_NORMAL", 0, "explode"), 
    EXPLOSION_LARGE("EXPLOSION_LARGE", 1, "largeexplode"), 
    EXPLOSION_HUGE("EXPLOSION_HUGE", 2, "hugeexplosion"), 
    FIREWORKS_SPARK("FIREWORKS_SPARK", 3, "fireworksSpark"), 
    WATER_BUBBLE("WATER_BUBBLE", 4, "bubble"), 
    WATER_SPLASH("WATER_SPLASH", 5, "splash"), 
    WATER_WAKE("WATER_WAKE", 6, "wake"), 
    SUSPENDED("SUSPENDED", 7, "suspended"), 
    SUSPENDED_DEPTH("SUSPENDED_DEPTH", 8, "depthsuspend"), 
    CRIT("CRIT", 9, "crit"), 
    CRIT_MAGIC("CRIT_MAGIC", 10, "magicCrit"), 
    SMOKE_NORMAL("SMOKE_NORMAL", 11, "smoke"), 
    SMOKE_LARGE("SMOKE_LARGE", 12, "largesmoke"), 
    SPELL("SPELL", 13, "spell"), 
    SPELL_INSTANT("SPELL_INSTANT", 14, "instantSpell"), 
    SPELL_MOB("SPELL_MOB", 15, "mobSpell"), 
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 16, "mobSpellAmbient"), 
    SPELL_WITCH("SPELL_WITCH", 17, "witchMagic"), 
    DRIP_WATER("DRIP_WATER", 18, "dripWater"), 
    DRIP_LAVA("DRIP_LAVA", 19, "dripLava"), 
    VILLAGER_ANGRY("VILLAGER_ANGRY", 20, "angryVillager"), 
    VILLAGER_HAPPY("VILLAGER_HAPPY", 21, "happyVillager"), 
    TOWN_AURA("TOWN_AURA", 22, "townaura"), 
    NOTE("NOTE", 23, "note"), 
    PORTAL("PORTAL", 24, "portal"), 
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 25, "enchantmenttable"), 
    FLAME("FLAME", 26, "flame"), 
    LAVA("LAVA", 27, "lava"), 
    FOOTSTEP("FOOTSTEP", 28, "footstep"), 
    CLOUD("CLOUD", 29, "cloud"), 
    REDSTONE("REDSTONE", 30, "reddust"), 
    SNOWBALL("SNOWBALL", 31, "snowballpoof"), 
    SNOW_SHOVEL("SNOW_SHOVEL", 32, "snowshovel"), 
    SLIME("SLIME", 33, "slime"), 
    HEART("HEART", 34, "heart"), 
    BARRIER("BARRIER", 35, "barrier"), 
    ICON_CRACK("ICON_CRACK", 36, "iconcrack", 2), 
    BLOCK_CRACK("BLOCK_CRACK", 37, "blockcrack", 1), 
    BLOCK_DUST("BLOCK_DUST", 38, "blockdust", 1), 
    WATER_DROP("WATER_DROP", 39, "droplet"), 
    ITEM_TAKE("ITEM_TAKE", 40, "take"), 
    MOB_APPEARANCE("MOB_APPEARANCE", 41, "mobappearance");
    
    public final String name;
    public final int extra;
    private static final HashMap particleMap;
    private static final Particle[] $VALUES;
    
    private Particle(final String s, final int n, final String s2) {
        this(s, n, s2, 0);
    }
    
    private Particle(final String s, final int n, final String name, final int extra) {
        this.name = name;
        this.extra = extra;
    }
    
    public static Particle find(final String s) {
        return Particle.particleMap.get(s);
    }
    
    public static Particle find(final int n) {
        if (n < 0) {
            return null;
        }
        final Particle[] values = values();
        return (n >= values.length) ? null : values[n];
    }
    
    static {
        $VALUES = new Particle[] { Particle.EXPLOSION_NORMAL, Particle.EXPLOSION_LARGE, Particle.EXPLOSION_HUGE, Particle.FIREWORKS_SPARK, Particle.WATER_BUBBLE, Particle.WATER_SPLASH, Particle.WATER_WAKE, Particle.SUSPENDED, Particle.SUSPENDED_DEPTH, Particle.CRIT, Particle.CRIT_MAGIC, Particle.SMOKE_NORMAL, Particle.SMOKE_LARGE, Particle.SPELL, Particle.SPELL_INSTANT, Particle.SPELL_MOB, Particle.SPELL_MOB_AMBIENT, Particle.SPELL_WITCH, Particle.DRIP_WATER, Particle.DRIP_LAVA, Particle.VILLAGER_ANGRY, Particle.VILLAGER_HAPPY, Particle.TOWN_AURA, Particle.NOTE, Particle.PORTAL, Particle.ENCHANTMENT_TABLE, Particle.FLAME, Particle.LAVA, Particle.FOOTSTEP, Particle.CLOUD, Particle.REDSTONE, Particle.SNOWBALL, Particle.SNOW_SHOVEL, Particle.SLIME, Particle.HEART, Particle.BARRIER, Particle.ICON_CRACK, Particle.BLOCK_CRACK, Particle.BLOCK_DUST, Particle.WATER_DROP, Particle.ITEM_TAKE, Particle.MOB_APPEARANCE };
        particleMap = new HashMap();
        final Particle[] values = values();
        while (0 < values.length) {
            final Particle particle = values[0];
            Particle.particleMap.put(particle.name, particle);
            int n = 0;
            ++n;
        }
    }
}
