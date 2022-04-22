package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

final class BossBarImpl extends HackyBossBarPlatformBridge implements BossBar
{
    private static final BiConsumer FLAGS_ADDED;
    private static final BiConsumer FLAGS_REMOVED;
    private final List listeners;
    private Component name;
    private float progress;
    private Color color;
    private Overlay overlay;
    private final Set flags;
    
    BossBarImpl(@NotNull final Component name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay) {
        this.listeners = new CopyOnWriteArrayList();
        this.flags = EnumSet.noneOf(Flag.class);
        this.name = Objects.requireNonNull(name, "name");
        this.progress = progress;
        this.color = Objects.requireNonNull(color, "color");
        this.overlay = Objects.requireNonNull(overlay, "overlay");
    }
    
    BossBarImpl(@NotNull final Component name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay, @NotNull final Set flags) {
        this(name, progress, color, overlay);
        this.flags.addAll(flags);
    }
    
    @NotNull
    @Override
    public Component name() {
        return this.name;
    }
    
    @NotNull
    @Override
    public BossBar name(@NotNull final Component newName) {
        Objects.requireNonNull(newName, "name");
        final Component name = this.name;
        if (!Objects.equals(newName, name)) {
            this.name = newName;
            this.forEachListener(this::lambda$name$4);
        }
        return this;
    }
    
    @Override
    public float progress() {
        return this.progress;
    }
    
    @NotNull
    @Override
    public BossBar progress(final float newProgress) {
        checkProgress(newProgress);
        final float progress = this.progress;
        if (newProgress != progress) {
            this.progress = newProgress;
            this.forEachListener(this::lambda$progress$5);
        }
        return this;
    }
    
    static void checkProgress(final float progress) {
        if (progress < 0.0f || progress > 1.0f) {
            throw new IllegalArgumentException("progress must be between 0.0 and 1.0, was " + progress);
        }
    }
    
    @NotNull
    @Override
    public Color color() {
        return this.color;
    }
    
    @NotNull
    @Override
    public BossBar color(@NotNull final Color newColor) {
        Objects.requireNonNull(newColor, "color");
        final Color color = this.color;
        if (newColor != color) {
            this.color = newColor;
            this.forEachListener(this::lambda$color$6);
        }
        return this;
    }
    
    @NotNull
    @Override
    public Overlay overlay() {
        return this.overlay;
    }
    
    @NotNull
    @Override
    public BossBar overlay(@NotNull final Overlay newOverlay) {
        Objects.requireNonNull(newOverlay, "overlay");
        final Overlay overlay = this.overlay;
        if (newOverlay != overlay) {
            this.overlay = newOverlay;
            this.forEachListener(this::lambda$overlay$7);
        }
        return this;
    }
    
    @NotNull
    @Override
    public Set flags() {
        return Collections.unmodifiableSet((Set<?>)this.flags);
    }
    
    @NotNull
    @Override
    public BossBar flags(@NotNull final Set newFlags) {
        if (newFlags.isEmpty()) {
            final EnumSet<Enum> copy = EnumSet.copyOf((Collection<Enum>)this.flags);
            this.flags.clear();
            this.forEachListener(this::lambda$flags$8);
        }
        else if (!this.flags.equals(newFlags)) {
            final EnumSet<Enum> copy2 = EnumSet.copyOf((Collection<Enum>)this.flags);
            this.flags.clear();
            this.flags.addAll(newFlags);
            final EnumSet<Enum> copy3 = EnumSet.copyOf((Collection<Enum>)newFlags);
            final EnumSet<Enum> set = copy2;
            Objects.requireNonNull(set);
            copy3.removeIf(set::contains);
            final EnumSet<Enum> copy4 = EnumSet.copyOf((Collection<Enum>)copy2);
            final Set flags = this.flags;
            Objects.requireNonNull(flags);
            copy4.removeIf(flags::contains);
            this.forEachListener(this::lambda$flags$9);
        }
        return this;
    }
    
    @Override
    public boolean hasFlag(@NotNull final Flag flag) {
        return this.flags.contains(flag);
    }
    
    @NotNull
    @Override
    public BossBar addFlag(@NotNull final Flag flag) {
        return this.editFlags(flag, Set::add, BossBarImpl.FLAGS_ADDED);
    }
    
    @NotNull
    @Override
    public BossBar removeFlag(@NotNull final Flag flag) {
        return this.editFlags(flag, Set::remove, BossBarImpl.FLAGS_REMOVED);
    }
    
    @NotNull
    private BossBar editFlags(@NotNull final Flag flag, @NotNull final BiPredicate predicate, final BiConsumer onChange) {
        if (predicate.test(this.flags, flag)) {
            onChange.accept(this, Collections.singleton(flag));
        }
        return this;
    }
    
