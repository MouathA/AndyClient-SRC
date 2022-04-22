package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class TextComponentImpl extends AbstractComponent implements TextComponent
{
    private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED;
    @VisibleForTesting
    static final char SECTION_CHAR = '§';
    static final TextComponent EMPTY;
    static final TextComponent NEWLINE;
    static final TextComponent SPACE;
    private final String content;
    
    @NotNull
    private static TextComponent createDirect(@NotNull final String content) {
        return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
    }
    
    TextComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String content) {
        super(children, style);
        this.content = Objects.requireNonNull(content, "content");
        if (TextComponentImpl.WARN_WHEN_LEGACY_FORMATTING_DETECTED) {
            final LegacyFormattingDetected warnWhenLegacyFormattingDetected = this.warnWhenLegacyFormattingDetected();
            if (warnWhenLegacyFormattingDetected != null) {
                Nag.print(warnWhenLegacyFormattingDetected);
            }
        }
    }
    
    @VisibleForTesting
    @Nullable
    final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
        if (this.content.indexOf(167) != -1) {
            return new LegacyFormattingDetected(this);
        }
        return null;
    }
    
    @NotNull
    @Override
    public String content() {
        return this.content;
    }
    
    @NotNull
    @Override
    public TextComponent content(@NotNull final String content) {
        if (Objects.equals(this.content, content)) {
            return this;
        }
        return new TextComponentImpl(this.children, this.style, Objects.requireNonNull(content, "content"));
    }
    
    @NotNull
    @Override
    public TextComponent children(@NotNull final List children) {
        return new TextComponentImpl(children, this.style, this.content);
    }
    
    @NotNull
    @Override
    public TextComponent style(@NotNull final Style style) {
        return new TextComponentImpl(this.children, style, this.content);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other instanceof TextComponentImpl && super.equals(other) && Objects.equals(this.content, ((TextComponentImpl)other).content));
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.content.hashCode();
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T)ExaminableProperty.of("content", this.content)), (Stream<?>)super.examinablePropertiesWithoutChildren());
    }
    
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @NotNull
    @Override
    public Component style(@NotNull final Style style) {
        return this.style(style);
    }
    
    @NotNull
    @Override
    public Component children(@NotNull final List children) {
        return this.children(children);
    }
    
    @NotNull
    @Override
    public ComponentBuilder toBuilder() {
        return this.toBuilder();
    }
    
    @NotNull
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static {
        WARN_WHEN_LEGACY_FORMATTING_DETECTED = Boolean.getBoolean(String.join(".", "net", "kyori", "adventure", "text", "warnWhenLegacyFormattingDetected"));
        EMPTY = createDirect("");
        NEWLINE = createDirect("\n");
        SPACE = createDirect(" ");
    }
    
    static final class BuilderImpl extends AbstractComponentBuilder implements TextComponent.Builder
    {
        private String content;
        
        BuilderImpl() {
            this.content = "";
        }
        
        BuilderImpl(@NotNull final TextComponent component) {
            super(component);
            this.content = "";
            this.content = component.content();
        }
        
        @NotNull
        @Override
        public TextComponent.Builder content(@NotNull final String content) {
            this.content = Objects.requireNonNull(content, "content");
            return this;
        }
        
        @NotNull
        @Override
        public String content() {
            return this.content;
        }
        
        @NotNull
        @Override
        public TextComponent build() {
            if (this.isEmpty()) {
                return Component.empty();
            }
            return new TextComponentImpl(this.children, this.buildStyle(), this.content);
        }
        
        private boolean isEmpty() {
            return this.content.isEmpty() && this.children.isEmpty() && !this.hasStyle();
        }
        
        @NotNull
        @Override
        public BuildableComponent build() {
            return this.build();
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
    }
}
