package com.viaversion.viaversion.libs.kyori.adventure.title;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.time.*;

final class TitleImpl implements Title
{
    private final Component title;
    private final Component subtitle;
    @Nullable
    private final Times times;
    
    TitleImpl(@NotNull final Component title, @NotNull final Component subtitle, @Nullable final Times times) {
        this.title = title;
        this.subtitle = subtitle;
        this.times = times;
    }
    
    @NotNull
    @Override
    public Component title() {
        return this.title;
    }
    
    @NotNull
    @Override
    public Component subtitle() {
        return this.subtitle;
    }
    
    @Nullable
    @Override
    public Times times() {
        return this.times;
    }
    
    @Override
    public Object part(@NotNull final TitlePart part) {
        if (part == TitlePart.TITLE) {
            return this.title;
        }
        if (part == TitlePart.SUBTITLE) {
            return this.subtitle;
        }
        if (part == TitlePart.TIMES) {
            return this.times;
        }
        throw new IllegalArgumentException("Don't know what " + part + " is.");
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final TitleImpl titleImpl = (TitleImpl)other;
        return this.title.equals(titleImpl.title) && this.subtitle.equals(titleImpl.subtitle) && Objects.equals(this.times, titleImpl.times);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.title.hashCode() + this.subtitle.hashCode()) + Objects.hashCode(this.times);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("title", this.title), ExaminableProperty.of("subtitle", this.subtitle), ExaminableProperty.of("times", this.times) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    static class TimesImpl implements Times
    {
        private final Duration fadeIn;
        private final Duration stay;
        private final Duration fadeOut;
        
        TimesImpl(final Duration fadeIn, final Duration stay, final Duration fadeOut) {
            this.fadeIn = fadeIn;
            this.stay = stay;
            this.fadeOut = fadeOut;
        }
        
        @NotNull
        @Override
        public Duration fadeIn() {
            return this.fadeIn;
        }
        
        @NotNull
        @Override
        public Duration stay() {
            return this.stay;
        }
        
        @NotNull
        @Override
        public Duration fadeOut() {
            return this.fadeOut;
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || this.getClass() != other.getClass()) {
                return false;
            }
            final TimesImpl timesImpl = (TimesImpl)other;
            return this.fadeIn.equals(timesImpl.fadeIn) && this.stay.equals(timesImpl.stay) && this.fadeOut.equals(timesImpl.fadeOut);
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * this.fadeIn.hashCode() + this.stay.hashCode()) + this.fadeOut.hashCode();
        }
        
        @NotNull
        @Override
        public Stream examinableProperties() {
            return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("fadeIn", this.fadeIn), ExaminableProperty.of("stay", this.stay), ExaminableProperty.of("fadeOut", this.fadeOut) });
        }
        
        @Override
        public String toString() {
            return (String)this.examine(StringExaminer.simpleEscaping());
        }
    }
}
