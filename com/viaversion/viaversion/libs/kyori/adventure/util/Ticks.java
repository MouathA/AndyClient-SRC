package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.time.*;
import org.jetbrains.annotations.*;

public interface Ticks
{
    public static final int TICKS_PER_SECOND = 20;
    public static final long SINGLE_TICK_DURATION_MS = 50L;
    
    @NotNull
    default Duration duration(final long ticks) {
        return Duration.ofMillis(ticks * 50L);
    }
}
