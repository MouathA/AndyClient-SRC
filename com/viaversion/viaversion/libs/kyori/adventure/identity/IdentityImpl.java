package com.viaversion.viaversion.libs.kyori.adventure.identity;

import java.util.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

final class IdentityImpl implements Examinable, Identity
{
    private final UUID uuid;
    
    IdentityImpl(final UUID uuid) {
        this.uuid = uuid;
    }
    
    @NotNull
    @Override
    public UUID uuid() {
        return this.uuid;
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other instanceof Identity && this.uuid.equals(((Identity)other).uuid()));
    }
    
    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
