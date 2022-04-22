package net.minecraft.scoreboard;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class ScoreHealthCriteria extends ScoreDummyCriteria
{
    private static final String __OBFID;
    
    public ScoreHealthCriteria(final String s) {
        super(s);
    }
    
    @Override
    public int func_96635_a(final List list) {
        float n = 0.0f;
        for (final EntityPlayer entityPlayer : list) {
            n += entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount();
        }
        if (list.size() > 0) {
            n /= list.size();
        }
        return MathHelper.ceiling_float_int(n);
    }
    
    @Override
    public boolean isReadOnly() {
        return true;
    }
    
    @Override
    public IScoreObjectiveCriteria.EnumRenderType func_178790_c() {
        return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
    }
    
    static {
        __OBFID = "CL_00000623";
    }
}
