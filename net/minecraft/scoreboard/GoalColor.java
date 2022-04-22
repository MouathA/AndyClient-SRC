package net.minecraft.scoreboard;

import net.minecraft.util.*;
import java.util.*;

public class GoalColor implements IScoreObjectiveCriteria
{
    private final String field_178794_j;
    private static final String __OBFID;
    
    public GoalColor(final String s, final EnumChatFormatting enumChatFormatting) {
        this.field_178794_j = String.valueOf(s) + enumChatFormatting.getFriendlyName();
        IScoreObjectiveCriteria.INSTANCES.put(this.field_178794_j, this);
    }
    
    @Override
    public String getName() {
        return this.field_178794_j;
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
        __OBFID = "CL_00001961";
    }
}
