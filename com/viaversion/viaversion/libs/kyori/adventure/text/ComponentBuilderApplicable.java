package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

@FunctionalInterface
public interface ComponentBuilderApplicable
{
    @Contract(mutates = "param")
    void componentBuilderApply(@NotNull final ComponentBuilder component);
}
