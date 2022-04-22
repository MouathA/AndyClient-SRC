package net.minecraft.client.audio;

import java.util.*;
import com.google.common.collect.*;

public enum SoundCategory
{
    MASTER("MASTER", 0, "MASTER", 0, "master", 0), 
    MUSIC("MUSIC", 1, "MUSIC", 1, "music", 1), 
    RECORDS("RECORDS", 2, "RECORDS", 2, "record", 2), 
    WEATHER("WEATHER", 3, "WEATHER", 3, "weather", 3), 
    BLOCKS("BLOCKS", 4, "BLOCKS", 4, "block", 4), 
    MOBS("MOBS", 5, "MOBS", 5, "hostile", 5), 
    ANIMALS("ANIMALS", 6, "ANIMALS", 6, "neutral", 6), 
    PLAYERS("PLAYERS", 7, "PLAYERS", 7, "player", 7), 
    AMBIENT("AMBIENT", 8, "AMBIENT", 8, "ambient", 8);
    
    private static final Map field_147168_j;
    private static final Map field_147169_k;
    private final String categoryName;
    private final int categoryId;
    private static final SoundCategory[] $VALUES;
    private static final String __OBFID;
    private static final SoundCategory[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00001686";
        ENUM$VALUES = new SoundCategory[] { SoundCategory.MASTER, SoundCategory.MUSIC, SoundCategory.RECORDS, SoundCategory.WEATHER, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.ANIMALS, SoundCategory.PLAYERS, SoundCategory.AMBIENT };
        field_147168_j = Maps.newHashMap();
        field_147169_k = Maps.newHashMap();
        $VALUES = new SoundCategory[] { SoundCategory.MASTER, SoundCategory.MUSIC, SoundCategory.RECORDS, SoundCategory.WEATHER, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.ANIMALS, SoundCategory.PLAYERS, SoundCategory.AMBIENT };
        final SoundCategory[] values = values();
        while (0 < values.length) {
            final SoundCategory soundCategory = values[0];
            if (SoundCategory.field_147168_j.containsKey(soundCategory.getCategoryName()) || SoundCategory.field_147169_k.containsKey(soundCategory.getCategoryId())) {
                throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + soundCategory);
            }
            SoundCategory.field_147168_j.put(soundCategory.getCategoryName(), soundCategory);
            SoundCategory.field_147169_k.put(soundCategory.getCategoryId(), soundCategory);
            int n = 0;
            ++n;
        }
    }
    
    private SoundCategory(final String s, final int n, final String s2, final int n2, final String categoryName, final int categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return this.categoryName;
    }
    
    public int getCategoryId() {
        return this.categoryId;
    }
    
    public static SoundCategory func_147154_a(final String s) {
        return SoundCategory.field_147168_j.get(s);
    }
}
