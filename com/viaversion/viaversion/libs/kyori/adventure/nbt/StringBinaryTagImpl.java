package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

@Debug.Renderer(text = "\"\\\"\" + this.value + \"\\\"\"", hasChildren = "false")
final class StringBinaryTagImpl extends AbstractBinaryTag implements StringBinaryTag
{
    private final String value;
    
    StringBinaryTagImpl(final String value) {
        this.value = value;
    }
    
    @NotNull
    @Override
    public String value() {
        return this.value;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && this.value.equals(((StringBinaryTagImpl)other).value));
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
