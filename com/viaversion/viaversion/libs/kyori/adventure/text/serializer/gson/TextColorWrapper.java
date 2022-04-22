package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.*;
import java.io.*;

final class TextColorWrapper
{
    @Nullable
    final TextColor color;
    @Nullable
    final TextDecoration decoration;
    final boolean reset;
    
    TextColorWrapper(@Nullable final TextColor color, @Nullable final TextDecoration decoration, final boolean reset) {
        this.color = color;
        this.decoration = decoration;
        this.reset = reset;
    }
    
    static final class Serializer extends TypeAdapter
    {
        static final Serializer INSTANCE;
        
        private Serializer() {
        }
        
        public void write(final JsonWriter out, final TextColorWrapper value) {
            throw new JsonSyntaxException("Cannot write TextColorWrapper instances");
        }
        
        @Override
        public TextColorWrapper read(final JsonReader in) throws IOException {
            final String nextString = in.nextString();
            final TextColor fromString = TextColorSerializer.fromString(nextString);
            final TextDecoration decoration = (TextDecoration)TextDecoration.NAMES.value(nextString);
            final boolean reset = decoration == null && nextString.equals("reset");
            if (fromString == null && decoration == null && !reset) {
                throw new JsonParseException("Don't know how to parse " + nextString + " at " + in.getPath());
            }
            return new TextColorWrapper(fromString, decoration, reset);
        }
        
        @Override
        public Object read(final JsonReader in) throws IOException {
            return this.read(in);
        }
        
        @Override
        public void write(final JsonWriter out, final Object value) throws IOException {
            this.write(out, (TextColorWrapper)value);
        }
        
        static {
            INSTANCE = new Serializer();
        }
    }
}
