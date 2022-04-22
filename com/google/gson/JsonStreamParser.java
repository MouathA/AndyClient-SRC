package com.google.gson;

import java.io.*;
import java.util.*;
import com.google.gson.internal.*;
import com.google.gson.stream.*;

public final class JsonStreamParser implements Iterator
{
    private final JsonReader parser;
    private final Object lock;
    
    public JsonStreamParser(final String s) {
        this(new StringReader(s));
    }
    
    public JsonStreamParser(final Reader reader) {
        (this.parser = new JsonReader(reader)).setLenient(true);
        this.lock = new Object();
    }
    
    public JsonElement next() throws JsonParseException {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return Streams.parse(this.parser);
    }
    
    public boolean hasNext() {
        // monitorenter(lock = this.lock)
        // monitorexit(lock)
        return this.parser.peek() != JsonToken.END_DOCUMENT;
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    public Object next() {
        return this.next();
    }
}
