package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.jetbrains.annotations.*;
import org.intellij.lang.annotations.*;

public interface Namespaced
{
    @NotNull
    @Pattern("[a-z0-9_\\-.]+")
    String namespace();
}
