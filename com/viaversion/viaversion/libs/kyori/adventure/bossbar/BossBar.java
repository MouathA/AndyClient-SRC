package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;

@ApiStatus.NonExtendable
public interface BossBar extends Examinable
{
    public static final float MIN_PROGRESS = 0.0f;
    public static final float MAX_PROGRESS = 1.0f;
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static final float MIN_PERCENT = 0.0f;
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static final float MAX_PERCENT = 1.0f;
    
    @NotNull
    default BossBar bossBar(@NotNull final ComponentLike name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay) {
        BossBarImpl.checkProgress(progress);
        return bossBar(name.asComponent(), progress, color, overlay);
    }
    
    @NotNull
    default BossBar bossBar(@NotNull final Component name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay) {
        BossBarImpl.checkProgress(progress);
        return new BossBarImpl(name, progress, color, overlay);
    }
    
    @NotNull
    default BossBar bossBar(@NotNull final ComponentLike name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay, @NotNull final Set flags) {
        BossBarImpl.checkProgress(progress);
        return bossBar(name.asComponent(), progress, color, overlay, flags);
    }
    
    @NotNull
    default BossBar bossBar(@NotNull final Component name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay, @NotNull final Set flags) {
        BossBarImpl.checkProgress(progress);
        return new BossBarImpl(name, progress, color, overlay, flags);
    }
    
    @NotNull
    Component name();
    
    @Contract("_ -> this")
    @NotNull
    default BossBar name(@NotNull final ComponentLike name) {
        return this.name(name.asComponent());
    }
    
    @Contract("_ -> this")
    @NotNull
    BossBar name(@NotNull final Component name);
    
    float progress();
    
    @Contract("_ -> this")
    @NotNull
    BossBar progress(final float progress);
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    default float percent() {
        return this.progress();
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    @Contract("_ -> this")
    @NotNull
    default BossBar percent(final float progress) {
        return this.progress(progress);
    }
    
    @NotNull
    Color color();
    
    @Contract("_ -> this")
    @NotNull
    BossBar color(@NotNull final Color color);
    
    @NotNull
    Overlay overlay();
    
    @Contract("_ -> this")
    @NotNull
    BossBar overlay(@NotNull final Overlay overlay);
    
    @NotNull
    Set flags();
    
    @Contract("_ -> this")
    @NotNull
    BossBar flags(@NotNull final Set flags);
    
    boolean hasFlag(@NotNull final Flag flag);
    
    @Contract("_ -> this")
    @NotNull
    BossBar addFlag(@NotNull final Flag flag);
    
    @Contract("_ -> this")
    @NotNull
    BossBar removeFlag(@NotNull final Flag flag);
    
    @Contract("_ -> this")
    @NotNull
    BossBar addFlags(@NotNull final Flag... flags);
    
    @Contract("_ -> this")
    @NotNull
    BossBar removeFlags(@NotNull final Flag... flags);
    
    @Contract("_ -> this")
    @NotNull
    BossBar addFlags(@NotNull final Iterable flags);
    
    @Contract("_ -> this")
    @NotNull
    BossBar removeFlags(@NotNull final Iterable flags);
    
    @Contract("_ -> this")
    @NotNull
    BossBar addListener(@NotNull final Listener listener);
    
    @Contract("_ -> this")
    @NotNull
    BossBar removeListener(@NotNull final Listener listener);
    
    public enum Overlay
    {
        PROGRESS("progress"), 
        NOTCHED_6("notched_6"), 
        NOTCHED_10("notched_10"), 
        NOTCHED_12("notched_12"), 
        NOTCHED_20("notched_20");
        
        public static final Index NAMES;
        private final String name;
        private static final Overlay[] $VALUES;
        
        private Overlay(final String name) {
            this.name = name;
        }
        
        private static String lambda$static$0(final Overlay overlay) {
            return overlay.name;
        }
        
        static {
            $VALUES = new Overlay[] { Overlay.PROGRESS, Overlay.NOTCHED_6, Overlay.NOTCHED_10, Overlay.NOTCHED_12, Overlay.NOTCHED_20 };
            NAMES = Index.create(Overlay.class, Overlay::lambda$static$0);
        }
    }
    
    public enum Flag
    {
        DARKEN_SCREEN("darken_screen"), 
        PLAY_BOSS_MUSIC("play_boss_music"), 
        CREATE_WORLD_FOG("create_world_fog");
        
        public static final Index NAMES;
        private final String name;
        private static final Flag[] $VALUES;
        
        private Flag(final String name) {
            this.name = name;
        }
        
        private static String lambda$static$0(final Flag flag) {
            return flag.name;
        }
        
        static {
            $VALUES = new Flag[] { Flag.DARKEN_SCREEN, Flag.PLAY_BOSS_MUSIC, Flag.CREATE_WORLD_FOG };
            NAMES = Index.create(Flag.class, Flag::lambda$static$0);
        }
    }
    
    public enum Color
    {
        PINK("pink"), 
        BLUE("blue"), 
        RED("red"), 
        GREEN("green"), 
        YELLOW("yellow"), 
        PURPLE("purple"), 
        WHITE("white");
        
        public static final Index NAMES;
        private final String name;
        private static final Color[] $VALUES;
        
        private Color(final String name) {
            this.name = name;
        }
        
        private static String lambda$static$0(final Color color) {
            return color.name;
        }
        
        static {
            $VALUES = new Color[] { Color.PINK, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.WHITE };
            NAMES = Index.create(Color.class, Color::lambda$static$0);
        }
    }
    
    @ApiStatus.OverrideOnly
    public interface Listener
    {
        default void bossBarNameChanged(@NotNull final BossBar bar, @NotNull final Component oldName, @NotNull final Component newName) {
        }
        
        default void bossBarProgressChanged(@NotNull final BossBar bar, final float oldProgress, final float newProgress) {
            this.bossBarPercentChanged(bar, oldProgress, newProgress);
        }
        
        @Deprecated
        @ApiStatus.ScheduledForRemoval
        default void bossBarPercentChanged(@NotNull final BossBar bar, final float oldProgress, final float newProgress) {
        }
        
        default void bossBarColorChanged(@NotNull final BossBar bar, @NotNull final Color oldColor, @NotNull final Color newColor) {
        }
        
        default void bossBarOverlayChanged(@NotNull final BossBar bar, @NotNull final Overlay oldOverlay, @NotNull final Overlay newOverlay) {
        }
        
        default void bossBarFlagsChanged(@NotNull final BossBar bar, @NotNull final Set flagsAdded, @NotNull final Set flagsRemoved) {
        }
    }
}
