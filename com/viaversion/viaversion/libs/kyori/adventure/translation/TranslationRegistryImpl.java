package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.util.concurrent.*;
import java.text.*;
import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

final class TranslationRegistryImpl implements Examinable, TranslationRegistry
{
    private final Key name;
    private final Map translations;
    private Locale defaultLocale;
    
    TranslationRegistryImpl(final Key name) {
        this.translations = new ConcurrentHashMap();
        this.defaultLocale = Locale.US;
        this.name = name;
    }
    
    @Override
    public void register(@NotNull final String key, @NotNull final Locale locale, @NotNull final MessageFormat format) {
        this.translations.computeIfAbsent(key, this::lambda$register$0).register(locale, format);
    }
    
    @Override
    public void unregister(@NotNull final String key) {
        this.translations.remove(key);
    }
    
    @NotNull
    @Override
    public Key name() {
        return this.name;
    }
    
    @Override
    public boolean contains(@NotNull final String key) {
        return this.translations.containsKey(key);
    }
    
    @Nullable
    @Override
    public MessageFormat translate(@NotNull final String key, @NotNull final Locale locale) {
        final Translation translation = this.translations.get(key);
        if (translation == null) {
            return null;
        }
        return translation.translate(locale);
    }
    
    @Override
    public void defaultLocale(@NotNull final Locale defaultLocale) {
        this.defaultLocale = Objects.requireNonNull(defaultLocale, "defaultLocale");
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("translations", this.translations));
    }
    
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslationRegistryImpl)) {
            return false;
        }
        final TranslationRegistryImpl translationRegistryImpl = (TranslationRegistryImpl)other;
        return this.name.equals(translationRegistryImpl.name) && this.translations.equals(translationRegistryImpl.translations) && this.defaultLocale.equals(translationRegistryImpl.defaultLocale);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.translations, this.defaultLocale);
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    private Translation lambda$register$0(final String key) {
        return new Translation(key);
    }
    
    static Locale access$000(final TranslationRegistryImpl translationRegistryImpl) {
        return translationRegistryImpl.defaultLocale;
    }
    
    final class Translation implements Examinable
    {
        private final String key;
        private final Map formats;
        final TranslationRegistryImpl this$0;
        
        Translation(@NotNull final TranslationRegistryImpl this$0, final String key) {
            this.this$0 = this$0;
            this.key = Objects.requireNonNull(key, "translation key");
            this.formats = new ConcurrentHashMap();
        }
        
        void register(@NotNull final Locale locale, @NotNull final MessageFormat format) {
            if (this.formats.putIfAbsent(Objects.requireNonNull(locale, "locale"), Objects.requireNonNull(format, "message format")) != null) {
                throw new IllegalArgumentException(String.format("Translation already exists: %s for %s", this.key, locale));
            }
        }
        
        @Nullable
        MessageFormat translate(@NotNull final Locale locale) {
            MessageFormat messageFormat = this.formats.get(Objects.requireNonNull(locale, "locale"));
            if (messageFormat == null) {
                messageFormat = this.formats.get(new Locale(locale.getLanguage()));
                if (messageFormat == null) {
                    messageFormat = this.formats.get(TranslationRegistryImpl.access$000(this.this$0));
                    if (messageFormat == null) {
                        messageFormat = this.formats.get(TranslationLocales.global());
                    }
                }
            }
            return messageFormat;
        }
        
        @NotNull
        @Override
        public Stream examinableProperties() {
            return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("key", this.key), ExaminableProperty.of("formats", this.formats) });
        }
        
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Translation)) {
                return false;
            }
            final Translation translation = (Translation)other;
            return this.key.equals(translation.key) && this.formats.equals(translation.formats);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.key, this.formats);
        }
        
        @Override
        public String toString() {
            return (String)this.examine(StringExaminer.simpleEscaping());
        }
    }
}
