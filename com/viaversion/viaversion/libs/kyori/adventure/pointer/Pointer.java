package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

public interface Pointer extends Examinable
{
    @NotNull
    default Pointer pointer(@NotNull final Class type, @NotNull final Key key) {
        return new PointerImpl(type, key);
    }
    
    @NotNull
    Class type();
    
    @NotNull
    Key key();
    
    @NotNull
    default Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("type", this.type()), ExaminableProperty.of("key", this.key()) });
    }
}
