package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.*;

public class EntityNameRewriter
{
    private static final Map entityNames;
    
    private static void reg(final String s, final String s2) {
        EntityNameRewriter.entityNames.put("minecraft:" + s, "minecraft:" + s2);
    }
    
    public static String rewrite(final String s) {
        final String s2 = EntityNameRewriter.entityNames.get(s);
        if (s2 != null) {
            return s2;
        }
        final String s3 = EntityNameRewriter.entityNames.get("minecraft:" + s);
        if (s3 != null) {
            return s3;
        }
        return s;
    }
    
    static {
        entityNames = new HashMap();
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
