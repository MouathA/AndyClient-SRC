package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import java.util.concurrent.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;

final class ComponentFlattenerImpl implements ComponentFlattener
{
    static final ComponentFlattener BASIC;
    static final ComponentFlattener TEXT_ONLY;
    private static final int MAX_DEPTH = 512;
    private final Map flatteners;
    private final Map complexFlatteners;
    private final ConcurrentMap propagatedFlatteners;
    private final Function unknownHandler;
    
    ComponentFlattenerImpl(final Map flatteners, final Map complexFlatteners, @Nullable final Function unknownHandler) {
        this.propagatedFlatteners = new ConcurrentHashMap();
        this.flatteners = Collections.unmodifiableMap((Map<?, ?>)new HashMap<Object, Object>(flatteners));
        this.complexFlatteners = Collections.unmodifiableMap((Map<?, ?>)new HashMap<Object, Object>(complexFlatteners));
        this.unknownHandler = unknownHandler;
    }
    
    @Override
    public void flatten(@NotNull final Component input, @NotNull final FlattenerListener listener) {
        this.flatten0(input, listener, 0);
    }
    
    private void flatten0(@NotNull final Component input, @NotNull final FlattenerListener listener, final int depth) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(listener, "listener");
        if (input == Component.empty()) {
            return;
        }
        if (depth > 512) {
            throw new IllegalStateException("Exceeded maximum depth of 512 while attempting to flatten components!");
        }
        final Handler flattener = this.flattener(input);
        final Style style = input.style();
        listener.pushStyle(style);
        if (flattener != null) {
            flattener.handle(input, listener, depth + 1);
        }
        if (!input.children().isEmpty()) {
            final Iterator<Component> iterator = input.children().iterator();
            while (iterator.hasNext()) {
                this.flatten0(iterator.next(), listener, depth + 1);
            }
        }
        listener.popStyle(style);
    }
    
    @Nullable
    private Handler flattener(final Component test) {
        final Handler handler = this.propagatedFlatteners.computeIfAbsent(test.getClass(), this::lambda$flattener$7);
        if (handler == Handler.NONE) {
            return (this.unknownHandler == null) ? null : this::lambda$flattener$8;
        }
        return handler;
    }
    
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
    }
    
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    private void lambda$flattener$8(final Component component, final FlattenerListener flattenerListener, final int n) {
        this.unknownHandler.apply(component);
    }
    
    private Handler lambda$flattener$7(final Class clazz) {
        final Function function = this.flatteners.get(clazz);
        if (function != null) {
            return ComponentFlattenerImpl::lambda$flattener$1;
        }
        for (final Map.Entry<Class, V> entry : this.flatteners.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return ComponentFlattenerImpl::lambda$flattener$2;
            }
        }
        final BiConsumer biConsumer = this.complexFlatteners.get(clazz);
        if (biConsumer != null) {
            return this::lambda$flattener$4;
        }
        for (final Map.Entry<Class, V> entry2 : this.complexFlatteners.entrySet()) {
            if (entry2.getKey().isAssignableFrom(clazz)) {
                return this::lambda$flattener$6;
            }
        }
        return Handler.NONE;
    }
    
    private void lambda$flattener$6(final Map.Entry entry, final Component component, final FlattenerListener flattenerListener, final int n) {
        entry.getValue().accept(component, this::lambda$flattener$5);
    }
    
    private void lambda$flattener$5(final FlattenerListener listener, final int depth, final Component input) {
        this.flatten0(input, listener, depth);
    }
    
    private void lambda$flattener$4(final BiConsumer biConsumer, final Component component, final FlattenerListener flattenerListener, final int n) {
        biConsumer.accept(component, this::lambda$flattener$3);
    }
    
    private void lambda$flattener$3(final FlattenerListener listener, final int depth, final Component input) {
        this.flatten0(input, listener, depth);
    }
    
    private static void lambda$flattener$2(final Map.Entry entry, final Component component, final FlattenerListener flattenerListener, final int n) {
        flattenerListener.component(entry.getValue().apply(component));
    }
    
    private static void lambda$flattener$1(final Function function, final Component component, final FlattenerListener flattenerListener, final int n) {
        flattenerListener.component(function.apply(component));
    }
    
    private static String lambda$static$0(final KeybindComponent keybindComponent) {
        return keybindComponent.keybind();
    }
    
    static {
        BASIC = (ComponentFlattener)new BuilderImpl().mapper(KeybindComponent.class, ComponentFlattenerImpl::lambda$static$0).mapper(ScoreComponent.class, ScoreComponent::value).mapper(SelectorComponent.class, SelectorComponent::pattern).mapper(TextComponent.class, TextComponent::content).mapper(TranslatableComponent.class, TranslatableComponent::key).build();
        TEXT_ONLY = (ComponentFlattener)new BuilderImpl().mapper(TextComponent.class, TextComponent::content).build();
    }
    
    static final class BuilderImpl implements Builder
    {
        private final Map flatteners;
        private final Map complexFlatteners;
        @Nullable
        private Function unknownHandler;
        
        BuilderImpl() {
            this.flatteners = new HashMap();
            this.complexFlatteners = new HashMap();
        }
        
        BuilderImpl(final Map flatteners, final Map complexFlatteners, @Nullable final Function unknownHandler) {
            this.flatteners = new HashMap(flatteners);
            this.complexFlatteners = new HashMap(complexFlatteners);
            this.unknownHandler = unknownHandler;
        }
        
        @NotNull
        @Override
        public ComponentFlattener build() {
            return new ComponentFlattenerImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
        }
        
        @Override
        public Builder mapper(@NotNull final Class type, @NotNull final Function converter) {
            this.validateNoneInHierarchy(Objects.requireNonNull(type, "type"));
            this.flatteners.put(type, Objects.requireNonNull(converter, "converter"));
            this.complexFlatteners.remove(type);
            return this;
        }
        
        @Override
        public Builder complexMapper(@NotNull final Class type, @NotNull final BiConsumer converter) {
            this.validateNoneInHierarchy(Objects.requireNonNull(type, "type"));
            this.complexFlatteners.put(type, Objects.requireNonNull(converter, "converter"));
            this.flatteners.remove(type);
            return this;
        }
        
        private void validateNoneInHierarchy(final Class beingRegistered) {
            final Iterator<Class> iterator = this.flatteners.keySet().iterator();
            while (iterator.hasNext()) {
                testHierarchy(iterator.next(), beingRegistered);
            }
            final Iterator<Class> iterator2 = this.complexFlatteners.keySet().iterator();
            while (iterator2.hasNext()) {
                testHierarchy(iterator2.next(), beingRegistered);
            }
        }
        
        private static void testHierarchy(final Class existing, final Class beingRegistered) {
            if (!existing.equals(beingRegistered) && (existing.isAssignableFrom(beingRegistered) || beingRegistered.isAssignableFrom(existing))) {
                throw new IllegalArgumentException("Conflict detected between already registered type " + existing + " and newly registered type " + beingRegistered + "! Types in a component flattener must not share a common hierachy!");
            }
        }
        
        @Override
        public Builder unknownMapper(@Nullable final Function converter) {
            this.unknownHandler = converter;
            return this;
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
    }
    
    @FunctionalInterface
    interface Handler
    {
        public static final Handler NONE = Handler::lambda$static$0;
        
        void handle(final Component input, final FlattenerListener listener, final int depth);
        
        default void lambda$static$0(final Component component, final FlattenerListener flattenerListener, final int n) {
        }
    }
}
