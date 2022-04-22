package net.minecraft.util;

import com.google.common.collect.*;
import com.google.gson.*;
import java.util.*;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable
{
    private final Set underlyingSet;
    private static final String __OBFID;
    
    public JsonSerializableSet() {
        this.underlyingSet = Sets.newHashSet();
    }
    
    @Override
    public void func_152753_a(final JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            final Iterator iterator = jsonElement.getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                this.add(iterator.next().getAsString());
            }
        }
    }
    
    @Override
    public JsonElement getSerializableElement() {
        final JsonArray jsonArray = new JsonArray();
        final Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            jsonArray.add(new JsonPrimitive(iterator.next()));
        }
        return jsonArray;
    }
    
    @Override
    protected Set delegate() {
        return this.underlyingSet;
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Override
    protected Collection delegate() {
        return this.delegate();
    }
    
    static {
        __OBFID = "CL_00001482";
    }
}
