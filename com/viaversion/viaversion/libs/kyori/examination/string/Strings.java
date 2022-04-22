package com.viaversion.viaversion.libs.kyori.examination.string;

import org.jetbrains.annotations.*;
import java.util.stream.*;
import java.util.function.*;

final class Strings
{
    private Strings() {
    }
    
    @NotNull
    static String withSuffix(final String string, final char suffix) {
        return string + suffix;
    }
    
    @NotNull
    static String wrapIn(final String string, final char wrap) {
        return wrap + string + wrap;
    }
    
    static int maxLength(final Stream strings) {
        return strings.mapToInt(String::length).max().orElse(0);
    }
    
    @NotNull
    static String repeat(@NotNull final String string, final int count) {
        if (count == 0) {
            return "";
        }
        if (count == 1) {
            return string;
        }
        final StringBuilder sb = new StringBuilder(string.length() * count);
        while (0 < count) {
            sb.append(string);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    @NotNull
    static String padEnd(@NotNull final String string, final int minLength, final char padding) {
        return (string.length() >= minLength) ? string : String.format("%-" + minLength + "s", padding);
    }
}
