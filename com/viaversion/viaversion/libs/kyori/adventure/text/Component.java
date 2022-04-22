package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.stream.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.translation.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.util.*;
import java.util.regex.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;

@ApiStatus.NonExtendable
public interface Component extends ComponentBuilderApplicable, ComponentLike, Examinable, HoverEventSource
{
    public static final BiPredicate EQUALS = Objects::equals;
    public static final BiPredicate EQUALS_IDENTITY = Component::lambda$static$0;
    
    @NotNull
    default TextComponent empty() {
        return TextComponentImpl.EMPTY;
    }
    
    @NotNull
    default TextComponent newline() {
        return TextComponentImpl.NEWLINE;
    }
    
    @NotNull
    default TextComponent space() {
        return TextComponentImpl.SPACE;
    }
    
    @Deprecated
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent join(@NotNull final ComponentLike separator, @NotNull final ComponentLike... components) {
        return join(separator, Arrays.asList(components));
    }
    
    @Deprecated
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent join(@NotNull final ComponentLike separator, final Iterable components) {
        final Component join = join(JoinConfiguration.separator(separator), components);
        if (join instanceof TextComponent) {
            return (TextComponent)join;
        }
        return (TextComponent)((TextComponent.Builder)text().append(join)).build();
    }
    
    @Contract(pure = true)
    @NotNull
    default Component join(@NotNull final JoinConfiguration config, @NotNull final ComponentLike... components) {
        return join(config, Arrays.asList(components));
    }
    
    @Contract(pure = true)
    @NotNull
    default Component join(@NotNull final JoinConfiguration config, @NotNull final Iterable components) {
        return JoinConfigurationImpl.join(config, components);
    }
    
    @NotNull
    default Collector toComponent() {
        return toComponent(empty());
    }
    
    @NotNull
    default Collector toComponent(@NotNull final Component separator) {
        return Collector.of(Component::text, Component::lambda$toComponent$1, Component::lambda$toComponent$2, ComponentBuilder::build, new Collector.Characteristics[0]);
    }
    
