package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import java.util.*;

public class EntityNameRewrites
{
    private static final Map ENTITY_NAMES;
    
    private static void reg(final String s, final String s2) {
        EntityNameRewrites.ENTITY_NAMES.put("minecraft:" + s2, "minecraft:" + s);
    }
    
    public static String rewrite(final String s) {
        final String s2 = EntityNameRewrites.ENTITY_NAMES.get(s);
        if (s2 != null) {
            return s2;
        }
        final String s3 = EntityNameRewrites.ENTITY_NAMES.get("minecraft:" + s);
        if (s3 != null) {
            return s3;
        }
        return s;
    }
    
    static {
        ENTITY_NAMES = new HashMap();
        reg("commandblock_minecart", "command_block_minecart");
        reg("ender_crystal", "end_crystal");
        reg("evocation_fangs", "evoker_fangs");
        reg("evocation_illager", "evoker");
        reg("eye_of_ender_signal", "eye_of_ender");
        reg("fireworks_rocket", "firework_rocket");
        reg("illusion_illager", "illusioner");
        reg("snowman", "snow_golem");
        reg("villager_golem", "iron_golem");
        reg("vindication_illager", "vindicator");
        reg("xp_bottle", "experience_bottle");
        reg("xp_orb", "experience_orb");
    }
}
