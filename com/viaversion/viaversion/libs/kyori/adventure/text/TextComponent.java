package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

public interface TextComponent extends BuildableComponent, ScopedComponent
{
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @NotNull
    default TextComponent ofChildren(@NotNull final ComponentLike... components) {
        final Component join = Component.join(JoinConfiguration.noSeparators(), components);
        if (join instanceof TextComponent) {
            return (TextComponent)join;
        }
        return (TextComponent)((Builder)Component.text().append(join)).build();
    }
    
    @NotNull
    String content();
    
    @Contract(pure = true)
    @NotNull
    TextComponent content(@NotNull final String content);
    
    public interface Builder extends ComponentBuilder
    {
        @NotNull
        String content();
        
        @Contract("_ -> this")
        @NotNull
        Builder content(@NotNull final String content);
    }
}
