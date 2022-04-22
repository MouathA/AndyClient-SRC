package com.viaversion.viaversion.libs.kyori.adventure.title;

import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.time.*;

@ApiStatus.NonExtendable
public interface Title extends Examinable
{
    public static final Times DEFAULT_TIMES = Times.of(Ticks.duration(10L), Ticks.duration(70L), Ticks.duration(20L));
    
    @NotNull
    default Title title(@NotNull final Component title, @NotNull final Component subtitle) {
        return title(title, subtitle, Title.DEFAULT_TIMES);
    }
    
    @NotNull
    default Title title(@NotNull final Component title, @NotNull final Component subtitle, @Nullable final Times times) {
        return new TitleImpl(title, subtitle, times);
    }
    
    @NotNull
    Component title();
    
    @NotNull
    Component subtitle();
    
    @Nullable
    Times times();
    
    Object part(@NotNull final TitlePart part);
    
    public interface Times extends Examinable
    {
        @NotNull
        default Times of(@NotNull final Duration fadeIn, @NotNull final Duration stay, @NotNull final Duration fadeOut) {
            return new TitleImpl.TimesImpl(fadeIn, stay, fadeOut);
        }
        
        @NotNull
        Duration fadeIn();
        
        @NotNull
        Duration stay();
        
        @NotNull
        Duration fadeOut();
    }
}
