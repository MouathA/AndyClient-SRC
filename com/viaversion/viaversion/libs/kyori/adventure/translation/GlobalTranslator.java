package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.*;

public interface GlobalTranslator extends Translator, Examinable
{
    @NotNull
    default GlobalTranslator get() {
        return GlobalTranslatorImpl.INSTANCE;
    }
    
    @NotNull
    default TranslatableComponentRenderer renderer() {
        return GlobalTranslatorImpl.INSTANCE.renderer;
    }
    
    @NotNull
    default Component render(@NotNull final Component component, @NotNull final Locale locale) {
        return renderer().render(component, locale);
    }
    
    @NotNull
    Iterable sources();
    
    boolean addSource(@NotNull final Translator source);
    
    boolean removeSource(@NotNull final Translator source);
}
