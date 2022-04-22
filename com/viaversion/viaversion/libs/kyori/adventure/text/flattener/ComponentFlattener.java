package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.function.*;
import org.jetbrains.annotations.*;

public interface ComponentFlattener extends Buildable
{
    @NotNull
    default Builder builder() {
        return new ComponentFlattenerImpl.BuilderImpl();
    }
    
    @NotNull
    default ComponentFlattener basic() {
        return ComponentFlattenerImpl.BASIC;
    }
    
    @NotNull
    default ComponentFlattener textOnly() {
        return ComponentFlattenerImpl.TEXT_ONLY;
    }
    
    void flatten(@NotNull final Component input, @NotNull final FlattenerListener listener);
    
    public interface Builder extends Buildable.Builder
    {
        @NotNull
        Builder mapper(@NotNull final Class type, @NotNull final Function converter);
        
        @NotNull
        Builder complexMapper(@NotNull final Class type, @NotNull final BiConsumer converter);
        
        @NotNull
        Builder unknownMapper(@Nullable final Function converter);
    }
}
