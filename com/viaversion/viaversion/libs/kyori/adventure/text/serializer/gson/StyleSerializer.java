package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;

final class StyleSerializer extends TypeAdapter
{
    private static final TextDecoration[] DECORATIONS;
    static final String FONT = "font";
    static final String COLOR = "color";
    static final String INSERTION = "insertion";
    static final String CLICK_EVENT = "clickEvent";
    static final String CLICK_EVENT_ACTION = "action";
    static final String CLICK_EVENT_VALUE = "value";
    static final String HOVER_EVENT = "hoverEvent";
    static final String HOVER_EVENT_ACTION = "action";
    static final String HOVER_EVENT_CONTENTS = "contents";
    @Deprecated
    static final String HOVER_EVENT_VALUE = "value";
    private final LegacyHoverEventSerializer legacyHover;
    private final boolean emitLegacyHover;
    private final Gson gson;
    static final boolean $assertionsDisabled;
    
    static TypeAdapter create(@Nullable final LegacyHoverEventSerializer legacyHover, final boolean emitLegacyHover, final Gson gson) {
        return new StyleSerializer(legacyHover, emitLegacyHover, gson).nullSafe();
    }
    
    private StyleSerializer(@Nullable final LegacyHoverEventSerializer legacyHover, final boolean emitLegacyHover, final Gson gson) {
        this.legacyHover = legacyHover;
        this.emitLegacyHover = emitLegacyHover;
        this.gson = gson;
    }
    
    @Override
    public Style read(final JsonReader in) throws IOException {
        in.beginObject();
        final Style.Builder style = Style.style();
        while (in.hasNext()) {
            final String nextName = in.nextName();
            if (nextName.equals("font")) {
                style.font((Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE));
            }
            else if (nextName.equals("color")) {
                final TextColorWrapper textColorWrapper = (TextColorWrapper)this.gson.fromJson(in, SerializerFactory.COLOR_WRAPPER_TYPE);
                if (textColorWrapper.color != null) {
                    style.color(textColorWrapper.color);
                }
                else {
                    if (textColorWrapper.decoration == null) {
                        continue;
                    }
                    style.decoration(textColorWrapper.decoration, TextDecoration.State.TRUE);
                }
            }
            else if (TextDecoration.NAMES.keys().contains(nextName)) {
                style.decoration((TextDecoration)TextDecoration.NAMES.value(nextName), this.readBoolean(in));
            }
            else if (nextName.equals("insertion")) {
                style.insertion(in.nextString());
            }
            else if (nextName.equals("clickEvent")) {
                in.beginObject();
                ClickEvent.Action action = null;
                String value = null;
                while (in.hasNext()) {
                    final String nextName2 = in.nextName();
                    if (nextName2.equals("action")) {
                        action = (ClickEvent.Action)this.gson.fromJson(in, SerializerFactory.CLICK_ACTION_TYPE);
                    }
                    else if (nextName2.equals("value")) {
                        value = ((in.peek() == JsonToken.NULL) ? null : in.nextString());
                    }
                    else {
                        in.skipValue();
                    }
                }
                if (action != null && action.readable() && value != null) {
                    style.clickEvent(ClickEvent.clickEvent(action, value));
                }
                in.endObject();
            }
            else if (nextName.equals("hoverEvent")) {
                final JsonObject jsonObject = (JsonObject)this.gson.fromJson(in, JsonObject.class);
                if (jsonObject == null) {
                    continue;
                }
                final JsonPrimitive asJsonPrimitive = jsonObject.getAsJsonPrimitive("action");
                if (asJsonPrimitive == null) {
                    continue;
                }
                final HoverEvent.Action action2 = (HoverEvent.Action)this.gson.fromJson(asJsonPrimitive, SerializerFactory.HOVER_ACTION_TYPE);
                if (!action2.readable()) {
                    continue;
                }
                Object value3;
                if (jsonObject.has("contents")) {
                    final JsonElement value2 = jsonObject.get("contents");
                    final Class type = action2.type();
                    if (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(type)) {
                        value3 = this.gson.fromJson(value2, SerializerFactory.COMPONENT_TYPE);
                    }
                    else if (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(type)) {
                        value3 = this.gson.fromJson(value2, SerializerFactory.SHOW_ITEM_TYPE);
                    }
                    else if (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(type)) {
                        value3 = this.gson.fromJson(value2, SerializerFactory.SHOW_ENTITY_TYPE);
                    }
                    else {
                        value3 = null;
                    }
                }
                else if (jsonObject.has("value")) {
                    value3 = this.legacyHoverEventContents(action2, (Component)this.gson.fromJson(jsonObject.get("value"), SerializerFactory.COMPONENT_TYPE));
                }
                else {
                    value3 = null;
                }
                if (value3 == null) {
                    continue;
                }
                style.hoverEvent(HoverEvent.hoverEvent(action2, value3));
            }
            else {
                in.skipValue();
            }
        }
        in.endObject();
        return style.build();
    }
    
