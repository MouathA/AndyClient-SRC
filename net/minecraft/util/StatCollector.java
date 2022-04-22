package net.minecraft.util;

public class StatCollector
{
    private static StringTranslate localizedName;
    private static StringTranslate fallbackTranslator;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001211";
        StatCollector.localizedName = StringTranslate.getInstance();
        StatCollector.fallbackTranslator = new StringTranslate();
    }
    
    public static String translateToLocal(final String s) {
        return StatCollector.localizedName.translateKey(s);
    }
    
    public static String translateToLocalFormatted(final String s, final Object... array) {
        return StatCollector.localizedName.translateKeyFormat(s, array);
    }
    
    public static String translateToFallback(final String s) {
        return StatCollector.fallbackTranslator.translateKey(s);
    }
    
    public static boolean canTranslate(final String s) {
        return StatCollector.localizedName.isKeyTranslated(s);
    }
    
    public static long getLastTranslationUpdateTimeInMilliseconds() {
        return StatCollector.localizedName.getLastUpdateTimeInMilliseconds();
    }
}
