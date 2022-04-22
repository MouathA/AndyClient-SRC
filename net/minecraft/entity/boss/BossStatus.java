package net.minecraft.entity.boss;

public final class BossStatus
{
    public static float healthScale;
    public static int statusBarTime;
    public static String bossName;
    public static boolean hasColorModifier;
    private static final String __OBFID;
    
    public static void setBossStatus(final IBossDisplayData bossDisplayData, final boolean hasColorModifier) {
        BossStatus.healthScale = bossDisplayData.getHealth() / bossDisplayData.getMaxHealth();
        BossStatus.statusBarTime = 100;
        BossStatus.bossName = bossDisplayData.getDisplayName().getFormattedText();
        BossStatus.hasColorModifier = hasColorModifier;
    }
    
    static {
        __OBFID = "CL_00000941";
    }
}
