package net.minecraft.client.stream;

import net.minecraft.stats.*;

public class MetadataAchievement extends Metadata
{
    private static final String __OBFID;
    
    public MetadataAchievement(final Achievement achievement) {
        super("achievement");
        this.func_152808_a("achievement_id", achievement.statId);
        this.func_152808_a("achievement_name", achievement.getStatName().getUnformattedText());
        this.func_152808_a("achievement_description", achievement.getDescription());
        this.func_152807_a("Achievement '" + achievement.getStatName().getUnformattedText() + "' obtained!");
    }
    
    static {
        __OBFID = "CL_00001824";
    }
}