    private boolean readBoolean(final JsonReader in) throws IOException {
        final JsonToken peek = in.peek();
        if (peek == JsonToken.BOOLEAN) {
            return in.nextBoolean();
        }
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
            return Boolean.parseBoolean(in.nextString());
        }
        throw new JsonParseException("Token of type " + peek + " cannot be interpreted as a boolean");
    }
    
    private Object legacyHoverEventContents(final HoverEvent.Action action, final Component rawValue) {
        if (action == HoverEvent.Action.SHOW_TEXT) {
            return rawValue;
        }
        if (this.legacyHover != null) {
            if (action == HoverEvent.Action.SHOW_ENTITY) {
                return this.legacyHover.deserializeShowEntity(rawValue, this.decoder());
            }
            if (action == HoverEvent.Action.SHOW_ITEM) {
                return this.legacyHover.deserializeShowItem(rawValue);
            }
        }
        throw new UnsupportedOperationException();
    }
    
    private Codec.Decoder decoder() {
        return this::lambda$decoder$0;
    }
    
    private Codec.Encoder encoder() {
        return this::lambda$encoder$1;
    }
    
    public void write(final JsonWriter out, final Style value) throws IOException {
        out.beginObject();
        while (0 < StyleSerializer.DECORATIONS.length) {
            final TextDecoration textDecoration = StyleSerializer.DECORATIONS[0];
            final TextDecoration.State decoration = value.decoration(textDecoration);
            if (decoration != TextDecoration.State.NOT_SET) {
                final String s = (String)TextDecoration.NAMES.key(textDecoration);
                assert s != null;
                out.name(s);
                out.value(decoration == TextDecoration.State.TRUE);
            }
            int n = 0;
            ++n;
        }
        final TextColor color = value.color();
        if (color != null) {
            out.name("color");
            this.gson.toJson(color, SerializerFactory.COLOR_TYPE, out);
        }
        final String insertion = value.insertion();
        if (insertion != null) {
            out.name("insertion");
            out.value(insertion);
        }
        final ClickEvent clickEvent = value.clickEvent();
        if (clickEvent != null) {
            out.name("clickEvent");
            out.beginObject();
            out.name("action");
            this.gson.toJson(clickEvent.action(), SerializerFactory.CLICK_ACTION_TYPE, out);
            out.name("value");
            out.value(clickEvent.value());
            out.endObject();
        }
        final HoverEvent hoverEvent = value.hoverEvent();
        if (hoverEvent != null) {
            out.name("hoverEvent");
            out.beginObject();
            out.name("action");
            final HoverEvent.Action action = hoverEvent.action();
            this.gson.toJson(action, SerializerFactory.HOVER_ACTION_TYPE, out);
            out.name("contents");
            if (action == HoverEvent.Action.SHOW_ITEM) {
                this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ITEM_TYPE, out);
            }
            else if (action == HoverEvent.Action.SHOW_ENTITY) {
                this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ENTITY_TYPE, out);
            }
            else {
                if (action != HoverEvent.Action.SHOW_TEXT) {
                    throw new JsonParseException("Don't know how to serialize " + hoverEvent.value());
                }
                this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
            }
            if (this.emitLegacyHover) {
                out.name("value");
                this.serializeLegacyHoverEvent(hoverEvent, out);
            }
            out.endObject();
        }
        final Key font = value.font();
        if (font != null) {
            out.name("font");
            this.gson.toJson(font, SerializerFactory.KEY_TYPE, out);
        }
        out.endObject();
    }
    
    private void serializeLegacyHoverEvent(final HoverEvent hoverEvent, final JsonWriter out) throws IOException {
        if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
            this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
        }
        else if (this.legacyHover != null) {
            Object o = null;
            if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
                o = this.legacyHover.serializeShowEntity((HoverEvent.ShowEntity)hoverEvent.value(), this.encoder());
            }
            else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
                o = this.legacyHover.serializeShowItem((HoverEvent.ShowItem)hoverEvent.value());
            }
            if (o != null) {
                this.gson.toJson(o, SerializerFactory.COMPONENT_TYPE, out);
            }
            else {
                out.nullValue();
            }
        }
        else {
            out.nullValue();
        }
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        return this.read(in);
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        this.write(out, (Style)value);
    }
    
    private String lambda$encoder$1(final Component component) throws JsonParseException {
        return this.gson.toJson(component, SerializerFactory.COMPONENT_TYPE);
    }
    
    private Component lambda$decoder$0(final String s) throws JsonParseException {
        return (Component)this.gson.fromJson(s, SerializerFactory.COMPONENT_TYPE);
    }
    
    static {
        $assertionsDisabled = !StyleSerializer.class.desiredAssertionStatus();
        DECORATIONS = new TextDecoration[] { TextDecoration.BOLD, TextDecoration.ITALIC, TextDecoration.UNDERLINED, TextDecoration.STRIKETHROUGH, TextDecoration.OBFUSCATED };
        final EnumSet<TextDecoration> all = EnumSet.allOf(TextDecoration.class);
        final TextDecoration[] decorations = StyleSerializer.DECORATIONS;
        while (0 < decorations.length) {
            all.remove(decorations[0]);
            int n = 0;
            ++n;
        }
        if (!all.isEmpty()) {
            throw new IllegalStateException("Gson serializer is missing some text decorations: " + all);
        }
    }
}
