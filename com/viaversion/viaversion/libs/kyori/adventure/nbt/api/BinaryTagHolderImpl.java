package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import java.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class BinaryTagHolderImpl implements BinaryTagHolder
{
    private final String string;
    
    BinaryTagHolderImpl(final String string) {
        this.string = Objects.requireNonNull(string, "string");
    }
    
    @NotNull
    @Override
    public String string() {
        return this.string;
    }
    
    @NotNull
    @Override
    public Object get(@NotNull final Codec codec) throws Exception {
        return codec.decode(this.string);
    }
    
    @Override
    public int hashCode() {
        return 31 * this.string.hashCode();
    }
    
    @Override
    public boolean equals(final Object that) {
        return that instanceof BinaryTagHolderImpl && this.string.equals(((BinaryTagHolderImpl)that).string);
    }
    
    @Override
    public String toString() {
        return this.string;
    }
}
