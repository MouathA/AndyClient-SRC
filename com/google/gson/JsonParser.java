package com.google.gson;

import java.io.*;
import com.google.gson.stream.*;
import com.google.gson.internal.*;

public final class JsonParser
{
    public JsonElement parse(final String s) throws JsonSyntaxException {
        return this.parse(new StringReader(s));
    }
    
    public JsonElement parse(final Reader reader) throws JsonIOException, JsonSyntaxException {
        final JsonReader jsonReader = new JsonReader(reader);
        final JsonElement parse = this.parse(jsonReader);
        if (!parse.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
            throw new JsonSyntaxException("Did not consume the entire document.");
        }
        return parse;
    }
    
    public JsonElement parse(final JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        final boolean lenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        final JsonElement parse = Streams.parse(jsonReader);
        jsonReader.setLenient(lenient);
        return parse;
    }
}
