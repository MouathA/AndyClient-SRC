package net.minecraft.client.resources;

import java.util.*;

public class I18n
{
    private static Locale i18nLocale;
    
    static void setLocale(final Locale i18nLocale) {
        I18n.i18nLocale = i18nLocale;
    }
    
    public static String format(final String s, final Object... array) {
        return I18n.i18nLocale.formatMessage(s, array);
    }
    
    public static Map getLocaleProperties() {
        return I18n.i18nLocale.field_135032_a;
    }
}
