package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;

final class SerializerFactory implements TypeAdapterFactory
{
    static final Class KEY_TYPE;
    static final Class COMPONENT_TYPE;
    static final Class STYLE_TYPE;
    static final Class CLICK_ACTION_TYPE;
    static final Class HOVER_ACTION_TYPE;
    static final Class SHOW_ITEM_TYPE;
    static final Class SHOW_ENTITY_TYPE;
    static final Class COLOR_WRAPPER_TYPE;
    static final Class COLOR_TYPE;
    static final Class TEXT_DECORATION_TYPE;
    static final Class BLOCK_NBT_POS_TYPE;
    private final boolean downsampleColors;
    private final LegacyHoverEventSerializer legacyHoverSerializer;
    private final boolean emitLegacyHover;
    
    SerializerFactory(final boolean downsampleColors, @Nullable final LegacyHoverEventSerializer legacyHoverSerializer, final boolean emitLegacyHover) {
        this.downsampleColors = downsampleColors;
        this.legacyHoverSerializer = legacyHoverSerializer;
        this.emitLegacyHover = emitLegacyHover;
    }
    
    @Override
    public TypeAdapter create(final Gson gson, final TypeToken type) {
        final Class rawType = type.getRawType();
        if (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(rawType)) {
            return ComponentSerializerImpl.create(gson);
        }
        if (SerializerFactory.KEY_TYPE.isAssignableFrom(rawType)) {
            return KeySerializer.INSTANCE;
        }
        if (SerializerFactory.STYLE_TYPE.isAssignableFrom(rawType)) {
            return StyleSerializer.create(this.legacyHoverSerializer, this.emitLegacyHover, gson);
        }
        if (SerializerFactory.CLICK_ACTION_TYPE.isAssignableFrom(rawType)) {
            return ClickEventActionSerializer.INSTANCE;
        }
        if (SerializerFactory.HOVER_ACTION_TYPE.isAssignableFrom(rawType)) {
            return HoverEventActionSerializer.INSTANCE;
        }
        if (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(rawType)) {
            return ShowItemSerializer.create(gson);
        }
        if (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(rawType)) {
            return ShowEntitySerializer.create(gson);
        }
        if (SerializerFactory.COLOR_WRAPPER_TYPE.isAssignableFrom(rawType)) {
            return TextColorWrapper.Serializer.INSTANCE;
        }
        if (SerializerFactory.COLOR_TYPE.isAssignableFrom(rawType)) {
            return this.downsampleColors ? TextColorSerializer.DOWNSAMPLE_COLOR : TextColorSerializer.INSTANCE;
        }
        if (SerializerFactory.TEXT_DECORATION_TYPE.isAssignableFrom(rawType)) {
            return TextDecorationSerializer.INSTANCE;
        }
        if (SerializerFactory.BLOCK_NBT_POS_TYPE.isAssignableFrom(rawType)) {
            return BlockNBTComponentPosSerializer.INSTANCE;
        }
        return null;
    }
    
    static {
        KEY_TYPE = Key.class;
        COMPONENT_TYPE = Component.class;
        STYLE_TYPE = Style.class;
        CLICK_ACTION_TYPE = ClickEvent.Action.class;
        HOVER_ACTION_TYPE = HoverEvent.Action.class;
        SHOW_ITEM_TYPE = HoverEvent.ShowItem.class;
        SHOW_ENTITY_TYPE = HoverEvent.ShowEntity.class;
        COLOR_WRAPPER_TYPE = TextColorWrapper.class;
        COLOR_TYPE = TextColor.class;
        TEXT_DECORATION_TYPE = TextDecoration.class;
        BLOCK_NBT_POS_TYPE = BlockNBTComponent.Pos.class;
    }
}
