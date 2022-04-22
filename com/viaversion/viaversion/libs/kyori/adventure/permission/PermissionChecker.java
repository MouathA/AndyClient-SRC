package com.viaversion.viaversion.libs.kyori.adventure.permission;

import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;

public interface PermissionChecker extends Predicate
{
    public static final Pointer POINTER = Pointer.pointer(PermissionChecker.class, Key.key("adventure", "permission"));
    
    @NotNull
    default PermissionChecker always(final TriState state) {
        if (state == TriState.TRUE) {
            return PermissionCheckers.TRUE;
        }
        if (state == TriState.FALSE) {
            return PermissionCheckers.FALSE;
        }
        return PermissionCheckers.NOT_SET;
    }
    
    @NotNull
    TriState value(final String permission);
    
    default boolean test(final String permission) {
        return this.value(permission) == TriState.TRUE;
    }
    
    default boolean test(final Object permission) {
        return this.test((String)permission);
    }
}
