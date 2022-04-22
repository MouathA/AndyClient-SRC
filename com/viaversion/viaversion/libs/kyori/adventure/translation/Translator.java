package com.viaversion.viaversion.libs.kyori.adventure.translation;

import java.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.text.*;

public interface Translator
{
    @Nullable
    default Locale parseLocale(@NotNull final String string) {
        final String[] split = string.split("_", 3);
        final int length = split.length;
        if (length == 1) {
            return new Locale(string);
        }
        if (length == 2) {
            return new Locale(split[0], split[1]);
        }
        if (length == 3) {
            return new Locale(split[0], split[1], split[2]);
        }
        return null;
    }
    
    @NotNull
    Key name();
    
    @Nullable
    MessageFormat translate(@NotNull final String key, @NotNull final Locale locale);
}
