package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class RecipeRewriter1_13_2 extends RecipeRewriter
{
    public RecipeRewriter1_13_2(final Protocol protocol) {
        super(protocol);
        this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
        this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
        this.recipeHandlers.put("smelting", this::handleSmelting);
    }
    
    public void handleSmelting(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        final Item[] array = (Item[])packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        while (0 < array.length) {
            this.rewrite(array[0]);
            int n = 0;
            ++n;
        }
        this.rewrite((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
        packetWrapper.passthrough(Type.FLOAT);
        packetWrapper.passthrough(Type.VAR_INT);
    }
    
    public void handleCraftingShaped(final PacketWrapper packetWrapper) throws Exception {
        final int n = (int)packetWrapper.passthrough(Type.VAR_INT) * (int)packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.STRING);
        while (0 < n) {
            final Item[] array = (Item[])packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
            while (0 < array.length) {
                this.rewrite(array[0]);
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        this.rewrite((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
    
    public void handleCraftingShapeless(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
            final Item[] array = (Item[])packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
            while (0 < array.length) {
                this.rewrite(array[0]);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.rewrite((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
}
