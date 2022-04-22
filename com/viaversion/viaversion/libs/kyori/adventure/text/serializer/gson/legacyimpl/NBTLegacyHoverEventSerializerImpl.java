package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.*;
import java.io.*;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.*;
import java.util.*;

final class NBTLegacyHoverEventSerializerImpl implements LegacyHoverEventSerializer
{
    static final NBTLegacyHoverEventSerializerImpl INSTANCE;
    private static final TagStringIO SNBT_IO;
    private static final Codec SNBT_CODEC;
    static final String ITEM_TYPE = "id";
    static final String ITEM_COUNT = "Count";
    static final String ITEM_TAG = "tag";
    static final String ENTITY_NAME = "name";
    static final String ENTITY_TYPE = "type";
    static final String ENTITY_ID = "id";
    
    private NBTLegacyHoverEventSerializerImpl() {
    }
    
    @Override
    public HoverEvent.ShowItem deserializeShowItem(@NotNull final Component input) throws IOException {
        assertTextComponent(input);
        final CompoundBinaryTag compoundBinaryTag = (CompoundBinaryTag)NBTLegacyHoverEventSerializerImpl.SNBT_CODEC.decode(((TextComponent)input).content());
        final CompoundBinaryTag compound = compoundBinaryTag.getCompound("tag");
        return HoverEvent.ShowItem.of(Key.key(compoundBinaryTag.getString("id")), compoundBinaryTag.getByte("Count", (byte)1), (compound == CompoundBinaryTag.empty()) ? null : BinaryTagHolder.encode(compound, NBTLegacyHoverEventSerializerImpl.SNBT_CODEC));
    }
    
    @Override
    public HoverEvent.ShowEntity deserializeShowEntity(@NotNull final Component input, final Codec.Decoder componentCodec) throws IOException {
        assertTextComponent(input);
        final CompoundBinaryTag compoundBinaryTag = (CompoundBinaryTag)NBTLegacyHoverEventSerializerImpl.SNBT_CODEC.decode(((TextComponent)input).content());
        return HoverEvent.ShowEntity.of(Key.key(compoundBinaryTag.getString("type")), UUID.fromString(compoundBinaryTag.getString("id")), (Component)componentCodec.decode(compoundBinaryTag.getString("name")));
    }
    
    private static void assertTextComponent(final Component component) {
        if (!(component instanceof TextComponent) || !component.children().isEmpty()) {
            throw new IllegalArgumentException("Legacy events must be single Component instances");
        }
    }
    
    @NotNull
    @Override
    public Component serializeShowItem(final HoverEvent.ShowItem input) throws IOException {
        final CompoundBinaryTag.Builder builder = (CompoundBinaryTag.Builder)((CompoundBinaryTag.Builder)CompoundBinaryTag.builder().putString("id", input.item().asString())).putByte("Count", (byte)input.count());
        final BinaryTagHolder nbt = input.nbt();
        if (nbt != null) {
            builder.put("tag", (BinaryTag)nbt.get(NBTLegacyHoverEventSerializerImpl.SNBT_CODEC));
        }
        return Component.text((String)NBTLegacyHoverEventSerializerImpl.SNBT_CODEC.encode(builder.build()));
    }
    
    @NotNull
    @Override
    public Component serializeShowEntity(final HoverEvent.ShowEntity input, final Codec.Encoder componentCodec) throws IOException {
        final CompoundBinaryTag.Builder builder = (CompoundBinaryTag.Builder)((CompoundBinaryTag.Builder)CompoundBinaryTag.builder().putString("id", input.id().toString())).putString("type", input.type().asString());
        final Component name = input.name();
        if (name != null) {
            builder.putString("name", (String)componentCodec.encode(name));
        }
        return Component.text((String)NBTLegacyHoverEventSerializerImpl.SNBT_CODEC.encode(builder.build()));
    }
    
    static {
        INSTANCE = new NBTLegacyHoverEventSerializerImpl();
        SNBT_IO = TagStringIO.get();
        final TagStringIO snbt_IO = NBTLegacyHoverEventSerializerImpl.SNBT_IO;
        Objects.requireNonNull(snbt_IO);
        final Codec.Decoder decoder = snbt_IO::asCompound;
        final TagStringIO snbt_IO2 = NBTLegacyHoverEventSerializerImpl.SNBT_IO;
        Objects.requireNonNull(snbt_IO2);
        SNBT_CODEC = Codec.of(decoder, snbt_IO2::asString);
    }
}
