package com.viaversion.viaversion.libs.kyori.adventure.translation;

import java.util.regex.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.text.*;
import org.jetbrains.annotations.*;
import java.util.function.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public interface TranslationRegistry extends Translator
{
    public static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");
    
    @NotNull
    default TranslationRegistry create(final Key name) {
        return new TranslationRegistryImpl(Objects.requireNonNull(name, "name"));
    }
    
    boolean contains(@NotNull final String key);
    
    @Nullable
    MessageFormat translate(@NotNull final String key, @NotNull final Locale locale);
    
    void defaultLocale(@NotNull final Locale locale);
    
    void register(@NotNull final String key, @NotNull final Locale locale, @NotNull final MessageFormat format);
    
    default void registerAll(@NotNull final Locale locale, @NotNull final Map formats) {
        final Set<K> keySet = formats.keySet();
        Objects.requireNonNull(formats);
        this.registerAll(locale, keySet, formats::get);
    }
    
    default void registerAll(@NotNull final Locale locale, @NotNull final Path path, final boolean escapeSingleQuotes) {
        final BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        this.registerAll(locale, new PropertyResourceBundle(bufferedReader), escapeSingleQuotes);
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }
    
    default void registerAll(@NotNull final Locale locale, @NotNull final ResourceBundle bundle, final boolean escapeSingleQuotes) {
        this.registerAll(locale, bundle.keySet(), TranslationRegistry::lambda$registerAll$0);
    }
    
    default void registerAll(@NotNull final Locale locale, @NotNull final Set keys, final Function function) {
        final List<Throwable> list = null;
        for (final String key : keys) {
            this.register(key, locale, function.apply(key));
        }
        if (list != null) {
            final int size = list.size();
            if (size == 1) {
                throw (IllegalArgumentException)list.get(0);
            }
            if (size > 1) {
                throw new IllegalArgumentException(String.format("Invalid key (and %d more)", size - 1), list.get(0));
            }
        }
    }
    
    void unregister(@NotNull final String key);
    
    default MessageFormat lambda$registerAll$0(final ResourceBundle resourceBundle, final boolean b, final Locale locale, final String s) {
        final String string = resourceBundle.getString(s);
        return new MessageFormat(b ? TranslationRegistry.SINGLE_QUOTE_PATTERN.matcher(string).replaceAll("''") : string, locale);
    }
}
