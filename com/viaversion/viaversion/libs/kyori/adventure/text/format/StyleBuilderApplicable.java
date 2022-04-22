package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

@FunctionalInterface
public interface StyleBuilderApplicable extends ComponentBuilderApplicable
{
    @Contract(mutates = "param")
    void styleApply(final Style.Builder style);
    
    default void componentBuilderApply(@NotNull final ComponentBuilder component) {
        component.style(this::styleApply);
    }
}
