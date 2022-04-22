package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import java.util.function.*;
import org.jetbrains.annotations.*;

public interface HoverEventSource
{
    @Nullable
    default HoverEvent unbox(@Nullable final HoverEventSource source) {
        return (source != null) ? source.asHoverEvent() : null;
    }
    
    @NotNull
    default HoverEvent asHoverEvent() {
        return this.asHoverEvent(UnaryOperator.identity());
    }
    
    @NotNull
    HoverEvent asHoverEvent(@NotNull final UnaryOperator op);
}
