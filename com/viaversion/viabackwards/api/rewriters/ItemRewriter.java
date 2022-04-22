package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;

public abstract class ItemRewriter extends ItemRewriterBase
{
    protected ItemRewriter(final BackwardsProtocol backwardsProtocol) {
        super(backwardsProtocol, true);
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag = (item.tag() != null) ? ((CompoundTag)item.tag().get("display")) : null;
        if (((BackwardsProtocol)this.protocol).getTranslatableRewriter() != null && compoundTag != null) {
            final StringTag stringTag = (StringTag)compoundTag.get("Name");
            if (stringTag != null) {
                final String string = ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(stringTag.getValue()).toString();
                if (!string.equals(stringTag.getValue())) {
                    this.saveStringTag(compoundTag, stringTag, "Name");
                }
                stringTag.setValue(string);
            }
            final ListTag listTag = (ListTag)compoundTag.get("Lore");
            if (listTag != null) {
                for (final Tag tag : listTag) {
                    if (!(tag instanceof StringTag)) {
                        continue;
                    }
                    final StringTag stringTag2 = (StringTag)tag;
                    final String string2 = ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(stringTag2.getValue()).toString();
                    if (!true && !string2.equals(stringTag2.getValue())) {
                        this.saveListTag(compoundTag, listTag, "Lore");
                    }
                    stringTag2.setValue(string2);
                }
            }
        }
        final MappedItem mappedItem = ((BackwardsProtocol)this.protocol).getMappingData().getMappedItem(item.identifier());
        if (mappedItem == null) {
            return super.handleItemToClient(item);
        }
        if (item.tag() == null) {
            item.setTag(new CompoundTag());
        }
        item.tag().put(this.nbtTagName + "|id", new IntTag(item.identifier()));
        item.setIdentifier(mappedItem.getId());
        if (compoundTag == null) {
            item.tag().put("display", compoundTag = new CompoundTag());
        }
        if (!compoundTag.contains("Name")) {
            compoundTag.put("Name", new StringTag(mappedItem.getJsonName()));
            compoundTag.put(this.nbtTagName + "|customName", new ByteTag());
        }
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null) {
            final IntTag intTag = (IntTag)item.tag().remove(this.nbtTagName + "|id");
            if (intTag != null) {
                item.setIdentifier(intTag.asInt());
            }
        }
        return item;
    }
    
    @Override
    public void registerAdvancements(final ClientboundPacketType clientboundPacketType, final Type type) {
        ((BackwardsProtocol)this.protocol).registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type p0, final PacketWrapper p1) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //     4: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //     9: pop            
                //    10: aload_2        
                //    11: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    14: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    19: checkcast       Ljava/lang/Integer;
                //    22: invokevirtual   java/lang/Integer.intValue:()I
                //    25: istore_3       
                //    26: iconst_0       
                //    27: iload_3        
                //    28: if_icmpge       286
                //    31: aload_2        
                //    32: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //    35: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    40: pop            
                //    41: aload_2        
                //    42: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //    45: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    50: checkcast       Ljava/lang/Boolean;
                //    53: invokevirtual   java/lang/Boolean.booleanValue:()Z
                //    56: ifeq            69
                //    59: aload_2        
                //    60: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //    63: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    68: pop            
                //    69: aload_2        
                //    70: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //    73: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    78: checkcast       Ljava/lang/Boolean;
                //    81: invokevirtual   java/lang/Boolean.booleanValue:()Z
                //    84: ifeq            231
                //    87: aload_2        
                //    88: getstatic       com/viaversion/viaversion/api/type/Type.COMPONENT:Lcom/viaversion/viaversion/api/type/Type;
                //    91: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    96: checkcast       Lcom/viaversion/viaversion/libs/gson/JsonElement;
                //    99: astore          5
                //   101: aload_2        
                //   102: getstatic       com/viaversion/viaversion/api/type/Type.COMPONENT:Lcom/viaversion/viaversion/api/type/Type;
                //   105: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   110: checkcast       Lcom/viaversion/viaversion/libs/gson/JsonElement;
                //   113: astore          6
                //   115: aload_0        
                //   116: getfield        com/viaversion/viabackwards/api/rewriters/ItemRewriter$1.this$0:Lcom/viaversion/viabackwards/api/rewriters/ItemRewriter;
                //   119: invokestatic    com/viaversion/viabackwards/api/rewriters/ItemRewriter.access$000:(Lcom/viaversion/viabackwards/api/rewriters/ItemRewriter;)Lcom/viaversion/viaversion/api/protocol/Protocol;
                //   122: checkcast       Lcom/viaversion/viabackwards/api/BackwardsProtocol;
                //   125: invokevirtual   com/viaversion/viabackwards/api/BackwardsProtocol.getTranslatableRewriter:()Lcom/viaversion/viabackwards/api/rewriters/TranslatableRewriter;
                //   128: astore          7
                //   130: aload           7
                //   132: ifnull          149
                //   135: aload           7
                //   137: aload           5
                //   139: invokevirtual   com/viaversion/viabackwards/api/rewriters/TranslatableRewriter.processText:(Lcom/viaversion/viaversion/libs/gson/JsonElement;)V
                //   142: aload           7
                //   144: aload           6
                //   146: invokevirtual   com/viaversion/viabackwards/api/rewriters/TranslatableRewriter.processText:(Lcom/viaversion/viaversion/libs/gson/JsonElement;)V
                //   149: aload_0        
                //   150: getfield        com/viaversion/viabackwards/api/rewriters/ItemRewriter$1.this$0:Lcom/viaversion/viabackwards/api/rewriters/ItemRewriter;
                //   153: aload_2        
                //   154: aload_1        
                //   155: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   160: checkcast       Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //   163: invokevirtual   com/viaversion/viabackwards/api/rewriters/ItemRewriter.handleItemToClient:(Lcom/viaversion/viaversion/api/minecraft/item/Item;)Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //   166: pop            
                //   167: aload_2        
                //   168: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   171: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   176: pop            
                //   177: aload_2        
                //   178: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
                //   181: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   186: checkcast       Ljava/lang/Integer;
                //   189: invokevirtual   java/lang/Integer.intValue:()I
                //   192: istore          8
                //   194: iload           8
                //   196: iconst_1       
                //   197: iand           
                //   198: ifeq            211
                //   201: aload_2        
                //   202: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   205: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   210: pop            
                //   211: aload_2        
                //   212: getstatic       com/viaversion/viaversion/api/type/Type.FLOAT:Lcom/viaversion/viaversion/api/type/types/FloatType;
                //   215: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   220: pop            
                //   221: aload_2        
                //   222: getstatic       com/viaversion/viaversion/api/type/Type.FLOAT:Lcom/viaversion/viaversion/api/type/types/FloatType;
                //   225: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   230: pop            
                //   231: aload_2        
                //   232: getstatic       com/viaversion/viaversion/api/type/Type.STRING_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                //   235: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   240: pop            
                //   241: aload_2        
                //   242: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   245: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   250: checkcast       Ljava/lang/Integer;
                //   253: invokevirtual   java/lang/Integer.intValue:()I
                //   256: istore          5
                //   258: iconst_0       
                //   259: iload           5
                //   261: if_icmpge       280
                //   264: aload_2        
                //   265: getstatic       com/viaversion/viaversion/api/type/Type.STRING_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                //   268: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   273: pop            
                //   274: iinc            6, 1
                //   277: goto            258
                //   280: iinc            4, 1
                //   283: goto            26
                //   286: return         
                //    Exceptions:
                //  throws java.lang.Exception
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        });
    }
    
    static Protocol access$000(final ItemRewriter itemRewriter) {
        return itemRewriter.protocol;
    }
}