    @Contract(pure = true)
    default BlockNBTComponent.Builder blockNBT() {
        return new BlockNBTComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default BlockNBTComponent blockNBT(@NotNull final Consumer consumer) {
        return (BlockNBTComponent)Buildable.configureAndBuild(blockNBT(), consumer);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default BlockNBTComponent blockNBT(@NotNull final String nbtPath, final BlockNBTComponent.Pos pos) {
        return blockNBT(nbtPath, false, pos);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default BlockNBTComponent blockNBT(@NotNull final String nbtPath, final boolean interpret, final BlockNBTComponent.Pos pos) {
        return blockNBT(nbtPath, interpret, null, pos);
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    default BlockNBTComponent blockNBT(@NotNull final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, final BlockNBTComponent.Pos pos) {
        return new BlockNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, pos);
    }
    
    @Contract(pure = true)
    default EntityNBTComponent.Builder entityNBT() {
        return new EntityNBTComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default EntityNBTComponent entityNBT(@NotNull final Consumer consumer) {
        return (EntityNBTComponent)Buildable.configureAndBuild(entityNBT(), consumer);
    }
    
    @Contract("_, _ -> new")
    @NotNull
    default EntityNBTComponent entityNBT(@NotNull final String nbtPath, @NotNull final String selector) {
        return (EntityNBTComponent)((EntityNBTComponent.Builder)entityNBT().nbtPath(nbtPath)).selector(selector).build();
    }
    
    @Contract(pure = true)
    default KeybindComponent.Builder keybind() {
        return new KeybindComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default KeybindComponent keybind(@NotNull final Consumer consumer) {
        return (KeybindComponent)Buildable.configureAndBuild(keybind(), consumer);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull final String keybind) {
        return keybind(keybind, Style.empty());
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(final KeybindComponent.KeybindLike keybind) {
        return keybind(Objects.requireNonNull(keybind, "keybind").asKeybind(), Style.empty());
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull final String keybind, @NotNull final Style style) {
        return new KeybindComponentImpl(Collections.emptyList(), style, keybind);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @NotNull final Style style) {
        return new KeybindComponentImpl(Collections.emptyList(), style, Objects.requireNonNull(keybind, "keybind").asKeybind());
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color) {
        return keybind(keybind, Style.style(color));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @Nullable final TextColor color) {
        return keybind(Objects.requireNonNull(keybind, "keybind").asKeybind(), Style.style(color));
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color, final TextDecoration... decorations) {
        return keybind(keybind, Style.style(color, decorations));
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @Nullable final TextColor color, final TextDecoration... decorations) {
        return keybind(Objects.requireNonNull(keybind, "keybind").asKeybind(), Style.style(color, decorations));
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color, @NotNull final Set decorations) {
        return keybind(keybind, Style.style(color, decorations));
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @Nullable final TextColor color, @NotNull final Set decorations) {
        return keybind(Objects.requireNonNull(keybind, "keybind").asKeybind(), Style.style(color, decorations));
    }
    
    @Contract(pure = true)
    default ScoreComponent.Builder score() {
        return new ScoreComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default ScoreComponent score(@NotNull final Consumer consumer) {
        return (ScoreComponent)Buildable.configureAndBuild(score(), consumer);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default ScoreComponent score(@NotNull final String name, @NotNull final String objective) {
        return score(name, objective, null);
    }
    
    @Deprecated
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default ScoreComponent score(@NotNull final String name, @NotNull final String objective, @Nullable final String value) {
        return new ScoreComponentImpl(Collections.emptyList(), Style.empty(), name, objective, value);
    }
    
    @Contract(pure = true)
    default SelectorComponent.Builder selector() {
        return new SelectorComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default SelectorComponent selector(@NotNull final Consumer consumer) {
        return (SelectorComponent)Buildable.configureAndBuild(selector(), consumer);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default SelectorComponent selector(@NotNull final String pattern) {
        return selector(pattern, null);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default SelectorComponent selector(@NotNull final String pattern, @Nullable final ComponentLike separator) {
        return new SelectorComponentImpl(Collections.emptyList(), Style.empty(), pattern, separator);
    }
    
    @Contract(pure = true)
    default StorageNBTComponent.Builder storageNBT() {
        return new StorageNBTComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default StorageNBTComponent storageNBT(@NotNull final Consumer consumer) {
        return (StorageNBTComponent)Buildable.configureAndBuild(storageNBT(), consumer);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default StorageNBTComponent storageNBT(@NotNull final String nbtPath, @NotNull final Key storage) {
        return storageNBT(nbtPath, false, storage);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default StorageNBTComponent storageNBT(@NotNull final String nbtPath, final boolean interpret, @NotNull final Key storage) {
        return storageNBT(nbtPath, interpret, null, storage);
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    default StorageNBTComponent storageNBT(@NotNull final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, @NotNull final Key storage) {
        return new StorageNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, storage);
    }
    
    @Contract(pure = true)
    default TextComponent.Builder text() {
        return new TextComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default TextComponent text(@NotNull final Consumer consumer) {
        return (TextComponent)Buildable.configureAndBuild(text(), consumer);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TextComponent text(@NotNull final String content) {
        if (content.isEmpty()) {
            return empty();
        }
        return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(@NotNull final String content, @NotNull final Style style) {
        return new TextComponentImpl(Collections.emptyList(), style, content);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(@NotNull final String content, @Nullable final TextColor color) {
        return new TextComponentImpl(Collections.emptyList(), Style.style(color), content);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(@NotNull final String content, @Nullable final TextColor color, final TextDecoration... decorations) {
        return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(@NotNull final String content, @Nullable final TextColor color, @NotNull final Set decorations) {
        return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TextComponent text(final boolean value) {
        return text(String.valueOf(value));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final boolean value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final boolean value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final boolean value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final boolean value, @Nullable final TextColor color, @NotNull final Set decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(pure = true)
    @NotNull
    default TextComponent text(final char value) {
        if (value == '\n') {
            return newline();
        }
        if (value == ' ') {
            return space();
        }
        return text(String.valueOf(value));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final char value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final char value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final char value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final char value, @Nullable final TextColor color, @NotNull final Set decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TextComponent text(final double value) {
        return text(String.valueOf(value));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final double value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final double value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final double value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final double value, @Nullable final TextColor color, @NotNull final Set decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TextComponent text(final float value) {
        return text(String.valueOf(value));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final float value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final float value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final float value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final float value, @Nullable final TextColor color, @NotNull final Set decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TextComponent text(final int value) {
        return text(String.valueOf(value));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final int value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final int value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final int value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final int value, @Nullable final TextColor color, @NotNull final Set decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TextComponent text(final long value) {
        return text(String.valueOf(value));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final long value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final long value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final long value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TextComponent text(final long value, @Nullable final TextColor color, @NotNull final Set decorations) {
        return text(String.valueOf(value), color, decorations);
    }
    
    @Contract(pure = true)
    default TranslatableComponent.Builder translatable() {
        return new TranslatableComponentImpl.BuilderImpl();
    }
    
    @Contract("_ -> new")
    @NotNull
    default TranslatableComponent translatable(@NotNull final Consumer consumer) {
        return (TranslatableComponent)Buildable.configureAndBuild(translatable(), consumer);
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key) {
        return translatable(key, Style.empty());
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), Style.empty());
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style) {
        return new TranslatableComponentImpl(Collections.emptyList(), style, key, Collections.emptyList());
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color) {
        return translatable(key, Style.style(color));
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, final TextDecoration... decorations) {
        return translatable(key, Style.style(color, decorations));
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, final TextDecoration... decorations) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set decorations) {
        return translatable(key, Style.style(color, decorations));
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set decorations) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @NotNull final ComponentLike... args) {
        return translatable(key, Style.empty(), args);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final ComponentLike... args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style, @NotNull final ComponentLike... args) {
        return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style, @NotNull final ComponentLike... args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style, args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final ComponentLike... args) {
        return translatable(key, Style.style(color), args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final ComponentLike... args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, args);
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final ComponentLike... args) {
        return translatable(key, Style.style(color, decorations), args);
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final ComponentLike... args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations, args);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @NotNull final List args) {
        return new TranslatableComponentImpl(Collections.emptyList(), Style.empty(), key, args);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final List args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style, @NotNull final List args) {
        return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style, @NotNull final List args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style, args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    default TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final List args) {
        return translatable(key, Style.style(color), args);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final List args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, args);
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final List args) {
        return translatable(key, Style.style(color, decorations), args);
    }
    
    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    default TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set decorations, @NotNull final List args) {
        return translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), color, decorations, args);
    }
    
    @NotNull
    List children();
    
    @Contract(pure = true)
    @NotNull
    Component children(@NotNull final List children);
    
    default boolean contains(@NotNull final Component that) {
        return this.contains(that, Component.EQUALS_IDENTITY);
    }
    
    @Deprecated
    default void detectCycle(@NotNull final Component that) {
        if (that.contains(this)) {
            throw new IllegalStateException("Component cycle detected between " + this + " and " + that);
        }
    }
    
    @Contract(pure = true)
    @NotNull
    Component append(@NotNull final Component component);
    
    @NotNull
    default Component append(@NotNull final ComponentLike component) {
        return this.append(component.asComponent());
    }
    
    @Contract(pure = true)
    @NotNull
    default Component append(@NotNull final ComponentBuilder builder) {
        return this.append(builder.build());
    }
    
    @NotNull
    Style style();
    
    @Contract(pure = true)
    @NotNull
    Component style(@NotNull final Style style);
    
    @Contract(pure = true)
    @NotNull
    default Component style(@NotNull final Consumer consumer) {
        return this.style(this.style().edit(consumer));
    }
    
    @Contract(pure = true)
    @NotNull
    default Component style(@NotNull final Consumer consumer, final Style.Merge.Strategy strategy) {
        return this.style(this.style().edit(consumer, strategy));
    }
    
    @Contract(pure = true)
    @NotNull
    default Component style(final Style.Builder style) {
        return this.style(style.build());
    }
    
    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull final Component that) {
        return this.mergeStyle(that, Style.Merge.all());
    }
    
    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull final Component that, final Style.Merge... merges) {
        return this.mergeStyle(that, Style.Merge.of(merges));
    }
    
    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull final Component that, @NotNull final Set merges) {
        return this.style(this.style().merge(that.style(), merges));
    }
    
    @Nullable
    default TextColor color() {
        return this.style().color();
    }
    
    @Contract(pure = true)
    @NotNull
    default Component color(@Nullable final TextColor color) {
        return this.style(this.style().color(color));
    }
    
    @Contract(pure = true)
    @NotNull
    default Component colorIfAbsent(@Nullable final TextColor color) {
        if (this.color() == null) {
            return this.color(color);
        }
        return this;
    }
    
    default boolean hasDecoration(@NotNull final TextDecoration decoration) {
        return this.decoration(decoration) == TextDecoration.State.TRUE;
    }
    
    @Contract(pure = true)
    @NotNull
    default Component decorate(@NotNull final TextDecoration decoration) {
        return this.decoration(decoration, TextDecoration.State.TRUE);
    }
    
    default TextDecoration.State decoration(@NotNull final TextDecoration decoration) {
        return this.style().decoration(decoration);
    }
    
    @Contract(pure = true)
    @NotNull
    default Component decoration(@NotNull final TextDecoration decoration, final boolean flag) {
        return this.decoration(decoration, TextDecoration.State.byBoolean(flag));
    }
    
    @Contract(pure = true)
    @NotNull
    default Component decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        return this.style(this.style().decoration(decoration, state));
    }
    
    @NotNull
    default Map decorations() {
        return this.style().decorations();
    }
    
    @Contract(pure = true)
    @NotNull
    default Component decorations(@NotNull final Map decorations) {
        return this.style(this.style().decorations(decorations));
    }
    
    @Nullable
    default ClickEvent clickEvent() {
        return this.style().clickEvent();
    }
    
    @Contract(pure = true)
    @NotNull
    default Component clickEvent(@Nullable final ClickEvent event) {
        return this.style(this.style().clickEvent(event));
    }
    
    @Nullable
    default HoverEvent hoverEvent() {
        return this.style().hoverEvent();
    }
    
    @Contract(pure = true)
    @NotNull
    default Component hoverEvent(@Nullable final HoverEventSource source) {
        return this.style(this.style().hoverEvent(source));
    }
    
    @Nullable
    default String insertion() {
        return this.style().insertion();
    }
    
    @Contract(pure = true)
    @NotNull
    default Component insertion(@Nullable final String insertion) {
        return this.style(this.style().insertion(insertion));
    }
    
    default boolean hasStyling() {
        return !this.style().isEmpty();
    }
    
    @Contract(pure = true)
    @NotNull
    Component replaceText(@NotNull final Consumer configurer);
    
    @Contract(pure = true)
    @NotNull
    Component replaceText(@NotNull final TextReplacementConfig config);
    
    @NotNull
    Component compact();
    
    @NotNull
    default Iterable iterable(@NotNull final ComponentIteratorType type, @NotNull final ComponentIteratorFlag... flags) {
        return this.iterable(type, (flags == null) ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
    }
    
    @NotNull
    default Iterable iterable(@NotNull final ComponentIteratorType type, @NotNull final Set flags) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(flags, "flags");
        return new ForwardingIterator(this::lambda$iterable$3, this::lambda$iterable$4);
    }
    
    @NotNull
    default Iterator iterator(@NotNull final ComponentIteratorType type, @NotNull final ComponentIteratorFlag... flags) {
        return this.iterator(type, (flags == null) ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
    }
    
    @NotNull
    default Iterator iterator(@NotNull final ComponentIteratorType type, @NotNull final Set flags) {
        return new ComponentIterator(this, Objects.requireNonNull(type, "type"), Objects.requireNonNull(flags, "flags"));
    }
    
    @NotNull
    default Spliterator spliterator(@NotNull final ComponentIteratorType type, @NotNull final ComponentIteratorFlag... flags) {
        return this.spliterator(type, (flags == null) ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
    }
    
    @NotNull
    default Spliterator spliterator(@NotNull final ComponentIteratorType type, @NotNull final Set flags) {
        return Spliterators.spliteratorUnknownSize((Iterator<?>)this.iterator(type, flags), 0);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement) {
        return this.replaceText(Component::lambda$replaceText$5);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function replacement) {
        return this.replaceText(Component::lambda$replaceText$6);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceFirstText(@NotNull final String search, @Nullable final ComponentLike replacement) {
        return this.replaceText(Component::lambda$replaceFirstText$7);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceFirstText(@NotNull final Pattern pattern, @NotNull final Function replacement) {
        return this.replaceText(Component::lambda$replaceFirstText$8);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement, final int numberOfReplacements) {
        return this.replaceText(Component::lambda$replaceText$9);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function replacement, final int numberOfReplacements) {
        return this.replaceText(Component::lambda$replaceText$10);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement, @NotNull final IntFunction2 fn) {
        return this.replaceText(Component::lambda$replaceText$11);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function replacement, @NotNull final IntFunction2 fn) {
        return this.replaceText(Component::lambda$replaceText$12);
    }
    
    default void componentBuilderApply(@NotNull final ComponentBuilder component) {
        component.append(this);
    }
    
    @NotNull
    default Component asComponent() {
        return this;
    }
    
    @NotNull
    default HoverEvent asHoverEvent(@NotNull final UnaryOperator op) {
        return HoverEvent.showText(op.apply(this));
    }
    
    default void lambda$replaceText$12(final Pattern pattern, final Function replacement, final IntFunction2 condition, final TextReplacementConfig.Builder builder) {
        builder.match(pattern).replacement(replacement).condition(condition);
    }
    
    default void lambda$replaceText$11(final String literal, final ComponentLike replacement, final IntFunction2 condition, final TextReplacementConfig.Builder builder) {
        builder.matchLiteral(literal).replacement(replacement).condition(condition);
    }
    
    default void lambda$replaceText$10(final Pattern pattern, final int times, final Function replacement, final TextReplacementConfig.Builder builder) {
        builder.match(pattern).times(times).replacement(replacement);
    }
    
    default void lambda$replaceText$9(final String literal, final int times, final ComponentLike replacement, final TextReplacementConfig.Builder builder) {
        builder.matchLiteral(literal).times(times).replacement(replacement);
    }
    
    default void lambda$replaceFirstText$8(final Pattern pattern, final Function replacement, final TextReplacementConfig.Builder builder) {
        builder.match(pattern).once().replacement(replacement);
    }
    
    default void lambda$replaceFirstText$7(final String literal, final ComponentLike replacement, final TextReplacementConfig.Builder builder) {
        builder.matchLiteral(literal).once().replacement(replacement);
    }
    
    default void lambda$replaceText$6(final Pattern pattern, final Function replacement, final TextReplacementConfig.Builder builder) {
        builder.match(pattern).replacement(replacement);
    }
    
    default void lambda$replaceText$5(final String literal, final ComponentLike replacement, final TextReplacementConfig.Builder builder) {
        builder.matchLiteral(literal).replacement(replacement);
    }
    
    default Spliterator lambda$iterable$4(final ComponentIteratorType type, final Set flags) {
        return this.spliterator(type, flags);
    }
    
    default Iterator lambda$iterable$3(final ComponentIteratorType type, final Set flags) {
        return this.iterator(type, flags);
    }
    
    default TextComponent.Builder lambda$toComponent$2(final Component component, final TextComponent.Builder builder, final TextComponent.Builder builder2) {
        final List children = builder.children();
        final TextComponent.Builder builder3 = (TextComponent.Builder)text().append(children);
        if (!children.isEmpty()) {
            builder3.append(component);
        }
        builder3.append(builder2.children());
        return builder3;
    }
    
    default void lambda$toComponent$1(final Component component, final TextComponent.Builder builder, final Component component2) {
        if (component != empty() && !builder.children().isEmpty()) {
            builder.append(component);
        }
        builder.append(component2);
    }
    
    default boolean lambda$static$0(final Component component, final Component component2) {
        return component == component2;
    }
}
