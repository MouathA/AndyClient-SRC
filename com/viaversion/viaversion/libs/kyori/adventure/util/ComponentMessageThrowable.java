package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;

public interface ComponentMessageThrowable
{
    @Nullable
    default Component getMessage(@Nullable final Throwable throwable) {
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable)throwable).componentMessage();
        }
        return null;
    }
    
    @Nullable
    default Component getOrConvertMessage(@Nullable final Throwable throwable) {
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable)throwable).componentMessage();
        }
        if (throwable != null) {
            final String message = throwable.getMessage();
            if (message != null) {
                return Component.text(message);
            }
        }
        return null;
    }
    
    @Nullable
    Component componentMessage();
}
