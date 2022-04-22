package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

final class PointerImpl implements Pointer
{
    private final Class type;
    private final Key key;
    
    PointerImpl(final Class type, final Key key) {
        this.type = type;
        this.key = key;
    }
    
    @NotNull
    @Override
    public Class type() {
        return this.type;
    }
    
    @NotNull
    @Override
    public Key key() {
        return this.key;
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final PointerImpl pointerImpl = (PointerImpl)other;
        return this.type.equals(pointerImpl.type) && this.key.equals(pointerImpl.key);
    }
    
    @Override
    public int hashCode() {
        return 31 * this.type.hashCode() + this.key.hashCode();
    }
}
