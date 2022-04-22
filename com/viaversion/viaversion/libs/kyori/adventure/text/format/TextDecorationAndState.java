package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

@ApiStatus.NonExtendable
public interface TextDecorationAndState extends Examinable, StyleBuilderApplicable
{
    @NotNull
    TextDecoration decoration();
    
    TextDecoration.State state();
    
    default void styleApply(final Style.Builder style) {
        style.decoration(this.decoration(), this.state());
    }
    
    @NotNull
    default Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("decoration", this.decoration()), ExaminableProperty.of("state", this.state()) });
    }
}
