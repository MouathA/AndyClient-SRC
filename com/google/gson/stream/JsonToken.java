package com.google.gson.stream;

public enum JsonToken
{
    BEGIN_ARRAY("BEGIN_ARRAY", 0), 
    END_ARRAY("END_ARRAY", 1), 
    BEGIN_OBJECT("BEGIN_OBJECT", 2), 
    END_OBJECT("END_OBJECT", 3), 
    NAME("NAME", 4), 
    STRING("STRING", 5), 
    NUMBER("NUMBER", 6), 
    BOOLEAN("BOOLEAN", 7), 
    NULL("NULL", 8), 
    END_DOCUMENT("END_DOCUMENT", 9);
    
    private static final JsonToken[] $VALUES;
    
    private JsonToken(final String s, final int n) {
    }
    
    static {
        $VALUES = new JsonToken[] { JsonToken.BEGIN_ARRAY, JsonToken.END_ARRAY, JsonToken.BEGIN_OBJECT, JsonToken.END_OBJECT, JsonToken.NAME, JsonToken.STRING, JsonToken.NUMBER, JsonToken.BOOLEAN, JsonToken.NULL, JsonToken.END_DOCUMENT };
    }
}
