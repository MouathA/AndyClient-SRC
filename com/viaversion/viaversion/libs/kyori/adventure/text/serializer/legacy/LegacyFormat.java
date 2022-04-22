package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

public final class LegacyFormat implements Examinable
{
    static final LegacyFormat RESET;
    @Nullable
    private final NamedTextColor color;
    @Nullable
    private final TextDecoration decoration;
    private final boolean reset;
    
    LegacyFormat(@Nullable final NamedTextColor color) {
        this.color = color;
        this.decoration = null;
        this.reset = false;
    }
    
    LegacyFormat(@Nullable final TextDecoration decoration) {
        this.color = null;
        this.decoration = decoration;
        this.reset = false;
    }
    
    private LegacyFormat(final boolean reset) {
        this.color = null;
        this.decoration = null;
        this.reset = reset;
    }
    
    @Nullable
    public TextColor color() {
        return this.color;
    }
    
    @Nullable
    public TextDecoration decoration() {
        return this.decoration;
    }
    
    public boolean reset() {
        return this.reset;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final LegacyFormat legacyFormat = (LegacyFormat)other;
        return this.color == legacyFormat.color && this.decoration == legacyFormat.decoration && this.reset == legacyFormat.reset;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * Objects.hashCode(this.color) + Objects.hashCode(this.decoration)) + Boolean.hashCode(this.reset);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("color", this.color), ExaminableProperty.of("decoration", this.decoration), ExaminableProperty.of("reset", this.reset) });
    }
    
    static {
        RESET = new LegacyFormat(true);
    }
}
