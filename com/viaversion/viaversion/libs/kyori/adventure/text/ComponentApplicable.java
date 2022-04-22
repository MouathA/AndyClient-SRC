package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

@FunctionalInterface
public interface ComponentApplicable
{
    @NotNull
    Component componentApply(@NotNull final Component component);
}
