package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

@ApiStatus.NonExtendable
public interface Style extends Buildable, Examinable
{
    public static final Key DEFAULT_FONT = Key.key("default");
    
    @NotNull
    default Style empty() {
        return StyleImpl.EMPTY;
    }
    
    @NotNull
    default Builder style() {
        return new StyleImpl.BuilderImpl();
    }
    
    @NotNull
    default Style style(@NotNull final Consumer consumer) {
        return (Style)Buildable.configureAndBuild(style(), consumer);
    }
    
    @NotNull
    default Style style(@Nullable final TextColor color) {
        if (color == null) {
            return empty();
        }
        return new StyleImpl(null, color, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
    }
    
    @NotNull
    default Style style(@NotNull final TextDecoration decoration) {
        return style().decoration(decoration, true).build();
    }
    
    @NotNull
    default Style style(@Nullable final TextColor color, final TextDecoration... decorations) {
        final Builder style = style();
        style.color(color);
        StyleImpl.decorate(style, decorations);
        return style.build();
    }
    
    @NotNull
    default Style style(@Nullable final TextColor color, final Set decorations) {
        final Builder style = style();
        style.color(color);
        if (!decorations.isEmpty()) {
            final Iterator<TextDecoration> iterator = decorations.iterator();
            while (iterator.hasNext()) {
                style.decoration(iterator.next(), true);
            }
        }
        return style.build();
    }
    
    @NotNull
    default Style style(final StyleBuilderApplicable... applicables) {
        if (applicables.length == 0) {
            return empty();
        }
        final Builder style = style();
        while (0 < applicables.length) {
            applicables[0].styleApply(style);
            int n = 0;
            ++n;
        }
        return style.build();
    }
    
    @NotNull
    default Style style(@NotNull final Iterable applicables) {
        final Builder style = style();
        final Iterator<StyleBuilderApplicable> iterator = applicables.iterator();
        while (iterator.hasNext()) {
            iterator.next().styleApply(style);
        }
        return style.build();
    }
    
    @NotNull
    default Style edit(@NotNull final Consumer consumer) {
        return this.edit(consumer, Merge.Strategy.ALWAYS);
    }
    
    @NotNull
    default Style edit(@NotNull final Consumer consumer, final Merge.Strategy strategy) {
        return style(this::lambda$edit$0);
    }
    
    @Nullable
    Key font();
    
    @NotNull
    Style font(@Nullable final Key font);
    
    @Nullable
    TextColor color();
    
    @NotNull
    Style color(@Nullable final TextColor color);
    
    @NotNull
    Style colorIfAbsent(@Nullable final TextColor color);
    
    default boolean hasDecoration(@NotNull final TextDecoration decoration) {
        return this.decoration(decoration) == TextDecoration.State.TRUE;
    }
    
    TextDecoration.State decoration(@NotNull final TextDecoration decoration);
    
    @NotNull
    default Style decorate(@NotNull final TextDecoration decoration) {
        return this.decoration(decoration, TextDecoration.State.TRUE);
    }
    
    @NotNull
    default Style decoration(@NotNull final TextDecoration decoration, final boolean flag) {
        return this.decoration(decoration, TextDecoration.State.byBoolean(flag));
    }
    
    @NotNull
    Style decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state);
    
    @NotNull
    Map decorations();
    
    @NotNull
    Style decorations(@NotNull final Map decorations);
    
    @Nullable
    ClickEvent clickEvent();
    
    @NotNull
    Style clickEvent(@Nullable final ClickEvent event);
    
    @Nullable
    HoverEvent hoverEvent();
    
    @NotNull
    Style hoverEvent(@Nullable final HoverEventSource source);
    
    @Nullable
    String insertion();
    
    @NotNull
    Style insertion(@Nullable final String insertion);
    
    @NotNull
    default Style merge(@NotNull final Style that) {
        return this.merge(that, Merge.all());
    }
    
    @NotNull
    default Style merge(@NotNull final Style that, final Merge.Strategy strategy) {
        return this.merge(that, strategy, Merge.all());
    }
    
    @NotNull
    default Style merge(@NotNull final Style that, @NotNull final Merge merge) {
        return this.merge(that, Collections.singleton(merge));
    }
    
