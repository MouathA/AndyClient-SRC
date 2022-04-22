package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;

final class AlwaysMerger implements Merger
{
    static final AlwaysMerger INSTANCE;
    
    private AlwaysMerger() {
    }
    
    @Override
    public void mergeColor(final StyleImpl.BuilderImpl target, @Nullable final TextColor color) {
        target.color(color);
    }
    
    @Override
    public void mergeDecoration(final StyleImpl.BuilderImpl target, @NotNull final TextDecoration decoration, final TextDecoration.State state) {
        target.decoration(decoration, state);
    }
    
    @Override
    public void mergeClickEvent(final StyleImpl.BuilderImpl target, @Nullable final ClickEvent event) {
        target.clickEvent(event);
    }
    
    @Override
    public void mergeHoverEvent(final StyleImpl.BuilderImpl target, @Nullable final HoverEvent event) {
        target.hoverEvent(event);
    }
    
    @Override
    public void mergeInsertion(final StyleImpl.BuilderImpl target, @Nullable final String insertion) {
        target.insertion(insertion);
    }
    
    @Override
    public void mergeFont(final StyleImpl.BuilderImpl target, @Nullable final Key font) {
        target.font(font);
    }
    
    static {
        INSTANCE = new AlwaysMerger();
    }
}
