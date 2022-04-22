package com.viaversion.viaversion.libs.kyori.adventure.sound;

import java.util.*;
import java.util.stream.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;

abstract class SoundStopImpl implements SoundStop
{
    static final SoundStop ALL;
    private final Sound.Source source;
    
    SoundStopImpl(final Sound.Source source) {
        this.source = source;
    }
    
    @Override
    public Sound.Source source() {
        return this.source;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundStopImpl)) {
            return false;
        }
        final SoundStopImpl soundStopImpl = (SoundStopImpl)other;
        return Objects.equals(this.sound(), soundStopImpl.sound()) && Objects.equals(this.source, soundStopImpl.source);
    }
    
    @Override
    public int hashCode() {
        return 31 * Objects.hashCode(this.sound()) + Objects.hashCode(this.source);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("name", this.sound()), ExaminableProperty.of("source", this.source) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    static {
        ALL = new SoundStopImpl() {
            @Nullable
            @Override
            public Key sound() {
                return null;
            }
        };
    }
}