    @NotNull
    default Style merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Merge merge) {
        return this.merge(that, strategy, Collections.singleton(merge));
    }
    
    @NotNull
    default Style merge(@NotNull final Style that, @NotNull final Merge... merges) {
        return this.merge(that, Merge.of(merges));
    }
    
    @NotNull
    default Style merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Merge... merges) {
        return this.merge(that, strategy, Merge.of(merges));
    }
    
    @NotNull
    default Style merge(@NotNull final Style that, @NotNull final Set merges) {
        return this.merge(that, Merge.Strategy.ALWAYS, merges);
    }
    
    @NotNull
    Style merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Set merges);
    
    boolean isEmpty();
    
    @NotNull
    Builder toBuilder();
    
    @NotNull
    default Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    default void lambda$edit$0(final Merge.Strategy strategy, final Consumer consumer, final Builder builder) {
        if (strategy == Merge.Strategy.ALWAYS) {
            builder.merge(this, strategy);
        }
        consumer.accept(builder);
        if (strategy == Merge.Strategy.IF_ABSENT_ON_TARGET) {
            builder.merge(this, strategy);
        }
    }
    
    public interface Builder extends Buildable.Builder
    {
        @Contract("_ -> this")
        @NotNull
        Builder font(@Nullable final Key font);
        
        @Contract("_ -> this")
        @NotNull
        Builder color(@Nullable final TextColor color);
        
        @Contract("_ -> this")
        @NotNull
        Builder colorIfAbsent(@Nullable final TextColor color);
        
        @Contract("_ -> this")
        @NotNull
        default Builder decorate(@NotNull final TextDecoration decoration) {
            return this.decoration(decoration, TextDecoration.State.TRUE);
        }
        
        @Contract("_ -> this")
        @NotNull
        default Builder decorate(@NotNull final TextDecoration... decorations) {
            while (0 < decorations.length) {
                this.decorate(decorations[0]);
                int n = 0;
                ++n;
            }
            return this;
        }
        
        @Contract("_, _ -> this")
        @NotNull
        default Builder decoration(@NotNull final TextDecoration decoration, final boolean flag) {
            return this.decoration(decoration, TextDecoration.State.byBoolean(flag));
        }
        
        @Contract("_, _ -> this")
        @NotNull
        Builder decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state);
        
        @Contract("_ -> this")
        @NotNull
        Builder clickEvent(@Nullable final ClickEvent event);
        
        @Contract("_ -> this")
        @NotNull
        Builder hoverEvent(@Nullable final HoverEventSource source);
        
        @Contract("_ -> this")
        @NotNull
        Builder insertion(@Nullable final String insertion);
        
        @Contract("_ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that) {
            return this.merge(that, Merge.all());
        }
        
        @Contract("_, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, final Merge.Strategy strategy) {
            return this.merge(that, strategy, Merge.all());
        }
        
        @Contract("_, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, @NotNull final Merge... merges) {
            if (merges.length == 0) {
                return this;
            }
            return this.merge(that, Merge.of(merges));
        }
        
        @Contract("_, _, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Merge... merges) {
            if (merges.length == 0) {
                return this;
            }
            return this.merge(that, strategy, Merge.of(merges));
        }
        
        @Contract("_, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, @NotNull final Set merges) {
            return this.merge(that, Merge.Strategy.ALWAYS, merges);
        }
        
        @Contract("_, _, _ -> this")
        @NotNull
        Builder merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Set merges);
        
        @Contract("_ -> this")
        @NotNull
        default Builder apply(@NotNull final StyleBuilderApplicable applicable) {
            applicable.styleApply(this);
            return this;
        }
        
        @NotNull
        Style build();
        
        @NotNull
        default Object build() {
            return this.build();
        }
    }
    
    public enum Merge
    {
        COLOR, 
        DECORATIONS, 
        EVENTS, 
        INSERTION, 
        FONT;
        
        static final Set ALL;
        static final Set COLOR_AND_DECORATIONS;
        private static final Merge[] $VALUES;
        
        @NotNull
        public static Set all() {
            return Merge.ALL;
        }
        
        @NotNull
        public static Set colorAndDecorations() {
            return Merge.COLOR_AND_DECORATIONS;
        }
        
        @NotNull
        public static Set of(final Merge... merges) {
            return MonkeyBars.enumSet(Merge.class, (Enum[])merges);
        }
        
        static boolean hasAll(@NotNull final Set merges) {
            return merges.size() == Merge.ALL.size();
        }
        
        static {
            $VALUES = new Merge[] { Merge.COLOR, Merge.DECORATIONS, Merge.EVENTS, Merge.INSERTION, Merge.FONT };
            ALL = of(values());
            COLOR_AND_DECORATIONS = of(Merge.COLOR, Merge.DECORATIONS);
        }
        
        public enum Strategy
        {
            ALWAYS, 
            NEVER, 
            IF_ABSENT_ON_TARGET;
            
            private static final Strategy[] $VALUES;
            
            static {
                $VALUES = new Strategy[] { Strategy.ALWAYS, Strategy.NEVER, Strategy.IF_ABSENT_ON_TARGET };
            }
        }
    }
}
