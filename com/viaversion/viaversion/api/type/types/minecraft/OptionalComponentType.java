package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import io.netty.buffer.*;

public class OptionalComponentType extends Type
{
    public OptionalComponentType() {
        super(JsonElement.class);
    }
    
    @Override
    public JsonElement read(final ByteBuf byteBuf) throws Exception {
        return byteBuf.readBoolean() ? ((JsonElement)Type.COMPONENT.read(byteBuf)) : null;
    }
    
    public void write(final ByteBuf byteBuf, final JsonElement jsonElement) throws Exception {
        if (jsonElement == null) {
            byteBuf.writeBoolean(false);
        }
        else {
            byteBuf.writeBoolean(true);
            Type.COMPONENT.write(byteBuf, jsonElement);
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (JsonElement)o);
    }
}
