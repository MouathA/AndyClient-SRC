package com.viaversion.viaversion.libs.kyori.adventure.sound;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

abstract class SoundImpl implements Sound
{
    static final Emitter EMITTER_SELF;
    private final Source source;
    private final float volume;
    private final float pitch;
    private SoundStop stop;
    
    SoundImpl(@NotNull final Source source, final float volume, final float pitch) {
        this.source = source;
        this.volume = volume;
        this.pitch = pitch;
    }
    
    @NotNull
    @Override
    public Source source() {
        return this.source;
    }
    
    @Override
    public float volume() {
        return this.volume;
    }
    
    @Override
    public float pitch() {
        return this.pitch;
    }
    
    @NotNull
    @Override
    public SoundStop asStop() {
        if (this.stop == null) {
            this.stop = SoundStop.namedOnSource(this.name(), this.source());
        }
        return this.stop;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundImpl)) {
            return false;
        }
        final SoundImpl soundImpl = (SoundImpl)other;
        return this.name().equals(soundImpl.name()) && this.source == soundImpl.source && ShadyPines.equals(this.volume, soundImpl.volume) && ShadyPines.equals(this.pitch, soundImpl.pitch);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * this.name().hashCode() + this.source.hashCode()) + Float.hashCode(this.volume)) + Float.hashCode(this.pitch);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("name", this.name()), ExaminableProperty.of("source", this.source), ExaminableProperty.of("volume", this.volume), ExaminableProperty.of("pitch", this.pitch) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    static {
        EMITTER_SELF = new Emitter() {
            @Override
            public String toString() {
                return "SelfSoundEmitter";
            }
        };
    }
}
