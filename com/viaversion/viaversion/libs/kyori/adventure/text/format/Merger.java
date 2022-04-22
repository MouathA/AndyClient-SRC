package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;

interface Merger
{
    void mergeColor(final StyleImpl.BuilderImpl target, @Nullable final TextColor color);
    
    void mergeDecoration(final StyleImpl.BuilderImpl target, @NotNull final TextDecoration decoration, final TextDecoration.State state);
    
    void mergeClickEvent(final StyleImpl.BuilderImpl target, @Nullable final ClickEvent event);
    
    void mergeHoverEvent(final StyleImpl.BuilderImpl target, @Nullable final HoverEvent event);
    
    void mergeInsertion(final StyleImpl.BuilderImpl target, @Nullable final String insertion);
    
    void mergeFont(final StyleImpl.BuilderImpl target, @Nullable final Key font);
}
