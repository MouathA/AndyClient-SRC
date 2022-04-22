package com.viaversion.viaversion.libs.kyori.adventure.translation;

import java.util.function.*;
import java.util.*;

final class TranslationLocales
{
    private static final Supplier GLOBAL;
    
    private TranslationLocales() {
    }
    
    static Locale global() {
        return TranslationLocales.GLOBAL.get();
    }
    
    private static Locale lambda$static$1(final Locale locale) {
        return locale;
    }
    
    private static Locale lambda$static$0() {
        return Locale.US;
    }
    
    static {
        final String property = System.getProperty("net.kyo".concat("ri.adventure.defaultTranslationLocale"));
        if (property == null || property.isEmpty()) {
            GLOBAL = TranslationLocales::lambda$static$0;
        }
        else if (property.equals("system")) {
            GLOBAL = Locale::getDefault;
        }
        else {
            GLOBAL = TranslationLocales::lambda$static$1;
        }
    }
}
