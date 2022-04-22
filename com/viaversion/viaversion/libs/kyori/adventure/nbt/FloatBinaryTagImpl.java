package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

@Debug.Renderer(text = "String.valueOf(this.value) + \"f\"", hasChildren = "false")
final class FloatBinaryTagImpl extends AbstractBinaryTag implements FloatBinaryTag
{
    private final float value;
    
    FloatBinaryTagImpl(final float value) {
        this.value = value;
    }
    
    @Override
    public float value() {
        return this.value;
    }
    
    @Override
    public byte byteValue() {
        return (byte)(ShadyPines.floor(this.value) & 0xFF);
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    @Override
    public float floatValue() {
        return this.value;
    }
    
    @Override
    public int intValue() {
        return ShadyPines.floor(this.value);
    }
    
    @Override
    public long longValue() {
        return (long)this.value;
    }
    
    @Override
    public short shortValue() {
        return (short)(ShadyPines.floor(this.value) & 0xFFFF);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && Float.floatToIntBits(this.value) == Float.floatToIntBits(((FloatBinaryTagImpl)other).value));
    }
    
    @Override
    public int hashCode() {
        return Float.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
