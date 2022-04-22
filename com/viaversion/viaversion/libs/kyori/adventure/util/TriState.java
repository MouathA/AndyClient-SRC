package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;

public enum TriState
{
    NOT_SET, 
    FALSE, 
    TRUE;
    
    private static final TriState[] $VALUES;
    
    @NotNull
    public static TriState byBoolean(final boolean value) {
        return value ? TriState.TRUE : TriState.FALSE;
    }
    
    @NotNull
    public static TriState byBoolean(@Nullable final Boolean value) {
        return (value == null) ? TriState.NOT_SET : byBoolean((boolean)value);
    }
    
    static {
        $VALUES = new TriState[] { TriState.NOT_SET, TriState.FALSE, TriState.TRUE };
    }
}
