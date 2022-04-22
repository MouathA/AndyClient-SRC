package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

@Debug.Renderer(text = "\"0x\" + Integer.toString(this.value, 16)", hasChildren = "false")
final class ByteBinaryTagImpl extends AbstractBinaryTag implements ByteBinaryTag
{
    private final byte value;
    
    ByteBinaryTagImpl(final byte value) {
        this.value = value;
    }
    
    @Override
    public byte value() {
        return this.value;
    }
    
    @Override
    public byte byteValue() {
        return this.value;
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
        return this == other || (other != null && this.getClass() == other.getClass() && this.value == ((ByteBinaryTagImpl)other).value);
    }
    
    @Override
    public int hashCode() {
        return Byte.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
