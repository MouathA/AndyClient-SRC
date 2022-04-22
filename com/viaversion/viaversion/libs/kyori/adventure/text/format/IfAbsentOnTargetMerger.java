package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;

final class IfAbsentOnTargetMerger implements Merger
{
    static final IfAbsentOnTargetMerger INSTANCE;
    
    private IfAbsentOnTargetMerger() {
    }
    
    @Override
    public void mergeColor(final StyleImpl.BuilderImpl target, @Nullable final TextColor color) {
        if (target.color == null) {
            target.color(color);
        }
    }
    
    @Override
    public void mergeDecoration(final StyleImpl.BuilderImpl target, @NotNull final TextDecoration decoration, final TextDecoration.State state) {
        target.decorationIfAbsent(decoration, state);
    }
    
    @Override
    public void mergeClickEvent(final StyleImpl.BuilderImpl target, @Nullable final ClickEvent event) {
        if (target.clickEvent == null) {
            target.clickEvent(event);
        }
    }
    
    @Override
    public void mergeHoverEvent(final StyleImpl.BuilderImpl target, @Nullable final HoverEvent event) {
        if (target.hoverEvent == null) {
            target.hoverEvent(event);
        }
    }
    
    @Override
    public void mergeInsertion(final StyleImpl.BuilderImpl target, @Nullable final String insertion) {
        if (target.insertion == null) {
            target.insertion(insertion);
        }
    }
    
    @Override
    public void mergeFont(final StyleImpl.BuilderImpl target, @Nullable final Key font) {
        if (target.font == null) {
            target.font(font);
        }
    }
    
    static {
        INSTANCE = new IfAbsentOnTargetMerger();
    }
}
