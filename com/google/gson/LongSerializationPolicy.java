package com.google.gson;

public enum LongSerializationPolicy
{
    DEFAULT {
        @Override
        public JsonElement serialize(final Long n) {
            return new JsonPrimitive(n);
        }
    }, 
    STRING {
        @Override
        public JsonElement serialize(final Long n) {
            return new JsonPrimitive(String.valueOf(n));
        }
    };
    
    private static final LongSerializationPolicy[] $VALUES;
    
    private LongSerializationPolicy(final String s, final int n) {
    }
    
    public abstract JsonElement serialize(final Long p0);
    
    LongSerializationPolicy(final String s, final int n, final LongSerializationPolicy$1 longSerializationPolicy) {
        this(s, n);
    }
    
    static {
        $VALUES = new LongSerializationPolicy[] { LongSerializationPolicy.DEFAULT, LongSerializationPolicy.STRING };
    }
}
