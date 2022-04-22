package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.jetbrains.annotations.*;
import java.util.*;

public interface KeyedValue extends Keyed
{
    @NotNull
    default KeyedValue of(@NotNull final Key key, @NotNull final Object value) {
        return new KeyedValueImpl(key, Objects.requireNonNull(value, "value"));
    }
    
    @NotNull
    Object value();
}
