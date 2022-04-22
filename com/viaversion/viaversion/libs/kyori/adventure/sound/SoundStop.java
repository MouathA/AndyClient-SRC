package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.util.*;
import java.util.function.*;
import org.jetbrains.annotations.*;

@ApiStatus.NonExtendable
public interface SoundStop extends Examinable
{
    @NotNull
    default SoundStop all() {
        return SoundStopImpl.ALL;
    }
    
    @NotNull
    default SoundStop named(@NotNull final Key sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl() {
            final Key val$sound;
            
            @NotNull
            @Override
            public Key sound() {
                return this.val$sound;
            }
        };
    }
    
    @NotNull
    default SoundStop named(final Sound.Type sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl() {
            final Sound.Type val$sound;
            
            @NotNull
            @Override
            public Key sound() {
                return this.val$sound.key();
            }
        };
    }
    
    @NotNull
    default SoundStop named(@NotNull final Supplier sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl() {
            final Supplier val$sound;
            
            @NotNull
            @Override
            public Key sound() {
                return this.val$sound.get().key();
            }
        };
    }
    
    @NotNull
    default SoundStop source(final Sound.Source source) {
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl() {
            @Nullable
            @Override
            public Key sound() {
                return null;
            }
        };
    }
    
    @NotNull
    default SoundStop namedOnSource(@NotNull final Key sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl() {
            final Key val$sound;
            
            @NotNull
            @Override
            public Key sound() {
                return this.val$sound;
            }
        };
    }
    
    @NotNull
    default SoundStop namedOnSource(final Sound.Type sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        return namedOnSource(sound.key(), source);
    }
    
    @NotNull
    default SoundStop namedOnSource(@NotNull final Supplier sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl() {
            final Supplier val$sound;
            
            @NotNull
            @Override
            public Key sound() {
                return this.val$sound.get().key();
            }
        };
    }
    
    @Nullable
    Key sound();
    
    Sound.Source source();
}
