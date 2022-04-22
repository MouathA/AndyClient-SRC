package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;

public abstract class AbstractComponentRenderer implements ComponentRenderer
{
    @NotNull
    @Override
    public Component render(@NotNull final Component component, @NotNull final Object context) {
        if (component instanceof TextComponent) {
            return this.renderText((TextComponent)component, context);
        }
        if (component instanceof TranslatableComponent) {
            return this.renderTranslatable((TranslatableComponent)component, context);
        }
        if (component instanceof KeybindComponent) {
            return this.renderKeybind((KeybindComponent)component, context);
        }
        if (component instanceof ScoreComponent) {
            return this.renderScore((ScoreComponent)component, context);
        }
        if (component instanceof SelectorComponent) {
            return this.renderSelector((SelectorComponent)component, context);
        }
        if (component instanceof NBTComponent) {
            if (component instanceof BlockNBTComponent) {
                return this.renderBlockNbt((BlockNBTComponent)component, context);
            }
            if (component instanceof EntityNBTComponent) {
                return this.renderEntityNbt((EntityNBTComponent)component, context);
            }
            if (component instanceof StorageNBTComponent) {
                return this.renderStorageNbt((StorageNBTComponent)component, context);
            }
        }
        return component;
    }
    
    @NotNull
    protected abstract Component renderBlockNbt(@NotNull final BlockNBTComponent component, @NotNull final Object context);
    
    @NotNull
    protected abstract Component renderEntityNbt(@NotNull final EntityNBTComponent component, @NotNull final Object context);
    
    @NotNull
    protected abstract Component renderStorageNbt(@NotNull final StorageNBTComponent component, @NotNull final Object context);
    
    @NotNull
    protected abstract Component renderKeybind(@NotNull final KeybindComponent component, @NotNull final Object context);
    
    @NotNull
    protected abstract Component renderScore(@NotNull final ScoreComponent component, @NotNull final Object context);
    
    @NotNull
    protected abstract Component renderSelector(@NotNull final SelectorComponent component, @NotNull final Object context);
    
    @NotNull
    protected abstract Component renderText(@NotNull final TextComponent component, @NotNull final Object context);
    
    @NotNull
    protected abstract Component renderTranslatable(@NotNull final TranslatableComponent component, @NotNull final Object context);
}
