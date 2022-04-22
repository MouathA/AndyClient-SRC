package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.regex.*;
import org.intellij.lang.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface TextReplacementConfig extends Buildable, Examinable
{
    @NotNull
    default Builder builder() {
        return new TextReplacementConfigImpl.Builder();
    }
    
    @NotNull
    Pattern matchPattern();
    
    @FunctionalInterface
    public interface Condition
    {
        @NotNull
        PatternReplacementResult shouldReplace(@NotNull final MatchResult result, final int matchCount, final int replaced);
    }
    
    public interface Builder extends Buildable.Builder
    {
        @Contract("_ -> this")
        default Builder matchLiteral(final String literal) {
            return this.match(Pattern.compile(literal, 16));
        }
        
        @Contract("_ -> this")
        @NotNull
        default Builder match(@NotNull @RegExp final String pattern) {
            return this.match(Pattern.compile(pattern));
        }
        
        @Contract("_ -> this")
        @NotNull
        Builder match(@NotNull final Pattern pattern);
        
        @NotNull
        default Builder once() {
            return this.times(1);
        }
        
        @Contract("_ -> this")
        @NotNull
        default Builder times(final int times) {
            return this.condition(Builder::lambda$times$0);
        }
        
        @Contract("_ -> this")
        @NotNull
        default Builder condition(@NotNull final IntFunction2 condition) {
            return this.condition(Builder::lambda$condition$1);
        }
        
        @Contract("_ -> this")
        @NotNull
        Builder condition(@NotNull final Condition condition);
        
        @Contract("_ -> this")
        @NotNull
        default Builder replacement(@NotNull final String replacement) {
            Objects.requireNonNull(replacement, "replacement");
            return this.replacement(Builder::lambda$replacement$2);
        }
        
        @Contract("_ -> this")
        @NotNull
        default Builder replacement(@Nullable final ComponentLike replacement) {
            return this.replacement(Builder::lambda$replacement$3);
        }
        
        @Contract("_ -> this")
        @NotNull
        default Builder replacement(@NotNull final Function replacement) {
            Objects.requireNonNull(replacement, "replacement");
            return this.replacement(Builder::lambda$replacement$4);
        }
        
        @Contract("_ -> this")
        @NotNull
        Builder replacement(@NotNull final BiFunction replacement);
        
        default ComponentLike lambda$replacement$4(final Function function, final MatchResult matchResult, final TextComponent.Builder builder) {
            return function.apply(builder);
        }
        
        default ComponentLike lambda$replacement$3(final Component component, final MatchResult matchResult, final TextComponent.Builder builder) {
            return component;
        }
        
        default ComponentLike lambda$replacement$2(final String content, final TextComponent.Builder builder) {
            return builder.content(content);
        }
        
        default PatternReplacementResult lambda$condition$1(final IntFunction2 intFunction2, final MatchResult matchResult, final int first, final int second) {
            return (PatternReplacementResult)intFunction2.apply(first, second);
        }
        
        default PatternReplacementResult lambda$times$0(final int n, final int n2, final int n3) {
            return (n3 < n) ? PatternReplacementResult.REPLACE : PatternReplacementResult.STOP;
        }
    }
}
