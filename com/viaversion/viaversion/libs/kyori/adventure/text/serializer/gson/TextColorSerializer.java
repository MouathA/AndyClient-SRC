package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import org.jetbrains.annotations.*;

final class TextColorSerializer extends TypeAdapter
{
    static final TypeAdapter INSTANCE;
    static final TypeAdapter DOWNSAMPLE_COLOR;
    private final boolean downsampleColor;
    
    private TextColorSerializer(final boolean downsampleColor) {
        this.downsampleColor = downsampleColor;
    }
    
    public void write(final JsonWriter out, final TextColor value) throws IOException {
        if (value instanceof NamedTextColor) {
            out.value((String)NamedTextColor.NAMES.key(value));
        }
        else if (this.downsampleColor) {
            out.value((String)NamedTextColor.NAMES.key(NamedTextColor.nearestTo(value)));
        }
        else {
            out.value(value.asHexString());
        }
    }
    
    @Nullable
    @Override
    public TextColor read(final JsonReader in) throws IOException {
        final TextColor fromString = fromString(in.nextString());
        if (fromString == null) {
            return null;
        }
        return this.downsampleColor ? NamedTextColor.nearestTo(fromString) : fromString;
    }
    
    @Nullable
    static TextColor fromString(@NotNull final String value) {
        if (value.startsWith("#")) {
            return TextColor.fromHexString(value);
        }
        return (TextColor)NamedTextColor.NAMES.value(value);
    }
    
    @Nullable
    @Override
    public Object read(final JsonReader in) throws IOException {
        return this.read(in);
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        this.write(out, (TextColor)value);
    }
    
    static {
        INSTANCE = new TextColorSerializer(false).nullSafe();
        DOWNSAMPLE_COLOR = new TextColorSerializer(true).nullSafe();
    }
}
