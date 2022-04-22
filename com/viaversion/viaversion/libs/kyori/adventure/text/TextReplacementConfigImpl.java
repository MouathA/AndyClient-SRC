package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.function.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.regex.*;

final class TextReplacementConfigImpl implements TextReplacementConfig
{
    private final Pattern matchPattern;
    private final BiFunction replacement;
    private final Condition continuer;
    
    TextReplacementConfigImpl(final Builder builder) {
        this.matchPattern = builder.matchPattern;
        this.replacement = builder.replacement;
        this.continuer = builder.continuer;
    }
    
    @NotNull
    @Override
    public Pattern matchPattern() {
        return this.matchPattern;
    }
    
    TextReplacementRenderer.State createState() {
        return new TextReplacementRenderer.State(this.matchPattern, this.replacement, this.continuer);
    }
    
    @Override
    public TextReplacementConfig.Builder toBuilder() {
        return new Builder(this);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("matchPattern", this.matchPattern), ExaminableProperty.of("replacement", this.replacement), ExaminableProperty.of("continuer", this.continuer) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static Pattern access$000(final TextReplacementConfigImpl textReplacementConfigImpl) {
        return textReplacementConfigImpl.matchPattern;
    }
    
    static BiFunction access$100(final TextReplacementConfigImpl textReplacementConfigImpl) {
        return textReplacementConfigImpl.replacement;
    }
    
    static Condition access$200(final TextReplacementConfigImpl textReplacementConfigImpl) {
        return textReplacementConfigImpl.continuer;
    }
    
    static final class Builder implements TextReplacementConfig.Builder
    {
        @Nullable
        Pattern matchPattern;
        @Nullable
        BiFunction replacement;
        Condition continuer;
        
        Builder() {
            this.continuer = Builder::lambda$new$0;
        }
        
        Builder(final TextReplacementConfigImpl instance) {
            this.continuer = Builder::lambda$new$0;
            this.matchPattern = TextReplacementConfigImpl.access$000(instance);
            this.replacement = TextReplacementConfigImpl.access$100(instance);
            this.continuer = TextReplacementConfigImpl.access$200(instance);
        }
        
        @NotNull
        @Override
        public Builder match(@NotNull final Pattern pattern) {
            this.matchPattern = Objects.requireNonNull(pattern, "pattern");
            return this;
        }
        
        @NotNull
        @Override
        public Builder condition(final Condition condition) {
            this.continuer = Objects.requireNonNull(condition, "continuation");
            return this;
        }
        
        @NotNull
        @Override
        public Builder replacement(@NotNull final BiFunction replacement) {
            this.replacement = Objects.requireNonNull(replacement, "replacement");
            return this;
        }
        
        @NotNull
        @Override
        public TextReplacementConfig build() {
            if (this.matchPattern == null) {
                throw new IllegalStateException("A pattern must be provided to match against");
            }
            if (this.replacement == null) {
                throw new IllegalStateException("A replacement action must be provided");
            }
            return new TextReplacementConfigImpl(this);
        }
        
        @NotNull
        @Override
        public TextReplacementConfig.Builder replacement(@NotNull final BiFunction replacement) {
            return this.replacement(replacement);
        }
        
        @NotNull
        @Override
        public TextReplacementConfig.Builder condition(final Condition condition) {
            return this.condition(condition);
        }
        
        @NotNull
        @Override
        public TextReplacementConfig.Builder match(@NotNull final Pattern pattern) {
            return this.match(pattern);
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
        
        private static PatternReplacementResult lambda$new$0(final MatchResult matchResult, final int n, final int n2) {
            return PatternReplacementResult.REPLACE;
        }
    }
}
