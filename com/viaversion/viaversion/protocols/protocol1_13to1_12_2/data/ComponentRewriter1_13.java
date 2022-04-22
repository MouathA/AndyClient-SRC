package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.nbt.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;

public class ComponentRewriter1_13 extends ComponentRewriter
{
    public ComponentRewriter1_13(final Protocol protocol) {
        super(protocol);
    }
    
    @Override
    protected void handleHoverEvent(final JsonObject jsonObject) {
        super.handleHoverEvent(jsonObject);
        if (!jsonObject.getAsJsonPrimitive("action").getAsString().equals("show_item")) {
            return;
        }
        final JsonElement value = jsonObject.get("value");
        if (value == null) {
            return;
        }
        final String itemNBT = this.findItemNBT(value);
        if (itemNBT == null) {
            return;
        }
        final CompoundTag string = BinaryTagIO.readString(itemNBT);
        final CompoundTag tag = (CompoundTag)string.get("tag");
        final ShortTag shortTag = (ShortTag)string.get("Damage");
        final short data = (short)((shortTag != null) ? shortTag.asShort() : 0);
        final DataItem dataItem = new DataItem();
        dataItem.setData(data);
        dataItem.setTag(tag);
        this.protocol.getItemRewriter().handleItemToClient(dataItem);
        if (data != dataItem.data()) {
            string.put("Damage", new ShortTag(dataItem.data()));
        }
        if (tag != null) {
            string.put("tag", tag);
        }
        final JsonArray jsonArray = new JsonArray();
        final JsonObject jsonObject2 = new JsonObject();
        jsonArray.add(jsonObject2);
        jsonObject2.addProperty("text", BinaryTagIO.writeString(string));
        jsonObject.add("value", jsonArray);
    }
    
    protected String findItemNBT(final JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            final Iterator iterator = jsonElement.getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                final String itemNBT = this.findItemNBT(iterator.next());
                if (itemNBT != null) {
                    return itemNBT;
                }
            }
        }
        else if (jsonElement.isJsonObject()) {
            final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonObject().getAsJsonPrimitive("text");
            if (asJsonPrimitive != null) {
                return asJsonPrimitive.getAsString();
            }
        }
        else if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsJsonPrimitive().getAsString();
        }
        return null;
    }
    
    @Override
    protected void handleTranslate(final JsonObject jsonObject, final String s) {
        super.handleTranslate(jsonObject, s);
        String s2 = Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().get(s);
        if (s2 == null) {
            s2 = Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(s);
        }
        if (s2 != null) {
            jsonObject.addProperty("translate", s2);
        }
    }
}
