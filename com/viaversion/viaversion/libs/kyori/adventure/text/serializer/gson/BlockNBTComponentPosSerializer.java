package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;

final class BlockNBTComponentPosSerializer extends TypeAdapter
{
    static final TypeAdapter INSTANCE;
    
    private BlockNBTComponentPosSerializer() {
    }
    
    @Override
    public BlockNBTComponent.Pos read(final JsonReader in) throws IOException {
        return BlockNBTComponent.Pos.fromString(in.nextString());
    }
    
    public void write(final JsonWriter out, final BlockNBTComponent.Pos value) throws IOException {
        out.value(value.asString());
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        return this.read(in);
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        this.write(out, (BlockNBTComponent.Pos)value);
    }
    
    static {
        INSTANCE = new BlockNBTComponentPosSerializer().nullSafe();
    }
}
