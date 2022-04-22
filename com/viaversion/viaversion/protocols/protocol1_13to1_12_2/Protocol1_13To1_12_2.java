package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.google.common.primitives.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;
import com.google.common.collect.*;

public class Protocol1_13To1_12_2 extends AbstractProtocol
{
    public static final MappingData MAPPINGS;
    private static final Map SCOREBOARD_TEAM_NAME_REWRITE;
    private static final Set FORMATTING_CODES;
    private final EntityRewriter entityRewriter;
    private final ItemRewriter itemRewriter;
    private final ComponentRewriter componentRewriter;
    public static final PacketHandler POS_TO_3_INT;
    private static final PacketHandler SEND_DECLARE_COMMANDS_AND_TAGS;
    
    public Protocol1_13To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_13.class, ServerboundPackets1_12_1.class, ServerboundPackets1_13.class);
        this.entityRewriter = new MetadataRewriter1_13To1_12_2(this);
        this.itemRewriter = new InventoryPackets(this);
        this.componentRewriter = new ComponentRewriter1_13(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        this.registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Protocol1_13To1_12_2.access$000(this.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
        this.registerClientbound(State.STATUS, 0, 0, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson((String)packetWrapper.get(Type.STRING, 0), JsonObject.class);
                        if (jsonObject.has("favicon")) {
                            jsonObject.addProperty("favicon", jsonObject.get("favicon").getAsString().replace("\n", ""));
                        }
                        packetWrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson(jsonObject));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.STATISTICS, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final ArrayList<StatisticData> list = new ArrayList<StatisticData>();
                        while (0 < intValue) {
                            final String s = (String)packetWrapper.read(Type.STRING);
                            final String[] split = s.split("\\.");
                            final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                            if (split.length == 2) {
                                final Integer n = StatisticMappings.CUSTOM_STATS.get(s);
                                if (n != null) {
                                    n;
                                }
                                else {
                                    Via.getPlatform().getLogger().warning("Could not find 1.13 -> 1.12.2 statistic mapping for " + s);
                                }
                            }
                            else if (split.length > 2) {
                                final String s2 = split[1];
                                switch (s2.hashCode()) {
                                    case 664431610: {
                                        if (s2.equals("mineBlock")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case 1485652307: {
                                        if (s2.equals("craftItem")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -148334278: {
                                        if (s2.equals("useItem")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -1898270542: {
                                        if (s2.equals("breakItem")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -988476804: {
                                        if (s2.equals("pickup")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case 3092207: {
                                        if (s2.equals("drop")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -1964602143: {
                                        if (s2.equals("killEntity")) {
                                            break;
                                        }
                                        break;
                                    }
                                    case -863208777: {
                                        if (s2.equals("entityKilledBy")) {}
                                        break;
                                    }
                                }
                                switch (7) {
                                    case 0: {}
                                    case 1: {}
                                    case 2: {}
                                    case 3: {}
                                    case 4: {}
                                    case 5: {}
                                }
                            }
                            if (-1 != -1) {
                                list.add(new StatisticData(7, -1, intValue2));
                            }
                            int n2 = 0;
                            ++n2;
                        }
                        packetWrapper.write(Type.VAR_INT, list.size());
                        for (final StatisticData statisticData : list) {
                            packetWrapper.write(Type.VAR_INT, statisticData.getCategoryId());
                            packetWrapper.write(Type.VAR_INT, statisticData.getNewId());
                            packetWrapper.write(Type.VAR_INT, statisticData.getValue());
                        }
                    }
                });
            }
        });
        this.componentRewriter.registerBossBar(ClientboundPackets1_12_1.BOSSBAR);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.CHAT_MESSAGE);
        this.registerClientbound(ClientboundPackets1_12_1.TAB_COMPLETE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.VAR_INT, ((TabCompleteTracker)packetWrapper.user().get(TabCompleteTracker.class)).getTransactionId());
                        final String input = ((TabCompleteTracker)packetWrapper.user().get(TabCompleteTracker.class)).getInput();
                        int length;
                        if (input.endsWith(" ") || input.isEmpty()) {
                            length = input.length();
                        }
                        else {
                            final int n = input.length() - (length = input.lastIndexOf(32) + 1);
                        }
                        packetWrapper.write(Type.VAR_INT, length);
                        packetWrapper.write(Type.VAR_INT, 0);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            String substring = (String)packetWrapper.read(Type.STRING);
                            if (substring.startsWith("/") && length == 0) {
                                substring = substring.substring(1);
                            }
                            packetWrapper.write(Type.STRING, substring);
                            packetWrapper.write(Type.BOOLEAN, false);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.OPEN_WINDOW, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Protocol1_13To1_12_2.access$000(this.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.COOLDOWN, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                        packetWrapper.cancel();
                        if (intValue == 383) {
                            while (true) {
                                final Integer value = this.this$1.this$0.getMappingData().getItemMappings().get(intValue << 16 | 0x0);
                                if (value == null) {
                                    break;
                                }
                                final PacketWrapper create = packetWrapper.create(ClientboundPackets1_13.COOLDOWN);
                                create.write(Type.VAR_INT, value);
                                create.write(Type.VAR_INT, intValue2);
                                create.send(Protocol1_13To1_12_2.class);
                                int n = 0;
                                ++n;
                            }
                        }
                        else {
                            while (true) {
                                final int value2 = this.this$1.this$0.getMappingData().getItemMappings().get(intValue << 4 | 0x0);
                                if (value2 == -1) {
                                    break;
                                }
                                final PacketWrapper create2 = packetWrapper.create(ClientboundPackets1_13.COOLDOWN);
                                create2.write(Type.VAR_INT, value2);
                                create2.write(Type.VAR_INT, intValue2);
                                create2.send(Protocol1_13To1_12_2.class);
                                int n = 0;
                                ++n;
                            }
                        }
                    }
                });
            }
        });
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.DISCONNECT);
        this.registerClientbound(ClientboundPackets1_12_1.EFFECT, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                        if (intValue == 1010) {
                            packetWrapper.set(Type.INT, 1, this.this$1.this$0.getMappingData().getItemMappings().get(intValue2 << 4));
                        }
                        else if (intValue == 2001) {
                            packetWrapper.set(Type.INT, 1, WorldPackets.toNewId((intValue2 & 0xFFF) << 4 | intValue2 >> 12));
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.JOIN_GAME, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$8 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().getEntityTracker(Protocol1_13To1_12_2.class).addEntity((int)packetWrapper.get(Type.INT, 0), Entity1_13Types.EntityType.PLAYER);
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                    }
                });
                this.handler(Protocol1_13To1_12_2.access$100());
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.CRAFT_RECIPE_RESPONSE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.handler(Protocol1_13To1_12_2$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "viaversion:legacy/" + packetWrapper.read(Type.VAR_INT));
            }
        });
        this.componentRewriter.registerCombatEvent(ClientboundPackets1_12_1.COMBAT_EVENT);
        this.registerClientbound(ClientboundPackets1_12_1.MAP_DATA, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$10 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                            packetWrapper.write(Type.VAR_INT, (byteValue & 0xF0) >> 4);
                            packetWrapper.passthrough(Type.BYTE);
                            packetWrapper.passthrough(Type.BYTE);
                            packetWrapper.write(Type.BYTE, (byte)(byteValue & 0xF));
                            packetWrapper.write(Type.OPTIONAL_COMPONENT, null);
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.UNLOCK_RECIPES, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$11 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.BOOLEAN, false);
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                });
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$11 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        while (0 < ((intValue == 0) ? 2 : 1)) {
                            final int[] array = (int[])packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                            final String[] array2 = new String[array.length];
                            while (0 < array.length) {
                                array2[0] = "viaversion:legacy/" + array[0];
                                int n = 0;
                                ++n;
                            }
                            packetWrapper.write(Type.STRING_ARRAY, array2);
                            int n2 = 0;
                            ++n2;
                        }
                        if (intValue == 0) {
                            packetWrapper.create(ClientboundPackets1_13.DECLARE_RECIPES, new PacketHandler() {
                                final Protocol1_13To1_12_2$11$2 this$2;
                                
                                @Override
                                public void handle(final PacketWrapper packetWrapper) throws Exception {
                                    packetWrapper.write(Type.VAR_INT, RecipeData.recipes.size());
                                    for (final Map.Entry<Object, V> entry : RecipeData.recipes.entrySet()) {
                                        packetWrapper.write(Type.STRING, entry.getKey());
                                        packetWrapper.write(Type.STRING, ((RecipeData.Recipe)entry.getValue()).getType());
                                        final String type = ((RecipeData.Recipe)entry.getValue()).getType();
                                        switch (type.hashCode()) {
                                            case -571676035: {
                                                if (type.equals("crafting_shapeless")) {
                                                    break;
                                                }
                                                break;
                                            }
                                            case 1533084160: {
                                                if (type.equals("crafting_shaped")) {
                                                    break;
                                                }
                                                break;
                                            }
                                            case -491776273: {
                                                if (type.equals("smelting")) {}
                                                break;
                                            }
                                        }
                                        switch (2) {
                                            case 0: {
                                                packetWrapper.write(Type.STRING, ((RecipeData.Recipe)entry.getValue()).getGroup());
                                                packetWrapper.write(Type.VAR_INT, ((RecipeData.Recipe)entry.getValue()).getIngredients().length);
                                                final DataItem[][] ingredients = ((RecipeData.Recipe)entry.getValue()).getIngredients();
                                                final int n = ingredients.length;
                                                while (0 < 0) {
                                                    final DataItem[] array = ingredients[0].clone();
                                                    while (0 < array.length) {
                                                        if (array[0] != null) {
                                                            array[0] = new DataItem(array[0]);
                                                        }
                                                        int n2 = 0;
                                                        ++n2;
                                                    }
                                                    packetWrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, array);
                                                    int n3 = 0;
                                                    ++n3;
                                                }
                                                packetWrapper.write(Type.FLAT_ITEM, new DataItem(((RecipeData.Recipe)entry.getValue()).getResult()));
                                                continue;
                                            }
                                            case 1: {
                                                packetWrapper.write(Type.VAR_INT, ((RecipeData.Recipe)entry.getValue()).getWidth());
                                                packetWrapper.write(Type.VAR_INT, ((RecipeData.Recipe)entry.getValue()).getHeight());
                                                packetWrapper.write(Type.STRING, ((RecipeData.Recipe)entry.getValue()).getGroup());
                                                final DataItem[][] ingredients2 = ((RecipeData.Recipe)entry.getValue()).getIngredients();
                                                final int n = ingredients2.length;
                                                while (0 < 0) {
                                                    final DataItem[] array2 = ingredients2[0].clone();
                                                    while (0 < array2.length) {
                                                        if (array2[0] != null) {
                                                            array2[0] = new DataItem(array2[0]);
                                                        }
                                                        int n2 = 0;
                                                        ++n2;
                                                    }
                                                    packetWrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, array2);
                                                    int n3 = 0;
                                                    ++n3;
                                                }
                                                packetWrapper.write(Type.FLAT_ITEM, new DataItem(((RecipeData.Recipe)entry.getValue()).getResult()));
                                                continue;
                                            }
                                            case 2: {
                                                packetWrapper.write(Type.STRING, ((RecipeData.Recipe)entry.getValue()).getGroup());
                                                final DataItem[] array3 = ((RecipeData.Recipe)entry.getValue()).getIngredient().clone();
                                                while (0 < array3.length) {
                                                    if (array3[0] != null) {
                                                        array3[0] = new DataItem(array3[0]);
                                                    }
                                                    int n = 0;
                                                    ++n;
                                                }
                                                packetWrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, array3);
                                                packetWrapper.write(Type.FLAT_ITEM, new DataItem(((RecipeData.Recipe)entry.getValue()).getResult()));
                                                packetWrapper.write(Type.FLOAT, ((RecipeData.Recipe)entry.getValue()).getExperience());
                                                packetWrapper.write(Type.VAR_INT, ((RecipeData.Recipe)entry.getValue()).getCookingTime());
                                                continue;
                                            }
                                        }
                                    }
                                }
                            }).send(Protocol1_13To1_12_2.class);
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.RESPAWN, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$12 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                        if (Via.getConfig().isServersideBlockConnections()) {
                            ConnectionData.clearBlockStorage(packetWrapper.user());
                        }
                    }
                });
                this.handler(Protocol1_13To1_12_2.access$100());
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.SCOREBOARD_OBJECTIVE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$13 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                        if (byteValue == 0 || byteValue == 2) {
                            packetWrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson((String)packetWrapper.read(Type.STRING)));
                            packetWrapper.write(Type.VAR_INT, (int)(((String)packetWrapper.read(Type.STRING)).equals("integer") ? 0 : 1));
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.TEAMS, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$14 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper p0) throws Exception {
                        // 
                        // This method could not be decompiled.
                        // 
                        // Original Bytecode:
                        // 
                        //     1: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                        //     4: iconst_0       
                        //     5: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.get:(Lcom/viaversion/viaversion/api/type/Type;I)Ljava/lang/Object;
                        //    10: checkcast       Ljava/lang/Byte;
                        //    13: invokevirtual   java/lang/Byte.byteValue:()B
                        //    16: istore_2       
                        //    17: iload_2        
                        //    18: ifeq            26
                        //    21: iload_2        
                        //    22: iconst_2       
                        //    23: if_icmpne       238
                        //    26: aload_1        
                        //    27: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //    30: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    35: checkcast       Ljava/lang/String;
                        //    38: astore_3       
                        //    39: aload_1        
                        //    40: getstatic       com/viaversion/viaversion/api/type/Type.COMPONENT:Lcom/viaversion/viaversion/api/type/Type;
                        //    43: aload_3        
                        //    44: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/ChatRewriter.legacyTextToJson:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/JsonElement;
                        //    47: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //    52: aload_1        
                        //    53: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //    56: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    61: checkcast       Ljava/lang/String;
                        //    64: astore          4
                        //    66: aload_1        
                        //    67: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //    70: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    75: checkcast       Ljava/lang/String;
                        //    78: astore          5
                        //    80: aload_1        
                        //    81: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                        //    84: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    89: pop            
                        //    90: aload_1        
                        //    91: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //    94: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    99: pop            
                        //   100: aload_1        
                        //   101: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //   104: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   109: pop            
                        //   110: aload_1        
                        //   111: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                        //   114: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   119: checkcast       Ljava/lang/Byte;
                        //   122: invokevirtual   java/lang/Byte.intValue:()I
                        //   125: istore          6
                        //   127: bipush          21
                        //   129: iconst_m1      
                        //   130: if_icmpne       133
                        //   133: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
                        //   136: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.is1_13TeamColourFix:()Z
                        //   141: ifeq            196
                        //   144: aload_0        
                        //   145: getfield        com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2$14$1.this$1:Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2$14;
                        //   148: getfield        com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2$14.this$0:Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2;
                        //   151: aload           4
                        //   153: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2.getLastColorChar:(Ljava/lang/String;)C
                        //   156: istore          7
                        //   158: iload           7
                        //   160: invokestatic    com/viaversion/viaversion/util/ChatColorUtil.getColorOrdinal:(C)I
                        //   163: istore          6
                        //   165: new             Ljava/lang/StringBuilder;
                        //   168: dup            
                        //   169: invokespecial   java/lang/StringBuilder.<init>:()V
                        //   172: sipush          167
                        //   175: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
                        //   178: iload           7
                        //   180: invokestatic    java/lang/Character.toString:(C)Ljava/lang/String;
                        //   183: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                        //   186: aload           5
                        //   188: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                        //   191: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                        //   194: astore          5
                        //   196: aload_1        
                        //   197: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                        //   200: bipush          21
                        //   202: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                        //   205: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //   210: aload_1        
                        //   211: getstatic       com/viaversion/viaversion/api/type/Type.COMPONENT:Lcom/viaversion/viaversion/api/type/Type;
                        //   214: aload           4
                        //   216: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/ChatRewriter.legacyTextToJson:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/JsonElement;
                        //   219: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //   224: aload_1        
                        //   225: getstatic       com/viaversion/viaversion/api/type/Type.COMPONENT:Lcom/viaversion/viaversion/api/type/Type;
                        //   228: aload           5
                        //   230: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/ChatRewriter.legacyTextToJson:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/JsonElement;
                        //   233: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //   238: iload_2        
                        //   239: ifeq            252
                        //   242: iload_2        
                        //   243: iconst_3       
                        //   244: if_icmpeq       252
                        //   247: iload_2        
                        //   248: iconst_4       
                        //   249: if_icmpne       303
                        //   252: aload_1        
                        //   253: getstatic       com/viaversion/viaversion/api/type/Type.STRING_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                        //   256: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   261: checkcast       [Ljava/lang/String;
                        //   264: astore_3       
                        //   265: iconst_0       
                        //   266: aload_3        
                        //   267: arraylength    
                        //   268: if_icmpge       293
                        //   271: aload_3        
                        //   272: iconst_0       
                        //   273: aload_0        
                        //   274: getfield        com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2$14$1.this$1:Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2$14;
                        //   277: getfield        com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2$14.this$0:Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2;
                        //   280: aload_3        
                        //   281: iconst_0       
                        //   282: aaload         
                        //   283: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2.rewriteTeamMemberName:(Ljava/lang/String;)Ljava/lang/String;
                        //   286: aastore        
                        //   287: iinc            4, 1
                        //   290: goto            265
                        //   293: aload_1        
                        //   294: getstatic       com/viaversion/viaversion/api/type/Type.STRING_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                        //   297: aload_3        
                        //   298: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //   303: return         
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
        this.registerClientbound(ClientboundPackets1_12_1.UPDATE_SCORE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$15 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.STRING, this.this$1.this$0.rewriteTeamMemberName((String)packetWrapper.read(Type.STRING)));
                        final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                        packetWrapper.write(Type.BYTE, byteValue);
                        packetWrapper.passthrough(Type.STRING);
                        if (byteValue != 1) {
                            packetWrapper.passthrough(Type.VAR_INT);
                        }
                    }
                });
            }
        });
        this.componentRewriter.registerTitle(ClientboundPackets1_12_1.TITLE);
        new SoundRewriter(this).registerSound(ClientboundPackets1_12_1.SOUND);
        this.registerClientbound(ClientboundPackets1_12_1.TAB_LIST, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$16 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        Protocol1_13To1_12_2.access$000(this.this$1.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                        Protocol1_13To1_12_2.access$000(this.this$1.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_12_1.ADVANCEMENTS, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$17 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.passthrough(Type.BOOLEAN);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.STRING);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.STRING);
                            }
                            int intValue = 0;
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                Protocol1_13To1_12_2.access$000(this.this$1.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                                Protocol1_13To1_12_2.access$000(this.this$1.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                                final Item item = (Item)packetWrapper.read(Type.ITEM);
                                Protocol1_13To1_12_2.access$200(this.this$1.this$0).handleItemToClient(item);
                                packetWrapper.write(Type.FLAT_ITEM, item);
                                packetWrapper.passthrough(Type.VAR_INT);
                                intValue = (int)packetWrapper.passthrough(Type.INT);
                                if (false) {
                                    packetWrapper.passthrough(Type.STRING);
                                }
                                packetWrapper.passthrough(Type.FLOAT);
                                packetWrapper.passthrough(Type.FLOAT);
                            }
                            packetWrapper.passthrough(Type.STRING_ARRAY);
                            while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                                packetWrapper.passthrough(Type.STRING_ARRAY);
                                ++intValue;
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        this.cancelServerbound(State.LOGIN, 2);
        this.cancelServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT);
        this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$18 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (Via.getConfig().isDisable1_13AutoComplete()) {
                            packetWrapper.cancel();
                        }
                        ((TabCompleteTracker)packetWrapper.user().get(TabCompleteTracker.class)).setTransactionId((int)packetWrapper.read(Type.VAR_INT));
                    }
                });
                this.map(Type.STRING, new ValueTransformer(Type.STRING) {
                    final Protocol1_13To1_12_2$18 this$1;
                    
                    public String transform(final PacketWrapper packetWrapper, final String input) {
                        ((TabCompleteTracker)packetWrapper.user().get(TabCompleteTracker.class)).setInput(input);
                        return "/" + input;
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (String)o);
                    }
                });
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$18 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.BOOLEAN, false);
                        packetWrapper.write(Type.OPTIONAL_POSITION, null);
                        if (!packetWrapper.isCancelled() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
                            final TabCompleteTracker tabCompleteTracker = (TabCompleteTracker)packetWrapper.user().get(TabCompleteTracker.class);
                            packetWrapper.cancel();
                            tabCompleteTracker.setTimeToSend(System.currentTimeMillis() + Via.getConfig().get1_13TabCompleteDelay() * 50L);
                            tabCompleteTracker.setLastTabComplete((String)packetWrapper.get(Type.STRING, 0));
                        }
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$19 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item item = (Item)packetWrapper.read(Type.FLAT_ITEM);
                        final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                        Protocol1_13To1_12_2.access$200(this.this$1.this$0).handleItemToServer(item);
                        packetWrapper.write(Type.STRING, booleanValue ? "MC|BSign" : "MC|BEdit");
                        packetWrapper.write(Type.ITEM, item);
                    }
                });
            }
        });
        this.cancelServerbound(ServerboundPackets1_13.ENTITY_NBT_REQUEST);
        this.registerServerbound(ServerboundPackets1_13.PICK_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$20 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.STRING, "MC|PickItem");
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.CRAFT_RECIPE_REQUEST, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.handler(Protocol1_13To1_12_2$21::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.read(Type.STRING);
                final Integer tryParse;
                if (s.length() < 19 || (tryParse = Ints.tryParse(s.substring(18))) == null) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.VAR_INT, tryParse);
            }
        });
        this.registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$22 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (intValue == 0) {
                            final String s = (String)packetWrapper.read(Type.STRING);
                            final Integer tryParse;
                            if (s.length() < 19 || (tryParse = Ints.tryParse(s.substring(18))) == null) {
                                packetWrapper.cancel();
                                return;
                            }
                            packetWrapper.write(Type.INT, tryParse);
                        }
                        if (intValue == 1) {
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.read(Type.BOOLEAN);
                            packetWrapper.read(Type.BOOLEAN);
                        }
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.RENAME_ITEM, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_13To1_12_2$23::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|ItemName");
            }
        });
        this.registerServerbound(ServerboundPackets1_13.SELECT_TRADE, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_13To1_12_2$24::lambda$registerMap$0);
                this.map(Type.VAR_INT, Type.INT);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|TrSel");
            }
        });
        this.registerServerbound(ServerboundPackets1_13.SET_BEACON_EFFECT, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_13To1_12_2$25::lambda$registerMap$0);
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.VAR_INT, Type.INT);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|Beacon");
            }
        });
        this.registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_13To1_12_2$26::lambda$registerMap$0);
                this.handler(Protocol1_13To1_12_2.POS_TO_3_INT);
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$26 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                        final String s = (intValue == 0) ? "SEQUENCE" : ((intValue == 1) ? "AUTO" : "REDSTONE");
                        packetWrapper.write(Type.BOOLEAN, (byteValue & 0x1) != 0x0);
                        packetWrapper.write(Type.STRING, s);
                        packetWrapper.write(Type.BOOLEAN, (byteValue & 0x2) != 0x0);
                        packetWrapper.write(Type.BOOLEAN, (byteValue & 0x4) != 0x0);
                    }
                });
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|AutoCmd");
            }
        });
        this.registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK_MINECART, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$27 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.STRING, "MC|AdvCmd");
                        packetWrapper.write(Type.BYTE, 1);
                    }
                });
                this.map(Type.VAR_INT, Type.INT);
            }
        });
        this.registerServerbound(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final Protocol1_13To1_12_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_13To1_12_2$28::lambda$registerMap$0);
                this.handler(Protocol1_13To1_12_2.POS_TO_3_INT);
                this.map(Type.VAR_INT, new ValueTransformer((Type)Type.BYTE) {
                    final Protocol1_13To1_12_2$28 this$1;
                    
                    public Byte transform(final PacketWrapper packetWrapper, final Integer n) throws Exception {
                        return (byte)(n + 1);
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (Integer)o);
                    }
                });
                this.map(Type.VAR_INT, new ValueTransformer(Type.STRING) {
                    final Protocol1_13To1_12_2$28 this$1;
                    
                    public String transform(final PacketWrapper packetWrapper, final Integer n) throws Exception {
                        return (n == 0) ? "SAVE" : ((n == 1) ? "LOAD" : ((n == 2) ? "CORNER" : "DATA"));
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (Integer)o);
                    }
                });
                this.map(Type.STRING);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.BYTE, Type.INT);
                this.map(Type.VAR_INT, new ValueTransformer(Type.STRING) {
                    final Protocol1_13To1_12_2$28 this$1;
                    
                    public String transform(final PacketWrapper packetWrapper, final Integer n) throws Exception {
                        return (n == 0) ? "NONE" : ((n == 1) ? "LEFT_RIGHT" : "FRONT_BACK");
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (Integer)o);
                    }
                });
                this.map(Type.VAR_INT, new ValueTransformer(Type.STRING) {
                    final Protocol1_13To1_12_2$28 this$1;
                    
                    public String transform(final PacketWrapper packetWrapper, final Integer n) throws Exception {
                        return (n == 0) ? "NONE" : ((n == 1) ? "CLOCKWISE_90" : ((n == 2) ? "CLOCKWISE_180" : "COUNTERCLOCKWISE_90"));
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (Integer)o);
                    }
                });
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_12_2$28 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final float floatValue = (float)packetWrapper.read(Type.FLOAT);
                        final long longValue = (long)packetWrapper.read(Type.VAR_LONG);
                        final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                        packetWrapper.write(Type.BOOLEAN, (byteValue & 0x1) != 0x0);
                        packetWrapper.write(Type.BOOLEAN, (byteValue & 0x2) != 0x0);
                        packetWrapper.write(Type.BOOLEAN, (byteValue & 0x4) != 0x0);
                        packetWrapper.write(Type.FLOAT, floatValue);
                        packetWrapper.write(Type.VAR_LONG, longValue);
                    }
                });
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|Struct");
            }
        });
    }
    
    @Override
    protected void onMappingDataLoaded() {
        Types1_13.PARTICLE.filler(this).reader(3, ParticleType.Readers.BLOCK).reader(20, ParticleType.Readers.DUST).reader(11, ParticleType.Readers.DUST).reader(27, ParticleType.Readers.ITEM);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        userConnection.put(new TabCompleteTracker());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.put(new BlockStorage());
        if (Via.getConfig().isServersideBlockConnections() && Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof PacketBlockConnectionProvider) {
            userConnection.put(new BlockConnectionStorage());
        }
    }
    
    @Override
    public void register(final ViaProviders viaProviders) {
        viaProviders.register(BlockEntityProvider.class, new BlockEntityProvider());
        viaProviders.register(PaintingProvider.class, new PaintingProvider());
    }
    
    public char getLastColorChar(final String s) {
        final int length = s.length();
        for (int i = length - 1; i > -1; --i) {
            if (s.charAt(i) == '§' && i < length - 1) {
                final char char1 = s.charAt(i + 1);
                if (ChatColorUtil.isColorCode(char1) && !Protocol1_13To1_12_2.FORMATTING_CODES.contains(char1)) {
                    return char1;
                }
            }
        }
        return 'r';
    }
    
    protected String rewriteTeamMemberName(String string) {
        if (ChatColorUtil.stripColor(string).isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            while (1 < string.length()) {
                final char char1 = string.charAt(1);
                Character value = Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.get(char1);
                if (value == null) {
                    value = char1;
                }
                sb.append('§').append(value);
                final int n;
                n += 2;
            }
            string = sb.toString();
        }
        return string;
    }
    
    public static int[] toPrimitive(final Integer[] array) {
        final int[] array2 = new int[array.length];
        while (0 < array.length) {
            array2[0] = array[0];
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_13To1_12_2.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    public ComponentRewriter getComponentRewriter() {
        return this.componentRewriter;
    }
    
    @Override
    public com.viaversion.viaversion.api.data.MappingData getMappingData() {
        return this.getMappingData();
    }
    
    private static void lambda$static$3(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.create(ClientboundPackets1_13.DECLARE_COMMANDS, Protocol1_13To1_12_2::lambda$null$1).scheduleSend(Protocol1_13To1_12_2.class);
        packetWrapper.create(ClientboundPackets1_13.TAGS, Protocol1_13To1_12_2::lambda$null$2).scheduleSend(Protocol1_13To1_12_2.class);
    }
    
    private static void lambda$null$2(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, Protocol1_13To1_12_2.MAPPINGS.getBlockTags().size());
        for (final Map.Entry<Object, V> entry : Protocol1_13To1_12_2.MAPPINGS.getBlockTags().entrySet()) {
            packetWrapper.write(Type.STRING, entry.getKey());
            packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, toPrimitive((Integer[])(Object)entry.getValue()));
        }
        packetWrapper.write(Type.VAR_INT, Protocol1_13To1_12_2.MAPPINGS.getItemTags().size());
        for (final Map.Entry<Object, V> entry2 : Protocol1_13To1_12_2.MAPPINGS.getItemTags().entrySet()) {
            packetWrapper.write(Type.STRING, entry2.getKey());
            packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, toPrimitive((Integer[])(Object)entry2.getValue()));
        }
        packetWrapper.write(Type.VAR_INT, Protocol1_13To1_12_2.MAPPINGS.getFluidTags().size());
        for (final Map.Entry<Object, V> entry3 : Protocol1_13To1_12_2.MAPPINGS.getFluidTags().entrySet()) {
            packetWrapper.write(Type.STRING, entry3.getKey());
            packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, toPrimitive((Integer[])(Object)entry3.getValue()));
        }
    }
    
    private static void lambda$null$1(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, 2);
        packetWrapper.write(Type.BYTE, 0);
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 1 });
        packetWrapper.write(Type.BYTE, 22);
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
        packetWrapper.write(Type.STRING, "args");
        packetWrapper.write(Type.STRING, "brigadier:string");
        packetWrapper.write(Type.VAR_INT, 2);
        packetWrapper.write(Type.STRING, "minecraft:ask_server");
        packetWrapper.write(Type.VAR_INT, 0);
    }
    
    private static void lambda$static$0(final PacketWrapper packetWrapper) throws Exception {
        final Position position = (Position)packetWrapper.read(Type.POSITION);
        packetWrapper.write(Type.INT, position.x());
        packetWrapper.write(Type.INT, position.y());
        packetWrapper.write(Type.INT, position.z());
    }
    
    static ComponentRewriter access$000(final Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        return protocol1_13To1_12_2.componentRewriter;
    }
    
    static PacketHandler access$100() {
        return Protocol1_13To1_12_2.SEND_DECLARE_COMMANDS_AND_TAGS;
    }
    
    static ItemRewriter access$200(final Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        return protocol1_13To1_12_2.itemRewriter;
    }
    
    static {
        MAPPINGS = new MappingData();
        SCOREBOARD_TEAM_NAME_REWRITE = new HashMap();
        FORMATTING_CODES = Sets.newHashSet('k', 'l', 'm', 'n', 'o', 'r');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('0', 'g');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('1', 'h');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('2', 'i');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('3', 'j');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('4', 'p');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('5', 'q');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('6', 's');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('7', 't');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('8', 'u');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('9', 'v');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('a', 'w');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('b', 'x');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('c', 'y');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('d', 'z');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('e', '!');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('f', '?');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('k', '#');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('l', '(');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('m', ')');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('n', ':');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('o', ';');
        Protocol1_13To1_12_2.SCOREBOARD_TEAM_NAME_REWRITE.put('r', '/');
        POS_TO_3_INT = Protocol1_13To1_12_2::lambda$static$0;
        SEND_DECLARE_COMMANDS_AND_TAGS = Protocol1_13To1_12_2::lambda$static$3;
    }
}
