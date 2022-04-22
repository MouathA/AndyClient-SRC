package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.gson.*;

public class CommandBlockHandler implements BlockEntityProvider.BlockEntityHandler
{
    private final Protocol1_13To1_12_2 protocol;
    
    public CommandBlockHandler() {
        this.protocol = (Protocol1_13To1_12_2)Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class);
    }
    
    @Override
    public int transform(final UserConnection userConnection, final CompoundTag compoundTag) {
        final Tag value = compoundTag.get("CustomName");
        if (value instanceof StringTag) {
            ((StringTag)value).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)value).getValue()));
        }
        final Tag value2 = compoundTag.get("LastOutput");
        if (value2 instanceof StringTag) {
            final JsonElement string = JsonParser.parseString(((StringTag)value2).getValue());
            this.protocol.getComponentRewriter().processText(string);
            ((StringTag)value2).setValue(string.toString());
        }
        return -1;
    }
}
