package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class RecipeRewriter1_16 extends RecipeRewriter1_14
{
    public RecipeRewriter1_16(final Protocol protocol) {
        super(protocol);
        this.recipeHandlers.put("smithing", this::handleSmithing);
    }
    
    public void handleSmithing(final PacketWrapper p0) throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       com/viaversion/viaversion/api/type/Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT:Lcom/viaversion/viaversion/api/type/Type;
        //     4: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //     9: checkcast       [Lcom/viaversion/viaversion/api/minecraft/item/Item;
        //    12: astore_2       
        //    13: aload_2        
        //    14: astore_3       
        //    15: aload_3        
        //    16: arraylength    
        //    17: istore          4
        //    19: iconst_0       
        //    20: iload           4
        //    22: if_icmpge       42
        //    25: aload_3        
        //    26: iconst_0       
        //    27: aaload         
        //    28: astore          6
        //    30: aload_0        
        //    31: aload           6
        //    33: invokevirtual   com/viaversion/viaversion/protocols/protocol1_16to1_15_2/data/RecipeRewriter1_16.rewrite:(Lcom/viaversion/viaversion/api/minecraft/item/Item;)V
        //    36: iinc            5, 1
        //    39: goto            19
        //    42: aload_1        
        //    43: getstatic       com/viaversion/viaversion/api/type/Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT:Lcom/viaversion/viaversion/api/type/Type;
        //    46: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //    51: checkcast       [Lcom/viaversion/viaversion/api/minecraft/item/Item;
        //    54: astore_3       
        //    55: aload_3        
        //    56: astore          4
        //    58: aload           4
        //    60: arraylength    
        //    61: istore          5
        //    63: iconst_0       
        //    64: iconst_0       
        //    65: if_icmpge       86
        //    68: aload           4
        //    70: iconst_0       
        //    71: aaload         
        //    72: astore          7
        //    74: aload_0        
        //    75: aload           7
        //    77: invokevirtual   com/viaversion/viaversion/protocols/protocol1_16to1_15_2/data/RecipeRewriter1_16.rewrite:(Lcom/viaversion/viaversion/api/minecraft/item/Item;)V
        //    80: iinc            6, 1
        //    83: goto            63
        //    86: aload_0        
        //    87: aload_1        
        //    88: getstatic       com/viaversion/viaversion/api/type/Type.FLAT_VAR_INT_ITEM:Lcom/viaversion/viaversion/api/type/Type;
        //    91: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //    96: checkcast       Lcom/viaversion/viaversion/api/minecraft/item/Item;
        //    99: invokevirtual   com/viaversion/viaversion/protocols/protocol1_16to1_15_2/data/RecipeRewriter1_16.rewrite:(Lcom/viaversion/viaversion/api/minecraft/item/Item;)V
        //   102: return         
        //    Exceptions:
        //  throws java.lang.Exception
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
