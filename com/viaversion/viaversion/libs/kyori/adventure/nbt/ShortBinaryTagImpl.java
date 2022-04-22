package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

@Debug.Renderer(text = "String.valueOf(this.value) + \"s\"", hasChildren = "false")
final class ShortBinaryTagImpl extends AbstractBinaryTag implements ShortBinaryTag
{
    private final short value;
    
    ShortBinaryTagImpl(final short value) {
        this.value = value;
    }
    
    @Override
    public short value() {
        return this.value;
    }
    
    @Override
    public byte byteValue() {
        return (byte)(this.value & 0xFF);
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
        return this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
    }
    
    @Override
    public short shortValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && this.value == ((ShortBinaryTagImpl)other).value);
    }
    
    @Override
    public int hashCode() {
        return Short.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
