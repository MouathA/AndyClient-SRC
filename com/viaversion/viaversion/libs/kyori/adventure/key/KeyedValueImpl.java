package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

final class KeyedValueImpl implements Examinable, KeyedValue
{
    private final Key key;
    private final Object value;
    
    KeyedValueImpl(final Key key, final Object value) {
        this.key = key;
        this.value = value;
    }
    
    @NotNull
    @Override
    public Key key() {
        return this.key;
    }
    
    @NotNull
    @Override
    public Object value() {
        return this.value;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final KeyedValueImpl keyedValueImpl = (KeyedValueImpl)other;
        return this.key.equals(keyedValueImpl.key) && this.value.equals(keyedValueImpl.value);
    }
    
    @Override
    public int hashCode() {
        return 31 * this.key.hashCode() + this.value.hashCode();
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("key", this.key), ExaminableProperty.of("value", this.value) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
}
