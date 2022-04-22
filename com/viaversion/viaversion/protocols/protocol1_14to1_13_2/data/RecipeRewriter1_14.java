package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class RecipeRewriter1_14 extends RecipeRewriter1_13_2
{
    public RecipeRewriter1_14(final Protocol protocol) {
        super(protocol);
        this.recipeHandlers.put("stonecutting", this::handleStonecutting);
        this.recipeHandlers.put("blasting", this::handleSmelting);
        this.recipeHandlers.put("smoking", this::handleSmelting);
        this.recipeHandlers.put("campfire_cooking", this::handleSmelting);
    }
    
    public void handleStonecutting(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        final Item[] array = (Item[])packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        while (0 < array.length) {
            this.rewrite(array[0]);
            int n = 0;
            ++n;
        }
        this.rewrite((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
}
