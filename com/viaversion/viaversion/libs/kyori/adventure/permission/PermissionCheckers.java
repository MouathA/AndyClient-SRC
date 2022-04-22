package com.viaversion.viaversion.libs.kyori.adventure.permission;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;

final class PermissionCheckers
{
    static final PermissionChecker NOT_SET;
    static final PermissionChecker FALSE;
    static final PermissionChecker TRUE;
    
    private PermissionCheckers() {
    }
    
    static {
        NOT_SET = new Always(TriState.NOT_SET, null);
        FALSE = new Always(TriState.FALSE, null);
        TRUE = new Always(TriState.TRUE, null);
    }
    
    private static final class Always implements PermissionChecker
    {
        private final TriState value;
        
        private Always(final TriState value) {
            this.value = value;
        }
        
        @NotNull
        @Override
        public TriState value(final String permission) {
            return this.value;
        }
        
        @Override
        public String toString() {
            return PermissionChecker.class.getSimpleName() + ".always(" + this.value + ")";
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            return this == other || (other != null && this.getClass() == other.getClass() && this.value == ((Always)other).value);
        }
        
        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
        
        Always(final TriState value, final PermissionCheckers$1 object) {
            this(value);
        }
    }
}
