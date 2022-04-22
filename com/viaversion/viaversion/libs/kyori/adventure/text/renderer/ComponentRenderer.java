package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface ComponentRenderer
{
    @NotNull
    Component render(@NotNull final Component component, @NotNull final Object context);
    
    default ComponentRenderer mapContext(final Function transformer) {
        return this::lambda$mapContext$0;
    }
    
    default Component lambda$mapContext$0(final Function function, final Component component, final Object o) {
        return this.render(component, function.apply(o));
    }
}
