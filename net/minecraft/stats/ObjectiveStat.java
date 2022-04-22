package net.minecraft.stats;

import net.minecraft.scoreboard.*;

public class ObjectiveStat extends ScoreDummyCriteria
{
    private final StatBase field_151459_g;
    private static final String __OBFID;
    
    public ObjectiveStat(final StatBase field_151459_g) {
        super(field_151459_g.statId);
        this.field_151459_g = field_151459_g;
    }
    
    static {
        __OBFID = "CL_00000625";
    }
}
