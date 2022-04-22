package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.function.*;
import org.jetbrains.annotations.*;

@ApiStatus.NonExtendable
public interface JoinConfiguration extends Buildable, Examinable
{
    @NotNull
    default Builder builder() {
        return new JoinConfigurationImpl.BuilderImpl();
    }
    
    @NotNull
    default JoinConfiguration noSeparators() {
        return JoinConfigurationImpl.NULL;
    }
    
    @NotNull
    default JoinConfiguration separator(@Nullable final ComponentLike separator) {
        if (separator == null) {
            return JoinConfigurationImpl.NULL;
        }
        return (JoinConfiguration)builder().separator(separator).build();
    }
    
    @NotNull
    default JoinConfiguration separators(@Nullable final ComponentLike separator, @Nullable final ComponentLike lastSeparator) {
        if (separator == null && lastSeparator == null) {
            return JoinConfigurationImpl.NULL;
        }
        return (JoinConfiguration)builder().separator(separator).lastSeparator(lastSeparator).build();
    }
    
    @Nullable
    Component prefix();
    
    @Nullable
    Component suffix();
    
    @Nullable
    Component separator();
    
    @Nullable
    Component lastSeparator();
    
    @Nullable
    Component lastSeparatorIfSerial();
    
    @NotNull
    Function convertor();
    
    @NotNull
    Predicate predicate();
    
    public interface Builder extends Buildable.Builder
    {
        @Contract("_ -> this")
        @NotNull
        Builder prefix(@Nullable final ComponentLike prefix);
        
        @Contract("_ -> this")
        @NotNull
        Builder suffix(@Nullable final ComponentLike suffix);
        
        @Contract("_ -> this")
        @NotNull
        Builder separator(@Nullable final ComponentLike separator);
        
        @Contract("_ -> this")
        @NotNull
        Builder lastSeparator(@Nullable final ComponentLike lastSeparator);
        
        @Contract("_ -> this")
        @NotNull
        Builder lastSeparatorIfSerial(@Nullable final ComponentLike lastSeparatorIfSerial);
        
        @Contract("_ -> this")
        @NotNull
        Builder convertor(@NotNull final Function convertor);
        
        @Contract("_ -> this")
        @NotNull
        Builder predicate(@NotNull final Predicate predicate);
    }
}
