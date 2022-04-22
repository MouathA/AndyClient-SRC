package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.internal.bind.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.io.*;

public final class Streams
{
    private Streams() {
        throw new UnsupportedOperationException();
    }
    
    public static JsonElement parse(final JsonReader jsonReader) throws JsonParseException {
        jsonReader.peek();
        return (JsonElement)TypeAdapters.JSON_ELEMENT.read(jsonReader);
    }
    
    public static void write(final JsonElement jsonElement, final JsonWriter jsonWriter) throws IOException {
        TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonElement);
    }
    
    public static Writer writerForAppendable(final Appendable appendable) {
        return (appendable instanceof Writer) ? ((Writer)appendable) : new AppendableWriter(appendable);
    }
    
    private static final class AppendableWriter extends Writer
    {
        private final Appendable appendable;
        private final CurrentWrite currentWrite;
        
        AppendableWriter(final Appendable appendable) {
            this.currentWrite = new CurrentWrite();
            this.appendable = appendable;
        }
        
        @Override
        public void write(final char[] chars, final int n, final int n2) throws IOException {
            this.currentWrite.chars = chars;
            this.appendable.append(this.currentWrite, n, n + n2);
        }
        
        @Override
        public void write(final int n) throws IOException {
            this.appendable.append((char)n);
        }
        
        @Override
        public void flush() {
        }
        
        @Override
        public void close() {
        }
        
        static class CurrentWrite implements CharSequence
        {
            char[] chars;
            
            @Override
            public int length() {
                return this.chars.length;
            }
            
            @Override
            public char charAt(final int n) {
                return this.chars[n];
            }
            
            @Override
            public CharSequence subSequence(final int n, final int n2) {
                return new String(this.chars, n, n2 - n);
            }
        }
    }
}
