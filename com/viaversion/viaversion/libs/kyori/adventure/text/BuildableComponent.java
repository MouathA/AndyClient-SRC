package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;

public interface BuildableComponent extends Buildable, Component
{
    @NotNull
    ComponentBuilder toBuilder();
    
    @NotNull
    default Builder toBuilder() {
        return this.toBuilder();
    }
}
