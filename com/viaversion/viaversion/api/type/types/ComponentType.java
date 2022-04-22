package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.libs.gson.*;

public class ComponentType extends Type
{
    private static final StringType STRING_TAG;
    
    public ComponentType() {
        super(JsonElement.class);
    }
    
    @Override
    public JsonElement read(final ByteBuf byteBuf) throws Exception {
        return JsonParser.parseString(ComponentType.STRING_TAG.read(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final JsonElement jsonElement) throws Exception {
        ComponentType.STRING_TAG.write(byteBuf, jsonElement.toString());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (JsonElement)o);
    }
    
    static {
        STRING_TAG = new StringType(262144);
    }
}
