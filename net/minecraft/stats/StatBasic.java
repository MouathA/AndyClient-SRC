package net.minecraft.stats;

import net.minecraft.util.*;

public class StatBasic extends StatBase
{
    private static final String __OBFID;
    
    public StatBasic(final String s, final IChatComponent chatComponent, final IStatType statType) {
        super(s, chatComponent, statType);
    }
    
    public StatBasic(final String s, final IChatComponent chatComponent) {
        super(s, chatComponent);
    }
    
    @Override
    public StatBase registerStat() {
        super.registerStat();
        StatList.generalStats.add(this);
        return this;
    }
    
    static {
        __OBFID = "CL_00001469";
    }
}
