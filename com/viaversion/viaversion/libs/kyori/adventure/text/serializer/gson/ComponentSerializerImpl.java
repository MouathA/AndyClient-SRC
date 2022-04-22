package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import java.lang.reflect.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.io.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.reflect.*;

final class ComponentSerializerImpl extends TypeAdapter
{
    static final String TEXT = "text";
    static final String TRANSLATE = "translate";
    static final String TRANSLATE_WITH = "with";
    static final String SCORE = "score";
    static final String SCORE_NAME = "name";
    static final String SCORE_OBJECTIVE = "objective";
    static final String SCORE_VALUE = "value";
    static final String SELECTOR = "selector";
    static final String KEYBIND = "keybind";
    static final String EXTRA = "extra";
    static final String NBT = "nbt";
    static final String NBT_INTERPRET = "interpret";
    static final String NBT_BLOCK = "block";
    static final String NBT_ENTITY = "entity";
    static final String NBT_STORAGE = "storage";
    static final String SEPARATOR = "separator";
    static final Type COMPONENT_LIST_TYPE;
    private final Gson gson;
    
    static TypeAdapter create(final Gson gson) {
        return new ComponentSerializerImpl(gson).nullSafe();
    }
    
    private ComponentSerializerImpl(final Gson gson) {
        this.gson = gson;
    }
    
