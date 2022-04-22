package net.minecraft.scoreboard;

import java.util.*;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria
{
    private final String field_96644_g;
    private static final String __OBFID;
    
    public ScoreDummyCriteria(final String field_96644_g) {
        this.field_96644_g = field_96644_g;
        IScoreObjectiveCriteria.INSTANCES.put(field_96644_g, this);
    }
    
    @Override
    public String getName() {
        return this.field_96644_g;
    }
    
    @Override
    public int func_96635_a(final List list) {
        return 0;
    }
    
    @Override
    public boolean isReadOnly() {
        return false;
    }
    
    @Override
    public EnumRenderType func_178790_c() {
        return EnumRenderType.INTEGER;
    }
    
    static {
        __OBFID = "CL_00000622";
    }
}
