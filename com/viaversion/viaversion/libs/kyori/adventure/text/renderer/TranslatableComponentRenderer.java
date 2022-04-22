package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.translation.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;

public abstract class TranslatableComponentRenderer extends AbstractComponentRenderer
{
    private static final Set MERGES;
    
    @NotNull
    public static TranslatableComponentRenderer usingTranslationSource(@NotNull final Translator source) {
        Objects.requireNonNull(source, "source");
        return new TranslatableComponentRenderer() {
            final Translator val$source;
            
            @Nullable
            protected MessageFormat translate(@NotNull final String key, @NotNull final Locale context) {
                return this.val$source.translate(key, context);
            }
            
            @Nullable
            @Override
            protected MessageFormat translate(@NotNull final String key, @NotNull final Object context) {
                return this.translate(key, (Locale)context);
            }
        };
    }
    
    @Nullable
    protected abstract MessageFormat translate(@NotNull final String key, @NotNull final Object context);
    
    @NotNull
    @Override
    protected Component renderBlockNbt(@NotNull final BlockNBTComponent component, @NotNull final Object context) {
        return this.mergeStyleAndOptionallyDeepRender(component, ((BlockNBTComponent.Builder)nbt(Component.blockNBT(), component)).pos(component.pos()), context);
    }
    
    @NotNull
    @Override
    protected Component renderEntityNbt(@NotNull final EntityNBTComponent component, @NotNull final Object context) {
        return this.mergeStyleAndOptionallyDeepRender(component, ((EntityNBTComponent.Builder)nbt(Component.entityNBT(), component)).selector(component.selector()), context);
    }
    
    @NotNull
    @Override
    protected Component renderStorageNbt(@NotNull final StorageNBTComponent component, @NotNull final Object context) {
        return this.mergeStyleAndOptionallyDeepRender(component, ((StorageNBTComponent.Builder)nbt(Component.storageNBT(), component)).storage(component.storage()), context);
    }
    
    protected static NBTComponentBuilder nbt(final NBTComponentBuilder builder, final NBTComponent oldComponent) {
        return builder.nbtPath(oldComponent.nbtPath()).interpret(oldComponent.interpret());
    }
    
    @NotNull
    @Override
    protected Component renderKeybind(@NotNull final KeybindComponent component, @NotNull final Object context) {
        return this.mergeStyleAndOptionallyDeepRender(component, Component.keybind().keybind(component.keybind()), context);
    }
    
    @NotNull
    @Override
    protected Component renderScore(@NotNull final ScoreComponent component, @NotNull final Object context) {
        return this.mergeStyleAndOptionallyDeepRender(component, Component.score().name(component.name()).objective(component.objective()).value(component.value()), context);
    }
    
    @NotNull
    @Override
    protected Component renderSelector(@NotNull final SelectorComponent component, @NotNull final Object context) {
        return this.mergeStyleAndOptionallyDeepRender(component, Component.selector().pattern(component.pattern()), context);
    }
    
    @NotNull
    @Override
    protected Component renderText(@NotNull final TextComponent component, @NotNull final Object context) {
        return this.mergeStyleAndOptionallyDeepRender(component, Component.text().content(component.content()), context);
    }
    
    @NotNull
    @Override
    protected Component renderTranslatable(@NotNull final TranslatableComponent component, @NotNull final Object context) {
        final MessageFormat translate = this.translate(component.key(), context);
        if (translate == null) {
            final TranslatableComponent.Builder key = Component.translatable().key(component.key());
            if (!component.args().isEmpty()) {
                final ArrayList<Component> args = new ArrayList<Component>(component.args());
                while (0 < args.size()) {
                    args.set(0, this.render((Component)args.get(0), context));
                    int n = 0;
                    ++n;
                }
                key.args(args);
            }
            return this.mergeStyleAndOptionallyDeepRender(component, key, context);
        }
        final List args2 = component.args();
        final TextComponent.Builder text = Component.text();
        this.mergeStyle(component, text, context);
        if (args2.isEmpty()) {
            text.content(translate.format(null, new StringBuffer(), null).toString());
            return this.optionallyRenderChildrenAppendAndBuild(component.children(), text, context);
        }
        final Object[] array = new Object[args2.size()];
        final StringBuffer format = translate.format(array, new StringBuffer(), null);
        final AttributedCharacterIterator formatToCharacterIterator = translate.formatToCharacterIterator(array);
        while (formatToCharacterIterator.getIndex() < formatToCharacterIterator.getEndIndex()) {
            final int runLimit = formatToCharacterIterator.getRunLimit();
            final Integer n2 = (Integer)formatToCharacterIterator.getAttribute(MessageFormat.Field.ARGUMENT);
            if (n2 != null) {
                text.append(this.render(args2.get(n2), context));
            }
            else {
                text.append(Component.text(format.substring(formatToCharacterIterator.getIndex(), runLimit)));
            }
            formatToCharacterIterator.setIndex(runLimit);
        }
        return this.optionallyRenderChildrenAppendAndBuild(component.children(), text, context);
    }
    
    protected BuildableComponent mergeStyleAndOptionallyDeepRender(final Component component, final ComponentBuilder builder, final Object context) {
        this.mergeStyle(component, builder, context);
        return this.optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
    }
    
    protected BuildableComponent optionallyRenderChildrenAppendAndBuild(final List children, final ComponentBuilder builder, final Object context) {
        if (!children.isEmpty()) {
            children.forEach(this::lambda$optionallyRenderChildrenAppendAndBuild$0);
        }
        return builder.build();
    }
    
    protected void mergeStyle(final Component component, final ComponentBuilder builder, final Object context) {
        builder.mergeStyle(component, TranslatableComponentRenderer.MERGES);
        builder.clickEvent(component.clickEvent());
        final HoverEvent hoverEvent = component.hoverEvent();
        if (hoverEvent != null) {
            builder.hoverEvent(hoverEvent.withRenderedValue(this, context));
        }
    }
    
    private void lambda$optionallyRenderChildrenAppendAndBuild$0(final ComponentBuilder componentBuilder, final Object context, final Component component) {
        componentBuilder.append(this.render(component, context));
    }
    
    static {
        MERGES = Style.Merge.of(Style.Merge.COLOR, Style.Merge.DECORATIONS, Style.Merge.INSERTION, Style.Merge.FONT);
    }
}
