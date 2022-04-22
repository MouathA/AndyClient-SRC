package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;

public class MappedItem
{
    private final int id;
    private final String jsonName;
    
    public MappedItem(final int id, final String s) {
        this.id = id;
        this.jsonName = ChatRewriter.legacyTextToJsonString("§f" + s);
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getJsonName() {
        return this.jsonName;
    }
}
