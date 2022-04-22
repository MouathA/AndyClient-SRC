package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

public interface TextColor extends Comparable, Examinable, RGBLike, StyleBuilderApplicable, TextFormat
{
    @NotNull
    default TextColor color(final int value) {
        final int n = value & 0xFFFFFF;
        final NamedTextColor ofExact = NamedTextColor.ofExact(n);
        return (ofExact != null) ? ofExact : new TextColorImpl(n);
    }
    
    @NotNull
    default TextColor color(@NotNull final RGBLike rgb) {
        if (rgb instanceof TextColor) {
            return (TextColor)rgb;
        }
        return color(rgb.red(), rgb.green(), rgb.blue());
    }
    
    @NotNull
    default TextColor color(@NotNull final HSVLike hsv) {
        final float s = hsv.s();
        final float v = hsv.v();
        if (s == 0.0f) {
            return color(v, v, v);
        }
        final float n = hsv.h() * 6.0f;
        final int n2 = (int)Math.floor(n);
        final float n3 = n - n2;
        final float n4 = v * (1.0f - s);
        final float b = v * (1.0f - s * n3);
        final float r = v * (1.0f - s * (1.0f - n3));
        if (n2 == 0) {
            return color(v, r, n4);
        }
        if (n2 == 1) {
            return color(b, v, n4);
        }
        if (n2 == 2) {
            return color(n4, v, r);
        }
        if (n2 == 3) {
            return color(n4, b, v);
        }
        if (n2 == 4) {
            return color(r, n4, v);
        }
        return color(v, n4, b);
    }
    
    @NotNull
    default TextColor color(final int r, final int g, final int b) {
        return color((r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF));
    }
    
    @NotNull
    default TextColor color(final float r, final float g, final float b) {
        return color((int)(r * 255.0f), (int)(g * 255.0f), (int)(b * 255.0f));
    }
    
    @Nullable
    default TextColor fromHexString(@NotNull final String string) {
        if (string.startsWith("#")) {
            return color(Integer.parseInt(string.substring(1), 16));
        }
        return null;
    }
    
    @Nullable
    default TextColor fromCSSHexString(@NotNull final String string) {
        if (!string.startsWith("#")) {
            return null;
        }
        final String substring = string.substring(1);
        if (substring.length() != 3 && substring.length() != 6) {
            return null;
        }
        final int int1 = Integer.parseInt(substring, 16);
        if (substring.length() == 6) {
            return color(int1);
        }
        return color((int1 & 0xF00) >> 8 | (int1 & 0xF00) >> 4, (int1 & 0xF0) >> 4 | (int1 & 0xF0), (int1 & 0xF) << 4 | (int1 & 0xF));
    }
    
    int value();
    
    @NotNull
    default String asHexString() {
        return String.format("#%06x", this.value());
    }
    
    default int red() {
        return this.value() >> 16 & 0xFF;
    }
    
    default int green() {
        return this.value() >> 8 & 0xFF;
    }
    
    default int blue() {
        return this.value() & 0xFF;
    }
    
    @NotNull
    default TextColor lerp(final float t, @NotNull final RGBLike a, @NotNull final RGBLike b) {
        final float min = Math.min(1.0f, Math.max(0.0f, t));
        final int red = a.red();
        final int red2 = b.red();
        final int green = a.green();
        final int green2 = b.green();
        final int blue = a.blue();
        return color(Math.round(red + min * (red2 - red)), Math.round(green + min * (green2 - green)), Math.round(blue + min * (b.blue() - blue)));
    }
    
    default void styleApply(final Style.Builder style) {
        style.color(this);
    }
    
    default int compareTo(final TextColor that) {
        return Integer.compare(this.value(), that.value());
    }
    
    @NotNull
    default Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.asHexString()));
    }
    
    default int compareTo(final Object that) {
        return this.compareTo((TextColor)that);
    }
}
