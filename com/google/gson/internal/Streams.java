package com.google.gson.internal;

import com.google.gson.internal.bind.*;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.io.*;

public final class Streams
{
    public static JsonElement parse(final JsonReader jsonReader) throws JsonParseException {
        jsonReader.peek();
        return (JsonElement)TypeAdapters.JSON_ELEMENT.read(jsonReader);
    }
    
    public static void write(final JsonElement jsonElement, final JsonWriter jsonWriter) throws IOException {
        TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonElement);
    }
    
    public static Writer writerForAppendable(final Appendable appendable) {
        return (appendable instanceof Writer) ? ((Writer)appendable) : new AppendableWriter(appendable, null);
    }
    
    private static final class AppendableWriter extends Writer
    {
        private final Appendable appendable;
        private final CurrentWrite currentWrite;
        
        private AppendableWriter(final Appendable appendable) {
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
        
        AppendableWriter(final Appendable appendable, final Streams$1 object) {
            this(appendable);
        }
        
        static class CurrentWrite implements CharSequence
        {
            char[] chars;
            
            public int length() {
                return this.chars.length;
            }
            
            public char charAt(final int n) {
                return this.chars[n];
            }
            
            public CharSequence subSequence(final int n, final int n2) {
                return new String(this.chars, n, n2 - n);
            }
        }
    }
}
