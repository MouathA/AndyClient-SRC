package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.identity.*;
import com.viaversion.viaversion.libs.kyori.adventure.title.*;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.*;
import com.viaversion.viaversion.libs.kyori.adventure.sound.*;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.*;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

@FunctionalInterface
public interface ForwardingAudience extends Audience
{
    @ApiStatus.OverrideOnly
    @NotNull
    Iterable audiences();
    
    @NotNull
    default Pointers pointers() {
        return Pointers.empty();
    }
    
    @NotNull
    default Audience filterAudience(@NotNull final Predicate filter) {
        List<Audience> audiences = null;
        for (final Audience audience : this.audiences()) {
            if (filter.test(audience)) {
                final Audience filterAudience = audience.filterAudience(filter);
                if (filterAudience == Audience.empty()) {
                    continue;
                }
                if (audiences == null) {
                    audiences = new ArrayList<Audience>();
                }
                audiences.add(filterAudience);
            }
        }
        return (audiences != null) ? Audience.audience(audiences) : Audience.empty();
    }
    
    default void forEachAudience(@NotNull final Consumer action) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().forEachAudience(action);
        }
    }
    
    default void sendMessage(@NotNull final Identified source, @NotNull final Component message, @NotNull final MessageType type) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().sendMessage(source, message, type);
        }
    }
    
    default void sendMessage(@NotNull final Identity source, @NotNull final Component message, @NotNull final MessageType type) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().sendMessage(source, message, type);
        }
    }
    
    default void sendActionBar(@NotNull final Component message) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().sendActionBar(message);
        }
    }
    
    default void sendPlayerListHeader(@NotNull final Component header) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().sendPlayerListHeader(header);
        }
    }
    
    default void sendPlayerListFooter(@NotNull final Component footer) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().sendPlayerListFooter(footer);
        }
    }
    
    default void sendPlayerListHeaderAndFooter(@NotNull final Component header, @NotNull final Component footer) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().sendPlayerListHeaderAndFooter(header, footer);
        }
    }
    
    default void sendTitlePart(@NotNull final TitlePart part, @NotNull final Object value) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().sendTitlePart(part, value);
        }
    }
    
    default void clearTitle() {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().clearTitle();
        }
    }
    
    default void resetTitle() {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().resetTitle();
        }
    }
    
    default void showBossBar(@NotNull final BossBar bar) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().showBossBar(bar);
        }
    }
    
    default void hideBossBar(@NotNull final BossBar bar) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().hideBossBar(bar);
        }
    }
    
    default void playSound(@NotNull final Sound sound) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().playSound(sound);
        }
    }
    
    default void playSound(@NotNull final Sound sound, final double x, final double y, final double z) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().playSound(sound, x, y, z);
        }
    }
    
    default void playSound(@NotNull final Sound sound, final Sound.Emitter emitter) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().playSound(sound, emitter);
        }
    }
    
    default void stopSound(@NotNull final SoundStop stop) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().stopSound(stop);
        }
    }
    
    default void openBook(@NotNull final Book book) {
        final Iterator<Audience> iterator = this.audiences().iterator();
        while (iterator.hasNext()) {
            iterator.next().openBook(book);
        }
    }
    
    public interface Single extends ForwardingAudience
    {
        @ApiStatus.OverrideOnly
        @NotNull
        Audience audience();
        
        @Deprecated
        @NotNull
        default Iterable audiences() {
            return Collections.singleton(this.audience());
        }
        
        @NotNull
        default Optional get(@NotNull final Pointer pointer) {
            return this.audience().get(pointer);
        }
        
        @Contract("_, null -> null; _, !null -> !null")
        @Nullable
        default Object getOrDefault(@NotNull final Pointer pointer, @Nullable final Object defaultValue) {
            return this.audience().getOrDefault(pointer, defaultValue);
        }
        
        default Object getOrDefaultFrom(@NotNull final Pointer pointer, @NotNull final Supplier defaultValue) {
            return this.audience().getOrDefaultFrom(pointer, defaultValue);
        }
        
        @NotNull
        default Audience filterAudience(@NotNull final Predicate filter) {
            return filter.test(this.audience()) ? this : Audience.empty();
        }
        
        default void forEachAudience(@NotNull final Consumer action) {
            this.audience().forEachAudience(action);
        }
        
        @NotNull
        default Pointers pointers() {
            return this.audience().pointers();
        }
        
        default void sendMessage(@NotNull final Identified source, @NotNull final Component message, @NotNull final MessageType type) {
            this.audience().sendMessage(source, message, type);
        }
        
        default void sendMessage(@NotNull final Identity source, @NotNull final Component message, @NotNull final MessageType type) {
            this.audience().sendMessage(source, message, type);
        }
        
        default void sendActionBar(@NotNull final Component message) {
            this.audience().sendActionBar(message);
        }
        
        default void sendPlayerListHeader(@NotNull final Component header) {
            this.audience().sendPlayerListHeader(header);
        }
        
        default void sendPlayerListFooter(@NotNull final Component footer) {
            this.audience().sendPlayerListFooter(footer);
        }
        
        default void sendPlayerListHeaderAndFooter(@NotNull final Component header, @NotNull final Component footer) {
            this.audience().sendPlayerListHeaderAndFooter(header, footer);
        }
        
        default void sendTitlePart(@NotNull final TitlePart part, @NotNull final Object value) {
            this.audience().sendTitlePart(part, value);
        }
        
        default void clearTitle() {
            this.audience().clearTitle();
        }
        
        default void resetTitle() {
            this.audience().resetTitle();
        }
        
        default void showBossBar(@NotNull final BossBar bar) {
            this.audience().showBossBar(bar);
        }
        
        default void hideBossBar(@NotNull final BossBar bar) {
            this.audience().hideBossBar(bar);
        }
        
        default void playSound(@NotNull final Sound sound) {
            this.audience().playSound(sound);
        }
        
        default void playSound(@NotNull final Sound sound, final double x, final double y, final double z) {
            this.audience().playSound(sound, x, y, z);
        }
        
        default void playSound(@NotNull final Sound sound, final Sound.Emitter emitter) {
            this.audience().playSound(sound, emitter);
        }
        
        default void stopSound(@NotNull final SoundStop stop) {
            this.audience().stopSound(stop);
        }
        
        default void openBook(@NotNull final Book book) {
            this.audience().openBook(book);
        }
    }
}
