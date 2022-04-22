package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.pointer.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.identity.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.title.*;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.sound.*;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.*;

public interface Audience extends Pointered
{
    @NotNull
    default Audience empty() {
        return EmptyAudience.INSTANCE;
    }
    
    @NotNull
    default Audience audience(@NotNull final Audience... audiences) {
        final int length = audiences.length;
        if (length == 0) {
            return empty();
        }
        if (length == 1) {
            return audiences[0];
        }
        return audience(Arrays.asList(audiences));
    }
    
    @NotNull
    default ForwardingAudience audience(@NotNull final Iterable audiences) {
        return Audience::lambda$audience$0;
    }
    
    @NotNull
    default Collector toAudience() {
        return Audiences.COLLECTOR;
    }
    
    @NotNull
    default Audience filterAudience(@NotNull final Predicate filter) {
        return filter.test(this) ? this : empty();
    }
    
    default void forEachAudience(@NotNull final Consumer action) {
        action.accept(this);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final ComponentLike message) {
        this.sendMessage(Identity.nil(), message);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message) {
        this.sendMessage(source, message.asComponent());
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message) {
        this.sendMessage(source, message.asComponent());
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Component message) {
        this.sendMessage(Identity.nil(), message);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identified source, @NotNull final Component message) {
        this.sendMessage(source, message, MessageType.SYSTEM);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identity source, @NotNull final Component message) {
        this.sendMessage(source, message, MessageType.SYSTEM);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final ComponentLike message, @NotNull final MessageType type) {
        this.sendMessage(Identity.nil(), message, type);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
        this.sendMessage(source, message.asComponent(), type);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
        this.sendMessage(source, message.asComponent(), type);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Component message, @NotNull final MessageType type) {
        this.sendMessage(Identity.nil(), message, type);
    }
    
    default void sendMessage(@NotNull final Identified source, @NotNull final Component message, @NotNull final MessageType type) {
        this.sendMessage(source.identity(), message, type);
    }
    
    default void sendMessage(@NotNull final Identity source, @NotNull final Component message, @NotNull final MessageType type) {
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendActionBar(@NotNull final ComponentLike message) {
        this.sendActionBar(message.asComponent());
    }
    
    default void sendActionBar(@NotNull final Component message) {
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListHeader(@NotNull final ComponentLike header) {
        this.sendPlayerListHeader(header.asComponent());
    }
    
    default void sendPlayerListHeader(@NotNull final Component header) {
        this.sendPlayerListHeaderAndFooter(header, Component.empty());
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListFooter(@NotNull final ComponentLike footer) {
        this.sendPlayerListFooter(footer.asComponent());
    }
    
    default void sendPlayerListFooter(@NotNull final Component footer) {
        this.sendPlayerListHeaderAndFooter(Component.empty(), footer);
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListHeaderAndFooter(@NotNull final ComponentLike header, @NotNull final ComponentLike footer) {
        this.sendPlayerListHeaderAndFooter(header.asComponent(), footer.asComponent());
    }
    
    default void sendPlayerListHeaderAndFooter(@NotNull final Component header, @NotNull final Component footer) {
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void showTitle(@NotNull final Title title) {
        final Title.Times times = title.times();
        if (times != null) {
            this.sendTitlePart(TitlePart.TIMES, times);
        }
        this.sendTitlePart(TitlePart.SUBTITLE, title.subtitle());
        this.sendTitlePart(TitlePart.TITLE, title.title());
    }
    
    default void sendTitlePart(@NotNull final TitlePart part, @NotNull final Object value) {
    }
    
    default void clearTitle() {
    }
    
    default void resetTitle() {
    }
    
    default void showBossBar(@NotNull final BossBar bar) {
    }
    
    default void hideBossBar(@NotNull final BossBar bar) {
    }
    
    default void playSound(@NotNull final Sound sound) {
    }
    
    default void playSound(@NotNull final Sound sound, final double x, final double y, final double z) {
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void stopSound(@NotNull final Sound sound) {
        this.stopSound(Objects.requireNonNull(sound, "sound").asStop());
    }
    
    default void playSound(@NotNull final Sound sound, final Sound.Emitter emitter) {
    }
    
    default void stopSound(@NotNull final SoundStop stop) {
    }
    
    @ForwardingAudienceOverrideNotRequired
    default void openBook(final Book.Builder book) {
        this.openBook(book.build());
    }
    
    default void openBook(@NotNull final Book book) {
    }
    
    default Iterable lambda$audience$0(final Iterable iterable) {
        return iterable;
    }
}
