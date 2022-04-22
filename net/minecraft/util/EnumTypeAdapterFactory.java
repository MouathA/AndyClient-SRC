package net.minecraft.util;

import com.google.gson.reflect.*;
import com.google.gson.*;
import com.google.common.collect.*;
import java.io.*;
import com.google.gson.stream.*;
import java.util.*;

public class EnumTypeAdapterFactory implements TypeAdapterFactory
{
    private static final String __OBFID;
    
    @Override
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Class rawType = typeToken.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        final HashMap hashMap = Maps.newHashMap();
        final Object[] enumConstants = rawType.getEnumConstants();
        while (0 < enumConstants.length) {
            final Object o = enumConstants[0];
            hashMap.put(this.func_151232_a(o), o);
            int n = 0;
            ++n;
        }
        return new TypeAdapter(hashMap) {
            private static final String __OBFID;
            final EnumTypeAdapterFactory this$0;
            private final HashMap val$var4;
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                if (o == null) {
                    jsonWriter.nullValue();
                }
                else {
                    jsonWriter.value(EnumTypeAdapterFactory.access$0(this.this$0, o));
                }
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return this.val$var4.get(jsonReader.nextString());
            }
            
            static {
                __OBFID = "CL_00001495";
            }
        };
    }
    
    private String func_151232_a(final Object o) {
        return (o instanceof Enum) ? ((Enum)o).name().toLowerCase(Locale.US) : o.toString().toLowerCase(Locale.US);
    }
    
    static String access$0(final EnumTypeAdapterFactory enumTypeAdapterFactory, final Object o) {
        return enumTypeAdapterFactory.func_151232_a(o);
    }
    
    static {
        __OBFID = "CL_00001494";
    }
}
