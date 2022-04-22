package net.minecraft.stats;

import net.minecraft.scoreboard.*;
import java.text.*;
import java.util.*;
import net.minecraft.event.*;
import net.minecraft.util.*;

public class StatBase
{
    public final String statId;
    private final IChatComponent statName;
    public boolean isIndependent;
    private final IStatType type;
    private final IScoreObjectiveCriteria field_150957_c;
    private Class field_150956_d;
    private static NumberFormat numberFormat;
    public static IStatType simpleStatType;
    private static DecimalFormat decimalFormat;
    public static IStatType timeStatType;
    public static IStatType distanceStatType;
    public static IStatType field_111202_k;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001472";
        StatBase.numberFormat = NumberFormat.getIntegerInstance(Locale.US);
        StatBase.simpleStatType = new IStatType() {
            private static final String __OBFID;
            
            @Override
            public String format(final int n) {
                return StatBase.access$0().format(n);
            }
            
            static {
                __OBFID = "CL_00001473";
            }
        };
        StatBase.decimalFormat = new DecimalFormat("########0.00");
        StatBase.timeStatType = new IStatType() {
            private static final String __OBFID;
            
            @Override
            public String format(final int n) {
                final double n2 = n / 20.0;
                final double n3 = n2 / 60.0;
                final double n4 = n3 / 60.0;
                final double n5 = n4 / 24.0;
                final double n6 = n5 / 365.0;
                return (n6 > 0.5) ? (String.valueOf(StatBase.access$1().format(n6)) + " y") : ((n5 > 0.5) ? (String.valueOf(StatBase.access$1().format(n5)) + " d") : ((n4 > 0.5) ? (String.valueOf(StatBase.access$1().format(n4)) + " h") : ((n3 > 0.5) ? (String.valueOf(StatBase.access$1().format(n3)) + " m") : (String.valueOf(n2) + " s"))));
            }
            
            static {
                __OBFID = "CL_00001474";
            }
        };
        StatBase.distanceStatType = new IStatType() {
            private static final String __OBFID;
            
            @Override
            public String format(final int n) {
                final double n2 = n / 100.0;
                final double n3 = n2 / 1000.0;
                return (n3 > 0.5) ? (String.valueOf(StatBase.access$1().format(n3)) + " km") : ((n2 > 0.5) ? (String.valueOf(StatBase.access$1().format(n2)) + " m") : (String.valueOf(n) + " cm"));
            }
            
            static {
                __OBFID = "CL_00001475";
            }
        };
        StatBase.field_111202_k = new IStatType() {
            private static final String __OBFID;
            
            @Override
            public String format(final int n) {
                return StatBase.access$1().format(n * 0.1);
            }
            
            static {
                __OBFID = "CL_00001476";
            }
        };
    }
    
    public StatBase(final String statId, final IChatComponent statName, final IStatType type) {
        this.statId = statId;
        this.statName = statName;
        this.type = type;
        this.field_150957_c = new ObjectiveStat(this);
        IScoreObjectiveCriteria.INSTANCES.put(this.field_150957_c.getName(), this.field_150957_c);
    }
    
    public StatBase(final String s, final IChatComponent chatComponent) {
        this(s, chatComponent, StatBase.simpleStatType);
    }
    
    public StatBase initIndependentStat() {
        this.isIndependent = true;
        return this;
    }
    
    public StatBase registerStat() {
        if (StatList.oneShotStats.containsKey(this.statId)) {
            throw new RuntimeException("Duplicate stat id: \"" + StatList.oneShotStats.get(this.statId).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
        }
        StatList.allStats.add(this);
        StatList.oneShotStats.put(this.statId, this);
        return this;
    }
    
    public boolean isAchievement() {
        return false;
    }
    
    public String func_75968_a(final int n) {
        return this.type.format(n);
    }
    
    public IChatComponent getStatName() {
        final IChatComponent copy = this.statName.createCopy();
        copy.getChatStyle().setColor(EnumChatFormatting.GRAY);
        copy.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
        return copy;
    }
    
    public IChatComponent func_150955_j() {
        final IChatComponent statName = this.getStatName();
        final IChatComponent appendText = new ChatComponentText("[").appendSibling(statName).appendText("]");
        appendText.setChatStyle(statName.getChatStyle());
        return appendText;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.statId.equals(((StatBase)o).statId));
    }
    
    @Override
    public int hashCode() {
        return this.statId.hashCode();
    }
    
    @Override
    public String toString() {
        return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.field_150957_c + '}';
    }
    
    public IScoreObjectiveCriteria func_150952_k() {
        return this.field_150957_c;
    }
    
    public Class func_150954_l() {
        return this.field_150956_d;
    }
    
    public StatBase func_150953_b(final Class field_150956_d) {
        this.field_150956_d = field_150956_d;
        return this;
    }
    
    static NumberFormat access$0() {
        return StatBase.numberFormat;
    }
    
    static DecimalFormat access$1() {
        return StatBase.decimalFormat;
    }
}
