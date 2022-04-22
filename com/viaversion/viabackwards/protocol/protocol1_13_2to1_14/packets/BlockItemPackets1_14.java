package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.rewriter.*;
import com.google.common.collect.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.*;

public class BlockItemPackets1_14 extends ItemRewriter
{
    private EnchantmentRewriter enchantmentRewriter;
    
    public BlockItemPackets1_14(final Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.UNSIGNED_BYTE, (short)(int)packetWrapper.read(Type.VAR_INT));
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        Object o = null;
                        String s = null;
                        if (intValue < 6) {
                            if (intValue == 2) {
                                s = "Barrel";
                            }
                            o = "minecraft:container";
                        }
                        else {
                            switch (intValue) {
                                case 11: {
                                    o = "minecraft:crafting_table";
                                    break;
                                }
                                case 9:
                                case 13:
                                case 14:
                                case 20: {
                                    if (intValue == 9) {
                                        s = "Blast Furnace";
                                    }
                                    else if (intValue == 20) {
                                        s = "Smoker";
                                    }
                                    else if (intValue == 14) {
                                        s = "Grindstone";
                                    }
                                    o = "minecraft:furnace";
                                    break;
                                }
                                case 6: {
                                    o = "minecraft:dropper";
                                    break;
                                }
                                case 12: {
                                    o = "minecraft:enchanting_table";
                                    break;
                                }
                                case 10: {
                                    o = "minecraft:brewing_stand";
                                    break;
                                }
                                case 18: {
                                    o = "minecraft:villager";
                                    break;
                                }
                                case 8: {
                                    o = "minecraft:beacon";
                                    break;
                                }
                                case 7:
                                case 21: {
                                    if (intValue == 21) {
                                        s = "Cartography Table";
                                    }
                                    o = "minecraft:anvil";
                                    break;
                                }
                                case 15: {
                                    o = "minecraft:hopper";
                                    break;
                                }
                                case 19: {
                                    o = "minecraft:shulker_box";
                                    break;
                                }
                            }
                        }
                        if (o == null) {
                            ViaBackwards.getPlatform().getLogger().warning("Can't open inventory for 1.13 player! Type: " + intValue);
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.STRING, o);
                        JsonElement legacyTextToJson = (JsonElement)packetWrapper.read(Type.COMPONENT);
                        final JsonObject asJsonObject;
                        if (s != null && legacyTextToJson.isJsonObject() && (asJsonObject = legacyTextToJson.getAsJsonObject()).has("translate") && (intValue != 2 || asJsonObject.getAsJsonPrimitive("translate").getAsString().equals("container.barrel"))) {
                            legacyTextToJson = ChatRewriter.legacyTextToJson(s);
                        }
                        packetWrapper.write(Type.COMPONENT, legacyTextToJson);
                        packetWrapper.write(Type.UNSIGNED_BYTE, 27);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_HORSE_WINDOW, ClientboundPackets1_13.OPEN_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.write(Type.STRING, "EntityHorse");
                        final JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("translate", "minecraft.horse");
                        packetWrapper.write(Type.COMPONENT, jsonObject);
                        packetWrapper.write(Type.UNSIGNED_BYTE, ((Integer)packetWrapper.read(Type.VAR_INT)).shortValue());
                        packetWrapper.passthrough(Type.INT);
                    }
                });
            }
        });
        final BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION);
        this.registerSetCooldown(ClientboundPackets1_14.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.TRADE_LIST, ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.STRING, "minecraft:trader_list");
                        packetWrapper.write(Type.INT, (int)packetWrapper.read(Type.VAR_INT));
                        while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, this.this$1.this$0.handleItemToClient((Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM)));
                            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, this.this$1.this$0.handleItemToClient((Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM)));
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, this.this$1.this$0.handleItemToClient((Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM)));
                            }
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.read(Type.INT);
                            packetWrapper.read(Type.INT);
                            packetWrapper.read(Type.FLOAT);
                            int n = 0;
                            ++n;
                        }
                        packetWrapper.read(Type.VAR_INT);
                        packetWrapper.read(Type.VAR_INT);
                        packetWrapper.read(Type.BOOLEAN);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_BOOK, ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.STRING, "minecraft:book_open");
                        packetWrapper.passthrough(Type.VAR_INT);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_EQUIPMENT, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final EntityType entityType = packetWrapper.user().getEntityTracker(Protocol1_13_2To1_14.class).entityType((int)packetWrapper.get(Type.VAR_INT, 0));
                        if (entityType == null) {
                            return;
                        }
                        if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_HORSE)) {
                            packetWrapper.setId(63);
                            packetWrapper.resetReader();
                            packetWrapper.passthrough(Type.VAR_INT);
                            packetWrapper.read(Type.VAR_INT);
                            final Item item = (Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                            final int n = (item == null || item.identifier() == 0) ? 0 : (item.identifier() - 726);
                            if (n < 0 || n > 3) {
                                ViaBackwards.getPlatform().getLogger().warning("Received invalid horse armor: " + item);
                                packetWrapper.cancel();
                                return;
                            }
                            final ArrayList<Metadata> list = new ArrayList<Metadata>();
                            list.add(new Metadata(16, Types1_13_2.META_TYPES.varIntType, n));
                            packetWrapper.write(Types1_13.METADATA_LIST, list);
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.DECLARE_RECIPES, new PacketRemapper((RecipeRewriter)new RecipeRewriter1_13_2(this.protocol)) {
            final RecipeRewriter val$recipeHandler;
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    private final Set removedTypes = ImmutableSet.of("crafting_special_suspiciousstew", "blasting", "smoking", "campfire_cooking", "stonecutting");
                    final BlockItemPackets1_14$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                        while (0 < intValue) {
                            final String s = (String)packetWrapper.read(Type.STRING);
                            final String s2 = (String)packetWrapper.read(Type.STRING);
                            final String replace = s.replace("minecraft:", "");
                            if (this.removedTypes.contains(replace)) {
                                final String s3 = replace;
                                switch (s3.hashCode()) {
                                    case -1050336534: {
                                        if (s3.equals("blasting")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -2084878740: {
                                        if (s3.equals("smoking")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -68678766: {
                                        if (s3.equals("campfire_cooking")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -858939349: {
                                        if (s3.equals("stonecutting")) {}
                                        break;
                                    }
                                }
                                switch (3) {
                                    case 0:
                                    case 1:
                                    case 2: {
                                        packetWrapper.read(Type.STRING);
                                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                                        packetWrapper.read(Type.FLOAT);
                                        packetWrapper.read(Type.VAR_INT);
                                        break;
                                    }
                                    case 3: {
                                        packetWrapper.read(Type.STRING);
                                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                                        break;
                                    }
                                }
                                int n = 0;
                                ++n;
                            }
                            else {
                                packetWrapper.write(Type.STRING, s2);
                                packetWrapper.write(Type.STRING, replace);
                                this.this$1.val$recipeHandler.handle(packetWrapper, replace);
                            }
                            int n2 = 0;
                            ++n2;
                        }
                        packetWrapper.set(Type.VAR_INT, 0, intValue - 0);
                    }
                });
            }
        });
        this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_BREAK_ANIMATION, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_ACTION, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int newBlockId = ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$000(this.this$0)).getMappingData().getNewBlockId((int)packetWrapper.get(Type.VAR_INT, 0));
                if (newBlockId == -1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.VAR_INT, 0, newBlockId);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$11 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$100(this.this$1.this$0)).getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.EXPLOSION, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$12 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        while (0 < 3) {
                            final float floatValue = (float)packetWrapper.get(Type.FLOAT, 0);
                            if (floatValue < 0.0f) {
                                packetWrapper.set(Type.FLOAT, 0, (float)Math.floor(floatValue));
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$13 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper p0) throws Exception {
                        // 
                        // This method could not be decompiled.
                        // 
                        // Original Bytecode:
                        // 
                        //     1: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                        //     6: ldc             Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;.class
                        //     8: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                        //    13: checkcast       Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;
                        //    16: astore_2       
                        //    17: aload_1        
                        //    18: new             Lcom/viaversion/viaversion/protocols/protocol1_14to1_13_2/types/Chunk1_14Type;
                        //    21: dup            
                        //    22: invokespecial   com/viaversion/viaversion/protocols/protocol1_14to1_13_2/types/Chunk1_14Type.<init>:()V
                        //    25: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    30: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
                        //    33: astore_3       
                        //    34: aload_1        
                        //    35: new             Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type;
                        //    38: dup            
                        //    39: aload_2        
                        //    40: invokespecial   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type.<init>:(Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;)V
                        //    43: aload_3        
                        //    44: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //    49: aload_1        
                        //    50: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                        //    55: ldc             Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage;.class
                        //    57: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                        //    62: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage;
                        //    65: aload_3        
                        //    66: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                        //    71: aload_3        
                        //    72: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                        //    77: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage.getStoredLight:(II)Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage$ChunkLight;
                        //    80: astore          4
                        //    82: iconst_0       
                        //    83: aload_3        
                        //    84: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //    89: arraylength    
                        //    90: if_icmpge       408
                        //    93: aload_3        
                        //    94: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //    99: iconst_0       
                        //   100: aaload         
                        //   101: astore          6
                        //   103: aload           6
                        //   105: ifnonnull       111
                        //   108: goto            402
                        //   111: new             Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLightImpl;
                        //   114: dup            
                        //   115: invokespecial   com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLightImpl.<init>:()V
                        //   118: astore          7
                        //   120: aload           6
                        //   122: aload           7
                        //   124: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setLight:(Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight;)V
                        //   129: aload           4
                        //   131: ifnonnull       167
                        //   134: aload           7
                        //   136: getstatic       com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage.FULL_LIGHT:[B
                        //   139: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.setBlockLight:([B)V
                        //   144: aload_2        
                        //   145: invokevirtual   com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld.getEnvironment:()Lcom/viaversion/viaversion/api/minecraft/Environment;
                        //   148: getstatic       com/viaversion/viaversion/api/minecraft/Environment.NORMAL:Lcom/viaversion/viaversion/api/minecraft/Environment;
                        //   151: if_acmpne       235
                        //   154: aload           7
                        //   156: getstatic       com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage.FULL_LIGHT:[B
                        //   159: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.setSkyLight:([B)V
                        //   164: goto            235
                        //   167: aload           4
                        //   169: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage$ChunkLight.getBlockLight:()[[B
                        //   172: iconst_0       
                        //   173: aaload         
                        //   174: astore          8
                        //   176: aload           7
                        //   178: aload           8
                        //   180: ifnull          188
                        //   183: aload           8
                        //   185: goto            191
                        //   188: getstatic       com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage.FULL_LIGHT:[B
                        //   191: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.setBlockLight:([B)V
                        //   196: aload_2        
                        //   197: invokevirtual   com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld.getEnvironment:()Lcom/viaversion/viaversion/api/minecraft/Environment;
                        //   200: getstatic       com/viaversion/viaversion/api/minecraft/Environment.NORMAL:Lcom/viaversion/viaversion/api/minecraft/Environment;
                        //   203: if_acmpne       235
                        //   206: aload           4
                        //   208: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage$ChunkLight.getSkyLight:()[[B
                        //   211: iconst_0       
                        //   212: aaload         
                        //   213: astore          9
                        //   215: aload           7
                        //   217: aload           9
                        //   219: ifnull          227
                        //   222: aload           9
                        //   224: goto            230
                        //   227: getstatic       com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/ChunkLightStorage.FULL_LIGHT:[B
                        //   230: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.setSkyLight:([B)V
                        //   235: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
                        //   238: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.isNonFullBlockLightFix:()Z
                        //   243: ifeq            344
                        //   246: aload           6
                        //   248: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getNonAirBlocksCount:()I
                        //   253: ifeq            344
                        //   256: aload           7
                        //   258: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.hasBlockLight:()Z
                        //   263: ifeq            344
                        //   266: iconst_0       
                        //   267: bipush          16
                        //   269: if_icmpge       344
                        //   272: iconst_0       
                        //   273: bipush          16
                        //   275: if_icmpge       338
                        //   278: iconst_0       
                        //   279: bipush          16
                        //   281: if_icmpge       332
                        //   284: aload           6
                        //   286: iconst_0       
                        //   287: iconst_0       
                        //   288: iconst_0       
                        //   289: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getFlatBlock:(III)I
                        //   294: istore          11
                        //   296: getstatic       com/viaversion/viaversion/protocols/protocol1_14to1_13_2/Protocol1_14To1_13_2.MAPPINGS:Lcom/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData;
                        //   299: invokevirtual   com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.getNonFullBlocks:()Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
                        //   302: iload           11
                        //   304: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntSet.contains:(I)Z
                        //   309: ifeq            326
                        //   312: aload           7
                        //   314: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.getBlockLightNibbleArray:()Lcom/viaversion/viaversion/api/minecraft/chunks/NibbleArray;
                        //   319: iconst_0       
                        //   320: iconst_0       
                        //   321: iconst_0       
                        //   322: iconst_0       
                        //   323: invokevirtual   com/viaversion/viaversion/api/minecraft/chunks/NibbleArray.set:(IIII)V
                        //   326: iinc            10, 1
                        //   329: goto            278
                        //   332: iinc            9, 1
                        //   335: goto            272
                        //   338: iinc            8, 1
                        //   341: goto            266
                        //   344: iconst_0       
                        //   345: aload           6
                        //   347: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                        //   352: if_icmpge       402
                        //   355: aload           6
                        //   357: iconst_0       
                        //   358: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                        //   363: istore          9
                        //   365: aload_0        
                        //   366: getfield        com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/BlockItemPackets1_14$13$1.this$1:Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/BlockItemPackets1_14$13;
                        //   369: getfield        com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/BlockItemPackets1_14$13.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/BlockItemPackets1_14;
                        //   372: invokestatic    com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/BlockItemPackets1_14.access$200:(Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/BlockItemPackets1_14;)Lcom/viaversion/viaversion/api/protocol/Protocol;
                        //   375: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14;
                        //   378: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14.getMappingData:()Lcom/viaversion/viabackwards/api/data/BackwardsMappings;
                        //   381: iconst_0       
                        //   382: invokevirtual   com/viaversion/viabackwards/api/data/BackwardsMappings.getNewBlockStateId:(I)I
                        //   385: istore          10
                        //   387: aload           6
                        //   389: iconst_0       
                        //   390: iconst_0       
                        //   391: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setPaletteEntry:(II)V
                        //   396: iinc            8, 1
                        //   399: goto            344
                        //   402: iinc            5, 1
                        //   405: goto            82
                        //   408: return         
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
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.UNLOAD_CHUNK, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$14 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ChunkLightStorage)packetWrapper.user().get(ChunkLightStorage.class)).unloadChunk((int)packetWrapper.passthrough(Type.INT), (int)packetWrapper.passthrough(Type.INT));
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.EFFECT, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_14$15 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                        if (intValue == 1010) {
                            packetWrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$300(this.this$1.this$0)).getMappingData().getNewItemId(intValue2));
                        }
                        else if (intValue == 2001) {
                            packetWrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$400(this.this$1.this$0)).getMappingData().getNewBlockStateId(intValue2));
                        }
                    }
                });
            }
        });
        this.registerSpawnParticle(ClientboundPackets1_14.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.MAP_DATA, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_POSITION, new PacketRemapper() {
            final BlockItemPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        (this.enchantmentRewriter = new EnchantmentRewriter(this, false)).registerEnchantment("minecraft:multishot", "§7Multishot");
        this.enchantmentRewriter.registerEnchantment("minecraft:quick_charge", "§7Quick Charge");
        this.enchantmentRewriter.registerEnchantment("minecraft:piercing", "§7Piercing");
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        final CompoundTag tag = item.tag();
        final CompoundTag compoundTag;
        if (tag != null && (compoundTag = (CompoundTag)tag.get("display")) != null) {
            final ListTag listTag = (ListTag)compoundTag.get("Lore");
            if (listTag != null) {
                this.saveListTag(compoundTag, listTag, "Lore");
                for (final Tag tag2 : listTag) {
                    if (!(tag2 instanceof StringTag)) {
                        continue;
                    }
                    final StringTag stringTag = (StringTag)tag2;
                    final String value = stringTag.getValue();
                    if (value == null || value.isEmpty()) {
                        continue;
                    }
                    stringTag.setValue(ChatRewriter.jsonToLegacyText(value));
                }
            }
        }
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        final CompoundTag tag = item.tag();
        final CompoundTag compoundTag;
        if (tag != null && (compoundTag = (CompoundTag)tag.get("display")) != null) {
            final ListTag listTag = (ListTag)compoundTag.get("Lore");
            if (listTag != null && !this.hasBackupTag(compoundTag, "Lore")) {
                for (final Tag tag2 : listTag) {
                    if (tag2 instanceof StringTag) {
                        final StringTag stringTag = (StringTag)tag2;
                        stringTag.setValue(ChatRewriter.legacyTextToJsonString(stringTag.getValue()));
                    }
                }
            }
        }
        this.enchantmentRewriter.handleToServer(item);
        super.handleItemToServer(item);
        return item;
    }
    
    static Protocol access$000(final BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }
    
    static Protocol access$100(final BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }
    
    static Protocol access$200(final BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }
    
    static Protocol access$300(final BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }
    
    static Protocol access$400(final BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }
}