    @Override
    public BuildableComponent read(final JsonReader in) throws IOException {
        final JsonToken peek = in.peek();
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER || peek == JsonToken.BOOLEAN) {
            return Component.text(readString(in));
        }
        if (peek == JsonToken.BEGIN_ARRAY) {
            ComponentBuilder builder = null;
            in.beginArray();
            while (in.hasNext()) {
                final BuildableComponent read = this.read(in);
                if (builder == null) {
                    builder = read.toBuilder();
                }
                else {
                    builder.append(read);
                }
            }
            if (builder == null) {
                throw notSureHowToDeserialize(in.getPath());
            }
            in.endArray();
            return builder.build();
        }
        else {
            if (peek != JsonToken.BEGIN_OBJECT) {
                throw notSureHowToDeserialize(in.getPath());
            }
            final JsonObject jsonObject = new JsonObject();
            List<Object> emptyList = Collections.emptyList();
            String string = null;
            String nextString = null;
            List args = null;
            String nextString2 = null;
            String nextString3 = null;
            String nextString4 = null;
            String nextString5 = null;
            String nextString6 = null;
            String nextString7 = null;
            BlockNBTComponent.Pos pos = null;
            String nextString8 = null;
            Key storage = null;
            Component read2 = null;
            in.beginObject();
            while (in.hasNext()) {
                final String nextName = in.nextName();
                if (nextName.equals("text")) {
                    string = readString(in);
                }
                else if (nextName.equals("translate")) {
                    nextString = in.nextString();
                }
                else if (nextName.equals("with")) {
                    args = (List)this.gson.fromJson(in, ComponentSerializerImpl.COMPONENT_LIST_TYPE);
                }
                else if (nextName.equals("score")) {
                    in.beginObject();
                    while (in.hasNext()) {
                        final String nextName2 = in.nextName();
                        if (nextName2.equals("name")) {
                            nextString2 = in.nextString();
                        }
                        else if (nextName2.equals("objective")) {
                            nextString3 = in.nextString();
                        }
                        else if (nextName2.equals("value")) {
                            nextString4 = in.nextString();
                        }
                        else {
                            in.skipValue();
                        }
                    }
                    if (nextString2 == null || nextString3 == null) {
                        throw new JsonParseException("A score component requires a name and objective");
                    }
                    in.endObject();
                }
                else if (nextName.equals("selector")) {
                    nextString5 = in.nextString();
                }
                else if (nextName.equals("keybind")) {
                    nextString6 = in.nextString();
                }
                else if (nextName.equals("nbt")) {
                    nextString7 = in.nextString();
                }
                else if (nextName.equals("interpret")) {
                    in.nextBoolean();
                }
                else if (nextName.equals("block")) {
                    pos = (BlockNBTComponent.Pos)this.gson.fromJson(in, SerializerFactory.BLOCK_NBT_POS_TYPE);
                }
                else if (nextName.equals("entity")) {
                    nextString8 = in.nextString();
                }
                else if (nextName.equals("storage")) {
                    storage = (Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE);
                }
                else if (nextName.equals("extra")) {
                    emptyList = (List<Object>)this.gson.fromJson(in, ComponentSerializerImpl.COMPONENT_LIST_TYPE);
                }
                else if (nextName.equals("separator")) {
                    read2 = this.read(in);
                }
                else {
                    jsonObject.add(nextName, (JsonElement)this.gson.fromJson(in, JsonElement.class));
                }
            }
            Object o;
            if (string != null) {
                o = Component.text().content(string);
            }
            else if (nextString != null) {
                if (args != null) {
                    o = Component.translatable().key(nextString).args(args);
                }
                else {
                    o = Component.translatable().key(nextString);
                }
            }
            else if (nextString2 != null && nextString3 != null) {
                if (nextString4 == null) {
                    o = Component.score().name(nextString2).objective(nextString3);
                }
                else {
                    o = Component.score().name(nextString2).objective(nextString3).value(nextString4);
                }
            }
            else if (nextString5 != null) {
                o = Component.selector().pattern(nextString5).separator(read2);
            }
            else if (nextString6 != null) {
                o = Component.keybind().keybind(nextString6);
            }
            else {
                if (nextString7 == null) {
                    throw notSureHowToDeserialize(in.getPath());
                }
                if (pos != null) {
                    o = ((BlockNBTComponent.Builder)nbt(Component.blockNBT(), nextString7, false, read2)).pos(pos);
                }
                else if (nextString8 != null) {
                    o = ((EntityNBTComponent.Builder)nbt(Component.entityNBT(), nextString7, false, read2)).selector(nextString8);
                }
                else {
                    if (storage == null) {
                        throw notSureHowToDeserialize(in.getPath());
                    }
                    o = ((StorageNBTComponent.Builder)nbt(Component.storageNBT(), nextString7, false, read2)).storage(storage);
                }
            }
            ((ComponentBuilder)o).style((Style)this.gson.fromJson(jsonObject, SerializerFactory.STYLE_TYPE)).append(emptyList);
            in.endObject();
            return ((ComponentBuilder)o).build();
        }
    }
    
    private static String readString(final JsonReader in) throws IOException {
        final JsonToken peek = in.peek();
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
            return in.nextString();
        }
        if (peek == JsonToken.BOOLEAN) {
            return String.valueOf(in.nextBoolean());
        }
        throw new JsonParseException("Token of type " + peek + " cannot be interpreted as a string");
    }
    
    private static NBTComponentBuilder nbt(final NBTComponentBuilder builder, final String nbt, final boolean interpret, @Nullable final Component separator) {
        return builder.nbtPath(nbt).interpret(interpret).separator(separator);
    }
    
    public void write(final JsonWriter out, final Component value) throws IOException {
        out.beginObject();
        if (value.hasStyling()) {
            final JsonElement jsonTree = this.gson.toJsonTree(value.style(), SerializerFactory.STYLE_TYPE);
            if (jsonTree.isJsonObject()) {
                for (final Map.Entry<String, V> entry : jsonTree.getAsJsonObject().entrySet()) {
                    out.name(entry.getKey());
                    this.gson.toJson((JsonElement)entry.getValue(), out);
                }
            }
        }
        if (!value.children().isEmpty()) {
            out.name("extra");
            this.gson.toJson(value.children(), ComponentSerializerImpl.COMPONENT_LIST_TYPE, out);
        }
        if (value instanceof TextComponent) {
            out.name("text");
            out.value(((TextComponent)value).content());
        }
        else if (value instanceof TranslatableComponent) {
            final TranslatableComponent translatableComponent = (TranslatableComponent)value;
            out.name("translate");
            out.value(translatableComponent.key());
            if (!translatableComponent.args().isEmpty()) {
                out.name("with");
                this.gson.toJson(translatableComponent.args(), ComponentSerializerImpl.COMPONENT_LIST_TYPE, out);
            }
        }
        else if (value instanceof ScoreComponent) {
            final ScoreComponent scoreComponent = (ScoreComponent)value;
            out.name("score");
            out.beginObject();
            out.name("name");
            out.value(scoreComponent.name());
            out.name("objective");
            out.value(scoreComponent.objective());
            if (scoreComponent.value() != null) {
                out.name("value");
                out.value(scoreComponent.value());
            }
            out.endObject();
        }
        else if (value instanceof SelectorComponent) {
            final SelectorComponent selectorComponent = (SelectorComponent)value;
            out.name("selector");
            out.value(selectorComponent.pattern());
            this.serializeSeparator(out, selectorComponent.separator());
        }
        else if (value instanceof KeybindComponent) {
            out.name("keybind");
            out.value(((KeybindComponent)value).keybind());
        }
        else {
            if (!(value instanceof NBTComponent)) {
                throw notSureHowToSerialize(value);
            }
            final NBTComponent nbtComponent = (NBTComponent)value;
            out.name("nbt");
            out.value(nbtComponent.nbtPath());
            out.name("interpret");
            out.value(nbtComponent.interpret());
            this.serializeSeparator(out, nbtComponent.separator());
            if (value instanceof BlockNBTComponent) {
                out.name("block");
                this.gson.toJson(((BlockNBTComponent)value).pos(), SerializerFactory.BLOCK_NBT_POS_TYPE, out);
            }
            else if (value instanceof EntityNBTComponent) {
                out.name("entity");
                out.value(((EntityNBTComponent)value).selector());
            }
            else {
                if (!(value instanceof StorageNBTComponent)) {
                    throw notSureHowToSerialize(value);
                }
                out.name("storage");
                this.gson.toJson(((StorageNBTComponent)value).storage(), SerializerFactory.KEY_TYPE, out);
            }
        }
        out.endObject();
    }
    
    private void serializeSeparator(final JsonWriter out, @Nullable final Component separator) throws IOException {
        if (separator != null) {
            out.name("separator");
            this.write(out, separator);
        }
    }
    
    static JsonParseException notSureHowToDeserialize(final Object element) {
        return new JsonParseException("Don't know how to turn " + element + " into a Component");
    }
    
    private static IllegalArgumentException notSureHowToSerialize(final Component component) {
        return new IllegalArgumentException("Don't know how to serialize " + component + " as a Component");
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        return this.read(in);
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        this.write(out, (Component)value);
    }
    
    static {
        COMPONENT_LIST_TYPE = new TypeToken() {}.getType();
    }
}
