package net.minecraft.stats;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class Achievement extends StatBase
{
    public final int displayColumn;
    public final int displayRow;
    public final Achievement parentAchievement;
    private final String achievementDescription;
    private IStatStringFormat statStringFormatter;
    public final ItemStack theItemStack;
    private boolean isSpecial;
    private static final String __OBFID;
    
    public Achievement(final String s, final String s2, final int n, final int n2, final Item item, final Achievement achievement) {
        this(s, s2, n, n2, new ItemStack(item), achievement);
    }
    
    public Achievement(final String s, final String s2, final int n, final int n2, final Block block, final Achievement achievement) {
        this(s, s2, n, n2, new ItemStack(block), achievement);
    }
    
    public Achievement(final String s, final String s2, final int maxDisplayColumn, final int maxDisplayRow, final ItemStack theItemStack, final Achievement parentAchievement) {
        super(s, new ChatComponentTranslation("achievement." + s2, new Object[0]));
        this.theItemStack = theItemStack;
        this.achievementDescription = "achievement." + s2 + ".desc";
        this.displayColumn = maxDisplayColumn;
        this.displayRow = maxDisplayRow;
        if (maxDisplayColumn < AchievementList.minDisplayColumn) {
            AchievementList.minDisplayColumn = maxDisplayColumn;
        }
        if (maxDisplayRow < AchievementList.minDisplayRow) {
            AchievementList.minDisplayRow = maxDisplayRow;
        }
        if (maxDisplayColumn > AchievementList.maxDisplayColumn) {
            AchievementList.maxDisplayColumn = maxDisplayColumn;
        }
        if (maxDisplayRow > AchievementList.maxDisplayRow) {
            AchievementList.maxDisplayRow = maxDisplayRow;
        }
        this.parentAchievement = parentAchievement;
    }
    
    public Achievement func_180789_a() {
        this.isIndependent = true;
        return this;
    }
    
    public Achievement setSpecial() {
        this.isSpecial = true;
        return this;
    }
    
    public Achievement func_180788_c() {
        super.registerStat();
        AchievementList.achievementList.add(this);
        return this;
    }
    
    @Override
    public boolean isAchievement() {
        return true;
    }
    
    @Override
    public IChatComponent getStatName() {
        final IChatComponent statName = super.getStatName();
        statName.getChatStyle().setColor(this.getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
        return statName;
    }
    
    public Achievement func_180787_a(final Class clazz) {
        return (Achievement)super.func_150953_b(clazz);
    }
    
    public String getDescription() {
        return (this.statStringFormatter != null) ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription)) : StatCollector.translateToLocal(this.achievementDescription);
    }
    
    public Achievement setStatStringFormatter(final IStatStringFormat statStringFormatter) {
        this.statStringFormatter = statStringFormatter;
        return this;
    }
    
    public boolean getSpecial() {
        return this.isSpecial;
    }
    
    @Override
    public StatBase func_150953_b(final Class clazz) {
        return this.func_180787_a(clazz);
    }
    
    @Override
    public StatBase registerStat() {
        return this.func_180788_c();
    }
    
    @Override
    public StatBase initIndependentStat() {
        return this.func_180789_a();
    }
    
    static {
        __OBFID = "CL_00001466";
    }
}
