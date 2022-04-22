package com.viaversion.viaversion.libs.gson;

import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.internal.*;

public final class JsonParser
{
    @Deprecated
    public JsonParser() {
    }
    
    public static JsonElement parseString(final String s) throws JsonSyntaxException {
        return parseReader(new StringReader(s));
    }
    
    public static JsonElement parseReader(final Reader reader) throws JsonIOException, JsonSyntaxException {
        final JsonReader jsonReader = new JsonReader(reader);
        final JsonElement reader2 = parseReader(jsonReader);
        if (!reader2.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
            throw new JsonSyntaxException("Did not consume the entire document.");
        }
        return reader2;
    }
    
    public static JsonElement parseReader(final JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        final boolean lenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        final JsonElement parse = Streams.parse(jsonReader);
        jsonReader.setLenient(lenient);
        return parse;
    }
    
    @Deprecated
    public JsonElement parse(final String s) throws JsonSyntaxException {
        return parseString(s);
    }
    
    @Deprecated
    public JsonElement parse(final Reader reader) throws JsonIOException, JsonSyntaxException {
        return parseReader(reader);
    }
    
    @Deprecated
    public JsonElement parse(final JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        return parseReader(jsonReader);
    }
}