    @NotNull
    @Override
    public BossBar addFlags(@NotNull final Flag... flags) {
        return this.editFlags(flags, Set::add, BossBarImpl.FLAGS_ADDED);
    }
    
    @NotNull
    @Override
    public BossBar removeFlags(@NotNull final Flag... flags) {
        return this.editFlags(flags, Set::remove, BossBarImpl.FLAGS_REMOVED);
    }
    
    @NotNull
    private BossBar editFlags(final Flag[] flags, final BiPredicate predicate, final BiConsumer onChange) {
        if (flags.length == 0) {
            return this;
        }
        EnumSet<Flag> none = null;
        while (0 < flags.length) {
            if (predicate.test(this.flags, flags[0])) {
                if (none == null) {
                    none = EnumSet.noneOf(Flag.class);
                }
                none.add(flags[0]);
            }
            int n = 0;
            ++n;
        }
        if (none != null) {
            onChange.accept(this, none);
        }
        return this;
    }
    
    @NotNull
    @Override
    public BossBar addFlags(@NotNull final Iterable flags) {
        return this.editFlags(flags, Set::add, BossBarImpl.FLAGS_ADDED);
    }
    
    @NotNull
    @Override
    public BossBar removeFlags(@NotNull final Iterable flags) {
        return this.editFlags(flags, Set::remove, BossBarImpl.FLAGS_REMOVED);
    }
    
    @NotNull
    private BossBar editFlags(final Iterable flags, final BiPredicate predicate, final BiConsumer onChange) {
        EnumSet<Flag> none = null;
        for (final Flag flag : flags) {
            if (predicate.test(this.flags, flag)) {
                if (none == null) {
                    none = EnumSet.noneOf(Flag.class);
                }
                none.add(flag);
            }
        }
        if (none != null) {
            onChange.accept(this, none);
        }
        return this;
    }
    
    @NotNull
    @Override
    public BossBar addListener(@NotNull final Listener listener) {
        this.listeners.add(listener);
        return this;
    }
    
    @NotNull
    @Override
    public BossBar removeListener(@NotNull final Listener listener) {
        this.listeners.remove(listener);
        return this;
    }
    
    private void forEachListener(@NotNull final Consumer consumer) {
        final Iterator<Listener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("name", this.name), ExaminableProperty.of("progress", this.progress), ExaminableProperty.of("color", this.color), ExaminableProperty.of("overlay", this.overlay), ExaminableProperty.of("flags", this.flags) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    private void lambda$flags$9(final Set flagsAdded, final Set flagsRemoved, final Listener listener) {
        listener.bossBarFlagsChanged(this, flagsAdded, flagsRemoved);
    }
    
    private void lambda$flags$8(final Set flagsRemoved, final Listener listener) {
        listener.bossBarFlagsChanged(this, Collections.emptySet(), flagsRemoved);
    }
    
    private void lambda$overlay$7(final Overlay oldOverlay, final Overlay newOverlay, final Listener listener) {
        listener.bossBarOverlayChanged(this, oldOverlay, newOverlay);
    }
    
    private void lambda$color$6(final Color oldColor, final Color newColor, final Listener listener) {
        listener.bossBarColorChanged(this, oldColor, newColor);
    }
    
    private void lambda$progress$5(final float oldProgress, final float newProgress, final Listener listener) {
        listener.bossBarProgressChanged(this, oldProgress, newProgress);
    }
    
    private void lambda$name$4(final Component oldName, final Component newName, final Listener listener) {
        listener.bossBarNameChanged(this, oldName, newName);
    }
    
    private static void lambda$static$3(final BossBarImpl bossBarImpl, final Set set) {
        bossBarImpl.forEachListener(BossBarImpl::lambda$static$2);
    }
    
    private static void lambda$static$2(final BossBarImpl bar, final Set flagsRemoved, final Listener listener) {
        listener.bossBarFlagsChanged(bar, Collections.emptySet(), flagsRemoved);
    }
    
    private static void lambda$static$1(final BossBarImpl bossBarImpl, final Set set) {
        bossBarImpl.forEachListener(BossBarImpl::lambda$static$0);
    }
    
    private static void lambda$static$0(final BossBarImpl bar, final Set flagsAdded, final Listener listener) {
        listener.bossBarFlagsChanged(bar, flagsAdded, Collections.emptySet());
    }
    
    static {
        FLAGS_ADDED = BossBarImpl::lambda$static$1;
        FLAGS_REMOVED = BossBarImpl::lambda$static$3;
    }
}
