package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;

@ApiStatus.NonExtendable
public interface Sound extends Examinable
{
    @NotNull
    default Sound sound(@NotNull final Key name, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(source, "source");
        return new SoundImpl(volume, pitch) {
            final Key val$name;
            
            @NotNull
            @Override
            public Key name() {
                return this.val$name;
            }
        };
    }
    
    @NotNull
    default Sound sound(@NotNull final Type type, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(type, "type");
        return sound(type.key(), source, volume, pitch);
    }
    
    @NotNull
    default Sound sound(@NotNull final Supplier type, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(source, "source");
        return new SoundImpl(volume, pitch) {
            final Supplier val$type;
            
            @NotNull
            @Override
            public Key name() {
                return this.val$type.get().key();
            }
        };
    }
    
    @NotNull
    default Sound sound(@NotNull final Key name, final Source.Provider source, final float volume, final float pitch) {
        return sound(name, source.soundSource(), volume, pitch);
    }
    
    @NotNull
    default Sound sound(@NotNull final Type type, final Source.Provider source, final float volume, final float pitch) {
        return sound(type, source.soundSource(), volume, pitch);
    }
    
    @NotNull
    default Sound sound(@NotNull final Supplier type, final Source.Provider source, final float volume, final float pitch) {
        return sound(type, source.soundSource(), volume, pitch);
    }
    
    @NotNull
    Key name();
    
    @NotNull
    Source source();
    
    float volume();
    
    float pitch();
    
    @NotNull
    SoundStop asStop();
    
    public interface Emitter
    {
        @NotNull
        default Emitter self() {
            return SoundImpl.EMITTER_SELF;
        }
    }
    
    public interface Type extends Keyed
    {
        @NotNull
        Key key();
    }
    
    public enum Source
    {
        MASTER("master"), 
        MUSIC("music"), 
        RECORD("record"), 
        WEATHER("weather"), 
        BLOCK("block"), 
        HOSTILE("hostile"), 
        NEUTRAL("neutral"), 
        PLAYER("player"), 
        AMBIENT("ambient"), 
        VOICE("voice");
        
        public static final Index NAMES;
        private final String name;
        private static final Source[] $VALUES;
        
        private Source(final String name) {
            this.name = name;
        }
        
        private static String lambda$static$0(final Source source) {
            return source.name;
        }
        
        static {
            $VALUES = new Source[] { Source.MASTER, Source.MUSIC, Source.RECORD, Source.WEATHER, Source.BLOCK, Source.HOSTILE, Source.NEUTRAL, Source.PLAYER, Source.AMBIENT, Source.VOICE };
            NAMES = Index.create(Source.class, Source::lambda$static$0);
        }
        
        public interface Provider
        {
            @NotNull
            Source soundSource();
        }
    }
}
