package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import java.util.function.*;
import java.lang.reflect.*;

public class NamedSoundMapping
{
    private static final Map SOUNDS;
    
    public static String getOldId(String substring) {
        if (substring.startsWith("minecraft:")) {
            substring = substring.substring(10);
        }
        return NamedSoundMapping.SOUNDS.get(substring);
    }
    
    private static void lambda$static$0(final String s, final String s2) {
        NamedSoundMapping.SOUNDS.put(s2, s);
    }
    
    static {
        SOUNDS = new HashMap();
        final Field declaredField = NamedSoundRewriter.class.getDeclaredField("oldToNew");
        declaredField.setAccessible(true);
        ((Map)declaredField.get(null)).forEach(NamedSoundMapping::lambda$static$0);
    }
}
